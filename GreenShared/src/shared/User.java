package shared;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import javax.imageio.ImageIO;

public class User implements Serializable {
	
	private static final long serialVersionUID = -6922476422704096294L;
	
	private String Protocol;
	private int id;
	private String password;
	private String name;
	private String birth;
	private String phone;
	private int subject;
	private String myMessage;
	private byte[] photo;
	private boolean isTeacher;

	public User() {
	}

	public User(int id, String password, String name, String birth, String phone, int subject) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.phone = phone;
		this.subject = subject;
		this.isTeacher = false;
		this.photo = null;
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

	public int getSubject() {
		return subject;
	}

	public void setSubject(int subject) {
		this.subject = subject;
	}

	public byte[] getPhotoAtByte() {
//		byte[] imageInByte = null;
//		BufferedImage image = null;
//		try {
//			image = ImageIO.read(new ByteArrayInputStream(photo));
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(image, "jpg", baos);
//			baos.flush();
//			 
//			imageInByte = baos.toByteArray();
//			return imageInByte;
//		} catch (IOException e) {
//		}
//		return imageInByte;
		return photo;
	}
	
	public Image getPhoto() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new ByteArrayInputStream(photo));
			return image;
		} catch (IOException e) {
		}
		return image;
	}

	public void setPhoto(byte[] photo) {
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
