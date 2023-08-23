$(function(){

    $("#username").on("keyup", function(){

        let username = $("#username").val();
        const usernameRegex = /^[a-zA-Z0-9]*$/;

        if(username){
           $("#error_msg").html("");
        }

        if(!usernameRegex.test(username)){
            alert("아이디는 영문,숫자만 사용가능합니다.");
            $("#username").focus();
            return;
        }

        // 아이디 중복확인
        $.ajax({
            url: "/user/register/usernameChk",
            type: "POST",
            data: {username: username},
            dataType: "json",
            success: function(response){
                if(response == 1){
                    $("#username_error").html("이미 존재하는 아이디 입니다.");
                    $("#username_error").css("color","red");
                } else{
                    $("#username_error").html("사용가능한 아이디 입니다.");
                    $("#username_error").css("color","blue");
                }
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
    });


    $("#re_password").on("keyup", function(){
        let password = $("#password").val();
        let re_password = $("#re_password").val();

        if(password !== re_password){
            $("#password_error").html("비밀번호 불일치");
            $("#password_error").css("color", "red");
        } else {
            $("#password_error").html("비밀번호 일치");
            $("#password_error").css("color","blue");
        }
    });

    $("#password").on("keyup",function(){
        let password = $("#password").val();
        let re_password = $("#re_password").val();

        if(re_password){
            if(re_password !== password){
                $("#password_error").html("비밀번호 불일치");
                $("#password_error").css("color", "red");
            } else {
                $("#password_error").html("비밀번호 일치");
                $("#password_error").css("color","blue");
            }
        }
    });

    $("#phone").on("keyup",function(){
        let phone = $("#phone").val();

        if(phone != null){
            if(phone.length>=12){
                $("#phone_error").html("전화번호는 11자리 까지만 입력가능합니다.");
                $("#phone_error").css("color","red");
                return;
            } else{
                $("#phone_error").html("");
            }
        }
    })


    $("#authBtn").click(function(){

        const email = $("#email").val();

        const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        if(!email){
            alert("이메일을 입력해주세요");
            $("#email").focus();
            return;
        } else if(!emailRegex.test(email)){
            alert("유효하지 않은 형식의 이메일 입니다.");
            $("#email").focus();
            return;
        }

        $.ajax({
            url:"/user/register/emailChk",
            type: "POST",
            data: {email: email},
            dataType: "json",
            success: function(response){
                if(response == 1){
                    $("#email_error").html("이미 존재하는 이메일 입니다.");
                    $("#email_error").css("color","red");
                    $("#email").focus();
                    return;
                } else{

                    alert("인증번호를 보냈습니다.");
                    $("#email_error").html("");

                    const data = {"email" : email};

                    $.ajax({
                        url: "/register/authEmail",
                        type: "POST",
                        data: JSON.stringify(data),
                        contentType: "application/json",
                        dataType: "text",
                        success: function(response){
                            $("#inputAuthNum").val(response);

                            $("#checkBtn").unbind("click").click(checkBtnHandler);
                        },
                        error: function(xhr, status, error){
                            console.error(error);
                        }
                    });

                    $("#authDiv").show();
                }
            },
            error: function(xhr, status, error){
                console.error(error);
            }
        });
    });

    function checkBtnHandler(){
        var inputAuthNum = $("#inputAuthNum").val();
        var authNum = $("#authNum").val();

        if(inputAuthNum === authNum) {
            $("#authDiv").hide();
            $("#email").prop("readonly", true);
            $("#email_error").html("인증성공");
            $("#email_error").css("color","blue");
        } else{
            $("#email_error").html("인증번호 불일치");
            $("#email_error").css("color","red");
        }
    }

    $("form").submit(function(event){
        event.preventDefault();

        var inputAuthNum = $("#inputAuthNum").val();
        var authNum = $("#authNum").val();

        if(!authNum || inputAuthNum !== authNum){
            $("#email_error").html("이메일 인증을 진행해주세요");
            $("#email_error").css("color","red");
            return;
        }

        $(this).unbind("submit").submit();
    });



})












