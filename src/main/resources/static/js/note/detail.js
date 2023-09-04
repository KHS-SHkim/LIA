$(function(){
    $("#btn_comment").click(function(){
        const content = $("#contents").val().trim();
        const user = $("#user").val();
        const receiver = $("#receiver").val();
        const book = $("#book").val();
        const userInfo = $("#userInfo").val();

        const data = {
            "user_id": user,
            "receiver_id": receiver,
            "book_id": book,
            "contents": content
        };


        $.ajax({
            url: "/note/write",
            type: "POST",
            data: data,
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    loadNote(user,receiver);   // 댓글 목록 다시 업데이트
                    $("#contents").val('');   // 입력 input 은 비우기
                }
            },
        });
    });

    $("#contents").on("propertychange change keyup paste input",function(e){
        if ( e.key == "Enter" ) {
            const content = $("#contents").val().trim();
            const user = $("#user").val();
            const receiver = $("#receiver").val();
            const book = $("#book").val();
            const userInfo = $("#userInfo").val();

            const data = {
                "user_id": user,
                "receiver_id": receiver,
                "book_id": book,
                "contents": content
            };


            $.ajax({
                url: "/note/write",
                type: "POST",
                data: data,
                cache: false,
                success: function(data, status, xhr){
                    if(status == "success"){
                        loadNote(user,receiver);   // 댓글 목록 다시 업데이트
                        $("#contents").val('');   // 입력 input 은 비우기
                    }
                },
            });
        }
    });
});
function loadNote( user , receiver ){
//    console.log("/note/detailAjax?userid="+user+"&receiverid="+receiver);
    let d = {
        "user_id" : user,
        "receiver_id" : receiver
    }
    $.ajax({
        url: "/note/detailAjax",
        type: "GET",
        cache: false,
        data : d,
        success: function(data, status, xhr){
            buildComment(data);
        },
        err : function(){
            alert("err")
        }
    });
}

function buildComment(result){
    const out = [];



    result.noteList.forEach(note => {
//        console.log(note)
        let noteUser = note.userInfo;
        let NoteReceiver = note.receiverInfo;
        let noteContents = note.contents;
        out.push(`<div class="row mt-5">`)


        const hrow = ( result.user.id == noteUser ) ? ` <div class="myreceiver"> 내가 발송` : `<div class="receiver"> 내가 수신` ;
        const row = `
            ${hrow}
                <div>${note.userNickname}</div>
                <div>${noteContents}</div>
            </div>
        </div>
        `;
        out.push(row);
        $("#book").val(note.bookInfo);
    });

    $("#showNote").html(out.join("\n"));
}
