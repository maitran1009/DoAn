$(document).ready(function() {
	 $(".size-css").click(function() {
	 	$(".size-css").removeClass("size-css-active");
	 	
	 	$(this).addClass("size-css-active");
	 
        var size = $(this).attr("data");
        var url = $(this).closest("div.change-url").find("#add-cart").attr("href");
        url = url + "&size=" + size;
        $(this).closest("div.change-url").find("#add-cart").attr("href", url);
    });
});