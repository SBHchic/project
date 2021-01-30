var status = true;

$(document).ready(function(){

	// numberOfItems.jsp 에서의 작동
	// numberOfItems.jsp 에서 확인 버튼을 눌렀을 때
	$("#firstSubmit").click(function(){
		if($("input:radio[id=normalItem]").is(":checked")){
			location.href("numberOfItems_normal.jsp");
		} else if($("input:radio[id=superiorItem]").is(":checked")){
			location.href("numberOfItems_superior.jsp");
		}
	});
	
	// numberOfItems_normal.jsp 에서의 작동
	// nuberOfItems_normal.jsp 에서 토드 여부를 확인하고 토드에 관한 사항들을 보여줌
	$("input[id=toad_normalItem]").click(function(){
		if($("input[id=toad_normalItem]:checked").val()=="true"){ // 토드O 인 경우
			if($("#level_normalItem").val()>160){ // 토드가 안되는 레벨에서 체크한 경우
				alert("토드는 160제 이하에서 가능합니다");
				$("#level_normalItem").focus();
			} else {
				$("#selectToadProperty_normalItem").show();
			}
		} else if($("input[id=toad_normalItem]:checked").val()=="false"){ // 토드X 인 경우
			$("#selectToadProperty_normalItem").hide();
		}
	});
	
	// nuberOfItems_normal.jsp 에서 시행버튼을 눌렀을 때
	$("#finalSubmit_normal").click(function(){
		checkNormalItem();
		
		if (status) {
			var query = {
					numberOfItems:$("#numberOfItems_normalItem").val(),
					level:$("#level_normalItem").val(),
					numberOfRealItems:$("#numberOfRealItems_normalItem").val(),
					fromStarforce:$("#fromStarforce_normalItem").val(),
					starforce:$("#starforce_normalItem").val(),
					ignoreDestroy:$("#ignoreDestroy_normalItem").val(),
					succededCatch:$("input[id=succededCatch_normalItem]:checked").val(),
					mapleEvent:$("input[id=mapleEvent_normalItem]:checked").val(),
					toad:$("input[id=toad_normalItem]:checked").val(),
					numberOfToadItems:$("#numberOfToadItems_normalItem").val(),
					toadToStarforce:$("#toadToStarforce_normalItem").val(),
					toadIgnoreDestroy:$("#toadIgnoreDestroy_normalItem").val()
			};
			$.ajax({
				type:"post",
				url:"numberOfItems_normalPro.jsp",
				data:query,
				success:function(data){
					$("#result_normal").html(data);
					$("#resultbox_normal").show();
				}
			});
		}
	});
	
	// numberOfItems_superior.jsp 에서의 작동
	// nuberOfItems_superior.jsp 에서 시행버튼을 눌렀을 때
	$("#finalSubmit_superior").click(function(){
		checkSuperiorItem();
		
		if (status) {
			var query = {
					numberOfItems:$("#numberOfItems").val(),
					level:$("#level").val(),
					numberOfRealItems:$("#numberOfRealItems").val(),
					fromStarforce:$("#fromStarforce").val(),
					starforce:$("#starforce").val(),
					succededCatch:$("input[id=succededCatch]:checked").val()
			};
			$.ajax({
				type:"post",
				url:"numberOfItems_superiorPro.jsp",
				data:query,
				success:function(data){
					$("#result_superior").html(data);
					$("#resultbox_superior").show();
				}
			});
		}
	});
	
	// normal, superior 둘다 작동
	// nuberOfItems_normal.jsp 에서 이전버튼을 눌렀을 때
	$("#previous").click(function(){
		location.href("numberOfItems.jsp");
	});
});

function checkNormalItem() {
	status = true;
	
	if(!$("#numberOfItems_normalItem").val()) {// 표본 개수를 입력하지 않으면 수행
        alert("표본 개수를 입력하세요");
        $("#numberOfItems_normalItem").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#level_normalItem").val()) {// 레벨을 입력하지 않으면 수행
        alert("아이템 레벨을 입력하세요");
        $("#level_normalItem").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#numberOfRealItems_normalItem").val()) {// 본템 개수 제한을 입력하지 않으면 수행
        alert("본템 개수 제한을 입력하세요");
        $("#numberOfRealItems_normalItem").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#fromStarforce_normalItem").val()) {// 시작 스타포스를 입력하지 않으면 수행
        alert("시작 스타포스를 입력하세요");
        $("#fromStarforce_normalItem").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#starforce_normalItem").val()) {// 목표 스타포스를 입력하지 않으면 수행
        alert("목표 스타포스를 입력하세요");
        $("#starforce_normalItem").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if($("input[id=toad_normalItem]:checked").val()=="true"){
    	if(!$("#numberOfToadItems_normalItem").val()) {// 토드템 개수 제한을 입력하지 않으면 수행
 	       alert("토드템 개수 제한을 입력하세요");
    	    $("#numberOfToadItems_normalItem").focus();
        	status = false;
      	  	return false; // 사용자가 서비스를 요청한 시점으로 돌아감
  	 	}
    
	    if(!$("#toadToStarforce_normalItem").val()) {// 토드템 목표 스타포스를 입력하지 않으면 수행
   	    	alert("토드템 목표 스타포스를 입력하세요");
        	$("#toadToStarforce_normalItem").focus();
        	status = false;
        	return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    	}
    }
    
    var query = {
    		numberOfItems:$("#numberOfItems_normalItem").val(),
			level:$("#level_normalItem").val(),
			numberOfRealItems:$("#numberOfRealItems_normalItem").val(),
			fromStarforce:$("#fromStarforce_normalItem").val(),
			starforce:$("#starforce_normalItem").val(),
			ignoreDestroy:$("#ignoreDestroy_normalItem").val(),
			succededCatch:$("input[id=succededCatch_normalItem]:checked").val(),
			mapleEvent:$("input[id=mapleEvent_normalItem]:checked").val(),
			toad:$("input[id=toad_normalItem]:checked").val(),
			numberOfToadItems:$("#numberOfToadItems_normalItem").val(),
			toadToStarforce:$("#toadToStarforce_normalItem").val(),
			toadIgnoreDestroy:$("#toadIgnoreDestroy_normalItem").val()
	};
	$.ajax({
		type:"post",
		url:"checkProperty_normal.jsp",
		data:query,
		success:function(data){
			if(data == false){
				alert("오류가 발생했습니다. 입력정보를 다시 확인하세요");
				status = false;
				return false;
			} 
		}
	});
}

function checkSuperiorItem() {
	status = true;
	
	if(!$("#numberOfItems").val()) {// 표본 개수를 입력하지 않으면 수행
        alert("표본 개수를 입력하세요");
        $("#numberOfItems").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#level").val()) {// 레벨을 입력하지 않으면 수행
        alert("아이템 레벨을 입력하세요");
        $("#level").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#numberOfRealItems").val()) {// 본템 개수 제한을 입력하지 않으면 수행
        alert("본템 개수 제한을 입력하세요");
        $("#numberOfRealItems").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#fromStarforce").val()) {// 시작 스타포스를 입력하지 않으면 수행
        alert("시작 스타포스를 입력하세요");
        $("#fromStarforce").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    if(!$("#starforce").val()) {// 목표 스타포스를 입력하지 않으면 수행
        alert("목표 스타포스를 입력하세요");
        $("#starforce").focus();
        status = false;
        return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    }
    
    var query = {
    		numberOfItems:$("#numberOfItems").val(),
			level:$("#level").val(),
			numberOfRealItems:$("#numberOfRealItems").val(),
			fromStarforce:$("#fromStarforce").val(),
			starforce:$("#starforce").val(),
			succededCatch:$("input[id=succededCatch]:checked").val()
	};
	$.ajax({
		type:"post",
		url:"checkProperty_superior.jsp",
		data:query,
		success:function(data){
			if(data == false){
				alert("오류가 발생했습니다. 입력정보를 다시 확인하세요");
				status = false;
				return false;
			} 
		}
	});
}
