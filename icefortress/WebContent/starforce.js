var status = true;

$(document).ready(function(){

	// normalStarforceForm.jsp 에서 시행버튼을 눌렀을 때
	$("#submit_normal").click(function(){
		checkNormalItem();
		
		if (status) {
			var query = {
					numberOfItems:$("#numberOfItems").val(),
					level:$("#level").val(),
					fromStarforce:$("#fromStarforce").val(),
					starforce:$("#starforce").val(),
					ignoreDestroy:$("#ignoreDestroy").val(),
					discountPCRoom:$("#discountPCRoom").val(),
					succededCatch:$("input[id=succededCatch]:checked").val(),
					mapleEvent:$("input[id=mapleEvent]:checked").val(),
					discountMVPGrade:$("input[id=discountMVPGrade]:checked").val(),
					toad:$("input[id=toad]:checked").val(),
					toadToStarforce:$("#toadToStarforce").val(),
					toadIgnoreDestroy:$("#toadIgnoreDestroy").val()
			};
			$.ajax({
				type:"post",
				url:"normalStarforcePro.jsp",
				data:query,
				success:function(data){
					$("#result_normal").html(data);
					$("#resultbox_normal").show();
				}
			});
		}
	});
	
	// normalStarforceForm_cumulativeResult.jsp 에서 시행버튼을 눌렀을 때
	$("#search_normal").click(function(){
		// 데이터베이스에 이미 저장된 데이터를 불러오는 것이기 때문에 굳이 체크 안해도 됨
		
		var query = {
				numberOfItems:$("#numberOfItems").val(),
				level:$("#level").val(),
				fromStarforce:$("#fromStarforce").val(),
				starforce:$("#starforce").val(),
				ignoreDestroy:$("#ignoreDestroy").val(),
				discountPCRoom:$("#discountPCRoom").val(),
				succededCatch:$("input[id=succededCatch]:checked").val(),
				mapleEvent:$("input[id=mapleEvent]:checked").val(),
				discountMVPGrade:$("input[id=discountMVPGrade]:checked").val(),
				toad:$("input[id=toad]:checked").val(),
				toadToStarforce:$("#toadToStarforce").val(),
				toadIgnoreDestroy:$("#toadIgnoreDestroy").val()
		};
		$.ajax({
			type:"post",
			url:"normalStarforcePro_cumulativeResult.jsp",
			data:query,
			success:function(data){
				$("#result_normal").html(data);
				$("#resultbox_normal").show();
			}
		});
	});
	
	// normalStarforceForm.jsp, normalStarforceForm_cumulativeResult.jsp 에서의 작동
	// normalStarforceForm.jsp, normalStarforceForm_cumulativeResult.jsp 에서 토드 여부를 확인하고 토드에 관한 사항들을 보여줌
	$("input[id=toad]").click(function(){
		if($("input[id=toad]:checked").val()=="true"){ // 토드O 인 경우
			if($("#level").val()>160){ // 토드가 안되는 레벨에서 체크한 경우
				alert("토드는 160제 이하에서 가능합니다");
				$("#level").focus();
			} else if ($("#level").val()<20) { // 아이템 레벨의 입력이 안되어 있거나 비정상적인 입력을 했을 경우
				alert("아이템 레벨부터 입력해주시기 바랍니다.");
				$("#level").focus();
			} else {
				$("#selectToadProperty").show();
			}
		} else if($("input[id=toad]:checked").val()=="false"){ // 토드X 인 경우
			$("#selectToadProperty").hide();
		}
	});
	
	// superiorStarforceForm.jsp 에서 시행버튼을 눌렀을 때
	$("#submit_superior").click(function(){
		checkSuperiorItem();
		
		if (status) {
			var query = {
					numberOfItems:$("#numberOfItems").val(),
					level:$("#level").val(),
					fromStarforce:$("#fromStarforce").val(),
					starforce:$("#starforce").val(),
					succededCatch:$("input[id=succededCatch]:checked").val()
			};
			$.ajax({
				type:"post",
				url:"superiorStarforcePro.jsp",
				data:query,
				success:function(data){
					$("#result_superior").html(data);
					$("#resultbox_superior").show();
				}
			});
		}
	});
	
	// superiorStarforceForm_cumulativeResult.jsp 에서 시행버튼을 눌렀을 때
	$("#search_superior").click(function(){
		
		var query = {
				level:$("#level").val(),
				fromStarforce:$("#fromStarforce").val(),
				starforce:$("#starforce").val(),
				succededCatch:$("input[id=succededCatch]:checked").val()
		};
		$.ajax({
			type:"post",
			url:"superiorStarforcePro_cumulativeResult.jsp",
			data:query,
			success:function(data){
				$("#result_superior").html(data);
				$("#resultbox_superior").show();
			}
		});
	});
	
});

function checkNormalItem() {
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
    
    if($("input[id=toad]:checked").val()=="true"){
    	if(!$("#toadToStarforce").val()) {// 토드템 목표 스타포스를 입력하지 않으면 수행
   	    	alert("토드템 목표 스타포스를 입력하세요");
        	$("#toadToStarforce").focus();
        	status = false;
        	return false; // 사용자가 서비스를 요청한 시점으로 돌아감
    	}
    }
    
    var query = {
    		numberOfItems:$("#numberOfItems").val(),
			level:$("#level").val(),
			fromStarforce:$("#fromStarforce").val(),
			starforce:$("#starforce").val(),
			toad:$("input[id=toad]:checked").val(),
			toadToStarforce:$("#toadToStarforce").val()
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
			fromStarforce:$("#fromStarforce").val(),
			starforce:$("#starforce").val()
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