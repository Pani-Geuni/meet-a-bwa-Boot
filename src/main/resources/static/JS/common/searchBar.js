/**
 * @author 김예은
 */
 
 $(document).ready(function() {
    let category = [];
    
    $("#detailCategory").click(function(){
    	$.getJSON("/meet-a-bwa/resources/json/cate.json", function(data) {
            category = data.category;
            category_load(category);
        });
    });
    
    $("#searchWord").keydown(function(key){
    	if(key.keyCode==13) {             
	    	if($("#category").val() == "모임")
	    		location.href = "/meet-a-bwa/meet-list.do?type=interest&&typeData=" + $("#detailCategory").val() + "&&searchWord=" + $(this).val();
    		else if($("#category").val() == "액티비티")
	    		 location.href = "/meet-a-bwa/activity-list.do?category=" + $("#detailCategory").val() + "&&searchWord=" + $(this).val();
		}
    });
 
    function category_load(arr){
        let cnt = 1;
        let sample = $(".detailCate_list:eq(0)").clone();
        $("#detailCategory").empty().append(".detailCate_list:eq(0)");

        for(x of arr){
            let list = sample.clone();
            list.text(x);
            list.val(x);
            list.attr("idx", cnt++);
            $("#detailCategory").append(list);
        }
    }
    
 });