package shared;

import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Objects;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String Protocol;
	private int id;
	private String password;
	private String name;
	private String birth;
	private String phone;
	private String subject;
	private String myMessage;
	private Blob photo;
	private boolean isTeacher;
	
	public User() {
	}
	
	public User(int id, String password, String name, String birth, String phone, String subject) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.phone = phone;
		this.subject = subject;
		this.isTeacher = false;
	}
	public String getProtocol() {
		return Protocol;
	}
	public void setProtocol(String protocol) {
		Protocol = protocol;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	public String getMyMessage() {
		return myMessage;
	}
	public void setMyMessage(String myMessage) {
		this.myMessage = myMessage;
	}
	public boolean isTeacher() {
		return isTeacher;
	}
	public void setTeacher(boolean isTeacher) {
		this.isTeacher = isTeacher;
	}

	@Override
	public String toString() {
		return "User [Protocol=" + Protocol + ", id=" + id + ", password=" + password + ", name=" + name + ", birth="
				+ birth + ", phone=" + phone + ", subject=" + subject + ", myMessage=" + myMessage + ", photo=" + photo
				+ ", isTeacher=" + isTeacher + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return id == other.id;
	}
	
}
