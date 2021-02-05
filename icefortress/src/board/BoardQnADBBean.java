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
	
	// BoardQnADBBean 전역 객체 생성 <- 한개의 객제만 생성해서 공유
    private static BoardQnADBBean instance = new BoardQnADBBean();
    
    // BoardQnADBBean객체를 리턴하는 메서드
    public static BoardQnADBBean getInstance() {
        return instance;
    }
    
    private BoardQnADBBean() {}
    
    // 커넥션 풀에서 커넥션 객체를 얻어내는 메서드
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/icefortress");
        return ds.getConnection();
    }
    
    public String getDate() { // 현재시간을 가져오는 메서드
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
        return ""; // 데이터베이스 오류
    }
    
    public int getNext() { // 게시글 번호를 매겨줌 (boardQnA_ID)
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
    		return 1; // 첫 번째 게시글인 경우
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return -1; // 데이터베이스 오류
    }
    
    public int write(String boardQnA_Title, String userID, String boardQnA_Content) { // 글 작성 메서드
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
        	return 1; // 작성 성공
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // 데이터베이스 오류
    }
    
    // 일반 멤버인 경우 본인글과 답글을 리스트에 넣음 (보안을 위해)
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
    
    // 운영진인 경우 모든 글을 리스트에 넣음
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
    
    // 페이지 글 개수 제한으로 리스트 출력
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
    
    // 뷰 페이지에 데이터를 가져오는 메서드
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
    
    // 글을 읽을 접근권한이 있는지 확인
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
    	return -1; // 접근 권한 없음
    }
    
}
