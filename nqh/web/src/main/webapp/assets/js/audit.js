/**
 * Created by ZHUKE on 2016/4/10.
 */
$.ajax({
    url: "security/profileList",
    data: {
        accessToken: sessionStorage.getItem("accessToken")
    },
    success: function (msg) {
        if (msg.code == '401' || msg.code == '403') {
            alert(msg.msg);
            window.location.href = 'login.html';
        } else if (msg.code == 'OK') {
            var json = JSON.parse(msg.msg);

           /* var totalNum = json.length;
            $("#total_item").text(totalNum);
            var pageSize = 10;
            var N = 0;
            if (totalNum % pageSize == 0) {
                N = parseInt(totalNum / pageSize);
            } else {
                N = parseInt(totalNum / pageSize) + 1;
            }
            //alert(parseInt(N));
            $("#pageContent").empty();
            for (var i = 0; i < N; i++) {
                $("#pageContent").append("<li onclick='getPage(" + parseInt(i + 1) + ",10)' class=\"#\"><a href=\"#\">" + parseInt(i + 1) + "</a></li>");
            }*/

            if (json.length == 0) {
                $("#content").append("无");
            } else {
                for (var i = 0; i < json.length; i++) {
                    $("#content").append("<tr>\n" +
                        "                            <td  style=\"width: 12%\">" + json[i].userName + "</td>\n" +
                        "                            <td  style=\"width: 6%\">" + json[i].realName + "</td>\n" +
                        "                            <td  style=\"width: 17%\">" + json[i].idCardNo + "</td>\n" +
                        "                            <td  style=\"width: 12%\">" + json[i].schoolName + "</td>\n" +
                        "                            <td style=\"width: 17%\">" + json[i].address + "</td>\n" +
                        "                            <td style=\"width: 15%\"><a href='" + server_host + "/fileupload" + json[i].schoolCertification + "'>" + json[i].schoolCertification + "</a>" + "</td>\n" +
                        "                            <td style=\"width: 15%\"><a href='" + server_host + "/fileupload" + json[i].idCertification + "'>" + json[i].idCertification + "</a>" + "</td>\n" +
                        "                            <td style=\"width: 6%\"><button onclick='audit(\"" + json[i].userName + "\")'>通过审核</button></td>\n" +
                        "                        </tr>");
                }
            }
        }
    }
});


function audit(userName) {
    $.ajax({
        url: "security/audit_user",
        data: {
            accessToken: sessionStorage.getItem("accessToken"),
            userName: userName
        },
        success: function (msg) {
            if (msg.code == '401' || msg.code == '403') {
                alert(msg.msg);
                window.location.href = 'login.html';
            } else if (msg.code == 'OK') {
                alert(msg.msg);
            }
        }
    });
}