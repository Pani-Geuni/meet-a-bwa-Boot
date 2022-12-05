/**
 * @author 김예은
 */

$(function() {

    // 공용 알러트 창 닫기버튼
    $("#common-alert-btn").click(function(){
        $("#common-alert-popup-wrap").addClass("blind");
    });

	// 로그인 팝업 - 창닫기 버튼 클릭
	$("#login-popup-closeBtn").click(function() {
		$('#idInput').removeClass("length_error");
		$('#pwInput').removeClass("length_error");
		$(".login-layer").addClass("blind");
	});

	// ID 찾기 버튼 클릭
	$("#id_find_btn").click(function() {
		$("#id_find_wrap").removeClass("blind");
	});
	// PW 찾기 버튼 클릭
	$("#pw_find_btn").click(function() {
		$("#pw_find_wrap").removeClass("blind");
	});


	// 로그인 버튼 클릭 시 제출 전에 아이디/비번 입력되었는지 확인
	$('#loginOK_btn').click(function() {
		$('#idInput').removeClass("length_error");
		$('#pwInput').removeClass("length_error");

		if ($('#idInput').val().trim().length > 0) {
			if ($('#pwInput').val().trim().length > 0) {
				//로딩 화면
				$("#spinner-wrap").removeClass("blind");

				$.ajax({
					url: "/meet-a-bwa/m_loginOK.do",
					type: "POST",
					dataType: 'json',
					data: {
						username: $("#idInput").val().trim(),
						password: $("#pwInput").val().trim()
					},
					success: function(res) {
						//로딩 화면 닫기
						$("#spinner-wrap").addClass("blind");

						// 이메일 인증번호 확인 성공
						if (res.result == 1) {
							window.location.reload();
						} else {
							$("#common-alert-popup-wrap").removeClass("blind");
							$(".common-alert-txt").text("로그인에 실패하였습니다.\n다시 시도해주세요!");
						}
					},
					error: function() {
						//로딩 화면 닫기
						$("#spinner-wrap").addClass("blind");

						$("#common-alert-popup-wrap").removeClass("blind");
						$(".common-alert-txt").text("오류로 인해 로그인에 실패하였습니다.\n이용에 불편을 드려 죄송합니다!");
					}
				});
			} else {
				$('#pwInput').addClass("length_error");
			}
		} else {
			$('#idInput').addClass("length_error");

			if ($('#pwInput').val().trim().length == 0) {
				$('#pwInput').addClass("length_error");
			}
		}
	});

});