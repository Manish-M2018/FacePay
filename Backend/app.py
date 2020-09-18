from flask import Flask,request,jsonify
import hashlib
import os
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
        mydict={
            "email_id":eml,
            "password":phash,
            "balance":0,
        }
        mycol.insert_one(mydict)
        ret_obj['success']=True
    return ret_obj


if __name__=="__main__":
    app.run(debug=True,threaded=False)