<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公司年报管理</title>
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
		<li><a href="${ctx}/companyinfoyear/companyIntroductionYear/">公司年报列表</a></li>
		<li class="active"><a href="${ctx}/companyinfoyear/companyIntroductionYear/form?id=${companyIntroductionYear.id}">公司年报<shiro:hasPermission name="companyinfoyear:companyIntroductionYear:edit">${not empty companyIntroductionYear.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="companyinfoyear:companyIntroductionYear:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="companyIntroductionYear" action="${ctx}/companyinfoyear/companyIntroductionYear/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">文件名称：</label>
			<div class="controls">
				<form:input path="fileName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司中文法定名称：</label>
			<div class="controls">
				<form:input path="chineseNameOfCompany" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司法定英文名称：</label>
			<div class="controls">
				<form:input path="englishNameOfCompany" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营范围：</label>
			<div class="controls">
				<form:input path="manageScopeCompany" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会计师事务所：</label>
			<div class="controls">
				<form:input path="accountingFirm" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">中国注册会计师1：</label>
			<div class="controls">
				<form:input path="chineseCpa1" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">中国注册会计师2：</label>
			<div class="controls">
				<form:input path="chineseCpa2" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司会计负责人：</label>
			<div class="controls">
				<form:input path="accountingDirector" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司会计机构负责人：</label>
			<div class="controls">
				<form:input path="organizationAccountingDirector" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司董事、监事、高管简介：</label>
			<div class="controls">
				<form:input path="directorSupervisorTopmanagerIntro" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主承销商1：</label>
			<div class="controls">
				<form:input path="leadUnderwriter1" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主承销商2：</label>
			<div class="controls">
				<form:input path="leadUnderwriter2" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资信评级机构：</label>
			<div class="controls">
				<form:input path="creditRatingAgencies" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<form:input path="releaseTime" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收费：</label>
			<div class="controls">
				<form:input path="charge" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">聘用、解聘会计师事务所情况：</label>
			<div class="controls">
				<form:input path="engageDismissAccountingFirm" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="companyinfoyear:companyIntroductionYear:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>