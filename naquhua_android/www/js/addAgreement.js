$.ajax({

		url: server_host+"/transaction/isAvailable",
		data:{
			"accessToken":sessionStorage.getItem("accessToken")
		},
		success: function (msg) {
			if (msg.code == 401 || msg.code == 403) {
                alert(msg.msg);
                window.location.href = 'login.html';
            }else if(msg.code == 8888){
            	alert(msg.msg);
                window.location.href = 'profile.html';
            }
		}
	});

function addAgreement(){
	loadFor = $("#loadFor").val();
	loanTitle= encodeURI($("#loanTitle").val());
	amount=$("#amount").val();
	rateMonthly=$("#rateMonthly").val();
	description=encodeURI($("#description").val());
	repaymentLimit=$("#repaymentLimit").val();

	if(loadFor == null || loadFor == ""){
		$("#error_message").html("请输入借款原因");
		return;
	}

	if(loanTitle == null || loanTitle == ""){
		$("#error_message").html("请输入借款标题");
		return;
	}
	if(amount == null || amount == ""){
		$("#error_message").html("请输入借款金额");
		return;
	}
	if(rateMonthly == null || rateMonthly == ""){
		$("#error_message").html("请输入月利率");
		return;
	}
	if(description == null || description == ""){
		$("#error_message").html("请输入详细描述");
		return;
	}
	if(repaymentLimit == null || repaymentLimit == ""){
		$("#error_message").html("请输入还款期限");
		return;
	}

	if(!isNum(amount)){
		$("#error_message").html("借款金额格式错误");
		return;
	}
	if(!isNum(rateMonthly)){
		$("#error_message").html("月利率格式错误");
		return;
	}
	if(!isNum(repaymentLimit)){
		$("#error_message").html("还款期限格式错误");
		return;
	}


	$("#error_message").html("");
	$.ajax({

		url: server_host+"/transaction/agreement/add",
		data:{
			"accessToken":sessionStorage.getItem("accessToken"),
			loanFor: encodeURI($("#loadFor").val()),
			loanTitle: encodeURI($("#loanTitle").val()),
			amount: $("#amount").val(),
			rateMonthly: $("#rateMonthly").val(),
			description: encodeURI($("#description").val()),
			repaymentLimit:$("#repaymentLimit").val()
		},
		success: function (msg) {
			alert(msg.msg);
			if(msg.code = 'OK'){
				window.location.href = 'index.html';
			}else if (msg.code == '401' || msg.code == '403') {
                        alert(msg.msg);
                        window.location.href = 'login.html';
            }
		}
	});
}

