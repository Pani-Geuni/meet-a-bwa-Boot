/**
* @author 최진실
*/
$(function() {
		let btn_idCheck = $("#btn_idCheck");
		let result = $("#result");

		$("#btn_idCheck").click(function(event) {
			let id =$("#join-id");
			let req = new XMLHttpRequest();

			 req.addEventListener("load", function() {
				
			 	if(this.status==200){
			 		try {
						let txt_json = this.responseText;
						let res = JSON.parse(txt_json);
						let txt = "";
						
						if (res.result === 0) {
							$(".toastText_idCheck").removeClass("blind");
							$(".toastText_idCheck").text("사용중인 아이디 입니다.");
							$(".toastText_idCheck").addClass("no");
						} else {
							window.location.href="/meetabwa/user_idCheckOK"
							$(".toastText_idCheck").removeClass("no");
							$(".toastText_idCheck").removeClass("blind");
							$(".toastText_idCheck").text("사용가능한 아이디 입니다.");
							$(".toastText_idCheck").addClass("yes");
							$("#join-id").attr("readonly",true);
							$("#btn_idCheck").addClass("blind");
							$("#btn_idRe").removeClass("blind");
							
							$("#btn_idRe").click(function(){
								$("#btn_idCheck").removeClass("blind");
								$("#btn_idRe").addClass("blind");
								$("#join-id").removeAttr("readonly");
								
								// '사용 가능한 아이디' 토스트 메시지 지우기
								$(".toastText_idCheck").addClass("blind");
							});
						}

				 	} catch (e) {
				 		console.log("json 형식이 아님.");
				 	}
					
				 }

			 });
			

			event.preventDefault();
			event.stopPropagation();
		});
	});