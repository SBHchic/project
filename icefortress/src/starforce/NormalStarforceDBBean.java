package starforce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class NormalStarforceDBBean {

	// NormalStarforceDBBean 전역 객체 생성 <- 한개의 객제만 생성해서 공유
    private static NormalStarforceDBBean instance = new NormalStarforceDBBean();
    
    // NormalStarforceDBBean객체를 리턴하는 메서드
    public static NormalStarforceDBBean getInstance() {
        return instance;
    }
    
    private NormalStarforceDBBean() {}
    
    // 커넥션 풀에서 커넥션 객체를 얻어내는 메서드
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/icefortress");
        return ds.getConnection();
    }
    
    // 노말 아이템 강화처리 (normalStarforcePro.jsp)에서 사용하는 새 레코드 추가 메서드
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
    
    // 노말 아이템 강화처리 (normalStarforcePro.jsp)에서 새 레코드를 추가하기 전 확인 작업
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

			if(rs.next()){ // 해당 레코드가 있으면 수행
				return 1;
			} else { // 해당 레코드가 없으면 수행
				return -1;
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
    
    
    // 노말 아이템 강화처리 (normalStarforcePro.jsp)에서 기존 레코드가 있으면 수행
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
           	        "update normalStarforce set numberOfItems = ?,minSumUpgradePrice = ?,maxSumUpgradePrice = ?,averageSumUpgradePrice = ?,"
           	        + "minUpgradeCount = ?,maxUpgradeCount = ?,averageUpgradeCount = ?,"
           	        + "minDestroyCount = ?,maxDestroyCount = ?,averageDestroyCount = ?,"
           	        + "minChanceTimeCount = ?,maxChanceTimeCount = ?,averageChanceTimeCount = ? "
           	        + "where level = ? and fromStarforce = ? and starforce = ? and ignoreDestroy = ? and discountPCRoom = ? and succededCatch = ? and mapleEvent = ? and discountMVPGrade = ? and toad = ? and toadToStarforce = ? and toadIgnoreDestroy = ?");
            	pstmt.setLong(1, normalStarforce.getNumberOfItems()+rs.getLong("numberOfItems"));
            	double subTop1 = normalStarforce.getNumberOfItems();
            	double subTop2 = rs.getLong("numberOfItems");
            	double subUnder = (normalStarforce.getNumberOfItems()+rs.getLong("numberOfItems"));
            	pstmt.setLong(2, (long)(normalStarforce.getMinSumUpgradePrice()*(subTop1/subUnder)+rs.getLong("minSumUpgradePrice")*(subTop2/subUnder)));
                pstmt.setLong(3, (long)(normalStarforce.getMaxSumUpgradePrice()*(subTop1/subUnder)+rs.getLong("maxSumUpgradePrice")*(subTop2/subUnder)));
                pstmt.setLong(4, (long)(normalStarforce.getAverageSumUpgradePrice()*(subTop1/subUnder)+rs.getLong("averageSumUpgradePrice")*(subTop2/subUnder)));
                pstmt.setInt(5, (int)(normalStarforce.getMinUpgradeCount()*(subTop1/subUnder)+rs.getInt("minUpgradeCount")*(subTop2/subUnder)));
                pstmt.setInt(6, (int)(normalStarforce.getMaxUpgradeCount()*(subTop1/subUnder)+rs.getInt("maxUpgradeCount")*(subTop2/subUnder)));
                pstmt.setLong(7, (long)(normalStarforce.getAverageUpgradeCount()*(subTop1/subUnder)+rs.getLong("averageUpgradeCount")*(subTop2/subUnder)));
                pstmt.setInt(8, (int)(normalStarforce.getMinDestroyCount()*(subTop1/subUnder)+rs.getInt("minDestroyCount")*(subTop2/subUnder)));
                pstmt.setInt(9, (int)(normalStarforce.getMaxDestroyCount()*(subTop1/subUnder)+rs.getInt("maxDestroyCount")*(subTop2/subUnder)));
                pstmt.setLong(10, (long)(normalStarforce.getAverageDestroyCount()*(subTop1/subUnder)+rs.getLong("averageDestroyCount")*(subTop2/subUnder)));
                pstmt.setInt(11, (int)(normalStarforce.getMinChanceTimeCount()*(subTop1/subUnder)+rs.getInt("minChanceTimeCount")*(subTop2/subUnder)));
                pstmt.setInt(12, (int)(normalStarforce.getMaxChanceTimeCount()*(subTop1/subUnder)+rs.getInt("maxChanceTimeCount")*(subTop2/subUnder)));
                pstmt.setLong(13, (long)(normalStarforce.getAverageChanceTimeCount()*(subTop1/subUnder)+rs.getLong("averageChanceTimeCount")*(subTop2/subUnder)));
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
                return 1; // 업데이트 성공
            }
   		} catch (Exception e) {
   			e.printStackTrace();
   		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
		return -1; // 업데이트 실패
   	}
   	
   	// 노말 아이템 강화 누적 결과 처리 (normalStarforcePro_cumulativeResult.jsp)에서 기존 레코드가 있으면 수행 (나머지 정보들은 페이지가 가지고 있음)
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
		return existingResult; // 기존 데이터 가져옴
   	}
}
