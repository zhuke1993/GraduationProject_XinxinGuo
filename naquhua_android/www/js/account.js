$.ajax({
	url: server_host+"/transaction/isCardBind",
	data:{
		"accessToken":sessionStorage.getItem("accessToken")
	},
	success: function (msg) {
		if(msg.code == 'FAILED'){
			alert(msg.msg);
			$('#app_content').append("<span>银行卡号：</span><input name=\"bankCardNo\" id=\"bankCardNo\" type = \"text\"/><br>"
										+"<span>姓名：</span><input name=\"name\" id=\"name\" type = \"text\"/><br>"
										+"<span>预留手机号：</span><input name=\"phone\" id=\"phone\" type = \"text\"/><br>"
										+"<button onclick=\"bindCard()\" name=\"submit\" id=\"submit\">绑定</button><br><br>"
										+"<span style =\"color:red;\" id = \"errorMessage\"></span>");
		}else if (msg.code == '401' || msg.code == '403') {
            alert(msg.msg);
            window.location.href = 'login.html';
        }else if(msg.code == 'OK'){
        	$.ajax({
				url: server_host+"/transaction/accountDetail",
				data:{
					"accessToken":sessionStorage.getItem("accessToken")
				},
				success: function (msg) {
					if(msg.code == 'OK'){
						var json = JSON.parse(msg.msg);
						$('#app_content').append("<span>银行卡号：</span>"+json.bankCardNo+"<br>"
													+"<span>账户余额:</span>"+json.amount+"<br><br><hr>"
													+"<button onclick=\"recharge()\">充值</button>&nbsp&nbsp<input style=\"width:45%\" name=\"rechargeAmount\" id=\"rechargeAmount\" type = \"text\"/><br>"
													+"<button onclick=\"withdraw()\">提现</button>&nbsp&nbsp<input style=\"width:45%\" name=\"withdrawAmount\" id=\"withdrawAmount\" type = \"text\"/><br>"
													+"<p style='color:red;' id='error_message'></p>");
					}else if (msg.code == '401' || msg.code == '403') {
			            alert(msg.msg);
			            window.location.href = 'login.html';
			        }
				}
			});
        }
	}
});


function bindCard(){

	var bankCardNo = $("#bankCardNo").val();
	var bankOwenerName=$("#name").val();
	var phone=$("#phone").val();

	if(bankCardNo == null || bankCardNo == ''){
		$("#errorMessage").text("请输入银行卡号");
		return;
	}
	if(bankOwenerName == null || bankOwenerName == ''){
		$("#errorMessage").text("请输入姓名");
		return;
	}
	if(phone == null || phone == ''){
		$("#errorMessage").text("请输入预留手机");
		return;
	}

	$("#errorMessage").text("");
	$.ajax({
		url: server_host+"/transaction/cardBind",
		type:"POST",
		data:{
			"accessToken":sessionStorage.getItem("accessToken"),
			"bankCardNo":$("#bankCardNo").val(),
			"bankOwenerName":$("#name").val()
		},
		success: function (msg) {
			if(msg.code == 'OK'){
				alert("绑定成功");
				window.location.reload();
			}else if (msg.code == '401' || msg.code == '403') {
	            alert(msg.msg);
	            window.location.href = 'login.html';
	        }
		}
	});
}


function recharge(){
	rechargeAmount = $("#rechargeAmount").val();
	if(!isNum(rechargeAmount)){
		$("#error_message").html("充值金额格式错误");
		return;
	}
	
	$.ajax({
		url: server_host+"/transaction/recharge",
		type:"POST",
		data:{
			"accessToken":sessionStorage.getItem("accessToken"),
			"rechargeAmount":$("#rechargeAmount").val()
		},
		success: function (msg) {
			if(msg.code == 'OK'){
				alert("充值成功");
				window.location.reload();
			}else if (msg.code == '401' || msg.code == '403') {
	            alert(msg.msg);
	            window.location.href = 'login.html';
	        }
		}
	});
}


function withdraw(){

	withdrawAmount = $("#withdrawAmount").val();
	if(!isNum(withdrawAmount)){
		$("#error_message").html("提现金额格式错误");
		return;
	}

	$.ajax({
		url: server_host+"/transaction/withdraw",
		type:"POST",
		data:{
			"accessToken":sessionStorage.getItem("accessToken"),
			"withdrawAmount":$("#withdrawAmount").val()
		},
		success: function (msg) {
			if(msg.code == 'OK'){
				alert("提现成功");
				window.location.reload();
			}else if (msg.code == '401' || msg.code == '403') {
	            alert(msg.msg);
	            window.location.href = 'login.html';
	        }
		}
	});
}