$(function(){
    // 글 [삭제] 버튼
    $("#btnDel").click(function(){
        let answer = confirm("삭제하시겠습니까?");
        if(answer){
            $("form[name='frmDelete']").submit();
        }
    });

    $('#btnDeclaration').click(function(){
        let book_id = $("input[name='id']").val();
        location.href='/declaration/write?book_id='+book_id
    });

    $('#btnNote').click(function(){
        let book_id = $("input[name='id']").val();
        window.open("http://localhost:8095/note/write?book_id=" + book_id, "width=700px,height=800px,scrollbars=yes")

    });
});
