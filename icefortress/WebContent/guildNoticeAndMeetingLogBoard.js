var status = true;

$(document).ready(function(){

	// guildNoticeBoard_writeForm.jsp와 meetingLogBoard_writeForm.jsp 에서 작성 버튼을 눌렀을 때
	$("#submit_write").click(function(){
		checkWrite();
		if (status) {
			var query = {
					title:$("#title").val(),
					content:$("#content").val(),
					location:$("#submit_write").attr("name")
			};
			$.ajax({
				type:"post",
				url:"guildNoticeAndMeetingLogBoard_writePro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						if ($("#submit_write").attr("name") == 1){
							location.href="guildNoticeBoard.jsp";
						} else if ($("#submit_write").attr("name") == 0){
							location.href="meetingLogBoard.jsp";
						} else {
							alert("알수 없는 오류가 발생했습니다.");
						}
					} else {
						alert("작성에 실패했습니다.");
					}
				}
			});
		}
	});
	
	// guildNoticeBoard_view.jsp와 meetingLogBoard_view.jsp 에서 삭제버튼을 눌렀을 때
	$("#deleteWritten").click(function(){
		if(confirm("정말로 삭제하시겠습니까?") == true){
			var tmp = $("#deleteWritten").attr("name");
			var arr = tmp.split("_");
			var query = {
					writtenID:arr[0]
			};
			$.ajax({
				type:"post",
				url:"guildNoticeAndMeetingLogBoard_deleteWrittenPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						if (arr[1] == 1){
							location.href="guildNoticeBoard.jsp?pageNumber="+arr[2];
						} else if (arr[1] == 0){
							location.href="meetingLogBoard.jsp?pageNumber="+arr[2];
						} else {
							alert("알수 없는 오류가 발생했습니다.");
						}
					} else if (data == 0){
						alert("접근 권한이 없습니다.");
					} else {
						alert("삭제에 실패했습니다.");
					}
				}
			});
		} else {
			return;
		}
	});
	
	// guildNoticeBoard_updateWrittenForm.jsp와 meetingLogBoard_updateWrittenForm.jsp 에서 수정버튼을 눌렀을 때
	$("#submit_update").click(function(){
		checkWrite();
		
		if (status) {
			var tmp = $("#submit_update").attr("name");
			var arr = tmp.split("_");
			var query = {
					writtenID:arr[0],
					title:$("#title").val(),
					content:$("#content").val()
			};
			$.ajax({
				type:"post",
				url:"guildNoticeAndMeetingLogBoard_updateWrittenPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						if(arr[1] == 1){
							location.href="guildNoticeBoard_view.jsp?writtenID="+arr[0]+"&pageNumber="+arr[2];
						} else if (arr[1] == 0){
							location.href="meetingLogBoard_view.jsp?writtenID="+arr[0]+"&pageNumber="+arr[2];
						} else {
							alert("알 수 없는 오류가 발생했습니다.");
						}
					} else {
						alert("수정에 실패했습니다.");
					}
				}
			});
		}
	});
	
	// 이전버튼을 눌렀을 때
	$("#previous").click(function(){
		window.history.back();
	});
});

function page(pageBtn){
	var arr = (pageBtn.name).split("_");
	var pageNum = arr[0];
	var location = arr[1];
	if(location == 1){
		location.href="guildNoticeBoard.jsp?pageNumber="+pageNum;
	} else {
		location.href="meetingLogBoard.jsp?pageNumber="+pageNum;
	}
	
}

function checkWrite(){
	status = true;
	
	if(!$("#title").val()) {
        alert("글 제목을 입력하세요");
        $("#title").focus();
        status = false;
        return false; 
    }
    
    if(!$("#content").val()) {
        alert("글 내용을 입력하세요");
        $("#content").focus();
        status = false;
        return false;
    }
}
