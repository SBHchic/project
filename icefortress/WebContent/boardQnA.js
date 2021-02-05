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

// 페이지 번호를 선택했을 때 수행
function p(pageBtn) {
	var page = pageBtn.name;
	var query = "boardQnA.jsp?pageNumber=" + page;
	location.href(query);
}