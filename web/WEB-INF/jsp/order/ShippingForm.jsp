<%@ include file="../common/IncludeTop.jsp"%>

<div id="Catalog">
	<form action="confirmShip">
		<table>
			<tr>
				<th colspan=2>Shipping Address</th>
			</tr>
			<!--以下内容均默认为用户注册时填写的信息-->
			<tr>
				<td>First name:</td>
				<td><input type="text" name="order.shipToFirstName" value="${sessionScope.order.shipToFirstName}"/></td>
			</tr>
			<tr>
				<td>Last name:</td>
				<td><input type="text" name="order.shipToLastName" value="${sessionScope.order.shipToLastName}"/></td>
			</tr>
			<tr>
				<td>Address 1:</td>
				<td><input type="text" size="40" name="order.shipAddress1" value="${sessionScope.order.shipAddress1}"/></td>
			</tr>
			<tr>
				<td>Address 2:</td>
				<td><input type="text" size="40" name="order.shipAddress2" value="${sessionScope.order.shipAddress2}"/></td>
			</tr>
			<tr>
				<td>City:</td>
				<td><input type="text" name="order.shipCity" value="${sessionScope.order.shipCity}"/></td>
			</tr>
			<tr>
				<td>State:</td>
				<td><input type="text" size="4" name="order.shipState" value="${sessionScope.order.shipState}"/></td>
			</tr>
			<tr>
				<td>Zip:</td>
				<td><input type="text" size="10" name="order.shipZip" value="${sessionScope.order.shipZip}"/></td>
			</tr>
			<tr>
				<td>Country:</td>
				<td><input type="text" size="15" name="order.shipCountry" value="${sessionScope.order.shipCountry}"/></td>
			</tr>


		</table>

		<input type="submit" name="newOrder" value="Continue" />

	</form>

</div>

<%@ include file="../common/IncludeBottom.jsp"%>