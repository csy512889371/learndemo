<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib/taglib.jsp"%>
<!-- 
 龙果学院： www.roncoo.com
 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 讲师：吴水成（水到渠成），840765167@qq.com
 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 -->
<div class="pageContent">
	<form action="${baseURL }/pay/way/edit" cssClass="pageForm required-validate"
        onsubmit="return validateCallback(this, navTabAjaxDone);" method="post" >
        <input type="hidden" name="navTabId" value="zftdgl">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <input type="hidden" name="id" value="${rpPayWay.id}">
        <div class="tabsContent pageFormContent"  layoutH="56">
			<div>
				<fieldset>
					<legend>修改支付费率</legend>
                    <dl>
							<dt>支付方式：</dt>
							<dd>
								${rpPayWay.payWayName}
							</dd>
					</dl>
					<dl>
							<dt>支付类型：</dt>
							<dd>
								${rpPayWay.payTypeName}
							</dd>
					</dl>
					<dl>
							<dt>支付费率：</dt>
							<dd>
								<input type="text" name="payRate" maxlength="128" class="required number" value="${rpPayWay.payRate }"/>%
							</dd>
					</dl>
				</fieldset>
			</div>
		</div>
		<div class="formBar">
			<ul style="float: left;">
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">提交</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>