/**
 * @author 최진실
 */

$(function() {
	// 액티비티 가입하기
	let idx = "";

	$("#join_activity_btn").click(function() {
		if (($.cookie("user_no") == null)) {
			$(".warning-layer").removeClass("blind");
			$(".warning-close").click(function() {
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
			url: "/meet-a-bwa/activity_register",
			type: "GET",
			dataType: "json",
			data: {
				activity_no: idx,
				user_no: $.cookie("user_no")
			},

			success: function(res) {
				if (res.result == 1) location.reload();
				else {
					$('.popup-background:eq(1)').removeClass('blind')
					$('#common-alert-popup').removeClass('blind')
					$('.common-alert-txt').text('가입 조건을 확인해주세요.')
				}
			},
			error: function(res, status, text) {
				$('.popup-background:eq(1)').removeClass('blind')
				$('#common-alert-popup').removeClass('blind')
				$('.common-alert-txt').text('오류 발생으로 처리되지 못했습니다.')
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
			url: "/meet-a-bwa/a_withdrawal.do",
			type: "GET",
			dataType: "json",
			data: {
				activity_no: idx
			},

			success: function(res) {
				location.reload();
			},
			error: function(res, status, text) {
				console.log(text)
			}
		});
	}

	$("#common-alert-btn").click(function() {
		$(".popup-background:eq(1)").addClass("blind");
		$("#common-alert-popup").addClass("blind");
	});
});