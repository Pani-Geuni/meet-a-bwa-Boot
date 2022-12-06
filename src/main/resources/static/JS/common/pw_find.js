/**
 * @author 김예은
 */
 
   $("#find-pw-close").click(function(){
        // INPUT 초기화
        $(".find-popup-input").val("");

        // 경고 테두리 초기화
        $(".find-popup-input").removeClass("null-input-border");

        // 팝업 관련창 닫음
        $("#find-pw-section").addClass("blind");
        $(".popup-background:eq(0)").addClass("blind");
    });
 
$(function(){
	let flag = true;
	 
	$("#pw_find_okBtn").click(function(){
		if($("#pw-email-input").val().trim().length > 0 && $("#pw-id-input").val().trim().length > 0){
 			find_ajax();
 		}else if($("#pw-email-input").val().trim().length == 0){
 			$("#pw_find_result").addClass("blind");
 			fade_in_out("이메일을  입력해주세요.");
 		}else if($("#pw-id-input").val().trim().length == 0){
 			$("#pw_find_result").addClass("blind");
 			fade_in_out("아이디를  입력해주세요.");
 		}
 	});
 	$("#pw_find_cancleBtn").click(function(){
 		$("#pw_find_result").addClass("blind");
 		$("#pw_find_wrap").addClass("blind");
 	});
 	
 	function find_ajax(){
	 	$.ajax({
            url : "/meet-a-bwa/pw_find.do",
			type : "POST",
			dataType : 'json',
			data : {
				user_email : $("#pw-email-input").val().trim(),
				user_id : $("#pw-id-input").val().trim()
			},
			success : function(res) {
				$("#pw_find_wrap").addClass("blind");
		        $("#pw_find_result").removeClass("blind");
		        
				if(res.result == 1){
			       $("#common-alert-popup-wrap").removeClass("blind");
                     $(".common-alert-txt").html("이메일로 초기화된 비밀번호를 발송하였습니다!");
				}else{
			        $("#common-alert-popup-wrap").removeClass("blind");
                     $(".common-alert-txt").html("해당 정보로 가입된 회원이 없습니다.");
				}
			},
			error : function(error) {
				$("#pw_find_wrap").addClass("blind");
			 	  $(".popup-background:eq(1)").removeClass("blind");
	              $("#common-alert-popup").removeClass("blind");
	              $(".common-alert-txt").text("오류 발생으로 인해 처리에 실패하였습니다.");
			 }
        });
 	}
 	// 토스트 함수
    function fade_in_out(text){
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
 });