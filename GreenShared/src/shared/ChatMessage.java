package shared;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.swing.Icon;

public class ChatMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String Protocol;
	private String Message;
	private File file;
	private LocalDateTime time;
	private String sendUser; //보내는 자
	private String receiveUser; // 귓속말 대상 
	private int subject;
	private byte[] image;
	private String emopath;
	private Icon emo;
	
	
	public ChatMessage() {
	}
	// 글만 보낼 때 쓰는 생성자
	public ChatMessage(String protocol, String message, LocalDateTime time, String sendUser) {
		Protocol = protocol;
		Message = message;
		this.time = time;
		this.sendUser = sendUser;
	}
	// 파일 보낼 때
	public ChatMessage(String protocol, File file, LocalDateTime time, String sendUser) {
		Protocol = protocol;
		this.file = file;
		this.time = time;
		this.sendUser = sendUser;
	}
	// 귓속말
	public ChatMessage(String protocol, String message, LocalDateTime time, String sendUser, String receiveUser) {
		Protocol = protocol;
		Message = message;
		this.time = time;
		this.sendUser = sendUser;
		this.receiveUser = receiveUser;
	}
	
	public String getProtocol() {
		return Protocol;
	}

	public void setProtocol(String protocol) {
		
		
		Protocol = protocol;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public String getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	@Override
	public String toString() {
		return "ChatMessage [Protocol=" + Protocol + ", Message=" + Message + ", file=" + file + ", time=" + time
				+ ", sendUser=" + sendUser + ", receiveUser=" + receiveUser + "]";
	}
	public int getSubject() {
		return subject;
	}
	public void setSubject(int subject) {
		this.subject = subject;
	}
	
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Icon getEmo() {
		return emo;
	}
	public void setEmo(Icon emo) {
		this.emo = emo;
	}
	public String getEmopath() {
		return emopath;
	}
	public void setEmopath(String emopath) {
		this.emopath = emopath;
	}
}
