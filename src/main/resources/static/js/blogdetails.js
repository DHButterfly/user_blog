document.write("<script language='text/javascript' src='./manage_index.js' th:src='@{/js/manage_index.js}'></script>");
var newtoken = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, newtoken);
});

function qrCode(id,url){
    var qrcode = new QRCode(id, {
        text: url,
        width: 100,
        height: 100,
        colorDark : "#FFFAFA",
        colorLight : "#A020F0",
        correctLevel : QRCode.CorrectLevel.H
    });
}
$(function(){
    var blogId=getStorage("preBlogId");
    //removeStorage("preBlogId"); //用户刷新详情不用再回去请求了
    getBlogDetails(blogId);
    getBlogComment(blogId);
    qrCode("qrCode","http://localhost:8888/blog/pre_nav.do?id="+blogId);
    $("[name='blog-id']").val(blogId);
    // var test1 = setTimeout(function(){
    //     removeStorage("blogId");
    // },1000*60);//setTimeout 1000ms后执行1次
    // clearTimeout(test1);
    //removeStorage("blogId");
});
function getBlogDetails(id){
    $.ajax({
        url:'/blog/blogs/get_pre_details.do',
        async: false,
        cache: false,
        data:{
            id:id,
        },
        type: "POST",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (res) {
            console.log("请求的博客数据：");
            console.log(res);
            if (res.status == 0) {  //成功
                loadBlogDetails(res.data);//渲染博客详情
            }else{
                alert(res.msg);
            }
        },
        error: function (XMLHttpRequest) {
            alert("请求get_blog_details.do接口失败！" + XMLHttpRequest.readyState);
        }
    });
}

function getBlogComment(id){
    $.ajax({
        url:'/blog/comment/get_blog_comment.do',
        async: false,
        cache: false,
        data:{
            blogId:id,
        },
        type: "POST",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (res) {
            console.log("请求的博客评论数据：");
            console.log(res);
            if (res.status == 0) {  //成功
                loadBlogComment(res.data);//渲染博客xian数据
            }else{
                alert(res.msg);
            }
        },
        error: function (XMLHttpRequest) {
            alert("get_blog_comment.do接口失败！" + XMLHttpRequest.readyState);
        }
    });
}
function loadBlogComment(commentDataList){
    $('#comment-container').empty();
    $('#comment-container').append('<h3 class="ui dividing header" style="color:DeepPink;">评论列表</h3>');
    if(commentDataList!=null&&commentDataList.length!=0){
        for(let i=0;i<commentDataList.length;i++){
            let str= '<div class="ui segment comment">' +
                    '<a class="avatar">' +
                    '<img src="'+commentDataList[i].avatar+'">' +
                    '</a>' +
                    '<div class="content">' +
                    '<a class="author"><span>'+commentDataList[i].nickname+'</span>' ;
            if(commentDataList[i].blogger) str+='<div class="ui mini basic teal left pointing label m-padded-mini">博主</div>';
                    str+='</a>&nbsp;&nbsp;&nbsp;&nbsp;' +
                    '<span class="text">'+commentDataList[i].comment+
                    '</span><br>' +
                    '<div class="metadata" style="display:inline-block">' +
                    '<span class="date">'+commentDataList[i].createTime+'</span>' +
                    '&nbsp;&nbsp;&nbsp;&nbsp;</div>' +
                    '<div class="actions" style="display:inline-block">' +
                    '<a class="reply" data-commentid="'+commentDataList[i].id+'" data-commentnickname="'+commentDataList[i].nickname+'" onclick="reply(this)">回复</a>' +
                    '</div>' +
                    '</div>';
            if(commentDataList[i].blogCommentList.length!=0&&commentDataList[i].blogCommentList!=null){
                for(var j=0;j<commentDataList[i].blogCommentList.length;j++){
                    // var placeholder= $("[name='comment']").attr("placeholder");
                    // if(placeholder.indexOf("@")!=-1) comment=placeholder+comment;
                    str+='<div class="comments">' +
                        '<div class="comment">' +
                        '<a class="avatar">' +
                        '<img src="'+commentDataList[i].blogCommentList[j].avatar+'">' +
                        '</a>' +
                        '<div class="content">' +
                        '<a class="author"><span>'+commentDataList[i].blogCommentList[j].nickname+'</span>';
                        if(commentDataList[i].blogCommentList[j].blogger) str+='<div class="ui mini basic teal left pointing label m-padded-mini">博主</div>';
                        str+='<span class="m-color-teal">@&nbsp;'+commentDataList[i].blogCommentList[j].parentComment.nickname +'</span>';
                    if(commentDataList[i].blogCommentList[j].parentComment.blogger) str+='<div class="ui mini basic teal left pointing label m-padded-mini">博主</div>';
                        str+='</a>&nbsp;&nbsp;&nbsp;&nbsp;' +
                        '<span class="text">'+commentDataList[i].blogCommentList[j].comment+
                        '</span><br>' +
                        '<div class="metadata" style="float:left">' +
                        '<span class="date">'+commentDataList[i].blogCommentList[j].createTime+'</span>' +
                        '&nbsp;&nbsp;&nbsp;&nbsp;</div>' +
                        '<div class="actions" style="float:left">' +
                        '<a class="reply" data-commentid="'+commentDataList[i].blogCommentList[j].id+'" data-commentnickname="'+commentDataList[i].blogCommentList[j].nickname+'" onclick="reply(this)">回复</a>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>'
                }
                str+='<br></div>'
            }else{
                str+='<br>';
            }
            $('#comment-container').append(str);
        }
    }
}
function loadBlogDetails(data){
    $('#title')[0].innerText=data.title;
    $('#first-picture').attr('src',data.firstPicture);
    $('#flag')[0].innerText=data.flag==true?"原创":"转载";
    $('#blogdetail-tags').empty();
    var tagNames=data.tags;
    if(tagNames.length!=0){
        for(let i=0;i<tagNames.length;i++){
            $('#blogdetail-tags').append(
                '<div class="ui basic teal left pointing label">'+tagNames[i]+'</div>'
            )
        }
    }
    var test='<pre class=" language-css"><code class=" language-css"><span class="token property">font-size</span><span class="token number">1.8</span></code></pre>';
    $('#blog-content').html(data.content+test);
    // $('#blog-content')[0].innerHTML=data.content+'<pre class="language-css"><code class="language-css"><span>color:ceshi</span></code></pre>';

    // var hasAttribute=$('pre,code').hasClass("language-css");
    // if(!hasAttribute){
    //     $('pre,code').addClass("language-css");
    // }

    $('#header-content').empty();
    $('#header-content').append(
        '<div class="item">' +
        '<img src="'+data.blogUser.avatar+'" alt="" class="ui avatar image">' +
        '<div class="content">' +
        '<a href="/blog/pre_nav.do?id=5" class="header">'+data.blogUser.nickname+'</a>' +
        '</div>' +
        '</div>' +
        '<div class="item">' +
        '<i class="calendar icon"></i>'+data.updateTime +
        '</div>' +
        '<div class="item">' +
        '<i class="eye icon"></i>'+data.views+
        '</div>'
    );
    if(data.shareStatement){
        $('#user-information>li')[0].innerText="作者："+data.blogUser.nickname;
        $('#user-information>li')[1].innerText="发表时间："+data.updateTime;
    }else{
        $('#shareStatement').css("display","none");
    }

}

$('#comment-btn').click(function () {
    var boo=$('.ui.form').form('validate form');//表单校验
    if(boo){
        saveBlogComment();
    }else{
        console.log("表单校验失败！");
    }
});
function saveBlogComment(){
    var comment=$("[name='comment']").val();
    $.ajax({
        url:'/blog/comment/save_blog_comment.do',
        async: false,
        cache: false,
        data:{
            nickname : $("[name='nickname']").val(),
            email : $("[name='email']").val(),
            comment : comment,
            blogId : $("[name='blog-id']").val(),
            parentCommentId : $("[name='parentCommentId']").val(),
        },
        type: "POST",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (res) {
            if (res.status == 0) {  //成功
                //再次请求评论数据，刷新评论列表,清除当前数据
                getBlogComment( $("[name='blog-id']").val());
                $(window).scrollTo($('#comment-container'),500);
                clearContent();
                //window.location.reload();
            }else{
                alert(res.msg);
            }
        },
        error: function (XMLHttpRequest) {
            alert("请求save_blog_comment.do接口失败！" + XMLHttpRequest.readyState);
        }
    });
}
function clearContent(){
    $("[name='comment']").val('');
    $("[name='parentCommentId']").val(-1);
    $("[name='comment']").attr("placeholder","请输入评论信息...");
}
function reply(obj){
    var commentId=$(obj).data('commentid');//不要用大写，否则获取不到值
    var commentNickname=$(obj).data('commentnickname');
    $("[name='comment']").attr("placeholder","@"+commentNickname).focus();
    $("[name='parentCommentId']").val(commentId);
    $(window).scrollTo($('#comment-form'),500);
}
//表单校验
$('.ui.form').form({
    fields : {
        comment : {
            identifier: 'comment',
            rules: [{
                type : 'empty',
                prompt: '请输入评论信息！'
            }]
        },
        nickname : {
            identifier: 'nickname',
            rules: [{
                type : 'empty',
                prompt: '请输入您的昵称！'
            }]
        },
        email : {
            identifier: 'email',
            rules: [{
                type : 'email',
                prompt: '请给出正确的邮箱地址!'
            }]
        }
    }
});

function getStorage(key){
    var storage=window.localStorage;
    return storage.getItem(key);
}
function removeStorage(key) {
    var storage=window.localStorage;
    storage.removeItem(key);
}
