<%@ include file="../common/IncludeTop.jsp"%>

<div id="Catalog">

	<form action="addAccount" name="userInfo">
		<h3>User Information</h3>
		<table>
			<tr>
				<td>User ID:</td>
				<td>
					<input type="text" name="username" onblur="usernameIsExist();" required="required">
					<span id="usernameMsg"></span>
				</td>
			</tr>
			<tr>
				<td>New password:</td>
				<td>
					<input type="password" name="password" required="required">
				</td>
			</tr>
			<tr>
				<td>Repeat password:</td>
				<td><input type="password" name="repeatedPassword" onblur="pwdcheck()" required="required">
                    <span id="pwdCheck"></span>
                </td>
			</tr>
			<tr>
				<td>VerificationCode:</td>
				<td>
					<input type="text" name="vCode" size="5" maxlength="4"/>
					<a href="addAccount"><img border="0" src="verification" name="checkcode"></a>
				</td>
			</tr>
		</table>

		<%@ include file="IncludeAccountFields.jsp"%>

		<input type="submit" value="Save Account Information" name="submit" aria-disabled="">
	</form>
</div>

<%@ include file="../common/IncludeBottom.jsp"%>
<script>
	var xmlHttpRequest;
	var account;
	function createXMLHttpRequest() {
		if(window.XMLHttpRequest)
			xmlHttpRequest = new XMLHttpRequest();
		else if(window.ActiveXObject)
			xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
		else
			xmlHttpRequest = new ActiveObject("Microsoft.XMLHTTP");
	}
	function usernameIsExist() {
		var username = document.userInfo.username.value;
		sendRequest("usernameIsExist?username=" + username);
	}
	function sendRequest(url) {
        createXMLHttpRequest();
        xmlHttpRequest.open("GET", url, true);
        xmlHttpRequest.onreadystatechange = processResponse;
        xmlHttpRequest.send(null);
	}
	function processResponse() {
		if (xmlHttpRequest.readyState == 4) {
			if (xmlHttpRequest.status == 200) {
				var responseInfo = xmlHttpRequest.responseXML.
                getElementsByTagName("msg")[0].firstChild.data;

				var div1 = document.getElementById("usernameMsg");
				if (responseInfo == "Exist") {
					div1.innerHTML = "<font color='red'>Username has already exist.</font>";
                    account = false;
                    document.getElementById("submit").disabled = true;
				} else if(responseInfo == "NotExist"){
				    account = true;
					div1.innerHTML = "<font color='green'>Username is available</font>";
				} else if(responseInfo == "Empty"){
                    account = false;
                    div1.innerHTML = "<font color='red'>Username should not be empty!</font>";
                    document.getElementById("submit").disabled = true;
                }
			}
		}
	}
	function pwdcheck() {
	    var pwd1 = document.userInfo.password.value;
        var pwd2 = document.userInfo.repeatedPassword.value;
        if(pwd1 == "" || pwd2 == ""){
            pwdCheck.innerHTML="<font color='red'>The password should not be empty!</font>"
            document.getElementById("submit").disabled = true;
        }else if(pwd1 != pwd2){
            pwdCheck.innerHTML="<font color='red'>The two passwords you typed do not match!</font>"
            document.getElementById("submit").disabled = true;
        }else{
            pwdCheck.innerHTML="<font color='green'>The two password matching.</font>";
            if(account){
                document.getElementById("submit").disabled = false;
            }else{
                document.getElementById("submit").disabled = true;
            }

        }
    }
</script>
