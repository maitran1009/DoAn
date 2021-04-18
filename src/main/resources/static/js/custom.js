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
					cityId: this.value,
					districtId: $(this).attr("data-city")
				}
			}).done(function(value) {
				console.log(value);
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

	$(document).on("click", "#but_step2", function() {
		$.ajax({
			url: 'http://localhost:9090/mySuFood/pay/vnpay',
			type: 'GET',
			contentType: 'application/json',
			data: {
				bankCode: 'NCB',
				amount: 100000
			}
		}).done(function(value) {
			console.log(value);
			window.location.href = value;
		});
	});
});