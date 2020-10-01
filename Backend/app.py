from flask import Flask,request,jsonify
import hashlib
import os
import face_recognition as fr
import cv2
import numpy as np
import pandas as pd
import tensorflow as tf
import pymongo
from werkzeug.utils import secure_filename
app =Flask(__name__)
app.secret_key = b'_5#y2L"F4Q8z\n\xec]/'

myclient = pymongo.MongoClient("mongodb+srv://kds:Password123@cluster0-e1dhw.mongodb.net/facepay?retryWrites=true&w=majority")
mydb =myclient['facepay']


@app.route("/")
def index():
    return "app working"

@app.route("/register",methods = ['POST'])
def register():
    ret_obj={}
    mycol=mydb['users']
    eml=request.form.get('email_id')
    pwd=request.form.get('password')
    phash=hashlib.md5(pwd.encode())
    phash=phash.hexdigest()
    
    myquery = { "email_id": eml }
    mydoc = mycol.find(myquery)
    exists=[x for x in mydoc]

    if len(exists)!=0:
        ret_obj['success']=False
    else:
        
        file = request.files['file']
        filename = secure_filename(file.filename)
        filename="faces/"+eml.split("@")[0] +"."+filename.split(".")[1]
        file.save(filename)
        mydict={
            "email_id":eml,
            "password":phash,=
            "balance":0,
            "filename":filename
        }
        mycol.insert_one(mydict)
        ret_obj['success']=True
    return ret_obj

@app.route("/login",methods = ['POST'])
def login():
    ret_obj={}
    ret_obj['success']=False
    email = request.form['email_id']
    psw = request.form['password']
    phash=hashlib.md5(psw.encode())
    phash=phash.hexdigest()
    mycol=mydb['users']
    user = mycol.find_one({'email_id':email})
    if user['email_id']==email and user['password']==phash:
        ret_obj['balance']=0
        ret_obj['success']=True

    return ret_obj

@app.route("/add_amount",methods = ['POST'])
def add_amount():
    ret_obj={}
    ret_obj['success']=False
    mycol=mydb['users']
    eml=request.form.get('email_id')
    user = mycol.find_one({ "email_id": eml })
    amt=float(request.form['amount'])

    newbalance=float(user['balance'])+amt
    newvalues = { "$set": { "balance": newbalance } }
    mycol.update_one(user, newvalues)
    ret_obj['newbalance']=newbalance
    mycol=mydb['transactions']
    mydict={
        "email_id":eml,
        "amount":amt
    }
    mycol.insert_one(mydict)
    ret_obj['success']=True

    return ret_obj

@app.route("/pay",methods = ['POST'])
def pay():
    ret_obj={}
    ret_obj['success']=False
    from_email = request.form['from_email_id']
    to_email = request.form['to_email_id']
    amt=float(request.form['amount'])
    mycol=mydb['users']
    from_user = mycol.find_one({'email_id':from_email})
    if float(from_user['balance'])< amt:
        ret_obj['message']="Insufficient Balance"
    
    else:
        # add amt to_user
        to_user=mycol.find_one({'email_id':to_email})
        newbalance=float(to_user['balance'])+amt
        newvalues = { "$set": { "balance": newbalance } }
        mycol.update_one(to_user, newvalues)
        ret_obj['to_newbalance']=newbalance
        #subract amt from_user 
        newbalance=float(from_user['balance'])-amt
        newvalues = { "$set": { "balance": newbalance } }
        mycol.update_one(from_user, newvalues)
        ret_obj['from_newbalance']=newbalance
        # add it to transactions
        mycol=mydb['transactions']
        mydict={
            "from_email":from_email,
            "to_email":to_email,
            "amount":amt
        }
        mycol.insert_one(mydict)

        ret_obj['success']=True

    return ret_obj

@app.route("/compare_faces",methods = ['POST'])
def compare_face():
    ret_obj={}
    ret_obj['success']=False
    known_url = request.form['known_url']
    known_face = fr.load_image_file(known_url)
    unknown_face=fr.load_image_file(unknown_url)
    known_encoding = fr.face_encodings(known_face)[0]
    unknown_encoding = fr.face_encodings(unknown_face)[0]
    ans= fr.compare_faces(known_encoding,unknown_encoding,tolerance=.5)

if __name__=="__main__":
    app.run(debug=True, threaded=False)