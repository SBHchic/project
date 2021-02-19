package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GuildBoardDBBean {

private static GuildBoardDBBean instance = new GuildBoardDBBean();
    
    public static GuildBoardDBBean getInstance() {
        return instance;
    }
    
    private GuildBoardDBBean() {}
    
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
    
    public int getNextWrittenID() { // 게시글 번호를 매겨줌 (writtenID)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select writtenID from guildBoard order by writtenID desc");
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
    
    public int getNextReplyID(int writtenID) { // 답글 번호를 매겨줌 (replyID)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select replyID from guildBoard where writtenID = ? order by replyID desc");
    		pstmt.setInt(1, writtenID);
    		rs = pstmt.executeQuery();
    		if (rs.next()) {
    			return rs.getInt(1) + 1;
    		}
    		return 1; // 첫 번째 답글인 경우
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return -1; // 데이터베이스 오류
    }
    
    public int getNextCommentID(int writtenID, int replyID) { // 댓글 번호를 매겨줌 (commentID)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select commentID from guildBoard where writtenID = ? and replyID = ? order by commentID desc");
    		pstmt.setInt(1, writtenID);
    		pstmt.setInt(2, replyID);
    		rs = pstmt.executeQuery();
    		if (rs.next()) {
    			return rs.getInt(1) + 1;
    		}
    		return 1; // 첫 번째 댓글인 경우
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return -1; // 데이터베이스 오류
    }
    
    public int getNextCommentReply(int writtenID, int replyID, int commentID) { // 대댓글 번호를 매겨줌 (commentID_Re)
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select commentID_Re from guildBoard where writtenID = ? and replyID = ? and commentID = ?  order by commentID_Re desc");
    		pstmt.setInt(1, writtenID);
    		pstmt.setInt(2, replyID);
    		pstmt.setInt(3, commentID);
    		rs = pstmt.executeQuery();
    		if (rs.next()) {
    			return rs.getInt(1) + 1;
    		}
    		return 1; // 첫 번째 대댓글인 경우
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
    	return -1; // 데이터베이스 오류
    }
    
    // 모든 글을 리스트에 넣음 (공지 제외)
    public ArrayList<GuildBoardDataBean> getList() { 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<GuildBoardDataBean> list = new ArrayList<GuildBoardDataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from guildBoard where commentID = 0 and available = 1 and notice = 0 order by writtenID desc, replyID asc";
    		pstmt = conn.prepareStatement(SQL);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			GuildBoardDataBean tmp = new GuildBoardDataBean();
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
    
    // 페이지 글 개수 제한으로 리스트 출력
    public ArrayList<GuildBoardDataBean> getListView(ArrayList<GuildBoardDataBean> list, int start, int pageSize) {
    	ArrayList<GuildBoardDataBean> tmp = new ArrayList<GuildBoardDataBean>(pageSize);
    	for (int i = start-1; i < start-1+pageSize; i++) {
    		if (i >= list.size()) {
    			break;
    		}
    		tmp.add(list.get(i));
    	}
    	return tmp;
    }
    
    // 뷰 페이지에 데이터를 가져오는 메서드
    public GuildBoardDataBean viewWritten(int writtenID, int replyID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        GuildBoardDataBean written = null;
    	try {
    		conn = getConnection();
    		String SQL = "select * from guildBoard where writtenID = ? and replyID = ? and commentID = 0"; 
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, writtenID);
    		pstmt.setInt(2, replyID);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			written = new GuildBoardDataBean();
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
    
    // 공지사항 리스트 
    public ArrayList<GuildBoardDataBean> getNoticeList() { 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<GuildBoardDataBean> list = new ArrayList<GuildBoardDataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from guildBoard where commentID = 0 and available = 1 and notice = 1 order by writtenID desc, replyID asc";
    		pstmt = conn.prepareStatement(SQL);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			GuildBoardDataBean tmp = new GuildBoardDataBean();
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
    
    public int write(String title, String userID, String content, byte notice) { // 글 작성 메서드
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into guildBoard values (?,0,0,0,?,?,?,?,1,?,?)");
        	pstmt.setInt(1, getNextWrittenID());
        	pstmt.setString(2, title);
        	pstmt.setString(3, userID);
        	pstmt.setString(4, getDate());
        	pstmt.setString(5, content);
        	pstmt.setString(6, "2000-01-01 00:00:00"); // 기본값 지정
        	pstmt.setByte(7, notice);
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
    
    // 코멘트 리스트를 가져오는 메서드
    public ArrayList<GuildBoardDataBean> getCommentList(GuildBoardDataBean written) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<GuildBoardDataBean> list = new ArrayList<GuildBoardDataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from guildBoard where writtenID = ? and replyID = ? and commentID > 0 and commentID_Re >= 0 and available = 1 order by commentID asc, commentID_Re asc";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, written.getWrittenID());
    		pstmt.setInt(2, written.getReplyID());
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			GuildBoardDataBean tmp = new GuildBoardDataBean();
        		tmp.setWrittenID(rs.getInt(1));
        		tmp.setReplyID(rs.getInt(2));
        		tmp.setCommentID(rs.getInt(3));
        		tmp.setCommentID_Re(rs.getInt(4));
        		tmp.setTitle(rs.getString(5));
        		tmp.setUserID(rs.getString(6));
        		tmp.setReg_Date(rs.getString(7));
        		tmp.setContent(rs.getString(8));
        		tmp.setAvailable(rs.getByte(9));
        		tmp.setDeleteReg_Date(rs.getString(10));
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
    
    // 글, 답글을 삭제하는 메서드
    public int delete(int writtenID, int replyID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("select * from guildBoard where writtenID = ? and replyID = ?"); // 그 글의 댓글도 같이 불러옴
        	pstmt.setInt(1, writtenID);
        	pstmt.setInt(2, replyID);
        	rs = pstmt.executeQuery();
        	while(rs.next()) {
        		pstmt = conn.prepareStatement("update guildBoard set available = 0, deleteReg_Date = ? where writtenID = ? and replyID = ? and commentID = ? and commentID_Re = ?");
        		pstmt.setString(1, getDate());
        		pstmt.setInt(2, rs.getInt("writtenID"));
        		pstmt.setInt(3, rs.getInt("replyID"));
        		pstmt.setInt(4, rs.getInt("commentID")); // 그 글의 댓글
        		pstmt.setInt(5, rs.getInt("commentID_Re")); // 그 글의 대댓글
        		pstmt.executeUpdate(); // 그 글의 댓글, 대댓글도 같이 삭제됨
        	}
        	return 1; // 삭제 성공
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // 데이터베이스 오류
    }
    
    // 글, 답글을 수정하는 메서드
    public int update(GuildBoardDataBean update, String title, String content, byte notice) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update guildBoard set title = ?, content = ?, reg_Date = ?, notice = ? where writtenID = ? and replyID = ? and commentID = 0");
        	pstmt.setString(1, title);
        	pstmt.setString(2, content);
        	pstmt.setString(3, getDate());
        	pstmt.setByte(4, notice);
        	pstmt.setInt(5, update.getWrittenID());
        	pstmt.setInt(6, update.getReplyID());
        	pstmt.executeUpdate();
        	return 1; // 수정 성공
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // 데이터베이스 오류
    }
    
    public int writeReply(String title, String userID, String content, int writtenID) { // 답글 작성 메서드
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into guildBoard values (?, ?, 0, 0, ?, ?, ?, ?, 1, ?, 0)");
        	pstmt.setInt(1, writtenID);
        	pstmt.setInt(2, getNextReplyID(writtenID));
        	pstmt.setString(3, title);
        	pstmt.setString(4, userID);
        	pstmt.setString(5, getDate());
        	pstmt.setString(6, content);
        	pstmt.setString(7, "2000-01-01 00:00:00"); // 기본값 지정
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
    
    // 단일 코멘트를 가져오는 메서드
    public GuildBoardDataBean viewComment(int writtenID, int replyID, int commentID, int commentID_Re) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        GuildBoardDataBean comment = null;
    	try {
    		conn = getConnection();
    		String SQL = "select * from guildBoard where writtenID = ? and replyID = ? and commentID = ? and commentID_Re = ?";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, writtenID);
    		pstmt.setInt(2, replyID);
    		pstmt.setInt(3, commentID);
    		pstmt.setInt(4, commentID_Re);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			comment = new GuildBoardDataBean();
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
    
    // 코멘트를 삭제하는 메서드
    public int deleteComment(GuildBoardDataBean comment) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update guildBoard set available = 0, deleteReg_Date = ? where writtenID = ? and replyID = ? and commentID = ? and commentID_Re = ?"); 
        	pstmt.setString(1, getDate());
        	pstmt.setInt(2, comment.getWrittenID());
        	pstmt.setInt(3, comment.getReplyID());
        	pstmt.setInt(4, comment.getCommentID());
        	pstmt.setInt(5, comment.getCommentID_Re());
        	pstmt.executeUpdate();
        	return 1; // 삭제 성공
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // 데이터베이스 오류
    }
    
    // 댓글, 대댓글을 수정하는 메서드
    public int updateComment(GuildBoardDataBean comment, String content) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update guildBoard set content = ?, reg_Date = ? where writtenID = ? and replyID = ? and commentID = ? and commentID_Re = ?");
        	pstmt.setString(1, content);
        	pstmt.setString(2, getDate());
        	pstmt.setInt(3, comment.getWrittenID());
        	pstmt.setInt(4, comment.getReplyID());
        	pstmt.setInt(5, comment.getCommentID());
        	pstmt.setInt(6, comment.getCommentID_Re());
        	pstmt.executeUpdate();
        	return 1; // 수정 성공
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
    	}
        return -1; // 데이터베이스 오류
    }
    
    public int writeCommentReply(String userID, String content, int writtenID, int replyID, int commentID) { // 대댓글 작성 메서드
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into guildBoard values (?, ?, ?, ?, ?, ?, ?, ?, 1, ?, 0)");
        	pstmt.setInt(1, writtenID);
        	pstmt.setInt(2, replyID);
        	pstmt.setInt(3, commentID);
        	pstmt.setInt(4, getNextCommentReply(writtenID, replyID, commentID));
        	pstmt.setString(5, writtenID+","+replyID+","+commentID+"번째 댓글의 "+ getNextCommentReply(writtenID, replyID, commentID) +"번째 대댓글");
        	pstmt.setString(6, userID);
        	pstmt.setString(7, getDate());
        	pstmt.setString(8, content);
        	pstmt.setString(9, "2000-01-01 00:00:00"); // 기본값 지정
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
    
    public int writeComment(String userID, String content, int writtenID, int replyID) { // 댓글 작성 메서드
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into guildBoard values (?, ?, ?, 0, ?, ?, ?, ?, 1, ?, 0)");
        	pstmt.setInt(1, writtenID);
        	pstmt.setInt(2, replyID);
        	pstmt.setInt(3, getNextCommentID(writtenID, replyID));
        	pstmt.setString(4, writtenID+","+replyID+"의 "+ getNextCommentID(writtenID, replyID) +"번째 댓글");
        	pstmt.setString(5, userID);
        	pstmt.setString(6, getDate());
        	pstmt.setString(7, content);
        	pstmt.setString(8, "2000-01-01 00:00:00"); // 기본값 지정
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
}
