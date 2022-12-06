/**
 * @author 김예은, 강경석
 */

  // 공용 알러트 창 닫기버튼
    $("#common-alert-btn").click(function(){
        $("#common-alert-popup-wrap").addClass("blind");
    });
 
 $(function(){
 	let flag = true;
 	
 	$("#id_find_okBtn").click(function(){
 		if($("#id-email-input").val().trim().length != 0){
 			find_ajax();
 		}else{
 			$("#id_find_result").addClass("blind");
 			fade_in_out("이메일을 입력해주세요.");
		} 		
 	});
 	$("#id_find_cancleBtn").click(function(){
 		$("#id_find_result").addClass("blind");
 		$("#id_find_wrap").addClass("blind");
 	});
 	
 	function find_ajax(){
	 	$.ajax({
            url : "/meet-a-bwa/id_find.do",
			type : "POST",
			dataType : 'json',
			data : {
				user_email : $("#id-email-input").val().trim()
			},
			success : function(res) {
				$("#id_find_wrap").addClass("blind");
		        $("#id_find_result").removeClass("blind");
		        
		        if(res.result == 1){
			        $("#common-alert-popup-wrap").removeClass("blind");
                     $(".common-alert-txt").html("이메일로 아이디를 전달하였습니다!");
				}else{
			        $("#common-alert-popup-wrap").removeClass("blind");
                     $(".common-alert-txt").html("해당 이메일로 가입된 회원이 없습니다.");
				}
			},
			error : function(error) {
				$("#id_find_wrap").addClass("blind");
			 	$("#common-alert-popup-wrap").removeClass("blind");
                $(".common-alert-txt").html("오류가 발생되어 아이디를 찾을수 없습니다.");
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