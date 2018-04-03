# coding:utf-8
from util.operation_excel import OperationExcel
import data.data_config
from util.operation_json import OperetionJson
from util.connect_db import OperationMysql


class GetData:
    def __init__(self):
        self.opera_excel = OperationExcel()

    # 去获取excel行数,就是我们的case个数
    def get_case_lines(self):
        return self.opera_excel.get_lines()

    # 获取是否执行
    def get_is_run(self, row):
        flag = None
        col = int(data_config.get_run())
        run_model = self.opera_excel.get_cell_value(row, col)
        if run_model == 'yes':
            flag = True
        else:
            flag = False
        return flag

    # 是否携带header
    def is_header(self, row):
        col = int(data_config.get_header())
        header = self.opera_excel.get_cell_value(row, col)
        if header != '':
            return header
        else:
            return None

    # 获取请求方式
    def get_request_method(self, row):
        col = int(data_config.get_run_way())
        request_method = self.opera_excel.get_cell_value(row, col)
        return request_method

    # 获取url
    def get_request_url(self, row):
        col = int(data_config.get_url())
        url = self.opera_excel.get_cell_value(row, col)
        return url

    # 获取请求数据
    def get_request_data(self, row):
        col = int(data_config.get_data())
        data = self.opera_excel.get_cell_value(row, col)
        if data == '':
            return None
        return data

    # 通过获取关键字拿到data数据
    def get_data_for_json(self, row):
        opera_json = OperetionJson()
        request_data = opera_json.get_data(self.get_request_data(row))
        return request_data

    # 获取预期结果
    def get_expcet_data(self, row):
        col = int(data_config.get_expect())
        expect = self.opera_excel.get_cell_value(row, col)
        if expect == '':
            return None
        return expect

    # 通过sql获取预期结果
    def get_expcet_data_for_mysql(self, row):
        op_mysql = OperationMysql()
        sql = self.get_expcet_data(row)
        res = op_mysql.search_one(sql)
        return res.decode('unicode-escape')

    def write_result(self, row, value):
        col = int(data_config.get_result())
        self.opera_excel.write_value(row, col, value)

    # 获取依赖数据的key
    def get_depend_key(self, row):
        col = int(data_config.get_data_depend())
        depent_key = self.opera_excel.get_cell_value(row, col)
        if depent_key == "":
            return None
        else:
            return depent_key

    # 判断是否有case依赖
    def is_depend(self, row):
        col = int(data_config.get_case_depend())
        depend_case_id = self.opera_excel.get_cell_value(row, col)
        if depend_case_id == "":
            return None
        else:
            return depend_case_id

    # 获取数据依赖字段
    def get_depend_field(self, row):
        col = int(data_config.get_field_depend())
        data = self.opera_excel.get_cell_value(row, col)
        if data == "":
            return None
        else:
            return data
