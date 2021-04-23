function loadProperties(){
    jQuery.i18n.properties({
        name:'about',//properties资源名称
        path:'/blog/i18n/',//properties资源路径
        mode:'map',//用 Map 的方式使用资源文件中的值
        language:'zh_CN',// 对应的语言
        // async:true,
        encoding: 'UTF-8',
        //cache:false,
        callback: function() { //加载成功后设置显示内容
            var username=$.i18n.prop('username');
            console.log(username);
        }
    });
}

$(function(){
    loadProperties();
});