<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
		<li class="active"><a href="${ctx}/procduct/shopProduct/">商品列表</a></li>
		<shiro:hasPermission name="procduct:shopProduct:edit"><li><a href="${ctx}/procduct/shopProduct/form">商品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopProduct" action="${ctx}/procduct/shopProduct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
<!-- 			<li><label>商品副标题：</label> -->
<%-- 				<form:input path="subtitle" htmlEscape="false" maxlength="255" class="input-medium"/> --%>
<!-- 			</li> -->
<!-- 			<li><label>商品类型：</label> -->
<%-- 				<form:input path="category" htmlEscape="false" maxlength="255" class="input-medium"/> --%>
<!-- 			</li> -->
<!-- 			<li><label>商品图片：</label> -->
<%-- 				<form:input path="mainImg" htmlEscape="false" maxlength="255" class="input-medium"/> --%>
<!-- 			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>商品副标题</th>
				<th>商品类型</th>
<!-- 				<th>商品图片</th> -->
<!-- 				<th>商品详情</th> -->
				<th>商品价格</th>
				<th>商品库存</th>
				<th>商品状态</th>
				<th>商品品牌</th>
				<th>所在仓库</th>
				<th>备注信息</th>
				<shiro:hasPermission name="procduct:shopProduct:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopProduct">
			<tr>
				<td><a href="${ctx}/procduct/shopProduct/form?id=${shopProduct.id}">
					${shopProduct.name}
				</a></td>
				<td>
					${shopProduct.subtitle}
				</td>
				<td>
					${shopProduct.categoryname}
				</td>
<!-- 				<td> -->
<%-- 					${shopProduct.mainImg} --%>
<!-- 				</td> -->
<!-- 				<td> -->
<%-- 					${shopProduct.detail} --%>
<!-- 				</td> -->
				<td>
					${shopProduct.price}
				</td>
				<td>
					${shopProduct.stock}
				</td>
				<td>
					${fns:getDictLabel(shopProduct.status, 'product_status', '')}
				</td>
				<td>
					${shopProduct.brandName}
				</td>
				<td>
					${shopProduct.warehouseName}
				</td>
				<td>
					${shopProduct.remarks}
				</td>
				<shiro:hasPermission name="procduct:shopProduct:edit"><td>
    				<a href="${ctx}/procduct/shopProduct/form?id=${shopProduct.id}">修改</a>
					<a href="${ctx}/procduct/shopProduct/delete?id=${shopProduct.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>