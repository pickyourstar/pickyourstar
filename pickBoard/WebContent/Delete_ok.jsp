<%@page import="pick.util.DBConn"%>
<%@page import="pickBoard.BoardDAO"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	//Delete_ok.jsp
	
	int STAR_NUMBER = Integer.parseInt(request.getParameter("STAR_NUMBER"));
	String pageNum = request.getParameter("pageNum");
	
	Connection conn = DBConn.getConnection();
	BoardDAO dao = new BoardDAO(conn);
	
	int result = dao.deleteData(STAR_NUMBER);
	
	//result 결과값에 따른 분기 처리 가능~!!!
	
	DBConn.close();
	
	response.sendRedirect(cp + "/List.jsp?pageNum=" + pageNum);


%>