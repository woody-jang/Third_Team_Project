package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

import gui.login.FirstFrame;
import shared.GreenProtocol;

public class Client {
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	private static FirstFrame ff;

	public static void main(String[] args) {
		

		try (Socket socket = new Socket("127.0.0.1", GreenProtocol.PORT);) {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			// 스윙
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					ff = new FirstFrame();
				}
			});

			// 서버가 쏴주는 걸 밑으로 받는다.
			Object o = null;
			while ((o = ois.readObject()) != null) {
				RequestAndGet rag = new RequestAndGet(o, oos);
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
}