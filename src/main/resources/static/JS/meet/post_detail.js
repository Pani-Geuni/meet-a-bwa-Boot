/**
 * @author 전판근
 */

$(function() {

	$("#btn-comment-insert").click(function() {

		let mother_no = $(this).attr("mother_no");
		if (mother_no == undefined) {
			mother_no = null;
		}

		let comment_content = $("#comment_content").val();
		let board_no = $(this).attr("board_no");
		let user_no = $(this).attr("user_no");
		let meet_no = window.location.href.split("idx=")[1].split("&")[0];

		console.log(mother_no);
		console.log(comment_content);
		console.log(board_no);
		console.log(user_no);
		console.log(meet_no);
		$.ajax({
			url: "/meet-a-bwa/comment_insert.do",
			type: "GET",
			dataType: "json",
			data: {
				comment_content: comment_content,
				board_no: board_no,
				user_no: user_no,
				meet_no: meet_no
			},

			success: function(res) {
				if (res.result == "1") {
					location.href = "/meet-a-bwa/post-detail.do?idx=" + meet_no + "&board_no=" + board_no;
				} else {
					console.log("실패")
				}
			},
			error: function(error) {
				console.log(error);
			}
		});
	});
})