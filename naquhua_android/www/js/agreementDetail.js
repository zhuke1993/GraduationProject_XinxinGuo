var agreementId = getURLParameter('agreementId');

$.ajax({
       
	url: server_host+"/transaction/agreement/"+agreementId,
	data:{
		"accessToken":sessionStorage.getItem("accessToken")
	},
	success: function (msg) {
		if (msg.code == 'OK') {
			var json = JSON.parse(msg.msg);

			$('#app_content').append("<p style=\"font-weight: bold; font-size: 20px;\">借款详情</p>\n" +
                "        <span>借款标题：</span><span>"+json[0].loanTitle+"</span><br><br>\n" +
                "        <span>借款描述：</span><span>"+json[0].description+"</span><br><br>\n" +
                "        <span>金额：</span><span id = \"amount\">"+json[0].amount+"</span><span>元</span><br><br>\n" +
                "        <span>还款期限：</span><span>"+json[0].repaymentLimit+"天</span><br><br>\n" +
                "        <span>年利率：</span><span>"+json[0].rateMonthly+"%</span><br><br>\n" +
                "        <span>已筹金额：</span><span>"+json[0].raisedAmount+"元</span><br><br>\n" +
                "        <span>剩余金额：</span><span>"+json[0].raisedLimitationAmount+"元</span><br><br>\n" +
                "        <br>\n" +
                "        <p>借款人信息</p>\n" +
                "        <span>姓名:</span><span>"+json[0].loanUserName+"</span><br><br>\n" +
                "        <span>学校:</span><span>"+json[0].schoolName+"</span><br><br>\n" +
                "        <span>地址:</span><span>"+json[0].address+"</span><br><br>\n" +
				"        <span>信用等级:</span><span style='color:red;'>"+json[0].creditScoreStr+"</span><br><br>\n" +
                "\n" +
                "        <br>\n" +
                "\n" +/*
                "        <p>信用记录</p>\n" +
                "        <span>借款记录：</span><span>1笔</span><br><br>\n" +
                "        <span>逾期记录：</span><span>0笔</span><br><br>"+*/" <span>投资金额：</span><input type=\"text\" id=\"investAmount\" name=\"investAmount\" style=\"width:30%\"/><br><button style=\"width: 30%;\" onclick=\"invest()\">确认投资</button>"+"<p id=\"errorMessage\" style=\"color:red;\"></p>"+"<br><br><br><br><br><br>");
		} else if (msg.code == '401' || msg.code == '403') {
                        alert(msg.msg);
                        window.location.href = 'login.html';
                }
	}
});