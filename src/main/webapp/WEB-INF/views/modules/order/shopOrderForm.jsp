<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
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
		<li><a href="${ctx}/order/shopOrder/">订单列表</a></li>
		<li class="active"><a href="${ctx}/order/shopOrder/form?id=${shopOrder.id}">订单<shiro:hasPermission name="order:shopOrder:edit">${not empty shopOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:shopOrder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopOrder" action="${ctx}/order/shopOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">订单号：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="orderno" htmlEscape="false" maxlength="255" class="input-xlarge required"/> --%>
<!-- 				<span class="help-inline"><font color="red">*</font> </span> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">会员昵称：</label>
			<div class="controls">
				<form:select path="userid" class="input-xlarge required">
					<c:forEach items="${users }" var= "user">
						<form:option value="${user.id }" label="${user.usernickname}"/> 
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付方式：</label>
			<div class="controls">
				<form:select path="paytype" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('payType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付状态：</label>
			<div class="controls">
				<form:select path="paystatus" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('payStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				<form:select path="orderstatus" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('orderStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单总额：</label>
			<div class="controls">
				<form:input path="amcount" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单运费：</label>
			<div class="controls">
				<form:input path="fee" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品总数量：</label>
			<div class="controls">
				<form:input path="quantity" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">送货地址：</label>
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="city" htmlEscape="false" maxlength="255" class="input-xlarge required"/> --%>
<!-- 				<span class="help-inline"><font color="red">*</font> </span> -->
<!-- 			</div> -->
			
			<div class="controls">
                <sys:treeselect id="city" name="city" value="${shopOrder.city}" labelName="${shopOrder.cityName }" labelValue="${shopOrder.cityName }"
					title="城市" url="/sys/area/treeData" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">送货详细地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">快递运单号：</label>
			<div class="controls">
				<form:input path="expressno" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">快递公司：</label>
			<div class="controls">
				<form:select path="expresscompanyname" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('express')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:shopOrder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>