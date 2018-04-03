#coding:utf-8
import MySQLdb.cursors
import json
class OperationMysql:
	def __init__(self):
		self.conn = MySQLdb.connect(
			host='localhost',
			port=3306,
			user='root',
			passwd='123456',
			db='le_study',
			charset='utf8',
			cursorclass=MySQLdb.cursors.DictCursor
			)
		self.cur = self.conn.cursor()

	#查询一条数据
	def search_one(self,sql):
		self.cur.execute(sql)
		result = self.cur.fetchone()
		result = json.dumps(result)
		return result

if __name__ == '__main__':
	op_mysql = OperationMysql()
	res = op_mysql.search_one("select * from web_user where Name='ctoedu'")
	print res
