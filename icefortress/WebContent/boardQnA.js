var status = true;

$(document).ready(function(){
	
	// writeQnAForm.jsp 에서 글쓰기 버튼을 눌렀을 때
	$("#submit_write").click(function(){
		checkWrite();
		
		if (status) {
			var query = {
					boardQnA_Title:$("#boardQnA_Title").val(),
					boardQnA_Content:$("#boardQnA_Content").val()
			};
			$.ajax({
				type:"post",
				url:"writeQnAPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						location.href("boardQnA.jsp");
					} else {
						alert("작성에 실패했습니다.");
						window.history.back();
					}
				}
			});
		}
	});
	
	// boardQnA_updateWrittenForm.jsp 에서 수정버튼을 눌렀을 때
	$("#submit_update").click(function(){
		checkWrite();
		
		if (status) {
			var tmp = $("#submit_update").attr("name");
			var arr = tmp.split(",");
			var query = {
					boardQnA_ID:arr[0],
					boardQnA_ReplyID:arr[1],
					boardQnA_Title:$("#boardQnA_Title").val(),
					boardQnA_Content:$("#boardQnA_Content").val()
			};
			$.ajax({
				type:"post",
				url:"boardQnA_updateWrittenPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						location.href("boardQnA_view.jsp?boardQnA_ID="+boardQnA_ID+"&boardQnA_ReplyID="+boardQnA_ReplyID);
					} else {
						alert("수정에 실패했습니다.");
						window.history.back();
					}
				}
			});
		}
	});
	
	// writeQnAForm.jsp, boardQnA_view.jsp 에서 사용
	// 이전버튼을 눌렀을 때
	$("#previous").click(function(){
		window.history.back();
	});
	
});

function checkWrite(){
	status = true;
	
	if(!$("#boardQnA_Title").val()) {// 글 제목을 입력하지 않으면 수행
        alert("글 제목을 입력하세요");
        $("#boardQnA_Title").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#boardQnA_Content").val()) {// 글 내용을 입력하지 않으면 수행
        alert("글 내용을 입력하세요");
        $("#boardQnA_Content").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
}

function commentReply(commentID) {
	var hidden = commentID.name;
	var query = hidden+"re";
	$("#"+query).toggle();
}