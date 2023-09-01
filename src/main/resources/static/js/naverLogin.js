var naver_id_login = new naver_id_login("0M5Hhr3GeByMD2Fuojgc", "http://localhost:8095/user/naverLogin");

var access_token = naver_id_login.oauthParams.access_token

naver_id_login.get_naver_userprofile("naverSignInCallback()");

  function naverSignInCallback(){
    var token = naver_id_login.oauthParams.access_token
    var email = naver_id_login.getProfileData('email');
    var name = naver_id_login.getProfileData('name');

    $.ajax({
        url:"/user/register/emailChk",
        type:"POST",
        data: {email: email},
        dataType: "json",
        success: function(response){
            if(response == 1){
                alert("이미 가입하신 이메일 입니다.");
                history.back();
                return;
            } else{
                 var atIndex = email.indexOf('@');
                 var username = atIndex !== -1 ? email.substring(0, atIndex) : "";

                 let usernameInput = document.getElementById("username");
                 let emailInput = document.getElementById("email");
                 let nicknameInput = document.getElementById("nickname");

                 $.ajax({
                    url:"/user/register/usernameChk",
                    type: "POST",
                    data: {username: username},
                    dataType: "json",
                    success: function(response){
                        if(response == 1){
                            alert("이미 존재하는 아이디가 있습니다. 아이디를 설정해주세요.");
                            $("#username").prop("readonly",false);
                            $("#username").focus();

                            emailInput.value = email;
                            nicknameInput.value = name;
                        } else{
                            usernameInput.value = username;
                            emailInput.value = email;
                            nicknameInput.value = name;
                        }
                    }
                 })


            }
        },
        error: function(xhr, status, error){
            console.error(error);
        }
    })

    $("#username").on("keyup", function(){

        let username = $("#username").val();
        const usernameRegex = /^[a-zA-Z0-9]*$/;

        $("#error_username").html("");

        // 아이디 중복확인
        $.ajax({
            url: "/user/register/usernameChk",
            type: "POST",
            data: {username: username},
            dataType: "json",
            success: function(response){
                if(!usernameRegex.test(username)){
                    $("#username_error").html("아이디는 영문 숫자만 입력가능합니다.");
                    $("#username_error").css("color","red");
                }
                else if(response == 1){
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

    $("#password").on("keyup",function(){
        $("#error_password").html("");
        $("#error_re_password").html("");
    })

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

        $("#error_password").html("");

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

        $("#error_phone").html("");

        if(phone != null){
            if(phone.length>11 || phone.length<11){
                $("#phone_error").html("전화번호는 11자리만 입력가능합니다.");
                $("#phone_error").css("color","red");
                return;
            } else{
                $("#phone_error").html("");
            }
        }
    })





  }


