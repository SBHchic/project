var status = true;

$(document).ready(function(){

	// guildBoard_writeForm.jsp 에서 글쓰기 버튼을 눌렀을 때
	$("#submit_write").click(function(){
		checkWrite();
		if (status) {
			var query = {
					title:$("#title").val(),
					content:$("#content").val(),
					notice:$("input[id='notice']:checked").val()
			};
			$.ajax({
				type:"post",
				url:"guildBoard_writePro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						location.href("guildBoard.jsp");
					} else {
						alert("작성에 실패했습니다.");
					}
				}
			});
		}
	});
	
	// guildBoard_view.jsp 에서 삭제버튼을 눌렀을 때
	$("#deleteWritten").click(function(){
		if(confirm("정말로 삭제하시겠습니까?") == true){
			var tmp = $("#deleteWritten").attr("name");
			var arr = tmp.split(",");
			var query = {
					writtenID:arr[0],
					replyID:arr[1]
			};
			$.ajax({
				type:"post",
				url:"guildBoard_deleteWrittenPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						location.href("guildBoard.jsp");
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
	
	// guildBoard_updateWrittenForm.jsp 에서 수정버튼을 눌렀을 때
	$("#submit_update").click(function(){
		checkWrite();
		
		if (status) {
			var tmp = $("#submit_update").attr("name");
			var arr = tmp.split(",");
			var query = {
					writtenID:arr[0],
					replyID:arr[1],
					title:$("#title").val(),
					content:$("#content").val(),
					notice:$("input[id='notice']:checked").val()
			};
			$.ajax({
				type:"post",
				url:"guildBoard_updateWrittenPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						
						location.href("guildBoard_view.jsp?writtenID="+arr[0]+"&replyID="+arr[1]);
					} else {
						alert("수정에 실패했습니다.");
					}
				}
			});
		}
	});
	
	// guildBoard_writeReplyForm.jsp 에서 답글쓰기 버튼을 눌렀을 때
	$("#submit_writeReply").click(function(){
		checkWrite();
		
		if (status) {
			var query = {
					title:$("#title").val(),
					content:$("#content").val(),
					writtenID:$("#submit_writeReply").attr("name")
			};
			$.ajax({
				type:"post",
				url:"guildBoard_writeReplyPro.jsp",
				data:query,
				success:function(data){
					if (data == 1){
						location.href("guildBoard.jsp");
					} else {
						alert("작성에 실패했습니다.");
					}
				}
			});
		}
	});
	
	// guildBoard_view.jsp에서 등록버튼을 눌렀을 때 (댓글)
	$("#writeComment").click(function(){
		checkCommentWrite();
		var tmp = $("#writeComment").attr("name");
		var arr = tmp.split(",");
		if (status) {
			var query = {
					writtenID:arr[0],
					replyID:arr[1],
					content:$("#content").val()
			};
			$.ajax({
				type:"post",
				url:"guildBoard_writeCommentPro.jsp",
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
	
	// 이전버튼을 눌렀을 때
	$("#previous").click(function(){
		window.history.back();
	});
});

function page(pageBtn){
	var pageNum = pageBtn.name;
	location.href("guildBoard.jsp?pageNumber="+pageNum);
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

function checkCommentWrite(){
	status = true;
	
	if(!$("#content").val()) {
        alert("댓글 내용을 입력하세요");
        $("#content").focus();
        status = false;
        return false; 
    }
}

// 댓글을 삭제할 경우 수행
function deleteComment(deleteObject){
	if(confirm("정말로 삭제하시겠습니까?") == true){
		var tmp = deleteObject.name;
		var arr = tmp.split("_");
		var query = {
					writtenID:arr[0],
					replyID:arr[1],
					commentID:arr[2],
					commentID_Re:arr[3]
		};
		$.ajax({
			type:"post",
			url:"guildBoard_deleteCommentPro.jsp",
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

// 댓글, 대댓글 수정버튼을 눌렀을 경우 + 취소버튼을 눌렀을 경우 수행
function update(comment) {
	var hidden = comment.name;
	var query1 = "comment_"+hidden;
	var query2 = "commentUpdate_"+hidden;
	$("#"+query1).toggle();
	$("#"+query2).toggle();
}

// 답글버튼을 눌렀을 때 수행
function commentReplyForm(commentID) {
	var hidden = commentID.name;
	var query = hidden+"re";
	$("#"+query).toggle();
}

// 댓글, 대댓글을 수정할 경우 수행
function updateComment(updateComment){
	var tmp = updateComment.name;
	var arr = tmp.split("_");
	
	if(!$("#content_"+tmp).val()){ // 수정할 내용을 입력하지 않았을 경우
		alert("수정할 내용을 입력하세요.");
		("#content_"+tmp).focus();
		return false;
	} else {
		var query = {
					writtenID:arr[0],
					replyID:arr[1],
					commentID:arr[2],
					commentID_Re:arr[3],
					content:$("#content_"+tmp).val()
		};
		$.ajax({
			type:"post",
			url:"guildBoard_updateCommentPro.jsp",
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

// 대댓글을 등록할 경우 수행
function writeCommentReply(commentReply){
	var tmp = commentReply.name;
	var arr = tmp.split("_");
	
	if(!$("#contentRe_"+tmp).val()){ // 대댓글 내용을 입력하지 않았을 경우
		alert("대댓글 내용을 입력하세요.");
		("#contentRe_"+tmp).focus();
		return false;
	} else {
		var query = {
					writtenID:arr[0],
					replyID:arr[1],
					commentID:arr[2],
					content:$("#contentRe_"+tmp).val()
		};
		$.ajax({
			type:"post",
			url:"guildBoard_writeCommentReplyPro.jsp",
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