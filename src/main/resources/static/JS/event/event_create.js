/**
 * @author 김예은
 */
 $(function(){
    let cnt = 0;
    let flag = true;
    let ajax_flag = true;

	// 이벤트 생성 버튼 클릭 (+)
	$("#event_create_btn").click(function() {
		$("#event_cu-wrap").removeClass("blind");
		$("#event-create-wrap").removeClass("blind");
	});
	
	
	/******************************************************/
	/********************** 이벤트 부분 **********************/
	/******************************************************/
    
	$("#event_createBtn").click(function() {
		let title_length = $("#event_title").val().trim().length;
        let endDate_length = $("#eventDate").val().trim().length;
        let time = $("#event_timeValue").text().trim().includes("--");
        let description_length = $("#event_description").val().trim().length;

        if(title_length > 0 && endDate_length > 0 && !time && description_length > 0){
        	let time = "";
        	let arr = $("#event_timeValue").text().trim().split(":");
        	let ampm = arr[0];
        	if(ampm == "오후"){
        		if(Number(arr[1]) != 12){
        			time = (Number(arr[1]) + 12) + ":" + arr[2] + ":59";
        		}else{
        			time = arr[1] + ":" + arr[2] + ":59";
        		}
        	}else if(ampm == "오전"){
        		time = arr[1] + ":" + arr[2] + ":59";
        	}
        	
        	insert_ajax(time);
        }else{
        	fade_in_out(undefined, undefined, "빈 항목이 존재합니다.");
        }
	});
	$("#event_cancleBtn").click(function() {
		cnt = 0;
	    flag = true;
    
		$("#event_title").val("");
		$("#eventDate").val("");
		$("#event_description").val("");
		$("#event_title_text_length").text("0/15");
		$("#event_description_text_length").text("0/200");
		
		$("#event_timeValue").text("--:--:--");
		$("#event-create-wrap").find(".ampm-list").removeClass("time_choice");
		$("#event-create-wrap").find(".time-list").removeClass("time_choice");
		$("#event-create-wrap").find(".minute-list").removeClass("time_choice");
		
		$("#event-customTimePicker").addClass("blind");
		
		let clone_title = $("#team_title").clone();
		let clone_element = $("#teamSection>.sample").clone();
		$("#vote_list_Wrap").empty().append(clone_title).append(clone_element);
		
		$("#event_cu-wrap").addClass("blind");
		$("#event-create-wrap").addClass("blind");
	});
	
	/************************************************ */
    /** 커스텀 타임피커 SECTION */
    /************************************************ */
    // 시간 설정 SECTION
    $("#e_timepicker_box").click(function(){
        $("#event-customTimePicker").toggleClass("blind");

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
    });

    
    $("#event-customTimePicker").click(function(event){
        event.stopPropagation();
    });
    

    $("#e_ampm_choice").on("click", ".ampm-list", function(){
        $("#e_ampm_choice").find(".ampm-list").removeClass("time_choice");
        $(this).addClass("time_choice");

        let arr = $("#event_timeValue").text().split(":");
        arr[0] = $(this).attr("type");

        $("#event_timeValue").text(arr.join(":"));
        
        if($("#e_ampm_choice").find(".ampm-list").hasClass("time_choice") && $("#e_time_choice").find(".time-list").hasClass("time_choice") && $("#e_minute_choice").find(".minute-list").hasClass("time_choice")){
            $(".customTimePicker").addClass("blind");
        }
    });
    $("#e_time_choice").on("click", ".time-list", function(){
        $("#e_time_choice").find(".time-list").removeClass("time_choice");
        $(this).addClass("time_choice");

        let arr = $("#event_timeValue").text().split(":");
        arr[1] = $(this).attr("time");

        $("#event_timeValue").text(arr.join(":"));

        if($("#e_ampm_choice").find(".ampm-list").hasClass("time_choice") && $("#e_time_choice").find(".time-list").hasClass("time_choice") && $("#e_minute_choice").find(".minute-list").hasClass("time_choice")){
            $(".customTimePicker").addClass("blind");
        }
    });
    $("#e_minute_choice").on("click", ".minute-list", function(){
        $("#e_minute_choice").find(".minute-list").removeClass("time_choice");
        $(this).addClass("time_choice");

        let arr = $("#event_timeValue").text().split(":");
        arr[2] = $(this).attr("minute");

        $("#event_timeValue").text(arr.join(":"));
        
        if($("#e_ampm_choice").find(".ampm-list").hasClass("time_choice") && $("#e_time_choice").find(".time-list").hasClass("time_choice") && $("#e_minute_choice").find(".minute-list").hasClass("time_choice")){
            $(".customTimePicker").addClass("blind");
        }
    });
    

    /************************************************************* */
    /** 글자수 제한 */
    /************************************************************* */
    // 이벤트 제목 글자수 제한
    $("#event_title").keydown(function(){
        text_limit(15, $(this), $("#event_title_text_length"));
    });
    $("#event_title").keyup(function(){
        text_limit(15, $(this), $("#event_title_text_length"));
    });
    
    // 이벤트 설명 글자수 제한
    $("#event_description").keydown(function(){
        text_limit(200, $(this), $("#event_description_text_length"));
    });
    $("#event_description").keyup(function(){
        text_limit(200, $(this), $("#event_description_text_length"));
    });



    /**************************************************************/
    /** DATEPICKER */
    /**************************************************************/
    $("#eventDate").datepicker({
        changeYear:true,
        changeMonth:true
    });
    
    
    /**************************************************************/
    /** 함수 SECTION */
    /**************************************************************/
    // 글자수 제한 및 글자수 표기 함수
    function text_limit(max_length, element, length_txt_element){
        if(element.val().length > max_length){
            fade_in_out(element, max_length, "글자수를 초과하였습니다.");
        }
        length_txt_element.text(element.val().length + "/" + max_length);
    }

    // 토스트 함수
    function fade_in_out(element, max_length, text){
    	if(element != undefined)
	        element.val(element.val().substr(0,max_length));

        if(flag){
            flag = false;
            $("#toast_txt").text(text);
            $("#toastWrap").removeClass("hide");
            $("#toastWrap").removeClass("fade-out");
            $("#toastWrap").addClass("fade-in");
        
            setTimeout(function() {
                flag = true; // 추후에 사용할 수 있도록 변수값 변경
                $("#toastWrap").removeClass("fade-in");
                $("#toastWrap").addClass("fade-out");
            }, 1000);
        }
    }
    
    function ampm_list(arr){
        for(x of arr){
            let sample = $("#e_ampm_listWrap").children(".sample").clone();
            sample.removeClass("sample");
            sample.text(x);
            sample.attr("type", x);

            $("#e_ampm_listWrap").append(sample);
        }
    }
    function time_list(arr){
        for(x of arr){
            let sample = $("#e_time_listWrap").children(".sample").clone();
            sample.removeClass("sample");
            sample.text(x);
            sample.attr("time", x);

            $("#e_time_listWrap").append(sample);
        }
    }
    function minute_list(arr){
        for(x of arr){
            let sample = $("#e_minute_listWrap").children(".sample").clone();
            sample.removeClass("sample");
            sample.text(x);
            sample.attr("minute", x);

            $("#e_minute_listWrap").append(sample);
        }
    }
    
    // 이벤트 생성 AJAX
    function insert_ajax(time){
    	 $.ajax({
        	url : "/meet-a-bwa/event_create.do",
			type : "POST",
			traditional : true, // data value중에 배열있을 때 필요
			dataType : 'json', // 결과값 받을 타입
			data : {
				event_title : $("#event_title").val().trim(),
				event_description : $("#event_description").val().trim(),
				event_d_day : $("#eventDate").val().trim() + " " + time,
				user_no : $.cookie("user_no"),
				activity_no : location.href.split("idx=")[1]
			},
			success : function(res) {
		        if(res.result == "insert success"){
		        	location.reload();
		        }else if(res.result == "insert fail"){
		        	fade_in_out(undefined, undefined, "오류로 인해 투표 생성에 실패하였습니다.");
		        }
			},
			error : function(error) {
			 	console.log(error);
			 }
        });
    }
});