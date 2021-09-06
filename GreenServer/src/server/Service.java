package server;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import shared.*;

public class Service {

	private DataBaseDAO dao = new DataBaseDAO();
	private ObjectOutputStream oos;

	public Service(ObjectOutputStream oos) {
		this.oos = oos;

	}

	// -----------------------------------------------------------------------------------------
	// 로그인 - 서윤
	public void confirmLogin(User user) {
		User u = dao.searchLogin(user);
		// 어드민 로그인 시 선생님 목록도 가져오게 하기
		if (u.getProtocol().equals(GreenProtocol.LOGIN_ADMIN)) {
			List<User> teacherList = dao.searchTeacher();
			try {
				oos.reset();
				oos.writeObject(u);
				oos.writeObject(teacherList);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (u.getProtocol().equals(GreenProtocol.LOGIN_OK)) { // 어드민이 아닐 시에는 유저만 쏴서 로그인
			Boolean alreadyLogin = false;
			if (Server.getCurrentUserList().contains(user))
				alreadyLogin = true;

			if (alreadyLogin) {
				u.setProtocol(GreenProtocol.ALREADY_USE);
			} else {
				Server.addUserList(u);
			}
			try {
				oos.reset();
				oos.writeObject(u);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			try {
				oos.reset();
				oos.writeObject(u);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void confirmJoin(User user) {
		User u = joinResult(user);
		try {
			oos.reset();
			oos.writeObject(u);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private User joinResult(User user) {
		// 가입결과
		int returnId = 0;
		returnId = dao.saveUser(user.getPassword(), user.getName(), user.getBirth(), user.getPhone(),
				user.getSubject());
		User u = new User();
		if (returnId != 0) {
			u.setId(returnId);
			u.setProtocol(GreenProtocol.JOIN_OK);
			return u;
		} else {
			u.setProtocol(GreenProtocol.JOIN_FAIL);
			return u;
		}
	}

	public void confirmJoinTeacher(User user) {
		User u = joinTeacherResult(user);
		try {
			oos.reset();
			oos.writeObject(u);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void executeDeleteTeacher(User user) {
		dao.deleteTeacher(user);
		List<User> list = dao.searchTeacher();
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.DELETE_TEACHER_OK);
			oos.writeObject(list);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void repaintTeacherList() {
		List<User> list = dao.searchTeacher();
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.REPAINT_TEACHER_LIST_OK);
			oos.writeObject(list);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void findId(User user) {
		User u = findIdResult(user);
		try {
			oos.reset();
			oos.writeObject(u);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void findPw(User user) {
		User u = findPwResult(user);
		try {
			oos.writeObject(u);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 선생님 가입
	public User joinTeacherResult(User user) {
		user.setSubject(0);
		int teacherId = 0;
		teacherId = dao.saveTeacher(user);
		if (teacherId != 0) {
			user.setId(teacherId);
			user.setProtocol(GreenProtocol.JOIN_TEACHER_OK);
			return user;
		} else {
			user.setProtocol(GreenProtocol.JOIN_TEACHER_NO);
			return user;
		}
	}

	public User findIdResult(User user) { // 아이디 찾기 결과
		int wantId = dao.searchId(user.getName(), user.getBirth(), user.getPhone());
		User wantIdU = new User();
		if (wantId != 0) {
			wantIdU.setProtocol(GreenProtocol.FIND_ID_OK);
			wantIdU.setId(wantId);
		} else {
			wantIdU.setProtocol(GreenProtocol.FIND_ID_NO);
		}
		return wantIdU;
	}

	public User findPwResult(User user) {
		String wantPw = dao.searchPw(user.getName(), user.getBirth(), user.getPhone(), user.getId());
		User wantPwu = new User();
		if (wantPw.equals("1")) {
			wantPwu.setProtocol(GreenProtocol.FIND_PW_NO);
		} else {
			wantPwu.setProtocol(GreenProtocol.FIND_PW_OK);
			wantPwu.setPassword(wantPw);
		}
		return wantPwu;
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 만들기 - 형수
	public void createRoom(ChatRoom cr) { // 채팅방만들기 다이얼로그용
		// 채팅방만들기, Map활성화
		if (!Server.getChatRoomList().contains(cr)) {
			Server.getChatRoomList().add(cr);
			cr.setProtocol(GreenProtocol.MAKE_CHAT_OK);
			try {
				oos.reset();
				oos.writeObject(cr);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			cr.setMap(new HashMap<User, ObjectOutputStream>());
			dao.creatRoom(cr);
		} else {
			try {
				oos.reset();
				oos.writeObject(GreenProtocol.MAKE_CHAT_FAIL);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void makeChatList() {
		// 만들어진 채팅리스트 보내주기 - 버튼만들려고
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<ChatRoom> list = Server.getChatRoomList();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getTitle(), list.get(i).getSubjectCode());
		}
		try {
			oos.reset();
			oos.writeObject(new ChatList(map, GreenProtocol.MAKE_CHAT));
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void makeChatListRefresh() {
		// 채팅리스트 보내주기 - 새로고침 (로그인한 모든사람들에게)
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<ChatRoom> list = Server.getChatRoomList();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getTitle(), list.get(i).getSubjectCode());
		}
		try {
			for (ObjectOutputStream oos : Server.getOosList()) {
				oos.reset();
				oos.writeObject(new ChatList(map, GreenProtocol.REFRESH_CHATLIST));
				oos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logOut(User u) {
		List<ChatRoom> list = Server.getChatRoomList();
		for (ChatRoom cr : list) {
			if (cr.getMap().keySet().contains(u)) { // 채팅방 들어가 있으면
				cr.getMap().remove(u); // 목록에서 유저 지우고
				if (cr.getHost().equals(u)) { // 만약 로그아웃한 유저가 호스트면
					for (Entry<User, ObjectOutputStream> entry : cr.getMap().entrySet()) { // 그 방의 모든유저에게
						ObjectOutputStream userOos = entry.getValue();
						try {
							userOos.reset();
							userOos.writeObject(GreenProtocol.HOST_EXIT_CHAT_RESULT); // 방폭파 프로토콜 전송
							userOos.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					list.remove(cr); // 서버의 채팅방 리스트에서 방 지우고
					int re = dao.removeChatRoom(cr.getSubjectCode()); // db수정함
				} else {
					serverAnnounce(cr.getSubjectCode(), u.getName() + "님이 퇴장하였습니다."); // 호스트가 아니면 퇴장메세지만 전송
				}

			}
		}
		if (Server.getCurrentUserList().contains(u))
			Server.getCurrentUserList().remove(u);
	}

	public void chatJoin(User u, ObjectOutputStream useroos) {
		// 채팅방에 입장
		try {
			// TODO
			List<ChatRoom> list = Server.getChatRoomList();
			for (int i = 0; i < list.size(); i++) {
				ChatRoom cr = list.get(i);
				if (cr.getSubjectCode() == u.getSubject() && cr.getMap().get(u) == null) {
					cr.getMap().put(u, useroos);
					ChatRoom crReturn = new ChatRoom(cr.getTitle(), cr.getSubjectCode(), cr.getChatLog(), cr.getHost());
					crReturn.setMap(null);
					crReturn.setProtocol(GreenProtocol.CHAT_JOIN_RESULT);
					oos.reset();
					oos.writeObject(crReturn);
					oos.flush();
					serverAnnounce(cr.getSubjectCode(), u.getName() + "님이 입장하였습니다.");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 - 3명
	// 석현
	public void allUserSendMessage(ChatMessage cm) {
		// 서버에 있는 자기의 채팅방에 있는 유저에게 메세지 뿌리기
		List<ChatRoom> list = Server.getChatRoomList();
		Map<User, ObjectOutputStream> map = null;

		for (ChatRoom chatRoom : list) {
			if (cm.getSubject() == chatRoom.getSubjectCode()) {
				map = chatRoom.getMap();
				System.out.println("자바채팅창확인함");
			}
		}
		System.out.println("메세지 보냈을때 확인할 맵 사이즈" + map.size());
		for (Iterator<Entry<User, ObjectOutputStream>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			try {
				ObjectOutputStream oos = iterator.next().getValue();
				oos.reset();
				oos.writeObject(cm);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (Map.Entry<User, ObjectOutputStream> entry : map.entrySet()) {
			ObjectOutputStream qweoos = entry.getValue();
			System.out.println("2 " + qweoos);
		}
		System.out.println("메세지 보내기 전송 완료");
	}

	public void oneUserSendMessage(ChatMessage cm) {
		// 귓속말 대상이 있을때 그사람에게 귓속말하기
		List<ChatRoom> list = Server.getChatRoomList();
		Map<User, ObjectOutputStream> map = null;

		for (ChatRoom chatRoom : list) {
			if (cm.getSubject() == chatRoom.getSubjectCode()) {
				map = chatRoom.getMap();
			}
		}

		for (Iterator<Entry<User, ObjectOutputStream>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<User, ObjectOutputStream> set = iterator.next();
			if (set.getKey().getName().equals(cm.getReceiveUser())) {
				try {
					// TODO
					set.getValue().reset();
					set.getValue().writeObject(cm);
					set.getValue().flush();
					oos.reset();
					oos.writeObject(cm);
					oos.flush();
				} catch (IOException e) {
					System.out.println("귓속말 오류");
				}
			}

		}
	}

	// 호스트가 보내면 처리는 클라이언트쪽에서 처리 하게 된다.(왜냐하면 호스트가 전체에게 보내는 것도 결국 모두에게 보내는것과 같기 때문에
	// GUI에서 색을 바꾸려면 결국 클라이언트에서도 판단을 해야하는데 그 경우 여기서 하면 2번을 하게 된다.
//		public void hostToAllSendmessage(ChatMessage cm) {
//			// HOST_CHAT_ALL
//			// 방장이 모두에게 메세지 보내기
//		}
	//
//		public void hostToOneSendmessage(ChatMessage cm) {
//			// HOST_CHAT_ONE
//			// 방장의 귓말
//		}

	// 영균
	public void outputFile(ChatMessage cm) {
		// 채팅방에 파일,사진 보내기
		List<ChatRoom> list = Server.getChatRoomList();
		Map<User, ObjectOutputStream> map = null;

		for (ChatRoom chatRoom : list) {
			if (cm.getSubject() == chatRoom.getSubjectCode()) {
				map = chatRoom.getMap();
			}
		}

		for (Iterator<Entry<User, ObjectOutputStream>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			try {
				ObjectOutputStream oos = iterator.next().getValue();
				oos.reset();
				oos.writeObject(cm);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void userListOk() {
		// USER_LIST_OK
		// 사용자 목록 확인해서 알려주기 (맵에서 확인)
	}

	public int getOutUserOk(User u) {
		// 유저 추방 시키고 (Map에서 해당 유저 삭제)

		List<ChatRoom> list = Server.getChatRoomList();
		Map<User, ObjectOutputStream> map = null;

		for (ChatRoom chatRoom : list) {
			if (u.getSubject() == chatRoom.getSubjectCode()) {
				map = chatRoom.getMap();
			}
		}

		for (Map.Entry<User, ObjectOutputStream> entry : map.entrySet()) {

			if (entry.getKey().equals(u)) {
				map.remove(u);
				try {
					entry.getValue().writeObject(GreenProtocol.YOU_ARE_FIRE);
					entry.getValue().flush();
					return 1;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return 0;
	}

	// 민주
	public void changeTitleResult(ObjectInputStream ois) {
		// 방 제목 변경 결과
		String newTitle = null;
		int subjectCode = 0;
		try {
			newTitle = (String) ois.readObject();
			subjectCode = (int) ois.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		dao.ChangeTitle(newTitle, subjectCode);
		List<ChatRoom> chatroomlist = Server.getChatRoomList();
		ChatRoom cr = null;
		for (ChatRoom chatRoom : chatroomlist) {
			if (chatRoom.getSubjectCode() == subjectCode) {
				chatRoom.setTitle(newTitle);
				cr = chatRoom;
				break;
			}
		}
		for (User user2 : cr.getMap().keySet()) {
			try {
				ObjectOutputStream oos = cr.getMap().get(user2);
				oos.reset();
				oos.writeObject(GreenProtocol.CHANGE_TITLE_RESULT);
				oos.writeObject(newTitle);
				oos.writeObject(cr.getSubjectCode());
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		serverAnnounce(subjectCode, "방제 '" + newTitle + "'로 변경");
	}

	public void findChatRoomUser(ObjectInputStream ois) {
		// 방장위임결과
		try {
			int subjectCode = (int) ois.readObject();
			ChatRoom cr = null;
			for (ChatRoom chatroom : Server.getChatRoomList()) {
				if (chatroom.getSubjectCode() == subjectCode) {
					cr = chatroom;
					break;
				}
			}
			List<User> users = new ArrayList<User>();
			for (User user : cr.getMap().keySet()) {
				users.add(user);
			}
			oos.reset();
			oos.writeObject(GreenProtocol.FIND_CHATROOM_USER);
			oos.flush();
			oos.reset();
			oos.writeObject(users);
			oos.flush();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeHostOk(ObjectInputStream ois) {
		// 방장위임결과
		int subjectCode = 0;
		String newHostName = "";
		int newHostID = 0;
		ChatRoom cr = null;
		try {
			subjectCode = (int) ois.readObject();
			User user = (User) ois.readObject();
			for (ChatRoom chatroom : Server.getChatRoomList()) {
				if (chatroom.getSubjectCode() == subjectCode) {
					cr = chatroom;
					for (User user2 : chatroom.getMap().keySet()) {
						if (user2.getId() == user.getId()) {
							chatroom.setHost(user2);
							newHostName = user2.getName();
							newHostID = user2.getId();
							break;
						}
					}
					break;
				}
			}
			for (User user2 : cr.getMap().keySet()) {
				try {
					ObjectOutputStream oos = cr.getMap().get(user2);
					oos.reset();
					oos.writeObject(GreenProtocol.CHANGE_HOST_RESULT);
					oos.writeObject(newHostID);
					oos.writeObject(cr.getSubjectCode());
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverAnnounce(subjectCode, "호스트가 '" + newHostName + "' 로 변경되었습니다.");
	}

	public void exitChatOk(ObjectInputStream ois) {
		// map에서 나간 유저 빼기
		int subjectCode = 0;
		String outUserName = "";
		try {
			int exitUserID = (int) ois.readObject();
			subjectCode = (int) ois.readObject();
			for (ChatRoom chatroom : Server.getChatRoomList()) {
				if (chatroom.getSubjectCode() == subjectCode) {
					for (User user2 : chatroom.getMap().keySet()) {
						if (user2.getId() == exitUserID) {
							chatroom.getMap().remove(user2);
							outUserName = user2.getName();
							break;
						}
					}
					break;
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		serverAnnounce(subjectCode, outUserName + "님이 퇴장하였습니다.");
	}

	public void hostExitChatOk(ObjectInputStream ois) {
		// 호스트가 방 폭파
		// 방장을빼고 다른 사람들에게 방이 없어진거 알려줌
		try {
			int subjectCode = (int) ois.readObject();
			ChatRoom cr = null;
			System.out.println(Server.getChatRoomList().size());
			for (ChatRoom chatroom : Server.getChatRoomList()) {
				if (chatroom.getSubjectCode() == subjectCode) {
					cr = chatroom;
					dao.removeChatRoom(subjectCode); // isrunning = 0
					break;
				}
			}

			for (User user2 : cr.getMap().keySet()) {
				try {
					ObjectOutputStream oos = cr.getMap().get(user2);
					oos.reset();
					oos.writeObject(GreenProtocol.HOST_EXIT_CHAT_RESULT);
					oos.writeObject(cr.getSubjectCode());
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Server.getChatRoomList().remove(cr);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void serverAnnounce(int subjectCode, String message) {
		// 채팅장에 공지 메세지 보내기
		List<ChatRoom> list = Server.getChatRoomList();
		Map<User, ObjectOutputStream> map = null;

		for (ChatRoom chatRoom : list) {
			if (chatRoom.getSubjectCode() == subjectCode) {
				map = chatRoom.getMap();
			}
		}

		for (User user : map.keySet()) {
			try {
				ObjectOutputStream oos = map.get(user);
				oos.reset();
				oos.writeObject(GreenProtocol.SERVER_ANNOUNCE);
				oos.writeObject(message);
				oos.writeObject(subjectCode);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//-----------------------------------------------------------------------------------------
	// 달력 - 세호
	public void getChatLogList(ObjectOutputStream oos, ObjectInputStream ois) {
		// 날짜별 채팅로그 조회 및 반환
		List<ChatRoom> chatRoomList = null;
		try {
			String selectedDate = (String) ois.readObject();
			int subjectCode = (int) ois.readObject();
			chatRoomList = dao.getChatLogList(selectedDate, subjectCode);

			oos.reset();
			oos.writeObject(GreenProtocol.SELECT_CALENDAR_RESULT);
			oos.flush();
			oos.reset();
			oos.writeObject(chatRoomList);
			oos.flush();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//-----------------------------------------------------------------------------------------
	// 마이페이지 - 민석
	public void lookProfile() {
		// SHOW_PROFILE_OK
		// 클릭한 사람의 사진, 이름, 상메 보내주기
	}

	public void changeUserInforWithPhoto(User user, ObjectInputStream ois) {
		File profile = null;
		try {
			profile = (File) ois.readObject();
			int result = 0;
			result = dao.updateProfile(user, profile);
			User repaintMyProfileUser = dao.repaintMyProfile(user);
			Thread.sleep(700);
			if (result == 1) {
				oos.reset();
				oos.writeObject(GreenProtocol.CHANGE_PROFILE_OK);
				oos.flush();
				oos.reset();
				oos.writeObject(repaintMyProfileUser);
				oos.flush();
			} else {
				oos.reset();
				oos.writeObject(GreenProtocol.CHANGE_PROFILE_NO);
				oos.flush();
				oos.reset();
				oos.writeObject(repaintMyProfileUser);
				oos.flush();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void changeUserInforNoPhoto(User user) {
		int result;
		try {
			result = dao.updateProfile(user);
			User repaintMyProfileUser = dao.repaintMyProfile(user);
			Thread.sleep(700);
			if (result == 1) {
				oos.reset();
				oos.writeObject(GreenProtocol.CHANGE_PROFILE_OK);
				oos.flush();
				oos.reset();
				oos.writeObject(repaintMyProfileUser);
				oos.flush();
			} else {
				oos.reset();
				oos.writeObject(GreenProtocol.CHANGE_PROFILE_NO);
				oos.flush();
				oos.reset();
				oos.writeObject(repaintMyProfileUser);
				oos.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void getMyProfile(User user) {
		User repaintMyProfileUser = dao.getMyProfile(user);
		try {
			oos.reset();
			oos.writeObject(repaintMyProfileUser);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ExitGUI(User u) {
		if (u.getId() != 0) {
			logOut(u);
		}
		Server.getOosList().remove(oos);
		Thread.currentThread().stop();
		Server.getUserThread().remove(Thread.currentThread());
	}

	public void allUserUpdateUserList(User u) {

		List<ChatRoom> list = Server.getChatRoomList();
		Map<User, ObjectOutputStream> map = null;
		ChatRoom cr = null;

		for (ChatRoom chatRoom : list) {
			if (u.getSubject() == chatRoom.getSubjectCode()) {
				map = chatRoom.getMap();
				cr = chatRoom;
				System.out.println("자바채팅창확인함");
			}
		}

		List<User> users = new ArrayList<User>();
		for (User user : map.keySet()) {
			users.add(user);
		}

		System.out.println("메세지 보냈을때 확인할 맵 사이즈" + map.size());
		for (Map.Entry<User, ObjectOutputStream> entry : map.entrySet()) {
			ObjectOutputStream qweoos = entry.getValue();
			try {
				qweoos.reset();
				qweoos.writeObject(users);
				qweoos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void allUserUpdateUserList(int subject) {

		List<ChatRoom> list = Server.getChatRoomList();
		Map<User, ObjectOutputStream> map = null;
		ChatRoom cr = null;

		for (ChatRoom chatRoom : list) {
			if (subject == chatRoom.getSubjectCode()) {
				map = chatRoom.getMap();
				cr = chatRoom;
				System.out.println("자바채팅창확인함");
			}
		}

		List<User> users = new ArrayList<User>();
		for (User user : map.keySet()) {
			users.add(user);
		}

		System.out.println("메세지 보냈을때 확인할 맵 사이즈" + map.size());
		for (Map.Entry<User, ObjectOutputStream> entry : map.entrySet()) {
			ObjectOutputStream qweoos = entry.getValue();
			try {
				qweoos.reset();
				qweoos.writeObject(users);
				qweoos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void updatechatlog(ChatMessage cm) {
		dao.updateLog(cm);

	}

	public void getoutUserResult(User u) {
		try {
			oos.reset();
			oos.writeObject(GreenProtocol.GET_OUT_USER_RESULT);
			oos.writeObject(u.getSubject());
			oos.flush();
			
			System.out.println("4." + u.getSubject());
			System.out.println(u.getName());
			serverAnnounce(u.getSubject(), "'" + u.getName() + "'이 추방되었습니다");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
