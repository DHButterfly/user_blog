$(function topNav(){
    $('#blog').click(function () {
        window.location.href="/blog/manage_nav.do?id=1&token="+getToken();
    });
    $('#type').click(function () {
        window.location.href="/blog/manage_nav.do?id=2&token="+getToken();
    });
    $('#tag').click(function () {
        window.location.href="/blog/manage_nav.do?id=3&token="+getToken();
    });
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