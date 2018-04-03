#coding:utf-8
import requests
import json
from operation_json import OperetionJson


class OperationHeader:

	def __init__(self,response):
		self.response = json.loads(response)

	def get_response_url(self):
		'''
		获取登录返回的token的url
		'''
		url = self.response['data']['url'][0]
		return url

	def get_cookie(self):
		'''
		获取cookie的jar文件
		'''
		url = self.get_response_url()+"&callback=jQuery21008240514814031887_1508666806688&_=1508666806689"
		cookie = requests.get(url).cookies
		return cookie

	def write_cookie(self):
		cookie = requests.utils.dict_from_cookiejar(self.get_cookie())
		op_json = OperetionJson()
		op_json.write_data(cookie)
		
if __name__ == '__main__':
	
	url = "http://m.ctoedu.com/passport/user/login"
	data = {
		"username":"18513199586",
		"password":"111111",
		"verify":"",
		"referer":"https://m.ctoedu.com"
	}
	res = json.dumps(requests.post(url,data).json())
	op_header = OperationHeader(res)
	op_header.write_cookie()
