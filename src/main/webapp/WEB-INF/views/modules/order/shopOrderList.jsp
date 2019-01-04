<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
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
		<li class="active"><a href="${ctx}/order/shopOrder/">订单列表</a></li>
		<shiro:hasPermission name="order:shopOrder:edit"><li><a href="${ctx}/order/shopOrder/form">订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopOrder" action="${ctx}/order/shopOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input path="orderno" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>会员昵称：</label>
				<form:input path="userid" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>会员昵称</th>
				<th>支付方式</th>
				<th>支付状态</th>
				<th>订单总额</th>
				<th>订单运费</th>
				<th>商品总数量</th>
				<th>送货地址</th>
				<th>送货详细地址</th>
				<th>快递运单号</th>
				<th>快递公司</th>
				<th>备注信息</th>
				<shiro:hasPermission name="order:shopOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopOrder">
			<tr>
				<td><a href="${ctx}/order/shopOrder/form?id=${shopOrder.id}">
					${shopOrder.orderno}
				</a></td>
				<td>
					${shopOrder.username}
				</td>
				<td>
					${fns:getDictLabel(shopOrder.paytype, 'payType', '')}
				</td>
				<td>
					${fns:getDictLabel(shopOrder.orderstatus, 'orderStatus', '')}
				</td>
				<td>
					${shopOrder.amcount}
				</td>
				<td>
					${shopOrder.fee}
				</td>
				<td>
					${shopOrder.quantity}
				</td>
				<td>
					${shopOrder.cityName}
				</td>
				<td>
					${shopOrder.address}
				</td>
				<td>
					${shopOrder.expressno}
				</td>
				<td>
					${fns:getDictLabel(shopOrder.expresscompanyname, 'express', '')}
				</td>
				<td>
					${shopOrder.remarks}
				</td>
				<shiro:hasPermission name="order:shopOrder:edit"><td>
    				<a href="${ctx}/order/shopOrder/form?id=${shopOrder.id}">修改</a>
					<a href="${ctx}/order/shopOrder/delete?id=${shopOrder.id}" onclick="return confirmx('确认要删除该订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>