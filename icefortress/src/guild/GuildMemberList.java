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
		String url = "https://maplestory.nexon.com/Common/Guild?gid=25429&wid=45&orderby=1&page=1"; // url����
		Document html = null;        // Document���� �������� ��ü �ҽ��� �����
		
		try {
			Connection conn = Jsoup.connect(url);
			html = conn.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// select�� �̿��Ͽ� ���ϴ� �±׸� ������
		Elements element = html.select("table tbody tr td");
		
		Iterator<Element> ie2 = element.select("td").iterator();
        
		while (ie2.hasNext()) {

			if (ie2.next().text().equals("������")) { // ������ ����
				String source = ie2.next().text();
				String[] subresult = source.split(" ");
				return subresult[0];
			}
		}
		return "��帶���Ͱ� �����ϴ�.";
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

				if (ie2.next().text().equals("�θ�����")) { // �θ����� ����
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

				if (ie2.next().text().equals("����")) { // ���� ����
					String source = ie2.next().text();
					String[] subresult = source.split(" ");
					list.add(subresult[0]);
				}
			}
		}
		return list;
	}
}
