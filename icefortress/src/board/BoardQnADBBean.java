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
    
    public int getNextWrittenID() { // �Խñ� ��ȣ�� �Ű��� (boardQnA_ID)
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
    
    public int getNextReplyWrittenID(int boardQnA_ID) { // ��� ��ȣ�� �Ű��� (boardQnA_ReplyID)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select boardQnA_ReplyID from boardQnA where boardQnA_ID = ? order by boardQnA_ReplyID desc");
    		pstmt.setInt(1, boardQnA_ID);
    		rs = pstmt.executeQuery();
    		if (rs.next()) {
    			return rs.getInt(1) + 1;
    		}
    		return 1; // ù ��° ����� ���
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return -1; // �����ͺ��̽� ����
    }
    
    public int getNextCommentWrite(int boardQnA_ID, int boardQnA_ReplyID) { // ��� ��ȣ�� �Ű��� (boardQnA_CommentID)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select boardQnA_CommentID from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID = ? order by boardQnA_CommentID desc");
    		pstmt.setInt(1, boardQnA_ID);
    		pstmt.setInt(2, boardQnA_ReplyID);
    		rs = pstmt.executeQuery();
    		if (rs.next()) {
    			return rs.getInt(1) + 1;
    		}
    		return 1; // ù ��° ����� ���
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return -1; // �����ͺ��̽� ����
    }
    
    public int getNextCommentReplyWrite(int boardQnA_ID, int boardQnA_ReplyID, int boardQnA_CommentID) { // ���� ��ȣ�� �Ű��� (boardQnA_CommentID_Re)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select boardQnA_CommentID_Re from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID = ? and boardQnA_CommentID = ?  order by boardQnA_CommentID_Re desc");
    		pstmt.setInt(1, boardQnA_ID);
    		pstmt.setInt(2, boardQnA_ReplyID);
    		pstmt.setInt(3, boardQnA_CommentID);
    		rs = pstmt.executeQuery();
    		if (rs.next()) {
    			return rs.getInt(1) + 1;
    		}
    		return 1; // ù ��° ������ ���
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return -1; // �����ͺ��̽� ����
    }
    
    public int write(String boardQnA_Title, String userID, String boardQnA_Content, byte notice) { // �� �ۼ� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into boardQnA values (?,0,0,0,?,?,?,?,1,?,?)");
        	pstmt.setInt(1, getNextWrittenID());
        	pstmt.setString(2, boardQnA_Title);
        	pstmt.setString(3, userID);
        	pstmt.setString(4, getDate());
        	pstmt.setString(5, boardQnA_Content);
        	pstmt.setString(6, "2000-01-01 00:00:00"); // �⺻�� ����
        	pstmt.setByte(7, notice);
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
    
    public int writeReply(String boardQnA_Title, String userID, String boardQnA_Content, int boardQnA_ID) { // ��� �ۼ� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into boardQnA values (?, ?, 0, 0, ?, ?, ?, ?, 1, ?, 0)");
        	pstmt.setInt(1, boardQnA_ID);
        	pstmt.setInt(2, getNextReplyWrittenID(boardQnA_ID));
        	pstmt.setString(3, boardQnA_Title);
        	pstmt.setString(4, userID);
        	pstmt.setString(5, getDate());
        	pstmt.setString(6, boardQnA_Content);
        	pstmt.setString(7, "2000-01-01 00:00:00"); // �⺻�� ����
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
    
    public int writeComment(String userID, String boardQnA_Content, int boardQnA_ID, int boardQnA_ReplyID) { // ��� �ۼ� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into boardQnA values (?, ?, ?, 0, ?, ?, ?, ?, 1, ?, 0)");
        	pstmt.setInt(1, boardQnA_ID);
        	pstmt.setInt(2, boardQnA_ReplyID);
        	pstmt.setInt(3, getNextCommentWrite(boardQnA_ID, boardQnA_ReplyID));
        	pstmt.setString(4, boardQnA_ID+","+boardQnA_ReplyID+"�� "+ getNextCommentWrite(boardQnA_ID, boardQnA_ReplyID) +"��° ���");
        	pstmt.setString(5, userID);
        	pstmt.setString(6, getDate());
        	pstmt.setString(7, boardQnA_Content);
        	pstmt.setString(8, "2000-01-01 00:00:00"); // �⺻�� ����
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
    
    public int writeCommentReply(String userID, String boardQnA_Content, int boardQnA_ID, int boardQnA_ReplyID, int boardQnA_CommentID) { // ���� �ۼ� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into boardQnA values (?, ?, ?, ?, ?, ?, ?, ?, 1, ?, 0)");
        	pstmt.setInt(1, boardQnA_ID);
        	pstmt.setInt(2, boardQnA_ReplyID);
        	pstmt.setInt(3, boardQnA_CommentID);
        	pstmt.setInt(4, getNextCommentReplyWrite(boardQnA_ID, boardQnA_ReplyID, boardQnA_CommentID));
        	pstmt.setString(5, boardQnA_ID+","+boardQnA_ReplyID+","+boardQnA_CommentID+"��° ����� "+ getNextCommentReplyWrite(boardQnA_ID, boardQnA_ReplyID, boardQnA_CommentID) +"��° ����");
        	pstmt.setString(6, userID);
        	pstmt.setString(7, getDate());
        	pstmt.setString(8, boardQnA_Content);
        	pstmt.setString(9, "2000-01-01 00:00:00"); // �⺻�� ����
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
    
    
    // ��, ����� �����ϴ� �޼���
    public int update(BoardQnADataBean update, String boardQnA_Title, String boardQnA_Content, byte notice) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update boardQnA set boardQnA_Title = ?, boardQnA_Content = ?, boardQnA_Reg_Date = ?, notice = ? where boardQnA_ID = ? and boardQnA_ReplyID = ? and boardQnA_CommentID = 0");
        	pstmt.setString(1, boardQnA_Title);
        	pstmt.setString(2, boardQnA_Content);
        	pstmt.setString(3, getDate());
        	pstmt.setByte(4, notice);
        	pstmt.setInt(5, update.getBoardQnA_ID());
        	pstmt.setInt(6, update.getBoardQnA_ReplyID());
        	pstmt.executeUpdate();
        	return 1; // ���� ����
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // �����ͺ��̽� ����
    }
    
    // ���, ������ �����ϴ� �޼���
    public int updateComment(BoardQnADataBean comment, String boardQnA_Content) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update boardQnA set boardQnA_Content = ?, boardQnA_Reg_Date = ? where boardQnA_ID = ? and boardQnA_ReplyID = ? and boardQnA_CommentID = ? and boardQnA_CommentID_Re = ?");
        	pstmt.setString(1, boardQnA_Content);
        	pstmt.setString(2, getDate());
        	pstmt.setInt(3, comment.getBoardQnA_ID());
        	pstmt.setInt(4, comment.getBoardQnA_ReplyID());
        	pstmt.setInt(5, comment.getBoardQnA_CommentID());
        	pstmt.setInt(6, comment.getBoardQnA_CommentID_Re());
        	pstmt.executeUpdate();
        	return 1; // ���� ����
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // �����ͺ��̽� ����
    }
    
    // ��, ����� �����ϴ� �޼���
    public int delete(int boardQnA_ID, int boardQnA_ReplyID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("select * from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID = ?"); // �� ���� ��۵� ���� �ҷ���
        	pstmt.setInt(1, boardQnA_ID);
        	pstmt.setInt(2, boardQnA_ReplyID);
        	rs = pstmt.executeQuery();
        	while(rs.next()) {
        		pstmt = conn.prepareStatement("update boardQnA set available = 0, boardQnA_DeleteReg_Date = ? where boardQnA_ID = ? and boardQnA_ReplyID = ? and boardQnA_CommentID = ? and boardQnA_CommentID_Re = ?");
        		pstmt.setString(1, getDate());
        		pstmt.setInt(2, rs.getInt("boardQnA_ID"));
        		pstmt.setInt(3, rs.getInt("boardQnA_ReplyID"));
        		pstmt.setInt(4, rs.getInt("boardQnA_CommentID")); // �� ���� ���
        		pstmt.setInt(5, rs.getInt("boardQnA_CommentID_Re")); // �� ���� ����
        		pstmt.executeUpdate(); // �� ���� ���, ���۵� ���� ������
        	}
        	return 1; // ���� ����
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // �����ͺ��̽� ����
    }
    
    // �ڸ�Ʈ�� �����ϴ� �޼���
    public int deleteComment(BoardQnADataBean comment) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update boardQnA set available = 0, boardQnA_DeleteReg_Date = ? where boardQnA_ID = ? and boardQnA_ReplyID = ? and boardQnA_CommentID = ? and boardQnA_CommentID_Re = ?"); 
        	pstmt.setString(1, getDate());
        	pstmt.setInt(2, comment.getBoardQnA_ID());
        	pstmt.setInt(3, comment.getBoardQnA_ReplyID());
        	pstmt.setInt(4, comment.getBoardQnA_CommentID());
        	pstmt.setInt(5, comment.getBoardQnA_CommentID_Re());
        	pstmt.executeUpdate();
        	return 1; // ���� ����
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // �����ͺ��̽� ����
    }
    
    // �Ϲ� ����� ��� ���α۰� ����� ����Ʈ�� ���� (������ ����) - getList_Submaster() �� ����
    public ArrayList<BoardQnADataBean> getList_Member(String userID, ArrayList<BoardQnADataBean> list) { 
        for (int i = list.size()-1; i >= 0; i--) {
        	if (!list.get(i).getUserID().equals(userID)) { // userID�� ����Ʈ�� userID�� �ٸ����
        		if (list.get(i).getBoardQnA_ReplyID() == 0) {
        			list.remove(i);
        		} else {
        			Connection conn = null;
        	        PreparedStatement pstmt = null;
        	        ResultSet rs = null;
        	    	try {
        	    		conn = getConnection();
        	    		String SQL = "select userID from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID = 0 and boardQnA_CommentID = 0";
        	    		pstmt = conn.prepareStatement(SQL);
        	    		pstmt.setInt(1, list.get(i).getBoardQnA_ID());
        	    		rs = pstmt.executeQuery();
        	    		if (rs.next()) {
        	    			if(!rs.getString(1).equals(userID)) {
        	    				list.remove(i);
        	    			}
        	    		}
        	    	} catch (Exception e) {
        	    		e.printStackTrace();
        	    	} finally {
        	    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
        	    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
        	            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        	    	}
        		}
        	}
        }
    	return list; 
    }
    
    // ����� ��� ��� ���� ����Ʈ�� ���� (���� ����)
    public ArrayList<BoardQnADataBean> getList_Submaster() { 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BoardQnADataBean> list = new ArrayList<BoardQnADataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from boardQnA where boardQnA_CommentID = 0 and available = 1 and notice = 0 order by boardQnA_ID desc, boardQnA_ReplyID asc";
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
        		tmp.setAvailable(rs.getByte(9));
        		tmp.setBoardQnA_DeleteReg_Date(rs.getString(10));
        		tmp.setNotice(rs.getByte(11));
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
    
    // �������� ����Ʈ 
    public ArrayList<BoardQnADataBean> getNoticeList() { 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BoardQnADataBean> list = new ArrayList<BoardQnADataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from boardQnA where boardQnA_CommentID = 0 and available = 1 and notice = 1 order by boardQnA_ID desc, boardQnA_ReplyID asc";
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
        		tmp.setAvailable(rs.getByte(9));
        		tmp.setBoardQnA_DeleteReg_Date(rs.getString(10));
        		tmp.setNotice(rs.getByte(11));
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
    		String SQL = "select * from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID = ? and boardQnA_CommentID = 0"; // available�� �ʿ���� ���� : ����Ʈ�� �̾Ƴ� �� �̹� �ɷ����� + ���߿� ���� �Խ��� ���� ����� ����
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, boardQnA_ID);
    		pstmt.setInt(2, boardQnA_ReplyID);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			written = new BoardQnADataBean();
        		written.setBoardQnA_ID(rs.getInt(1));
        		written.setBoardQnA_ReplyID(rs.getInt(2));
        		written.setBoardQnA_CommentID(rs.getInt(3));
        		written.setBoardQnA_CommentID_Re(rs.getInt(4));
        		written.setBoardQnA_Title(rs.getString(5));
        		written.setUserID(rs.getString(6));
        		written.setBoardQnA_Reg_Date(rs.getString(7));
        		written.setBoardQnA_Content(rs.getString(8));
        		written.setAvailable(rs.getByte(9));
        		written.setBoardQnA_DeleteReg_Date(rs.getString(10));
        		written.setNotice(rs.getByte(11));
        		return written;
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return null; 
    }
    
    // ���� �ڸ�Ʈ�� �������� �޼���
    public BoardQnADataBean viewComment(int boardQnA_ID, int boardQnA_ReplyID, int boardQnA_CommentID, int boardQnA_CommentID_Re) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardQnADataBean comment = null;
    	try {
    		conn = getConnection();
    		String SQL = "select * from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID = ? and boardQnA_CommentID = ? and boardQnA_CommentID_Re = ?";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, boardQnA_ID);
    		pstmt.setInt(2, boardQnA_ReplyID);
    		pstmt.setInt(3, boardQnA_CommentID);
    		pstmt.setInt(4, boardQnA_CommentID_Re);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			comment = new BoardQnADataBean();
        		comment.setBoardQnA_ID(rs.getInt(1));
        		comment.setBoardQnA_ReplyID(rs.getInt(2));
        		comment.setBoardQnA_CommentID(rs.getInt(3));
        		comment.setBoardQnA_CommentID_Re(rs.getInt(4));
        		comment.setBoardQnA_Title(rs.getString(5));
        		comment.setUserID(rs.getString(6));
        		comment.setBoardQnA_Reg_Date(rs.getString(7));
        		comment.setBoardQnA_Content(rs.getString(8));
        		comment.setAvailable(rs.getByte(9));
        		comment.setBoardQnA_DeleteReg_Date(rs.getString(10));
        		comment.setNotice(rs.getByte(11));
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return comment; 
    }
    
    // �ڸ�Ʈ ����Ʈ�� �������� �޼���
    public ArrayList<BoardQnADataBean> getCommentList(BoardQnADataBean written) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BoardQnADataBean> list = new ArrayList<BoardQnADataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from boardQnA where boardQnA_ID = ? and boardQnA_ReplyID = ? and boardQnA_CommentID > 0 and boardQnA_CommentID_Re >= 0 and available = 1 order by boardQnA_CommentID asc, boardQnA_CommentID_Re asc";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, written.getBoardQnA_ID());
    		pstmt.setInt(2, written.getBoardQnA_ReplyID());
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
        		tmp.setAvailable(rs.getByte(9));
        		tmp.setBoardQnA_DeleteReg_Date(rs.getString(10));
        		tmp.setNotice(rs.getByte(11));
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
