/*菜单栏移动端显示,PC端隐藏*/
$('.menu-toggle').click(function(){
	$('.m-item').toggleClass('m-mobile-hide');
});
/*赞赏功能*/
$('#payButton').popup({
	popup:$('.payQR.popup'),
	on:'click',
	position:'bottom center',
});
/*关于我鼠标放在微信图标显示二维码*/
$('.weixin').popup({
	popup:$('.wechat-QR.popup'),
	position:'bottom center',
});
/*博客详情页面*/
$('.blog-wechat').popup({
	popup:$('.wechat-QR.popup'),
	on: 'click',
	position:'left center',
});
/*下拉框*/
$('.ui.dropdown').dropdown({
    on: 'hover',
});
/*目录导航*/
$('.catalog-btn').popup({
	popup: $('.catalog-container.popup'),
	on: 'click',
	position:'left center'
});
/*根据id生成目录*/
tocbot.init({
  // Where to render the table of contents.
  tocSelector: '.js-toc',
  // Where to grab the headings to build the table of contents.
  contentSelector: '.js-toc-content',
  // Which headings to grab inside of the contentSelector element.
  headingSelector: 'h1, h2, h3',
  // For headings inside relative or absolute positioned containers within content.
  hasInnerContainers: true,
});
/*二维码生成*/

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
// qrCode("qrCode","http://localhost:8888/blog/pre_nav.do?id="+blogid);
/*平滑滚动*/
$('#back-to-top').click(function(){
	$(window).scrollTo(0,300);
});

/*滚动侦测*/
var waypoint = new Waypoint({
  element: document.getElementById('waypoint'),
  handler: function(direction) {
    if(direction=='down'){
		$('#toolbar').show();
	}else{
		$('#toolbar').hide(400);
	}
  }
})