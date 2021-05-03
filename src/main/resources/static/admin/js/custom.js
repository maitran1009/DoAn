$(document).ready(function() {
	$('[data-toggle="tooltip"]').tooltip();
	var actions = $("table td:last-child").html();
	// Append table with add row form on add new button click
	$(".add-new").click(function() {
		$(this).attr("disabled", "disabled");
		var index = $("table tbody tr:last-child").index();
		var row = '<tr>' +
			'<td><input type="text" class="form-control" name="name" id="name"></td>' +
			'<td><input type="text" class="form-control" name="department" id="department"></td>' +
			'<td><input type="text" class="form-control" name="phone" id="phone"></td>' +
			'<td>' + actions + '</td>' +
			'</tr>';
		$("table").append(row); $("table tbody tr").eq(index + 1).find(".add, .edit").toggle();
		$('[data-toggle="tooltip"]').tooltip();
	});
	// Add row on add button click
	$(document).on("click", ".add", function() {
		var empty = false;
		var input = $(this).parents("tr").find('input[type="text"]');
		input.each(function() {
			if (!$(this).val()) {
				$(this).addClass("error");
				empty = true;
			} else {
				$(this).removeClass("error");
			}
		});
		$(this).parents("tr").find(".error").first().focus();
		if (!empty) {
			input.each(function() {
				$(this).parent("td").html($(this).val());
			});
			$(this).parents("tr").find(".add, .edit").toggle();
			$(".add-new").removeAttr("disabled");
		}
	});
	// Edit row on edit button click
	$(document).on("click", ".edit", function() {
		$(this).parents("tr").find("td:not(:last-child)").each(function() {
			$(this).html('<input type="text" class="form-control" value="' + $(this).text() + '">');
		});
		$(this).parents("tr").find(".add, .edit").toggle();
		$(".add-new").attr("disabled", "disabled");
	});

	// Delete row on delete button click
	$(document).on("click", ".delete", function() {
		$(this).parents("tr").remove();
		$(".add-new").removeAttr("disabled");
	});

	$('body').on('click', '#add-detail', function() {
		$("#table-detail").append("<tr class='item-detail' data-detail='0'><td><select class='form-control size' name='size'><option value='1'>Nhỏ</option><option value='2'>Vừa</option><option value='3'>Lớn</option></select></td><td><select class='form-control status' name='status'><option value='1'>Còn hàng</option><option value='2'>Hết hàng</option></select></td><td><a class='delete' title='Delete'><i class='fas fa-trash'></i></a></td></tr>");
	});


	$('body').on('click', '#add-product', function() {
		var id = $(this).attr("data-flag");
		var name = $("input[name=name]").val();
		var price = $("input[name=price]").val();
		var description = $("textarea[name=description]").val();
		var image = $("input[name=image]").val().replace('C:\\fakepath\\', '');

		var product = {};
		var productDetails = [];

		$(".item-detail").each(function() {
			var productDetail = {};
			var flag = true;
			productDetail["id"] = $(this).attr('data-detail');
			productDetail["size"] = $(this).find('select.size').val();
			productDetail["status"] = $(this).find('select.status').val();
			if (productDetails.length == 0) {
				productDetails.push(productDetail);
			} else {
				$.each(productDetails, function(key, value) {
					if (productDetail.size == value.size) {
						flag = false;
					}
				});

				if (flag) {
					productDetails.push(productDetail);
				}
			}
		});

		product["id"] = id;
		product["name"] = name;
		product["price"] = price;
		product["description"] = description;
		if (image != "") {
			product["image"] = image;
		} else {
			product["image"] = $(".image-name").text();
		}
		product["productDetail"] = productDetails;

		var form = new FormData($('#fileUploadForm')[0]);

		$.ajax({
			url: 'http://localhost:9090/mySuFood/admin/product/create',
			type: 'POST',
			contentType: 'application/json',
			enctype: 'multipart/form-data',
			data: JSON.stringify(product)
		}).done(function(value) {
			if (value) {
				if (image != "") {
					// lưu file vào folder
					$.ajax({
						url: 'http://localhost:9090/mySuFood/admin/product/upload-file',
						type: 'POST',
						data: form,
						contentType: false,
						processData: false,
						enctype: "multipart/form-data"
					});
				}
				window.location = "http://localhost:9090/mySuFood/admin/product/list";
			}
		});
	});

	//Button delete
	$('body').on('click', '.product-delete', function() {
		var index = $(this).attr("data-index");
		var idx;
		$(".product-index").each(function(i) {
			idx = i + 1;
			if (i >= index) {
				idx--;
			}
			$(this).text(idx);
		});

		var id = $(this).attr("data");
		$.ajax({
			url: 'http://localhost:9090/mySuFood/admin/product/delete',
			type: 'GET',
			contentType: 'application/json',
			data: {
				id: id
			}
		}).done(function() {
		});
	});

	//Button edit
	$('body').on('click', '.product-edit', function() {
		var id = $(this).attr("data");
		$.ajax({
			url: 'http://localhost:9090/mySuFood/admin/product/info',
			type: 'GET',
			contentType: 'application/json',
			data: {
				id: id
			}
		}).done(function(product) {
			// css + html
			$(".image-name").css("display", "block");
			$("#table-detail").find(".item-detail").remove();
			$("#exampleModalLabel").text("Cập nhật sản phẩm");//Thay đổi text từ "Thêm sản phẩm"->"Cập nhật sản phẩm"
			$("#add-product").text("Cập nhật");
			$("#add-product").attr("data-flag", id);

			// write data
			$("input[name=name]").val(product.name);
			$("input[name=price]").val(product.price);
			$("textarea[name=description]").val(product.description);
			$(".image-name").text(product.image);

			var html = "";
			$.each(product.productDetail, function(key, value) {
				html += "<tr class='item-detail' data-detail='" + value.id + "' data-flag='0'><td><select class='form-control size' name='size'>";
				if (value.size == 1) {
					html += "<option value='1' selected>Nhỏ</option><option value='2'>Vừa</option><option value='3'>Lớn</option>";
				} else if (value.size == 2) {
					html += "<option value='1'>Nhỏ</option><option value='2' selected>Vừa</option><option value='3'>Lớn</option>";
				} else {
					html += "<option value='1'>Nhỏ</option><option value='2'>Vừa</option><option value='3' selected>Lớn</option>";
				}
				html += "</select></td><td><select class='form-control status' name='status'>";
				if (value.status == 1) {
					html += "<option value='1' selected>Còn hàng</option><option value='2'>Hết hàng</option>";
				}
				else {
					html += "<option value='1'>Còn hàng</option><option value='2' selected>Hết hàng</option>";
				}
				html += "</select></td><td><a class='delete' title='Delete'><i class='fas fa-trash'></i></a></td></tr>";
			});
			$("#table-detail").append(html);
		});
	});

	//Button delete
	$('body').on('click', '.order-delete', function() {
		var index = $(this).attr("data-index");
		var idx;
		$(".order-index").each(function(i) {
			idx = i + 1;
			if (i >= index) {
				idx--;
			}
			$(this).text(idx);
		});


		var id = $(this).attr("data");
		$.ajax({
			url: 'http://localhost:9090/mySuFood/admin/order/delete',
			type: 'GET',
			contentType: 'application/json',
			data: {
				id: id
			}
		}).done(function() {
		});
	});

	$('body').on('click', '#add-user', function() {
		$.ajax({
			url: 'http://localhost:9090/mySuFood/admin/user/create',
			type: 'GET',
			contentType: 'application/json',
			data: {
				id: $(this).attr("data-flag"),
				fullname: $("input[name=fullname]").val(),
				email: $("input[name=email]").val(),
				password: $("input[name=password]").val(),
				phone: $("input[name=phone]").val(),
				address: $("input[name=address]").val(),
				ward: $("select[name=ward]").val(),
				role: $("select[name=role]").val()
			}
		}).done(function(value) {
			if (value != "ok") {
				$(".user-error").text(value);
			} else {
				window.location = "http://localhost:9090/mySuFood/admin/user/list";
			}
		});
	});

	//Button edit
	$('body').on('click', '.user-edit', function() {
		var id = $(this).attr("data");
		$.ajax({
			url: 'http://localhost:9090/mySuFood/admin/user/info',
			type: 'GET',
			contentType: 'application/json',
			data: {
				id: id
			}
		}).done(function(user) {
			// css + html
			$("#table-detail").find(".item-detail").remove();
			$("#exampleModalLabel").text("Cập nhật thông tin tài khoản");//Thay đổi text từ "Thêm sản phẩm"->"Cập nhật sản phẩm"
			$("#add-user").text("Cập nhật");
			$("#add-user").attr("data-flag", id);
			$("input[name=password]").parent().remove();
			$(".user-pass").remove();
			$("select[name=city]").parent().remove();
			$(".user-city").remove();
			$("select[name=district]").parent().remove();
			$(".user-district").remove();
			$("select[name=ward]").parent().remove();
			$(".user-ward").remove();
			
			// chưa set quyen hien thi
			
			// write data
			$("input[name=fullname]").val(user.fullname);
			$("input[name=email]").val(user.userName);
			$("input[name=phone]").val(user.phone);
			$("input[name=address]").val(user.address);
			$("select[name=role]").val(user.role.id);
		});
	});
	
	
	//Button delete
	$('body').on('click', '.user-delete', function() {
		var index = $(this).attr("data-index");
		var idx;
		$(".user-index").each(function(i) {
			idx = i + 1;
			if (i >= index) {
				idx--;
			}
			$(this).text(idx);
		});


		var id = $(this).attr("data");
		$.ajax({
			url: 'http://localhost:9090/mySuFood/admin/user/delete',
			type: 'GET',
			contentType: 'application/json',
			data: {
				id: id
			}
		}).done(function() {
		});
	});
	
	$(document).on("click", ".input-group-append button", function() {
		var page;
		var url = window.location.href;
		var keyword = $(this).parent().parent().find("input").val();
		if(keyword != ""){
			if(url.search("product") > 0){
				page = 1;
			}else if(url.search("user") > 0){
				page = 2;
			}else{
				page = 3;
			}
			
			$.ajax({
				url: 'http://localhost:9090/mySuFood/admin/search',
				type: 'GET',
				contentType: 'application/json',
				data: {
					keyword: keyword, 
					page: page
				}
			}).done(function(value) {
				
			});
		}
	});
});

