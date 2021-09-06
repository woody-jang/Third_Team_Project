package shared;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatRoom implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String Protocol;
	private String title;
	private int subjectCode;
	private String chatLog;
	private User host;
	private Map<User, ObjectOutputStream> map;

	public ChatRoom(String title, int subjectCode, String chatLog, User host) {
		super();
		this.title = title;
		this.subjectCode = subjectCode;
		this.chatLog = chatLog;
		this.host = host;
	}
	
	public String getProtocol() {
		return Protocol;
	}

	public void setProtocol(String protocol) {
		Protocol = protocol;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public Map<User, ObjectOutputStream> getMap() {
		return map;
	}
	
	public void putMap(User user,ObjectOutputStream oos) {
		map.put(user, oos);
	}

	public void setMap(Map<User, ObjectOutputStream> map) {
		this.map = map;
	}

	public int getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(int subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getChatLog() {
		return chatLog;
	}

	public void setChatLog(String chatLog) {
		this.chatLog = chatLog;
	}

	@Override
	public int hashCode() {
		return Objects.hash(subjectCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ChatRoom))
			return false;
		ChatRoom other = (ChatRoom) obj;
		return subjectCode == other.subjectCode;
	}
	
}
