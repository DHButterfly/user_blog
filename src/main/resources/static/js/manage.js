document.write("<script language='text/javascript' src='/js/storage.js'></script>");
var newtoken = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, newtoken);
});
$(function (){

    //$("#user").val=
    $("#logout").click(function () {
        var msg = "您确定要退出吗？\n请确认！";
        if (confirm(msg) == true) {
            logout();
        }
    });
    $("#changePwd").click(function(){//更改密码

    })
});

$(function(){
    var user=document.getElementById("user");
    //var helloUser=document.getElementById("helloUser");
    var storUser=getStorage("user");
    if(storUser!=null){
        $("#img").attr('src',JSON.parse(storUser).avatar);
        user.innerHTML=JSON.parse(storUser).nickname;
        //helloUser.innerHTML=JSON.parse(storUser).nickname;
    }else{
        //跳转登录页面
        window.location.href = "/blog/index.do?token=" + token;
    }
});


function logout() {
    var token = getToken();
    //获取角色token
    $.ajax({
        //要请求的服务器url
        url: "/blog/user/logout.do",
        //表示请求参数的对象,参数:val=uname
        data: {
            token: token,
        },
        //是否为异步请求
        async: false,
        //是否缓存结果
        cache: false,
        //请求方式
        type: "POST",
        //服务器返回的是什么类型
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (res) {
            console.log(res)
            if (res.status == 0) {
                alert("您已安全退出！");
                window.location.href = "/blog/index.do?token=" + token;
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求/blog/user/logout.do接口失败！");
        }
    });
}




function getToken() {
    var query = decodeURI(window.location.search.substring(1));
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == "token") {
            return pair[1];
        }
    }
}
function getStorage(key){
    var storage=window.localStorage;
    return storage.getItem(key);
}