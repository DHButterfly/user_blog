$(function () {
    var typename=$("input[name='typename']");
//var typename=document.getElementsByName("typename");
    var blogType=getStorage("blogType");
    typename.val(JSON.parse(blogType).typeName);
    $("#type-inputId").val(JSON.parse(blogType).id);
    removeStorage("blogType")
});
function getStorage(key){
    var storage=window.localStorage;
    return storage.getItem(key);
}

function  removeStorage(key) {
    var storage=window.localStorage;
    storage.removeItem(key);
}
