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
	
	
	//������ ����
	public BoardDAO(Connection conn)
	{
		this.conn = conn;
		
	}
	
	
	//�Խù� ��ȣ�� �ִ밪 ����
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
	
	
	//�Խù� �ۼ� -> ������ �Է�
	public int insertData(BoardDTO dto) //throws SQLException
	{
		int result = 0;
		
		//hitCount�� �⺻�� '0' �Ǵ� 'default' �Ǵ� '�Է��׸� ����' ����
		//created �� �⺻�� sysdate �Ǵ� default �Ǵ� �Է��׸� ���� ����
		
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
	
	
	
	//DB ���ڵ��� ������ �������� �޼ҵ� ���� (������ ��ü~!!!)
	//-> �˻� ����� �۾��ϰ� �Ǹ�... �����ϰ� �� �޼ҵ� (�˻� ���~!!!)
	//����¡ ó��
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
	//�˻� ����� �߰�~!!!  ����,�ۼ���,����   �Է°�(ex.����,������,�౸)
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
			//�ܾ� ���Ե� �κ��� ��ġ�ϰڴ�~~~ like ���� ���
			
			//����Ŭ ������ ������
			//subject �� ���� �κ� ����
			sql = "SELECT COUNT(*) AS COUNT"
				+ " FROM TBL_TEMP"
				+ " WHERE " + searchKey + " LIKE ?";
			
			//where �ڿ� like �տ� �� �� ĭ�� ����α�
			
			
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
	
	
	
	
	//Ư�� ������(���۹�ȣ ~ ����ȣ) �Խù��� �����
	//�о���� �޼ҵ� ����
	//-> �˻� ����� �۾��ϰ� �Ǹ�... �����ϰ� �� �޼ҵ� (�˻� ���~!!!)
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
				
				
				//�ڡڡ�
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
			//��ó�� ���ֱ� üũ
			searchValue = "%" + searchValue + "%";  //�߰�����
			
			sql =  "SELECT STAR_NUMBER, STAR_TITLE, WRITER, STAR_COUNT, STAR_WRITE";
			sql += " FROM";
			sql += " (";
			sql += "    SELECT ROWNUM RNUM, DATA.*";
			sql += "    FROM";
			sql += "    (";
			sql += "        SELECT STAR_NUMBER, STAR_TITLE, WRITER, STAR_COUNT, TO_CHAR(STAR_WRITE,'YYYY-MM-DD') AS STAR_WRITE";
			sql += "        FROM TBL_TEMP";
			sql += "        WHERE " + searchKey + " LIKE ?";  //�߰�����
			sql += "        ORDER BY STAR_NUMBER DESC";
			sql += "    ) DATA";
			sql += " )";
			sql += " WHERE RNUM >= ? AND RNUM <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			//
			pstmt.setString(1, searchValue);   //�߰� ����
			
			//
			pstmt.setInt(2, start); //�ε��� ����
			pstmt.setInt(3, end);  //�ε��� ����
			
			// ? 3�� �߰�
			
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
	
	
	
	
	//Ư�� �Խù� ��ȸ�� ���� ��ȸ Ƚ�� ���� �޼ҵ� ����
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
	
	
	
	
	
	//Ư�� �Խù��� ������ �о���� �޼ҵ� ����
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
	
	
	
	
	//Ư�� �Խù��� �����ϴ� ����� �޼ҵ�
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
	
	
	//Ư�� �Խù��� ������ �����ϴ� �޼ҵ�
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
	
	
	//Ư�� �Խù��� ���� �Խù� ��ȣ ���� �޼ҵ� ����
	//���� �Խù��� �������� ���� ��� -1 ��ȯ
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
	
	
	//Ư�� �Խù��� ���� �Խù� ��ȣ ���� �޼ҵ� ����
	//���� �Խù��� �������� ���� ��� -1 ��ȯ
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
