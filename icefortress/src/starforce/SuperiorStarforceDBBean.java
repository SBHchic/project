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

	// SuperiorStarforceDBBean 전역 객체 생성 <- 한개의 객제만 생성해서 공유
    private static SuperiorStarforceDBBean instance = new SuperiorStarforceDBBean();
    
    // SuperiorStarforceDBBean객체를 리턴하는 메서드
    public static SuperiorStarforceDBBean getInstance() {
        return instance;
    }
    
    private SuperiorStarforceDBBean() {}
    
    // 커넥션 풀에서 커넥션 객체를 얻어내는 메서드
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/icefortress");
        return ds.getConnection();
    }
    
    // 슈페리얼 아이템 강화처리 (superiorStarforcePro.jsp)에서 사용하는 새 레코드 추가 메서드
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
    
    // 슈페리얼 아이템 강화처리 (superiorStarforcePro.jsp)에서 새 레코드를 추가하기 전 확인 작업
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
    
    
    // 슈페리얼 아이템 강화처리 (superiorStarforcePro.jsp)에서 기존 레코드가 있으면 수행
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
   	
   	// 슈페리얼 아이템 강화 누적 결과 처리 (superiorStarforcePro_cumulativeResult.jsp)에서 기존 레코드가 있으면 수행 (나머지 정보들은 페이지가 가지고 있음)
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
		return existingResult; // 기존 데이터 가져옴
   	}
   	
   	public static String condition(SuperiorStarforceDataBean superiorStarforce) {
   		String condition = "";
    	condition += "==== " + superiorStarforce.getLevel() + "제 슈페리얼 아이템의 " + superiorStarforce.getNumberOfItems() + "개 " + superiorStarforce.getFromStarforce() + "성 -> " + superiorStarforce.getStarforce() + "성까지의 강화 ==== <br>";
    	condition += "추가 설명 - 스타캐치 " + Item.starCatchToString(superiorStarforce.getSuccededCatch()) +"<br><br>";
    	return condition;
   	}
   	
   	public static String result(SuperiorStarforceDataBean superiorStarforce) {
   		String result = "";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템 중 스타포스 강화 최소비용은 " + superiorStarforce.getMinSumUpgradePrice() + "00메소 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템 중 스타포스 강화 최대비용은 " + superiorStarforce.getMaxSumUpgradePrice() + "00메소 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템의 평균적인 스타포스 강화 비용은 " + superiorStarforce.getAverageSumUpgradePrice() + "00메소 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템 중 가장 적은 강화 횟수는 " + superiorStarforce.getMinUpgradeCount() + "번 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템 중 가장 많은 강화 횟수는 " + superiorStarforce.getMaxUpgradeCount() + "번 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템의 평균적인 강화 횟수는 " + superiorStarforce.getAverageUpgradeCount() + "번 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템 중 가장 적은 파괴 횟수는 " + superiorStarforce.getMinDestroyCount() + "번 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템 중 가장 많은 파괴 횟수는 " + superiorStarforce.getMaxDestroyCount() + "번 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템의 평균적인 파괴 횟수는 " + superiorStarforce.getAverageDestroyCount() + "번 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템 중 가장 적은 찬스타임 횟수는 " + superiorStarforce.getMinChanceTimeCount() + "번 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템 중 가장 많은 찬스타임 횟수는 " + superiorStarforce.getMaxChanceTimeCount() + "번 입니다. <br>";
   		result += superiorStarforce.getNumberOfItems() + "개의 아이템의 평균적인 찬스타임 횟수는 " + superiorStarforce.getAverageChanceTimeCount() + "번 입니다.";
   		return result;
   	}
}
