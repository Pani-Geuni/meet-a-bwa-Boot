/**
 * @author 전판근
 */

$(function() {
	console.log("FEED");

	$(".meet-deatil-aside-title").click(function() {
		let meet_no = $(this).attr("idx");
		location.href = "/meet-a-bwa/meet-main.do?idx=" + meet_no;
	})

	$(".meet-member-info").click(function() {
		console.log("idx :", $(this).attr("idx"));
		let meet_no = $(this).attr("idx");

		location.href = "/meet-a-bwa/meet-member.do?idx=" + meet_no;
	});

	$(".meet-detail-link").on("click", function() {
		let idx = $(this).attr("idx");
		console.log(idx);
		location.href = "meet-detail.do?idx=" + idx;
	})

	$(".feed-post").click(function() {

		let meet_no = window.location.href.split("idx=")[1];
		let board_no = $(this).attr("board_no");

		console.log("board_no :", $(this).attr("board_no"));
		console.log(meet_no);

		location.href = "/meet-a-bwa/post-detail.do?idx=" + meet_no + "&board_no=" + board_no;

	});

	// 라벨 클릭시 옵션 목록이 열림/닫힘
	$(".feed").on('click', '.img-more', function(event) {
		if ($(this).parent().hasClass('active')) {
			$(this).parent().removeClass('active');
		} else {
			$(this).parent().addClass('active');
		}

		event.stopPropagation();
	});

	$(".all-feed .feed-post").each(function(index, item) {
		console.log($(this)[0].offsetHeight, $(this)[0].scrollHeight);
		
		// feed-post가 overflow 됐을 때, 더보기 버튼 활성화
		if ($(this)[0].offsetHeight < $(this)[0].scrollHeight) {
			$(this).next().children('div:eq(1)').removeClass("blind");
		} 
	})
	
	
	$(".feed").on('click', '#btn-more-post', function() {
		let feed_post = $(this).parent().parent().siblings(".feed-post");
		feed_post.css('height', $(feed_post)[0].scrollHeight);
		$(this).addClass("blind");
	});
		

	$(".post-detail-top").on('click', '.img-more', function(event) {
		if ($(this).parent().hasClass('active')) {
			$(this).parent().removeClass('active');
		} else {
			$(this).parent().addClass('active');
		}
		event.stopPropagation();
	});

	let idx = '';
	let title = '';
	let content = '';

	//----- OPEN
	$('[data-popup-open]').on('click', function(e) {
		idx = $(this).parent(".post-option-list").attr("idx");

		// 팝업 열기 버튼 클릭시 동작하는 이벤트입니다.
		var targeted_popup_class = $(this).attr('data-popup-open');
		if (targeted_popup_class == 'update') {
			let meet_no = window.location.href.split("idx=")[1].split("&")[0];
			let board_no = $(this).parents().attr("idx").split("&")[0]; 

			location.href = "/meet-a-bwa/post-update.do?idx=" + meet_no + "&board_no=" + board_no;
			
		} else {
			$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350);			
		}

		e.preventDefault();
	});

	//----- CLOSE
	$('[data-popup-close]').on('click', function(e) {
		// 팝업 닫기 버튼 클릭시 동작하는 이벤트입니다.
		var targeted_popup_class = jQuery(this).attr('data-popup-close');
		$('[data-popup="' + targeted_popup_class + '"]').fadeOut(350);

		e.preventDefault();
	});

	$("#btn-meet-write").on('click', function() {

		let meet_no = window.location.href.split("idx=")[1].split("&")[0];

		location.href = "/meet-a-bwa/post-write.do?idx=" + meet_no;
	})


	$("#btn-delete").on('click', function() {
		console.log(idx);

		let meet_no = window.location.href.split("idx=")[1].split("&")[0];

		console.log(meet_no);

		$.ajax({
			url: "/meet-a-bwa/post-delete.do",
			type: "GET",
			data: {
				board_no: idx
			},

			dataType: "json",
			success: function(res) {
				// 삭제 성공시 meet-main으로 이동
				location.replace('meet-main.do?idx=' + meet_no);
			},
			error: function(res, status, text) {
				console.log(text)
			}
		});
	});



	$("#activity-summary-list").on("click", ".activity_list_item", function() {
		let idx = $(this).attr("idx");
		console.log("activity idx :", idx);
		//		location.href = "/meet-a-bwa/activity-main.do?idx=" + idx;
	});
})