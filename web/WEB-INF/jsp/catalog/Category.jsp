<%@ include file="../common/IncludeTop.jsp"%>

<div id="BackLink">
	<a href="main">Return to Main Menu</a><!--点击返回主界面-->
</div>

<div id="Catalog">
<!--点击主界面的大类保存的category对象,servlet需要进行查询该类-->
<h2>${sessionScope.category.name}</h2>

<table>
	<tr>
		<th>Product ID</th>
		<th>Name</th>
	</tr>
	<!--servlet在这里需要查询该类的列表-->
	<c:forEach var="product" items="${sessionScope.productList}">
		<tr>
			<td>
				<a href="viewProduct?productId=${product.productId}">${product.productId}</a>
			</td>
			<td>${product.name}</td>
		</tr>
	</c:forEach>
</table>

</div>

<%@ include file="../common/IncludeBottom.jsp"%>
