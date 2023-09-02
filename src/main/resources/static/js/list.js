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
$(document).ready(function() {
    // 각 도서 행에 대한 처리
    $(".book-row").each(function() {
        var $row = $(this);
        var rentalStat = $row.find(".rental-stat").text().trim();
        var rentalEndDateISO = $row.find(".rental-end").text();

        // 대여 상태가 "대여중"인 경우에만 처리
        if (rentalStat === "대여중" && calculateReturnDate(rentalEndDateISO)!=null ) {
            var returnDateValue = calculateReturnDate(rentalEndDateISO); // 반납 예정일 계산
            console.log(returnDateValue);
            $row.find(".return-date-value").text("D-"+returnDateValue);
        }
    });
});

// 반납 예정일을 계산하는 함수
function calculateReturnDate(rentalEndDateISO) {
    var currentDate = new Date();
    var rentalEndDate = new Date(rentalEndDateISO);
        if (rentalEndDate < currentDate) {
            return null;
        }
    // 날짜 차이 계산

    var timeDiff = rentalEndDate - currentDate;
    var daysDiff = Math.floor(timeDiff / (1000 * 3600 * 24)); // 일 단위로 변환

    return daysDiff;
}






