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

    const out = [];
    data.forEach(declaration => {

    let row = `
        <tr class="text-center">
            <td class="col-2">
                <a href="/book/detail/"+${declaration.bookInfo}> ${declaration.bookName}</a>
            </td>
            <td class="col-2 text-muted td">${declaration.userNickname}</td>
            <td class="col-2 text-muted td">${declaration.reporterNickname}</td>
            <td class="col-2 text-muted td">${declaration.reportType}</td>
            <td class="col-2 text-muted td">${declaration.regDate}</td>
            <td class="col-2 text-muted td">`;
            if (declaration.answerContent == null || declaration.answerContent == ""){
                row+=`<a href="/declaration/detail/"+${declaration.id}>답변하러가기</a>`
            } else {
                row+=`<input value=${declaration.answerContent} disabled />`
            }
            row+=`</td></tr>`;
            out.push(row);
    });
    $("#declarationInfo").html(out.join("\n"));
}