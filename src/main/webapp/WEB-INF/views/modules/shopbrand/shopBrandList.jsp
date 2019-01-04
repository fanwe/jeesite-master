<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品品牌管理</title>
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
		<li class="active"><a href="${ctx}/shopbrand/shopBrand/">商品品牌列表</a></li>
		<shiro:hasPermission name="shopbrand:shopBrand:edit"><li><a href="${ctx}/shopbrand/shopBrand/form">商品品牌添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopBrand" action="${ctx}/shopbrand/shopBrand/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>品牌图片路径：</label>
				<form:input path="brandUrl" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>名称</th>
				<th>品牌图片路径</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="shopbrand:shopBrand:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopBrand">
			<tr>
				<td><a href="${ctx}/shopbrand/shopBrand/form?id=${shopBrand.id}">
					${shopBrand.name}
				</a></td>
				<td>
					${shopBrand.brandUrl}
				</td>
				<td>
					<fmt:formatDate value="${shopBrand.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopBrand.remarks}
				</td>
				<shiro:hasPermission name="shopbrand:shopBrand:edit"><td>
    				<a href="${ctx}/shopbrand/shopBrand/form?id=${shopBrand.id}">修改</a>
					<a href="${ctx}/shopbrand/shopBrand/delete?id=${shopBrand.id}" onclick="return confirmx('确认要删除该商品品牌吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>