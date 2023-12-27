from flask import Flask,render_template
import werkzeug.serving
from flask import request,send_file,make_response
from flask import jsonify, send_from_directory
from PIL import Image
import io
import base64
import codecs
app = Flask(__name__)

@app.route('/compute',methods=['POST'])
def archive():
    if request.method == 'POST': #이미지
        with open('32.jpg','rb') as f :
            img = f.read()
        print(send_file(io.BytesIO(img),mimetype='image/jpeg'))
    return send_file(io.BytesIO(img),mimetype='image/jpeg')

@app.route('/check',methods=['POST'])
def check():
    if request.method == 'POST' :
        json_data = request.get_json()
        content = json_data.get('content')
        url1 = '/theme/1.jpg'
        url2 = '/theme/2.jpg'
        url3 = '/theme/3.jpg'
        url4 = '/theme/4.jpg'
        summed = content
    # JSON 형태로 응답
        response_data = {
            'url1': url1,
            'url2': url2,
            'url3': url3,
            'url4': url4,
            'summed': summed
        }

        return jsonify(response_data)
    
@app.route('/theme/<filename>', methods=['POST', 'GET'])
def get_data(filename):
    return send_from_directory('C:\Users\piy13\Desktop\theme', filename)


app.run( '0.0.0.0' ,port = 10011, threaded=True,debug=True)