$.ajax({
	url:server_host+"/transaction/investList",
	type:"GET",
	data:{
		"accessToken":sessionStorage.getItem("accessToken")
	},
	success:function(msg){
		if (msg.code == '401' || msg.code == '403') {
                alert(msg.msg);
                window.location.href = 'login.html';
        }else {
        	//alert(msg.msg);
            var json = JSON.parse(msg.msg);
            for (var i = 0; i <= json.length; i++) {
                $("#app_content").append("<span onclick=\"window.location.href=\'agreementDetail.html?agreementId="+json[i].agreementId+"\'\"><span>"+json[i].loanTitle+"</span>"+"<span>......"+json[i].amount+"</span>"+"<span>......"+json[i].date+"</span></span><hr>");
            }
            $("#app_content").append("<br><br><br><br>");
        }
	}
});