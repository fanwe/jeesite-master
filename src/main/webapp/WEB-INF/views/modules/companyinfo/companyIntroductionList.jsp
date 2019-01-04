<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公司情况管理</title>
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
		<li class="active"><a href="${ctx}/companyinfo/companyIntroduction/">公司情况列表</a></li>
		<shiro:hasPermission name="companyinfo:companyIntroduction:edit"><li><a href="${ctx}/companyinfo/companyIntroduction/insert">公司情况添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="companyIntroduction" action="${ctx}/companyinfo/companyIntroduction/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>文件标题</th>
				<th>公司法定中文名称</th>
				<th>公司法定英文名称</th>
				<th>公司聘用的会计师事务所</th>
				<th>公司会计负责人</th>
				<th>公司会计机构负责人</th>
				<th>已发行债券情况</th>
				<shiro:hasPermission name="companyinfo:companyIntroduction:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="companyIntroduction">
			<tr>
				<td><a href="${ctx}/companyinfo/companyIntroduction/form?id=${companyIntroduction.id}">
					<fmt:formatDate value="${companyIntroduction.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${companyIntroduction.fileTitle}
				</td>
				<td>
					${companyIntroduction.companyLegalChineseName}
				</td>
				<td>
					${companyIntroduction.companyLegalEnglishName}
				</td>
				<td>
					${companyIntroduction.accountingFirm}
				</td>
				<td>
					${companyIntroduction.accountingDirector}
				</td>
				<td>
					${companyIntroduction.organizationAccountingDirector}
				</td>
				<td>
					${companyIntroduction.situationIssuedBonds}
				</td>
				<shiro:hasPermission name="companyinfo:companyIntroduction:edit"><td>
    				<a href="${ctx}/companyinfo/companyIntroduction/form?id=${companyIntroduction.id}">修改</a>
					<a href="${ctx}/companyinfo/companyIntroduction/delete?id=${companyIntroduction.id}" onclick="return confirmx('确认要删除该公司情况吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>