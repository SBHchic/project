package guild;

import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GuildMemberList {
	
	public static String getGuildMaster() {
		String url = "https://maplestory.nexon.com/Common/Guild?gid=25429&wid=45&orderby=1&page=1"; // url지정
		Document html = null;        // Document에는 페이지의 전체 소스가 저장됨
		
		try {
			Connection conn = Jsoup.connect(url);
			html = conn.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// select를 이용하여 원하는 태그를 선택함
		Elements element = html.select("table tbody tr td");
		
		Iterator<Element> ie2 = element.select("td").iterator();
        
		while (ie2.hasNext()) {

			if (ie2.next().text().equals("마스터")) { // 마스터 구별
				String source = ie2.next().text();
				String[] subresult = source.split(" ");
				return subresult[0];
			}
		}
		return "길드마스터가 없습니다.";
	}
	
	public static ArrayList<String> getGuildSubmasters() {
		String url = "https://maplestory.nexon.com/Common/Guild?gid=25429&wid=45&orderby=1&page=";
		Document html = null;
		ArrayList<String> list = new ArrayList<String>(20);
		
		for (int i = 1; i <= 2; i++) {
			try {
				Connection conn = Jsoup.connect(url+i);
				html = conn.get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Elements element = html.select("table tbody tr td");
			
			Iterator<Element> ie2 = element.select("td").iterator();
	        
			while (ie2.hasNext()) {

				if (ie2.next().text().equals("부마스터")) { // 부마스터 구별
					String source = ie2.next().text();
					String[] subresult = source.split(" ");
					list.add(subresult[0]);
				}
			}
		}
		return list;
	}
	
	public static ArrayList<String> getGuildMembers() {
		String url = "https://maplestory.nexon.com/Common/Guild?gid=25429&wid=45&orderby=1&page="; 
		Document html = null; 
		ArrayList<String> list = new ArrayList<String>(200);
		
		for (int i = 1; i <= 10; i++) {
			try {
				Connection conn = Jsoup.connect(url+i);
				html = conn.get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Elements element = html.select("table tbody tr td");
			
			Iterator<Element> ie2 = element.select("td").iterator();
	        
			while (ie2.hasNext()) {

				if (ie2.next().text().equals("길드원")) { // 길드원 구별
					String source = ie2.next().text();
					String[] subresult = source.split(" ");
					list.add(subresult[0]);
				}
			}
		}
		return list;
	}
}
