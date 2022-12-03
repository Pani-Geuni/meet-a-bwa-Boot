/**
* @author 최진실
*/
$(function () {

    var interest = ["취미",
        "팬클럽",
        "방송/연예",
        "스포츠/레저",
        "게임",
        "만화/애니메이션",
        "맛집/요리",
        "생활정보/인테리어",
        "건강/다이어트",
        "패션/뷰티",
        "여행/캠핑",
        "반려동물/동물",
        "문화/예술",
        "음악",
        "어학/외국어",
        "취업/자격증",
        "교육/공부",
        "IT/컴퓨터",
        "인문/과학",
        "경제/재테크",
        "종교/봉사",
        "일상/이야기",
        "나이/또래모임",
        "친목/모임",
        "자연/귀농"];

    cate_init();    
    function cate_init(){
        let sample = $(".interest_opt").eq(0).clone();
        for (let i = 0; i < interest.length; i++) {
            sample = sample.clone().text(interest[i]);
            sample.val(interest[i]);
            $("#interest").append(sample);
        }
    }


    /*************************************************** */
    /** 관심사 태그 추가 */
    /*************************************************** */
    let tag = $(".delete_interest:eq(0)").clone();
    let cnt=0;
	var result = [];
	var result_x = [];
	
    $("#interest").on("change", function (e) {
        tag = tag.clone();
        tag.removeClass("blind");
        let select_value = $(this).val();
    		
		if (!result.includes(select_value)&&select_value!='') {
            //선택한 관심사가 중복으로 들어가지 않도록 includes 함수 사용해서 배열 안에 해당 관심사가 없으면 아래 코드가 동작하게 함.
            tag.val(select_value + " X");
            result.push(select_value);
            tag.attr("idx",++cnt);
            $("#tagWrap").append(tag);
            result_x.push(select_value+ " X");
        }
    });


    /*************************************************** */
    /** 관심사 태그 삭제 */
    /*************************************************** */
    $("#tagWrap").on("click",".delete_interest",function () {
        let idx =$(this).attr("idx"); // 현재 배열 크기
        let arr = $(".delete_interest").slice(); // 태그 배열 전체 복시
        let select_value = $(this).val();
        
        $("#tagWrap").empty().append($(arr[0])); // 부모 비우고, 복사할 0번째 샘플 데이터만 붙임

        for (let index = 1; index < arr.length; index++) { 
           if($(arr[index]).attr("idx")!=idx){ // 삭제할 값과 같지 않은 데이터들만
                $("#tagWrap").append(arr[index]); //부모에 붙여줌.
           }
        }
        
        for(let i = 0; i < result_x.length; i++) {
  			if(result_x[i] == select_value)  {
    			result.splice(i, 1);
    			result_x.splice(i, 1);
    			i--;
  			}
		}
    });

});