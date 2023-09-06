$(function(){

    // 첨부파일 [추가] 버튼 누르면,  첨부할 파일(들) 추가
    let i = 0;
    $("#btnAdd").click(function(){
        $("#files").append(`
               <div class="input-group mb-2">
                   <input class="form-control col-xs-3" type="file" name="upfile${i}"/>
                   <button type="button" class="btn btn-outline-danger" onclick="$(this).parent().remove()">삭제</button>
               </div>`);
        i++;
    });


    $("#sample6_btn").click(function(){
        $("#error_rental_spot").html("");
    });

});

