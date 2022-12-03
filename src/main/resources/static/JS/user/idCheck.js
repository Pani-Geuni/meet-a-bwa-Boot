/**
* @author 최진실
*/
$(function() {
		let btn_idCheck = $("#btn_idCheck");
		let result = $("#result");

		$("#btn_idCheck").click(function(event) {
			let id =$("#user_id");
			let req = new XMLHttpRequest();

			 req.addEventListener("load", function() {
				
			 	if(this.status==200){
			 		try {
						let txt_json = this.responseText;
						let obj_json = JSON.parse(txt_json);
						let txt = "";
						
						if (obj_json.result === 'Not OK') {
							$(".toastText_idCheck").removeClass("blind");
							$(".toastText_idCheck").text("사용중인 아이디 입니다.");
							$(".toastText_idCheck").addClass("no");
						} else {
							$(".toastText_idCheck").removeClass("no");
							$(".toastText_idCheck").removeClass("blind");
							$(".toastText_idCheck").text("사용가능한 아이디 입니다.");
							$(".toastText_idCheck").addClass("yes");
							$("#user_id").attr("readonly",true);
							$("#btn_idCheck").addClass("blind");
							$("#btn_idRe").removeClass("blind");
							
							$("#btn_idRe").click(function(){
								$("#btn_idCheck").removeClass("blind");
								$("#btn_idRe").addClass("blind");
								$("#user_id").removeAttr("readonly");
								
								// '사용 가능한 아이디' 토스트 메시지 지우기
								$(".toastText_idCheck").addClass("blind");
							});
						}

				 	} catch (e) {
				 		console.log("json 형식이 아님.");
				 	}
					
				 }

			 });
			
			req.open("GET","http://localhost:8090/meet-a-bwa/idCheck.do?id="+ id.val());
			req.send();

			event.preventDefault();
			event.stopPropagation();
		});
	});