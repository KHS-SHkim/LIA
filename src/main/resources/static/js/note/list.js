$(function(){
    $(".typeA").click(function(){
        alert(this);
        alert(this.value)
        location.href=this.val();
    });
});