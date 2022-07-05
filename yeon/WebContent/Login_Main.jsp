<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login_Main</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
<!-- jquery -->
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">

$(function(){
	$("#logout").click(function(){
		window.open("Main.jsp");
		window.close();
		
	})
	});

	
</script>
</head>
<body>

<div class = "loginmaintext">
	쓰레기 하나를 주울 때 마다, 별 하나가 또렷해집니다.
	<h1>
		Pick Your Star
	</h1>
</div>

<div class="logout">

	<button id="logout" type="button">🌏지구로 떠나기</button>

</div>

<div>
	<ul>
		<li>
			<a href="#" class="menu1"> Home </a>
		</li>
		|
		<li>
			<a href="#" class="menu1"> About </a>
		</li>
		|
		<li>
			<a href="#" class="menu1"> My Blog </a>
		</li>
		|
		<li>
			<a href="#" class="menu1"> Contact </a>
		</li>
		|
		<li>
			<a href="#" class="menu1"> ... </a>
		</li>
	</ul>
<hr />
</div>

</body>
</html>