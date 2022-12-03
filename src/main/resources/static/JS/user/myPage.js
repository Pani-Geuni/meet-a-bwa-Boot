/**
* @author 전판근
*/
$(function() {
	$(".edit-user-info").on('click', function() {
		location.href = "u_update.do";
	})
	
	$(".my-meet").on('click', function() {
		location.href = "my-meet.do";
	})
	
	$(".my-activity").on('click', function() {
		location.href = "my-activity.do";
	})
})