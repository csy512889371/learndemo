<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="../../common/taglib/taglib.jsp"%>
<%@include file="../common/dwz.jsp"%>
<!-- 
 龙果学院： www.roncoo.com
 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 讲师：吴水成（水到渠成），840765167@qq.com
 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>龙果微支付系统（分布式事务教程版）管理后台</title>
</head>
<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#">标志</a>
				<ul class="nav">
					<li><a href="http://www.roncoo.com" target="_blank">龙果学院</a></li>
					<li><a href="../">退出系统</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default">
						<div class="selected">蓝色</div>
					</li>
					<li theme="green">
						<div>绿色</div>
					</li>
					<li theme="purple">
						<div>紫色</div>
					</li>
					<li theme="silver">
						<div>银色</div>
					</li>
					<li theme="azure">
						<div>天蓝</div>
					</li>
				</ul>
			</div>
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>系统菜单</h2>
					<div>收缩</div>
				</div>
				<div class="accordion" fillSpace="sidebar">
				
					<div class="accordionHeader">
						<h2>
							<span>Folder</span> 订单管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${baseURL }/trade/listPaymentOrder" target="navTab" rel="zfddgl">支付订单管理</a></li>
							<li><a href="${baseURL }/trade/listPaymentRecord" target="navTab" rel="zfjjgl">支付记录管理</a></li>
						</ul>
					</div>
					
					<div class="accordionHeader">
						<h2>
							<span>Folder</span> 账户管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${baseURL }/account/list" target="navTab" rel="zhxx">账户信息</a></li>
							<li><a href="${baseURL }/account/historyList" target="navTab" rel="zhlsxx">账户历史信息</a></li>
						</ul>
					</div>

					<div class="accordionHeader">
						<h2>
							<span>Folder</span> 用户管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${baseURL }/user/info/list" target="navTab" rel="yhxx">用户信息</a></li>
						</ul>
					</div>

					<div class="accordionHeader">
						<h2>
							<span>Folder</span> 支付管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${baseURL }/pay/product/list" target="navTab" rel="zfcpgl">支付产品管理</a></li>
							<li><a href="${baseURL }/pay/config/list" target="navTab" rel="yhzfpz">用户支付配置</a></li>
						</ul>
					</div>

				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"> <span> <span class="home_icon">我的主页</span>
								</span>
							</a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<p>
								<span>龙果微支付系统</span>
							</p>
							<p>
								开源版龙果微支付系统--更多持续更新资料请登录龙果学院: <a href="http://www.roncoo.com" target="_blank">http://www.roncoo.com</a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		Copyright &copy; 2015-2016 <a href="http://www.roncoo.com" target="_blank">龙果学院&nbsp;www.roncoo.com&nbsp;&nbsp;&nbsp;&nbsp;《微服务架构的分布式事务解决方案》视频教程，吴水成</a>
	</div>
</body>
</html>