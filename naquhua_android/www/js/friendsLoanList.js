var friendId = getURLParameter('userId');

$.ajax({
	url:server_host+"/transaction/friendLoanList/"+friendId,
	type:"GET",
	data:{
		"accessToken":sessionStorage.getItem("accessToken"),
        "userId":friendId
	},
	success:function(msg){
		if (msg.code == '401' || msg.code == '403') {
                alert(msg.msg);
                window.location.href = 'login.html';
        }else {
            var json = JSON.parse(msg.msg);
            if(json.length == 0){
                $('#app_content').append("该用户没有借款记录");
            }
            for (var i = json.length - 1; i >= 0; i--) {
                $("#app_content").append("<span onclick=\"window.location.href=\'agreementDetail.html?agreementId="+json[i].agreementId+"\'\"><span>"+json[i].loanTitle+"</span>"+"<span>......"+json[i].amount+"</span>"+"<span>......"+json[i].date+"</span></span><hr>");
            }

            $("#app_content").append("<div style=\"position: relative; float: left; width: 100%; height: 100px;\"></div>\n");
        }
	}
});