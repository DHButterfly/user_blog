function setStorage(key,value){
    var storage=window.localStorage;//window.sessionStorage
    storage.setItem(key,value);
}
function getStorage(key){
    var storage=window.localStorage;
    return storage.getItem(key);
}
function  clearStorage() {
    var storage=window.localStorage;
    storage.clear();
}
function  removeStorage(key) {
    var storage=window.localStorage;
    storage.removeItem(key);
}