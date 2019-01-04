<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公司年报管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/companyinfoyear/companyIntroductionYear/">公司年报列表</a></li>
		<shiro:hasPermission name="companyinfoyear:companyIntroductionYear:edit"><li><a href="${ctx}/companyinfoyear/companyIntroductionYear/insert">公司年报添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="companyIntroductionYear" action="${ctx}/companyinfoyear/companyIntroductionYear/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>文件名称：</label>
				<form:input path="fileName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>文件标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>创建时间</th>
				<th>文件名称</th>
				<th>文件标题</th>
				<th>公司中文法定名称</th>
				<th>公司法定英文名称</th>
				<th>经营范围</th>
				<th>会计师事务所</th>
				<th>中国注册会计师1</th>
				<th>中国注册会计师2</th>
				<th>公司会计负责人</th>
				<th>公司会计机构负责人</th>
				<th>公司董事、监事、高管简介</th>
				<th>主承销商1</th>
				<th>主承销商2</th>
				<th>资信评级机构</th>
				<th>发布时间</th>
				<th>收费</th>
				<th>聘用、解聘会计师事务所情况</th>
				<shiro:hasPermission name="companyinfoyear:companyIntroductionYear:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="companyIntroductionYear">
			<tr>
				<td><a href="${ctx}/companyinfoyear/companyIntroductionYear/form?id=${companyIntroductionYear.id}">
					<fmt:formatDate value="${companyIntroductionYear.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${companyIntroductionYear.fileName}
				</td>
				<td>
					${companyIntroductionYear.title}
				</td>
				<td>
					${companyIntroductionYear.chineseNameOfCompany}
				</td>
				<td>
					${companyIntroductionYear.englishNameOfCompany}
				</td>
				<td>
					${companyIntroductionYear.manageScopeCompany}
				</td>
				<td>
					${companyIntroductionYear.accountingFirm}
				</td>
				<td>
					${companyIntroductionYear.chineseCpa1}
				</td>
				<td>
					${companyIntroductionYear.chineseCpa2}
				</td>
				<td>
					${companyIntroductionYear.accountingDirector}
				</td>
				<td>
					${companyIntroductionYear.organizationAccountingDirector}
				</td>
				<td>
					${companyIntroductionYear.directorSupervisorTopmanagerIntro}
				</td>
				<td>
					${companyIntroductionYear.leadUnderwriter1}
				</td>
				<td>
					${companyIntroductionYear.leadUnderwriter2}
				</td>
				<td>
					${companyIntroductionYear.creditRatingAgencies}
				</td>
				<td>
					${companyIntroductionYear.releaseTime}
				</td>
				<td>
					${companyIntroductionYear.charge}
				</td>
				<td>
					${companyIntroductionYear.engageDismissAccountingFirm}
				</td>
				<shiro:hasPermission name="companyinfoyear:companyIntroductionYear:edit"><td>
    				<a href="${ctx}/companyinfoyear/companyIntroductionYear/form?id=${companyIntroductionYear.id}">修改</a>
					<a href="${ctx}/companyinfoyear/companyIntroductionYear/delete?id=${companyIntroductionYear.id}" onclick="return confirmx('确认要删除该公司年报吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>