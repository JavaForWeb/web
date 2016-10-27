var baseUrl = null;

var config = {};

window.onload = function() {
}


function postToServlet() {
    var baseUrl = "";
    var formData = {"width" : 10, "height" : 50 }
    $.ajax({
        url: baseUrl + "simple",
        type: "POST",
        data: formData,
        timeout: 30000
    }).done(function(data) {
        setStatusBar("danger", data);
    }).fail(function(error) {
        setStatusBar("danger", data);
    });

    function setStatusBar(type, msg) {
        $("#status").html(msg);
    }
}
