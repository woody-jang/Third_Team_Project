package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import shared.ChatRoom;
import shared.GreenProtocol;
import shared.User;

public class Server {
	// 채팅룸 만들 때 구별, 호스트도 구별(Boolean, 호스트가 true)

	private static List<Thread> UserThread = new ArrayList<>(); // 유저리스트
	private static List<ChatRoom> ChatRoomList = new LinkedList<>(); // 채팅방 리스트
	private static List<ObjectOutputStream> oosList = new ArrayList<>(); // 채팅방 리스트
	private static List<User> currentUserList = new ArrayList<>(); // 현재 접속하고 있는 사람들
	private static Object UserLock = new Object();

	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(GreenProtocol.PORT)) {
			while (true) {
				System.out.println("기다리는중");
				Socket client = server.accept();
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());

				Thread t = new Thread(new Runnable() {
					public RequestRead rr = new RequestRead(oos, ois);

					@Override
					public void run() {
						Object o = null;
						try {
							while ((o = ois.readObject()) != null) {
								rr.readObject(o);
							}
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				});
				oosList.add(oos);
				addUserThread(t);

				t.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addUserThread(Thread t) {
		synchronized (UserLock) {
			getUserThread().add(t);
		}
	}

	public static void removeUserThread(Thread t) {
		synchronized (UserLock) {
			getUserThread().remove(t);
		}
	}

	// 현재 로그인 중인 사람 추가
	public static void addUserList(User user) {
		synchronized (UserLock) {
			getCurrentUserList().add(user);
		}
	}

	// 현재 로그인 중인 사람이 로그아웃 했을때 아웃
	public static void removeUserList(User user) {
		synchronized (UserLock) {
			getCurrentUserList().remove(user);
		}
	}

	public static List<ChatRoom> getChatRoomList() {
		return ChatRoomList;
	}

	public static void setChatRoomList(List<ChatRoom> chatRoomList) {
		ChatRoomList = chatRoomList;
	}

	public static List<Thread> getUserThread() {
		return UserThread;
	}

	public static void setUserThread(List<Thread> userThread) {
		UserThread = userThread;
	}

	public static List<ObjectOutputStream> getOosList() {
		return oosList;
	}

	public static List<User> getCurrentUserList() {
		return currentUserList;
	}

	public static void setCurrentUserList(List<User> currentUserList) {
		Server.currentUserList = currentUserList;
	}

}