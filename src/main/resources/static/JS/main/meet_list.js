/**
 * @author 김예은
 */
$(function() {

	// 공용 알러트 창 닫기버튼
	$("#common-alert-btn").click(function() {
		if ($(this).attr("reload")) {
			$(this).attr("reload", false);
			window.location.reload();
		}
		else $("#common-alert-popup-wrap").addClass("blind");
	});


	// 좋아요 클릭 이벤트
	$("#meet_recommendSection").on("click", ".heartSection", function() {
		if ($(this).prop('tagName') == 'SECTION') {
			if (is_login != null) {
				// 좋아요 추가
				if ($(this).find(".afterLike_heart").hasClass("blind")) {
					//로딩 화면
					$("#spinner-wrap").removeClass("blind");
					
					var tmp_this = $(this);
					
					$.ajax({
						url: "/main_meet_like_insert",
						type: "GET",
						dataType: 'json',
						data: {
							meet_no: $(this).attr("idx"),
							user_no: $.cookie("user_no")
						},
						success: function(res) {
							//로딩 화면 닫기
							$("#spinner-wrap").addClass("blind");

							if (res.result == "1") {
								tmp_this.next(".likeCnt").text(Number(tmp_this.next(".likeCnt").text()) + 1);
								tmp_this.find(".beforeLike_heart").addClass("blind");
								tmp_this.find(".afterLike_heart").removeClass("blind");
							}
							else {
								$("#common-alert-popup-wrap").removeClass("blind");
								$(".common-alert-txt").html("좋아요 추가에 실패하였습니다.");
							}
						},
						error: function() {
							//로딩 화면 닫기
							$("#spinner-wrap").addClass("blind");

							$("#common-alert-popup-wrap").removeClass("blind");
							$(".common-alert-txt").html("오류로 인해 좋아요 추가에 실패하였습니다.<br>이용에 불편을 드려 죄송합니다!");
						}
					});
				}
				// 좋아요 삭제
				else {
					//로딩 화면
					$("#spinner-wrap").removeClass("blind");
					
					var tmp_this = $(this);
					
					$.ajax({
						url: "/main_meet_like_delete",
						type: "GET",
						dataType: 'json',
						data: {
							meet_no: $(this).attr("idx"),
							user_no: $.cookie("user_no")
						},
						success: function(res) {
							//로딩 화면 닫기
							$("#spinner-wrap").addClass("blind");

							if (res.result == "1") {
								tmp_this.next(".likeCnt").text(Number(tmp_this.next(".likeCnt").text()) - 1);
								tmp_this.find(".beforeLike_heart").removeClass("blind");
								tmp_this.find(".afterLike_heart").addClass("blind");
							}
							else {
								$("#common-alert-popup-wrap").removeClass("blind");
								$(".common-alert-txt").html("좋아요 삭제에 실패하였습니다.");
							}
						},
						error: function() {
							//로딩 화면 닫기
							$("#spinner-wrap").addClass("blind");

							$("#common-alert-popup-wrap").removeClass("blind");
							$(".common-alert-txt").html("오류로 인해 좋아요 삭제에 실패하였습니다.<br>이용에 불편을 드려 죄송합니다!");
						}
					});
				}
			} else {
				$(".warning-layer").removeClass("blind");
			}
			
			return false;
		}
	});


	like_meet_load();

	// 모임 좋아요 처리
	function like_meet_load() {
		let like_meet_arr = $("#like_meet_list").val();

		if (like_meet_arr != '') {
			var arr = like_meet_arr.split(",");
			arr = arr.slice(0, arr.length - 1);

			let meet_elements = $(".content-list.meet-list").slice();
			for (like_meet of arr) {
				for (list of meet_elements) {
					if ($(list).attr("idx") == like_meet) {
						$(list).find(".beforeLike_heart").addClass("blind");
						$(list).find(".afterLike_heart").removeClass("blind");
					}
				}
			}
		}
	}
})