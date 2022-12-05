/**
 * @author 김예은
 */
$(function() {

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

				console.log(res);

				if (res.length == 0) {
					$(".content_list.activity-list").addClass("blind");
					$(".no-list-wrap").removeClass("blind");
				} else {
					$(".content_list.activity-list").removeClass("blind");
					$(".no-list-wrap").addClass("blind");

					$("#recommend_list_wrap").empty();

					for (var i = 0; i < res.length; i++) {

						sample = "<div class='content_list activity-list' idx=" + res[i].activity_no + ">" +
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
						} else {
							sample += 
								"<div class='cate_tag tag'>" + 
									"<span class='category_name font_size_10'>" + res[i].activity_interest + "</span>" +
								"</div>";
						}

						sample += "</div></div>";

						sample += "<div class='content_img'>" +
							"<img alt='list_img' class='list_img' />" +
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

						$("#recommend_list_wrap").append(sample);
						$("#recommend_list_wrap").find(".list_img:last").attr("src" + "https://meet-a-bwa.s3.ap-northeast-2.amazonaws.com/activity/" + res[i].activity_image );
					}
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


	// 상세 리스트 클릭 이벤트
	$("#meet_recommendSection").on("click", ".content_list.meet-list", function() {
		let idx = $(this).attr("idx");
		location.href = "/meet-a-bwa/meet-main.do?idx=" + idx;
	});
	$("#recommend_list_wrap").on("click", ".content_list.activity-list", function() {
		let idx = $(this).attr("idx");
		location.href = "/meet-a-bwa/activity-main.do?idx=" + idx;
	});

	// 액티비티 추천 - +더보기 버튼 클릭
	$("#plusBtn_act").click(function() {
		let category = $(".tagItem.check").text();
		location.href = "/meet-a-bwa/activity-list.do?category=" + category + "&&searchWord=";
	});

	// 모임 좋아요 처리
	let like_meet_arr = $("#like_meet_list").val();
	if (like_meet_arr != '') {
		like_meet_arr = like_meet_arr.split("/");

		let meet_elements = $(".content_list.meet-list").slice();
		for (like_meet of like_meet_arr) {
			for (list of meet_elements) {
				if ($(list).attr("idx") == like_meet) {
					$(list).find(".beforeLike_heart").addClass("blind");
					$(list).find(".afterLike_heart").removeClass("blind");
				}
			}
		}
	}

	// 액티비티 좋아요 처리
	let like_activity_arr = $("#like_activity_list").val();
	if (like_activity_arr != '') {
		like_activity_arr = like_activity_arr.split("/");

		let activity_elements = $(".content_list.activity-list").slice();
		for (like_activity of like_activity_arr) {
			for (list of activity_elements) {
				if ($(list).attr("idx") == like_activity) {
					$(list).find(".beforeLike_heart").addClass("blind");
					$(list).find(".afterLike_heart").removeClass("blind");
				}
			}
		}
	}
});