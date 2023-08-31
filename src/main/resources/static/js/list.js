$(function(){
    $("[name='pageRows']").change(function(){
       // alert($(this).val());   // 확인용
       var frm = $("[name='frmPageRows']");
       frm.attr("method", "POST");
       frm.attr("action", "pageRows");   // ->  /board/list => /board/pageRows
       frm.submit();
    });
});
    // 대여가능만 프론트상에 뜨게 하기
    function filterList() {
        var isChecked = document.getElementById("availableOnly").checked;
        var rows = document.querySelectorAll(".book-row");

        rows.forEach(function(row) {
            var rentalStat = row.querySelector(".rental-stat").textContent;

            if (isChecked && rentalStat !== "대여가능") {
                row.style.display = "none";
            } else {
                row.style.display = "table-row";
            }
        });
    }