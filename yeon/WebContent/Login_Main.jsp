<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String email = request.getParameter("email");
String password = request.getParameter("password");

// 로그인 정보가 없으면 로그인 창으로 쫓아내는 코드
	if(session.getAttribute("email")==null)
	{
		response.sendRedirect("Logout.jsp");
	}

%>
<jsp:include page="./header.jsp" flush="true"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login_Main</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
<!-- jquery -->
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">

</script>
</head>
<body>



	<div class="api_api">
				천문현상
		<div>
		
				
		</div>
	</div>
	
	<div class="api_map">별 명당 지도</div>


</body>
</html>