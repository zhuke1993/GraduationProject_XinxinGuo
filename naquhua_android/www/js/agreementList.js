$.ajax({
         
	url: server_host+"/transaction/agreementSummaryList",
	data:{
	       "accessToken":sessionStorage.getItem("accessToken")
	},
	success: function (msg) {
                if (msg.code == '401' || msg.code == '403') {
                        alert(msg.msg);
                        window.location.href = 'login.html';
                }else if (msg.code == 'OK') {
			var json = JSON.parse(msg.msg);
			for (var i = json.length - 1; i >= 0; i--) {
				$('#app_content').append("<div onclick='location.href=\""+"agreementDetail.html?agreementId="+json[i].agreementId+"\"'  style=\"position: relative; height: 105px;\">\n" +
                "            <div style=\"position: relative; float: left; width: 100%; height: 10px;\"></div>\n" +
                "            <div style=\"float:left; margin-left: 10px; width: 60%;\">\n" +
                "                <span style=\"font-size: 20px; font-family: Cabin-Regular; font; font-weight: bold; color: black; left: 5px; float: left;\">"+json[i].loanTitle+"</span>\n" +
                "            </div>\n" +
                "            <div style=\"float: right;margin-right: 10px; width: 30%; background: #1BBC9B;\">\n" +
                "                <span style=\"font-size: 20px; font-family: Cabin-Regular; font; font-weight: bold; color: black; left: 5px; float: right;\">"+json[i].status+"</span>\n" +
                "            </div>\n" +
                "\n" +
                "            <div style=\"float:left; width: 98%; margin-left: 10px; margin-top: 6px;\">\n" +
                "                <span style=\"font-size: 16px; font-family: Cabin-Regular; font; font-weight: bold; color: #878F8D; left: 5px; float: left;\">"+json[i].schoolName+"</span>\n" +
				"                <span style=\"font-size: 16px; font-family: Cabin-Regular; font; font-weight: bold; color: red; left: 5px; float: left;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+json[i].creditScoreStr+"</span>\n" +
                "            </div>\n" +
                "\n" +
                "            <div style=\"float:left; margin-left: 10px; width: 30%; margin-top: 6px;\">\n" +
                "                <span style=\"font-size: 20px; font-family: Cabin-Regular; font; font-weight: bold; color: black; left: 5px; float: left;\">"+json[i].rateMonthly+"%</span>\n" +
                "            </div>\n" +
                "            <div style=\"float: left;margin-left: 10px; width: 30%; margin-top: 6px;;\">\n" +
                "                <span style=\"font-size: 20px; font-family: Cabin-Regular; font; font-weight: bold; color: black; left: 5px; float: left;\">"+json[i].repaymentLimit+"天</span>\n" +
                "            </div>\n" +
                "            <div style=\"float: right;margin-right: 10px; width: 30%; margin-top: 6px;\">\n" +
                "                <span style=\"font-size: 20px; font-family: Cabin-Regular; font; font-weight: bold; color: black; left: 5px; float: left;\">"+json[i].amount+"元</span>\n" +
                "            </div>\n" +
                "\n" +
                "            <div style=\"float:left; margin-left: 10px; width: 30%; margin-top: 6px;\">\n" +
                "                <span style=\"font-size: 16px; font-family: Cabin-Regular; font; font-weight: bold; color: black; left: 5px; float: left;color: #878F8D; \">年利率</span>\n" +
                "            </div>\n" +
                "            <div style=\"float: left;margin-left: 10px; width: 30%; margin-top: 6px;\">\n" +
                "                <span style=\"font-size: 16px; font-family: Cabin-Regular; font; font-weight: bold; color: black; left: 5px; float: left;color: #878F8D; \">期限</span>\n" +
                "            </div>\n" +
                "            <div style=\"float: right;margin-right: 10px; width: 30%;margin-top: 6px;\">\n" +
                "                <span style=\"font-size: 16px; font-family: Cabin-Regular; font; font-weight: bold; color: black; left: 5px; float: left;color: #878F8D; \">金额</span>\n" +
                "            </div>\n" +
                "            <div style=\"position: relative; float: left; width: 100%; height: 2px; width: 100% ;background: gray;\"></div>\n" +
                "        </div>");
			}

			$("#app_content").append("<div style=\"position: relative; float: left; width: 100%; height: 100px;\"></div>\n");

		} 
	}
});
