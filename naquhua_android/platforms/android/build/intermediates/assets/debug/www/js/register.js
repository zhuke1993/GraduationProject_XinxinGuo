function register(){
	var clientId = $("#email").val();
	var clientSecret = $("#password").val();
	var phone = $('#phone').val();
	var code = $('#code').val();
	var repassword = $('#repassword').val();

	var postData = {
		username: clientId,
		password: clientSecret
	};
	var requestUrl = server_host + "/security/register"; //要访问的api

	var errorMessage = "";
	var alias = '';
	$("#error_message").html(errorMessage);
	if (clientId == "" && clientSecret == "") {
		errorMessage = "请输入账号和密码";
		$("#error_message").html(errorMessage);
		return;
	}
	if (clientSecret == null || clientSecret == "") {

		errorMessage = "请输入密码";
		 
		$("#error_message").html(errorMessage);
		return;
	}

	if (phone == "") {
		errorMessage = "请输入电话号码";
		$("#error_message").html(errorMessage);
		return;
	}
	if (code == null || code == "") {

		errorMessage = "请输入验证码";
		 
		$("#error_message").html(errorMessage);
		return;
	}

	if (clientId == null || clientId == "") {
		errorMessage = "请输入账号";
		 
		$("#error_message").html(errorMessage);
		return;
	}


	//var patt2 = new RegExp("^[1][3,4,5,7,8][0-9]{9}$");
	var patt2 = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
	if (!patt2.test(clientId)) {
		errorMessage = "邮箱格式不正确";
		 
		$("#error_message").html(errorMessage);
		return;
	} else {
		localStorage.setItem("clientId", clientId);
	}

	//var patt1 = new RegExp("^[0-9a-zA-Z_`~\\!@#\\$\\%\\^&\\*()\\-\\+=]{6,20}$");
	var patt1 = /^[0-9a-zA-Z_`~\\!@#\\$\\%\\^&\\*()\\-\\+=]{5,20}$/;
	if (!patt1.test(clientSecret)) {
		errorMessage = "密码格式不正确";
		 
		$("#error_message").html(errorMessage);
		return;
	}


	if(clientSecret != repassword){
		errorMessage = "两次输入的密码不一致";
		 
		$("#error_message").html(errorMessage);
		return;
	}

	$.ajax({
		url: requestUrl,
		global: false,
		data:{
			email: clientId,
			password: clientSecret,
			code: code,
			phone: phone
		},
		success: function (msg) { //msg为返回的数据，在这里做数据绑定 
			if (msg.code == 'OK') {
				alert(msg.msg);
				window.location.href = 'login.html';
			} else{
				errorMessage = msg.msg;
				$("#error_message").html(errorMessage);
			}
		}
	});
}


function getValidateCode(){
	var email = $("#email").val();

	if (email == null || email == "") {
		errorMessage = "请输入邮箱";
		$("#error_message").html(errorMessage);
		return;
	}

	var patt2 = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
	if (!patt2.test(email)) {
		errorMessage = "邮箱格式不正确";
		$("#error_message").html(errorMessage);
		return;
	} 

	$.ajax({
		url: server_host+"/security/validation/code",
		global: false,
		contentType: "application/json; charset=utf-8",
		data:{
			email: email
		},
		success: function (msg) { 
			errorMessage = msg.msg;
			$("#error_message").html(errorMessage);
		}
	});

}