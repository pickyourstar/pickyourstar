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
<title>LoginForm</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<!-- jquery -->
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>

<script type="text/javascript">


$(function(){
	$("#Join").click(function(){
		$.ajax({
				type:"POST"
				, url:"JoinForm.jsp"
				, dataType:"html"
				, success:function(data)
				{
					console.log("success");
					console.log(data)
					let url = "JoinForm.jsp";
					console.log(url);
					location.replace(url);
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
<div class="login">
	<div class="image_login">
	</div>
	

	<form id="loginForm" name="loginForm" method="post" action=submit class="loginForm">
	
					<h1 class="login_h1">로그인</h1>
		<div>
		
			<label for="email">아이디(이메일) *</label><br />
	    	<input type="email" class="input_login" id="email" name="email" placeholder="이메일을 입력하세요.">
	    	
	    </div>
	    <br />
		<div>
	    	<label for="password">비밀번호 *</label><br />
	    	<input type="password" class="input_login" id="password" name="password" placeholder="비밀번호를 입력하세요."/>
	    	
	    </div>
		
		
	</form>
		<div>
			<input type="submit" class="login_btn" id="Login" value="로그인"/>
			<input type="button" class="login_btn" id="Join" value="회원가입 하러 가기"/>
		</div>
		<br />
	

</div>	
</body>
</html>