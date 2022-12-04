///**
// * @author 최진실
// */
//$(function() {
//	//******************************프로필 이미지********************************//
//	function readURL(input) {
//		if (input.files && input.files[0]) {
//			var reader = new FileReader();
//			reader.onload = function(e) {
//				$('#image').attr('src', e.target.result);
//			}
//			reader.readAsDataURL(input.files[0]);
//
//			if ($("#image").attr('src') != "/meet-a-bwa/resources/img/placeholder1.webp") {
//				$('#delete-file1').css('display', 'block');
//				$('#delete-file1').css('opacity', '1');
//
//				$('#imgWrap').hover(function() {
//					if ($("#image").attr('src') != "/meet-a-bwa/resources/img/default-image2.png") {
//						$('#delete-file2').css('display', 'block');
//						$('#delete-file2').css('opacity', '1');
//					}
//				});
//
//				$('#imgWrap').mouseleave(function() {
//					$('#delete-file2').css('opacity', '0');
//				});
//
//			}
//		}
//	}
//
//	$("#input-file").off().on("change", function() {
//		if (this.files && this.files[0]) {
//			var maxSize = 10 * 1024 * 1024; // 10MB
//			var fileSize = this.files[0].size;
//
//			if (fileSize > maxSize) {
//				$(".image-popup").removeClass("blind");
//				$(this).val('');
//
//				$(".ok").on("click", function() {
//					$(".image-popup").addClass("blind");
//				});
//
//				return false;
//			}
//		}
//	});
//
//	$("#input-file").change(function() {
//		readURL(this);
//	});
//
//	$('#delete-file1').click(function() {
//		$('#image').attr('src', "/meet-a-bwa/resources/img/placeholder1.webp");
//		$('#delete-file1').css('display', 'none');
//	});
//
//	$('#delete-file2').click(function() {
//		$('#image').attr('src', "/meet-a-bwa/resources/img/default-image2.png");
//		$('#delete-file2').css('display', 'none');
//	});
//	
//	//*******************************지역 셀렉트, 태그***********************************//
//	let cityArr = [];
//	let townArr = []; 
//	let tmpArr = [];
//  
//	let city = '';
//	let country = '';
//  
//	let flag = true; //중복적인 $.getJSON 을 막기휘안 변수
//  
//	$("#city").on("click",function (e) {
//		if(flag){ 
//			flag = false;
//
//			$.getJSON("/meet-a-bwa/resources/json/city.json", function(data) {
//		        cityArr = data.map(v => v.city);
//		        city_set(cityArr);
//		    });
//    	}
//	});
//
//	$("#city").on("change", function () {
//		city = $(this).val();
//  	
//		if(city.length != 0 && city!="전체"){
//	  	 $.getJSON("/meet-a-bwa/resources/json/city.json", function(data) {
//	        townArr = data.filter(function(v,i){
//	        	if(v.city == city){
//	        		return true; 
//	        	}});
//	        townArr = townArr[0].arr;
//		    town_set(townArr);
//		  });
//	  	}else{
//	  		let arr2 = $("#country").find(".country_option:lt(2)").clone(); //시/군 선택 전체 리스트 복사
//	    	$("#country").empty(); 
//	    
//		    for(x of arr2){
//		    	$("#country").append($(x));
//		    } 
//	  	}
//	});
//
//	/****************************************/
//	/* 함수 섹션 */
//	/****************************************/
//	//도시 리스트 세팅
//	function city_set(arr){
//		let arr2 = $("#city").find(".city_list:lt(2)").clone();
//	    $("#city").empty();
//
//	    for(x of arr2){
//	    	$("#city").append($(x));
//	    }
//	    
//		let sample = $(".city_list:eq(0)").clone();
//		
//		for(x of arr){
//			let list = sample.clone();
//			list.text(x);
//			list.val(x);
//			
//			$("#city").append(list);
//		}
//	}
//	
//	// 시/군 리스트 세팅
//	function town_set(arr){
//		//초기세팅
//		let arr2 = $("#country").find(".country_option:lt(2)").clone(); //시/군 선택 전체 리스트 복사
//	    $("#country").empty(); 
//	    
//	    for(x of arr2){
//	    	$("#country").append($(x));
//	    } 
//	    
//	    //도/시 선택에 맞는 시/군 세팅
//		let sample = $(".country_option:eq(0)").clone();
//		
//		for(x of arr){
//			let list = sample.clone();
//			list.text(x);
//			list.val(x);
//			
//			$("#country").append(list);
//		}
//	}
//	
//	
//	//******************************글자 수***************************************//
//	let text_flag = true;
//
//	//*********토스트 보이게 하는 함수*************
//	function fade_in_out(element){
//	    if(text_flag){
//	        text_flag = false;
//	
//	        if(element.attr("id") == 'activity_name'){
//		        $("#toastWrap_name").removeClass("hide");
//		        $("#toastWrap_name").removeClass("fade-out");
//		        $("#toastWrap_name").addClass("fade-in");
//	        }else if(element.attr("id")== 'activity_description'){
//		        $("#toastWrap_des").removeClass("hide");
//		        $("#toastWrap_des").removeClass("fade-out");
//		        $("#toastWrap_des").addClass("fade-in");
//	        }
//	        
//	        setTimeout(function() {
//	            text_flag = true; // 추후에 사용할 수 있도록 변수값 변경
//	            
//	            if(element.attr("id") == 'activity_name'){
//	                $("#toastWrap_name").removeClass("fade-in");
//	                $("#toastWrap_name").addClass("fade-out");
//	            }else if(element.attr("id")== 'activity_description'){
//		            $("#toastWrap_des").removeClass("fade-in");
//		            $("#toastWrap_des").addClass("fade-out");
//	            }
//	        }, 1000);
//	    }
//	}
//	
//	//**********글자 수 세고 제한하는 함수**********
//	function textLengthCnt(max_length, element ,txt){
//	    // 글자수 세기
//	    if (element.val().length == 0 || element.val() == '') {
//	        txt.text('0자');
//	    } else {
//	        txt.text(element.val().length + '자');
//	    }
//	    
//	    // 글자수 제한
//	    if (element.val().length > max_length) {
//	        // 500자 부터는 타이핑 되지 않도록
//	        element.val(element.val().substring(0, max_length));
//	        // 500자 넘으면 알림창 뜨도록
//	        fade_in_out(element);
//	    };
//    } 
//	
//	//***********키보드 함수***************
//	//액티비티 이름 글자수 제한
//	$('#activity_name').keyup(function () {
//	    textLengthCnt(10, $(this),$(".textCount_name"));
//	});
//	$('#activity_name').keydown(function () {
//	    textLengthCnt(10, $(this),$(".textCount_name"));
//	});
//	//액티비티 소개 글자수 제한
//	$('#activity_description').keyup(function () {
//		textLengthCnt(500, $(this),$(".textCount"));
//	});
//	$('#activity_description').keydown(function () {
//		textLengthCnt(500, $(this),$('.textCount'));
//	});
//
//
//    //********************************관심사 셀렉트, 태그*****************************//
//	var interest = ["취미",
//		"팬클럽",
//		"방송/연예",
//		"스포츠/레저",
//		"게임",
//		"만화/애니메이션",
//		"맛집/요리",
//		"생활정보/인테리어",
//		"건강/다이어트",
//		"패션/뷰티",
//		"여행/캠핑",
//		"반려동물/동물",
//		"문화/예술",
//		"음악",
//		"어학/외국어",
//		"취업/자격증",
//		"교육/공부",
//		"IT/컴퓨터",
//		"인문/과학",
//		"경제/재테크",
//		"종교/봉사",
//		"일상/이야기",
//		"나이/또래모임",
//		"친목/모임",
//		"자연/귀농"];
//
//	cate_init();
//	function cate_init() {
//		let sample = $(".interest_opt").eq(0).clone();
//		for (let i = 0; i < interest.length; i++) {
//			sample = sample.clone().text(interest[i]);
//			sample.val(interest[i]);
//			$("#interest").append(sample);
//		}
//	}
//
//	/*************************************************** */
//	/** 관심사 태그 추가 */
//	/*************************************************** */
//	let tag = $(".delete_interest:eq(0)").clone();
//	let cnt = 0;
//	var arr = [];
//
//	$("#interest").on("change", function(e) {
//		tag = tag.clone();
//		tag.removeClass("blind");
//		let select_value = $(this).val();
//
//		if (!arr.includes(select_value) && select_value != '') {
//			//선택한 관심사가 중복으로 들어가지 않도록 includes 함수 사용해서 배열 안에 해당 관심사가 없으면 아래 코드가 동작하게 함.
//			tag.val(select_value + " X");
//			tag.attr("idx", ++cnt);
//			$("#tagWrap").append(tag);
//			arr.push(select_value);
//		}
//	});
//
//	/*************************************************** */
//	/** 관심사 태그 삭제 */
//	/*************************************************** */
//	$("#tagWrap").on("click", ".delete_interest", function() {
//		let idx = $(this).attr("idx");
//		let arr = $(".delete_interest").slice();
//
//		$("#tagWrap").empty().append($(arr[0]));
//
//		for (let index = 1; index < arr.length; index++) {
//			if ($(arr[index]).attr("idx") != idx) {
//				$("#tagWrap").append(arr[index]);
//			}
//		}
//
//	});
//
//	//************************//
//	//*        연령대 태그       *//
//	//***********************//
//	let age_tag = $(".age_result:eq(0)").clone();
//	let age_cnt = 0;
//	var arr = [];
//	var arr_x = [];
//
//	$("#ageBody").on("change", function(e) {
//		age_tag = age_tag.clone();
//		age_tag.removeClass("blind"); // 초기에 자동 생성된 버튼 숨기기
//		let select_value = $("#ageBody option:selected").text();
//
//		if (!arr.includes(select_value) && select_value != '') {
//			//선택한 관심사가 중복으로 들어가지 않도록 includes 함수 사용해서 배열 안에 해당 관심사가 없으면 아래 코드가 동작하게 함.
//			age_tag.val(select_value + " X");
//			arr.push(select_value);
//			age_tag.attr("idx", ++age_cnt);
//
//			$("#tagWrap_age").append(age_tag);
//			arr_x.push(select_value + " X");
//		}
//	});
//
//	$("#tagWrap_age").on("click", ".delete_age", function() {
//		let idx = $(this).attr("idx"); // 현재 배열 크기
//		let arr = $(".delete_age").slice(); // 태그 배열 전체 복시
//
//		let select_value = $(this).text();
//
//		$("#tagWrap_age").empty().append($(arr[0]));
//
//		for (let index = 1; index < arr.length; index++) {
//			if ($(arr[index]).attr("idx") != idx) { // 삭제할 값과 같지 않은 데이터들만
//				$("#tagWrap_age").append(arr[index]); //부모에 붙여줌.
//			}
//		}
//
//		for (let i = 0; i < result_x.length; i++) {
//			if (result_x[i] == select_value) {
//				result.splice(i, 1);
//				result_x.splice(i, 1);
//				i--;
//			}
//		}
//	});
//
//});