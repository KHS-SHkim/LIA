$(function(){

    $("#imgBtn").click(function(){
        $("#upfile").css("display","block");
        $("#imgbox").css("display", "none");
        $("#imgBtn").css("display", "none");
    })

    $("#nicknameBtn").click(function(){
        $("#nicknameDiv").css("display","none");
        $("#nickname").css("display","block");
        $("#nickname").val("");
        $("#nicknameBtn").css("display","none");

        let nickname = $("#nickname").val();

        if(nickname == null || nickname.length == 0){
            $("#saveBtn").attr("disabled", true);
        }
    })

    $("#phoneBtn").click(function(){
        $("#phoneDiv").css("display","none");
        $("#phone").css("display","block");
        $("#phone").val("");
        $("#phoneBtn").css("display","none");

        let phone = $("#phone").val();

        if(phone == null || phone.length == 0){
            $("#saveBtn").attr("disabled", true);
        }
    })

    $("#chgPwBtn").click(function(){
        $("#chPwDiv").css("display","block");
        $("#chgPwBtn").css("display","none");
        $("#saveBtn").attr("disabled",true);
    })

    $("#chgPostBtn").click(function(){
        $("#addPostDiv").css("display","block");
        $("#postDiv").css("display","none");
        $("#chgPostBtn").css("display","none");

        $("#saveBtn").attr("disabled", true);

    })

    $("#nickname").on("keyup",function(){
        let nickname = $("#nickname").val();

        if(nickname == null || nickname.length == 0){
            $("#nickname_error").html("닉네임 입력은 필수입니다.");
            $("#nickname_error").css("color","red");
            $("#saveBtn").attr('disabled', true);
        } else{
            $("#nickname_error").html("");
            $("#saveBtn").attr('disabled', false);
        }
    })

    $("#phone").on("keyup",function(){
            let phone = $("#phone").val();

            if(phone == null || phone.length == 0){
                $("#saveBtn").attr('disabled', true);
                $("#phone_error").html("전화번호는 필수입니다.");
                $("#phone_error").css("color","red");
            } else{
                $("#saveBtn").attr('disabled', false);
                $("#phone_error").html("");
            }

            if(phone != null){
                if(phone.length>11 || phone.length<11){
                    $("#phone_error").html("전화번호는 11자리만 입력가능합니다.");
                    $("#phone_error").css("color","red");
                    return;
                } else{
                    $("#phone_error").html("");
                }
            }
        });

    $("#re_password").on("keyup", function(){
        let password = $("#password").val();
        let re_password = $("#re_password").val();

        if(re_password == null || re_password.length == 0){
            $("#saveBtn").attr('disabled', true);
            $("#password_error").html("비밀번호 확인은 필수입니다.");
            $("#password_error").css("color","red");
        } else{
            $("#password_error").html("");
        }

        if(password !== re_password){
            $("#password_error").html("비밀번호 불일치");
            $("#password_error").css("color", "red");
            $("#saveBtn").attr("disabled", true);
        } else {
            $("#password_error").html("비밀번호 일치");
            $("#password_error").css("color","blue");
            $("#saveBtn").attr("disabled", false);
        }
    });

    $("#password").on("keyup",function(){
        let password = $("#password").val();
        let re_password = $("#re_password").val();

        if(password == null || password.length == 0){
            $("#saveBtn").attr('disabled', true);
            $("#password_error").html("비밀번호 입력은 필수입니다.");
            $("#password_error").css("color","red");
        } else{
            $("#password_error").html("");
        }

        if(re_password){
            if(re_password !== password){
                $("#password_error").html("비밀번호 불일치");
                $("#password_error").css("color", "red");
                $("#saveBtn").attr("disabled", true);
            } else {
                $("#password_error").html("비밀번호 일치");
                $("#password_error").css("color","blue");
                $("#saveBtn").attr("disabled", false);
            }
        }
    });






    $("#post_num").on("keyup",function(){
        let post_num = $("#post_num").val();

        if(post_num == null || post_num.length == 0){
            $("#saveBtn").attr("disabled", true);
            $("#post_num_error").html("우편번호는 필수입니다.");
            $("#post_num_error").css("color","red");
        } else{
            $("#saveBtn").attr("disabled", false);
            $("#post_num_error").html("");
        }
    });

    $("#address").on("keyup",function(){
        let address = $("#address").val();

        if(address == null || address.length == 0){
            $("#saveBtn").attr("disabled", true);
            $("#address_error").html("주소는 필수입니다.");
            $("#address_error").css("color","red");
        } else{
            $("#saveBtn").attr("disabled", false);
            $("#address_error").html("");
        }
    });

    $("#address_detail").on("keyup",function(){
        let address_detail = $("#address_detail").val();

        if(address_detail == null || address_detail.length == 0){
            $("#saveBtn").attr("disabled", true);
            $("#address_detail_error").html("상세주소는 필수입니다.");
            $("#address_detail_error").css("color","red");
        } else{
            $("#saveBtn").attr("disabled", false);
            $("#address_detail_error").html("");
        }
    });

    $("#myForm").submit(function(event){
        event.preventDefault();

        let nickname = $("#nickname").val();
        let phone = $("#phone").val();
        let password = $("#password").val();
        let re_password = $("#re_password").val();
        let post_num = $("#post_num").val();
        let address = $("#address").val();
        let address_detail = $("#address_detail").val();

        if(nickname == null || nickname.length == 0 || phone == null || phone.length == 0 ||
        password == null || password.length==0 || re_password == null || re_password.length == 0 ||
        post_num == null || post_num.length==0 || address == null || address.length == 0 ||
        address_detail == null || address_detail.length == 0){
            alert("빈칸없이 채워주세요");
            return;
        }

        $(this).unbind("submit").submit();
    })


})