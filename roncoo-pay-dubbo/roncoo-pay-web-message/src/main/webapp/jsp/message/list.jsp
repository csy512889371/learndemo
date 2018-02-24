<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib/taglib.jsp"%>
<form id="pagerForm" method="post" action="${baseURL }/message/list">
	<%@include file="../common/pageParameter.jsp"%>
</form>
<!-- 
 龙果学院： www.roncoo.com
 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 讲师：吴水成（水到渠成），840765167@qq.com
 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 -->
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/message/list" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>消息ID： <input type="text" name="messageId" value="${param.messageId}" />
					</td>

					<td>状态： <select name="status">
							<option value="">请选择</option>
							<c:forEach items="${messageStatus}" var="statusVar">
								<option value="${statusVar.name}" <c:if test="${param.status == statusVar.name}"> selected="selected"</c:if>>${statusVar.desc}</option>
							</c:forEach>
					</select>
					</td>

					<td>消费队列： <select name="consumerQueue">
							<option value="">请选择</option>
							<c:forEach items="${queues}" var="queue">
								<option value="${queue.name}" <c:if test="${param.consumerQueue == queue.name}"> selected="selected"</c:if>>${queue.desc}</option>
							</c:forEach>
					</select>
					</td>
					<td>是否死亡： <select name="areadlyDead">
							<option value="">请选择</option>
							<option value="YES" <c:if test="${param.areadlyDead eq 'YES'}"> selected="selected"</c:if>>已死亡</option>
							<option value="NO" <c:if test="${param.areadlyDead eq 'NO'}"> selected="selected"</c:if>>未死亡</option>
					</select>
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button title="查询" type="submit">查&nbsp;询</button>
							</div>
						</div>

					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<label><b>死亡消息处理：</b></label>
				<td>
				消费队列： 
				<select name="queueName" id="queueName">
						<option value="">请选择</option>
						<c:forEach items="${queues}" var="queue">
							<option value="${queue.name}" <c:if test="${param.consumerQueue == queue.name}"> selected="selected"</c:if>>${queue.desc}</option>
						</c:forEach>
				</select>
				</td>
			</li>
			<li>&nbsp;&nbsp;</li>
			<a title="确定批量重新发送？" target="ajaxTodo" href="" onclick="js_method(this)" id="getQueueName" style="color: blue;">根据队列批量发送</a>
		</ul>
	</div>

	<table class="table" width="101%" layoutH="112">
		<thead>
			<tr>
				<th width="30">序号</th>
				<th width="130">创建时间</th>
				<th width="130">修改时间</th>
				<th width="250">消息ID</th>
				<th width="250">消费队列</th>
				<th width="50">状态</th>
				<th width="60">重发次数</th>
				<th width="60">是否死亡</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
				<tr>
					<td>${s.index + 1}</td>
					<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${item.editTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${item.messageId}</td>
					<td>${item.consumerQueue}</td>
					<td>
						<c:if test="${item.status eq 'WAITING_CONFIRM'}">待确认</c:if>
						<c:if test="${item.status eq 'SENDING'}">发送中</c:if>
					</td>
					<td>${item.messageSendTimes}</td>
					<td>
						<c:if test="${item.areadlyDead eq 'YES'}"><b style="color:red;">已死亡</b></c:if>
						<c:if test="${item.areadlyDead eq 'NO'}">未死亡</c:if>
					</td>
					<td>
						<c:if test="${item.areadlyDead eq 'YES'}">
							<a title="重新发送" target="ajaxTodo" href="${baseURL }/message/sendMessage?messageId=${item.messageId}" style="color: blue;" rel="dk"><b>重新发送</b></a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${pageBean.totalCount==0}">
				<tr>
					<td>暂无数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<%@include file="../common/pageBar.jsp"%>
</div>

<script type="text/javascript">
	function js_method(_a) {
		var queueName = document.getElementById("queueName").value;
		var url = "${baseURL }/message/sendAllMessage?queueName=" + queueName;
		document.getElementById("getQueueName").setAttribute("href", url);
		_a.click();
	}
</script>
