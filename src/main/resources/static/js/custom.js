$(document).ready(function() {
	 $(".size-css").click(function() {
	 	$(".size-css").removeClass("size-css-active");
	 	
	 	$(this).addClass("size-css-active");
	 
        var detail = $(this).attr("data");
        var url = $(this).closest("div.change-url").find("#add-cart").attr("href");
        url = url.split("&")[0];
        console.log(url);
        url = url + "&detail=" + detail;
        $(this).closest("div.change-url").find("#add-cart").attr("href", url);
    });
});