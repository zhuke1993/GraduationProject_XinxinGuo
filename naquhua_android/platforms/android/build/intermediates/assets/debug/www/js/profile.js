function profile(){
	var accessToken = sessionStorage.getItem("accessToken");

	if($("#realName").val() == null || $("#realName").val() == ''){
		$("#isAvailable").text("请输入真实姓名");
	}
	if($("#idCardNo").val() == null || $("#idCardNo").val() == ''){
		$("#isAvailable").text("请输入身份证编号");
	}
	if($("#schoolName").val() == null || $("#schoolName").val() == ''){
		$("#isAvailable").text("请输入学校名称");
	}
	if($("#address").val() == null || $("#address").val() == ''){
		$("#isAvailable").text("请输入住址");
	}


	$("#profileForm").attr('action',server_host+"/security/profile"
		+"?schoolName="+$("#schoolName").val()
		+"&address="+$("#address").val()
		+"realName="+$("#realName").val()
		+"idCardNo="+$("#idCardNo").val()
		+"&accessToken="+accessToken);
}


$.ajax({
	url:server_host+"/security/profile",
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
            if(json.isAvailable == '1'){
            	$("#isAvailable").text("资质审核通过");
        	}else{
            	$("#isAvailable").text("资质待审核");
        	}
        	$("#schoolName").val(json.schoolName);
			$("#address").val(json.address);
			$("#realName").val(json.realName);
			$("#idCardNo").val(json.idCardNo);
        }
	}
});