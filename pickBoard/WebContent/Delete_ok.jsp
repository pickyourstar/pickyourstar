<%@page import="pick.util.DBConn"%>
<%@page import="pick.board.BoardDAO"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	//Delete_ok.jsp
	
	int num = Integer.parseInt(request.getParameter("num"));
	String pageNum = request.getParameter("pageNum");
	
	Connection conn = DBConn.getConnection();
	BoardDAO dao = new BoardDAO(conn);
	
	int result = dao.deleteData(num);
	
	//result 결과값에 따른 분기 처리 가능~!!!
	
	DBConn.close();
	
	response.sendRedirect(cp + "/List.jsp?pageNum=" + pageNum);


%>