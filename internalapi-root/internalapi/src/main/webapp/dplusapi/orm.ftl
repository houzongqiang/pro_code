<!DOCTYPE HTML>
<html>
<head>
<title>Docker Registry Manager.</title>
<script src="${request.contextPath}/js/jquery/jquery-1.11.1.min.js"></script>
<script>
function submitTest(){
	var code = $("#code").val();
	var name = $("#name").val();
	if($.trim(name) == "" || $.trim(code) == ""){
		alert("数据不能为空！");
		return;
	}
	
	$.post("orm_save",$("#f").serialize(),function(data){
		if(data.respCode == 0){
			alert(data.respMsg);
			window.location.reload(true);
		}else{
			alert("操作失败，请稍后再试");
		}
	})
}

function getDateByJsonp(){

	var userCode = $("#userCode").val();
	
	if($.trim(userCode) == ""){
		alert("数据不能为空");
		return;
	}
	
	$.getJSON("find_one_by_user_code_jsonp/"+userCode + "?callback=?",function(data){
		if(data.respCode == 0){
			alert(data);
		}
	})
	
}
</script>

</head>
<body>
	<div>
		<span>这是一个页面！</span>
	</div>

	<div>
		<form id="f">
			<span>名称:</span>
			<input type="text" name="userCode" id="code" placeholder="code">
			<input type="text" name="userName" id="name" placeholder="名字">
			<input type="button" value="提交" onclick="submitTest();" >
		</form>	
	</div>
	<br />
	<div>
		<input type="text" id="userCode" placeholder="code">
		<input type="button" value="jsonp获取数据" onclick="getDateByJsonp();" >
	</div>
</body>
</html>