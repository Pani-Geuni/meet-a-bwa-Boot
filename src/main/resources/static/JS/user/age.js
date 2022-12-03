/**
* @author 최진실
*/
$(function () {
    let ageRange = $("#ageRange");
    $("#ageWrap").each(function () {
        for (let i = 10; i <= 90; i+=10) {
            $("#ageBody").append("<option>" + i + "대"+ "</option>");
        }
    });

    let tag = $(".age_result:eq(0)").clone();
    let cnt=0;
    var arr = [];
    var arr_x = [];
    
    $("#ageBody").on("change", function (e) {
        tag = tag.clone();
        tag.removeClass("blind"); // 초기에 자동 생성된 버튼 숨기기
        let select_value = $("#ageBody option:selected").text();
        if (!arr.includes(select_value)&&select_value!='') {
            //선택한 관심사가 중복으로 들어가지 않도록 includes 함수 사용해서 배열 안에 해당 관심사가 없으면 아래 코드가 동작하게 함.
            tag.val(select_value + " X");
            arr.push(select_value);
            tag.attr("idx",++cnt);
            $("#tagWrap_age").append(tag);
            arr_x.push(select_value+" X");
        }
    });

    $("#tagWrap_age").on("click", "#delete_age",function () {
     	let idx =$(this).attr("idx"); // 현재 배열 크기
        let arr = $(".delete_interest").slice(); // 태그 배열 전체 복시
        
        let select_value = $(this).text();

		$("#tagWrap_age").empty().append($(arr[0]));

        for (let index = 1; index < arr.length; index++) { 
           if($(arr[index]).attr("idx")!=idx){ // 삭제할 값과 같지 않은 데이터들만
                $("#tagWrap_age").append(arr[index]); //부모에 붙여줌.
           }
        }
        
        for(let i = 0; i < result_x.length; i++) {
  			if(result_x[i]==select_value)  {
    			result.splice(i, 1);
    			result_x.splice(i, 1);
    			i--;
  			}
		}
    });

});