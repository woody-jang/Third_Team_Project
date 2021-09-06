package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javax.swing.JOptionPane;

import shared.*;

public class RequestAndGet {
	private ObjectInputStream ois;
	private Service service;

	public RequestAndGet(ObjectInputStream ois, Service service) {
		this.ois = ois;
		this.service = service;
	}

	public void readObject(Object o) {
		// ---------------------------------------------------------------------
		if (o instanceof User) {
			User u = (User) o;

			if (u.getProtocol().equals(GreenProtocol.LOGIN_OK)) { // 로그인성공했을때
				service.loginUser(u); // 9/6수정
			} else if (u.getProtocol().equals(GreenProtocol.LOGIN_ADMIN)) {
				service.loginAdmin(u, ois); // 9/6수정
			} else if (u.getProtocol().equals(GreenProtocol.LOGIN_NO)) {
				JOptionPane.showMessageDialog(null, "아이디, 비밀번호를 확인하세요");
			} else if (u.getProtocol().equals(GreenProtocol.ALREADY_USE)) {
				JOptionPane.showMessageDialog(null, "이미 접속중입니다.");
			} else if (u.getProtocol().equals(GreenProtocol.JOIN_OK)) {
				int id = u.getId();
				JOptionPane.showMessageDialog(null, "당신의 아이디는 " + id + "입니다.");
				Client.ff.getJoinCard().getBtnBack().doClick();
				Client.ff.getLoginCard().getTf().setText(String.valueOf(id));
			} else if (u.getProtocol().equals(GreenProtocol.JOIN_FAIL)) {
				JOptionPane.showMessageDialog(null, "회원가입실패");
			} else if (u.getProtocol().equals(GreenProtocol.JOIN_TEACHER_OK)) {
				int teacherId = u.getId();
				String teacherPw = u.getPassword();
				JOptionPane.showMessageDialog(null, u.getName() + " 선생님의 아이디는 " + teacherId + ", 비밀번호는 " + teacherPw + " 입니다.");
			} else if (u.getProtocol().equals(GreenProtocol.JOIN_TEACHER_NO)) {
				JOptionPane.showMessageDialog(null, "선생님 회원가입 실패");
			} else if (u.getProtocol().equals(GreenProtocol.FIND_ID_OK)) {
				int id = u.getId();
				JOptionPane.showMessageDialog(null, "당신의 아이디는 " + id + "입니다.");
			} else if (u.getProtocol().equals(GreenProtocol.FIND_ID_NO)) {
				JOptionPane.showMessageDialog(null, "입력하신 정보가 일치하지 않습니다");
			} else if (u.getProtocol().equals(GreenProtocol.FIND_PW_OK)) {
				String pw = u.getPassword();
				JOptionPane.showMessageDialog(null, "당신의 비밀번호는 " + pw + " 입니다.");
			} else if (u.getProtocol().equals(GreenProtocol.FIND_PW_NO)) {
				JOptionPane.showMessageDialog(null, "입력하신 정보가 일치하지 않습니다.");
			} else if (u.getProtocol().equals(GreenProtocol.REPAINT_MYPROFILE_OK)) {
				service.repaintMyProfileResult(u);
			}
			// u.getprotocol.equals(LOGIN_RESULT)면 로그인 성공이기때문에 메인프레임을 열고
			// if(성공){
			// MainFrame(u);
			// 아니면 로그인 실패기때문에 다이얼로그나 실패정보 가르쳐주기.
			// ---------------------------------------------------------------------
		} else if (o instanceof ChatMessage) {
			ChatMessage cm = (ChatMessage) o;
			if (cm.getProtocol().equals(GreenProtocol.INPUT_PICTURE)) {
				service.inputpicture(Client.mf, cm);
			} else if (cm.getProtocol().equals(GreenProtocol.INPUT_FILE)) {
				service.inputFile(Client.mf, cm);
			} else if (cm.getProtocol().equals(GreenProtocol.Emoticon)) {
				service.arriveEmoticon(Client.mf, cm);
			} else {
				if (Client.user.isTeacher()) {
					service.ArriveMessage(Client.mf.mcdMap.get(cm.getSubject()), cm);
				} else {
					service.ArriveMessage(Client.mf, cm);
				}
			}
			// ---------------------------------------------------------------------
		} else if (o instanceof ChatRoom) {
			ChatRoom cr = (ChatRoom) o;
			if (cr.getProtocol().equals(GreenProtocol.CHAT_JOIN_RESULT)) {
				service.chatJoin(cr);
			} else if (cr.getProtocol().equals(GreenProtocol.MAKE_CHAT_OK)) {
				service.makeChatOK(cr);
			}
			// ---------------------------------------------------------------------
		} else if (o instanceof String) {
			String str = (String) o;
			// 채팅방 만들기
			if (str.equals(GreenProtocol.MAKE_CHAT_FAIL)) {
				service.makeChatFail();
			} else if (str.equals(GreenProtocol.CHANGE_TITLE_RESULT)) { // 채팅방이름 변경
				service.changeTitleResult(ois);
			} else if (str.equals(GreenProtocol.FIND_CHATROOM_USER)) {
				service.findChatRoomUser(ois);
			} else if (str.equals(GreenProtocol.CHANGE_HOST_RESULT)) {
				service.changeHostResult(ois);
			} else if (str.equals(GreenProtocol.HOST_EXIT_CHAT_RESULT)) {
				service.hostExitChatResult(ois);
			} else if (str.equals(GreenProtocol.SERVER_ANNOUNCE)) {
				service.announceMessage(ois);
			} else if (str.equals(GreenProtocol.SELECT_CALENDAR_RESULT)) {
				service.getChatLogList(ois);
			} else if (str.equals(GreenProtocol.REPAINT_TEACHER_LIST_OK)) {
				try {
					Client.TeacherList = (List<User>) ois.readObject();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			} else if (str.equals(GreenProtocol.DELETE_TEACHER_OK)) {
				try {
					Client.TeacherList = (List<User>) ois.readObject();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
			// 민석
			else if (str.equals(GreenProtocol.CHANGE_PROFILE_OK)) {
				try {
					User u = (User) ois.readObject();
					Client.mf.getUser().setId(u.getId());
					Client.mf.getUser().setBirth(u.getBirth());
					Client.mf.getUser().setSubject(u.getSubject());
					Client.mf.getUser().setName(u.getName());
					Client.mf.getUser().setMyMessage(u.getMyMessage());
					Client.mf.getUser().setPassword(u.getPassword());
					Client.mf.getUser().setPhone(u.getPhone());

					Client.mf.getUser().setPhoto(u.getPhotoAtByte());
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "성공");
			} else if (str.equals(GreenProtocol.CHANGE_PROFILE_NO)) {
				User u;
				try {
					u = (User) ois.readObject();

					Client.mf.getUser().setId(u.getId());
					Client.mf.getUser().setBirth(u.getBirth());
					Client.mf.getUser().setSubject(u.getSubject());
					Client.mf.getUser().setName(u.getName());
					Client.mf.getUser().setMyMessage(u.getMyMessage());
					Client.mf.getUser().setPassword(u.getPassword());
					Client.mf.getUser().setPhone(u.getPhone());

					Client.mf.getUser().setPhoto(u.getPhotoAtByte());
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "실패");
			} else if (str.equals(GreenProtocol.GET_OUT_USER_RESULT)) {
				service.getOutUserResult(ois);
			} else if (str.equals(GreenProtocol.YOU_ARE_FIRE)) {
				Client.mf.getMcd().dispose();
				JOptionPane.showMessageDialog(null, "강퇴되었습니다.");
			} else if (str.equals(GreenProtocol.LOGOUT_RESULT)) { // 로그아웃
				service.logOut();
			}
		} else if (o instanceof ChatList) {
			ChatList cl = (ChatList) o;
			// 채팅방버튼만들기
			if (cl.getProtocol().equals(GreenProtocol.MAKE_CHAT)) {
				service.makeChatRoomDialog(cl);
//					} else if (cl.getProtocol().equals(GreenProtocol.REPAINT_RESULT)) { // 
//						service.btnRefresh();
			} else if (cl.getProtocol().equals(GreenProtocol.REFRESH_CHATLIST)) {
				service.btnRefresh(cl);
			}
		} else if (o instanceof List<?>) {
			List<User> userList = (List<User>) o;
			if (Client.user.isTeacher()) {
				Client.mf.mcdMap.get(userList.get(0).getSubject()).setUserlist(userList);
				Client.mf.mcdMap.get(userList.get(0).getSubject()).updateUserList();
			} else {
				Client.userList = userList;
				Client.mf.getMcd().updateUserList();
			}
		}
	}
}
