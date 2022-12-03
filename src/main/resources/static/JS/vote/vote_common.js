/**
* @author 김예은
*/
$(function(){
    let cnt = 1;
    let cnt2 = 1;
    let ajax_flag = true;
    
    plus_list();


    /************************************************ */
    /** 버튼 클릭 이벤트 SECTION */
    /************************************************ */
    // 투표 항목 추가 버튼 클릭 이벤트
    $("#vote_list_plusBtn").click(function(){
        plus_list();
    });


    /************************************************ */
    /** 커스텀 타임피커 SECTION */
    /************************************************ */
    // 시간 설정 SECTION
    $("#timepicker_box").click(function(event){
        $("#customTimePicker").toggleClass("blind");

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

    
    $("#customTimePicker").click(function(event){
        event.stopPropagation();
    });

    $("#ampm_choice").on("click", ".ampm-list", function(event){
        $(".ampm-list").removeClass("time_choice");
        $(this).addClass("time_choice");

        let arr = $("#timeValue").text().split(":");
        arr[0] = $(this).attr("type");

        $("#timeValue").text(arr.join(":"));
        
        if($(".ampm-list").hasClass("time_choice")&&$(".time-list").hasClass("time_choice")&&$(".minute-list").hasClass("time_choice")){
            $("#customTimePicker").addClass("blind");
        }
    });
    $("#time_choice").on("click", ".time-list", function(event){
        $(".time-list").removeClass("time_choice");
        $(this).addClass("time_choice");

        let arr = $("#timeValue").text().split(":");
        arr[1] = $(this).attr("time");

        $("#timeValue").text(arr.join(":"));

        if($(".ampm-list").hasClass("time_choice")&&$(".time-list").hasClass("time_choice")&&$(".minute-list").hasClass("time_choice")){
            $("#customTimePicker").addClass("blind");
        }
    });
    $("#minute_choice").on("click", ".minute-list", function(event){
        $(".minute-list").removeClass("time_choice");
        $(this).addClass("time_choice");

        let arr = $("#timeValue").text().split(":");
        arr[2] = $(this).attr("minute");

        $("#timeValue").text(arr.join(":"));
        
        if($(".ampm-list").hasClass("time_choice")&&$(".time-list").hasClass("time_choice")&&$(".minute-list").hasClass("time_choice")){
            $("#customTimePicker").addClass("blind");
        }
    });


    /************************************************ */
    /** 글자수 제한 */
    /************************************************ */
    // 투표 제목 글자수 제한
    $("#vote_title").keydown(function(){
        text_limit(15, $(this), $("#title_text_length"));
    });
    $("#vote_title").keyup(function(){
        text_limit(15, $(this), $("#title_text_length"));
    });
    
    // 투표 설명 글자수 제한
    $("#vote_description").keydown(function(){
        text_limit(150, $(this), $("#description_text_length"));
    });
    $("#vote_description").keyup(function(){
        text_limit(150, $(this), $("#description_text_length"));
    });

    // 투표 항목 글자수 제한
    $("#vote_list_Wrap").on("keydown", ".list_text", function(){
        text_limit(50, $(this), undefined);
    });
    $("#vote_list_Wrap").on("keyup", ".list_text", function(){
        text_limit(50, $(this), undefined);
    });


    /************************************************ */
    /** 투표 항목 삭제 */
    /************************************************ */
    $("#vote_list_Wrap").on("click", ".removeBtn", function(){
        cnt2 = 1;
        
        let wrap = $(this).parents("#vote_list_Wrap");
        let sample = $("#vote_list_Wrap").children(".sample").clone();
        let idx = $(this).parents(".vote_list").attr("idx");
        let arr = wrap.children(".vote_list").not(".blind");
        wrap.empty().append(sample);

        for(var i = 0; i < arr.length; i++){
            if($(arr[i]).attr("idx") != idx){
                $(arr[i]).children(".list_title").text(cnt2+".");
                wrap.append(arr[i]);
                cnt2++;
            }
        }

    });


    /************************************************ */
    /** DATEPICKER **/
    /************************************************ */
    $("#vote_endDate").datepicker({
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
    
    // 투표 항목 추가 함수
    function plus_list(){
        let sample_list = $("#vote_list_Wrap").children(".sample").clone();
        sample_list.removeClass("blind sample");
        sample_list.attr("idx", cnt);
        sample_list.children(".list_title").text(cnt2+".");
        
        if(cnt2 == 1){
            sample_list.children(".removeBtn").addClass("blind");
        }
        
        cnt++;
        cnt2++;

        $("#vote_list_Wrap").append(sample_list);
    }

    // 토스트 함수
    function fade_in_out(element, max_length, text){
        if(element != undefined && max_length != undefined)
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
            }, 2000);
        }
    }

    function ampm_list(arr){
        for(x of arr){
            let sample = $("#ampm_listWrap").children(".sample").clone();
            
            sample.removeClass("sample");
            sample.text(x);
            sample.attr("type", x);

            $("#ampm_listWrap").append(sample);
        }
    }
    function time_list(arr){
        for(x of arr){
            let sample = $("#time_listWrap").children(".sample").clone();
            
            sample.removeClass("sample");
            sample.text(x);
            sample.attr("time", x);

            $("#time_listWrap").append(sample);
        }
    }
    function minute_list(arr){
        for(x of arr){
            let sample = $("#minute_listWrap").children(".sample").clone();
            
            sample.removeClass("sample");
            sample.text(x);
            sample.attr("minute", x);

            $("#minute_listWrap").append(sample);
        }
    }

});