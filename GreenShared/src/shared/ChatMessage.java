package shared;

import java.io.File;
import java.io.Serializable;
import java.time.LocalTime;

public class ChatMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String Protocol;
	private String Message;
	private File file;
	private LocalTime time;
	private String sendUser; //보내는 자
	private String receiveUser; // 귓속말 대상 
	
	public ChatMessage() {
	}
	// 글만 보낼 때 쓰는 생성자
	public ChatMessage(String protocol, String message, LocalTime time, String sendUser) {
		Protocol = protocol;
		Message = message;
		this.time = time;
		this.sendUser = sendUser;
	}
	// 파일 보낼 때
	public ChatMessage(String protocol, File file, LocalTime time, String sendUser) {
		Protocol = protocol;
		this.file = file;
		this.time = time;
		this.sendUser = sendUser;
	}
	// 귓속말
	public ChatMessage(String protocol, String message, LocalTime time, String sendUser, String receiveUser) {
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

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
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
}
