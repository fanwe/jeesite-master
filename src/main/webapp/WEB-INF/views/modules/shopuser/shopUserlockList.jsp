<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
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
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/shopuser/shopUser/">会员列表</a></li> --%>
<%-- 		<shiro:hasPermission name="shopuser:shopUser:edit"><li><a href="${ctx}/shopuser/shopUser/form">会员添加</a></li></shiro:hasPermission> --%>
<!-- 	</ul> -->
	<form:form id="searchForm" modelAttribute="shopUser" action="${ctx}/shopuser/shopUser/unlocklist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<li><label>会员名称：</label>
				<form:input path="username" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>会员名称</th>
				<th>会员昵称</th>
				<th>邮箱</th>
				<th>电话</th>
				<th>最后一次登陆时间</th>
				<th>最后一次登录ip</th>
				<th>注册时间</th>
				<th>会员等级</th>
				<shiro:hasPermission name="shopuser:shopUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopUser">
			<tr>
				<td><a href="${ctx}/shopuser/shopUser/form?id=${shopUser.id}">
					${shopUser.username}
				</a></td>
				<td>
					${shopUser.usernickname}
				</td>
				<td>
					${shopUser.email}
				</td>
				<td>
					${shopUser.phone}
				</td>
				<td>
					<fmt:formatDate value="${shopUser.lastlogintime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopUser.lastloginip}
				</td>
				<td>
					<fmt:formatDate value="${shopUser.regeistdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopUser.userlevelname}
				</td>
				<shiro:hasPermission name="shopuser:shopUser:edit"><td>
					<a href="${ctx}/shopuser/shopUser/unlock?id=${shopUser.id}" onclick="return confirmx('确认要解冻该会员吗？', this.href)">解冻</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>