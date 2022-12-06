/**
 * @author 최진실
 */

$(function () {
    // 액티비티 가입하기
    let idx = "";
	
	 $("#join_activity_btn").click(function() {
		 if(($.cookie("isLogin") != "true")) {
			$(".warning-layer").removeClass("blind");
			$(".warning-close").click(function(){
		 		$(".warning-layer").addClass("blind");
	 		});
		}
		else {
			idx = $(this).attr("idx");
			ajax_load_join(idx);
		}
	 });
	
	function ajax_load_join(idx) {
		$.ajax({
			url : "/meet-a-bwa/a_registered.do",
			type : "GET",
			dataType : "json",
			data : {
				activity_no : idx
			},
			
			success : function(res) {
				location.reload();
			},
			error : function(res, status, text) {
				console.log("error");
				console.log(text);
			}
		});
	}
	
	// 액티비티 탈퇴
 	$(".activityExitBtn").click(function() {
		$(".activityExit-popup").removeClass("blind");
		$(".withdrawal").click(function() {
			$(".activityExit-popup").addClass("blind");
			idx = $(this).attr("idx");
			ajax_load_exit(idx);
		});
			
		$(".cancle").click(function() {
			$(".activityExit-popup").addClass("blind");
		});
	 }); 
	
	function ajax_load_exit(idx) {
		$.ajax({
			url : "/meet-a-bwa/a_withdrawal.do",
			type : "GET",
			dataType : "json",
			data : {
				activity_no : idx
			},
			
			success : function(res) {
				location.reload();
			},
			error : function(res, status, text) {
				console.log(text)
			}
		});
	}
});