$(document).ready(function () {
    $(".size-css-enable").click(function () {
        $(this).parent().find(".size-css-enable").removeClass("size-css-active");

        $(this).addClass("size-css-active");

        var detail = $(this).attr("data");
        var url = $(this).closest("div.change-url").find("#add-cart").attr("data-url");
        url = url + "&detail=" + detail;
        $(this).closest("div.change-url").find("#add-cart").attr("href", url);
        $(this).closest("div.change-url").find("#add-cart").attr("href", url);
        $(this).closest("div.change-url").find("#add-cart").attr("class", "add-cart");
    });

    $(document).on("click", ".no-add-cart", function () {
        alert("Vui lòng chọn kích thước sản phẩm");
    });

    $('#pay-city').on('change', function () {
        if (this.value > 0) {
            $('#pay-district').attr("data-city", this.value);
            $.ajax({
                url: 'http://localhost:9090/mySuFood/province/district',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    cityId: this.value
                }
            }).done(function (value) {
                if (value != null) {
                    var html = "<option value='-1'>Chọn Quận/Huyện</option>";
                    $.each(value, function (key, value) {
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

    $('#pay-district').on('change', function () {
        if (this.value > 0) {
            $.ajax({
                url: 'http://localhost:9090/mySuFood/province/ward',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    cityId: $(this).attr("data-city"),
                    districtId: this.value
                }
            }).done(function (value) {
                if (value != null) {
                    var html = "<option value='-1'>Chọn Phường/Xã</option>";
                    $.each(value, function (key, value) {
                        html += "<option value='" + value.wardId + "'>" + value.wardName + "</option>";
                    });
                    $('#pay-ward').html(html);
                }
            });
        } else {
            $('#pay-ward').html("<option value='-1'>Chọn Phường/Xã</option>");
        }
    });


    $(document).on("click", "#redirect-pay", function () {
        var user = {};
        user["email"] = $("input[name=email]").val();
        user["name"] = $("input[name=name]").val();
        user["phone"] = $("input[name=phone]").val();
        user["ward"] = $("select[name=ward]").val();
        user["address"] = $("input[name=address]").val();
        localStorage.setItem("user", JSON.stringify(user));

        window.location.href = "http://localhost:9090/mySuFood/pay/type";
    });

    $(document).on("change", "input[name=payment_method]", function () {
        var key = $(this).val();
        console.log(key);
        if (key != 2) {
            $("button.but_step2").attr("data-toggle", "modal");
            $("button.but_step2").attr("data-target", "#exampleModal");
            $("button.but_step2").attr("id", "but_step_confirm");
        } else {
            $("button.but_step2").removeAttr("data-toggle");
            $("button.but_step2").removeAttr("data-target");
            $("button.but_step2").attr("id", "but_step2");
        }
    });


    $(document).on("click", "#but_step2", function () {
        var type = $("input[name=payment_method]:checked").val();
        var user = JSON.parse(localStorage.getItem("user"));
        var amount = $(".payment-due-price").text();
        if (type == 2) {
            $.ajax({
                url: 'http://localhost:9090/mySuFood/pay/momo/get-url',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    amount: amount.replace(".", "").replace(" ₫", ""),
                    email: user.email,
                    name: user.name,
                    phone: user.phone,
                    ward: user.ward,
                    address: user.address,
                    payType: type
                }
            }).done(function (value) {
                window.location.href = value;
            });
        }
    });


    $(document).on("click", "#but_step_confirm", function () {
        var user = JSON.parse(localStorage.getItem("user"));
        $.ajax({
            url: 'http://localhost:9090/mySuFood/pay/confirm',
            type: 'GET',
            contentType: 'application/json',
            data: {
                email: user.email,
                fullName: user.name
            }
        });
    });

    $(document).on("click", "#pay-confirm", function () {
        var user = JSON.parse(localStorage.getItem("user"));
        var type = $("input[name=payment_method]:checked").val();
        var code = $("input[name=code]").val();

        $.ajax({
            url: 'http://localhost:9090/mySuFood/pay/success',
            type: 'GET',
            contentType: 'application/json',
            data: {
                email: user.email,
                name: user.name,
                phone: user.phone,
                ward: user.ward,
                address: user.address,
                payType: type,
                code: code
            }
        }).done(function (value) {
            if (value) {
                localStorage.removeItem("user");
                window.location.href = "http://localhost:9090/mySuFood/pay/pay-success";
            } else {
                alert("Mã xác thực không chính xác");
            }
        });
    });

    // button đăng ký
    $(document).on("click", "#register", function () {
        const user = {};
        const fullname = $("input[name=fullname]").val();
        const email = $("input[name=email]").val();
        const password = $("input[name=password]").val();
        const rePassword = $("input[name=rePassword]").val();
        const phone = $("input[name=phone]").val();
        const address = $("input[name=address]").val();
        const ward = $("select[name=ward]").val();

        user["fullname"] = fullname;
        user["email"] = email;
        user["password"] = password;
        user["rePassword"] = rePassword;
        user["phone"] = phone;
        user["address"] = address;
        user["ward"] = ward;
        user["role"] = 2;
        user["userRegist"] = true;

        // validate
        if (validateRegist(user)) {
            $.ajax({
                url: 'http://localhost:9090/mySuFood/dang-ky',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(user)
            }).done(function (value) {
                console.log(value);
                if (value === "") {
                    alert("Bạn đã đăng ký thành công tài khoản. \n Vui lòng kiểm tra mail để xác nhận.");
                    window.location.href = "http://localhost:9090/mySuFood/dang-nhap";
                } else {
                    $(".error-server").text(value);
                }
            });
        }
    });

    function validateRegist(user) {
        const regexEmail = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        const regexPhone = /^(84|0[3|5|7|8|9])+([0-9]{8})\b/;
        const flag = false;

        const fullname = $("span.fullname");
        if (user.fullname === "") {
            fullname.text("Vui lòng nhập họ và tên");
            fullname.addClass("fail");
        } else {
            fullname.empty();
            fullname.removeClass("fail");
        }

        const email = $("span.email");
        if (user.email === "") {
            email.text("Vui lòng nhập email");
            email.addClass("fail");
        } else if (!regexEmail.test(user.email)) {
            email.text("Email không đúng định dạng");
            email.addClass("fail");
        } else {
            email.empty();
            email.removeClass("fail");
        }

        const phone = $("span.phone");
        if (user.phone === "") {
            phone.text("Vui lòng nhập số điện thoại");
            phone.addClass("fail");
        } else if (!regexPhone.test(user.phone)) {
            phone.text("Số điện thoại không đúng định dạng");
            phone.addClass("fail");
        } else {
            phone.empty();
            phone.removeClass("fail");
        }

        const address = $("span.address");
        if (user.address === "" || user.ward === "-1") {
            address.text("Vui lòng nhập địa chỉ");
            address.addClass("fail");
        } else {
            address.empty();
            address.removeClass("fail");
        }

        const password = $("span.password");
        if (user.password === "") {
            password.text("Vui lòng nhập mật khẩu");
            password.addClass("fail");
        } else {
            password.empty();
            password.removeClass("fail");
        }

        const rePassword = $("span.rePassword");
        if (user.rePassword === "") {
            rePassword.text("Vui lòng nhập lại mật khẩu");
            rePassword.addClass("fail");
        } else if (user.password !== user.rePassword) {
            rePassword.text("Không khớp với mật khẩu");
            rePassword.addClass("fail");
        } else {
            rePassword.empty();
            rePassword.removeClass("fail");
        }

        return !$("span").hasClass("fail");
    }
});