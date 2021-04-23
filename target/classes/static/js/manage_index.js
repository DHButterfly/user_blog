// document.write("<script language='text/javascript' src='pageing.js'></script>");
// import './pageing.js'
var newtoken = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, newtoken);
});
//分页，页码导航,要求参数为一个对象
function createPageNav(opt) {
    opt= opt || {};
    var $container  = opt.$container || null, //必需，页码容器，请确保这个容器只用来存放页码导航
        pageCount  = Number(opt.pageCount)  || 0,  //必需，页码总数
        currentNum  = Number(opt.currentNum) || 1,  //选填，当前页码
        maxCommonLen = Number(opt.maxCommonLen)|| 10,  //选填，普通页码的最大个数

        className = opt.className || "pagination",//选填，分页类型：pagination或pager等
        preText  = opt.preText  || "上一页",   //选填，上一页文字显示，适用于只有前后页按钮的情况
        nextText = opt.nextText  || "下一页",   //选填，下一页文字，同上
        firstText = opt.firstText || "首页",
        lastText = opt.lastText  || "末页",

        hasFirstBtn = opt.hasFirstBtn  === false ? false : true,
        hasLastBtn  = opt.hasLastBtn  === false ? false : true,
        hasPreBtn  = opt.hasPreBtn   === false ? false : true,
        hasNextBtn  = opt.hasNextBtn  === false ? false : true,
        hasInput   = opt.hasInput   === false ? false : true,
        hasCommonPage= opt.hasCommonPage === false ? false : true,//选填，是否存在普通页

        beforeFun = opt.beforeFun || null, //选填，页码跳转前调用的函数，可通过返回false来阻止跳转，可接收目标页码参数
        afterFun = opt.afterFun || null, //选填，页码跳转后调用的函数，可接收目标页码参数
        noPageFun = opt.noPageFun || null; //选填，页码总数为0时调用的函数

    //当前显示的最小页码，用于计算起始页码，直接容器,当前页，前，后，首，末，输入框
    var minNum=1,changeLen,$parent,$currentPage,$preBtn,$nextBtn,$firstBtn,$lastBtn,$input;

    //容器
    if (!$container || $container.length != 1){
        console.log("分页容器不存在或不正确");
        return false;
    }
    //总页数
    if(pageCount <= 0){
        if(noPageFun) noPageFun();
        return false;
    }
    //当前页
    if (currentNum < 1) currentNum = 1;
    else if (currentNum > pageCount) currentNum = pageCount;
    //普通页码的最大个数，起始页算法限制，不能小于3
    if(maxCommonLen<3) maxCommonLen=3;
    //跳转页响应长度，用于计算起始页码
    if(maxCommonLen>=8) changeLen=3;
    else if(maxCommonLen>=5) changeLen=2;
    else changeLen=1;

    $container.hide();
    _initPageNav();
    $container.show();

    function _initPageNav(){
        var initStr = [];
        initStr.push('<nav class="ui small orange inverted pagination menu">');
        if(hasFirstBtn)initStr.push('<li class="ui mini item first-page" value="1"> <span class="mouse">'+ firstText +'</span></li>');
        if(hasPreBtn)  initStr.push('<li class="ui mini item pre-page"  value="' + (currentNum - 1) + '"> <span class="mouse">'+ preText +'</span></li>');
        if(hasNextBtn) initStr.push('<li class="ui mini item next-page" value="' + (currentNum + 1) + '"> <span class="mouse">'+ nextText +'</span></li>');
        if(hasLastBtn) initStr.push('<li class="ui mini item last-page" value="' + pageCount + '"> <span class="mouse">'+ lastText +'</span></li>');
        if(hasInput)
            initStr.push('<div  class="ui mini item input-page-div">当前第<input type="text" maxlength="6" class="page" value="' + currentNum + '" />页，共<span>'
                + pageCount
                + '</span>页<a class="ui tiny basic inverted button" id="ensure" style="margin-left:0.2em;background-color:#FFA500;">确定</a></div>');
        initStr.push('</nav>');//btn-xs input-btn-xs



        $container.html(initStr.join(""));
        //初始化变量
        $parent=$container.children();
        // $parent=$container;
        //alert($parent)
        if(hasFirstBtn) $firstBtn = $parent.children("li.first-page");
        if(hasPreBtn)  $preBtn  = $parent.children("li.pre-page");
        if(hasNextBtn) $nextBtn = $parent.children("li.next-page");
        if(hasLastBtn) $lastBtn = $parent.children("li.last-page");
        if(hasInput){
            $input = $container.find("div.input-page-div>input");
            $container.find("div.input-page-div>a").click(function(){
                _gotoPage($input.val());
            });
        }
        //初始化功能按钮
        _buttonToggle(currentNum);
        //生成普通页码
        if(hasCommonPage) {
            _createCommonPage(_computeStartNum(currentNum), currentNum);
        }
        //绑定点击事件
        $container.on("click", "li",function () {
            var $this=$(this);
            if ($this.is("li") && $this.attr("value")){
                if(!$this.hasClass("disabled") && !$this.hasClass("active")){
                    _gotoPage($this.attr("value"));
                }
            }
        });
    }
    //跳转到页码
    function _gotoPage(targetNum) {
        targetNum=_formatNum(targetNum);
        if (targetNum == 0 || targetNum == currentNum) return false;
        // 跳转前回调函数
        if (beforeFun && beforeFun(targetNum) === false) return false;
        //修改值
        currentNum=targetNum;
        if(hasInput)  $input.val(targetNum);
        if(hasPreBtn) $preBtn.attr("value", targetNum - 1);
        if(hasNextBtn) $nextBtn.attr("value", targetNum + 1);
        //修改功能按钮的状态
        _buttonToggle(targetNum);
        // 计算起始页码
        if(hasCommonPage) {
            var starNum = _computeStartNum(targetNum);
            if (starNum == minNum) {// 要显示的页码是相同的
                $currentPage.removeClass("active");
                $currentPage = $parent.children("li.commonPage").eq(targetNum - minNum).addClass("active");
            }
            else {// 需要刷新页码
                _createCommonPage(starNum, targetNum);
            }
        }
        // 跳转后回调函数
        if (afterFun) afterFun(targetNum);
    }
    //整理目标页码的值
    function _formatNum(num){
        num = Number(num);
        if(isNaN(num)) num=0;
        else if (num <= 0) num = 1;
        else if (num > pageCount) num = pageCount;
        return num;
    }
    //功能按钮的开启与关闭
    function _buttonToggle(current){
        if (current == 1) {
            if(hasFirstBtn) $firstBtn.addClass("disabled");
            if(hasPreBtn)  $preBtn.addClass("disabled");
        }
        else {
            if(hasFirstBtn) $firstBtn.removeClass("disabled");
            if(hasPreBtn)  $preBtn.removeClass("disabled");
        }

        if (current == pageCount) {
            if(hasNextBtn) $nextBtn.addClass("disabled");
            if(hasLastBtn) $lastBtn.addClass("disabled");
        }
        else {
            if(hasNextBtn) $nextBtn.removeClass("disabled");
            if(hasLastBtn) $lastBtn.removeClass("disabled");
        }
    }
    //计算当前显示的起始页码
    function _computeStartNum(targetNum) {
        var startNum;
        if (pageCount <= maxCommonLen)
            startNum = 1;
        else {
            if ((targetNum - minNum) >= (maxCommonLen-changeLen)) {//跳转到靠后的页码
                startNum = targetNum - changeLen;
                if ((startNum + maxCommonLen-1) > pageCount) startNum = pageCount - (maxCommonLen-1);// 边界修正
            }
            else if ((targetNum - minNum) <= (changeLen-1)) {//跳转到靠前的页码
                startNum = targetNum - (maxCommonLen-changeLen-1);
                if (startNum <= 0) startNum = 1;// 边界修正
            }
            else {// 不用改变页码
                startNum = minNum;
            }
        }
        return startNum;
    }
    //生成普通页码
    function _createCommonPage(startNum, activeNum) {
        var initStr = [];
        for (var i = 1,pageNum=startNum; i <= pageCount && i <= maxCommonLen; i++ , pageNum++) {
            initStr.push('<li style="list-style-type:none;" class="ui mini item commonPage" value="' + pageNum + '"><a href="javascript:" rel="external nofollow" >' + pageNum + '</a></li>');
        }

        $parent.hide();
        $parent.children("li.commonPage").remove();
        if(hasPreBtn) $preBtn.after(initStr.join(""));
        else if(hasFirstBtn) $firstBtn.after(initStr.join(""));
        else $parent.prepend(initStr.join(""));
        minNum = startNum;
        $currentPage = $parent.children("li.commonPage").eq(activeNum-startNum).addClass("active");
        $parent.show();
    }
}


function getAllType(){
    $.ajax({
        url:'/blog/blogs/get_all_type.do?token='+getToken(),
        // data:{
        //
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
        success:function(res){
            if(res.status==0){
                createBlogType(res);
            }else if(res.status==1){
                alert(res.msg);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求get_all_type.do接口失败！" + XMLHttpRequest.readyState);
        }
    });
}



$('#blog-search').click(function () {
    var title=$('input[name="title"]').val();
    var typeId=$('[name="typeId"]').val();
    var recommend=$('#recommend').is(':checked')==true?1:0;//选中true，未选中false
    title=title==""?null:title;
    typeId=typeId==""?0:typeId;
    //请求数据并分页展示
    findSearchBlog(1,5,title,typeId,recommend,1);

});
$(function(){
    findSearchBlog(1,5,"",0,0,0);
    getAllType();
});
function findSearchBlog(pageNum,pageSize,title,typeId,recommend,isSearch){
    //非空校验
    $.ajax({
        url:'/blog/blogs/get_blog.do',
        data:{
            pageNum:pageNum,
            pageSize:pageSize,
            title:title,
            typeId:typeId,
            recommend:recommend,
            isSearch:isSearch,
            token: getToken(),
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
        success: function(data){
            if(data.status==0){
                list = data.data;
                createPageNav({
                    $container : $("#pageing"),
                    pageCount : list.pages,
                    currentNum : list.pageNum,
                    afterFun : function(num){
                        if(isSearch==1) findSearchBlog(num,5,title,typeId,recommend,1);
                        else findSearchBlog(num,5);
                    }
                });
                createBlogList(data);//展示页面数据
                console.info(data);
            }else if(data.status==1){
                alert(data.msg);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求get_blog.do接口失败！" + XMLHttpRequest.readyState);
        }
    });
}
function createBlogType(result) {
    var data=result.data;
    console.log("博客类型数据:");
    console.log(data);
    $('#typeMenu').empty();
    for(var i=0;i<data.length;i++){  //渲染数据<div class="item" data-value="1">错误日志</div>
            $('#typeMenu').append(
                "<div class='item' data-value="+data[i].id+">"+ data[i].name+ "</div>"
            )
    }
}

function createBlogList(result) { //展示博客列表数据
    //判空处理
    var data=result.data;
    console.log("博客获取数据:"+data);
    var dataList=data.list;
    $("#blogtable").empty();//清空
    if(dataList==null||dataList.length==0){
        $("#blogtable").append(
            "<div class='ui error message'>没有查询到数据!</div>"
        );
    }else{
        for(var i=0;i<dataList.length;i++){
            var isRecommond=dataList[i].recommend==true?'是':'否';
            var isPublished=dataList[i].published==true?'已发布':'草稿';
            $("#blogtable").append(
                "<tr>"+
                "<td>"+dataList[i].id+"</td>"+
                "<td>"+dataList[i].title+"</td>"+
                "<td>"+dataList[i].typeId+"</td>"+
                "<td>"+isRecommond+"</td>"+
                "<td>"+isPublished+"</td>"+
                "<td>"+dataList[i].updateTime+"</td>"+
                "<td>"+
                "<a class='ui mini teal button' id='blogEdit' onclick='blogEdit(this)'>编辑</a>" +
                "<a class='ui mini red button' id='blogDelete' onclick='blogDelete(this)'>删除</a>"+
                "</td>"+
                "</tr>"
            );
        }
    }

}
function blogEdit(row) {  //得到编辑id，存入storage中
    var token=getToken();
    var id=row.parentNode.parentNode.firstElementChild.innerHTML;
    console.log("编辑博客id为：",id);
    setStorage("blogId",id);
    // window.location.href="/blog/manage_blog_edit.do?token="+getToken();
    window.open("/blog/manage_blog_edit.do?token="+getToken());
}
function blogDelete(row){
    var msg = "您确定删除该博客吗？\n请确认！";
    if (confirm(msg) == true) {
        var token=getToken();
        var id=row.parentNode.parentNode.firstElementChild.innerHTML;
        $.ajax({
            url:'/blog/blogs/delete_blog.do',
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
                    console.log("博数据删除成功！");
                    //删除成功之后提示一下
                    window.location.reload();
                    //window.location.reload(true) //浏览器重新从服务器请求资源,在http请求头中不会包含缓存标记。
                }else if(res.status==1){
                    alert(res.msg);
                }
            },
            error: function (XMLHttpRequest) {
                alert("请求delete_blog.do接口失败！" + XMLHttpRequest.readyState);
            }
        });
    }
}

$(function () {
    $('#add-blog').click(function () {
        window.location.href="/blog/manage_blog_edit.do?token="+getToken();
    });
});

$('#clear-type')
    .on('click', function() {
        $('.ui.search.dropdown')
            .dropdown('clear');
    });
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
// exports.getToken = getToken();
// const renderer = require('./manage_index.js');
function setStorage(key,value){
    var storage=window.localStorage;//window.sessionStorage
    storage.setItem(key,value);
}