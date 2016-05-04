function invest(){
	var investAmount = $('#investAmount').val();
	var patt1 = /^[0-9]{1,20}$/;
	if (!patt1.test(investAmount)) {
		errorMessage = "金额非法";
		$("#errorMessage").html(errorMessage);
		return;
	}

	var limitAmount = $("#amount").text();

	if (parseInt(investAmount) > parseInt(limitAmount)) {
		errorMessage = "投资金额大于借款金额";
		$("#errorMessage").html(errorMessage);
		return;
	}

	$("#errorMessage").html("");

	var agreementId = getURLParameter('agreementId');

	$.ajax({
		url:server_host+"/transaction/agreement/invest",
		data:{
			"accessToken":sessionStorage.getItem("accessToken"),
			amount:investAmount,
			agreementId:agreementId
		},
		success: function(msg){
			if (msg.code == 'OK') {
				alert(msg.msg);
				window.location.href = 'index.html';
			} else if(msg.code == '401' || msg.code == '403'){
				alert(msg.msg);
				window.location.href = 'login.html';
			}else{
				alert(msg.msg);
			}
		}
	});


}