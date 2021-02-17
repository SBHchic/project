var status = true;

$(document).ready(function(){
	// [로그인]버튼을 클릭하면 자동실행	
	// 입력한 아이디와 비밀번호를 갖고 loginPro.jsp 페이지 실행
	$("#login").click(function(){
		checkIt();// 입력폼에 입력한 상황 체크
		if(status){
		  // 입력된 사용자의 아이디와 비밀번호를 얻어냄
		  var query = {userID : $("#userID").val(), 
				       userPassword:$("#userPassword").val()};
		  
		  $.ajax({
		     type: "post",
		     url: "loginPro.jsp",
		     data: query,
		     success: function(data){
		    	 if(data == 1){ // 로그인 성공
		    		 location.href="main.jsp";
		         }else if(data == 0){ // 비밀번호 틀림
		    	  	 alert("비밀번호가 틀립니다.");
		    	  	 $("#userPassword").val("");
		    	  	 $("#userPassword").focus();
		    	 }else if(data == -1){ // 아이디 틀림
		    		 alert("아이디가 존재하지 않습니다.");
		    		 $("#userID").val("");
		    		 $("#userPassword").val("");
		    	  	 $("#userID").focus();
		    	 }
		     }
		  });
		}
	});
	
});

// 인증되지 않은 사용자 영역에서 사용하는 입력 폼의 입력값 유무 확인
function checkIt(){
	status = true;
	if(!$.trim($("#userID").val())){
		alert("아이디를 입력하세요.");
		$("#userID").focus();
		status = false;
		return false;
	}
	
	if(!$.trim($("#userPassword").val())){
		alert("비밀번호를 입력하세요.");
		$("#userPassword").focus();
		status = false;
		return false;
	}
}