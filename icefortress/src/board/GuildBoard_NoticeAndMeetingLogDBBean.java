package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GuildBoard_NoticeAndMeetingLogDBBean {

private static GuildBoard_NoticeAndMeetingLogDBBean instance = new GuildBoard_NoticeAndMeetingLogDBBean();
    
    public static GuildBoard_NoticeAndMeetingLogDBBean getInstance() {
        return instance;
    }
    
    private GuildBoard_NoticeAndMeetingLogDBBean() {}
    
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
    		
    		pstmt = conn.prepareStatement("select writtenID from guildBoard_noticeAndMeetingLog order by writtenID desc");
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
    
    // 길드 공지사항 or 운영진 회의록을 리스트에 넣음
    public ArrayList<GuildBoard_NoticeAndMeetingLogDataBean> getList(byte location) { 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<GuildBoard_NoticeAndMeetingLogDataBean> list = new ArrayList<GuildBoard_NoticeAndMeetingLogDataBean>();
    	try {
    		conn = getConnection();
    		String SQL = "select * from guildBoard_noticeAndMeetingLog where location = ? and available = 1 order by writtenID desc";
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setByte(1, location);
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			GuildBoard_NoticeAndMeetingLogDataBean tmp = new GuildBoard_NoticeAndMeetingLogDataBean();
        		tmp.setWrittenID(rs.getInt(1));
        		tmp.setTitle(rs.getString(2));
        		tmp.setUserID(rs.getString(3));
        		tmp.setReg_Date(rs.getString(4));
        		tmp.setContent(rs.getString(5));
        		tmp.setAvailable(rs.getByte(6));
        		tmp.setLocation(rs.getByte(7));
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
    public ArrayList<GuildBoard_NoticeAndMeetingLogDataBean> getListView(ArrayList<GuildBoard_NoticeAndMeetingLogDataBean> list, int start, int pageSize) {
    	ArrayList<GuildBoard_NoticeAndMeetingLogDataBean> tmp = new ArrayList<GuildBoard_NoticeAndMeetingLogDataBean>(pageSize);
    	for (int i = start-1; i < start-1+pageSize; i++) {
    		if (i >= list.size()) {
    			break;
    		}
    		tmp.add(list.get(i));
    	}
    	return tmp;
    }
    
    public int write(String title, String userID, String content, byte location) { // 글 작성 메서드
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("insert into guildBoard_noticeAndMeetingLog values (?,?,?,?,?,1,?)");
        	pstmt.setInt(1, getNextWrittenID());
        	pstmt.setString(2, title);
        	pstmt.setString(3, userID);
        	pstmt.setString(4, getDate());
        	pstmt.setString(5, content);
        	pstmt.setByte(6, location);
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
    
    // 뷰 페이지에 데이터를 가져오는 메서드
    public GuildBoard_NoticeAndMeetingLogDataBean viewWritten(int writtenID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        GuildBoard_NoticeAndMeetingLogDataBean written = null;
    	try {
    		conn = getConnection();
    		String SQL = "select * from guildBoard_noticeAndMeetingLog where writtenID = ? "; 
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setInt(1, writtenID);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			written = new GuildBoard_NoticeAndMeetingLogDataBean();
        		written.setWrittenID(rs.getInt(1));
        		written.setTitle(rs.getString(2));
        		written.setUserID(rs.getString(3));
        		written.setReg_Date(rs.getString(4));
        		written.setContent(rs.getString(5));
        		written.setAvailable(rs.getByte(6));
        		written.setLocation(rs.getByte(7));
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
    
    // 글을 수정하는 메서드
    public int update(GuildBoard_NoticeAndMeetingLogDataBean update, String title, String content) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("update guildBoard_noticeAndMeetingLog set title = ?, content = ?, reg_Date = ? where writtenID = ? ");
        	pstmt.setString(1, title);
        	pstmt.setString(2, content);
        	pstmt.setString(3, getDate());
        	pstmt.setInt(4, update.getWrittenID());
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
    
    // 글, 답글을 삭제하는 메서드
    public int delete(int writtenID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	conn = getConnection();
        	
        	pstmt = conn.prepareStatement("select * from guildBoard_noticeAndMeetingLog where writtenID = ?"); 
        	pstmt.setInt(1, writtenID);
        	rs = pstmt.executeQuery();
        	while(rs.next()) {
        		pstmt = conn.prepareStatement("update guildBoard_noticeAndMeetingLog set available = 0 where writtenID = ?");
        		pstmt.setInt(1, rs.getInt("writtenID"));
        		pstmt.executeUpdate();
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
}
