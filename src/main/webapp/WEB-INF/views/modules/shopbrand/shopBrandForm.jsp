<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品品牌管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shopbrand/shopBrand/">商品品牌列表</a></li>
		<li class="active"><a href="${ctx}/shopbrand/shopBrand/form?id=${shopBrand.id}">商品品牌<shiro:hasPermission name="shopbrand:shopBrand:edit">${not empty shopBrand.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shopbrand:shopBrand:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopBrand" action="${ctx}/shopbrand/shopBrand/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">品牌图片：</label>
			<div class="controls">
				<form:hidden path="brandUrl" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="brandUrl" type="images" uploadPath="/cms/article" selectMultiple="false"/>
<%-- 				<sys:ckfinder input="image" type="images" uploadPath="/cms/category"/> --%>
			</div>
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="brandUrl" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
		</div>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shopbrand:shopBrand:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>