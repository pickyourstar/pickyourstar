package pickBoard;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO
{
	private Connection conn;
	
	
	//생성자 정의
	public BoardDAO(Connection conn)
	{
		this.conn = conn;
		
	}
	
	
	//게시물 번호의 최대값 얻어내기
	public int getMaxNum() //throws SQLException
	{
		int result = 0;
		
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try
		{
			
			sql = "SELECT NVL(MAX(STAR_NUMBER), 0) AS MAXNUM FROM TBL_TEMP";
		
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
		
			if(rs.next())
				result = rs.getInt(1);
				//result = rs.getInt("MAXNUM");
				
			rs.close();
			stmt.close();

			
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
	
		
		
		
		return result;
	}
	
	
	//게시물 작성 -> 데이터 입력
	public int insertData(BoardDTO dto) //throws SQLException
	{
		int result = 0;
		
		//hitCount는 기본값 '0' 또는 'default' 또는 '입력항목 생략' 가능
		//created 는 기본값 sysdate 또는 default 또는 입력항목 생략 가능
		
		PreparedStatement pstmt = null;
		
		String sql = "";
		
		try
		{
			
			sql = "INSERT INTO TBL_TEMP(STAR_NUMBER, STAR_TITLE, STAR_CONTENT, STAR_IMAGE, STAR_COUNT, STAR_REPLY, STAR_WRITE, STAR_MODIFY, STAR_DELETE, WRITER)" 
					+ "VALUES(?, ?, ?, ?, 0, 0, SYSDATE, ?, ?,?)";
		
	
	    pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, dto.getSTAR_NUMBER());
		pstmt.setString(2, dto.getSTAR_TITLE());
		pstmt.setString(3, dto.getSTAR_CONTENT());
		pstmt.setString(4, dto.getSTAR_IMAGE());
		pstmt.setString(5, dto.getSTAR_MODIFY());
		pstmt.setString(6, dto.getSTAR_DELETE());
		pstmt.setString(7, dto.getWRITER());

		
		result = pstmt.executeUpdate();
		pstmt.close();
		
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
		
		
		
	}
	
	
	
	//DB 레코드의 갯수를 가져오는 메소드 정의 (지금은 전체~!!!)
	//-> 검색 기능을 작업하게 되면... 수정하게 될 메소드 (검색 대상~!!!)
	//페이징 처리
	/*public int getDataCount()
	{
		int result = 0;
		
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try
		{
			sql = "SELECT COUNT(*) AS COUNT FROM TBL_BOARD";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				result = rs.getInt(1);
			rs.close();
			stmt.close();
			
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return result;
		
	}*/
	
	//CHECK~!!!
	//검색 기능을 추가~!!!  제목,작성자,내용   입력값(ex.어제,최현지,축구)
	public int getDataCount(String searchKey, String searchValue)
	{
		int result = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try
		{
			//check
			searchValue = "%" + searchValue + "%";
			//단어 포함된 부분을 서치하겠다~~~ like 구문 사용
			
			//오라클 쿼리문 가져옴
			//subject 와 음식 부분 수정
			sql = "SELECT COUNT(*) AS COUNT"
				+ " FROM TBL_TEMP"
				+ " WHERE " + searchKey + " LIKE ?";
			
			//where 뒤와 like 앞에 꼭 한 칸씩 비워두기
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchValue);
			
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt("COUNT");
			
			rs.close();
			pstmt.close();
			
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return result;
		
	}
	
	
	
	
	//특정 영역의(시작번호 ~ 끝번호) 게시물의 목록을
	//읽어오는 메소드 정의
	//-> 검색 기능을 작업하게 되면... 수정하게 될 메소드 (검색 대상~!!!)
	/*
	public List<BoardDTO> getLists(int start, int end)
	{
		
		
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		
		//PreparedStatement pstmt = conn.prepareStatement(sql);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try
		{
			sql =  "SELECT NUM, NAME, SUBJECT, HITCOUNT, CREATED";
			sql += " FROM";
			sql += " (";
			sql += "    SELECT ROWNUM RNUM, DATA.*";
			sql += "    FROM";
			sql += "    (";
			sql += "        SELECT NUM, NAME, SUBJECT, HITCOUNT, TO_CHAR(CREATED,'YYYY-MM-DD') AS CREATED";
			sql += "        FROM TBL_BOARD";
			sql += "        ORDER BY NUM DESC";
			sql += "    ) DATA";
			sql += " )";
			sql += " WHERE RNUM >= ? AND RNUM <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			
			
			//
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next())
			{
				
				//
				BoardDTO dto = new BoardDTO();
				
				//pstmt.setInt(1, rs.getNum());
				//pstmt.setString(2, rs.getName());
				//pstmt.setString(3, rs.getSubject());
				//pstmt.setInt(4, rs.getHitcount());
				//pstmt.setString(5, rs.getCreated());
				
				
				//★★★
				dto.setNum(rs.getInt("NUM"));
				dto.setName(rs.getString("NAME"));
				dto.setSubject(rs.getString("SUBJECT"));
				dto.setHitCount(rs.getInt("HITCOUNT"));
				dto.setCreated(rs.getString("CREATED"));
				
				result.add(dto);
			
			
			}
			rs.close();
			pstmt.close();
			
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		
		return result;
		
	}*/
	
	public List<BoardDTO> getLists(int start, int end, String searchKey, String searchValue)
	{
		
		
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		
		//PreparedStatement pstmt = conn.prepareStatement(sql);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try
		{
			//전처리 해주기 체크
			searchValue = "%" + searchValue + "%";  //추가구문
			
			sql =  "SELECT STAR_NUMBER, STAR_TITLE, WRITER, STAR_COUNT, STAR_WRITE";
			sql += " FROM";
			sql += " (";
			sql += "    SELECT ROWNUM RNUM, DATA.*";
			sql += "    FROM";
			sql += "    (";
			sql += "        SELECT STAR_NUMBER, STAR_TITLE, WRITER, STAR_COUNT, TO_CHAR(STAR_WRITE,'YYYY-MM-DD') AS STAR_WRITE";
			sql += "        FROM TBL_TEMP";
			sql += "        WHERE " + searchKey + " LIKE ?";  //추가구문
			sql += "        ORDER BY STAR_NUMBER DESC";
			sql += "    ) DATA";
			sql += " )";
			sql += " WHERE RNUM >= ? AND RNUM <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			//
			pstmt.setString(1, searchValue);   //추가 구문
			
			//
			pstmt.setInt(2, start); //인덱스 변경
			pstmt.setInt(3, end);  //인덱스 변경
			
			// ? 3개 추가
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next())
			{
	
				BoardDTO dto = new BoardDTO();
				
				
				dto.setSTAR_NUMBER(rs.getInt("STAR_NUMBER"));
				dto.setSTAR_TITLE(rs.getString("STAR_TITLE"));
				dto.setWRITER(rs.getString("WRITER"));
				dto.setSTAR_COUNT(rs.getInt("STAR_COUNT"));
				dto.setSTAR_WRITE(rs.getString("STAR_WRITE"));
				
				result.add(dto);
			
			
			}
			rs.close();
			pstmt.close();
			
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		
		return result;
		
	}
	
	
	
	
	//특정 게시물 조회에 따른 조회 횟수 증가 메소드 정의
	public int updateHitCount(int num)
	{
		int result = 0;
		
		
		//PreparedStatement pstmt = conn.prepareStatement();
		
		PreparedStatement pstmt = null;
		
		String sql = "";
		
		try
		{
			sql = "UPDATE TBL_TEMP SET STAR_COUNT = STAR_COUNT + 1 WHERE STAR_NUMBER=?";
			
			//
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
	
		
		return result; 
	}
	
	
	
	
	
	//특정 게시물의 내용을 읽어오는 메소드 정의
	public BoardDTO getReadData(int num)
	{
		//BoardDTO result = null;
		
		BoardDTO result = new BoardDTO();
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		
		try
		{
			
			
			sql = "SELECT STAR_NUMBER, WRITER, STAR_TITLE, STAR_CONTENT, STAR_COUNT,"
					+ " TO_CHAR(STAR_WRITE, 'YYYY-MM-DD') AS STAR_WRITE FROM TBL_TEMP WHERE STAR_NUMBER=?";	
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			//result = pstmt.executeQuery();
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				
				
				//BoardDTO dto = new BoardDTO();
				
				result = new BoardDTO();
				result.setSTAR_NUMBER(rs.getInt("STAR_NUMBER"));
				result.setWRITER(rs.getString("WRITER"));			
				result.setSTAR_TITLE(rs.getString("STAR_TITLE"));
				result.setSTAR_CONTENT(rs.getString("STAR_CONTENT"));
				result.setSTAR_COUNT(rs.getInt("STAR_COUNT"));
				result.setSTAR_WRITE(rs.getString("STAR_WRITE"));
			
			}
			
			
			rs.close();
			pstmt.close();

			
			
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}

		return result;
		
		
	}
	
	
	
	
	//특정 게시물을 삭제하는 기능의 메소드
	public int deleteData(int num)
	{
		
		int result = 0;
		

		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		
		String sql = "";
		
		
		try
		{
			sql = "DELETE FROM TBL_TEMP WHERE STAR_NUMBER=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			
			//
			pstmt.close();
		
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		

		
		return result;
		
	}
	
	
	//특정 게시물의 내용을 수정하는 메소드
	public int updateData(BoardDTO dto)
	{
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = "";
		
		
		try
		{
			sql = "UPDATE TBL_TEMP SET WRITER=?, STAR_TITLE=?, STAR_CONTENT=? WHERE STAR_NUMBER=?";
			
			pstmt = conn.prepareStatement(sql);
			
			//
			pstmt.setString(1, dto.getWRITER());
			pstmt.setString(2, dto.getSTAR_TITLE());
			pstmt.setString(3, dto.getSTAR_CONTENT());
			pstmt.setInt(4, dto.getSTAR_NUMBER());
			
		    result = pstmt.executeUpdate();
		    
		    pstmt.close();
			
		}catch(Exception e)
		{
			
			System.out.println(e.toString());
		}
		
		return result;
		
		
		
		
		
	}
	
	
	//특정 게시물의 이전 게시물 번호 얻어내는 메소드 정의
	//이전 게시물이 존재하지 않을 경우 -1 반환
	public int getBeforeNum(int num)
	{
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		String sql = "";
		
		
		try
		{

			sql = "SELECT NVL(MAX(STAR_NUMBER), -1) AS BEFORENUM FROM TBL_TEMP WHERE STAR_NUMBER<?";
			
			//
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			//
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				result = rs.getInt("BEFORENUM");
			}
		
			
			rs.close();
			pstmt.close();
			
		}catch(Exception e)
		{
			
			System.out.println(e.toString());
		}
		
		return result;
		
		
	}
	
	
	//특정 게시물의 다음 게시물 번호 얻어내는 메소드 정의
	//다음 게시물이 존재하지 않을 경우 -1 반환
	public int getNextNum(int num)
	{
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		String sql = "";
		
		
		try
		{

			sql = "SELECT NVL(MIN(STAR_NUMBER), -1) NEXTNUM FROM TBL_TEMP WHERE STAR_NUMBER>?";
			
			//
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			//
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				result = rs.getInt("NEXTNUM");
				
			}
		
			
			rs.close();
			pstmt.close();
			
		}catch(Exception e)
		{
			
			System.out.println(e.toString());
		}
		
		return result;
		
		
	}
	
	
	
	
	
	
	
}
