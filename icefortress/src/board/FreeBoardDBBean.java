package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class FreeBoardDBBean {

    private static FreeBoardDBBean instance = new FreeBoardDBBean();
    
    public static FreeBoardDBBean getInstance() {
        return instance;
    }
    
    private FreeBoardDBBean() {}
    
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
    
    public int getNextWrittenID() { // �Խñ� ��ȣ�� �Ű��� (writtenID)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select writtenID from freeBoard order by writtenID desc");
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
    
    public int getNextReplyID(int writtenID) { // ��� ��ȣ�� �Ű��� (replyID)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select replyID from freeBoard where writtenID = ? order by replyID desc");
    		pstmt.setInt(1, writtenID);
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
    
    public int getNextCommentID(int writtenID, int replyID) { // ��� ��ȣ�� �Ű��� (commentID)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select commentID from freeBoard where writtenID = ? and replyID = ? order by commentID desc");
    		pstmt.setInt(1, writtenID);
    		pstmt.setInt(2, replyID);
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
    
    public int getNextCommentReply(int writtenID, int replyID, int commentID) { // ���� ��ȣ�� �Ű��� (commentID_Re)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select commentID_Re from freeBoard where writtenID = ? and replyID = ? and commentID = ?  order by commentID_Re desc");
    		pstmt.setInt(1, writtenID);
    		pstmt.setInt(2, replyID);
    		pstmt.setInt(3, commentID);
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
    
    // ��� ���� ����Ʈ�� ����
    public ArrayList<FreeBoardDataBean> getList() { 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<FreeBoardDataBean> list = new ArrayList<FreeBoardDataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from freeBoard where commentID = 0 and available = 1 and notice = 0 order by writtenID desc, replyID asc";
    		pstmt = conn.prepareStatement(SQL);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			FreeBoardDataBean tmp = new FreeBoardDataBean();
        		tmp.setWrittenID(rs.getInt(1));
        		tmp.setReplyID(rs.getInt(2));
        		tmp.setCommentID(rs.getInt(3));
        		tmp.setCommentID_Re(rs.getInt(4));
        		tmp.setTitle(rs.getString(5));
        		tmp.setUserID(rs.getString(6));
        		tmp.setReg_Date(rs.getString(7));
        		tmp.setContent(rs.getString(8));
        		tmp.setAvailable(rs.getByte(9));
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
    public ArrayList<FreeBoardDataBean> getListView(ArrayList<FreeBoardDataBean> list, int start, int pageSize) {
    	ArrayList<FreeBoardDataBean> tmp = new ArrayList<FreeBoardDataBean>(pageSize);
    	for (int i = start-1; i < start-1+pageSize; i++) {
    		if (i >= list.size()) {
    			break;
    		}
    		tmp.add(list.get(i));
    	}
    	return tmp;
    }
    
    // �� �������� �����͸� �������� �޼���
    public FreeBoardDataBean viewWritten(int writtenID, int replyID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FreeBoardDataBean written = null;
    	try {
    		conn = getConnection();
    		String SQL = "select * from freeBoard where writtenID = ? and replyID = ? and commentID = 0"; 
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, writtenID);
    		pstmt.setInt(2, replyID);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			written = new FreeBoardDataBean();
        		written.setWrittenID(rs.getInt(1));
        		written.setReplyID(rs.getInt(2));
        		written.setCommentID(rs.getInt(3));
        		written.setCommentID_Re(rs.getInt(4));
        		written.setTitle(rs.getString(5));
        		written.setUserID(rs.getString(6));
        		written.setReg_Date(rs.getString(7));
        		written.setContent(rs.getString(8));
        		written.setAvailable(rs.getByte(9));
        		written.setDeleteReg_Date(rs.getString(10));
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
    
    // �������� ����Ʈ 
    public ArrayList<FreeBoardDataBean> getNoticeList() { 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<FreeBoardDataBean> list = new ArrayList<FreeBoardDataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from freeBoard where commentID = 0 and available = 1 and notice = 1 order by writtenID desc, replyID asc";
    		pstmt = conn.prepareStatement(SQL);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			FreeBoardDataBean tmp = new FreeBoardDataBean();
        		tmp.setWrittenID(rs.getInt(1));
        		tmp.setReplyID(rs.getInt(2));
        		tmp.setCommentID(rs.getInt(3));
        		tmp.setCommentID_Re(rs.getInt(4));
        		tmp.setTitle(rs.getString(5));
        		tmp.setUserID(rs.getString(6));
        		tmp.setReg_Date(rs.getString(7));
        		tmp.setContent(rs.getString(8));
        		tmp.setAvailable(rs.getByte(9));
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
    
    public int write(String title, String userID, String content, byte notice) { // �� �ۼ� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into freeBoard values (?,0,0,0,?,?,?,?,1,?,?)");
        	pstmt.setInt(1, getNextWrittenID());
        	pstmt.setString(2, title);
        	pstmt.setString(3, userID);
        	pstmt.setString(4, getDate());
        	pstmt.setString(5, content);
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
    
    // �ڸ�Ʈ ����Ʈ�� �������� �޼���
    public ArrayList<FreeBoardDataBean> getCommentList(FreeBoardDataBean written) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<FreeBoardDataBean> list = new ArrayList<FreeBoardDataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from freeBoard where writtenID = ? and replyID = ? and commentID > 0 and commentID_Re >= 0 and available = 1 order by commentID asc, commentID_Re asc";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, written.getWrittenID());
    		pstmt.setInt(2, written.getReplyID());
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			FreeBoardDataBean tmp = new FreeBoardDataBean();
        		tmp.setWrittenID(rs.getInt(1));
        		tmp.setReplyID(rs.getInt(2));
        		tmp.setCommentID(rs.getInt(3));
        		tmp.setCommentID_Re(rs.getInt(4));
        		tmp.setTitle(rs.getString(5));
        		tmp.setUserID(rs.getString(6));
        		tmp.setReg_Date(rs.getString(7));
        		tmp.setContent(rs.getString(8));
        		tmp.setAvailable(rs.getByte(9));
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
    
    // ��, ����� �����ϴ� �޼���
    public int delete(int writtenID, int replyID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("select * from freeBoard where writtenID = ? and replyID = ?"); // �� ���� ��۵� ���� �ҷ���
        	pstmt.setInt(1, writtenID);
        	pstmt.setInt(2, replyID);
        	rs = pstmt.executeQuery();
        	while(rs.next()) {
        		pstmt = conn.prepareStatement("update freeBoard set available = 0, deleteReg_Date = ? where writtenID = ? and replyID = ? and commentID = ? and commentID_Re = ?");
        		pstmt.setString(1, getDate());
        		pstmt.setInt(2, rs.getInt("writtenID"));
        		pstmt.setInt(3, rs.getInt("replyID"));
        		pstmt.setInt(4, rs.getInt("commentID")); // �� ���� ���
        		pstmt.setInt(5, rs.getInt("commentID_Re")); // �� ���� ����
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
    
    // ��, ����� �����ϴ� �޼���
    public int update(FreeBoardDataBean update, String title, String content, byte notice) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update freeBoard set title = ?, content = ?, reg_Date = ?, notice = ? where writtenID = ? and replyID = ? and commentID = 0");
        	pstmt.setString(1, title);
        	pstmt.setString(2, content);
        	pstmt.setString(3, getDate());
        	pstmt.setByte(4, notice);
        	pstmt.setInt(5, update.getWrittenID());
        	pstmt.setInt(6, update.getReplyID());
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
    
    public int writeReply(String title, String userID, String content, int writtenID) { // ��� �ۼ� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into freeBoard values (?, ?, 0, 0, ?, ?, ?, ?, 1, ?, 0)");
        	pstmt.setInt(1, writtenID);
        	pstmt.setInt(2, getNextReplyID(writtenID));
        	pstmt.setString(3, title);
        	pstmt.setString(4, userID);
        	pstmt.setString(5, getDate());
        	pstmt.setString(6, content);
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
    
    // ���� �ڸ�Ʈ�� �������� �޼���
    public FreeBoardDataBean viewComment(int writtenID, int replyID, int commentID, int commentID_Re) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FreeBoardDataBean comment = null;
    	try {
    		conn = getConnection();
    		String SQL = "select * from freeBoard where writtenID = ? and replyID = ? and commentID = ? and commentID_Re = ?";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, writtenID);
    		pstmt.setInt(2, replyID);
    		pstmt.setInt(3, commentID);
    		pstmt.setInt(4, commentID_Re);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			comment = new FreeBoardDataBean();
        		comment.setWrittenID(rs.getInt(1));
        		comment.setReplyID(rs.getInt(2));
        		comment.setCommentID(rs.getInt(3));
        		comment.setCommentID_Re(rs.getInt(4));
        		comment.setTitle(rs.getString(5));
        		comment.setUserID(rs.getString(6));
        		comment.setReg_Date(rs.getString(7));
        		comment.setContent(rs.getString(8));
        		comment.setAvailable(rs.getByte(9));
        		comment.setDeleteReg_Date(rs.getString(10));
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
    
    // �ڸ�Ʈ�� �����ϴ� �޼���
    public int deleteComment(FreeBoardDataBean comment) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update freeBoard set available = 0, deleteReg_Date = ? where writtenID = ? and replyID = ? and commentID = ? and commentID_Re = ?"); 
        	pstmt.setString(1, getDate());
        	pstmt.setInt(2, comment.getWrittenID());
        	pstmt.setInt(3, comment.getReplyID());
        	pstmt.setInt(4, comment.getCommentID());
        	pstmt.setInt(5, comment.getCommentID_Re());
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
    public int updateComment(FreeBoardDataBean comment, String content) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update freeBoard set content = ?, reg_Date = ? where writtenID = ? and replyID = ? and commentID = ? and commentID_Re = ?");
        	pstmt.setString(1, content);
        	pstmt.setString(2, getDate());
        	pstmt.setInt(3, comment.getWrittenID());
        	pstmt.setInt(4, comment.getReplyID());
        	pstmt.setInt(5, comment.getCommentID());
        	pstmt.setInt(6, comment.getCommentID_Re());
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
    
    public int writeCommentReply(String userID, String content, int writtenID, int replyID, int commentID) { // ���� �ۼ� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into freeBoard values (?, ?, ?, ?, ?, ?, ?, ?, 1, ?, 0)");
        	pstmt.setInt(1, writtenID);
        	pstmt.setInt(2, replyID);
        	pstmt.setInt(3, commentID);
        	pstmt.setInt(4, getNextCommentReply(writtenID, replyID, commentID));
        	pstmt.setString(5, writtenID+","+replyID+","+commentID+"��° ����� "+ getNextCommentReply(writtenID, replyID, commentID) +"��° ����");
        	pstmt.setString(6, userID);
        	pstmt.setString(7, getDate());
        	pstmt.setString(8, content);
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
    
    public int writeComment(String userID, String content, int writtenID, int replyID) { // ��� �ۼ� �޼���
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into freeBoard values (?, ?, ?, 0, ?, ?, ?, ?, 1, ?, 0)");
        	pstmt.setInt(1, writtenID);
        	pstmt.setInt(2, replyID);
        	pstmt.setInt(3, getNextCommentID(writtenID, replyID));
        	pstmt.setString(4, writtenID+","+replyID+"�� "+ getNextCommentID(writtenID, replyID) +"��° ���");
        	pstmt.setString(5, userID);
        	pstmt.setString(6, getDate());
        	pstmt.setString(7, content);
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
}
