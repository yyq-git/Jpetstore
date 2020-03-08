<%@ include file="../common/IncludeTop.jsp"%>
<!--显示登陆失败的信息-->
<c:if test="${sessionScope.message!=null}">
	${sessionScope.message}
</c:if>

<div id="Catalog">
	<form action="signOn">
		<p>Please enter your username and password.</p>
		<p>Username:<input type="text" name="username" value="j2ee"> <br />
			Password:<input type="password" name="password" value="j2ee"><br />
			VerificationCode:<input type="text" name="vCode" size="5" maxlength="4"/>
			<a href="signOn"><img border="0" src="verification" name="checkcode"></a>
		</p>
		<input type="submit" name="signon" value="Login">
	</form>
		Need a user name and password?
		<a href="signUp">Register Now!</a>
</div>

<%@ include file="../common/IncludeBottom.jsp"%>

