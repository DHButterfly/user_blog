// document.write("<script src='./security_csrf.js' type='text/javascript'></script>");
var newtoken = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, newtoken);
});
// $(function () {
//     getTableData();
// });
function getTableData(){
    var token=getToken();
    $.ajax({
        url:'/blog/type/get_blogtype.do?token='+token,
        // data:{
        //     token: token,
        // },
        //是否为异步请求
        async: false,
        //是否缓存结果
        cache: false,
        //请求方式
        type: "GET",
        //服务器返回的是什么类型
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (res) {
            console.log("分类表获取数据");
            console.log(res)
            if (res.status == 0) {  //成功
                getData(res)
            }else if(res.status==1){
                alert(res.msg)
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求get_blogtype.do接口失败！" + XMLHttpRequest.readyState);
        }
    });
}
function getData(result) {
    var data=result.data;
    console.log("分类获取数据:"+data);
    var dataList=data.list;
    $("#typetable").empty();//清空
    for(var i=0;i<dataList.length;i++){
        $("#typetable").append(
            "<tr>"+
            "<td>"+dataList[i].id+"</td>"+
            "<td>"+dataList[i].name+"</td>"+
            "<td>"+
            "<a class='ui mini teal button' id='typeEdit' onclick='toEditType(this)'>编辑</a>" +
            "<a class='ui mini red button' id='typeDelete' onclick='deleteType(this)'>删除</a>"+
            "</td>"
        )
    }
}
$(function topNav(){
    $('#blog').click(function () {
        window.location.href="/blog/manage_nav.do?id=1&token="+getToken();
    });
    $('#type').click(function () {
        window.location.href="/blog/manage_nav.do?id=2&token="+getToken();
    });
    $('#tag').click(function () {
       window.location.href="/blog/manage_nav.do?id=3&token="+getToken();
    });
});
function manageDelete(row){
    var choose=confirm("确定删除吗?");
    if(choose==true){
        var token=getToken();
        var id=row.parentNode.parentNode.firstElementChild.innerHTML;
        console.log("id为：",id);
        console.log(getId());
        var url=getId()==2?'/blog/type/delete_blogtype.do':'/blog/tag/delete_blogtag.do';
        $.ajax({
            url: url,
            data:{
                id:id,
                token:token,
            },
            //是否为异步请求
            async: false,
            //是否缓存结果
            cache: false,
            //请求方式
            type: "POST",
            //服务器返回的是什么类型
            dataType: "json",
            //设置了widthCredentials为true的请求中会包含远程域的所有cookie，但这些cookie仍然遵循同源策略，
            // 所以外域是访问不了这些cookie的，现在我们就可以安全地跨域访问
            xhrFields: {
                withCredentials: true
            },
            success:function(res){
                if(res.status==0){
                    console.log("数据删除成功！");
                    window.location.reload();
                }else if(res.status==1){
                    alert(res.msg);
                }
            },
            error: function (XMLHttpRequest) {
                alert("请求"+url+"接口失败！" + XMLHttpRequest.readyState);
            }
        });
    }
}
$(function othersClick(){
    $('#addtype').click(function () {
        window.location.href="/blog/manage_nav.do?id=4&token="+getToken();
        //跳转到新增页面添加数据之后返回类型页面
    });
    $('.commit').click(function () { //提交新增类型表单,因为编辑也是同一个页面，需要使用标识区分是编辑还是新增
        var blogType=getStorage("blogType");
        var id= JSON.parse(blogType).id;
        //var typeName= JSON.parse(blogType).typeName);
        var typeName=$("input[name='typename']").val();
        if(!typeName) alert("JS校验：请输入博客类型名!");//&&typeof(typeName)!="undefined" && typeName !=0
        else{
            if(id!=""){ //编辑
                editType(new BlogType(id,typeName));
            }else{
                commitType(typeName);//新增
            }
        }
    });
});

function BlogType(id,typeName){
    this.id=id;
    this.typeName=typeName;
}

function toEditType(row){ //id和typeName可通过传参方式获取，而不使用页面上的值
    //编辑按钮
    var typeName=row.parentNode.parentNode.childNodes[1].innerHTML;
    //var typeName=$(tbody).find('tr').eq(1).find('td').eq(2);//document.queryselector("#tbody > tr:nth-child(2)")
    //var typename=$("input[name='typename']");
    var id=row.parentNode.parentNode.firstElementChild.innerHTML;
    var blogType=new BlogType(id,typeName);
    setStorage("blogType",JSON.stringify(blogType));
    window.location.href="/blog/manage_nav.do?id=4&token="+getToken();
}
function commitType(typeName){
    var token=getToken();
    $.ajax({
        url:"/blog/type/add_blogtype.do",
        data:{
            name:typeName,
            token:token,
        },
        type: "POST",
        cache:false,
        async:false,
        //服务器返回的是什么类型
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success:function(res){
            if(res.status==0){
                alert("新增成功,若未同步，刷新页面即可！");
                //window.location.reload();
                window.location.href="/blog/manage_nav.do?id=2&token="+getToken();
            }else if(res.status==1){
                alert(res.msg);
            }
        },
        error: function (XMLHttpRequest) {
            alert("请求add_blogtype.do接口失败！" + XMLHttpRequest.readyState);
        }
    })
}
function editType(blogType){
    var token=getToken();
    $.ajax({
        url:"/blog/type/update_blogtype.do",
        data:{
            //blogType:blogType,
            id:blogType.id,
            name:blogType.typeName,
            token:token,
        },
        type: "POST",
        cache:false,
        async:false,
        //服务器返回的是什么类型
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success:function(res){
            if(res.status==0){
                alert("修改成功,若未同步，刷新页面即可！");
                removeStorage("blogType");
                window.location.href="/blog/manage_nav.do?id=2&token="+getToken();
            }else if(res.status==1){
                alert(res.msg);
            }
        },
        error: function (XMLHttpRequest) {
            alert("请求edit_blogtype.do接口失败！" + XMLHttpRequest.readyState);
        }
    })
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


function setStorage(key,value){
    var storage=window.localStorage;
    storage.setItem(key,value);
}
function getStorage(key){
    var storage=window.localStorage;
    return storage.getItem(key);
}

function  removeStorage(key) {
    var storage=window.localStorage;
    storage.removeItem(key);
}

function sessionStore(key,value) {
    var storage=window.sessionStorage;
    storage.setItem(key,value);
}