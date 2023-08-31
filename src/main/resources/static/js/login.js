$(function(){


    $("#naver_id_login").click(function(){

        location.href = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=0M5Hhr3GeByMD2Fuojgc&state=test&redirect_uri=http://localhost:8095/user/naverLogin";

    })

})