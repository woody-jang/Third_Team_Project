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

	private static Object UserLock = new Object();

	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(GreenProtocol.PORT)) {
			while (true) {
				System.out.println("기다리는 중");
				Socket client = server.accept();
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());

				Thread t = new Thread(new Runnable() {
					public User user;

					Object o = null;

					@Override
					public void run() {
						user = new User();
						try {
							while ((o = ois.readObject()) != null) {
								RequestRead requestRead = new RequestRead(o, oos, ois);
							}
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				});

				addUserThread(t);

				t.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<ChatRoom> getChatRoomList() {
		return ChatRoomList;
	}

	public static void setChatRoomList(List<ChatRoom> chatRoomList) {
		ChatRoomList = chatRoomList;
	}

	public static void addUserThread(Thread t) {
		synchronized (UserLock) {
			UserThread.add(t);
		}
	}

	public static void removeUserThread(Thread t) {
		synchronized (UserLock) {
			UserThread.remove(t);
		}
	}

}