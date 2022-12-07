/**
 * @author 최진실
 */

$(function() {

	///// 액티비티 가입하기 /////
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
		//로딩 화면
		$(".popup-background:eq(1)").removeClass("blind");
		$("#spinner-section").removeClass("blind");

		$.ajax({
			url: "/meet-a-bwa/activity_register",
			type: "GET",
			dataType: "json",
			data: {
				activity_no: idx,
				user_no: $.cookie("user_no")
			},

			success: function(res) {
				//로딩 화면 닫기
				$(".popup-background:eq(1)").addClass("blind");
				$("#spinner-section").addClass("blind");

				if (res.result == 1) {
					$(".joinSuccess-popup").removeClass("blind");
					$(".go").click(function() {
						$(".joinSuccess-popup").addClass("blind");
						location.reload();
					});
				}
				else if (res.result == 0) {
					$('.popup-background:eq(1)').removeClass('blind')
					$('#common-alert-popup').removeClass('blind')
					$('.common-alert-txt').text('가입 조건을 확인해주세요.')
				}
				else if (res.result == 2) {
					$('.popup-background:eq(1)').removeClass('blind')
					$('#common-alert-popup').removeClass('blind')
					$('.common-alert-txt').text('정원이 초과되었습니다.')
				}
				else if (res.result == 3) {
					$('.popup-background:eq(1)').removeClass('blind')
					$('#common-alert-popup').removeClass('blind')
					$('.common-alert-txt').text('모임에 먼저 가입해주세요!')
				}

			},
			error: function(res, status, text) {
				//로딩 화면 닫기
				$(".popup-background:eq(1)").addClass("blind");
				$("#spinner-section").addClass("blind");
				// 에러 팝업
				$('.popup-background:eq(1)').removeClass('blind')
				$('#common-alert-popup').removeClass('blind')
				$('.common-alert-txt').text('오류 발생으로 처리되지 못했습니다.')
			}
		});
	}

	// 날짜 함수
	function leadingZeros(n, digits) {
		var zero = '';
		n = n.toString();

		if (n.length < digits) {
			for (i = 0; i < digits - n.length; i++)
				zero += '0';
		}
		return zero + n;
	}

	// 액티비티 수정 페이지 요청
	$(".activityUpdateBtn").on('click', function() {
		//		let query = location.search;
		//		let param = new URLSearchParams(query);
		//		let activity_no = param.get('activity_no');
		idx = $(this).attr("idx");
		let recruitment_etime = $("#recruitment_etime").val();
		var now = new Date();
		console.log("recruitment_etime:" + recruitment_etime);
		console.log("now:" + now);

		//		recruitment_etime = leadingZeros(recruitment_etime.getFullYear(), 4) + '-' +
		//			leadingZeros(recruitment_etime.getMonth() + 1, 2) + '-' +
		//			leadingZeros(recruitment_etime.getDate(), 2);
		//
		now = leadingZeros(now.getFullYear(), 4) + '-' +
			leadingZeros(now.getMonth() + 1, 2) + '-' +
			leadingZeros(now.getDate(), 2);

		//로딩 화면
		$(".popup-background:eq(1)").removeClass("blind");
		$("#spinner-section").removeClass("blind");

		if (now < recruitment_etime) {
			//로딩 화면 닫기
			$(".popup-background:eq(1)").addClass("blind");
			$("#spinner-section").addClass("blind");

			location.href = "/meet-a-bwa/activity_update?activity_no=" + idx;
		} else {
			//로딩 화면 닫기
			$(".popup-background:eq(1)").addClass("blind");
			$("#spinner-section").addClass("blind");

			// 에러 팝업
			$('.popup-background:eq(1)').removeClass('blind')
			$('#common-alert-popup').removeClass('blind')
			$('.common-alert-txt').text('모집 기간이 지나면 수정이 불가합니다.')
		}
	});

	// 액티비티 삭제
	$(".activityDeleteBtn").click(function() {
		$(".activityDelete-popup").removeClass("blind");
		$(".delete").click(function() {

			$(".activityDelete-popup").addClass("blind");

			let activity_stime = $("#activity_stime").val();
			var now = new Date();
			console.log("activity_stime:" + activity_stime);
			console.log("now:" + now);

			now = leadingZeros(now.getFullYear(), 4) + '-' +
				leadingZeros(now.getMonth() + 1, 2) + '-' +
				leadingZeros(now.getDate(), 2);
			if (now < activity_stime) {
				idx = $(this).attr("idx");
				ajax_load_delete(idx);
			} else {
				// 에러 팝업
				$('.popup-background:eq(1)').removeClass('blind')
				$('#common-alert-popup').removeClass('blind')
				$('.common-alert-txt').text('활동이 시작되면 삭제가 불가합니다.')
			}
		});

		$(".cancle").click(function() {
			$(".activityDelete-popup").addClass("blind");
		});
	});

	function ajax_load_delete(idx) {
		//로딩 화면
		$(".popup-background:eq(1)").removeClass("blind");
		$("#spinner-section").removeClass("blind");
		
		var user_no = $.cookie('user_no');

		$.ajax({
			url: "/meet-a-bwa/activity_delete",
			type: "POST",
			dataType: "json",
			data: {
				activity_no: idx,
				user_no : user_no
			},

			success: function(res) {
				//로딩 화면 닫기
				$(".popup-background:eq(1)").addClass("blind");
				$("#spinner-section").addClass("blind");

				if (res.result == 1) {
					//					location.reload();
					location.href = "/meet-a-bwa/meet_main.do?idx=" + res.meet_no;
				} else {
					$('.popup-background:eq(1)').removeClass('blind')
					$('#common-alert-popup').removeClass('blind')
					$('.common-alert-txt').text('오류 발생으로 처리되지 못했습니다.')
				}
			},
			error: function(res, status, text) {
				//로딩 화면 닫기
				$(".popup-background:eq(1)").addClass("blind");
				$("#spinner-section").addClass("blind");

				// 에러 팝업
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

			let recruitment_etime = $("#recruitment_etime").val();
			var now = new Date();
			console.log("recruitment_etime:" + recruitment_etime);
			console.log("now:" + now);

			now = leadingZeros(now.getFullYear(), 4) + '-' +
				leadingZeros(now.getMonth() + 1, 2) + '-' +
				leadingZeros(now.getDate(), 2);
			if (now < recruitment_etime) {
				idx = $(this).attr("idx");
				ajax_load_exit(idx);
			} else {
				// 에러 팝업
				$('.popup-background:eq(1)').removeClass('blind')
				$('#common-alert-popup').removeClass('blind')
				$('.common-alert-txt').text('모집이 종료되면 되면 탈퇴가 불가합니다.')
			}
		});

		$(".cancle").click(function() {
			$(".activityExit-popup").addClass("blind");
		});
	});

	function ajax_load_exit(idx) {
		//로딩 화면
		$(".popup-background:eq(1)").removeClass("blind");
		$("#spinner-section").removeClass("blind");

		$.ajax({
			url: "/meet-a-bwa/activity_withdrawal",
			type: "POST",
			dataType: "json",
			data: {
				activity_no: idx
			},

			success: function(res) {
				//로딩 화면 닫기
				$(".popup-background:eq(1)").addClass("blind");
				$("#spinner-section").addClass("blind");

				if (res.result == 1) {
					location.reload();
				} else {
					$('.popup-background:eq(1)').removeClass('blind')
					$('#common-alert-popup').removeClass('blind')
					$('.common-alert-txt').text('오류 발생으로 처리되지 못했습니다.')
				}
			},
			error: function(res, status, text) {
				//로딩 화면 닫기
				$(".popup-background:eq(1)").addClass("blind");
				$("#spinner-section").addClass("blind");

				// 에러 팝업
				$('.popup-background:eq(1)').removeClass('blind')
				$('#common-alert-popup').removeClass('blind')
				$('.common-alert-txt').text('오류 발생으로 처리되지 못했습니다.')
			}
		});
	}

	$("#common-alert-btn").click(function() {
		$(".popup-background:eq(1)").addClass("blind");
		$("#common-alert-popup").addClass("blind");
	});
});