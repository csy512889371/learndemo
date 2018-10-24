1) roncoo-pay-dubbo 基于dubbo的微服务分布式事务解决方案
2) spring-cloud-microservice-in-action spring cloud 例子
3) ctoedu-dubbo dubbo rest 
4) ctoedu-dubbo-demo 使用dubbo注解方式
5) mybatis 简单的mybatis例子（初学）
6) ctoedu-ldap 基于ldap实现
7) ctoedu-jap-example jpa 多对多 一对多等映射例子
8) ctoedu-oauth spring security oauth2 实现认证
9) cto-edu-springboot-demo-two 例子：缓存（redis ehcache）/ 发送邮件 /spring security /rabbitmq
10) ctoedu-ThreadPool-TaskExecutor 线程池异步阻塞调用
11) ctoedu-redis redis 线程池 管道
12) ctoedu-rabitmq rabitmq 使用
13) elasticsearch-analysis-ik-5.2.0 ik分词器支持乐加载

14) p3test python 接口测试

## 15 miaosha 秒杀

* spring boot
* redis 集成
* mq集成
* 后台参数校验
* 全局异常
* 两次md5加密
* 分布式session
* 缓存
* jmeter 测试
* 限流
* 隐藏接口
* 验证码 34 +9
* 异步处理（mq）秒杀等


## 16 soufang 搜房 :elastic 实战

* spring boot
* spring security 用户认证
* jpa
* junit test h2
* 文件上传、上传到七牛云存储
* 阿里云短信配置
* elastic search
** 异步创建索引
** 搜索提示
** 聚合统计
** 结合百度地图点聚合
** 百度LBS 

* ELK 
* kafka
* Thymeleaf
* email邮件

## 17 kafka

kafka/Jkafka 基于java的kafka消费者和生产者实例
kafka/Pykafka 基于python的kafka消费者和生产者实例


## 18 kafka-storm-log

flume kafka storm redis 整合
参照博客 https://blog.csdn.net/qq_27384769/article/details/80158968



## 19 spark-streaming-kafka

实时项目统计

javaweb log -> flume -> kafka -> sparkStreaming -> Hbase -> Spring boot -> echart

参考博客 https://blog.csdn.net/qq_27384769/article/details/80220626


## 20 python/nowstagram

flask 简单例子 包含 数据库持久层框架flask_sqlalchemy

部署

> 服务器
```
apt-get install nginx mysql-server gunicorn python-flask libmysqlclient-dev python-dev
```

> 依赖包
```
pip install Flask-Script Flask-SQLAlchemy Flask-Login qiniu Flask-MySQLdb
```

Nginx 配置 /etc/nginx/sites-enabled/c1

```
server {
	listen 80;
	server_name c1.nowcoder.com;
	location / {
		proxy_pass http://127.0.0.1:8000;
	}
}
```

启动服务器：

```
gunicorn –D –w 2*core+1 –b 127.0.0.1:8000 nowstagram:app
```

## 21 wenda 问答

* spring boot 
* mybatis
* 敏感词过滤 敏感词树/中文分词
* 关注、收藏、通知
* redis 事务
* JedisAdapter JedisPool zrange zscore zcard exec multi
* timeline 推 拉 推/拉 （活跃 在线）
* 算分算法


## 22 scala/ScalaInAction

ScalaInAction scala 的基础demo


## 23 netty

. IOServer NIO简单例子
. NettyHello Netty3 hello 例子 包括服务端和客户端
. NIONetty netty3 的核心代码解析。将netty 基于nio基础上加上多线程对nio进行优化。
. nettySource netty3源码
. Netty5 的例子 + 简单rpc
. netty心跳 netty3heart netty5heart
. ProtoTest 序列化与反序化  与java 序列化反序列化
* chat_netty3 基于netty3
* chat_netty4 基于netty4
* chat_protobuf 序列化协议 使用protobuf

## 24 opencv-tensorflow

python/opencv-tensorflow 基于anaconda jupyter编写的例子代码



image::https://github.com/csy512889371/learnDoc/blob/master/image/2018/fz/44.png?raw=true[ctoedu,800,450]


## 25 go/analysis

* go 协程 实现网站 pv uv 统计

## 26 go/golearn
* go 基础学习

## 27 ctoedu_business 
 
* android
* 


