<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 
 龙果学院： www.roncoo.com
 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 讲师：吴水成（水到渠成），840765167@qq.com
 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 -->
<div class="panelBar">
	<div class="pages">
		<span>显示</span>
		<select theme="simple" cssClass="combox" name="numPerPage"
			value="${pageBean.numPerPage}" onchange="navTabPageBreak({numPerPage:this.value})">
			<c:forEach begin="15" end="100" step="5" varStatus="s">
				<option value="${s.index}" ${pageBean.numPerPage eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
			</c:forEach>
		</select>
		<span> 条,共<a style="color: red">${pageBean.totalCount}</a>条, 共${pageBean.totalPage}页, 当前第${pageBean.currentPage}页 </span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageBean.totalCount}" numPerPage="${pageBean.numPerPage}"
		pageNumShown="10"
		currentPage="${pageBean.currentPage}"></div>
</div>