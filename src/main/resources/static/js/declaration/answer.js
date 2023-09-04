$(function(){
    const declaration_id = $("#declarationInfo").val();
    $("#btnAnswer").click(function(){
        const row = `
         <div class="mb-3 mt-3">
            <label> 답변내용 : </label>
            <input type="text" name="answerContent" id="answerContent"/>
            <input type="hidden"  name="declaration_id" id="declaration_id"/>
        </div>
        `
        $("#adminAnswer").html(row);
    });

    $("#btnAnswersubmit").click(function(){
        var answer = $("#answerContent").val();
        if (answer==null){
            alert("답변 입력 후 선택해주세요.");
        }else{
            $("#declaration_id").val(declaration_id)
            $("form[name='frmAnswer']").submit();
        }
    });

})