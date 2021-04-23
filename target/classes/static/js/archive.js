var newtoken = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, newtoken);
});

$(function(){
    getAllArchiveBlogs();
});
function getAllArchiveBlogs() {
    $.ajax({
        url: '/blog/blogs/get_blog_archive.do',
        async: false,
        cache: false,
        type: "POST",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (res) {
            console.log("请求的博客归档数据：");
            console.log(res);
            if (res.status == 0) {  //成功
                loadBlogArchive(res.data);//渲染博客xian数据
            } else {
                alert(res.msg);
            }
        },
        error: function (XMLHttpRequest) {
            alert("get_blog_archive.do接口失败！" + XMLHttpRequest.readyState);
        }
    });
}
function loadBlogArchive(blogList){
    $('#archive-content').empty();
    let totalBlog=0;
    for(let i=0;i<blogList.length;i++){ //每一年
        var str='<h2 class="ui center teal aligned header">'+splitDate(blogList[i][0].updateTime,1)+'</h2>'+
            '<div class="ui fluid vertical menu">';
        totalBlog+=blogList[i].length;
        for(let j=0;j<blogList[i].length;j++){
            var flag=blogList[i][j].flag==true?"原创":"转载";
               str+= '<a onclick="toBlogDetails('+blogList[i][j].id+')" class="item" target="_blank">' +
                '<span>' +
                '<i class="mini teal circle icon"></i>&nbsp;&nbsp;'+blogList[i][j].title+
                '<div class="ui teal basic left pointing label m-padded-mini">'+splitDate(blogList[i][j].updateTime,2)+'</div>' +
                '</span>' +
                '<div class="ui orange basic left pointing label m-padded-mini">'+flag+'</div>' +
                '</a>';
        }
        str+='</div>';
        $('#archive-content').append(str);
    }
    $('#archive-total').text(totalBlog);
}

function toBlogDetails(id){
    setStorage("preBlogId",id);
    window.open("/blog/pre_nav.do?id=6");//打开新的页面
}
function splitDate(date,id){
    var dataString=date.split("-");
    if(id==1) return dataString[0];
    else return dataString[1]+"月"+dataString[2]+"日";
}
function setStorage(key,value){
    var storage=window.localStorage;
    storage.setItem(key,value);
}