package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.util.Base64;
import java.util.Base64.Encoder;
import user.BCrypt;

public class UserDBBean {
	
	// UserDBBean 전역 객체 생성 <- 한개의 객제만 생성해서 공유
    private static UserDBBean instance = new UserDBBean();
    
    // UserDBBean객체를 리턴하는 메서드
    public static UserDBBean getInstance() {
        return instance;
    }
    
    private UserDBBean() {}
    
    // 커넥션 풀에서 커넥션 객체를 얻어내는 메서드
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/icefortress");
        return ds.getConnection();
    }
 
	
	// 로그인 폼 처리(loginPro.jsp) + 회원정보 수정 처리(memberCheck.jsp)페이지의 사용자 인증 처리
    public int userCheck(String userID, String userPassword){
    	Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs= null;
		Encoder sha = Base64.getEncoder();
		try {
			conn = getConnection();
			
            String orgPass = userPassword;
            String shaPass = sha.encodeToString(orgPass.getBytes());
        	
            pstmt = conn.prepareStatement(
            	"select userPassword from member where userID = ?");
            pstmt.setString(1, userID);
            rs= pstmt.executeQuery();

			if(rs.next()){ // 해당 아이디가 있으면 수행
				String dbpasswd= rs.getString("userPassword"); 
				if(BCrypt.checkpw(shaPass,dbpasswd)) {
					return 1; // 인증성공 (비밀번호 일치)
				} else {
					return 0; // 비밀번호 틀림
				}
			} else { // 해당 아이디 없으면 수행
				return -1; // 아이디 없음
			}
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
			if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -2; // 데이터베이스 오류
	}
    
    // 회원 가입 처리(registerPro.jsp)에서 사용하는 새 레코드 추가 메서드
    public void insertMember(UserDataBean member){
    	Connection conn = null;
        PreparedStatement pstmt = null;
        Encoder sha = Base64.getEncoder();
        try {
        	conn = getConnection();
        	
            String orgPass = member.getUserPassword();
            String shaPass = sha.encodeToString(orgPass.getBytes());
        	String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
        	
            pstmt = conn.prepareStatement("insert into member values (?,?,?,?)");
            pstmt.setString(1, member.getUserID());
            pstmt.setString(2, bcPass);
            pstmt.setInt(3, 0);
            pstmt.setTimestamp(4, member.getReg_date());		
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
    }
	
    // 아이디 중복 확인 (confirmId.jsp)에서 아이디의 중복 여부를 확인하는 메서드
   	public int confirmId(String userID) {
   		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs= null;
   		try {
   			conn = getConnection();   
   			
               pstmt = conn.prepareStatement(
               	"select userID from member where userID = ?");
               pstmt.setString(1, userID);
               rs= pstmt.executeQuery();

   			if(rs.next()) // 아이디 존재
   				return 1; // 중복되는 아이디 있음
   			else
   				return -1; // 중복되는 아이디 없음
           } catch(Exception e) {
               e.printStackTrace();
           } finally {
   			if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
               if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
               if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
           }
   		return -2; // 데이터베이스 오류
   	}
   	
   	// 비밀번호 변경 (modifyPasswordPro.jsp)에서 비밀번호 변경을 위한 메서드
   	public int updatePassword(String userID, String userPassword) {
   		Connection conn = null;
        PreparedStatement pstmt = null;
		Encoder sha = Base64.getEncoder();
		try {
			conn = getConnection();
			
            String orgPass = userPassword;
            String shaPass = sha.encodeToString(orgPass.getBytes());
            String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
        	
            pstmt = conn.prepareStatement("update member set userPassword = ?" + "where userID = ?");
            pstmt.setString(1, bcPass);
            pstmt.setString(2, userID);
            pstmt.executeUpdate();
            
            return 1; // 비밀번호 변경 성공
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // 비밀번호 변경 실패
   	}
   	
   	// 아이디 변경 (modifyIDPro.jsp)에서 아이디 변경을 위한 메서드
   	public int updateID(String userID, String newUserID) {
   		Connection conn = null;
   		PreparedStatement pstmt = null;
   		ResultSet rs = null;
   		try {
   			conn = getConnection();
   			pstmt = conn.prepareStatement("select * from member where userID = ?");
   			pstmt.setString(1, userID);
   			rs = pstmt.executeQuery();
   			String userPassword = "";
   			int grade = 0;
   			Timestamp reg_date = null;
   			if (rs.next()) {
   				userPassword = rs.getString("userPassword");
   	   			grade = rs.getInt("grade");
   	   			reg_date = rs.getTimestamp("reg_date");
   			}
   			
   			pstmt = conn.prepareStatement("insert into member values (?,?,?,?)");
            pstmt.setString(1, newUserID);
            pstmt.setString(2, userPassword);
            pstmt.setInt(3, grade);
            pstmt.setTimestamp(4, reg_date);		
            pstmt.executeUpdate();
            return 1; // 아이디 변경 성공
   		} catch (Exception e) {
   			e.printStackTrace();
   		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // 아이디 변경 실패
   	}
   	// 아이디 변경 (modifyIDPro.jsp)에서 아이디 변경 후 기존 아이디 삭제를 위한 메서드
   	public int deleteMember(String userID){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        try {
			conn = getConnection();
			
			pstmt = conn.prepareStatement("delete from member where userID = ?");
            pstmt.setString(1, userID);
            pstmt.executeUpdate();
			return 1; // 기존 아이디 삭제 성공
			
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // 기존 아이디 삭제 실패
    }
   	
   	// 회원 탈퇴 처리(deletePro.jsp)에서 회원 정보를 삭제하는 메서드
    public int deleteMember(String userID, String userPassword){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        Encoder sha = Base64.getEncoder();
        try {
			conn = getConnection();
			
			String orgPass = userPassword;
            String shaPass = sha.encodeToString(orgPass.getBytes());

            pstmt = conn.prepareStatement(
            	"select userPassword from member where userID = ?");
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            
			if(rs.next()){
				String dbpasswd= rs.getString("userPassword"); 
				if(BCrypt.checkpw(shaPass,dbpasswd)){
					pstmt = conn.prepareStatement(
            	      "delete from member where userID = ?");
                    pstmt.setString(1, userID);
                    pstmt.executeUpdate();
					return 1; // 회원 탈퇴처리 성공
				}else
					return 0; // 회원 탈퇴처리 실패
			}
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // 데이터베이스 오류
    }
    
    // 세션의 등급을 확인하는 메서드
    public int checkGrade(String userID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        try {
			conn = getConnection();

            pstmt = conn.prepareStatement(
            	"select grade from member where userID = ?");
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            
			if(rs.next()){
				return rs.getInt("grade"); // 세션에 있는 아이디의 등급을 가져옴
			} else {
				return -1; // 비로그인 상태(세션이 없는 상태)인 경우
			}
			
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -2; // 데이터베이스 오류
    }
}
