$(document).ready(function(){

	// numberOfItems.jsp 에서 확인 버튼을 눌렀을 때
	$("#firstSubmit").click(function(){
		if($("input:radio[id=normalitem]").is(":checked")){
			location.href("numberOfItems_normal.jsp");
		} else {
			location.href("numberOfItems_superior.jsp");
		}
	});
	
	// nuberOfItems_normal.jsp 에서 토드 여부를 확인하고 토드에 관한 사항들을 보여줌
	$("input[id=toad]").click(function(){
		if($("input[id=toad]:checked").val()=="true"){ // 토드O 인 경우
			$("#selectToadProperty").show();
		} else if($("input[id=toad]:checked").val()=="false"){ // 토드X 인 경우
			$("#selectToadProperty").hide();
		}
	});
	
	// nuberOfItems_normal.jsp 에서 이전버튼을 눌렀을 때
	$("#privious").click(function(){
		location.href("numberOfItems.jsp");
	});
});