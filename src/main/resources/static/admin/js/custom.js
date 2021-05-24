$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
    var actions = $("table td:last-child").html();
    // Append table with add row form on add new button click
    $(".add-new").click(function () {
        $(this).attr("disabled", "disabled");
        var index = $("table tbody tr:last-child").index();
        var row = '<tr>' +
            '<td><input type="text" class="form-control" name="name" id="name"></td>' +
            '<td><input type="text" class="form-control" name="department" id="department"></td>' +
            '<td><input type="text" class="form-control" name="phone" id="phone"></td>' +
            '<td>' + actions + '</td>' +
            '</tr>';
        $("table").append(row);
        $("table tbody tr").eq(index + 1).find(".add, .edit").toggle();
        $('[data-toggle="tooltip"]').tooltip();
    });
    // Add row on add button click
    $(document).on("click", ".add", function () {
        var empty = false;
        var input = $(this).parents("tr").find('input[type="text"]');
        input.each(function () {
            if (!$(this).val()) {
                $(this).addClass("error");
                empty = true;
            } else {
                $(this).removeClass("error");
            }
        });
        $(this).parents("tr").find(".error").first().focus();
        if (!empty) {
            input.each(function () {
                $(this).parent("td").html($(this).val());
            });
            $(this).parents("tr").find(".add, .edit").toggle();
            $(".add-new").removeAttr("disabled");
        }
    });
    // Edit row on edit button click
    $(document).on("click", ".edit", function () {
        $(this).parents("tr").find("td:not(:last-child)").each(function () {
            $(this).html('<input type="text" class="form-control" value="' + $(this).text() + '">');
        });
        $(this).parents("tr").find(".add, .edit").toggle();
        $(".add-new").attr("disabled", "disabled");
    });

    // Delete row on delete button click
    $(document).on("click", ".delete", function () {
        $(this).parents("tr").remove();
        $(".add-new").removeAttr("disabled");
    });

    $('body').on('click', '#add-detail', function () {
        $("#table-detail").append("<tr class='item-detail' data-detail='0'><td><select class='form-control size' name='size'><option value='1'>Nhỏ</option><option value='2'>Vừa</option><option value='3'>Lớn</option></select></td><td><select class='form-control status' name='status'><option value='1'>Còn hàng</option><option value='2'>Hết hàng</option></select></td><td><a class='delete' title='Delete'><i class='fas fa-trash'></i></a></td></tr>");
    });

    $('body').on('click', '#add-product', function () {
        const id = $(this).attr("data-flag");
        const name = $("input[name=name]").val();
        const price = $("input[name=price]").val();
        const description = $("textarea[name=description]").val();
        const image = $("input[name=image]").val().replace('C:\\fakepath\\', '');
        const product = {};
        const productDetails = [];
        $(".item-detail").each(function () {
            const productDetail = {};
            let flag = true;
            productDetail["id"] = $(this).attr('data-detail');
            productDetail["size"] = $(this).find('select.size').val();
            productDetail["status"] = $(this).find('select.status').val();
            if (productDetails.length === 0) {
                productDetails.push(productDetail);
            } else {
                $.each(productDetails, function (key, value) {
                    if (productDetail.size === value.size) {
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
        if (image !== "") {
            product["image"] = image;
        } else {
            product["image"] = $(".image-name").text();
        }
        product["productDetail"] = productDetails;

        const form = new FormData($('#fileUploadForm')[0]);
        const keyword = $(".navbar-search").find("input").val();
        const page = $("ul.pagination").find("li.active").attr("data");

        if (validateProduct(product)) {
            $.ajax({
                url: 'http://localhost:9090/mySuFood/admin/product/create',
                type: 'POST',
                contentType: 'application/json',
                enctype: 'multipart/form-data',
                data: JSON.stringify(product)
            }).done(function (value) {
                console.log(value);
                if (value) {
                    if (image !== "") {
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
                    closeDialog();
                    if (id === "0") {
                        modalConfirm("Thêm mới thành công sản phẩm");
                        delay("http://localhost:9090/mySuFood/admin/product/list");
                    } else {
                        modalConfirm("Cập nhật thành công sản phẩm");
                        $.ajax({
                            url: 'http://localhost:9090/mySuFood/admin/product/list-ajax',
                            type: 'GET',
                            contentType: 'application/json',
                            data: {
                                keyword: keyword,
                                page: page
                            }
                        }).done(function (list) {
                            loadListProduct(list);
                            pagination(list.pagination);
                        });
                    }
                }
            });
        }
    });

    //Button delete
    $('body').on('click', '.product-delete', function () {
        let idx;
        const id = $(this).attr("data");
        const keyword = $(".navbar-search").find("input").val();
        let page = $("li.page-item.active").find("a").text();

        if ($("td.user-index").length === 1) {
            page = page - 1;
        }

        $.ajax({
            url: 'http://localhost:9090/mySuFood/admin/product/delete',
            type: 'GET',
            contentType: 'application/json',
            data: {
                id: id
            }
        }).done(function () {
            closeDialog();
            modalConfirm("Xoá thành công sản phẩm");

            $.ajax({
                url: 'http://localhost:9090/mySuFood/admin/product/list-ajax',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    keyword: keyword,
                    page: page
                }
            }).done(function (list) {
                loadListProduct(list);
                pagination(list.pagination);
            });

            removeElement(id);
        });
    });

    //Button edit
    $('body').on('change', 'input[name=image]', function () {
        $(".image-name").css("display", "none").text("");
    });

    //Button edit
    $('body').on('click', '#button-add-product', function () {
        changeFormProduct(null);
    });

    //Button edit
    $('body').on('click', '.product-edit', function () {
        $.ajax({
            url: 'http://localhost:9090/mySuFood/admin/product/info',
            type: 'GET',
            contentType: 'application/json',
            data: {
                id: $(this).attr("data")
            }
        }).done(function (product) {
            changeFormProduct(product);
        });
    });


    $('body').on('click', '.dialog-order-edit', function () {
        const id = $(this).attr("data");
        const status = $(this).attr("data-status");
        const boxButton = $(".box-button");
        boxButton.find("button").attr("data", id).attr("style","display:inline-block;");
        boxButton.find("[data-status='" + status + "']").attr("style","display:none;");
    });

    // button edit
    $('body').on('click', '.order-edit', function () {
        const id = $(this).attr("data");
        const status = $(this).attr("data-status");
        const keyword = $(".navbar-search").find("input").val();
        let page = $("li.page-item.active").find("a").text();
        $.ajax({
            url: 'http://localhost:9090/mySuFood/admin/order/update',
            type: 'GET',
            contentType: 'application/json',
            data: {
                id: id,
                status: status
            }
        }).done(function () {
            closeDialog();
            modalConfirm("Cập nhật thành công trạng thái đơn hàng");
            $.ajax({
                url: 'http://localhost:9090/mySuFood/admin/order/list-ajax',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    keyword: keyword,
                    page: page
                }
            }).done(function (list) {
                loadListOrder(list);
                pagination(list.pagination);
            });
            removeElement(id);
        });
    });

    //Button delete
    $('body').on('click', '.order-delete', function () {
        const id = $(this).attr("data");
        const keyword = $(".navbar-search").find("input").val();
        let page = $("li.page-item.active").find("a").text();
        if ($("td.user-index").length === 1) {
            page = page - 1;
        }
        $.ajax({
            url: 'http://localhost:9090/mySuFood/admin/order/delete',
            type: 'GET',
            contentType: 'application/json',
            data: {
                id: id
            }
        }).done(function () {
            closeDialog();
            modalConfirm("Xoá thành công đơn hàng");
            $.ajax({
                url: 'http://localhost:9090/mySuFood/admin/order/list-ajax',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    keyword: keyword,
                    page: page
                }
            }).done(function (list) {
                loadListOrder(list);
                pagination(list.pagination);
            });
            removeElement(id);
        });
    });

    $('body').on('click', '#add-user', function () {
        const user = {};
        const id = $(this).attr("data-flag");
        const fullname = $("input[name=fullname]").val();
        const email = $("input[name=email]").val();
        const password = $("input[name=password]").val();
        const phone = $("input[name=phone]").val();
        const address = $("input[name=address]").val();
        const ward = $("select[name=ward]").val();
        const role = $("select[name=role]").val();

        user["id"] = id;
        user["fullname"] = fullname;
        user["email"] = email;
        user["password"] = password;
        user["phone"] = phone;
        user["address"] = address;
        user["ward"] = ward;
        user["role"] = role;

        const keyword = $(".navbar-search").find("input").val();
        const page = $("ul.pagination").find("li.active").attr("data");
        if (validateRegist(user)) {
            $.ajax({
                url: 'http://localhost:9090/mySuFood/admin/user/create',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(user)
            }).done(function (value) {
                if (value === "") {
                    closeDialog();
                    if (id === "0") {
                        modalConfirm("Đăng ký thành công tài khoản");
                        delay("http://localhost:9090/mySuFood/admin/user/list");
                    } else {
                        modalConfirm("Cập nhật thành công tài khoản");
                        $.ajax({
                            url: 'http://localhost:9090/mySuFood/admin/user/list-ajax',
                            type: 'GET',
                            contentType: 'application/json',
                            data: {
                                keyword: keyword,
                                page: page
                            }
                        }).done(function (list) {
                            loadListuser(list);
                            pagination(list.pagination);
                        });
                    }
                } else {
                    $(".user-error").text(value);
                }
            });
        }
    });

    //Button add
    $('body').on('click', '#button-add-user', function () {
        changeFormUser(null);
    });

    //Button edit
    $('body').on('click', '.user-edit', function () {
        removeMessageForm();
        $.ajax({
            url: 'http://localhost:9090/mySuFood/admin/user/info',
            type: 'GET',
            contentType: 'application/json',
            data: {
                id: $(this).attr("data")
            }
        }).done(function (user) {
            changeFormUser(user);
        });
    });

    $('body').on('click', '.dialog-delete', function () {
        const id = $(this).attr("data");
        const index = $(this).attr("data-index");
        $(".user-delete").attr("data", id).attr("data-index", index);
        $(".product-delete").attr("data", id).attr("data-index", index);
    });

    //Button delete
    $('body').on('click', '.user-delete', function () {
        const id = $(this).attr("data");
        const keyword = $(".navbar-search").find("input").val();
        let page = $("li.page-item.active").find("a").text();
        if ($("td.user-index").length === 1) {
            page = page - 1;
        }
        $.ajax({
            url: 'http://localhost:9090/mySuFood/admin/user/delete',
            type: 'GET',
            contentType: 'application/json',
            data: {
                id: id
            }
        }).done(function () {
            closeDialog();
            modalConfirm("Xoá thành công tài khoản");

            $.ajax({
                url: 'http://localhost:9090/mySuFood/admin/user/list-ajax',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    keyword: keyword,
                    page: page
                }
            }).done(function (list) {
                loadListuser(list);
                pagination(list.pagination);
            });

            removeElement(id);
        });
    });

    $(document).on("click", ".input-group-append button", function () {
        const url = window.location.href;
        const keyword = $(this).parent().parent().find("input").val();
        if (keyword !== "") {
            if (url.search("product") > 0) {
                $.ajax({
                    url: 'http://localhost:9090/mySuFood/admin/product/list-ajax',
                    type: 'GET',
                    contentType: 'application/json',
                    data: {
                        keyword: keyword,
                        page: 1
                    }
                }).done(function (list) {
                    loadListProduct(list);
                    pagination(list.pagination);
                });
            } else if (url.search("user") > 0) {
                $.ajax({
                    url: 'http://localhost:9090/mySuFood/admin/user/list-ajax',
                    type: 'GET',
                    contentType: 'application/json',
                    data: {
                        keyword: keyword,
                        page: 1
                    }
                }).done(function (list) {
                    loadListuser(list);
                    pagination(list.pagination);
                });
            } else {
                $.ajax({
                    url: 'http://localhost:9090/mySuFood/admin/order/list-ajax',
                    type: 'GET',
                    contentType: 'application/json',
                    data: {
                        keyword: keyword,
                        page: 1
                    }
                }).done(function (list) {
                    loadListOrder(list);
                    pagination(list.pagination);
                });
            }
        }
    });

    $(document).on("click", ".page-item", function () {
        $(".page-item").removeClass("active");
        $(this).addClass("active");
        const page = $(this).attr("data");
        const url = window.location.href;
        const keyword = $(".navbar-search").find("input").val();

        if (url.search("product") > 0) {
            $.ajax({
                url: 'http://localhost:9090/mySuFood/admin/product/list-ajax',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    keyword: keyword,
                    page: page
                }
            }).done(function (value) {
                loadListProduct(value);
            });
        } else if (url.search("user") > 0) {
            $.ajax({
                url: 'http://localhost:9090/mySuFood/admin/user/list-ajax',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    keyword: keyword,
                    page: page
                }
            }).done(function (value) {
                loadListuser(value);
            });
        } else {
            $.ajax({
                url: 'http://localhost:9090/mySuFood/admin/order/list-ajax',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    keyword: keyword,
                    page: page
                }
            }).done(function (value) {
                loadListOrder(value);
            });
        }
    });

    function loadListuser(value) {
        let html = "";
        let index = 0;
        let i = 0;
        const pageSize = value.pagination.page === 1 ? 0 : (value.pagination.page - 1) * value.pagination.pageSize;
        $.each(value.users, function (key, value) {
            i++;
            index = i + pageSize;
            html += "<tr>";
            html += "<td class='text-center user-index'>" + index + "</td>";
            html += "<td>" + value.fullname + "</td>";
            html += "<td>" + value.userName + "</td>";
            html += "<td>" + value.phone + "</td>";
            html += "<td>" + value.createDate + "</td>";
            html += "<td>" + value.role.name + "</td>";
            html += "<td>" + value.address + "</td>";
            html += "<td>";
            html += "<a class='user-edit' data='" + value.id + "' title='Edit' style='color: orange;' data-toggle='modal' data-target='#exampleModal'><i class='fas fa-pen'></i></a>";
            html += "<a class='dialog-delete' title='Delete' data-toggle='modal' data-target='#myModal' style='color: red;' data='" + value.id + "' data-index='" + index + "'><i class='fas fa-trash'></i></a>";
            html += "</td></tr>";
        });
        $(".user-list").empty().append(html);
    }

    function loadListProduct(value) {
        let html = "";
        let index = 0;
        let i = 0;
        const pageSize = value.pagination.page === 1 ? 0 : (value.pagination.page - 1) * value.pagination.pageSize;
        $.each(value.products, function (key, value) {
            i++;
            index = i + pageSize;
            html += "<tr>";
            html += "<td class='text-center product-index'>" + index + "</td>";
            html += "<td><img src='/mySuFood" + value.image + "' style='width: 100%; height: 100px;'></td>";
            html += "<td>" + value.name + "</td>";
            html += "<td>" + value.priceStr + "</td>";
            html += "<td>";
            $.each(value.productDetail, function (key, value1) {
                html += "<p class='style-detail'>" + value1.sizeName + "</p>";
            });
            html += "</td>";
            html += "<td>";
            $.each(value.productDetail, function (key, value1) {
                html += "<p class='style-detail'>" + value1.statusName + "</p>";
            });
            html += "</td>";
            html += "<td>" + value.description + "</td>";
            html += "<td>";
            html += "<a class='product-edit' data='" + value.id + "' title='Edit' style='color: orange;' data-toggle='modal' data-target='#exampleModal'><i class='fas fa-pen'></i></a>";
            html += "<a class='dialog-delete' title='Delete' data-toggle='modal' data-target='#myModal' style='color: red;' data='" + value.id + "' data-index='" + index + "'><i class='fas fa-trash'></i></a>";
            html += "</td></tr>";
        });
        $(".product-list").empty().append(html);
    }

    function loadListOrder(value) {
        let html = "";
        let index = 0;
        let i = 0;
        const pageSize = value.pagination.page === 1 ? 0 : (value.pagination.page - 1) * value.pagination.pageSize;
        $.each(value.orders, function (key, value) {
            i++;
            index = i + pageSize;
            html += "<tr>";
            html += "<td class='text-center order-index'>" + index + "</td>";
            html += "<td>" + value.fullname + "</td>";
            html += "<td>" + value.email + "</td>";
            html += "<td>" + value.phone + "</td>";
            html += "<td>" + value.address + "</td>";
            html += "<td>";
            $.each(value.orderDetails, function (key, value1) {
                html += "<p class='style-detail'>" + value1.productName + "</p>";
            });
            html += "</td>";
            html += "<td>";
            $.each(value.orderDetails, function (key, value1) {
                html += "<p class='style-detail'>" + value1.sizeName + "</p>";
            });
            html += "</td>";
            html += "<td>";
            $.each(value.orderDetails, function (key, value1) {
                html += "<p class='style-detail'>" + value1.quantity + "</p>";
            });
            html += "</td>";
            html += "<td>" + value.statusName + "</td>";
            html += "<td>";
            html += "<a class='dialog-order-edit' title='Edit' data='" + value.id + "' data-status='" + value.status + "' style='color: orange;' data-toggle='modal' data-target='#exampleModal'> <i class='fas fa-pen' style='font-size: 14px;'></i> </a>";
            html += "<a class='dialog-delete' title='Delete' data-toggle='modal' data-target='#myModal' style='color: red;' data='" + value.id + "' data-index='" + index + "'><i class='fas fa-trash' style='font-size: 14px;'></i></a>";
            html += "</td></tr>";
        });
        $(".order-list").empty().append(html);
    }

    function pagination(pagination) {
        let html = "";
        $.each(pagination.totalPage, function (key, value) {
            html += "<li data='" + value + "' class='page-item";
            if (value === pagination.page) {
                html += " active";
            }
            html += "'>";
            html += "<a class='page-link' href='javascript:void(0)'>" + value + "</a>";
            html += "</li>";
        });
        $("ul.pagination").empty().append(html);
    }

    function validateRegist(user) {
        const regexEmail = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        const regexPhone = /^(84|0[3|5|7|8|9])+([0-9]{8})\b/;

        const fullname = $("span.fullname");
        const labelFullName = $("span.label-fullname");
        if (user.fullname === "") {
            fullname.text("Vui lòng nhập họ và tên");
            labelFullName.text("1")
            fullname.addClass("fail");
        } else {
            fullname.empty();
            labelFullName.empty();
            fullname.removeClass("fail");
        }

        const email = $("span.email");
        const labelEmail = $("span.label-email");
        if (user.email === "") {
            email.text("Vui lòng nhập email");
            labelEmail.text("1")
            email.addClass("fail");
        } else if (!regexEmail.test(user.email)) {
            email.text("Email không đúng định dạng");
            labelEmail.text("1")
            email.addClass("fail");
        } else {
            email.empty();
            labelEmail.empty();
            email.removeClass("fail");
        }

        const phone = $("span.phone");
        const labelPhone = $("span.label-phone");
        if (user.phone === "") {
            phone.text("Vui lòng nhập số điện thoại");
            labelPhone.text("1");
            phone.addClass("fail");
        } else if (!regexPhone.test(user.phone)) {
            phone.text("Số điện thoại không đúng định dạng");
            labelPhone.text("1");
            phone.addClass("fail");
        } else {
            phone.empty();
            labelPhone.empty();
            phone.removeClass("fail");
        }

        const password = $("span.password");
        const labelPassword = $("span.label-password");
        if (user.password === "") {
            password.text("Vui lòng nhập mật khẩu");
            labelPassword.text("1");
            password.addClass("fail");
        } else {
            password.empty();
            labelPassword.empty();
            password.removeClass("fail");
        }

        const role = $("span.role");
        if (user.role === "0") {
            role.text("Vui lòng chọn quyền cho tài khoản");
            role.addClass("fail");
        } else {
            role.empty();
            role.removeClass("fail");
        }

        return !$("span").hasClass("fail");
    }

    function validateProduct(product) {
        const image = $("span.image");
        const labelImage = $("span.label-image");
        if (product.image === "") {
            image.text("Vui lòng chọn ảnh cho sản phẩm").addClass("fail");
            labelImage.text("1")
        } else {
            image.empty().removeClass("fail");
            labelImage.empty();
        }

        const name = $("span.name");
        const labelName = $("span.label-name");
        if (product.name === "") {
            name.text("Vui lòng nhập tên sản phẩm").addClass("fail");
            labelName.text("1")
        } else {
            name.empty().removeClass("fail");
            labelName.empty();
        }

        const regNumber = /^\d+$/;
        const price = $("span.price");
        const labelPrice = $("span.label-price");
        if (product.price === "") {
            price.text("Vui lòng nhập tên giá sản phẩm").addClass("fail");
            labelPrice.text("1")
        } else if (!regNumber.test(product.price)) {
            price.text("Giá tiền phải nhập số nguyên").addClass("fail");
            labelPrice.text("1")
        } else {
            price.empty().removeClass("fail");
            labelPrice.empty();
        }

        const detail = $("span.detail");
        if (product.productDetail.length === 0) {
            detail.text("Vui lòng thêm chi tiết cho sản phẩm").addClass("fail");
        } else {
            detail.empty().removeClass("fail");
        }

        return !$("span").hasClass("fail");
    }

    function removeElement(id, key) {
        $("tr").find("[data='" + id + "']").closest("tr").remove();
        if (key === "product") {
            $(".product-index").each(function (i) {
                let index = i + 1;
                $(this).text(index);
                $(this).parent().find("a.dialog-delete").attr("data-index", index);
            });
        } else if (key === "user") {
            $(".user-index").each(function (i) {
                let index = i + 1;
                $(this).text(index);
                $(this).parent().find("a.dialog-delete").attr("data-index", index);
            });
        } else {
            $(".order-index").each(function (i) {
                let index = i + 1;
                $(this).text(index);
                $(this).parent().find("a.dialog-delete").attr("data-index", index);
            });
        }
    }

    function closeDialog() {
        $('#myModal').modal('hide');
        $('#exampleModal').modal('hide');
    }

    function modalConfirm(value) {
        $('#myModalConfirm').modal('show');
        $(".content-dialog").empty().html(value);
    }

    function removeMessageForm() {
        $("span").removeClass("fail");
        $("span.fullname").empty();
        $("span.label-fullname").empty();
        $("span.email").empty();
        $("span.label-email").empty();
        $("span.phone").empty();
        $("span.label-phone").empty();
        $("span.password").empty();
        $("span.label-password").empty();
        $("span.role").empty();
        $("span.name").empty();
        $("span.label-name").empty();
        $("span.image").empty();
        $("span.label-image").empty();
        $("span.price").empty();
        $("span.detail").empty();
        $("span.label-price").empty();
    }

    function changeFormUser(user) {
        removeMessageForm();
        if (user === null) {
            $.ajax({
                url: 'http://localhost:9090/mySuFood/province/city',
                type: 'GET',
                contentType: 'application/json'
            }).done(function (cities) {
                let html = "<div class='col-12 user-error'></div>";
                html += "<div class='col-3'>";
                html += "<label class='style-label' for='fullname'>Họ và tên <span class='required'>*</span></label>";
                html += "<span class='error-message label-fullname'></span>";
                html += "<label class='style-label' for='email'>Email <span class='required'>*</span></label>";
                html += "<span class='error-message label-email'></span>";
                html += "<label class='style-label user-pass' for='password'>Mật khẩu <span class='required'>*</span></label>";
                html += "<span class='error-message label-password'></span>";
                html += "<label class='style-label' for='phone'>Số diện thoại <span class='required'>*</span></label>";
                html += "<span class='error-message label-phone'></span>";
                html += "<label class='style-label user-city'>Tỉnh/Tp </label>";
                html += "<label class='style-label user-district'>Quận/Huyện </label>";
                html += "<label class='style-label user-ward'>Phường/Xã </label>";
                html += "<label class='style-label' for='address'>Địa chỉ </label>";
                html += "<label class='style-label'>Quyền <span class='required'>*</span></label>";
                html += "</div>";
                html += "<div class='col-9'>";
                html += "<div class='form-group'>";
                html += "<input type='text' class='form-control' name='fullname' id='fullname' placeholder='Họ và tên'/>";
                html += "<span class='error-message fullname'></span>";
                html += "</div>";
                html += "<div class='form-group'>";
                html += "<input type='text' class='form-control' name='email' id='email' placeholder='Email'/>";
                html += "<span class='error-message email'></span>";
                html += "</div>";
                html += "<div class='form-group'>";
                html += "<input type='password' class='form-control' name='password' id='password' placeholder='Mật khẩu'/>";
                html += "<span class='error-message password'></span>";
                html += "</div>";
                html += "<div class='form-group'>";
                html += "<input type='text' class='form-control' name='phone' id='phone' placeholder='Số điện thoại'/>";
                html += "<span class='error-message phone'></span>";
                html += "</div>";
                html += "<div class='form-group'>";
                html += "<select name='city' id='pay-city' class='form-control'>";
                html += "<option value='-1' class='required_form'>Chọn Tỉnh/Tp</option>";
                $.each(cities, function (key, value) {
                    html += "<option value='" + value.cityId + "'>" + value.cityName + "</option>";
                });
                html += "</select>";
                html += "</div>";
                html += "<div class='form-group'>";
                html += "<select name='district' id='pay-district' data-city='0' class='form-control'>";
                html += "<option value='-1'>Chọn Quận/Huyện</option>";
                html += "</select>";
                html += "</div>";
                html += "<div class='form-group'>";
                html += "<select name='ward' id='pay-ward' data-district='0' class='form-control'>";
                html += "<option value='-1'>Chọn Phường/Xã</option>";
                html += "</select>";
                html += "</div>";
                html += "<div class='form-group'>";
                html += "<input type='text' class='form-control' name='address' id='address' placeholder='Địa chỉ'/>";
                html += "</div>";
                html += "<div class='form-group'>";
                html += "<select name='role' class='form-control'>";
                html += "<option value='0'>Chọn quyền</option>";
                html += "<option value='1'>Quản trị viên</option>";
                html += "<option value='2'>Khách hàng</option>";
                html += "</select>";
                html += "<span class='error-message role'></span>";
                html += "</div>";
                html += "</div>";
                $("#exampleModalLabel").text("Đăng ký thông tin tài khoản");//Thay đổi text từ "Thêm sản phẩm"->"Cập nhật sản phẩm"
                $("#add-user").text("Thêm mới").attr("data-flag", 0);
                $("#fileUploadForm").empty().html(html);
            });
        } else {
            // css + html
            $("#table-detail").find(".item-detail").remove();
            $("#exampleModalLabel").text("Cập nhật thông tin tài khoản");//Thay đổi text từ "Thêm sản phẩm"->"Cập nhật sản phẩm"
            $("#add-user").text("Cập nhật").attr("data-flag", user.id);
            $("input[name=password]").parent().remove();
            $(".user-pass").remove();
            $("select[name=city]").parent().remove();
            $(".user-city").remove();
            $("select[name=district]").parent().remove();
            $(".user-district").remove();
            $("select[name=ward]").parent().remove();
            $(".user-ward").remove();
            // write data
            $("input[name=fullname]").val(user.fullname);
            $("input[name=email]").val(user.userName);
            $("input[name=phone]").val(user.phone);
            $("input[name=address]").val(user.address);
            $("select[name=role]").val(user.role.id);
        }
    }

    function changeFormProduct(product) {
        removeMessageForm();
        if (product === null) {
            $("#exampleModalLabel").text("Thêm mới sản phẩm");
            $("#add-product").text("Thêm mới").attr("data-flag", 0);
            $("input[name=image]").val("");
            $("input[name=name]").val("");
            $("input[name=price]").val("");
            $("textarea[name=description]").val("");
            $(".image-name").css("display", "none").text("");

            let html = "<tr class='item-detail' data-flag='0'>";
            html += "<td>";
            html += "<select class='form-control size' name='size'>";
            html += "<option value='1'>Nhỏ</option>";
            html += "<option value='2'>Vừa</option>";
            html += "<option value='3'>Lớn</option>";
            html += "</select>";
            html += "</td>";
            html += "<td>";
            html += "<select class='form-control status' name='status'>";
            html += "<option value='1'>Còn hàng</option>";
            html += "<option value='2'>Hết hàng</option>";
            html += "</select>";
            html += "</td>";
            html += "<td>";
            html += "<a class='delete' title='Delete'> <i class='fas fa-trash'></i></a>";
            html += "</td>";
            html += "</tr>";
            $("#table-detail").empty().html(html);
        } else {
            // css + html
            $("#exampleModalLabel").text("Cập nhật sản phẩm");//Thay đổi text từ "Thêm sản phẩm"->"Cập nhật sản phẩm"
            $("#add-product").text("Cập nhật").attr("data-flag", product.id);
            // write data
            $("input[name=name]").val(product.name);
            $("input[name=price]").val(product.price);
            $("textarea[name=description]").val(product.description);
            $(".image-name").css("display", "block").text(product.image);

            let html = "";
            $.each(product.productDetail, function (key, value) {
                html += "<tr class='item-detail' data-detail='" + value.id + "' data-flag='0'><td><select class='form-control size' name='size'>";
                if (value.size === 1) {
                    html += "<option value='1' selected>Nhỏ</option><option value='2'>Vừa</option><option value='3'>Lớn</option>";
                } else if (value.size === 2) {
                    html += "<option value='1'>Nhỏ</option><option value='2' selected>Vừa</option><option value='3'>Lớn</option>";
                } else {
                    html += "<option value='1'>Nhỏ</option><option value='2'>Vừa</option><option value='3' selected>Lớn</option>";
                }
                html += "</select></td><td><select class='form-control status' name='status'>";
                if (value.status === 1) {
                    html += "<option value='1' selected>Còn hàng</option><option value='2'>Hết hàng</option>";
                } else {
                    html += "<option value='1'>Còn hàng</option><option value='2' selected>Hết hàng</option>";
                }
                html += "</select></td><td><a class='delete' title='Delete'><i class='fas fa-trash'></i></a></td></tr>";
            });
            $("#table-detail").empty().append(html);
        }
    }

    function delay(url) {
        setTimeout(
            function () {
                window.location = url;
            }, 1500);
    }
});

