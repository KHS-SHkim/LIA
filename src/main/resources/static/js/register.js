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
                    $("#username_error").html("사용 가능한 아이디 입니다.");
                    $("#username_error").css("color","blue");
                } else{
                    $("#username_error").html("이미 존재하는 아이디 입니다.");
                    $("#username_error").css("color","red");
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

    });

})












