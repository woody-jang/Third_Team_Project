package client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.admin.AdminFrame;
import gui.chat.CreateChatRoomDialog;
import gui.chat.MainChatDialog;
import gui.login.FirstFrame;
import gui.mainpage.MainFrame;
import shared.*;

public class Service {
	private ObjectOutputStream oos;

	public Service(ObjectOutputStream oos) {
		this.oos = oos;
	}

	// -----------------------------------------------------------------------------------------
	// 로그인 - 서윤
	public void login(User user) {
		try {
			oos.reset();
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void join(User user) {
		try {
			oos.reset();
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void join_teacher(User newTeacher) {
		try {
			oos.reset();
			oos.writeObject(newTeacher);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void wantTeacherList() {
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.REPAINT_TEACHER_LIST);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteTeacher(User wantTeacherDelete) {
		try {
			oos.reset();
			oos.writeObject(wantTeacherDelete);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void findId(User user) {
		try {
			oos.reset();
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void findPw(User user) {
		try {
			oos.reset();
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 만들기 - 형수
	public void makeChat() {
		// 처음 채팅방만들기 다이얼로그 요청
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.MAKE_CHAT);
			oos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void makeChat(ChatRoom cr) {
		// 채팅방생성해라!!
		cr.setProtocol(GreenProtocol.MAKE_CHAT);
		try {
			oos.reset();
			oos.writeObject(cr);
			oos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void makeChatRoomDialog(ChatList cl) {
		// 방 만들기 다이얼로그 띄우기
		new CreateChatRoomDialog(cl);
	}

	public void makeChatOK(ChatRoom cr) {
		// 채팅방 생성완료
		JOptionPane.showMessageDialog(null, "채팅방이 생성되었습니다.");
		requestBtnRefresh();
		chatJoin(cr.getSubjectCode());
	}

	public void makeChatFail() {
		// 채팅방 생성실패
		JOptionPane.showMessageDialog(null, "생성에 실패하였습니다");
	}

	public void requestBtnRefresh() {
		// 새로고침요청
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.REFRESH_CHATLIST);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void btnRefresh(ChatList cl) {
		// 새로고침 : 채팅방 만들어진 목록(버튼) 새로고침
		if (cl.getMap().size() == 0) {
			return;
		}
		if (Client.mf == null) {
			return;
		}
		JPanel mp = Client.mf.getMidpnl();
		mp.removeAll();
		Map<String, Integer> map = cl.getMap();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			String title = entry.getKey();
			Integer subjectCode = entry.getValue();
			MakeButton btn = new MakeButton(title, subjectCode);
			JPanel pnl = new JPanel();
			pnl.add(btn);
			mp.add(pnl);
			if (!Client.user.isTeacher()) {
				btn.setEnabled(btn.getSubjectCode() == Client.user.getSubject());
			}
		}
		mp.revalidate();
		mp.repaint();
	}

	public void logOut() {
		int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.NO_OPTION) {
			return;
		}
		Client.user.setProtocol(GreenProtocol.LOGOUT);
		try {
			oos.reset();
			oos.writeObject(Client.user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Client.user.isTeacher()) {
			for (Integer sub : Client.mf.mcdMap.keySet()) {
				if (Client.mf.mcdMap.get(sub) != null)
					Client.mf.mcdMap.get(sub).dispose();
			}
		} else if (Client.mf.getMcd() != null) {
			Client.mf.getMcd().dispose();
		}
		Client.setff(new FirstFrame(oos));
		Client.mf.dispose();
		Client.user = null;
	}

	public void chatJoin(int btnSubjectCode) {

		try {
			int mySubject = Client.user.getSubject();
			Client.user.setProtocol(GreenProtocol.CHAT_JOIN);
			Client.user.setSubject(btnSubjectCode);
			oos.reset();
			oos.writeObject(Client.user);
			oos.flush();
			Client.user.setSubject(mySubject);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void chatJoin(ChatRoom cr) {
		if (Client.user.isTeacher()) {
			MainChatDialog mcd = new MainChatDialog(Client.mf.getUser(), oos, cr.getTitle(), cr.getSubjectCode(),
					cr.getHost().getId());
			Client.mf.mcdMap.put(cr.getSubjectCode(), mcd);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Client.mf.setMcd(mcd);
					Client.mf.getMcd().showGUI();
				}
			});
			return;
		}
		MainChatDialog mcd = new MainChatDialog(Client.mf.getUser(), oos, cr.getTitle(), cr.getSubjectCode(),
				cr.getHost().getId());

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Client.mf.setMcd(mcd);
				Client.mf.getMcd().showGUI();
			}
		});
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 - 3명
	// 석현
	public void ArriveMessage(MainFrame mf, ChatMessage message) {

		if (message.getSendUser().equals(Client.mf.getUser().getName())) {
			mf.passMCDmymessage(message);
		} else {
			mf.passMCDothermessage(message);
		}
	}

	public void ArriveMessage(MainChatDialog mainChatDialog, ChatMessage cm) {
		if (cm.getSendUser().equals(Client.mf.getUser().getName())) {
			mainChatDialog.myMessage(cm);
		} else {
			mainChatDialog.otherMessage(cm);
		}
	}

	// 영균
	public void inputFile(MainChatDialog mainChatDialog, ChatMessage cm) {
		mainChatDialog.myFile(cm);
	}// 파일위해 필요함.

	public void inputpicture(MainChatDialog mainChatDialog, ChatMessage cm) {
		mainChatDialog.myPicture(cm);
	}// 사진위해 필요함.

	public void getOutUser(User user) {// 내용물 추가함.
		// 유저 추방 시켜달라고 요청
		try {
			oos.reset();
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 민주
	public void changeTitle(String newTitle, int subjectCode) {
		// 방 제목 변경하는 메소드
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.CHANGE_TITLE);
			oos.writeObject(newTitle);
			oos.writeObject(subjectCode);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeTitleResult(ObjectInputStream ois) {
		// 방 제목 변경한걸 사용자들에게 쏴줌
		try {
			String newTitle = (String) ois.readObject();
			int subjectCode = (int) ois.readObject();
			if (Client.user.isTeacher()) {
				Client.mf.mcdMap.get(subjectCode).setTitle(newTitle);
			} else {
				Client.mf.getMcd().setTitle(newTitle);
			}
			requestBtnRefresh();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void findChatRoomUser(int subjectCode) {
		// CHATROOM USER찾기(호스트변경,호스트권한)
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.FIND_CHATROOM_USER);
			oos.writeObject(subjectCode);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void findChatRoomUser(ObjectInputStream ois) {
		// CHATROOM USER찾음
		try {
			Client.userList = (List<User>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void changeHost(User user, int subjectCode) {
		// 호스트 변경 요청
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.CHANGE_HOST);
			oos.writeObject(subjectCode);
			oos.flush();

			oos.reset();
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeHostResult(ObjectInputStream ois) {
		// 호스트 권한 넘겨줌
		try {
			int newHostUserID = (int) ois.readObject();
			int subjectCode = (int) ois.readObject();
			if (Client.user.isTeacher()) {
				Client.mf.mcdMap.get(subjectCode).ChangeHost(newHostUserID);
			} else {
				Client.mf.getMcd().ChangeHost(newHostUserID);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void exitChat(int exitUserID, int subjectCode) {
		// 채팅방 나가기
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.EXIT_CHAT);
			oos.writeObject(exitUserID);
			oos.writeObject(subjectCode);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void hostExitChat(int subjectCode) {
		// 호스트 나가기
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.HOST_EXIT_CHAT);
			oos.writeObject(subjectCode);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void hostExitChatResult(ObjectInputStream ois) {
		// 호스트나감 -> 방폭파 -> JOption
		int subjectCode = 0;
		try {
			subjectCode = (int) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Client.user.isTeacher()) {
			Client.mf.mcdMap.get(subjectCode).dispose();
			Client.mf.mcdMap.remove(subjectCode);
		} else {
			Client.mf.getMcd().dispose();
		}
		JOptionPane.showMessageDialog(Client.mf, "선생님이 방을 나갔습니다");
		requestBtnRefresh();
	}

	public void announceMessage(ObjectInputStream ois) {
		// 공지사항 날림
		try {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String message = (String) ois.readObject();
			int subjectCode = (int) ois.readObject();
			if (Client.user.isTeacher()) {
				Client.mf.mcdMap.get(subjectCode).announce(message);
			} else {
				Client.mf.getMcd().announce(message);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

//-----------------------------------------------------------------------------------------
	// 달력 - 세호
	public void requestChatLogList(String selectedDate, int subjectCode) {
		// 서버에 요청함
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.SELECT_CALENDAR);
			oos.flush();

			oos.reset();
			oos.writeObject(selectedDate);
			oos.writeObject(subjectCode);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getChatLogList(ObjectInputStream ois) {
		// 받아옴
		try {
			Client.setChatRoomList((List<ChatRoom>) ois.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//-----------------------------------------------------------------------------------------
	// 마이페이지 - 민석
	public void LookProfile() {
		// SHOW_PROFILE
		// 클릭한사람의 프로필을 요청
	}

	public void repaintMyProfile(User u) {
		u.setProtocol(GreenProtocol.REPAINT_MYPROFILE);
		try {
			oos.reset();
			oos.writeObject(u);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void repaintMyProfileResult(User u) {
		Client.mf.getMypage().setUser(u);
	}

	public void changeUserInformation(User u) {
		try {
			u.setProtocol(GreenProtocol.CHANGE_PROFILE);
			u.setName(Client.mf.getMypage().getTfName().getText());
			u.setPassword(Client.mf.getMypage().getTfPw().getText());
			u.setPhone(Client.mf.getMypage().getTfPhone().getText());
			u.setMyMessage(Client.mf.getMypage().getTfMyMessage().getText());
			oos.reset();
			oos.writeObject(u);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeUserInformation(User u, File choosefile) {
	      u.setProtocol(GreenProtocol.CHANGE_PROFILE_WITH_PHOTO);
	      u.setName(Client.mf.getMypage().getTfName().getText());
	      u.setPassword(Client.mf.getMypage().getTfPw().getText());
	      u.setPhone(Client.mf.getMypage().getTfPhone().getText());
	      u.setMyMessage(Client.mf.getMypage().getTfMyMessage().getText());
	      byte[] imageBytes = null;
	      try {
	         imageBytes = Files.readAllBytes(choosefile.toPath());
	         u.setPhoto(imageBytes);
	      } catch (IOException e1) {
	         e1.printStackTrace();
	      }
	      try {
	         oos.reset();
	         oos.writeObject(u);
	         oos.flush();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }

	public void arriveEmoticon(MainChatDialog mainChatDialog, ChatMessage cm) {
		int subjectCode = cm.getSubject();
		if (Client.user.isTeacher()) {
			if (cm.getSendUser().equals(Client.mf.getUser().getName())) {
				Client.mf.mcdMap.get(subjectCode).myEmoticon(cm);
			} else {
				Client.mf.mcdMap.get(subjectCode).otherEmoticon(cm);
			}
		} else {
			if (cm.getSendUser().equals(Client.mf.getUser().getName())) {
				mainChatDialog.myEmoticon(cm);
			} else {
				mainChatDialog.otherEmoticon(cm);
			}
		}
	}

	public void sendMessage(ChatMessage cm) {
		try {
			oos.reset();
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getOutUserResult(ObjectInputStream ois) {
		try {
			int subjectcode = (int) ois.readObject();
			Client.mf.mcdMap.get(subjectcode).getCcud().newUserList();
			JOptionPane.showMessageDialog(null, "강퇴하였습니다.");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loginUser(User user) { // 9/6추가
		Client.user = user;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Client.mf = new MainFrame(user);
				Client.ff.dispose();
			}
		});
	}

	public void loginAdmin(User user, ObjectInputStream ois) { // 9/6추가
		List<User> list;
		try {
			list = (List) ois.readObject();
			Client.af = new AdminFrame(user, list, oos);
			Client.ff.dispose();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void ExitGUI() {
		User user = Client.user;
		if (user == null) {
			user = new User();
			user.setId(0);
		}
		user.setProtocol(GreenProtocol.EXIT_GUI);

		try {
			oos.reset();
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void joinTeacherOk(User u) {
		String name = u.getName();
		int teacherId = u.getId();
		String teacherPw = u.getPassword();
		Client.af.name = name;
		Client.af.id = teacherId;
		Client.af.pw = teacherPw;
	}
}
