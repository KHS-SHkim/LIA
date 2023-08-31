$(function(){
    var code = $("#code").val();
    var state = $("#state").val();

    location.href = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=0M5Hhr3GeByMD2Fuojgc&client_secret=6PPtc2l5M4&code="+code+"&state="+state;




})