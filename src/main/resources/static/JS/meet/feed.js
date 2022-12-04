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

	$(".feed-post").click(function() {

		let meet_no = window.location.href.split("idx=")[1];
		let board_no = $(this).attr("board_no");

		console.log("board_no :", $(this).attr("board_no"));
		console.log(meet_no);
		
		location.href = "/meet-a-bwa/post-detail.do?idx=" + meet_no +"&board_no=" + board_no;

	});


})