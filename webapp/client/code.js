
$(document).ready(function() {
    var baseUrl = "http://localhost:8080";

    $("#searchbutton").click(function() {
        console.log("Sending request to server.");
        $.ajax({
            method: "GET",
            url: baseUrl + "/search",
            data: {query: $('#searchbox').val()}
        }).success( function (data) { 
            console.log("Received response " + data);
            $("#responsesize").html("<p> We found " + data.length + " websites for you </p>");
            var buffer = "<ul>\n";
            $.each(data, function(index, value) { 
                buffer += "<li><a href=\"" + value.url + "\">" + value.title + "</a></li>\n";
            });
            buffer += "</ul>";
            $("#urllist").html(buffer);
        });
    });

    $(document).bind('keypress', function(e){
        if(e.keyCode==13){
            $('#searchbutton').trigger('click');
        }
    });
});
