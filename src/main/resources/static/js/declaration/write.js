$(function(){
    $("#reportType").change(function(){
        if ($(this).val() == "기타") {
            $("#reportTypeOrder").prop('disabled', false);       // 비활성화된 경우 활성화
        }
        else {
            $("#reportTypeOrder").prop('disabled', true);        // 활성화된 경우 비활성화
        }
    });
})