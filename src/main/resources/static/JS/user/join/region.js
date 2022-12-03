/**
* @author 최진실
*/
$(function () {

  let cityArr = [];
  let townArr = []; 
  let tmpArr = [];
  
  let city = '';
  let country = '';
  
  let flag = true; //중복적인 $.getJSON 을 막기휘안 변수
  
  $("#city").on("click",function (e) {
    if(flag){ 
    	flag = false;
    	
	    $.getJSON("/meet-a-bwa/resources/json/city.json", function(data) {
	        cityArr = data.map(v => v.city);
	        city_set(cityArr);
	    });
    }
  });

  $("#city").on("change", function () {
  	city = $(this).val();
  	
  	if(city.length != 0 && city!="전체"){
	  	 $.getJSON("/meet-a-bwa/resources/json/city.json", function(data) {
	        townArr = data.filter(function(v,i){
	        	if(v.city == city){
	        		return true; 
	        	}
	        });
	        townArr = townArr[0].arr;
		    town_set(townArr);
		  });
	 }else{
	  let arr2 = $("#country").find(".country_option:lt(2)").clone(); //시/군 선택 전체 리스트 복사
	    $("#country").empty(); 
	    
	    for(x of arr2){
	    	$("#country").append($(x));
	    } 
	  }
  });
 


	/****************************************/
	/* 함수 섹션 */
	/****************************************/
	//도시 리스트 세팅
	function city_set(arr){
		let arr2 = $("#city").find(".city_list:lt(2)").clone();
	    $("#city").empty();

	    for(x of arr2){
	    	$("#city").append($(x));
	    }
		let sample = $(".city_list:eq(0)").clone();
		
		for(x of arr){
			let list = sample.clone();
			list.text(x);
			list.val(x);
			
			$("#city").append(list);
		}
	}
	
	// 시/군 리스트 세팅
	function town_set(arr){
		//초기세팅
		let arr2 = $("#country").find(".country_option:lt(2)").clone(); //시/군 선택 전체 리스트 복사
	    $("#country").empty(); 
	    
	    for(x of arr2){
	    	$("#country").append($(x));
	    } 
	    
	    //도/시 선택에 맞는 시/군 세팅
		let sample = $(".country_option:eq(0)").clone();
		
		for(x of arr){
			let list = sample.clone();
			list.text(x);
			list.val(x);
			
			$("#country").append(list);
		}
	}
});