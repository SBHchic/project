package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardQnADBBean {
	
	// BoardQnADBBean ���� ��ü ���� <- �Ѱ��� ������ �����ؼ� ����
    private static BoardQnADBBean instance = new BoardQnADBBean();
    
    // BoardQnADBBean��ü�� �����ϴ� �޼���
    public static BoardQnADBBean getInstance() {
        return instance;
    }
    
    private BoardQnADBBean() {}
    
    // Ŀ�ؼ� Ǯ���� Ŀ�ؼ� ��ü�� ���� �޼���
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/icefortress");
        return ds.getConnection();
    }
    
    public String getDate() { // ����ð��� �������� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	conn = getConnection();
        	pstmt = conn.prepareStatement("select now()");
        	rs = pstmt.executeQuery();
        	if (rs.next()) {
        		return rs.getString(1);
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return ""; // �����ͺ��̽� ����
    }
    
    public int getNext() { // �Խñ� ��ȣ�� �Ű��� (boardQnA_ID)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select boardQnA_ID from boardQnA order by boardQnA_ID desc");
    		rs = pstmt.executeQuery();
    		if (rs.next()) {
    			return rs.getInt(1) + 1;
    		}
    		return 1; // ù ��° �Խñ��� ���
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return -1; // �����ͺ��̽� ����
    }
    
    public int write(String boardQnA_Title, String userID, String boardQnA_Content) { // �� �ۼ� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into boardQnA values (?, 0, 0, 0, ?, ?, ?, ?)");
        	pstmt.setInt(1, getNext());
        	pstmt.setString(2, boardQnA_Title);
        	pstmt.setString(3, userID);
        	pstmt.setString(4, getDate());
        	pstmt.setString(5, boardQnA_Content);
        	pstmt.executeUpdate();
        	return 1; // �ۼ� ����
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // �����ͺ��̽� ����
    }
    
    // �Ϲ� ����� ��� ���α۰� ����� ����Ʈ�� ���� (������ ����)
    public ArrayList<BoardQnADataBean> getList_Member(String userID) { 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BoardQnADataBean> list = new ArrayList<BoardQnADataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from boardQnA where userID = ? and boardQnA_CommentID = 0 order by boardQnA_ID desc";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setString(1, userID);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			Connection conn2 = null;
    	        PreparedStatement pstmt2 = null;
    	        ResultSet rs2 = null;
    	        try {
    	        	conn2 = getConnection();
    	        	String SQL2 = "select * from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID >= 0 and boardQnA_CommentID = 0 order by boardQnA_ReplyID asc";
    	        	pstmt2 = conn2.prepareStatement(SQL2);
    	        	pstmt2.setInt(1, rs.getInt("boardQnA_ID"));
    	        	rs2 = pstmt.executeQuery();
    	        	while (rs2.next()) {
    	        		BoardQnADataBean tmp = new BoardQnADataBean();
    	        		tmp.setBoardQnA_ID(rs2.getInt(1));
    	        		tmp.setBoardQnA_ReplyID(rs2.getInt(2));
    	        		tmp.setBoardQnA_CommentID(rs2.getInt(3));
    	        		tmp.setBoardQnA_CommentID_Re(rs.getInt(4));
    	        		tmp.setBoardQnA_Title(rs2.getString(5));
    	        		tmp.setUserID(rs2.getString(6));
    	        		tmp.setBoardQnA_Reg_Date(rs2.getString(7));
    	        		tmp.setBoardQnA_Content(rs2.getString(8));
    	        		list.add(tmp);
    	        	}
    	        	
    	        } catch(Exception e) {
    	        	e.printStackTrace();
    	        } finally {
    	    		if (rs2 != null) try { rs2.close(); } catch(SQLException sqle) {}
    	    		if (pstmt2 != null) try { pstmt2.close(); } catch(SQLException sqle) {}
    	            if (conn2 != null) try { conn2.close(); } catch(SQLException sqle) {}
    	    	}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return list; 
    }
    
    // ����� ��� ��� ���� ����Ʈ�� ����
    public ArrayList<BoardQnADataBean> getList_Submaster() { 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BoardQnADataBean> list = new ArrayList<BoardQnADataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from boardQnA where boardQnA_CommentID = 0 order by boardQnA_ID desc, boardQnA_ReplyID asc";
    		pstmt = conn.prepareStatement(SQL);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			BoardQnADataBean tmp = new BoardQnADataBean();
        		tmp.setBoardQnA_ID(rs.getInt(1));
        		tmp.setBoardQnA_ReplyID(rs.getInt(2));
        		tmp.setBoardQnA_CommentID(rs.getInt(3));
        		tmp.setBoardQnA_CommentID_Re(rs.getInt(4));
        		tmp.setBoardQnA_Title(rs.getString(5));
        		tmp.setUserID(rs.getString(6));
        		tmp.setBoardQnA_Reg_Date(rs.getString(7));
        		tmp.setBoardQnA_Content(rs.getString(8));
        		list.add(tmp);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return list; 
    }
    
    // ������ �� ���� �������� ����Ʈ ���
    public ArrayList<BoardQnADataBean> getListView(ArrayList<BoardQnADataBean> list, int start, int pageSize) {
    	ArrayList<BoardQnADataBean> tmp = new ArrayList<BoardQnADataBean>(pageSize);
    	for (int i = start-1; i < start-1+pageSize; i++) {
    		if (i >= list.size()) {
    			break;
    		}
    		tmp.add(list.get(i));
    	}
    	return tmp;
    }
    
    // �� �������� �����͸� �������� �޼���
    public BoardQnADataBean viewWritten(int boardQnA_ID, int boardQnA_ReplyID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardQnADataBean written = null;
    	try {
    		conn = getConnection();
    		String SQL = "select * from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID = ? and boardQnA_CommentID = 0";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, boardQnA_ID);
    		pstmt.setInt(2, boardQnA_ReplyID);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			written = new BoardQnADataBean();
        		written.setBoardQnA_ID(rs.getInt(1));
        		written.setBoardQnA_ReplyID(rs.getInt(2));
        		written.setBoardQnA_CommentID(rs.getInt(3));
        		written.setBoardQnA_CommentID_Re(rs.getInt(4));
        		written.setBoardQnA_Title(rs.getString(5));
        		written.setUserID(rs.getString(6));
        		written.setBoardQnA_Reg_Date(rs.getString(7));
        		written.setBoardQnA_Content(rs.getString(8));
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return written; 
    }
    
    // ���� ���� ���ٱ����� �ִ��� Ȯ��
    public int checkAccessRights(String userID, int boardQnA_ID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		String SQL = "select * from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID = 0 and boardQnA_CommentID = 0";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, boardQnA_ID);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			if (userID.equals(rs.getString("userID"))) {
    				return 1;
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return -1; // ���� ���� ����
    }
    
}
