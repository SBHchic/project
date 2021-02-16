package board;

public class GuildBoardDataBean {

	private int writtenID; // 글 번호
	private int replyID; // 답글 번호
	private int commentID; // 댓글 번호
	private int commentID_Re; // 대댓글 번호
	private String title;
	private String userID;
	private String reg_Date;
	private String content;
	private byte available; // 1- 존재, 0- 삭제
	private String deleteReg_Date; // 삭제 시간
	private byte notice;
	
	public int getWrittenID() {
		return writtenID;
	}
	public void setWrittenID(int writtenID) {
		this.writtenID = writtenID;
	}
	public int getReplyID() {
		return replyID;
	}
	public void setReplyID(int replyID) {
		this.replyID = replyID;
	}
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public int getCommentID_Re() {
		return commentID_Re;
	}
	public void setCommentID_Re(int commentID_Re) {
		this.commentID_Re = commentID_Re;
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
	public String getDeleteReg_Date() {
		return deleteReg_Date;
	}
	public void setDeleteReg_Date(String deleteReg_Date) {
		this.deleteReg_Date = deleteReg_Date;
	}
	public byte getNotice() {
		return notice;
	}
	public void setNotice(byte notice) {
		this.notice = notice;
	}
	
}
