
    var naver_id_login = new naver_id_login("0M5Hhr3GeByMD2Fuojgc", "http://localhost:8095/user/naverLogin");
    var state = naver_id_login.getUniqState();
    naver_id_login.setDomain("http://localhost:8095/user/login");
    naver_id_login.setState(state);
    naver_id_login.init_naver_id_login();
