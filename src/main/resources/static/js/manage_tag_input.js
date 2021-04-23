$(function () {
    var tagname=$("input[name='tagname']");
//var typename=document.getElementsByName("typename");
    var blogTag=getStorage("blogTag");
    tagname.val(JSON.parse(blogTag).tagName);
    $("#tag-inputId").val(JSON.parse(blogTag).id);
    removeStorage("blogTag")
});
function getStorage(key){
    var storage=window.localStorage;
    return storage.getItem(key);
}

function  removeStorage(key) {
    var storage=window.localStorage;
    storage.removeItem(key);
}
