package board;

public class GuildBoard_NoticeAndMeetingLogDataBean {
	
	private int writtenID; // �� ��ȣ
	private String title;
	private String userID;
	private String reg_Date;
	private String content;
	private byte available; // 1- ����, 0- ����
	private byte location; // 0- ��� ȸ�Ƿ�, 1- ��� ��������
	
	public int getWrittenID() {
		return writtenID;
	}
	public void setWrittenID(int writtenID) {
		this.writtenID = writtenID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getReg_Date() {
		return reg_Date;
	}
	public void setReg_Date(String reg_Date) {
		this.reg_Date = reg_Date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public byte getAvailable() {
		return available;
	}
	public void setAvailable(byte available) {
		this.available = available;
	}
	public byte getLocation() {
		return location;
	}
	public void setLocation(byte location) {
		this.location = location;
	}
	
	
}
