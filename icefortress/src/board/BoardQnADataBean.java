package board;

public class BoardQnADataBean {
	
	private int boardQnA_ID;
	private int boardQnA_ReplyID; // ��� ��ȣ
	private int boardQnA_CommentID; // ��� ��ȣ
	private String boardQnA_Title;
	private String userID;
	private String boardQnA_Reg_Date;
	private String boardQnA_Content;
	
	public int getBoardQnA_ID() {
		return boardQnA_ID;
	}
	public void setBoardQnA_ID(int boardQnA_ID) {
		this.boardQnA_ID = boardQnA_ID;
	}
	public int getBoardQnA_ReplyID() {
		return boardQnA_ReplyID;
	}
	public void setBoardQnA_ReplyID(int boardQnA_ReplyID) {
		this.boardQnA_ReplyID = boardQnA_ReplyID;
	}
	public int getBoardQnA_CommentID() {
		return boardQnA_CommentID;
	}
	public void setBoardQnA_CommentID(int boardQnA_CommentID) {
		this.boardQnA_CommentID = boardQnA_CommentID;
	}
	public String getBoardQnA_Title() {
		return boardQnA_Title;
	}
	public void setBoardQnA_Title(String boardQnA_Title) {
		this.boardQnA_Title = boardQnA_Title;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getBoardQnA_Reg_Date() {
		return boardQnA_Reg_Date;
	}
	public void setBoardQnA_Reg_Date(String boardQnA_Reg_Date) {
		this.boardQnA_Reg_Date = boardQnA_Reg_Date;
	}
	public String getBoardQnA_Content() {
		return boardQnA_Content;
	}
	public void setBoardQnA_Content(String boardQnA_Content) {
		this.boardQnA_Content = boardQnA_Content;
	}
}