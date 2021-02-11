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
						
						location.href("boardQnA_view.jsp?boardQnA_ID="+arr[0]+"&boardQnA_ReplyID="+arr[1]);
					} else {
						alert("수정에 실패했습니다.");
					}
				}
			});
		}
	});
	
	//boardQnA_view.jsp 에서 삭제버튼을 눌렀을 때
	$("#deleteWritten").click(function(){
		if(confirm("정말로 삭제하시겠습니까?") == true){
			var tmp = $("#deleteWritten").attr("name");
			var arr = tmp.split(",");
			var query = {
					boardQnA_ID:arr[0],
					boardQnA_ReplyID:arr[1]
			};
			$.ajax({
				type:"post",
				url:"boardQnA_deleteWrittenPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						location.href("boardQnA.jsp");
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
	
	//boardQnA_view.jsp에서 등록버튼을 눌렀을 때 (댓글)
	$("#writeComment").click(function(){
		checkCommentWrite();
		var tmp = $("#writeComment").attr("name");
		var arr = tmp.split(",");
		if (status) {
			var query = {
					boardQnA_ID:arr[0],
					boardQnA_ReplyID:arr[1],
					boardQnA_Content:$("#boardQnA_Content").val()
			};
			$.ajax({
				type:"post",
				url:"boardQnA_writeCommentPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						location.reload();
					} else {
						alert("작성에 실패했습니다.");
					}
				}
			});
		}
	});
	
	// boardQnA_writeReplyForm.jsp 에서 답글쓰기 버튼을 눌렀을 때
	$("#submit_writeReply").click(function(){
		checkWrite();
		
		if (status) {
			var query = {
					boardQnA_Title:$("#boardQnA_Title").val(),
					boardQnA_Content:$("#boardQnA_Content").val(),
					boardQnA_ID:$("#submit_writeReply").attr("name")
			};
			$.ajax({
				type:"post",
				url:"boardQnA_writeReplyPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						location.href("boardQnA.jsp");
					} else {
						alert("작성에 실패했습니다.");
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

function checkCommentWrite(){
	status = true;
	
	if(!$("#boardQnA_Content").val()) {// 댓글 내용을 입력하지 않으면 수행
        alert("글 내용을 입력하세요");
        $("#boardQnA_Content").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
}

// 답글버튼을 눌렀을 때 수행
function commentReplyForm(commentID) {
	var hidden = commentID.name;
	var query = hidden+"re";
	$("#"+query).toggle();
}

// 댓글을 삭제할 경우 수행
function deleteComment(deleteObject){
	if(confirm("정말로 삭제하시겠습니까?") == true){
		var tmp = deleteObject.name;
		var arr = tmp.split("_");
		var query = {
					boardQnA_ID:arr[0],
					boardQnA_ReplyID:arr[1],
					boardQnA_CommentID:arr[2],
					boardQnA_CommentID_Re:arr[3]
					
		};
		$.ajax({
			type:"post",
			url:"boardQnA_deleteCommentPro.jsp",
			data:query,
			success:function(data){
				if (data == 1){
					location.reload();
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
}

// 대댓글을 등록할 경우 수행
function writeCommentReply(commentReply){
	var tmp = commentReply.name;
	var arr = tmp.split("_");
	
	if(!$("#boardQnA_ContentRe_"+tmp).val()){ // 댓글 내용을 입력하지 않았을 경우
		alert("댓글 내용을 입력하세요.");
		("#boardQnA_ContentRe_"+tmp).focus();
		return false;
	} else {
		var query = {
					boardQnA_ID:arr[0],
					boardQnA_ReplyID:arr[1],
					boardQnA_CommentID:arr[2],
					boardQnA_Content:$("#boardQnA_ContentRe_"+tmp).val()
		};
		$.ajax({
			type:"post",
			url:"boardQnA_writeCommentReplyPro.jsp",
			data:query,
			success:function(data){
				if (data == 1){
					location.reload();
				} else {
					alert("작성에 실패했습니다.");
				}
			}
		});
	}
}

// 댓글, 대댓글 수정버튼을 눌렀을 경우 + 취소버튼을 눌렀을 경우 수행
function update(Comment) {
	var hidden = Comment.name;
	var query1 = "comment_"+hidden;
	var query2 = "commentUpdate_"+hidden;
	$("#"+query1).toggle();
	$("#"+query2).toggle();
}

// 댓글, 대댓글을 수정할 경우 수행
function updateComment(updateComment){
	var tmp = updateComment.name;
	var arr = tmp.split("_");
	
	if(!$("#boardQnA_Content_"+tmp).val()){ // 수정할 내용을 입력하지 않았을 경우
		alert("수정할 내용을 입력하세요.");
		("#boardQnA_Content_"+tmp).focus();
		return false;
	} else {
		var query = {
					boardQnA_ID:arr[0],
					boardQnA_ReplyID:arr[1],
					boardQnA_CommentID:arr[2],
					boardQnA_CommentID_Re:arr[3],
					boardQnA_Content:$("#boardQnA_Content_"+tmp).val()
		};
		$.ajax({
			type:"post",
			url:"boardQnA_updateCommentPro.jsp",
			data:query,
			success:function(data){
				if (data == 1){
					location.reload();
				} else {
					alert("수정에 실패했습니다.");
				}
			}
		});
	}
}

function p(pageBtn){
	var pageNum = pageBtn.name;
	location.href("boardQnA.jsp?pageNumber="+pageNum);
}
	