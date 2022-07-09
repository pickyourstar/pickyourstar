package com.test;

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
			
			sql = "SELECT NVL(MAX(NUM), 0) AS MAXNUM FROM TBL_BOARD";
		
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
			
			sql = "INSERT INTO TBL_BOARD(NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, CREATED)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, 0, SYSDATE)";
		
	
	    pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, dto.getNum());
		pstmt.setString(2, dto.getName());
		pstmt.setString(3, dto.getPwd());
		pstmt.setString(4, dto.getEmail());
		pstmt.setString(5, dto.getSubject());
		pstmt.setString(6, dto.getContent());
		pstmt.setString(7, dto.getIpAddr());
		//pstmt.setInt(2, dto.getHitCount());
		
		//pstmt.setString(2, dto.getCreated());
		
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
				+ " FROM TBL_BOARD"
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
			
			sql =  "SELECT NUM, NAME, SUBJECT, HITCOUNT, CREATED";
			sql += " FROM";
			sql += " (";
			sql += "    SELECT ROWNUM RNUM, DATA.*";
			sql += "    FROM";
			sql += "    (";
			sql += "        SELECT NUM, NAME, SUBJECT, HITCOUNT, TO_CHAR(CREATED,'YYYY-MM-DD') AS CREATED";
			sql += "        FROM TBL_BOARD";
			sql += "        WHERE " + searchKey + " LIKE ?";  //�߰�����
			sql += "        ORDER BY NUM DESC";
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
			sql = "UPDATE TBL_BOARD SET HITCOUNT = HITCOUNT + 1 WHERE NUM=?";
			
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
			
			
			sql = "SELECT NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT,"
					+ " TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED FROM TBL_BOARD WHERE NUM=?";	
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			//result = pstmt.executeQuery();
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				
				
				//BoardDTO dto = new BoardDTO();
				
				result = new BoardDTO();
				result.setNum(rs.getInt("NUM"));
				result.setName(rs.getString("NAME"));
				result.setPwd(rs.getString("PWD"));
				result.setEmail(rs.getString("EMAIL"));				
				result.setSubject(rs.getString("SUBJECT"));
				result.setContent(rs.getString("CONTENT"));
				result.setIpAddr(rs.getString("IPADDR"));
				result.setHitCount(rs.getInt("HITCOUNT"));
				result.setCreated(rs.getString("CREATED"));
				
				/*
				 * pstmt.setInt(1, rs.getInt("NUM")); pstmt.setString(2, rs.getString("NAME"));
				 * pstmt.setString(3, rs.getString("PWD")); pstmt.setString(4,
				 * rs.getString("EMAIL")); pstmt.setString(5, rs.getString("SUBJECT"));
				 * pstmt.setString(6, rs.getString("CONTENT")); pstmt.setString(7,
				 * rs.getString("IPADDR")); pstmt.setInt(8, rs.getInt("HITCOUNT"));
				 */
				
				//result.add();
				//���� dto �� ���� ���� �ʿ䰡 ����...
			
			
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
			sql = "DELETE FROM TBL_BOARD WHERE NUM=?";
			
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
			sql = "UPDATE TBL_BOARD SET NAME=?, PWD=?, EMAIL=?, SUBJECT=?, CONTENT=? WHERE NUM=?";
			
			pstmt = conn.prepareStatement(sql);
			
			//
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getSubject());
			pstmt.setString(5, dto.getContent());
			pstmt.setInt(6, dto.getNum());
			
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

			sql = "SELECT NVL(MAX(NUM), -1) AS BEFORENUM FROM TBL_BOARD WHERE NUM<?";
			
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

			sql = "SELECT NVL(MIN(NUM), -1) NEXTNUM FROM TBL_BOARD WHERE NUM>?";
			
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
