$(document).ready(function() {
	 $(".size-css").click(function() {
	 	$(".size-css").removeClass("size-css-active");
	 	
	 	$(this).addClass("size-css-active");
	 
<<<<<<< HEAD
        var size = $(this).attr("data");
        var url = $(this).closest("div.change-url").find("#add-cart").attr("href");
        url = url + "&size=" + size;
=======
        var detail = $(this).attr("data");
        var url = $(this).closest("div.change-url").find("#add-cart").attr("href");
        url = url.split("&")[0];
        console.log(url);
        url = url + "&detail=" + detail;
>>>>>>> 0bedecabb78b8a7ed530b3d507dddf02807ca561
        $(this).closest("div.change-url").find("#add-cart").attr("href", url);
    });
});