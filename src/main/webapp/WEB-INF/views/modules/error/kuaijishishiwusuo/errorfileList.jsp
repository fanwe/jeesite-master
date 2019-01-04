<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会计师事务所管理</title>
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
		<li class="active"><a href="${ctx}/error/kuaijishishiwusuo/errorfile/">会计师事务所列表</a></li>
		<shiro:hasPermission name="error:kuaijishishiwusuo:errorfile:edit"><li><a href="${ctx}/error/kuaijishishiwusuo/errorfile/form">会计师事务所添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="errorfile" action="${ctx}/error/kuaijishishiwusuo/errorfile/" method="post" class="breadcrumb form-search">
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
				<th>文件名称</th>
				<shiro:hasPermission name="error:kuaijishishiwusuo:errorfile:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="errorfile">
			<tr>
				<td><a href="${ctx}/error/kuaijishishiwusuo/errorfile/form?id=${errorfile.id}">
					<fmt:formatDate value="${errorfile.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${errorfile.fileName}
				</td>
				<shiro:hasPermission name="error:kuaijishishiwusuo:errorfile:edit"><td>
    				<a href="${ctx}/error/kuaijishishiwusuo/errorfile/form?id=${errorfile.id}">修改</a>
					<a href="${ctx}/error/kuaijishishiwusuo/errorfile/delete?id=${errorfile.id}" onclick="return confirmx('确认要删除该会计师事务所吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>