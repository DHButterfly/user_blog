document.write("<script language='text/javascript' src='./manage_index.js' th:src='@{/js/manage_index.js}'></script>");
var newtoken = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
	xhr.setRequestHeader(header, newtoken);
});

$('.ui.dropdown').dropdown({
    on: 'hover',
});

// markdown编辑器初始化
var testEditor;
$(function() {
	testEditor = editormd({
		id      : "md-content",
		width   : "100%",
		height  : 640,
		//path    : "../lib/editormd/lib/"
		path : "/blog/lib/editormd/lib/"
	});
});
//移动端导航按钮显示
$('.menu-toggle').click(function(){
	$('.m-item').toggleClass('m-mobile-hide');
});

//保存和发布应该先看一下有没有id值，没有则调用插入方法
$('#save-blog').click(function () {
	$('[name="published"]').val(false);
	var boo=$('.ui.form').form('validate form');//表单校验
	if(boo) saveBlog();
});
$('#publish-blog').click(function () {
	$('[name="published"]').val(true);
	var boo=$('.ui.form').form('validate form');//表单校验
	if(boo) saveBlog();
	//$('#blog-form').submit();
});
//提示消息关闭功能
$('.message.close')
	.on('click',function () {
		$(this).closest('.message').transition('fade');
	});

$('.ui.tag.dropdown')
	.dropdown({
		allowAdditions: true
	});

var blogId;
$(function () {
	getAllTypes();
	getAllTags();
	blogId=getStorage("blogId");
	removeStorage("blogId");
	if(blogId!=null){
		//根据博客id请求到数据渲染到页面中
		getBlogDetails(blogId);
		//removeStorage("blogId");
	}
});

function tagMenu(data){
	//tagMenu
	if(data!=null){
		$('#tagMenu').empty();
		for(let i=0; i<data.length; i++){
			$('#tagMenu').append(
				"<div class='item' data-value="+data[i].id+">"+ data[i].name+ "</div>"
			)
		}
	}else{ //否则展示查询无数据
		alert("无标签数据!");
	}
}
function getAllTypes(){
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
function createBlogType(result) {
	var data=result.data;
	console.log("博客类型数据:");
	console.log(data);
	$('#typeMenu').empty();
	for(let i=0;i<data.length;i++){  //渲染数据<div class="item" data-value="1">错误日志</div>
		$('#typeMenu').append(
			"<div class='item' data-value="+data[i].id+">"+ data[i].name+ "</div>"
		)
	}
}
function getAllTags(){
	$.ajax({
		url:'/blog/blogs/get_all_tag.do?token='+getToken(),
		async: false,
		//async. 默认是 true，即为异步方式，$.ajax执行后，会继续执行ajax后面的脚本，直到服务器端返回数据后，触发$.ajax里的success方法，这时候执行的是两个线程。
		// async 设置为 false，则所有的请求均为同步请求，在没有返回值之前，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
		cache: false,
		type: "GET",
		dataType: "json",
		xhrFields: {
			withCredentials: true
		},
		success: function(res){
			if(res.status==0){
				console.log("标签数据：")
				console.log(res);
				tagMenu(res.data);
				//渲染标签数据
			}else if(res.status==1){
				alert(res.msg);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert("请求get_all_tag.do接口失败！" + XMLHttpRequest.readyState);
		}
	});
}
function saveBlog(){
	var id=blogId;
	var title=$('[name="title"]').val();
	var firstPicture=$('[name="firstPicture"]').val();
	var flag=$('[name="flag"]').val();

	var appreciation=$('[name="appreciation"]').is(":checked");//转载申明是否开启
	var sharestatement=$('[name="sharestatement"]').is(":checked");//版权开启
	var commentabled=$('[name="commentabled"]').is(":checked");//评论功能
	var recommend=$('[name="recommend"]').is(":checked");//是否推荐


	var published=$('[name="published"]').val();//发布还是草稿
	var typeId=$('[name="typeId"]').val();
	var tagIds=$('[name="tagIds"]').val();
	var content=$('[name="content"]').val();
	var description=$('[name="description"]').val();


	console.log("表单数据：");
	// console.log($('#blog-form').serialize());
	var blogs={
		"id":id,
		"title":title,
		"firstPicture":firstPicture,
		"flag":flag,
		"appreciation":appreciation,
		"sharestatement":sharestatement,
		"commentabled":commentabled,
		"published":published,
		"recommend":recommend,
		"typeId":typeId,
		"content":content,
		"description":description
	};
	console.log(JSON.stringify(blogs));////将JSON对象转换为字符串
	// var blogs=JSON.parse(JSON.stringify($('#blog-form').serialize()));
	published=published=="true"?true:false;
	flag=flag=="原创"?true:false;
	typeId=parseInt(typeId);

	$.ajax({
		type: "POST",//方法类型
		async: false,
		cache: false,
		dataType: "json",
		url: "/blog/blogs/save_all_blog.do",
		// contentType:"application/json;charset=UTF-8",
		data:{
			// blog:$('#blog-form').serialize(),
			// blog:JSON.stringify(blogs),
			id:id,
			title:title,
			firstPicture:firstPicture,
			flag:flag,
			appreciation:appreciation,
			sharestatement:sharestatement,
			commentabled:commentabled,
			published:published,
			recommend:recommend,
			typeId:typeId,
			content:content,
			description:description,
			tagIds:tagIds,
			token:getToken()
		},
		xhrFields: {
			withCredentials: true
		},
		success: function (result) {
			console.log(result);
			if (result.status == 0) {
				//成功之后跳转博客列表页面,显示发布成功信息
				published==true?alert("发布成功！"):alert("保存成功！");
				window.location.href="/blog/manage_nav.do?id=1&token="+getToken();
			} else if (result.status == 1) {
				alert(result.msg);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert("save_all_blog.do接口失败！" + XMLHttpRequest.readyState);
		}
	});
}
function getBlogDetails(id) {
	$.ajax({
		url:"/blog/blogs/get_blog_details.do",
		data:{
			id:id,
			token:getToken()
		},
		type: "POST",//方法类型
		async: false,
		cache: false,
		dataType: "json",
		xhrFields: {
			withCredentials: true
		},
		success: function (res) {
			console.log(res);
			if (res.status == 0) {
				//渲染页面
				createBlogDetails(res.data);
			} else if (res.status == 1) {
				alert(res.msg);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert("get_blog_details.do接口失败！" + XMLHttpRequest.readyState);
		}
	});
}
function createBlogDetails(data){
	$('[name="title"]').val(data.title);
	$('[name="firstPicture"]').val(data.firstPicture);
	$('[name="flag"]').val(data.flag);//原创、转载
	if(data.appreciation){
		$('[name="appreciation"]').prop("checked",true);
		$('[name="appreciation"]').attr("checked","checked");
	}

	if(data.commentabled){
		$('[name="commentabled"]').prop("checked",true);
		$('[name="commentabled"]').attr("checked","checked");
	}
	if(data.recommend){
		$('[name="recommend"]').prop("checked",true);
		$('[name="recommend"]').attr("checked","checked");
	}
	if(data.sharestatement){
		$('[name="sharestatement"]').prop("checked",true);
		$('[name="sharestatement"]').attr("checked","checked");
	}

	$('[name="published"]').val(data.published);//隐藏域值,标明是发布还是草稿
	// if(data.recommend) $('[name="recommend"]').val(data.recommend);
	var typeFlag=data.flag==true?"原创":"转载";
	$('#flagMenu div[data-value="'+typeFlag+'"]').addClass("active selected");
	$('#flagText').text(typeFlag);

	$('#typeMenu div[data-value="'+data.typeId+'"]').addClass("active selected");
	$('#typeText').removeClass("default");
	let typeText=$('#typeMenu div[data-value="'+data.typeId+'"]').text();
	$('#typeText').text(typeText);
	$('[name="typeId"]').val(data.typeId);


	//alert(data.tagIds);
	var taglist=data.tagIds.split(",");
	for(let i=0;i<taglist.length;i++){
		$('#tagMenu div[data-value="'+taglist[i]+'"]').addClass("active filtered");
		let tagText=$('#tagMenu div[data-value="'+taglist[i]+'"]').text();
		$('.tag').append(
			// "<a class='ui label transition visible' data-value='2' style='display:inline-block !important;'>测试一下<i class='delete icon'></i></a>"
		"<a class='ui label transition visible' data-value='"+ taglist[i]+"' style='display:inline-block !important;'>"+tagText + "<i class='delete icon'></i></a>"
		);
	}
	$('#tagsText').removeClass("default");
	$('#tagsText').text("");
	$('[name="tagIds"]').val(data.tagIds);

	$('[name="content"]').val(data.content);
	$('[name="description"]').val(data.description);
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

//表单校验
$('.ui.form').form({
	fields : {
		title : {
			identifier: 'title',
			rules: [{
				type : 'empty',
				prompt: '标题不能为空！'
			}]
		},
		content : {
			identifier: 'content',
			rules: [{
				type : 'empty',
				prompt: '博客类容不能为空！'
			}]
		},
		typeId : {
			identifier: 'typeId',
			rules: [{
				type : 'empty',
				prompt: '请选择博客类型！'
			}]
		},
		firstPicture : {
			identifier: 'firstPicture',
			rules: [{
				type : 'empty',
				prompt: '请给出首图地址!'
			}]
		},
		description : {
			identifier: 'description',
			rules: [{
				type : 'empty',
				prompt: '请输入博客描述...'
			}]
		}
	}
});
function getStorage(key){
	var storage=window.localStorage;
	return storage.getItem(key);
}

function  removeStorage(key) {
	var storage=window.localStorage;
	storage.removeItem(key);
}