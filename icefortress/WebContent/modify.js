var status = true;
var checkIDBoolean = false;
var checkedID = "";

$(document).ready(function(){
    // modify.jsp페이지의 [아이디 변경] 버튼을 클릭하면 자동실행
	// 입력한 비밀번호를 갖고 memberCheck.jsp페이지 실행
	$("#checkBeforeModifyingID").click(function(){ // [아이디 변경] 버튼 클릭
		var query = {userPassword:$("#userPassword").val()};
		
		$.ajax({
			type: "post",
			url: "memberCheck.jsp",
			data: query,
			success: function(data){
				if(data == 1) { // 비밀번호 일치
		    		 location.href="modifyIDForm.jsp";
		    	} else if (data == 0) { //비밀번호 틀림
		    	  	 alert("비밀번호가 틀립니다.");
		    	  	 $("#userPassword").val("");
		    	  	 $("#userPassword").focus();
		    	} else {
		    		 alert("알수 없는 오류가 발생했습니다.");
		    	}
		   }
		});
	});
	
	// modify.jsp페이지의 [비밀번호 변경] 버튼을 클릭하면 자동실행
	// 입력한 비밀번호를 갖고 memberCheck.jsp페이지 실행
	$("#checkBeforeModifyingPassword").click(function(){ // [비밀번호 변경] 버튼 클릭
		var query = {userPassword:$("#userPassword").val()};
		
		$.ajax({
			type: "post",
			url: "memberCheck.jsp",
			data: query,
			success: function(data){
				if(data == 1) { // 비밀번호 일치
		    		 location.href="modifyPasswordForm.jsp";
		    	} else if (data == 0) { //비밀번호 틀림
		    	  	 alert("비밀번호가 틀립니다.");
		    	  	 $("#userPassword").val("");
		    	  	 $("#userPassword").focus();
		    	} else {
		    		 alert("알수 없는 오류가 발생했습니다.");
		    	}
		   }
		});
	});
	
	// modifyPasswordForm.jsp페이지의 [비밀번호 변경] 버튼 클릭시 자동실행
	//수정폼에 입력한 값을 갖고 modifyPasswordPro.jsp 실행
	$("#modifyingPassword").click(function(){
		checkRepass(); // 입력폼에 입력한 상황 체크
		if (status) {
			var query = { userPassword:$("#userPassword").val()};
			$.ajax({
				type: "post",
				url: "modifyPasswordPro.jsp",
				data: query,
				success: function(data){
					if(data == 1) { // 비밀번호 변경 성공
						alert("비밀번호가 변경되었습니다.");
						location.href="main.jsp";
					} else {
						alert("알수 없는 오류가 발생했습니다.");
					}
				}
			});
		}
	});
	
	// [중복확인] 버튼을 클릭하면 자동실행
	// 입력한 아이디 값을 가지고 confirmId.jsp페이지 실행
	$("#checkID").click(function(){
	  if($("#newUserID").val()){
		// 아이디를 입력하고 [중복확인]버튼을 클릭한 경우
		var query = {userID:$("#newUserID").val()};
		
	    $.ajax({
	    	type:"post", // 요청방식
	    	url:"confirmId.jsp", // 요청페이지
	    	data:query, // 파라미터
	    	success:function(data){ // 요청페이지 처리에 성공시
	    		if(data == 1){ // 사용할 수 없는 아이디
	    			alert("사용할 수 없는 아이디입니다.");
	    	    	$("#newUserID").val("");
	    	    } else if(data == -1) { // 사용할 수 있는 아이디
	    	  	    alert("사용할 수 있는 아이디입니다.");
	    	  	    checkIDBoolean = true;
	    	  	    checkedID = $("#newUserID").val();
	    	  	}
	 	    }
	    });
	  } else { // 아이디를 입력하지 않고 [중복확인]버튼을 클릭한 경우
		  alert("사용할 아이디를 입력하세요");
		  $("#newUserID").focus();
	  }
	});
	
	// modifyIDForm.jsp페이지의 [아이디 변경] 버튼 클릭시 자동실행
	//수정폼에 입력한 값을 갖고 modifyIDPro.jsp 실행
	$("#modifyingID").click(function(){
		checkID(); // 입력폼에 입력한 아이디 + 아이디 중복확인 체크
		if (status) {
			var query = { newUserID:$("#newUserID").val()};
			$.ajax({
				type: "post",
				url: "modifyIDPro.jsp",
				data: query,
				success: function(data){
					if(data == 1) { // 아이디 변경 성공
						alert("아이디가 변경되었습니다. 다시 로그인 해주세요.");
						location.href="loginForm.jsp";
					} else {
						alert("알수 없는 오류가 발생했습니다.");
					}
				}
			});
		}
	});
	
	// [취소]버튼 클릭시 자동실행
	$("#cancel").click(function(){
		location.href="main.jsp";
	});
	
	// modify.jsp페이지의 [탈퇴]버튼을 클릭하면 자동실행
	// 입력한 비밀번호를 가지고 memberCheck.jsp실행 후 비밀번호가 맞으면 deletePro.jsp페이지를 실행
	$("#delete").click(function(){ // [탈퇴]버튼 클릭
		if (confirm("정말 탈퇴하시겠습니까?") == true){ // 확인
			var query = {userPassword:$("#userPassword").val()};
			
			// 입력한 비밀번호를 가지고 memberCheck.jsp페이지 실행 
			$.ajax({
				type: "post",
				url: "memberCheck.jsp",
				data: query,
				success: function(data){
					if(data == 1){ // 비밀번호 맞음
						// 회원탈퇴 페이지 deletePro.jsp 실행
						$.ajax({
							   type: "POST",
							   url: "deletePro.jsp",
							   data: query,
							   success: function(data){
								   if(data == 1){ // 탈퇴 성공
									  alert("탈퇴 되었습니다. 지금까지 이용해주셔서 감사합니다.");
									  location.href="main.jsp";
							       } else {
							       	  alert("알수 없는 오류가 발생했습니다.");
							       }
							   }
							});
					} else { //비밀번호 틀림
			    	  	 alert("비밀번호가 맞지 않습니다.");
			    	  	 $("#userPassword").val("");
			    	  	 $("#userPassword").focus();
			    	}   
				}
			});
		} else { // 취소
    	return false;
		}
	});	
	
 });
 
 // 사용자가 입력한 비밀번호와 비밀번호 확인부분이 같은지 확인
function checkRepass() {
	status = true;
	
	// 비밀번호와 재입력비밀번호가 같지않으면 수행
    if($("#userPassword").val() != $("#repass").val()){
        alert("비밀번호를 동일하게 입력하세요");
        $("#repass").var("");
        $("#repass").focus();
        status = false;
        return false;
    }
}

function checkID() {
	status = true;
	
	if(!$("#newUserID").val()) {// 아이디를 입력하지 않으면 수행
        alert("아이디를 입력하세요");
        $("#newUserID").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    // 아이디 중복체크를 하지 않은 경우 + 중복체크 후 아이디 수정을 했을 경우
    if(checkIDBoolean == false || $("#newUserID").val() != checkedID){
    	alert("아이디 중복확인을 해주세요");
    	$("#checkID").focus();
    	status = false;
	}
}

