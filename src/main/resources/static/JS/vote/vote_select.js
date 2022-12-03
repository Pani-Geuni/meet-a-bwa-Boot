/**
* @author 김예은
*/
$(function() {
	let idx = '';
	let vote_idx = '';
	let choice_idx = "";
	let flag = false;
	let updateFlag = false;
	
	let vote_no = "";
	let title = "";
	let description = "";
	let vote_time = "";
	let vote_date = "";
	let vote_contents = "";
	let toast_flag = true;
	let ajax_flag = true;
	
	
	// 오른쪽 섹션 - 투표 생성 버튼 클릭 (+)
	$("#vote_create_btn").click(function() {
		$(".vote-create-update-wrap").removeClass("blind");
		$("#event-create").removeClass("blind");
	});

	// 투표 리스트 선택 - 투표 보기
	$("#vote-summary-list").on("click", ".vote-list-item", function(){
		idx = $(this).attr("idx");
		ajax_load(idx);
	});
	
	
	/********************************************************************/
	/******************** 투표 보기 관련 팝업 내 이벤트 섹션 **********************/
	/********************************************************************/

	// 세팅 초기화
    $(".vote_closeBtn, #view_closeBtn2").click(function(){
    	$(".vote-view-wrap").addClass("blind");
    	$(".vote-view-wrap>.select_custom").addClass("blind");
    	$(".vote-view-wrap>.choice_mem_cnt").addClass("blind");
    	$(".vote-view-wrap>.list_percentage_wrap").addClass("blind");
    	$("#choice_wrap").find(".in_circle").removeClass("choice");
    	$(".voteBtn").addClass("blind");
    	$(".reVoteBtn").addClass("blind");
    	$(".view_closeBtn").addClass("blind");
    	$("#dropdown").addClass("blind");
    	
    	
    	let sample = $(".choiceList:eq(0)").clone();
    	$("#choice_wrap").empty().append(sample);
    });
    
    // 투표 버튼 클릭
    $("#voteBtn").click(function(){
    	let isChoice = $("#choice_wrap").find(".in_circle").hasClass("choice");
    	
    	if(!isChoice){
    		fade_in_out("투표 항목을 선택해주세요.");
    	}else{
    		let c_idx = $("#choice_wrap").find(".in_circle.choice").attr("contentidx");
    		
    		if(updateFlag){
    			location.href = "/meet-a-bwa/re_voteOK.do?vote_no=" + idx + "&user_no=" + $.cookie("user_no") + "&content_no=" + c_idx + "&meet_no=" + location.href.split("idx=")[1];
    		}else{
    			location.href = "/meet-a-bwa/voteOK.do?vote_no=" + idx + "&user_no=" + $.cookie("user_no") + "&content_no=" + c_idx + "&meet_no=" + location.href.split("idx=")[1];
    		}
    	}
    });
    // 재투표 버튼 클릭
    $("#re_voteBtn").click(function(){
    	flag = true;
    	
    	$(".reVoteBtn").addClass("blind");
    	$(".voteBtn").removeClass("blind");
    	$("#choice_wrap").find(".choice_mem_cnt").addClass("blind");
		$("#choice_wrap").find(".list_percentage_wrap").addClass("blind");
    });	
    
    // 투표 마감 여부 묻는 컨펌 창 버튼 이벤트
    $("#end_yesBtn").click(function(){
    	location.href = "/meet-a-bwa/m_vote_stateUpdate.do?vote_no=" + vote_idx + "&meet_no=" + location.href.split("idx=")[1];
	});
    $("#end_noBtn").click(function(){
		$(".update-confirm-wrap").addClass("blind");
	});
	
	
	// 커스텀 셀렉트 show, hide
    $("#more_vertival").click(function(){
        $("#dropdown").toggleClass("blind");
    });
	// 투표 종료 리스트 선택
	$("#end").click(function(){
		vote_idx = $(this).attr("voteIdx");
		$(".update-confirm-wrap").removeClass("blind");
    });
	// 커스텀 셀렉트 선택 시 커스텀 셀렉트 닫고 삭제 여부 묻는 삭제 컨펌창 띄움
	$("#delete").click(function(){
		vote_idx = $(this).attr("voteIdx");

        $("#confirmWrap").removeClass("blind");
        $("#dropdown").addClass("blind");
    });
    // 커스텀 셀렉트 중 수정 클릭 -> 창 초기화 하여 닫은 후 수정창 오픈
    $("#update").click(function(){
    	let num = 0;
    	
    	// 세팅 초기화 후 수정 팝업 open
    	$(".vote-view-wrap").addClass("blind");
    	$(".vote-view-wrap>.select_custom").addClass("blind");
    	$(".vote-view-wrap>.choice_mem_cnt").addClass("blind");
    	$(".vote-view-wrap>.list_percentage_wrap").addClass("blind");
    	$("#choice_wrap").find(".in_circle").removeClass("choice");
    	$(".voteBtn").addClass("blind");
    	$(".reVoteBtn").addClass("blind");
    	$(".view_closeBtn").addClass("blind");
    	$("#dropdown").addClass("blind");
    	
    	
    	let sample = $(".choiceList:eq(0)").clone();
    	$("#choice_wrap").empty().append(sample);
    	
    	$(".vote-view-wrap").addClass("blind");
    	$(".vote-create-update-wrap").removeClass("blind");
    	$("#event-create").addClass("blind");
    	
    	
    	$("#event-update").removeClass("blind");
    	$("#u_vote_title").val(title);
    	$("#u_title_text_length").text(title.length + "/15");
    	$("#u_vote_endDate").val(vote_date);
    	$("#u_timeValue").text(vote_time);
    	$("#u_vote_description").val(description);
    	$("#u_description_text_length").text(description.length + "/150");

    	for(content of vote_contents){
    		let sample = $("#u_vote_list_Wrap").find(".sample").clone();
    		sample.removeClass("sample");
    		sample.removeClass("blind");
    		
    		sample.find(".list_title").text(++num);
    		sample.find(".list_text").val(content);
    		$("#u_vote_list_Wrap").append(sample);
    	}
    });
    
    // 투표 항목 선택 시 UI변경
    $("#choice_wrap").on("click", ".out_circle", function(){
   		if(flag){
	    	$("#choice_wrap").find(".in_circle").removeClass("choice");
	    	$(this).find(".in_circle").addClass("choice");
	    	choice_idx = $(this).find(".in_circle").attr("contentIdx");
   		}
    });
    
    // 삭제 컨펌 팝업 - '예'버튼 클릭 시 삭제 로직 처리
	$("#yesBtn").click(function(){
        location.href = "/meet-a-bwa/m_vote_delete.do?vote_no=" + vote_idx + "&meet_no=" + location.href.split("idx=")[1];
    });
    // 삭제 컨펌 팝업에서 아니오 클릭
	$("#noBtn").click(function(){
        $("#confirmWrap").addClass("blind");
    });
	
	function ajax_load(idx) {
		$.ajax({
			url : "/meet-a-bwa/m_vote_view.do",
			type : "GET",
			data : {
				user_no : $.cookie("user_no"),
				vote_no : idx
			},
			
			dataType: "JSON",
			success: function(res) {
				vote_no =  res.vote_no;
				title = res.vote_title;
				description = res.vote_description;
				vote_date = res.vote_eod.split(" ")[0];
				vote_contents = res.content_arr.map(v => v.content).slice();
				
				// 투표 정보 세팅
				$("#delete").attr("voteIdx", res.vote_no);
				$("#end").attr("voteIdx", res.vote_no);
				$(".vote-view-wrap").removeClass("blind");
				$("#view-title").text(res.vote_title);
				$(".vote-view-wrap>.select_list_wrap").attr("voteNo", res.vote_no);
				$(".vote-view-wrap>.select_list_wrap").attr("voteNo", res.vote_no);
				$("#text_area").text(res.vote_description);
				$("#vote_writer").text("작성자 : " + res.user_name);
				
				let date = res.vote_eod.split(" ")[0];
				let time = res.vote_eod.split(" ")[1];
				let timeArr = time.split(":");
				let timeString = "";

				if(timeArr[0] > 12){
					if((timeArr[0]-12) < 10){
						timeString = "오후0"+ (timeArr[0]-12)  + ":" + timeArr[1] + ":" + timeArr[2];
					}else{
						timeString = "오후"+ (timeArr[0]-12)  + ":" + timeArr[1] + ":" + timeArr[2];
					}
				}else{
					timeString = "오전"+ timeArr[0] + ":" + timeArr[1] + ":" + timeArr[2];
				}
				vote_time = timeString.substring(0,2) + ":" + timeString.substring(2,4) +  ":" + timeArr[1];
				
				
				// 작성자일 때만 수정/삭제 가능
				if(res.user_no != $.cookie("user_no")){
					$("#more_vertival").addClass("blind");
				}else{
					$("#more_vertival").removeClass("blind");
				}
				
				
				// 마감날짜 안지났고 / 작성자가 마감시키지 않았을 때 => 투표 / 재투표 가능
				if(new Date().getTime() <= new Date(res.vote_eod).getTime() && res.vote_state == "Y"){
					$("#end_date").text("투표마감 : " + date + " " + timeString);
					$("#end").removeClass("blind");
					
					// 투표한적없음
					if(res.isVote == ""){
						flag = true;
						updateFlag = false;
						
						$(".voteBtn").removeClass("blind");
						
						// 투표 항목 세팅
						for(list of res.content_arr){
							let sample = $(".choiceList:eq(0)").clone().removeClass("blind");
							
							sample.find(".txt").text(list.content);
							sample.find(".in_circle").attr("contentIdx", list.content_no);
							$("#choice_wrap").append(sample);
						}
					}else{ // 투표한 적 있음
						updateFlag = true;
						flag = false;
						$(".reVoteBtn").removeClass("blind");
    					
						// 투표 항목 세팅
						for(var i = 0; i < res.content_arr.length; i++){
							let sample = $(".choiceList:eq(0)").clone().removeClass("blind");
							sample.find(".choice_mem_cnt").removeClass("blind");
	    					sample.find(".list_percentage_wrap").removeClass("blind");
							sample.find(".txt").text(res.content_arr[i].content);
							if(res.content_arr[i].content_no == res.isVote){
								sample.find(".in_circle").addClass("choice");
							}
							sample.find(".in_circle").attr("contentIdx", res.content_arr[i].content_no);
							$("#choice_wrap").append(sample);
						}
						
						if(res.vote_result.length > 0){
							let total_cnt = 0;
							for(var j = 0; j < res.vote_result.length; j++){
								total_cnt += Number(res.vote_result[j].cnt);
							}
							
							let tmp_arr = $("#choice_wrap").children(".choiceList:gt(0)").slice();
							for(var i = 0; i < tmp_arr.length; i++){
								let tmp = false;
								for(var j = 0; j < res.vote_result.length; j++){
									if($(tmp_arr[i]).find(".in_circle").attr("contentIdx") == res.vote_result[j].content_no){
										tmp = true;
										$(tmp_arr[i]).find(".choice_mem_cnt").text(res.vote_result[j].cnt + "명");
				    					$(tmp_arr[i]).find(".list_percentage").css("width", Math.round((res.vote_result[j].cnt / total_cnt) * 100)+"%");		
									}
								}
								if(!tmp){
									$(tmp_arr[i]).find(".choice_mem_cnt").text("0명");
			    					$(tmp_arr[i]).find(".list_percentage").css("width", "0%");	
								}
							}
						}
						
					}
				}
				// 투표 참여 / 재참여 불가능
				else{
					flag = false;
					updateFlag = false;
					$(".view_closeBtn").removeClass("blind");
					$("#end").addClass("blind");
					
					if(new Date().getTime() <= new Date(res.vote_eod).getTime() && res.vote_state == "N"){
						$("#end_date").text("투표조기마감");
					}
					else $("#end_date").text("투표마감 : "+ date + " " + timeString);
					
					// 투표 항목 세팅
					for(var i = 0; i < res.content_arr.length; i++){
						let sample = $(".choiceList:eq(0)").clone().removeClass("blind");
						sample.find(".choice_mem_cnt").removeClass("blind");
    					sample.find(".list_percentage_wrap").removeClass("blind");
						sample.find(".txt").text(res.content_arr[i].content);
						if(res.content_arr[i].content_no == res.isVote){
							sample.find(".in_circle").addClass("choice");
						}
						sample.find(".in_circle").attr("contentIdx", res.content_arr[i].content_no);
						$("#choice_wrap").append(sample);
					}
					
					
					if(res.vote_result.length > 0){
						let total_cnt = 0;
						for(var j = 0; j < res.vote_result.length; j++){
							total_cnt += Number(res.vote_result[j].cnt);
						}
						
						let tmp_arr = $("#choice_wrap").children(".choiceList:gt(0)").slice();
						for(var i = 0; i < tmp_arr.length; i++){
							let tmp = false;
							for(var j = 0; j < res.vote_result.length; j++){
								if($(tmp_arr[i]).find(".in_circle").attr("contentIdx") == res.vote_result[j].content_no){
									tmp = true;
									$(tmp_arr[i]).find(".choice_mem_cnt").text(res.vote_result[j].cnt + "명");
			    					$(tmp_arr[i]).find(".list_percentage").css("width", Math.round((res.vote_result[j].cnt / total_cnt) * 100)+"%");		
								}
							}
							if(!tmp){
								$(tmp_arr[i]).find(".choice_mem_cnt").text("0명");
		    					$(tmp_arr[i]).find(".list_percentage").css("width", "0%");	
							}
						}
					}
					
				}
				
			},
			error: function(res, status, text) {
				console.log(text);
			}
		})
	}
	
	
	/********************************************************************/
	/******************** 투표 수정 관련 팝업 내 이벤트 섹션 *********************/
	/********************************************************************/
	$("#closeBtn").click(function(){
		title = "";
		description = "";
		vote_time = "";
		vote_date = "";
		vote_contents = "";
		toast_flag = true;
		ajax_flag = true;
		
		$("#u_customTimePicker").addClass("blind");
		
		let a_sample = $("#u_ampm_listWrap").find(".sample").clone();
		$("#u_ampm_listWrap").empty().append(a_sample);
		
		let t_sample = $("#u_time_listWrap").find(".sample").clone();
		$("#u_time_listWrap").empty().append(t_sample);
		
		let m_sample = $("#u_minute_listWrap").find(".sample").clone();
		$("#u_minute_listWrap").empty().append(m_sample);
		
        sample = $("#u_vote_list_Wrap").find(".sample").clone();
        $("#u_vote_list_Wrap").empty().append(sample);
        
		$(".vote-create-update-wrap").addClass("blind");
		$("#event-update").addClass("blind");
    });
    $("#vote_updateBtn").click(function(){
    	let title_length = $("#u_vote_title").val().trim().length;
        let endDate_length = $("#u_vote_endDate").val().trim().length;
        let time = $("#u_timeValue").text().trim().includes("--");
        let description_length = $("#u_vote_description").val().trim().length;
        
        
        if(title_length > 0 && endDate_length > 0 && !time && description_length > 0){
        	let tmp_time = "";
        	let arr = $("#u_timeValue").text().trim().split(":");
        	let ampm = arr[0];
        	if(ampm == "오후"){
        		if(Number(arr[1]) != 12){
        			tmp_time = (Number(arr[1]) + 12) + ":" + arr[2] + ":59";
        		}else{
        			tmp_time = arr[1] + ":" + arr[2] + ":59";
        		}
        	}else if(ampm == "오전"){
        		tmp_time = arr[1] + ":" + arr[2] + ":59";
        	}
        	
	        update_ajax(tmp_time);
        }
        else{
            fade_in_out(undefined, undefined, "빈 항목이 존재합니다.");
        }  
        
	});
    
    
    /************************************************ */
    /** 커스텀 타임피커 SECTION */
    /************************************************ */
    // 시간 설정 SECTION
    $("#u_timepicker_box").click(function(event){
        $("#u_customTimePicker").toggleClass("blind");

        if(ajax_flag){
            $.ajax({
                url : "/meet-a-bwa/resources/json/time.json",
                success : function(res){
                    ajax_flag = false;
                    ampm_list(res.type);
                    time_list(res.time);
                    minute_list(res.minute);
                }
            });
        }
        event.stopPropagation();
    });
    
     $("#u_customTimePicker").click(function(event){
        event.stopPropagation();
    });
    
    
    $("#u_ampm_choice").on("click", ".ampm-list", function(event){
        $(".ampm-list").removeClass("time_choice");
        $(this).addClass("time_choice");

        let arr = $("#u_timeValue").text().split(":");
        arr[0] = $(this).attr("type");

        $("#u_timeValue").text(arr.join(":"));
        
        if($(".ampm-list").hasClass("time_choice") && $(".time-list").hasClass("time_choice") && $(".minute-list").hasClass("time_choice")){
            $(".ampm-list").removeClass("time_choice");
            $(".time-list").removeClass("time_choice");
            $(".minute-list").removeClass("time_choice");
            
            $("#u_customTimePicker").addClass("blind");
        }
    });
    $("#u_time_choice").on("click", ".time-list", function(event){
        $(".time-list").removeClass("time_choice");
        $(this).addClass("time_choice");

        let arr = $("#u_timeValue").text().split(":");
        arr[1] = $(this).attr("time");

        $("#u_timeValue").text(arr.join(":"));

        if($(".ampm-list").hasClass("time_choice")&&$(".time-list").hasClass("time_choice")&&$(".minute-list").hasClass("time_choice")){
            $(".ampm-list").removeClass("time_choice");
            $(".time-list").removeClass("time_choice");
            $(".minute-list").removeClass("time_choice");
            
            $("#u_customTimePicker").addClass("blind");
        }
    });
    $("#u_minute_choice").on("click", ".minute-list", function(event){
        $(".minute-list").removeClass("time_choice");
        $(this).addClass("time_choice");

        let arr = $("#u_timeValue").text().split(":");
        arr[2] = $(this).attr("minute");

        $("#u_timeValue").text(arr.join(":"));
        
        if($(".ampm-list").hasClass("time_choice")&&$(".time-list").hasClass("time_choice")&&$(".minute-list").hasClass("time_choice")){
            $(".ampm-list").removeClass("time_choice");
            $(".time-list").removeClass("time_choice");
            $(".minute-list").removeClass("time_choice");
            
            $("#u_customTimePicker").addClass("blind");
        }
    });
    
    
    /************************************************ */
    /** 글자수 제한 */
    /************************************************ */
    // 투표 제목 글자수 제한
    $("#u_vote_title").keydown(function(){
        text_limit(15, $(this), $("#u_title_text_length"));
    });
    $("#u_vote_title").keyup(function(){
        text_limit(15, $(this), $("#u_title_text_length"));
    });
    
    // 투표 설명 글자수 제한
    $("#u_vote_description").keydown(function(){
        text_limit(150, $(this), $("#u_description_text_length"));
    });
    $("#u_vote_description").keyup(function(){
        text_limit(150, $(this), $("#u_description_text_length"));
    });

    // 투표 항목 글자수 제한
    $("#u_vote_list_Wrap").on("keydown", ".list_text", function(){
        text_limit(50, $(this), undefined);
    });
    $("#u_vote_list_Wrap").on("keyup", ".list_text", function(){
        text_limit(50, $(this), undefined);
    });
    
    
    /************************************************ */
    /** DATEPICKER **/
    /************************************************ */
    $("#u_vote_endDate").datepicker({
        changeYear:true,
        changeMonth:true
    });
    
    /************************************************ */
    /** 함수 SECTION **/
    /************************************************ */

    // 글자수 제한 및 글자수 표기 함수
    function text_limit(max_length, element, length_txt_element){
        if(element.val().length > max_length){
            fade_in_out(element, max_length, "글자수를 초과하였습니다.");
        }

        if(length_txt_element != undefined)
            length_txt_element.text(element.val().length + "/" + max_length);
    }
    // 토스트 함수
    function fade_in_out(element, max_length, text){
        if(element != undefined && max_length != undefined)
            element.val(element.val().substr(0,max_length));

        if(toast_flag){
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
    
    function ampm_list(arr){
        for(x of arr){
            let sample = $("#u_ampm_listWrap").children(".sample").clone();
            sample.removeClass("sample");
            sample.text(x);
            sample.attr("type", x);

            $("#u_ampm_listWrap").append(sample);
        }
    }
    function time_list(arr){
        for(x of arr){
            let sample = $("#u_time_listWrap").children(".sample").clone();
            sample.removeClass("sample");
            sample.text(x);
            sample.attr("time", x);

            $("#u_time_listWrap").append(sample);
        }
    }
    function minute_list(arr){
        for(x of arr){
            let sample = $("#u_minute_listWrap").children(".sample").clone();
            sample.removeClass("sample");
            sample.text(x);
            sample.attr("minute", x);

            $("#u_minute_listWrap").append(sample);
        }
    }
    
    function update_ajax(time){
        	$.ajax({
	        	url : "/meet-a-bwa/m_vote_update.do",
				type : "POST",
				dataType : 'json', // 결과값 받을 타입
				data : {
					vote_no : vote_no,
					vote_title : $("#u_vote_title").val().trim(),
					vote_description : $("#u_vote_description").val().trim(),
					vote_eod : $("#u_vote_endDate").val().trim() + " " + time
				},
				success : function(res) {
			        if(res.result == "update success"){
			        	location.reload();
			        }else if(res.result == "update fail"){
			        	fade_in_out(undefined, undefined, "오류로 인해 투표 수정에 실패하였습니다.");
			        }
				},
				error : function(error) {
				 	console.log(error);
				 }
	        });
        }
})