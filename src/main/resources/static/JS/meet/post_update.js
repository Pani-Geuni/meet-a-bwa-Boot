$(function() {


	// 포스트 작성 버튼
	$('.btn-post-update').on('click', function() {
		console.log("write");

		let user_no = $.cookie("user_no");
		let meet_no = window.location.href.split("idx=")[1].split("&")[0];
		let board_no = window.location.href.split("board_no=")[1];
		let board_title = $('.board-title').val();
		let board_content = $('#summernote').val();

		console.log("user_no :", user_no);
		console.log("meet_no :", meet_no);
		console.log("board title :", board_title);
		console.log("board content :", board_content);

		if (board_title == "" || board_title == null || board_title == undefined) {
			$("#common-alert-popup-wrap").removeClass("blind");
			$(".common-alert-txt").html("제목을 입력해 주세요.");
		} else if (board_content == "" || board_content == null || board_content == undefined) {
			$("#common-alert-popup-wrap").removeClass("blind");
			$(".common-alert-txt").html("내용을 입력해 주세요.");
		} else {
			console.log("hi");
			$.ajax({
				url: "/meet-a-bwa/post-updateOK.do",
				type: "POST",
				dataType: "json",
				data: {
					board_title: board_title,
					board_content: board_content,
					user_no: user_no,
					meet_no: meet_no,
					board_no: board_no
				},

				success: function(res) {

					if (res.result == "1") {
						location.href = "/meet-a-bwa/post-detail.do?idx="+ meet_no+"&board_no=" + board_no;
					} else {
						console.log("res result 0");
					}
				},

				error: function(error) {
					console.log(error);
				}
			});
		}
	});
})