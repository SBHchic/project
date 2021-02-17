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

public class UserDBBean {
	
	// UserDBBean ���� ��ü ���� <- �Ѱ��� ������ �����ؼ� ����
    private static UserDBBean instance = new UserDBBean();
    
    // UserDBBean��ü�� �����ϴ� �޼���
    public static UserDBBean getInstance() {
        return instance;
    }
    
    private UserDBBean() {}
    
    // Ŀ�ؼ� Ǯ���� Ŀ�ؼ� ��ü�� ���� �޼���
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/icefortress");
        return ds.getConnection();
    }
 
	
	// �α��� �� ó��(loginPro.jsp) + ȸ������ ���� ó��(memberCheck.jsp)�������� ����� ���� ó��
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

			if(rs.next()){ // �ش� ���̵� ������ ����
				String dbpasswd= rs.getString("userPassword"); 
				if(BCrypt.checkpw(shaPass,dbpasswd)) {
					return 1; // �������� (��й�ȣ ��ġ)
				} else {
					return 0; // ��й�ȣ Ʋ��
				}
			} else { // �ش� ���̵� ������ ����
				return -1; // ���̵� ����
			}
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
			if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -2; // �����ͺ��̽� ����
	}
    
    // ȸ�� ���� ó��(registerPro.jsp)���� ����ϴ� �� ���ڵ� �߰� �޼���
    public int insertMember(UserDataBean member){
    	Connection conn = null;
        PreparedStatement pstmt = null;
        Encoder sha = Base64.getEncoder();
        try {
        	conn = getConnection();
        	
            String orgPass = member.getUserPassword();
            String shaPass = sha.encodeToString(orgPass.getBytes());
        	String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
        	
            pstmt = conn.prepareStatement("insert into member values (?,?,?,?,?)");
            pstmt.setString(1, member.getUserID());
            pstmt.setString(2, bcPass);
            pstmt.setInt(3, 0);
            pstmt.setTimestamp(4, member.getReg_date());
            pstmt.setString(5, member.getServer());
            pstmt.executeUpdate();
            return 1; // ȸ������ ����
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
        return -1;
    }
	
    // ���̵� �ߺ� Ȯ�� (confirmId.jsp)���� ���̵��� �ߺ� ���θ� Ȯ���ϴ� �޼���
   	public int confirmId(String userID) {
   		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs= null;
   		try {
   			conn = getConnection();   
   			
            pstmt = conn.prepareStatement("select userID from member where userID = ?");
            pstmt.setString(1, userID);
            rs= pstmt.executeQuery();

   			if(rs.next()) {// ���̵� ����
   				return 1; // �ߺ��Ǵ� ���̵� ����
   			}
   			
   			return -1; // �ߺ��Ǵ� ���̵� ����
           } catch(Exception e) {
               e.printStackTrace();
           } finally {
   			if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
               if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
               if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
           }
   		return -2; // �����ͺ��̽� ����
   	}
   	
   	// ��й�ȣ ���� (modifyPasswordPro.jsp)���� ��й�ȣ ������ ���� �޼���
   	public int updatePassword(String userID, String userPassword) {
   		Connection conn = null;
        PreparedStatement pstmt = null;
		Encoder sha = Base64.getEncoder();
		try {
			conn = getConnection();
			
            String orgPass = userPassword;
            String shaPass = sha.encodeToString(orgPass.getBytes());
            String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
        	
            pstmt = conn.prepareStatement("update member set userPassword = ? where userID = ?");
            pstmt.setString(1, bcPass);
            pstmt.setString(2, userID);
            pstmt.executeUpdate();
            
            return 1; // ��й�ȣ ���� ����
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // ��й�ȣ ���� ����
   	}
   	
   	// ���̵� ���� (modifyIDPro.jsp)���� ���̵� ������ ���� �޼���
   	public int updateID(String userID, String newUserID) {
   		Connection conn = null;
   		PreparedStatement pstmt = null;
   		try {
   			conn = getConnection();
   			pstmt = conn.prepareStatement("update member set userID = ? where userID = ?");
			pstmt.setString(1, newUserID);
			pstmt.setString(2, userID);	
            pstmt.executeUpdate();
            return 1; // ���̵� ���� ����
   		} catch (Exception e) {
   			e.printStackTrace();
   		} finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // ���̵� ���� ����
   	}
   	// ���̵� ���� (modifyIDPro.jsp)���� ���̵� ���� �� ���� ���̵� ������ ���� �޼���
   	public int deleteMember(String userID){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        try {
			conn = getConnection();
			
			pstmt = conn.prepareStatement("delete from member where userID = ?");
            pstmt.setString(1, userID);
            pstmt.executeUpdate();
			return 1; // ���� ���̵� ���� ����
			
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // ���� ���̵� ���� ����
    }
   	
   	// ȸ�� Ż�� ó��(deletePro.jsp)���� ȸ�� ������ �����ϴ� �޼���
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
				if(BCrypt.checkpw(shaPass,rs.getString("userPassword"))){
					pstmt = conn.prepareStatement("delete from member where userID = ?");
                    pstmt.setString(1, userID);
                    pstmt.executeUpdate();
					return 1; // ȸ�� Ż��ó�� ����
				}else
					return 0; // ȸ�� Ż��ó�� ����
			}
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // �����ͺ��̽� ����
    }
    
    // ������ ����� Ȯ���ϴ� �޼���
    public int checkGrade(String userID) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        try {
			conn = getConnection();

            pstmt = conn.prepareStatement("select grade from member where userID = ?");
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            
			if(rs.next()){
				return rs.getInt("grade"); // ���ǿ� �ִ� ���̵��� ����� ������
			}
			
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // ��α��� ����(������ ���� ����)�� ���
    }
}
