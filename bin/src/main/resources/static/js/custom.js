$(document).ready(function() {
	$(".size-css-enable").click(function() {
		$(this).parent().find(".size-css-enable").removeClass("size-css-active");

		$(this).addClass("size-css-active");

		var detail = $(this).attr("data");
		var url = $(this).closest("div.change-url").find("#add-cart").attr("data-url");
		url = url + "&detail=" + detail;
		$(this).closest("div.change-url").find("#add-cart").attr("href", url);
		$(this).closest("div.change-url").find("#add-cart").attr("href", url);
		$(this).closest("div.change-url").find("#add-cart").attr("class", "add-cart");
	});

	$(document).on("click", ".no-add-cart", function() {
		alert("Vui lòng chọn kích thước sản phẩm");
	});

	$('#pay-city').on('change', function() {
		if (this.value > 0) {
			$('#pay-district').attr("data-city", this.value);
			$.ajax({
				url: 'http://localhost:9090/mySuFood/province/district',
				type: 'GET',
				contentType: 'application/json',
				data: {
					cityId: this.value
				}
			}).done(function(value) {
				if (value != null) {
					var html = "<option value='-1'>Chọn Quận/Huyện</option>";
					$.each(value, function(key, value) {
						html += "<option value='" + value.districtId + "'>" + value.districtName + "</option>";
					});
					$('#pay-district').html(html);
				}
			});
		} else {
			$('#pay-district').attr("data-city", 0);
			$('#pay-district').html("<option value='-1'>Chọn Quận/Huyện</option>");
			$('#pay-ward').html("<option value='-1'>Chọn Phường/Xã</option>");
		}
	});

	$('#pay-district').on('change', function() {
		if (this.value > 0) {
			$.ajax({
				url: 'http://localhost:9090/mySuFood/province/ward',
				type: 'GET',
				contentType: 'application/json',
				data: {
					cityId: $(this).attr("data-city"),
					districtId: this.value
				}
			}).done(function(value) {
				if (value != null) {
					var html = "<option value='-1'>Chọn Phường/Xã</option>";
					$.each(value, function(key, value) {
						html += "<option value='" + value.wardId + "'>" + value.wardName + "</option>";
					});
					$('#pay-ward').html(html);
				}
			});
		} else {
			$('#pay-ward').html("<option value='-1'>Chọn Phường/Xã</option>");
		}
	});


	$(document).on("click", "#redirect-pay", function() {
		var user = {};
		user["email"] = $("input[name=email]").val();
		user["name"] = $("input[name=name]").val();
		user["phone"] = $("input[name=phone]").val();
		user["ward"] = $("select[name=ward]").val();
		user["address"] = $("input[name=address]").val();
		localStorage.setItem("user", JSON.stringify(user));

		window.location.href = "http://localhost:9090/mySuFood/pay/type";
	});

	$(document).on("click", "#but_step2", function() {
		var type = $("input[name=payment_method]:checked").val();
		var user = JSON.parse(localStorage.getItem("user"));
		var amount = $(".payment-due-price").text();
		if (type == 2) {
			$.ajax({
				url: 'http://localhost:9090/mySuFood/pay/momo/get-url',
				type: 'GET',
				contentType: 'application/json',
				data: {
//					amount: amount.replace(".","").replace(" ₫","")
					amount: 10000,
					email : user.email,
					name : user.name,
					phone : user.phone,
					ward : user.ward,
					address : user.address,
					payType : type
				}
			}).done(function(value) {
				window.location.href = value;
			});
		} else {
			$.ajax({
				url: 'http://localhost:9090/mySuFood/pay/success',
				type: 'GET',
				contentType: 'application/json',
				data: {
					email : user.email,
					name : user.name,
					phone : user.phone,
					ward : user.ward,
					address : user.address,
					payType : type
				}
			}).done(function(value) {
				if(value){
					localStorage.removeItem("user");
					window.location.href = "http://localhost:9090/mySuFood/pay/pay-success";
				}
			});
		}
	});
});