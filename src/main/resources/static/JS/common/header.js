/**
 * @author 김예은
 */
 
 $(function(){
 
 	$("#logo_img").click(function(){
	 	window.location.href="/meet-a-bwa/index.do";
 	});
 	
 	$("#mypageBtn").click(function(){
	 	window.location.href="/meet-a-bwa/mypage.do";
 	});
 	
 	$("#loginBtn").click(function(){
 		$(".login-layer").removeClass("blind");
 	});
 	
 	$("#logoutBtn").click(function(){
 		$(".logout-layer").removeClass("blind");
 	});

 });