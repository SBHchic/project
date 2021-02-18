package starforce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.lang.Math;

public class NormalStarforceDBBean {

	// NormalStarforceDBBean ���� ��ü ���� <- �Ѱ��� ������ �����ؼ� ����
    private static NormalStarforceDBBean instance = new NormalStarforceDBBean();
    
    // NormalStarforceDBBean��ü�� �����ϴ� �޼���
    public static NormalStarforceDBBean getInstance() {
        return instance;
    }
    
    private NormalStarforceDBBean() {}
    
    // Ŀ�ؼ� Ǯ���� Ŀ�ؼ� ��ü�� ���� �޼���
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/icefortress");
        return ds.getConnection();
    }
    
    // �븻 ������ ��ȭó�� (normalStarforcePro.jsp)���� ����ϴ� �� ���ڵ� �߰� �޼���
    public void insert(NormalStarforceDataBean normalStarforce){
    	Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = getConnection();
        	
            pstmt = conn.prepareStatement("insert into normalStarforce values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, normalStarforce.getNumberOfItems());
            pstmt.setInt(2, normalStarforce.getLevel());
            pstmt.setInt(3, normalStarforce.getFromStarforce());
            pstmt.setInt(4, normalStarforce.getStarforce());
            pstmt.setBoolean(5, normalStarforce.isIgnoreDestroy());
            pstmt.setBoolean(6, normalStarforce.isDiscountPCRoom());
            pstmt.setByte(7, normalStarforce.getSuccededCatch());
            pstmt.setByte(8, normalStarforce.getMapleEvent());
            pstmt.setByte(9, normalStarforce.getDiscountMVPGrade());
            pstmt.setBoolean(10, normalStarforce.isToad());
            pstmt.setInt(11, normalStarforce.getToadToStarforce());
            pstmt.setBoolean(12, normalStarforce.isToadIgnoreDestroy());
            pstmt.setLong(13, normalStarforce.getMinSumUpgradePrice());
            pstmt.setLong(14, normalStarforce.getMaxSumUpgradePrice());
            pstmt.setLong(15, normalStarforce.getAverageSumUpgradePrice());
            pstmt.setInt(16, normalStarforce.getMinUpgradeCount());
            pstmt.setInt(17, normalStarforce.getMaxUpgradeCount());
            pstmt.setLong(18, normalStarforce.getAverageUpgradeCount());
            pstmt.setInt(19, normalStarforce.getMinDestroyCount());
            pstmt.setInt(20, normalStarforce.getMaxDestroyCount());
            pstmt.setLong(21, normalStarforce.getAverageDestroyCount());
            pstmt.setInt(22, normalStarforce.getMinChanceTimeCount());
            pstmt.setInt(23, normalStarforce.getMaxChanceTimeCount());
            pstmt.setLong(24, normalStarforce.getAverageChanceTimeCount());
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
    }
    
    // �븻 ������ ��ȭó�� (normalStarforcePro.jsp)���� �� ���ڵ带 �߰��ϱ� �� Ȯ�� �۾�
    public int check(NormalStarforceDataBean normalStarforce){
    	Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs= null;
		try {
			conn = getConnection();
        	
            pstmt = conn.prepareStatement(
            	"select minSumUpgradePrice from normalStarforce where level = ? and fromStarforce = ? and starforce = ? and ignoreDestroy = ? and discountPCRoom = ? and succededCatch = ? and mapleEvent = ? and discountMVPGrade = ? and toad = ? and toadToStarforce = ? and toadIgnoreDestroy = ?");
            pstmt.setInt(1, normalStarforce.getLevel());
            pstmt.setInt(2, normalStarforce.getFromStarforce());
            pstmt.setInt(3, normalStarforce.getStarforce());
            pstmt.setBoolean(4, normalStarforce.isIgnoreDestroy());
            pstmt.setBoolean(5, normalStarforce.isDiscountPCRoom());
            pstmt.setByte(6, normalStarforce.getSuccededCatch());
            pstmt.setByte(7, normalStarforce.getMapleEvent());
            pstmt.setByte(8, normalStarforce.getDiscountMVPGrade());
            pstmt.setBoolean(9, normalStarforce.isToad());
            pstmt.setInt(10, normalStarforce.getToadToStarforce());
            pstmt.setBoolean(11, normalStarforce.isToadIgnoreDestroy());
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
    
    
    // �븻 ������ ��ȭó�� (normalStarforcePro.jsp)���� ���� ���ڵ尡 ������ ����
   	public int update(NormalStarforceDataBean normalStarforce) {
   		
   		Connection conn = null;
   		PreparedStatement pstmt = null;
   		ResultSet rs = null;
   		try {
   			conn = getConnection();
   			pstmt = conn.prepareStatement(
   	            "select * from normalStarforce where level = ? and fromStarforce = ? and starforce = ? and ignoreDestroy = ? and discountPCRoom = ? and succededCatch = ? and mapleEvent = ? and discountMVPGrade = ? and toad = ? and toadToStarforce = ? and toadIgnoreDestroy = ?");
   			pstmt.setInt(1, normalStarforce.getLevel());
            pstmt.setInt(2, normalStarforce.getFromStarforce());
            pstmt.setInt(3, normalStarforce.getStarforce());
            pstmt.setBoolean(4, normalStarforce.isIgnoreDestroy());
            pstmt.setBoolean(5, normalStarforce.isDiscountPCRoom());
            pstmt.setByte(6, normalStarforce.getSuccededCatch());
            pstmt.setByte(7, normalStarforce.getMapleEvent());
            pstmt.setByte(8, normalStarforce.getDiscountMVPGrade());
            pstmt.setBoolean(9, normalStarforce.isToad());
            pstmt.setInt(10, normalStarforce.getToadToStarforce());
            pstmt.setBoolean(11, normalStarforce.isToadIgnoreDestroy());
            rs= pstmt.executeQuery();
   			
            if (rs.next()) {
            	pstmt = conn.prepareStatement(
           	        "update normalStarforce set numberOfItems = ?,minSumUpgradePrice = ?,maxSumUpgradePrice = ?,averageSumUpgradePrice = ?,minUpgradeCount = ?,maxUpgradeCount = ?,averageUpgradeCount = ?,"
           	        + "minDestroyCount = ?,maxDestroyCount = ?,averageDestroyCount = ?,minChanceTimeCount = ?,maxChanceTimeCount = ?,averageChanceTimeCount = ? "
           	        + "where level = ? and fromStarforce = ? and starforce = ? and ignoreDestroy = ? and discountPCRoom = ? and succededCatch = ? and mapleEvent = ? and discountMVPGrade = ? and toad = ? and toadToStarforce = ? and toadIgnoreDestroy = ?");
            	pstmt.setLong(1, normalStarforce.getNumberOfItems()+rs.getLong("numberOfItems"));
            	double subTop1 = normalStarforce.getNumberOfItems();
            	double subTop2 = rs.getLong("numberOfItems");
            	double subUnder = normalStarforce.getNumberOfItems()+rs.getLong("numberOfItems");
            	pstmt.setLong(2, Math.round(normalStarforce.getMinSumUpgradePrice()*(subTop1/subUnder)+rs.getLong("minSumUpgradePrice")*(subTop2/subUnder)));
                pstmt.setLong(3, Math.round(normalStarforce.getMaxSumUpgradePrice()*(subTop1/subUnder)+rs.getLong("maxSumUpgradePrice")*(subTop2/subUnder)));
                pstmt.setLong(4, Math.round(normalStarforce.getAverageSumUpgradePrice()*(subTop1/subUnder)+rs.getLong("averageSumUpgradePrice")*(subTop2/subUnder)));
                pstmt.setInt(5, (int) Math.round(normalStarforce.getMinUpgradeCount()*(subTop1/subUnder)+rs.getInt("minUpgradeCount")*(subTop2/subUnder)));
                pstmt.setInt(6, (int) Math.round(normalStarforce.getMaxUpgradeCount()*(subTop1/subUnder)+rs.getInt("maxUpgradeCount")*(subTop2/subUnder)));
                pstmt.setLong(7, Math.round(normalStarforce.getAverageUpgradeCount()*(subTop1/subUnder)+rs.getLong("averageUpgradeCount")*(subTop2/subUnder)));
                pstmt.setInt(8, (int) Math.round(normalStarforce.getMinDestroyCount()*(subTop1/subUnder)+rs.getInt("minDestroyCount")*(subTop2/subUnder)));
                pstmt.setInt(9, (int) Math.round(normalStarforce.getMaxDestroyCount()*(subTop1/subUnder)+rs.getInt("maxDestroyCount")*(subTop2/subUnder)));
                pstmt.setLong(10, Math.round(normalStarforce.getAverageDestroyCount()*(subTop1/subUnder)+rs.getLong("averageDestroyCount")*(subTop2/subUnder)));
                pstmt.setInt(11, (int) Math.round(normalStarforce.getMinChanceTimeCount()*(subTop1/subUnder)+rs.getInt("minChanceTimeCount")*(subTop2/subUnder)));
                pstmt.setInt(12, (int) Math.round(normalStarforce.getMaxChanceTimeCount()*(subTop1/subUnder)+rs.getInt("maxChanceTimeCount")*(subTop2/subUnder)));
                pstmt.setLong(13, Math.round(normalStarforce.getAverageChanceTimeCount()*(subTop1/subUnder)+rs.getLong("averageChanceTimeCount")*(subTop2/subUnder)));
                pstmt.setInt(14, normalStarforce.getLevel());
                pstmt.setInt(15, normalStarforce.getFromStarforce());
                pstmt.setInt(16, normalStarforce.getStarforce());
                pstmt.setBoolean(17, normalStarforce.isIgnoreDestroy());
                pstmt.setBoolean(18, normalStarforce.isDiscountPCRoom());
                pstmt.setByte(19, normalStarforce.getSuccededCatch());
                pstmt.setByte(20, normalStarforce.getMapleEvent());
                pstmt.setByte(21, normalStarforce.getDiscountMVPGrade());
                pstmt.setBoolean(22, normalStarforce.isToad());
                pstmt.setInt(23, normalStarforce.getToadToStarforce());
                pstmt.setBoolean(24, normalStarforce.isToadIgnoreDestroy());
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
   	
   	// �븻 ������ ��ȭ ���� ��� ó�� (normalStarforcePro_cumulativeResult.jsp)���� ���� ���ڵ尡 ������ ���� (������ �������� �������� ������ ����)
   	public NormalStarforceDataBean select(NormalStarforceDataBean normalStarforce) {
   		
   		Connection conn = null;
   		PreparedStatement pstmt = null;
   		ResultSet rs = null;
   		NormalStarforceDataBean existingResult = null;
   		try {
   			conn = getConnection();
   			pstmt = conn.prepareStatement(
   	            "select * from normalStarforce where level = ? and fromStarforce = ? and starforce = ? and ignoreDestroy = ? and discountPCRoom = ? and succededCatch = ? and mapleEvent = ? and discountMVPGrade = ? and toad = ? and toadToStarforce = ? and toadIgnoreDestroy = ?");
   			pstmt.setInt(1, normalStarforce.getLevel());
            pstmt.setInt(2, normalStarforce.getFromStarforce());
            pstmt.setInt(3, normalStarforce.getStarforce());
            pstmt.setBoolean(4, normalStarforce.isIgnoreDestroy());
            pstmt.setBoolean(5, normalStarforce.isDiscountPCRoom());
            pstmt.setByte(6, normalStarforce.getSuccededCatch());
            pstmt.setByte(7, normalStarforce.getMapleEvent());
            pstmt.setByte(8, normalStarforce.getDiscountMVPGrade());
            pstmt.setBoolean(9, normalStarforce.isToad());
            pstmt.setInt(10, normalStarforce.getToadToStarforce());
            pstmt.setBoolean(11, normalStarforce.isToadIgnoreDestroy());
            rs= pstmt.executeQuery();
   			
            if (rs.next()) {
            	existingResult = new NormalStarforceDataBean();
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
   	
   	public static String condition (NormalStarforceDataBean normalStarforce) {
   		String condition = "";
   		condition += "==== " + normalStarforce.getLevel() + "�� �븻�������� " + normalStarforce.getNumberOfItems() + "�� " + normalStarforce.getFromStarforce() + "�� -> " + normalStarforce.getStarforce() + "�������� ��ȭ ==== <br>";
    	condition += "�߰� ���� - ��Ÿĳġ " + Item.starCatchToString(normalStarforce.getSuccededCatch()) + ", �ı����� : " + Item.booleanToString(normalStarforce.isIgnoreDestroy()) + "<br>";
    	condition += "��忩�� : " + Item.booleanToString(normalStarforce.isToad()) + "<br>";
    	if (normalStarforce.isToad()) {
    		condition += " ����� 0�� -> " + normalStarforce.getToadToStarforce() + "��, ����� �ı����� : " + Item.booleanToString(normalStarforce.isToadIgnoreDestroy()) + "<br>";
    	}
    	condition += "������ �̺�Ʈ ���� : " + Item.mapleEventToString(normalStarforce.getMapleEvent()) + "<br><br>";
    	return condition;
   	}
   	
   	public static String result(NormalStarforceDataBean normalStarforce) {
   		String result = "";
   		result += normalStarforce.getNumberOfItems() + "���� ������ �� ��Ÿ���� ��ȭ �ּҺ���� " + normalStarforce.getMinSumUpgradePrice() + "00�޼� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� ������ �� ��Ÿ���� ��ȭ �ִ����� " + normalStarforce.getMaxSumUpgradePrice() + "00�޼� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� �������� ������� ��Ÿ���� ��ȭ ����� " + normalStarforce.getAverageSumUpgradePrice() + "00�޼� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� ������ �� ���� ���� ��ȭ Ƚ���� " + normalStarforce.getMinUpgradeCount() + "�� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� ������ �� ���� ���� ��ȭ Ƚ���� " + normalStarforce.getMaxUpgradeCount() + "�� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� �������� ������� ��ȭ Ƚ���� " + normalStarforce.getAverageUpgradeCount() + "�� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� ������ �� ���� ���� �ı� Ƚ���� " + normalStarforce.getMinDestroyCount() + "�� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� ������ �� ���� ���� �ı� Ƚ���� " + normalStarforce.getMaxDestroyCount() + "�� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� �������� ������� �ı� Ƚ���� " + normalStarforce.getAverageDestroyCount() + "�� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� ������ �� ���� ���� ����Ÿ�� Ƚ���� " + normalStarforce.getMinChanceTimeCount() + "�� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� ������ �� ���� ���� ����Ÿ�� Ƚ���� " + normalStarforce.getMaxChanceTimeCount() + "�� �Դϴ�. <br>";
   		result += normalStarforce.getNumberOfItems() + "���� �������� ������� ����Ÿ�� Ƚ���� " + normalStarforce.getAverageChanceTimeCount() + "�� �Դϴ�.";
   		return result;
   	}
}
