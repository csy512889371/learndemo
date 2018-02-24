<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
 龙果学院： www.roncoo.com
 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 讲师：吴水成（水到渠成），840765167@qq.com
 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 -->
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="${baseURL }/common/taglib/taglib.jsp" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>龙果微支付系统管理后台</title>
<link rel="stylesheet" type="text/css" href="${baseURL }/dwz/themes/css/login.css" />
<script type="text/javascript" language="javascript" src="${baseURL }/common/js/login/login.js"></script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="http://www.roncoo.com" target="_blank"><img src="${baseURL }/dwz/themes/default/images/login_logo.png" /></a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#">设为首页</a></li>
					</ul>
				</div>
				<h2 class="login_title"><img src="${baseURL }/dwz/themes/default/images/login_title.png" /></h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form id="form1" action="${baseURL }/login/index" method="post">
				    <div style="color: red;padding-left: 80px;padding-bottom: 10px;">${message}</div>
					<div class="login_user">
			              <ul>
			                  <li><label>帐&nbsp;&nbsp;&nbsp;&nbsp;号：</label></li>
			                  <li>
			                       <input type="text" name="empNo" size="18" class="login_input" value="admin"/>
			                  </li>
			              </ul>
			              <ul>
			                  <li><label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label></li>
			                  <li>
			                      <input type="password" name="empPwd" size="18" class="login_input" value="admin"/>
			                  </li>
			              </ul>
			        </div>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="${baseURL }/dwz/themes/default/images/login_banner.jpg" /></div>
			<div class="login_main">
				<ul class="helpList">
				</ul>
				<div class="login_inner">
				</div>
			</div>
		</div>
		<div id="login_footer">
			Copyright &copy; 2015-2016 <a href="http://www.roncoo.com" target="_blank">龙果学院</a>
		</div>
	</div>
</body>
</html>