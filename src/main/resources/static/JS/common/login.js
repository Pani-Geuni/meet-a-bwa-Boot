/**
 * @author 김예은
 */
 
 $(function(){
 	let flag = true;
 
 	if($.cookie("login_result") == "fail" && !$(".login-layer").hasClass("blind")){
 		fade_in_out("로그인에 실패하였습니다.")
 	}
 
    // 로그인 팝업 - 창닫기 버튼 클릭
    $("#login-popup-closeBtn").click(function(){
    	$('#idInput').removeClass("length_error");
		$('#pwInput').removeClass("length_error");
    	$(".login-layer").addClass("blind");
    });
    
    // ID 찾기 버튼 클릭
    $("#id_find_btn").click(function(){
    	$("#id_find_wrap").removeClass("blind");
    });
    // PW 찾기 버튼 클릭
    $("#pw_find_btn").click(function(){
    	$("#pw_find_wrap").removeClass("blind");
    });
    
    
    
 	// 로그인 버튼 클릭 시 제출 전에 아이디/비번 입력되었는지 확인
	  $( '#loginForm' ).submit( function() {
		$('#idInput').removeClass("length_error");
		$('#pwInput').removeClass("length_error");
		
      	if($('#idInput').val().trim().length > 0){
      		if($('#pwInput').val().trim().length > 0){
      			return true;
      		}else{
      			$('#pwInput').addClass("length_error");
      			return false;
      		}
      	}else{
  			$('#idInput').addClass("length_error");
      		if($('#pwInput').val().trim().length == 0){
      			$('#pwInput').addClass("length_error");
      			return false;
      		}
      		return false;
      	}
      });
      
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