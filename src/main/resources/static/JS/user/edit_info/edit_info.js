/**
* @author 최진실
*/
$(function () {
	$("#edit_member_information_btn").click(function(){
        let pw =$("#user_pw").val().length;
        let pw_check =$("#pw_check").val().length;
        let nickname = $("#nickname").val().length;
        let email = $("#email").val().length;
        let tel = $("#tel").val().length;
    });
});