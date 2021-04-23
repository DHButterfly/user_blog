document.write("<script language='text/javascript' src='./storage.js'></script>");
// document.write("<script language='javascript' src='./storage.js'></script>");
var newtoken = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, newtoken);
});
// XMLHttpRequest对象和设置作为参数传递给回调函数
// $(document).ajaxSend(function(evt,request,settings){})是全局事件,也就是说,
//只要该页面定义了这个函数,那么,在每个ajax请求前都会执行该函数
$(document).ready(function () {
    change_verificode();
});
function check_input() {
    // var username=$("#username").value;
    // var password=$("#password").value;
    var username=document.getElementById('username').value;
    var password=document.getElementById('password').value;
    var valificode = $("#valificode").val();
    var code = $("#code").val();
    if (username == "" || username == null) {
        alert("请输入用户名!")
        return false;
    } else if (password == "" || password == null) {
        alert("请输入密码!")
        return false;
    }  else if (password.length<2||password.length>15) {
        alert("密码为6到15位!")
        return false;
    }  else if (valificode == "" || valificode == null) {
        alert("请输入验证码!")
        return false;
    } else {
        //校验验证码
        if (code.toUpperCase() == valificode.toUpperCase()) {
            //验证码输入正确
            //调用登录方法
            login(username, password);
            return true;
        } else {
            //验证码输入错误
            alert("验证码输入错误!")
            $("#valificode").val("");
            change_verificode();

        }
    }
}
function login(username, password) {
    // var Authorization=username;
    var token=username;
    //每次 Ajax 提交前，插入 Token：
    // setStorage(token,username);
    // var test=getStorage(token);
    // console.log(test);
    // $(document).ajaxSend(function (event, xhr) {
    //     // xhr.setRequestHeader("Content-Type", "application/json;charset=utf-8") ;
    //     xhr.setRequestHeader("Authorization", Authorization) ;
    // });
    $.ajax({
        //要请求的服务器url
        url: "/blog/user/login.do",
        //表示请求参数的对象,参数:val=uname
        data: {
            username: username,
            password: password,
        },
        //是否为异步请求
        async: false,
        //是否缓存结果
        cache: false,
        //请求方式
        type: "POST",
        //服务器返回的是什么类型
        dataType: "json",
        xhrFields:{
            withCredentials:true
        },
        // headers: {
        //     Authorization: Authorization,
        // },
        // beforeSend: function (XMLHttpRequest) {
        //     XMLHttpRequest.setRequestHeader("Authorization", token);
        // },
        // contentType: "application/json",
        //函数会在服务器执行成功时被调用,参数result就是服务器返回的值
        success: function (res) {
            console.log(res)
            if(res.status==0){
                // alert("成功登录！");成功登录，返回首页信息
                setStorage("user",JSON.stringify(res.data));
                window.location.href="/blog/index.do?token="+token;
            }else if(res.status==1){
                // alert("登录失败！");
                alert(res.msg);
            }else if(res.status==2){
                //刷新当前页面
                alert("该用户无登录权限！");
                window.location.reload();
            }else{
                alert("返回状态码出错！");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求/user/login.do接口失败1");
            change_verificode();
        }
    });
}

function setStorage(key,value){
    var storage=window.localStorage;
    storage.setItem(key,value);
}
$(function () {
    $("#login").click(function () {
        check_input();
        // check_login();
        // return false;
    });
    // 响应回车按钮
    $("#login").keyup(function(event) {
        if(13 == event.keyCode) {
            check_input();
        }
    });
    // $('.message a').click(function () {
    //     $('form').animate({
    //         height: 'toggle',
    //         opacity: 'toggle'
    //     }, 'slow');
    // });
    $("#code").click(function () {
        change_verificode();
    });
})
function change_verificode() {
    code = $("#code");
    // alert(code);
    // 验证码组成库
    var arrays = new Array(
        '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z'
    );
    codes = '';// 重新初始化验证码
    for (var i = 0; i < 4; i++) {
        // 随机获取一个数组的下标
        var r = parseInt(Math.random() * arrays.length);
        codes += arrays[r];
    }
    // 验证码添加到input里
    code.val(codes);
}

// 这里使用最原始的js语法实现，可对应换成jquery语法进行逻辑控制
// var demoImg = document.getElementById("demo_img");
// var demoInput = document.getElementById("demo_input");
// //隐藏text block，显示password block
// function hideShowPsw(){
//     if (demoInput.type == "password") {
//         demoInput.type = "text";
//         demo_img.src = "invisible.png";
//     }else {
//         demoInput.type = "password";
//         demo_img.src = "visible.png";
//     }
// }