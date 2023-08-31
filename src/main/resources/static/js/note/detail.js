$(function(){
    $("#btn_comment").click(function(){
        // 입력한 댓글
        const content = $("#contents").val().trim();
        const user = $("#user").val();
        const receiver = $("#receiver").val();
        const book = $("#book").val();
        const data = {
            "user": user,
            "receiver": receiver,
            "book_id": book,
            "contents": content
        };
        alert(user);
        alert(receiver);
        alert(book);
        alert(content);

        $.ajax({
            url: "/note/write",
            type: "POST",
            data: data,
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    if(data.status !== "OK"){
                        alert(data.status);
                        return;
                    }
                    loadNote(user,receiver);   // 댓글 목록 다시 업데이트
                    $("#contents").val('');   // 입력 input 은 비우기
                }
            },
        });
    });
});

function loadNote(user,receiver){
    alert(user);
    alert(receiver);
//    $.ajax({
//        url: "/note/list/"+user.id+"/"+receiver.id
//        type: "GET",
//        cache: false,
//        success: function(data, status, xhr){
//            if(status == "success"){
//                //alert(xhr.responseText);   // response 결과 확인용.
//
//                // data 매개변수 : JSON 으로 response 되면 JS object 로 변환되어 받아온다
//                if(data.status !== "OK"){
//                    alert(data.status);
//                    return;
//                }
//            }
//        }
//
//    });
}