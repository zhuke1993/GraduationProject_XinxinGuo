function login() {

	var clientId = $("#username").val();
	var clientSecret = $("#password").val();
	var postData = {
		username: clientId,
		password: clientSecret
	};
	var requestUrl = server_host + "/security/login"; //要访问的api

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

	$.ajax({ 
	url: server_host+"/security/login",
	data:{
	      	username: clientId,
			password: clientSecret
	},
	success: function (msg) {
		if (msg.code == 'OK') {
				var accessToken = msg.msg;
				window.sessionStorage.setItem('isLogin', true);
				sessionStorage.setItem("accessToken", accessToken);
				window.location.href = 'index.html';
			} else{
				errorMessage = msg.msg;
				$("#error_message").html(errorMessage);
			}
	}
});

}