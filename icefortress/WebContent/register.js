var status = true;
var checkIDBoolean = false;
var checkedID = "";

$(document).ready(function(){
	// [중복확인] 버튼을 클릭하면 자동실행
	// 입력한 아이디 값을 가지고 confirmId.jsp페이지 실행
	$("#checkID").click(function(){
	  if($("#userID").val()){
		// 아이디를 입력하고 [중복확인]버튼을 클릭한 경우
		var query = {userID:$("#userID").val()};
		
	    $.ajax({
	    	type:"post", // 요청방식
	    	url:"confirmId.jsp", // 요청페이지
	    	data:query, // 파라미터
	    	success:function(data){ // 요청페이지 처리에 성공시
	    		if(data == 1){ // 사용할 수 없는 아이디
	    			alert("사용할 수 없는 아이디입니다.");
	    	    	$("#userID").val("");
	    	    } else if(data == -1) { // 사용할 수 있는 아이디
	    	  	    alert("사용할 수 있는 아이디입니다.");
	    	  	    checkIDBoolean = true;
	    	  	    checkedID = $("#userID").val();
	    	  	}
	 	    }
	    });
	  } else { // 아이디를 입력하지 않고 [중복확인]버튼을 클릭한 경우
		  alert("사용할 아이디를 입력하세요");
		  $("#userID").focus();
	  }
	});
	
	// [회원가입]버튼을 클릭하면 자동실행
	// 사용자가 가입폼인 registerForm.jsp페이지에 입력한 내용을 가지고 registerPro.jsp페이지 실행
	$("#register").click(function(){
	   checkIt(); // 입력폼에 입력한 상황 체크
	   
	   if(status){
		  var query = {userID:$("#userID").val(), 
				  userPassword:$("#userPassword").val(),
				  server:$("#server").val()};
		  
		  $.ajax({
		      type:"post",
		      url:"registerPro.jsp",
		      data:query,
		      success:function(data){
		      	  if (data == 1){
		      	  	 alert("회원가입에 성공하셨습니다.");
		    	  	 location.href="main.jsp";
		    	  } else {
		    	  	 alert("알수 없는 오류가 발생했습니다.");
		    	  }
		 	  }
		  });
	   }
	});
	
	// [취소]버튼을 클릭하면 자동실행
	$("#cancel").click(function(){
		location.href="main.jsp";
	});

 });

// 사용자가 입력폼에 입력한 상황을 체크
function checkIt() {
	status = true;
	
    if(!$("#userID").val()) {// 아이디를 입력하지 않으면 수행
        alert("아이디를 입력하세요");
        $("#userID").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#userPassword").val()) { // 비밀번호를 입력하지 않으면 수행
        alert("비밀번호를 입력하세요");
        $("#userPassword").focus();
        status = false;
        return false;
    }
    // 비밀번호와 재입력비밀번호가 같지않으면 수행
    if($("#userPassword").val() != $("#repass").val()){
        alert("비밀번호를 동일하게 입력하세요");
        $("#repass").focus();
        status = false;
        return false;
    }
    // 아이디 중복체크를 하지 않은 경우 + 중복체크 후 아이디 수정을 했을 경우
    if(checkIDBoolean == false || $("#userID").val() != checkedID){
    	alert("아이디 중복확인을 해주세요");
    	$("#checkID").focus();
    	status = false;
	}
	
    if(!$("#server").val()){ // 서버를 입력하지 않으면 수행
    	alert("서버를 입력하세요");
    	$("#server").focus();
    	status = false;
	}
}