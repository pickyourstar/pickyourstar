<%@page import="pick.board.BoardDTO"%>
<%@page import="pick.util.DBConn"%>
<%@page import="pick.board.BoardDAO"%>
<%@page import="java.sql.Connection"%>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<jsp:useBean id="dto" class="pick.board.BoardDTO" scope="page"></jsp:useBean>
<jsp:setProperty property="*" name="dto"/>

<%
	//Created_ok.jsp
	
	Connection conn = DBConn.getConnection();
 	BoardDAO dao = new BoardDAO(conn);

 	
 	//게시물 현재 상태의 최댓값 얻어오기
 	int maxNum = dao.getMaxNum();
 	
 	//게시물 번호 최대값에 1을 더해서 set 하는 과정 -> dto 에 추가
 	dto.setNum(maxNum+1);
 	
 	//IP Address 확인
 	//-> request.getRemoteAddr();  -> 클라이언트(브라우저)의 IP Address 확인 -> dto 에 추가
 	dto.setIpAddr(request.getRemoteAddr());
 	
 	dao.insertData(dto);
 	
  	DBConn.close();
  	
  	response.sendRedirect("List.jsp");
  	
  	//사진 이미지 전달 받는 부분 추가
  	String realFolder="";
  	String saveFolder = "bbsCreated";		//사진을 저장할 경로
  	String encType = "utf-8";				//변환형식
  	int maxSize=5*1024*1024;				//사진의 size
  			
  	ServletContext context = this.getServletContext();		//절대경로를 얻는다
  	realFolder = context.getRealPath(saveFolder);			//saveFolder의 절대경로를 얻음
  			
  	MultipartRequest multi = null;

  	//파일업로드를 직접적으로 담당		
  	multi = new MultipartRequest(request,realFolder,maxSize,encType,new DefaultFileRenamePolicy());

  	//form으로 전달받은 3가지를 가져온다
  	String fileName = multi.getFilesystemName("fileName");
  	String subject = multi.getParameter("subject");
  	String content = multi.getParameter("content");

  	/*  오류 나는 부분...?
    pickBoard.setSubject(subject);
  	pickBoard.setContent(content);
  	 */
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
 	
 	
%>