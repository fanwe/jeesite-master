<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
		<li><a href="${ctx}/procduct/shopProduct/">商品列表</a></li>
		<li class="active"><a href="${ctx}/procduct/shopProduct/form?id=${shopProduct.id}">商品<shiro:hasPermission name="procduct:shopProduct:edit">${not empty shopProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="procduct:shopProduct:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopProduct" action="${ctx}/procduct/shopProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品副标题：</label>
			<div class="controls">
				<form:input path="subtitle" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品类型：</label>
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="category" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
			<div class="controls">
				 <sys:treeselect id="category" name="category" value="${shopProduct.category}" labelName="shopProduct.categoryname" labelValue="${shopProduct.categoryname}"
					title="用户" url="/shopcatgory/shopCategory/treeData" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品图片：</label>
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="mainImg" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
			<div class="controls">
				<form:hidden path="mainImg" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="mainImg" type="images" uploadPath="/cms/article" selectMultiple="false"/>
<%-- 				<sys:ckfinder input="image" type="images" uploadPath="/cms/category"/> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品详情：</label>
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="detail" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
<!-- 			</div> -->
				<div class="controls">
				<form:textarea id="detail" htmlEscape="false" path="detail" rows="4" maxlength="200" class="input-xxlarge"/>
				<sys:ckeditor replace="detail" uploadPath="/cms/site" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品价格：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品库存：</label>
			<div class="controls">
				<form:input path="stock" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品品牌：</label>
			<div class="controls">
				<form:select path="brand" class="input-xlarge ">
					<c:forEach items="${ShopBrandList}" var="brand">
						<form:option value="${brand.id}" label="${brand.name }"/>
					
					</c:forEach>
<%-- 					<form:options items="${shopCategoryList}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
				</form:select>
			</div>	
		</div>
		<div class="control-group">
			<label class="control-label">所在仓库：</label>
			<div class="controls">
<%-- 				<form:input path="warehouse" htmlEscape="false" maxlength="255" class="input-xlarge "/> --%>
				<form:select path="warehouse" class="input-xlarge ">
					<c:forEach items="${warehoseList}" var="ware">
						<form:option value="${ware.id}" label="${ware.name }"/>
					</c:forEach>
				</form:select>
			</div>
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
			<shiro:hasPermission name="procduct:shopProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>