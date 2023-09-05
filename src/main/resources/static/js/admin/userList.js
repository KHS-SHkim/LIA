$(function(){

    $(".form-select").on("change",function(){

        const answer = confirm("회원의 상태를 변경하시겠습니까?");

        var state = $(this).val();
        var username = $("#username").val();

        if(answer){

            const data = {"state" : state,
                          "username" : username};

            $.ajax({
                url: "/admin/chState",
                type: "POST",
                data: data,
                cache: false,
                success: function(response){
                    if(response == 1){
                        alert("회원의 상태를 변경했습니다.");
                    }
                },
                error: function(xhr, status, error){
                    console.error(error);
                }
            });

        }


    })


})