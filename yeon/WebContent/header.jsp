<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	
	// 로그인 정보가 없으면 로그인 창으로 쫓아내는 코드
	if(session.getAttribute("email")==null)
	{
		response.sendRedirect("Logout.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PickYourStar</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
<!-- jquery -->
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">

$(function(){
	$("#MyBlog").click(function(){
		$.ajax({
				type:"POST"
				, url:"MypageOk.jsp"
				, dataType:"html"
				, success:function(data)
				{
					
					console.log("success");
					console.log(data)
					let url = "MypageOk.jsp";
					console.log(url);
					location.replace(url);
				},
				error: function(request , status, error){
					alert("잘못된 접근입니다.");
				}
		});
	});
});

$(function(){
	$("#Home").click(function(){
		$.ajax({
				type:"POST"
				, url:"Login_Main.jsp"
				, dataType:"html"
				, success:function(data)
				{
					
					console.log("success");
					console.log(data)
					let url = "Login_Main.jsp";
					console.log(url);
					location.replace(url);
				},
				error: function(request , status, error){
					alert("잘못된 접근입니다.");
				}
		});
	});
});
$(function(){
	$("#About").click(function(){
		$.ajax({
				type:"POST"
				, url:"About.jsp"
				, dataType:"html"
				, success:function(data)
				{
					
					console.log("success");
					console.log(data)
					let url = "About.jsp";
					console.log(url);
					location.replace(url);
				},
				error: function(request , status, error){
					alert("잘못된 접근입니다.");
				}
		});
	});
});
$(function(){
	$("#Contact").click(function(){
		$.ajax({
				type:"POST"
				, url:"Login_Contact.jsp"
				, dataType:"html"
				, success:function(data)
				{
					
					console.log("success");
					console.log(data)
					let url = "Login_Contact.jsp";
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
<header>
<div class = "loginmaintext">
	쓰레기 하나를 주울 때 마다, 별 하나가 또렷해집니다.
	<h1>
		Pick Your Star
	</h1>
</div>


<div class="logout">
	<%
		if(session.getAttribute("email")!=null){
			String email = (String)session.getAttribute("email");
			out.println(email+"님 반갑습니다.<br>");
			out.println("<a href='Logout.jsp'>🌏지구로 떠나기</a>");
		}
	
	%>


</div>

<div class="menuall">
	<ul>
		<li>
			<a href="javascript:;" class="menu1" id="Home"> Home </a>
		</li>
		|
		<li>
			<a href="javascript:;" class="menu1" id="About"> About </a>
		</li>
		|
		<li>
			<a href="javascript:;" class="menu1" id="MyBlog"> My Blog </a>
		</li>
		|
		<li>
			<a href="javascript:;" class="menu1" id="Contact"> Contact </a>
		</li>
		|
		<li>
			<a href="javascript:;" class="menu1"> ... </a>
		</li>
	</ul>
<hr />
</div>
</header>

	

</body>
</html>