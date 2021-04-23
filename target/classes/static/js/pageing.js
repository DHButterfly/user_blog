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
        //initStr.push('<nav><ul class="'+ className +'" onselectstart="return false">');
        // if(hasFirstBtn)initStr.push('<li class="ui mini item first-page" value="1">'+firstText +'</li>');
        // if(hasPreBtn) initStr.push('<li class="ui mini item pre-page" value="' + (currentNum - 1) + '">'+preText +'</li>');
        // if(hasNextBtn) initStr.push('<li class="ui mini item next-page" value="' + (currentNum + 1) + '">'+ nextText +'</li>');
        // if(hasLastBtn) initStr.push('<li class="ui mini item last-page" value="' + pageCount + '">'+ lastText +'</li>');
        // if(hasInput)
        //     initStr.push('<div class="ui mini item input-page-div">当前第<input type="text" style="width:4em;height:2em" maxlength="6" value="' + currentNum + '" />页，共<span>'
        //         + pageCount
        //         + '</span>页<a href="#" class="ui small right floated teal button">确定</a></div>');
        //initStr.push('</ul></nav>');


        initStr.push('<nav class="ui small orange inverted pagination menu">');
        if(hasFirstBtn)initStr.push('<li class="ui mini item first-page" value="1"><span class="mouse">'+ firstText +'</span></li>');
        if(hasPreBtn)  initStr.push('<li class="ui mini item pre-page"  value="' + (currentNum - 1) + '"><span class="mouse">'+ preText +'</span></li>');
        if(hasNextBtn) initStr.push('<li class="ui mini item next-page" value="' + (currentNum + 1) + '"><span class="mouse">'+ nextText +'</span></li>');
        if(hasLastBtn) initStr.push('<li class="ui mini item last-page" value="' + pageCount + '"><span class="mouse">'+ lastText +'</span></li>');
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

$(function () {
    getResult(1,5);
});

function getResult(pageNum,pageSize){
    var token=getToken();
    var url;
    if(getId()==2) url='/blog/type/get_blogtype.do?token='+token+'&pageNum='+pageNum+"&pageSize="+pageSize;
    if(getId()==3) url='/blog/tag/get_blogtag.do?token='+token+'&pageNum='+pageNum+"&pageSize="+pageSize;
    $.ajax({
        type: "GET",
        //url: "../../metrics.do?type=8&pageNum="+pageNum+"&pageSize="+pageSize,
        url:url,
        // url:'/blog/type/get_blogtype.do?token='+token,
        data: {},
        async: false,
        //是否缓存结果
        cache: false,
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(data){
            let list = data.data;
            createPageNav({
                $container : $("#pageing"),
                pageCount : list.pages,
                currentNum : list.pageNum,
                afterFun : function(num){
                    getResult(num,5);
                }
            });
            if(getId()==2) createTypeList(data);//渲染博客类型页面数据
            if(getId()==3) creatTagList(data);//渲染博客标签页面数据
            console.info(data);
        }
    });
}
$('#addtag').click(function () {
    window.location.href="/blog/manage_nav.do?id=5&token="+getToken();
    //跳转到新增页面添加数据之后返回类型页面
});
$('#addtype').click(function () {
    window.location.href="/blog/manage_nav.do?id=4&token="+getToken();
    //跳转到新增页面添加数据之后返回类型页面
});
$('.commit').click(function () { //提交新增类型表单,因为编辑也是同一个页面，需要使用标识区分是编辑还是新增
    if(getId()==4){
        // var blogType=getStorage("blogType");
        // var id= JSON.parse(blogType).id;
        //var typeName= JSON.parse(blogType).typeName);
        var id=$("input[name='type-inputId']").val();
        var typeName=$("input[name='typename']").val();
        if(!typeName) alert("JS校验：请输入博客类型名!");//&&typeof(typeName)!="undefined" && typeName !=0
        else{
            if(id!=""){ //编辑
                editTypeTag(getId(),new BlogType(id,typeName));
            }else{
                commitTypeTag(getId(),typeName);//新增
            }
        }
    }else{
        // var blogTag=getStorage("blogTag");
        // var id= JSON.parse(blogTag).id;
        var id=$("input[name='tag-inputId']").val();
        var tagName=$("input[name='tagname']").val();
        if(!tagName) alert("JS校验：请输入博客标签名!");
        else{
            if(id!=""){ //编辑
                editTypeTag(getId(),new BlogTag(id,tagName));
            }else{
                commitTypeTag(getId(),tagName);//新增
            }
        }
    }
});



function commitTypeTag(id,name){
    var token=getToken();
    var url=id==4?"/blog/type/add_blogtype.do":"/blog/tag/save_blogtag.do";
    $.ajax({
        url: url,
        data:{
            name:name,
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
                alert("新增成功！");
                let newid=id==4?2:3;
                window.location.href="/blog/manage_nav.do?id="+newid+"&token="+getToken();
            }else if(res.status==1){
                alert(res.msg);
            }
        },
        error: function (XMLHttpRequest) {
            alert("请求"+url+"接口失败！");
        }
    })
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
function toEditTag(row){
    //编辑按钮
    var tagName=row.parentNode.parentNode.childNodes[1].innerHTML;
    //var typeName=$(tbody).find('tr').eq(1).find('td').eq(2);//document.queryselector("#tbody > tr:nth-child(2)")
    //var typename=$("input[name='typename']");
    var id=row.parentNode.parentNode.firstElementChild.innerHTML;
    var blogTag=new BlogTag(id,tagName);
    setStorage("blogTag",JSON.stringify(blogTag));
    window.location.href="/blog/manage_nav.do?id=5&token="+getToken();
}
function editTypeTag(id,blogTypeTag){
    var token=getToken();
    var url=id==4?"/blog/type/update_blogtype.do":"/blog/tag/save_blogtag.do";
    $.ajax({
        url:url,
        data:{
            id:blogTypeTag.id,
            name:id==4?blogTypeTag.typeName:blogTypeTag.tagName,
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
                alert("修改成功，刷新页面即可！");
                if(id==4) removeStorage("blogType");
                if(id==5) removeStorage("blogTag");
                let newid=id==4?2:3;
                window.location.href="/blog/manage_nav.do?id="+newid+"&token="+getToken();
            }else if(res.status==1){
                alert(res.msg);
            }
        },
        error: function (XMLHttpRequest) {
            alert("请求"+url+"接口失败！");
        }
    })
}
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
function getId() {
    var query = decodeURI(window.location.search.substring(1));
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == "id") {
            return pair[1];
        }
    }
}
function BlogTag(id,tagName){
    this.id=id;
    this.tagName=tagName;
}

function BlogType(id,typeName){
    this.id=id;
    this.typeName=typeName;
}
function creatTagList(result) {
    var data=result.data;
    console.log("标签获取数据:"+data);
    var dataList=data.list;
    $("#tagtable").empty();//清空
    for(var i=0;i<dataList.length;i++){
        $("#tagtable").append(
            "<tr>"+
            "<td>"+dataList[i].id+"</td>"+
            "<td>"+dataList[i].name+"</td>"+
            "<td>"+
            "<a class='ui mini teal button' id='tagEdit' onclick='toEditTag(this)'>编辑</a>" +
            "<a class='ui mini red button' id='tagDelete' onclick='manageDelete(this)'>删除</a>"+
            "</td>"+
            "</tr>"
        )
    }
}
function createTypeList(result) {
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
            "<a class='ui mini red button' id='typeDelete' onclick='manageDelete(this)'>删除</a>"+
            "</td>"+
            "</tr>"
        )
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