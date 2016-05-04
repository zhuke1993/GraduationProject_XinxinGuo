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