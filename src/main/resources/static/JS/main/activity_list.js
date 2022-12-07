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

	// 액티비티 추천 - 카테고리 더보기 버튼 클릭 이벤트
	$("#plusImg").click(function() {
		$("#fold_tag").removeClass("blind");
		$(this).addClass("blind");
		$("#foldImg").removeClass("blind");
	});

	// 액티비티 추천 - 카테고리 접기 버튼 클릭 이벤트
	$("#foldImg").click(function() {
		$("#fold_tag").addClass("blind");
		$(this).addClass("blind");
		$("#plusImg").removeClass("blind");
	});

	// 액티비티 추천 - 카테고리 클릭 이벤트
	$(".tagItem").click(function(e) {
		$(".tagItem").removeClass("check");
		$(e.target).addClass("check");

		let category = $(this).text();
		//로딩 화면
		$("#spinner-wrap").removeClass("blind");

		$.ajax({
			url: "/select_activity_category",
			type: "GET",
			dataType: 'json',
			data: {
				category: category
			},
			success: function(res) {
				//로딩 화면 닫기
				$("#spinner-wrap").addClass("blind");

				if (res.length == 0) {
					$(".content_list.activity-list").addClass("blind");
					$(".no-list-wrap:last").removeClass("blind");
				}
				else {
					$(".content_list.activity-list").removeClass("blind");
					$(".no-list-wrap:last").addClass("blind");

					$("#has_a_list").empty();

					for (var i = 0; i < res.length; i++) {

						sample =
							"<div class='content_list activity-list' idx=" + res[i].activity_no + ">" +
							"<div class='info-list-wrap'>" +
							"<div class='listCommon'>" +
							"<span class='content_title'>" + res[i].activity_name + "</span>" +
							"</div>" +
							"<div class='description_list listCommon'>" +
							"<span class='content_description'>" + res[i].activity_info + "</span>" +
							"</div>" +
							"<div class='listCommon'>" +
							"<div class='tagSection'>";

						if (res[i].activity_county != null) {
							sample +=
								"<div class='loca_tag tag'>" +
								"<img src='/IMG/common/map.png' class='tag_img'>" +
								"<span class='location_name font_size_10'>" + res[i].activity_county + "</span>" +
								"</div>";
						}
						else {
							sample +=
								"<div class='cate_tag tag'>" +
								"<span class='category_name font_size_10'>" + res[i].activity_interest + "</span>" +
								"</div>";
						}

						sample += "</div></div>";

						sample += "<div class='content_img'>" +
							"<img src=" + res[i].activity_image + " alt='list_img' class='list_img' />" +
							"</div></div>";

						sample +=
							"<div class='bottomWrap'>" +
							"<div class='meet_info'>" +
							"<div class='meet_member_info'>" +
							"<span class='member_cnt member_ment'>" + res[i].user_cnt + "명</span>" +
							"<span class='member_ment'>참여중</span>" +
							"</div>";

						if (res[i].activity_age_arr != null) {
							sample +=
								"<div class='meet_condition'>" +
								"<img src='/IMG/common/line.svg' alt='line이미지' class='divide'/>" +
								"<span class='condition_bold condition_common'><b>모집</b></span>";

							for (var j = 0; j < res[i].activity_age_arr.length; j++) {
								sample += "<span class='condition_regular condition_common age_condition'>" + res[i].activity_age_arr[j] + "</span>"
							}

							sample += "</div>";

						}
						sample += "</div>";

						sample += "<div class='likeWrap'>" +
							"<section class='heartSection' idx=" + res[i].activity_no + ">" +
							"<img src='/IMG/common/heart-outlined.svg' alt='라인하트이미지' class='beforeLike_heart heartCommon' />" +
							"<img src='/IMG/common/heart-filled.svg' alt='풀하트이미지' class='afterLike_heart heartCommon blind' />" +
							"</section>" +
							"<span class='likeCnt'>" + res[i].like_cnt + "</span>" +
							"</div> </div> </div > ";

						$("#has_a_list").append(sample);
					}

					like_meet_load();
					like_activity_load();
				}
			},
			error: function() {
				//로딩 화면 닫기
				$("#spinner-wrap").addClass("blind");

				$("#common-alert-popup-wrap").removeClass("blind");
				$(".common-alert-txt").html("오류로 인해 카테고리별 검색에 실패하였습니다.<br>이용에 불편을 드려 죄송합니다!");
			}
		});
	});


	$("#activity-list-section").on("click", ".heartSection", function(event) {
		if ($(this).prop('tagName') == 'SECTION') {
			if (is_login != null) {
				// 좋아요 추가
				if ($(this).find(".afterLike_heart").hasClass("blind")) {
					//로딩 화면
					$("#spinner-wrap").removeClass("blind");
					
					var tmp_this = $(this);
					
					$.ajax({
						url: "/main_activity_like_insert",
						type: "GET",
						dataType: 'json',
						data: {
							activity_no: $(this).attr("idx"),
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
						url: "/main_activity_like_delete",
						type: "GET",
						dataType: 'json',
						data: {
							activity_no: $(this).attr("idx"),
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
		}
		return false;
	});

	// 액티비티 추천 - +더보기 버튼 클릭
	$("#plusBtn_act").click(function() {
		let category = $(".tagItem.check").text();
		location.href = "/meet-a-bwa/activity-list.do?category=" + category + "&&searchWord=";
	});


	like_activity_load();

	// 액티비티 좋아요 처리
	function like_activity_load() {
		let like_activity_arr = $("#like_activity_list").val();

		if (like_activity_arr != '') {
			var arr = like_activity_arr.split(",");
			arr = arr.slice(0, arr.length - 1);

			let activity_elements = $(".content-list.activity-list").slice();
			for (like_activity of arr) {
				for (list of activity_elements) {
					if ($(list).attr("idx") == like_activity) {
						$(list).find(".beforeLike_heart").addClass("blind");
						$(list).find(".afterLike_heart").removeClass("blind");
					}
				}
			}
		}
	}
});