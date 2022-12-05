/**
 * @author 김예은
 */

$(function() {

	// 로그아웃 팝업 - 취소버튼 클릭
	$(".btn-cancel").click(function() {
		$(".logout-layer").addClass("blind");
		$(".popup-background:eq(0)").addClass("blind");
	});
	$("#logout-btn").click(function() {
		location.href = "/meet-a-bwa/logoutOK.do";
	});

});