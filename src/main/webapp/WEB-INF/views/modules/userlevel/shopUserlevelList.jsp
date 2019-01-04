<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员等级管理</title>
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
		<li class="active"><a href="${ctx}/userlevel/shopUserlevel/">会员等级列表</a></li>
		<shiro:hasPermission name="userlevel:shopUserlevel:edit"><li><a href="${ctx}/userlevel/shopUserlevel/form">会员等级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopUserlevel" action="${ctx}/userlevel/shopUserlevel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>会员编码</th>
				<th>会员名称</th>
				<th>积分范围</th>
				<th>描述</th>
				<th>备注信息</th>
				<shiro:hasPermission name="userlevel:shopUserlevel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopUserlevel">
			<tr>
				<td><a href="${ctx}/userlevel/shopUserlevel/form?id=${shopUserlevel.id}">
					${shopUserlevel.code}
				</a></td>
				<td>
					${shopUserlevel.name}
				</td>
				<td>
					${shopUserlevel.minscore}~${shopUserlevel.maxscore}
				</td>
				
				<td>
					${shopUserlevel.description}
				</td>
				<td>
					${shopUserlevel.remarks}
				</td>
				<shiro:hasPermission name="userlevel:shopUserlevel:edit"><td>
    				<a href="${ctx}/userlevel/shopUserlevel/form?id=${shopUserlevel.id}">修改</a>
					<a href="${ctx}/userlevel/shopUserlevel/delete?id=${shopUserlevel.id}" onclick="return confirmx('确认要删除该会员等级吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>