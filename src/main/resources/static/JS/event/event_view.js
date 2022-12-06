/**
 * @author 김예은
 */
$(function() {
	let event_idx = "";
	let toast_flag = true;
	let ajax_flag2 = true;
	
	
	// 공용 알러트 창 닫기버튼
	$("#common-alert-btn").click(function() {
		if ($(this).attr("reload")) {
			$(this).attr("reload", false);
			window.location.reload();
		}
		else $("#common-alert-popup-wrap").addClass("blind");
	});
	
	
	/***************************************** */
	/**************** 이벤트 상세 *************** */
	/***************************************** */
	
	// 이벤트 상세 보기 클릭 이벤트
	$(".content_list_activity.event-list.event_action").click(function() {
		idx = $(this).attr("idx");
		ajax_load(idx);
		$("#event-view").removeClass("blind");
	});
	
	// 이벤트 확인 창 확인 버튼 클릭 이벤트
	$("#event_okBtn").click(function() {
		event_idx = "";
		$("#event_u_title_text_length").text("0/15");
		$("#event_u_description_text_length").text("0/200");
		$("#event-view").addClass("blind");
		$("#event_dropdown").addClass("blind");
	});
	
	// 이벤트 커스텀 셀렉트
	$("#event_more_vertival").click(function() {
		$("#event_dropdown").toggleClass("blind");
	});
	// 이벤트 커스텀 셀렉트 - 삭제 버튼 클릭
	$("#event_delete").click(function() {
		$("#event_dropdown").addClass("blind");
		$("#event_confirmWrap").removeClass("blind");
	});
	// 이벤트 커스텀 셀렉트 - 수정 버튼 클릭
	$("#event_update").click(function() {
		$("#event-view").addClass("blind");
		$("#event_dropdown").addClass("blind");

		$("#event_cu-wrap").removeClass("blind");
		$("#event-update-wrap").removeClass("blind");
	});


	/***************************************** */
	/**************** 이벤트 삭제 *************** */
	/***************************************** */
	
	// 삭제 컨펌창 - 예 버튼 클릭 이벤트
	var event_delete_flag = true;
	$("#e_yesBtn").click(function() {
		if(event_delete_flag){
			//로딩 화면
			$("#spinner-wrap").removeClass("blind");
			event_delete_flag = false;
	
			$.ajax({
				url: "/meet-a-bwa/event_delete.do",
				type: "POST",
				dataType: 'json', // 결과값 받을 타입
				data: {
					event_no: event_idx,
					activity_no: location.href.split("activity_no=")[1]
				},
				success: function(res) {
					event_delete_flag = true;
					$("#spinner-wrap").addClass("blind");
	
					if (res.result == "1") {
						$("#event-view").addClass("blind");
	
						$("#common-alert-popup-wrap").removeClass("blind");
						$(".common-alert-txt").html("이벤트가 삭제되었습니다.");
						$("#common-alert-btn").attr("reload", true);
					} else if (res.result == "0") {
						fade_in_out(undefined, undefined, "오류로 인해 이벤트 삭제에 실패하였습니다.");
					}
				},
				error: function() {
					event_delete_flag = true;
					$("#spinner-wrap").addClass("blind");
					fade_in_out(undefined, undefined, "오류로 인해 이벤트 삭제에 실패하였습니다.");
				}
			});
		}
	});

	// 삭제 컨펌창 - 아니오 버튼 클릭 이벤트
	$("#e_noBtn").click(function() {
		$("#event_confirmWrap").addClass("blind");
	});



	/***************************************** */
	/**************** 이벤트 수정 *************** */
	/***************************************** */
	// 이벤트 수정 - 창닫기 버튼
	$("#e_u_closeBtn").click(function() {
		toast_flag = true;
		ajax_flag2 = true;

		$("#eu_customTimePicker").addClass("blind");

		let a_sample = $("#eu_ampm_listWrap").find(".sample").clone();
		$("#eu_ampm_listWrap").empty().append(a_sample);

		let t_sample = $("#eu_time_listWrap").find(".sample").clone();
		$("#eu_time_listWrap").empty().append(t_sample);

		let m_sample = $("#eu_minute_listWrap").find(".sample").clone();
		$("#eu_minute_listWrap").empty().append(m_sample);

		$("#event_cu-wrap").addClass("blind");
		$("#event-update-wrap").addClass("blind");
	});
	
	// 이벤트 수정 - 수정 버튼
	var event_update_flag = true;
	$("#event_updateBtn").click(function() {
		let title_length = $("#event_u_title").val().trim().length;
		let endDate_length = $("#u_eventDate").val().length;
		let time = $("#event_u_timeValue").text().includes("--");
		let description_length = $("#event_u_event_description").val().trim().length;

		if (title_length > 0 && endDate_length > 0 && !time && description_length > 0) {
			let tmp_time = "";
			let arr = $("#event_u_timeValue").text().trim().split(":");
			let ampm = arr[0];
			if (ampm == "오후") {
				if (Number(arr[1]) != 12) {
					tmp_time = (Number(arr[1]) + 12) + ":" + arr[2] + ":59";
				} else {
					tmp_time = arr[1] + ":" + arr[2] + ":59";
				}
			} else if (ampm == "오전") {
				tmp_time = arr[1] + ":" + arr[2] + ":59";
			}
			if(event_update_flag)
				update_ajax($("#u_eventDate").val() + " " + tmp_time);
		}
		else {
			fade_in_out(undefined, undefined, "빈 항목이 존재합니다.");
		}
	});


	/************************************************ */
	/*************** 커스텀 타임피커 SECTION **************/
	/************************************************ */
	
	// 시간 설정 SECTION
	$("#eu_t_box").click(function() {
		$("#eu_customTimePicker").toggleClass("blind");

		if (ajax_flag2) {
			$.ajax({
				url: "/json/time.json",
				success: function(res) {
					ajax_flag2 = false;
					ampm_list(res.type);
					time_list(res.time);
					minute_list(res.minute);
				}
			});
		}
	});

	$("#eu_customTimePicker").click(function(event) {
		event.stopPropagation();
	});

	$("#eu_ampm_choice").on("click", ".ampm-list", function() {
		$("#eu_ampm_choice").find(".ampm-list").removeClass("time_choice");
		$(this).addClass("time_choice");

		let arr = $("#event_u_timeValue").text().split(":");
		arr[0] = $(this).attr("type");

		$("#event_u_timeValue").text(arr.join(":"));

		if ($("#eu_ampm_choice").find(".ampm-list").hasClass("time_choice") && $("#eu_time_choice").find(".time-list").hasClass("time_choice") && $("#eu_minute_choice").find(".minute-list").hasClass("time_choice")) {
			$("#eu_ampm_choice").find(".ampm-list").removeClass("time_choice");
			$("#eu_time_choice").find(".time-list").removeClass("time_choice");
			$("#eu_minute_choice").find(".minute-list").removeClass("time_choice");

			$("#eu_customTimePicker").addClass("blind");
		}
	});
	$("#eu_time_choice").on("click", ".time-list", function() {
		$("#eu_time_choice").find(".time-list").removeClass("time_choice");
		$(this).addClass("time_choice");

		let arr = $("#event_u_timeValue").text().split(":");
		arr[1] = $(this).attr("time");

		$("#event_u_timeValue").text(arr.join(":"));

		if ($("#eu_ampm_choice").find(".ampm-list").hasClass("time_choice") && $("#eu_time_choice").find(".time-list").hasClass("time_choice") && $("#eu_minute_choice").find(".minute-list").hasClass("time_choice")) {
			$("#eu_ampm_choice").find(".ampm-list").removeClass("time_choice");
			$("#eu_time_choice").find(".time-list").removeClass("time_choice");
			$("#eu_minute_choice").find(".minute-list").removeClass("time_choice");

			$("#eu_customTimePicker").addClass("blind");
		}
	});
	$("#eu_minute_choice").on("click", ".minute-list", function() {
		$("#eu_minute_choice").find(".minute-list").removeClass("time_choice");
		$(this).addClass("time_choice");

		let arr = $("#event_u_timeValue").text().split(":");
		arr[2] = $(this).attr("minute");

		$("#event_u_timeValue").text(arr.join(":"));

		if ($("#eu_ampm_choice").find(".ampm-list").hasClass("time_choice") && $("#eu_time_choice").find(".time-list").hasClass("time_choice") && $("#eu_minute_choice").find(".minute-list").hasClass("time_choice")) {
			$("#eu_ampm_choice").find(".ampm-list").removeClass("time_choice");
			$("#eu_time_choice").find(".time-list").removeClass("time_choice");
			$("#eu_minute_choice").find(".minute-list").removeClass("time_choice");

			$("#eu_customTimePicker").addClass("blind");
		}
	});

	/****************************************** */
	/***************** 글자수 제한 *****************/
	/****************************************** */
	
	// 이벤트 제목 글자수 제한
	$("#event_u_title").keydown(function() {
		text_limit(15, $(this), $("#event_u_title_text_length"));
	});
	$("#event_u_title").keyup(function() {
		text_limit(15, $(this), $("#event_u_title_text_length"));
	});

	// 투표 설명 글자수 제한
	$("#event_u_event_description").keydown(function() {
		text_limit(200, $(this), $("#event_u_description_text_length"));
	});
	$("#event_u_event_description").keyup(function() {
		text_limit(200, $(this), $("#event_u_description_text_length"));
	});


	/************************************************/
	/****************** DATEPICKER ******************/
	/************************************************/
	$("#u_eventDate").datepicker({
		changeYear: true,
		changeMonth: true
	});


	/********************************************* */
	/***************** 함수 SECTION *****************/
	/********************************************* */
	
	// 글자수 제한 및 글자수 표기 함수
	function text_limit(max_length, element, length_txt_element) {
		if (element.val().length > max_length) {
			fade_in_out(element, max_length, "글자수를 초과하였습니다.");
		}

		if (length_txt_element != undefined)
			length_txt_element.text(element.val().length + "/" + max_length);
	}
	// 토스트 함수
	function fade_in_out(element, max_length, text) {
		if (element != undefined && max_length != undefined)
			element.val(element.val().substr(0, max_length));

		if (toast_flag) {
			toast_flag = false;
			$("#toast_txt").text(text);
			$("#toastWrap").removeClass("hide");
			$("#toastWrap").removeClass("fade-out");
			$("#toastWrap").addClass("fade-in");

			setTimeout(function() {
				toast_flag = true; // 추후에 사용할 수 있도록 변수값 변경
				$("#toastWrap").removeClass("fade-in");
				$("#toastWrap").addClass("fade-out");
			}, 1000);
		}
	}

	function ampm_list(arr) {
		for (x of arr) {
			let sample = $("#eu_ampm_listWrap").children(".sample").clone();
			sample.removeClass("sample");
			sample.text(x);
			sample.attr("type", x);

			$("#eu_ampm_listWrap").append(sample);
		}
	}
	function time_list(arr) {
		for (x of arr) {
			let sample = $("#eu_time_listWrap").children(".sample").clone();
			sample.removeClass("sample");
			sample.text(x);
			sample.attr("time", x);

			$("#eu_time_listWrap").append(sample);
		}
	}
	function minute_list(arr) {
		for (x of arr) {
			let sample = $("#eu_minute_listWrap").children(".sample").clone();
			sample.removeClass("sample");
			sample.text(x);
			sample.attr("minute", x);

			$("#eu_minute_listWrap").append(sample);
		}
	}

	// 이벤트 정보 불러오기 AJAX
	function ajax_load(idx) {
		//로딩 화면
		$("#spinner-wrap").removeClass("blind");
		
		$.ajax({
			url: "/meet-a-bwa/event_view.do",
			type: "GET",
			data: {
				event_no: idx
			},
			dataType: "JSON",
			success: function(res) {
				$("#spinner-wrap").addClass("blind");
				event_idx = res.event_no;

				$("#event-view-title").text(res.event_title);
				$("#event_update").attr("idx", res.event_no);
				$("#event_delete").attr("idx", res.event_no);
				$("#event_w_date").text("작성일 : " + res.event_date);
				$("#event_writer").text("작성자 : " + res.user_name);

				if (res.user_no != $.cookie("user_no")) {
					$("#event_more_vertival").addClass("blind");
				}

				let date = res.event_d_day.split(" ")[0];
				let dateArr = date.split("/");
				let dateString = dateArr[0] + "년 " + dateArr[1] + "월 " + dateArr[2] + "일";
				let time = res.event_d_day.split(" ")[1];
				let timeArr = time.split(":");
				let timeString = "";

				if (timeArr[0] > 12) {
					if ((timeArr[0] - 12) < 10) {
						timeString = "오후0" + (timeArr[0] - 12) + "시 " + timeArr[1] + "분";
					} else {
						timeString = "오후" + (timeArr[0] - 12) + "시 " + timeArr[1] + "분";
					}
				} else {
					timeString = "오전" + timeArr[0] + "시 " + timeArr[1] + "분";
				}

				$("#event_date").text(dateString + " " + timeString);
				$("#event_view_description").text(res.event_description);

				// 이벤트 수정에도 미리 배치
				$("#event_u_title").val(res.event_title);
				$("#event_u_title_text_length").text(res.event_title.length + "/15");
				$("#u_eventDate").val(date);
				$("#event_u_timeValue").text(timeString.substring(0, 2) + ":" + timeString.substring(2, 4) + ":" + timeArr[1]);
				$("#event_u_event_description").text(res.event_description);
				$("#event_u_description_text_length").text(res.event_description.length + "/200");

			},error: function() {
				$("#spinner-wrap").addClass("blind");
				fade_in_out(undefined, undefined, "오류로 인해 이벤트 정보 불러오기에 실패하였습니다.");
			}
		})
	}
	
	// 이벤트 수정 AJAX
	function update_ajax(time) {
		//로딩 화면
		$("#spinner-wrap").removeClass("blind");
		event_update_flag = false;

		$.ajax({
			url: "/meet-a-bwa/event_update.do",
			type: "POST",
			dataType: 'json', // 결과값 받을 타입
			data: {
				event_no: event_idx,
				event_title: $("#event_u_title").val().trim(),
				event_description: $("#event_u_event_description").val().trim(),
				event_d_day: $("#eventDate").val().trim() + " " + time
			},
			success: function(res) {
				event_update_flag = true;
				$("#spinner-wrap").addClass("blind");

				if (res.result == "1") {
					$("#event_cu-wrap").addClass("blind");
					$("#event-update-wrap").addClass("blind");

					$("#common-alert-popup-wrap").removeClass("blind");
					$(".common-alert-txt").html("이벤트가 수정되었습니다.");
					$("#common-alert-btn").attr("reload", true);
				} else if (res.result == "0") {
					fade_in_out(undefined, undefined, "오류로 인해 이벤트 수정에 실패하였습니다.");
				}
			},
			error: function() {
				event_update_flag = true;
				$("#spinner-wrap").addClass("blind");
				fade_in_out(undefined, undefined, "오류로 인해 이벤트 수정에 실패하였습니다.");
			}
		});
	}

});