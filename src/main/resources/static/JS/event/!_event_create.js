$(function(){
    let cnt = 0;
    let flag = true;
    let team_arr = [];

	// 이벤트 생성 버튼 클릭 (+)
	$("#event_create_btn").click(function() {
		$("#event_cu-wrap").removeClass("blind");
		$("#event-create-wrap").removeClass("blind");
	});
	
	
	/******************************************************/
	/********************** 이벤트 부분 **********************/
	/******************************************************/
    plus_list();
    
    
	$("#event_createBtn").click(function() {
		let title_length = $("#event_title").val().trim().length;
        let endDate_length = $("#eventDate").val().trim().length;
        let time = $("#event_timeValue").text().trim().includes("--");
        let description_length = $("#event_description").val().trim().length;
        let team = returnTeam_checkNull();

        if(title_length > 0 && endDate_length > 0 && !time && description_length > 0 && content){
        }
	});
	$("#event_cancleBtn").click(function() {
		cnt = 0;
	    flag = true;
	    team_arr = [];
    
		$("#event_title").val("");
		$("#eventDate").val("");
		$("#event_description").val("");
		
		$("#event_timeValue").text("--:--:--");
		$("#event-create-wrap").find(".ampm-list").removeClass("time_choice");
		$("#event-create-wrap").find(".time-list").removeClass("time_choice");
		$("#event-create-wrap").find(".minute-list").removeClass("time_choice");
		
		let clone_title = $("#team_title").clone();
		let clone_element = $("#teamSection>.sample").clone();
		$("#vote_list_Wrap").empty().append(clone_title).append(clone_element);
		
		$("#event_cu-wrap").addClass("blind");
		$("#event-create-wrap").addClass("blind");
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
    

    /************************************************************* */
    /** 커스텀 셀렉트 드롭 다운 */
    /************************************************************* */
    $("#teamSection").on("click",".selectedWrap", function(event){
        let selectWrap = $(this).parents(".e_select_custom").siblings(".selectFlag");
        
        if($(selectWrap[0]).hasClass("dropDown")) $(selectWrap[0]).removeClass("dropDown");
        else $(selectWrap[0]).addClass("dropDown");

        if($(this).hasClass("focus")) $(this).removeClass("focus");
        else $(this).addClass("focus");
        
        if($(selectWrap[0]).hasClass("blind")) $(selectWrap[0]).removeClass("blind");
        else $(selectWrap[0]).addClass("blind");
        
        event.stopPropagation();
    });


    /************************************************************* */
    /** 조 편성 추가 */
    /************************************************************* */
    $("#team_plus_btn").click(function(){
        plus_list();
    });


    /************************************************************* */
    /** 조 편성 시 인원 태그 추가 */
    /************************************************************* */
    $("#teamSection").on("click", ".li_common", function(event){
        let name = $(this).text();
        let append_element = $(this).parents(".selectFlag").siblings(".e_select_custom").find(".selectedWrap").children(".selected_value");
        let sample = append_element.children(".after_choice").children(".sample_tag").clone();
        
        sample.text(name + " x");
        sample.removeClass("blind");
        sample.removeClass("sample_tag");
        sample.prop("idx", cnt);
        cnt++;
        
        append_element.children(".before_choice").addClass("blind");
        append_element.children(".after_choice").removeClass("blind");
        append_element.children(".after_choice").append(sample);
        
        // 드롭다운 닫고 포커스 클래스 제거
        $(this).parents(".selectFlag").addClass("blind");
        $(this).parents(".selectFlag").siblings(".selectedWrap").removeClass("focus");
        
        event.stopPropagation();
    });


    /************************************************************* */
    /** 인원 태그 삭제 */
    /************************************************************* */
    $("#teamSection").on("click", ".team_mem_tag", function(event){
        let idx = $(this).prop("idx");
        let parents = $(this).parents(".after_choice");
        let arr  = parents.children(".team_mem_tag").not(".blind").slice();
        let sample_tag = $(".sample_tag:eq(0)").clone();
        
        parents.empty().append(sample_tag);
        if(arr.length == 1){
            parents.addClass("blind");
            parents.siblings(".before_choice").removeClass("blind");
        }
        else{
            for(x of arr){
                if(idx != $(x).prop("idx")){
                    parents.append($(x));
                }
            }
        }
        
        event.stopPropagation();
    });


    /**************************************************************/
    /** DATEPICKER */
    /**************************************************************/
    $("#eventDate").datepicker({
        changeYear:true,
        changeMonth:true,
        onSelect:function(dateText) {
            console.log(dateText);
        }
    });
    
    
    /**************************************************************/
    /** 함수 SECTION */
    /**************************************************************/
    // 글자수 제한 및 글자수 표기 함수
    function text_limit(max_length, element, length_txt_element){
        if(element.val().length > max_length){
            fade_in_out(element, max_length);
        }
        length_txt_element.text(element.val().length + "/" + max_length);
    }

    // 투표 항목 추가 함수
    function plus_list(){
        let select_element = $("#teamSection>.sample:eq(0)").clone();
        select_element.removeClass("sample");
        select_element.removeClass("blind");
    	if($("#teamSection>.selectWrap").length == 1){
    		select_element.find(".removeBtn").addClass("blind");
    	}
        
        $("#teamSection").append(select_element);
    }

    // 토스트 함수
    function fade_in_out(element, max_length){
        element.val(element.val().substr(0,max_length));

        if(flag){
            flag = false;
            $("#toast_txt").text("글자수를 초과하였습니다.")
            $("#toastWrap").removeClass("hide");
            $("#toastWrap").removeClass("fade-out");
            $("#toastWrap").addClass("fade-in");
        
            setTimeout(function() {
                flag = true; // 추후에 사용할 수 있도록 변수값 변경
                $("#toastWrap").removeClass("fade-in");
                $("#toastWrap").addClass("fade-out");
            }, 2000);
        }
    }
    
    // 조편성 항목 - 빈항목있는지 체크
    function returnTeam_checkNull(){
    	team_arr = [];
        let elementArr = $("#teamSection").find(".selectWrap:gt(0)").slice();
        
        for(element of elementArr){
            if(!$(element).find(".before_choice").hasClass("blind")){
            	content_arr = [];
                return false;
            }else{
            	let tmpArr = $(element).find(".after_choice").find()
            	content_arr.push($(element).find(".after_choice").val().trim());
            }
        }
        return true;
    }
});