$(document).ready(function () {
    $(".size-css-enable").click(function () {
        $(this).parent().find(".size-css-enable").removeClass("size-css-active");

        $(this).addClass("size-css-active");

        const detail = $(this).attr("data");
        let url = $(this).closest("div.change-url").find("#add-cart").attr("data-url");
        url = url + "&detail=" + detail;
        $(this).closest("div.change-url").find("#add-cart").attr("href", url);
        $(this).closest("div.change-url").find("#add-cart").attr("href", url);
        $(this).closest("div.change-url").find("#add-cart").attr("class", "add-cart");
    });

    $(document).on("click", ".no-add-cart", function () {
        $('#myModalConfirm').modal('show');
    });

    $(document).on('change', '#pay-city', function () {
        const district = $('#pay-district');
        if (this.value > 0) {
            district.attr("data-city", this.value);
            $.ajax({
                url: 'http://localhost:9090/mySuFood/province/district',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    cityId: this.value
                }
            }).done(function (value) {
                if (value != null) {
                    let html = "<option value='-1'>Chọn Quận/Huyện</option>";
                    $.each(value, function (key, value) {
                        html += "<option value='" + value.districtId + "'>" + value.districtName + "</option>";
                    });
                    $('#pay-district').html(html);
                }
            });
        } else {
            district.attr("data-city", 0).html("<option value='-1'>Chọn Quận/Huyện</option>");
        }
        $('#pay-ward').html("<option value='-1'>Chọn Phường/Xã</option>");
        $(".admin-address").val("");
    });

    $(document).on('change', '#pay-district', function () {
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
                    let html = "<option value='-1'>Chọn Phường/Xã</option>";
                    $.each(value, function (key, value) {
                        html += "<option value='" + value.wardId + "'>" + value.wardName + "</option>";
                    });
                    $('#pay-ward').html(html);
                }
            });
        }
        $('#pay-ward').html("<option value='-1'>Chọn Phường/Xã</option>");
        $(".admin-address").val("");
    });

    $(document).on('change', '#pay-ward', function () {
        $(".admin-address").val("");
    });

    $(document).on("click", "#redirect-pay", function () {
        const user = {};
        user["email"] = $("input[name=email]").val();
        user["name"] = $("input[name=fullname]").val();
        user["phone"] = $("input[name=phone]").val();
        user["city"] = $("select[name=city]").val();
        user["district"] = $("select[name=district]").val();
        user["ward"] = $("select[name=ward]").val();
        user["address"] = $("input[name=address]").val();
        if (validateRegist(user)) {
            localStorage.setItem("user", JSON.stringify(user));
            window.location.href = "http://localhost:9090/mySuFood/pay/type";
        }
    });

    $('#myModalPay').on('hidden.bs.modal', function () {
        window.location.href = "http://localhost:9090/mySuFood";
    });

    $('#myModalUser').on('hidden.bs.modal', function () {
        window.location.href = "http://localhost:9090/mySuFood/dang-nhap";
    });

    $(document).on("change", "input[name=payment_method]", function () {
        const key = $(this).val();
        const button = $("button.but_step2");
        if (key !== "2") {
            button.attr("data-toggle", "modal").attr("data-target", "#exampleModal").attr("id", "but_step_confirm");
        } else {
            button.removeAttr("data-toggle").removeAttr("data-target").attr("id", "but_step2");
        }
    });


    $(document).on("click", "#but_step2", function () {
        const type = $("input[name=payment_method]:checked").val();
        const user = JSON.parse(localStorage.getItem("user"));
        const amount = $(".payment-due-price").text();
        console.log(type);
        if (type === "2") {
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
        $("span.error-message").attr("style", "display:none;")
        const user = JSON.parse(localStorage.getItem("user"));
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
        const user = JSON.parse(localStorage.getItem("user"));
        const type = $("input[name=payment_method]:checked").val();
        const code = $("input[name=code]").val();
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
                $('#exampleModal').modal('hide');
                $('#myModal').modal('show');
                localStorage.removeItem("user");
            } else {
                $("span.error-message").attr("style", "display:block;margin-top: 0;")
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
                    $('#myModalUser').modal('show');

                } else {
                    $(".error-server").text(value);
                }
            });
        }
    });

    function validateRegist(user) {
        const regexEmail = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        const regexPhone = /^(84|0[3|5|7|8|9])+([0-9]{8})\b/;

        const fullname = $("span.fullname");
        if (user.fullname === "" || user.name === "") {
            fullname.text("Vui lòng nhập họ và tên").addClass("fail");
        } else {
            fullname.empty().removeClass("fail");
        }

        const email = $("span.email");
        if (user.email === "") {
            email.text("Vui lòng nhập email").addClass("fail");
        } else if (!regexEmail.test(user.email)) {
            email.text("Email không đúng định dạng").addClass("fail");
        } else {
            email.empty().removeClass("fail");
        }

        const phone = $("span.phone");
        if (user.phone === "") {
            phone.text("Vui lòng nhập số điện thoại").addClass("fail");
        } else if (!regexPhone.test(user.phone)) {
            phone.text("Số điện thoại không đúng định dạng").addClass("fail");
        } else {
            phone.empty().removeClass("fail");
        }

        const city = $("span.city");
        if (user.city === "-1") {
            city.text("Vui lòng chọn Tỉnh/TP").addClass("fail");
        } else {
            city.empty().removeClass("fail");
        }

        const district = $("span.district");
        if (user.district === "-1") {
            district.text("Vui lòng chọn Quận/Huyện").addClass("fail");
        } else {
            district.empty().removeClass("fail");
        }

        const ward = $("span.ward");
        if (user.ward === "-1") {
            ward.text("Vui lòng chọn Phường/Xã").addClass("fail");
        } else {
            ward.empty().removeClass("fail");
        }

        const address = $("span.address");
        if (user.address === "" || user.ward === "-1") {
            address.text("Vui lòng nhập địa chỉ").addClass("fail");
        } else {
            address.empty().removeClass("fail");
        }

        const password = $("span.password");
        if (user.password === "") {
            password.text("Vui lòng nhập mật khẩu").addClass("fail");
        } else {
            password.empty().removeClass("fail");
        }

        const rePassword = $("span.rePassword");
        if (user.rePassword === "") {
            rePassword.text("Vui lòng nhập lại mật khẩu").addClass("fail");
        } else if (user.password !== user.rePassword) {
            rePassword.text("Không khớp với mật khẩu").addClass("fail");
        } else {
            rePassword.empty().removeClass("fail");
        }

        return !$("span").hasClass("fail");
    }
});