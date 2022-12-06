$(function() {

	jsonArray = [];

	$("#summernote").summernote({
		width: 658,
		height: 500,
		minHeight: 300,
		maxHeight: 1000,
		focus: true,
		lang: "ko-KR",
		placeholder: '내용을 입력해 주세요.',

		toolbar: [
			['fontname', ['fontname']],
			['fontsize', ['fontsize']],
			['font', ['bold', 'underline', 'clear']],
			['color', ['color']],
			['para', ['ul', 'ol', 'paragraph']],
			['insert', ['link', 'picture', 'video']]
		],
		fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체'],
		fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72'],
		inheritPlaceholder: true,
		popover: {
			image: [
				['image', ['resizeFull', 'resizeHalf', 'resizeQuarter', 'resizeNone']],
				['float', ['floatLeft', 'floatRight', 'floatNone']],
				['remove', ['removeMedia']]
			],
			link: [
				['link', ['linkDialogShow', 'unlink']]
			],
		},

		callbacks: {
			onImageUpload: function(files, editor, welEditable) {

				// 파일 업로드(다중업로드를 위해 반복문 사용)
				for (var i = files.length - 1; i >= 0; i--) {
					uploadSummernoteImageFile(files[i],
						this);
				}
			}
		}
	});

	$("#summernote").summernote('formatPara');

	function uploadSummernoteImageFile(file, el) {
		var data = new FormData();
		data.append("file", file);

		$.ajax({
			url: "/meet-a-bwa/summernote_image.do",
			type: "POST",
			enctype: 'multipart/form-data',
			data: data,
			cache: false,
			contentType: false,
			processData: false,
			success: function(data) {
				var json = JSON.parse(data);
				$(el).summernote('editor.insertImage', json["url"]);
				jsonArray.push(json["url"]);
				jsonFn(jsonArray);
			},
			error: function(e) {
				console.log(e);
			}
		});
	}

	function jsonFn(jsonArray) {
		console.log("jsonArray :", jsonArray);
	}


	// 포스트 작성 버튼
	$('.btn-post-write').on('click', function() {
		console.log("write");

		let user_no = $.cookie("user_no");
		let meet_no = window.location.href.split("idx=")[1];
		let board_title = $('.board-title').val();
		let board_content = $('#summernote').val();

		console.log("user_no :", user_no);
		console.log("board_no :", meet_no);
		console.log("board title :", board_title);
		console.log("board content :", board_content);

		if (board_title == "" || board_title == null || board_title == undefined) {
			$("#common-alert-popup-wrap").removeClass("blind");
			$(".common-alert-txt").html("제목을 입력해 주세요.");
		} else if (board_content == "" || board_content == null || board_content == undefined) {
			$("#common-alert-popup-wrap").removeClass("blind");
			$(".common-alert-txt").html("내용을 입력해 주세요.");
		} else {
			$.ajax({
				url: "/meet-a-bwa/post-writeOK.do",
				type: "POST",
				dataType: "json",
				data: {
					board_title: board_title,
					board_content: board_content,
					user_no: user_no,
					meet_no: meet_no
				},

				success: function(res) {

					if (res.result == "1") {
						location.href = "/meet-a-bwa/meet-main.do?idx=" + meet_no;
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