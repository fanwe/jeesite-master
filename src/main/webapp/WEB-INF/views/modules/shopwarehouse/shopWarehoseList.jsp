<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>仓库管理</title>
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
		<li class="active"><a href="${ctx}/shopwarehouse/shopWarehose/">仓库列表</a></li>
		<shiro:hasPermission name="shopwarehouse:shopWarehose:edit"><li><a href="${ctx}/shopwarehouse/shopWarehose/form">仓库添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopWarehose" action="${ctx}/shopwarehouse/shopWarehose/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>仓库名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>备注信息：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>仓库名称</th>
				<th>仓库所在城市</th>
				<th>仓库地址</th>
				<th>描述</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="shopwarehouse:shopWarehose:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopWarehose">
			<tr>
				<td><a href="${ctx}/shopwarehouse/shopWarehose/form?id=${shopWarehose.id}">
					${shopWarehose.name}
				</a></td>
				<td>
					${shopWarehose.cityname}
				</td>
				<td>
					${shopWarehose.address}
				</td>
				<td>
					${shopWarehose.description}
				</td>
				<td>
					<fmt:formatDate value="${shopWarehose.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopWarehose.remarks}
				</td>
				<shiro:hasPermission name="shopwarehouse:shopWarehose:edit"><td>
    				<a href="${ctx}/shopwarehouse/shopWarehose/form?id=${shopWarehose.id}">修改</a>
					<a href="${ctx}/shopwarehouse/shopWarehose/delete?id=${shopWarehose.id}" onclick="return confirmx('确认要删除该仓库吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>