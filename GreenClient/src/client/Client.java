package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import gui.admin.AddTeacher;
import gui.admin.AdminFrame;
import gui.login.FirstFrame;
import gui.mainpage.MainFrame;
import shared.ChatRoom;
import shared.GreenProtocol;
import shared.User;

public class Client {
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	public static FirstFrame ff;
	public static MainFrame mf;
	public static AdminFrame af;
	public static AddTeacher at;
	public static Service service;
	public static boolean changeTitle = false;
	public static List<User> userList = null;
	public static User user = null;
	private static List<ChatRoom> chatRoomList;
	private static RequestAndGet rag;
	public static List<User> TeacherList = new ArrayList<>();

	public static void main(String[] args) {

		try (Socket socket = new Socket("192.168.191.155", GreenProtocol.PORT);) {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			service = new Service(oos);
			rag = new RequestAndGet(ois,service);
			// 스윙
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					ff = new FirstFrame(oos);
				}
			});
			
			
			// 서버가 쏴주는 걸 밑으로 받는다.
			Object o = null;
			while ((o = ois.readObject()) != null) {
				rag.readObject(o);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static FirstFrame getff() {
		return ff;
	}

	public static void setff(FirstFrame ff) {
		Client.ff = ff;
	}

	public static List<ChatRoom> getChatRoomList() {
		return chatRoomList;
	}

	public static void setChatRoomList(List<ChatRoom> chatRoomList) {
		Client.chatRoomList = chatRoomList;
	}
	
	public static String getSubjectString(int subject) { //과목 int -> String
		switch (subject) {
		case 10:
			return "JAVA";
		case 20:
			return "Python";
		case 30:
			return "C언어";
		default:
			return "";
		}
	}

}