package starforce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.lang.Math;

public class SuperiorStarforceDBBean {

	// SuperiorStarforceDBBean ���� ��ü ���� <- �Ѱ��� ������ �����ؼ� ����
    private static SuperiorStarforceDBBean instance = new SuperiorStarforceDBBean();
    
    // SuperiorStarforceDBBean��ü�� �����ϴ� �޼���
    public static SuperiorStarforceDBBean getInstance() {
        return instance;
    }
    
    private SuperiorStarforceDBBean() {}
    
    // Ŀ�ؼ� Ǯ���� Ŀ�ؼ� ��ü�� ���� �޼���
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/icefortress");
        return ds.getConnection();
    }
    
    // ���丮�� ������ ��ȭó�� (superiorStarforcePro.jsp)���� ����ϴ� �� ���ڵ� �߰� �޼���
    public void insert(SuperiorStarforceDataBean superiorStarforce){
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
            pstmt = conn.prepareStatement("insert into superiorStarforce values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, superiorStarforce.getNumberOfItems());
            pstmt.setInt(2, superiorStarforce.getLevel());
            pstmt.setInt(3, superiorStarforce.getFromStarforce());
            pstmt.setInt(4, superiorStarforce.getStarforce());
            pstmt.setByte(5, superiorStarforce.getSuccededCatch());
            pstmt.setLong(6, superiorStarforce.getMinSumUpgradePrice());
            pstmt.setLong(7, superiorStarforce.getMaxSumUpgradePrice());
            pstmt.setLong(8, superiorStarforce.getAverageSumUpgradePrice());
            pstmt.setInt(9, superiorStarforce.getMinUpgradeCount());
            pstmt.setInt(10, superiorStarforce.getMaxUpgradeCount());
            pstmt.setLong(11, superiorStarforce.getAverageUpgradeCount());
            pstmt.setInt(12, superiorStarforce.getMinDestroyCount());
            pstmt.setInt(13, superiorStarforce.getMaxDestroyCount());
            pstmt.setLong(14, superiorStarforce.getAverageDestroyCount());
            pstmt.setInt(15, superiorStarforce.getMinChanceTimeCount());
            pstmt.setInt(16, superiorStarforce.getMaxChanceTimeCount());
            pstmt.setLong(17, superiorStarforce.getAverageChanceTimeCount());
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
    }
    
    // ���丮�� ������ ��ȭó�� (superiorStarforcePro.jsp)���� �� ���ڵ带 �߰��ϱ� �� Ȯ�� �۾�
    public int check(SuperiorStarforceDataBean superiorStarforce){
    	Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs= null;
		try {
			conn = getConnection();
        	
            pstmt = conn.prepareStatement(
            	"select minSumUpgradePrice from superiorStarforce where level = ? and fromStarforce = ? and starforce = ? and succededCatch = ?");
            pstmt.setInt(1, superiorStarforce.getLevel());
            pstmt.setInt(2, superiorStarforce.getFromStarforce());
            pstmt.setInt(3, superiorStarforce.getStarforce());
            pstmt.setByte(4, superiorStarforce.getSuccededCatch());
            rs= pstmt.executeQuery();

			if(rs.next()){ // �ش� ���ڵ尡 ������ ����
				return 1;
			} else { // �ش� ���ڵ尡 ������ ����
				return -1;
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
    
    
    // ���丮�� ������ ��ȭó�� (superiorStarforcePro.jsp)���� ���� ���ڵ尡 ������ ����
   	public int update(SuperiorStarforceDataBean superiorStarforce) {
   		
   		Connection conn = null;
   		PreparedStatement pstmt = null;
   		ResultSet rs = null;
   		try {
   			conn = getConnection();
   			pstmt = conn.prepareStatement(
   	            "select * from superiorStarforce where level = ? and fromStarforce = ? and starforce = ? and succededCatch = ?");
   			pstmt.setInt(1, superiorStarforce.getLevel());
            pstmt.setInt(2, superiorStarforce.getFromStarforce());
            pstmt.setInt(3, superiorStarforce.getStarforce());
            pstmt.setByte(4, superiorStarforce.getSuccededCatch());
            rs= pstmt.executeQuery();
   			
            if (rs.next()) {
            	pstmt = conn.prepareStatement(
           	        "update superiorStarforce set numberOfItems = ?,minSumUpgradePrice = ?,maxSumUpgradePrice = ?,averageSumUpgradePrice = ?,"
           	        + "minUpgradeCount = ?,maxUpgradeCount = ?,averageUpgradeCount = ?,"
           	        + "minDestroyCount = ?,maxDestroyCount = ?,averageDestroyCount = ?,"
           	        + "minChanceTimeCount = ?,maxChanceTimeCount = ?,averageChanceTimeCount = ? "
           	        + "where level = ? and fromStarforce = ? and starforce = ? and succededCatch = ?");
            	pstmt.setLong(1, superiorStarforce.getNumberOfItems()+rs.getLong("numberOfItems"));
            	double subTop1 = superiorStarforce.getNumberOfItems();
            	double subTop2 = rs.getLong("numberOfItems");
            	double subUnder = (superiorStarforce.getNumberOfItems()+rs.getLong("numberOfItems"));
            	pstmt.setLong(2, Math.round(superiorStarforce.getMinSumUpgradePrice()*(subTop1/subUnder)+rs.getLong("minSumUpgradePrice")*(subTop2/subUnder)));
                pstmt.setLong(3, Math.round(superiorStarforce.getMaxSumUpgradePrice()*(subTop1/subUnder)+rs.getLong("maxSumUpgradePrice")*(subTop2/subUnder)));
                pstmt.setLong(4, Math.round(superiorStarforce.getAverageSumUpgradePrice()*(subTop1/subUnder)+rs.getLong("averageSumUpgradePrice")*(subTop2/subUnder)));
                pstmt.setInt(5, (int) Math.round(superiorStarforce.getMinUpgradeCount()*(subTop1/subUnder)+rs.getInt("minUpgradeCount")*(subTop2/subUnder)));
                pstmt.setInt(6, (int) Math.round(superiorStarforce.getMaxUpgradeCount()*(subTop1/subUnder)+rs.getInt("maxUpgradeCount")*(subTop2/subUnder)));
                pstmt.setLong(7, Math.round(superiorStarforce.getAverageUpgradeCount()*(subTop1/subUnder)+rs.getLong("averageUpgradeCount")*(subTop2/subUnder)));
                pstmt.setInt(8, (int) Math.round(superiorStarforce.getMinDestroyCount()*(subTop1/subUnder)+rs.getInt("minDestroyCount")*(subTop2/subUnder)));
                pstmt.setInt(9, (int) Math.round(superiorStarforce.getMaxDestroyCount()*(subTop1/subUnder)+rs.getInt("maxDestroyCount")*(subTop2/subUnder)));
                pstmt.setLong(10, Math.round(superiorStarforce.getAverageDestroyCount()*(subTop1/subUnder)+rs.getLong("averageDestroyCount")*(subTop2/subUnder)));
                pstmt.setInt(11, (int) Math.round(superiorStarforce.getMinChanceTimeCount()*(subTop1/subUnder)+rs.getInt("minChanceTimeCount")*(subTop2/subUnder)));
                pstmt.setInt(12, (int) Math.round(superiorStarforce.getMaxChanceTimeCount()*(subTop1/subUnder)+rs.getInt("maxChanceTimeCount")*(subTop2/subUnder)));
                pstmt.setLong(13, Math.round(superiorStarforce.getAverageChanceTimeCount()*(subTop1/subUnder)+rs.getLong("averageChanceTimeCount")*(subTop2/subUnder)));
                pstmt.setInt(14, superiorStarforce.getLevel());
                pstmt.setInt(15, superiorStarforce.getFromStarforce());
                pstmt.setInt(16, superiorStarforce.getStarforce());
                pstmt.setByte(17, superiorStarforce.getSuccededCatch());
                pstmt.executeUpdate();
                return 1; // ������Ʈ ����
            }
   		} catch (Exception e) {
   			e.printStackTrace();
   		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // ������Ʈ ����
   	}
   	
   	// ���丮�� ������ ��ȭ ���� ��� ó�� (superiorStarforcePro_cumulativeResult.jsp)���� ���� ���ڵ尡 ������ ���� (������ �������� �������� ������ ����)
   	public SuperiorStarforceDataBean select(SuperiorStarforceDataBean superiorStarforce) {
   		
   		Connection conn = null;
   		PreparedStatement pstmt = null;
   		ResultSet rs = null;
   		SuperiorStarforceDataBean existingResult = null;
   		try {
   			conn = getConnection();
   			pstmt = conn.prepareStatement(
   	            "select * from superiorStarforce where level = ? and fromStarforce = ? and starforce = ? and succededCatch = ?");
   			pstmt.setInt(1, superiorStarforce.getLevel());
            pstmt.setInt(2, superiorStarforce.getFromStarforce());
            pstmt.setInt(3, superiorStarforce.getStarforce());
            pstmt.setByte(4, superiorStarforce.getSuccededCatch());
            rs= pstmt.executeQuery();
   			
            if (rs.next()) {
            	existingResult = new SuperiorStarforceDataBean();
            	existingResult.setNumberOfItems(rs.getLong("numberOfItems"));
            	existingResult.setMinSumUpgradePrice(rs.getLong("minSumUpgradePrice"));
            	existingResult.setMaxSumUpgradePrice(rs.getLong("maxSumUpgradePrice"));
            	existingResult.setAverageSumUpgradePrice(rs.getLong("averageSumUpgradePrice"));
            	existingResult.setMinUpgradeCount(rs.getInt("minUpgradeCount"));
            	existingResult.setMaxUpgradeCount(rs.getInt("maxUpgradeCount"));
            	existingResult.setAverageUpgradeCount(rs.getLong("averageUpgradeCount"));
            	existingResult.setMinDestroyCount(rs.getInt("minDestroyCount"));
            	existingResult.setMaxDestroyCount(rs.getInt("maxDestroyCount"));
            	existingResult.setAverageDestroyCount(rs.getLong("averageDestroyCount"));
            	existingResult.setMinChanceTimeCount(rs.getInt("minChanceTimeCount"));
            	existingResult.setMaxChanceTimeCount(rs.getInt("maxChanceTimeCount"));
            	existingResult.setAverageChanceTimeCount(rs.getLong("averageChanceTimeCount"));
            }
   		} catch (Exception e) {
   			e.printStackTrace();
   		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return existingResult; // ���� ������ ������
   	}
   	
   	public static String condition(SuperiorStarforceDataBean superiorStarforce) {
   		String condition = "";
    	condition += "==== " + superiorStarforce.getLevel() + "�� ���丮�� �������� " + superiorStarforce.getNumberOfItems() + "�� " + superiorStarforce.getFromStarforce() + "�� -> " + superiorStarforce.getStarforce() + "�������� ��ȭ ==== <br>";
    	condition += "�߰� ���� - ��Ÿĳġ " + Item.starCatchToString(superiorStarforce.getSuccededCatch()) +"<br><br>";
    	return condition;
   	}
   	
   	public static String result(SuperiorStarforceDataBean superiorStarforce) {
   		String result = "";
   		result += superiorStarforce.getNumberOfItems() + "���� ������ �� ��Ÿ���� ��ȭ �ּҺ���� " + superiorStarforce.getMinSumUpgradePrice() + "00�޼� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� ������ �� ��Ÿ���� ��ȭ �ִ����� " + superiorStarforce.getMaxSumUpgradePrice() + "00�޼� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� �������� ������� ��Ÿ���� ��ȭ ����� " + superiorStarforce.getAverageSumUpgradePrice() + "00�޼� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� ������ �� ���� ���� ��ȭ Ƚ���� " + superiorStarforce.getMinUpgradeCount() + "�� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� ������ �� ���� ���� ��ȭ Ƚ���� " + superiorStarforce.getMaxUpgradeCount() + "�� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� �������� ������� ��ȭ Ƚ���� " + superiorStarforce.getAverageUpgradeCount() + "�� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� ������ �� ���� ���� �ı� Ƚ���� " + superiorStarforce.getMinDestroyCount() + "�� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� ������ �� ���� ���� �ı� Ƚ���� " + superiorStarforce.getMaxDestroyCount() + "�� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� �������� ������� �ı� Ƚ���� " + superiorStarforce.getAverageDestroyCount() + "�� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� ������ �� ���� ���� ����Ÿ�� Ƚ���� " + superiorStarforce.getMinChanceTimeCount() + "�� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� ������ �� ���� ���� ����Ÿ�� Ƚ���� " + superiorStarforce.getMaxChanceTimeCount() + "�� �Դϴ�. <br>";
   		result += superiorStarforce.getNumberOfItems() + "���� �������� ������� ����Ÿ�� Ƚ���� " + superiorStarforce.getAverageChanceTimeCount() + "�� �Դϴ�.";
   		return result;
   	}
}
