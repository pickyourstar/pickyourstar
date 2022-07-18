<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JoinForm.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<!-- jquery -->
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
$(document).ready(function()
		{
			//alert("확인");
			
			$("#birthday").datepicker(
			{
				dateFormat : "yy-mm-dd"
				, changeMonth : true
				, changeYear : true
			});
		});

$(function(){
	$("#join_btn").click(function(){
	
		
		$.ajax({
				type:"POST"
				, url:"LoginForm.jsp"
				, dataType:"html"
				, success:function(data)
				{
					console.log("success");
					console.log(data)
					let url = "LoginForm.jsp";
					console.log(url);
					location.replace(url);
					alert("회원가입 완료되었습니다. 로그인 창으로 이동합니다.");
		
				},
				error: function(request , status, error){
					alert("잘못된 접근입니다.");
				}
		});
	});
});	

</script>

</head>
<body>
<div class="join">
	<div class="image_join">
	</div>
	

	<form method="post" action="LoginForm.jsp">
	
					
		<div class="joinForm">
			<h1 class="join_h1">회원가입</h1>
			<label for="email">아이디(이메일) *</label><br />
	    	<input type="email" class="input_join" id="email" name="email" placeholder="이메일을 입력하세요.">
	    	
	    </div>
	    <br />
		<div class="joinForm">
	    	<label for="password">비밀번호 *</label><br />
	    	<input type="password" class="input_join" id="password" name="password" placeholder="비밀번호를 입력하세요."/>
	    	
	    </div>
	    <br />
		<div class="joinForm">
	    	<label>닉네임 *</label><br />
	    	<input type="text" class="input_join" id="nickname" name="nickname" placeholder="닉네임을 입력하세요."/>
	    	
	    </div>
	    <br />
		<div class="joinForm">
	    	<label>생년월일 *</label><br />
	    	<input type="text" class="input_join" id="birthday" name="birthday" placeholder="생년월일을 입력하세요."/>
	    	
	    </div>
		
		
	
		<div>
	
			<input type="submit" class="join_btn" id="join_btn" value="회원가입">
		</div>
	
		<br />
	</form>	

</div>	
</body>
</html>