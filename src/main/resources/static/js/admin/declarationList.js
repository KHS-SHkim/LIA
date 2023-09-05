$(function(){

    $(".form-select").on("change",function(){

        var state = $(this).val();

        const data = {"state" : state};

        $.ajax({
            url: "/admin/chDeclaration",
            type: "POST",
            data: data,
            cache: false,
            success: function(data){
                loadList(data)
            }
        });
    })
});
function loadList(data){
alert(data)
//    let row = `
//    <tr th:each="declaration : ${list}" class="text-center">
//        <td class="col-2">
//            <a th:href="@{'/declaration/detail/'+${declaration.id}}" th:text="${declaration.book.name}"></a>
//        </td>
//        <td class="col-2 text-muted td" th:text="${declaration.user.nickname}"></td>
//        <td class="col-2 text-muted td" th:text="${declaration.reporter.nickname}"></td>
//        <td class="col-2 text-muted td" th:text="${declaration.reportType}"></td>
//        <td class="col-2 text-muted td" th:text="${#temporals.format(declaration.regDate, 'yyyy-MM-dd HH:mm')}"></td>
//        <td class="col-2 text-muted td">
//            <a th:unless="${declaration.answerContent}" th:text="답변하러가기" th:href="@{'/declaration/detail/'+${declaration.id}}"></a>
//            <input th:if="${declaration.answerContent}" th:value="${declaration.answerContent}" disabled />
//        </td>
//    </tr>
//    `;
}