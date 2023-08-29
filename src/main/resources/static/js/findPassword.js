$(function(){



    $("#authBtn").click(function(){

        let username = $("#username").val();
        let phone = $("#phone").val();
        let email = $("#email").val();

        const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        const usernameRegex = /^[a-zA-Z0-9]*$/;

        if(!username){
            alert("아이디를 입력해주세요");
            $("#username").focus();
            return;
        } else if (!usernameRegex.test(username)){
            alert("아이디는 영문 숫자만 가능합니다.");
            $("#username").focus();
            return;
        }

        if(!phone){
            alert("전화번호를 입력해주세요");
            $("#phone").focus();
            return;
        } else if (phone.length > 11 || phone.length < 11){
            alert("전화번호는 11자리만 가능합니다.");
            $("#phone").focus();
            return;
        }


        if(!email){
            alert("이메일을 입력해주세요");
            $("#email").focus();
            return;
        } else if (!emailRegex.test(email)){
            alert("유효하지 않은 형식의 이메일 입니다.");
            $("#email").focus();
            return;
        }

        const data = {"username" : username,
                      "phone" : phone,
                      "email" : email};

        $.ajax({
            url:"/user/findPassword/check",
            type:"POST",
            data: data,
            cache: false,
            success: function(response){
                if(response == 1){
                    alert("정보가 일치하지않습니다.");
                    return;
                } else{

                    alert("인증번호를 보냈습니다.");

                    const data = {"email" : email};

                    $.ajax({
                        url: "/register/authEmail",
                        type: "POST",
                        data: JSON.stringify(data),
                        contentType:"application/json",
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
            $("#username").prop("readonly", true);
            $("#phone").prop("readonly", true);
            $("#email_error").html("인증성공");
            $("#email_error").css("color","blue");
            $("#passwordDiv").css("display", "block")
        } else{
            $("#email_error").html("인증번호 불일치");
            $("#email_error").css("color","red");
        }
    }




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

        $("#password_error").html("");

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



    $("#checkForm").submit(function(event){
        event.preventDefault();

        let username = $("#username").val();
        let phone = $("#phone").val();
        let email = $("#email").val();
        let password = $("#password").val();
        let re_password = $("#re_password").val();

        let inputAuthNum = $("#inputAuthNum").val();
        let authNum = $("#authNum").val();

        if(!authNum || inputAuthNum !== authNum){
            $("#email_error").html("이메일 인증을 진행해주세요");
            $("#email_error").css("color","red");
            return;
        }

        if(!username || username.length==0 || !phone || phone.length == 0 ||
            !email || email.length == 0 || !password || password.length == 0 ||
             !re_password || re_password.length == 0)
             {
                 alert("빈칸없이 채워주세요");
                 return;
             }

        if(password !== re_password){
            alert("비밀번호가 일치하지 않습니다");
            return;
        }

        $(this).unbind("submit").submit();

    });


})

