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

myclient = pymongo.MongoClient("mongodb+srv://kds:<password>@cluster0-e1dhw.mongodb.net/results?retryWrites=true&w=majority")
mydb =myclient['facepay']
mycol=mydb['users']

@app.route("/")
def index():
    return "hey"


if __name__=="__main__":
    app.run(debug=True,threaded=False)