package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import shared.ChatMessage;
import shared.ChatRoom;
import shared.GreenProtocol;
import shared.User;

public class RequestRead {
	Service service;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	public RequestRead(ObjectOutputStream oos, ObjectInputStream ois) {
		// 여기에 넣을거 알아서 넣으세요
		this.oos = oos;
		this.ois = ois;
		service = new Service(oos);
	}

	public void readObject(Object o) throws IOException {
		if (o instanceof User) {
			User u = (User) o;
			if (u.getProtocol().equals(GreenProtocol.CHAT_JOIN)) {
				service.chatJoin(u, oos);
				service.allUserUpdateUserList(u);
			} else if (u.getProtocol().equals(GreenProtocol.LOGOUT)) {
				service.logOut(u);
			} // 서윤
			else if (u.getProtocol().equals(GreenProtocol.LOGIN)) { // 유저클래스안에 있는 프로토콜이 로그인이면(if)
				service.confirmLogin(u);
			} else if (u.getProtocol().equals(GreenProtocol.JOIN)) {
				service.confirmJoin(u);
			} else if (u.getProtocol().equals(GreenProtocol.JOIN_TEACHER)) {
				service.confirmJoinTeacher(u);
			} else if (u.getProtocol().equals(GreenProtocol.DELETE_TEACHER)) {
				service.executeDeleteTeacher(u);
			} else if (u.getProtocol().equals(GreenProtocol.FIND_ID)) {
				service.findId(u);
			} else if (u.getProtocol().equals(GreenProtocol.FIND_PW)) {
				service.findPw(u);
			} else if (u.getProtocol().equals(GreenProtocol.CHAT_JOIN)) {
				service.chatJoin(u, oos);
			} else if (u.getProtocol().equals(GreenProtocol.LOGOUT)) {
				service.logOut(u);
			} else if (u.getProtocol().equals(GreenProtocol.GET_OUT_USER)) {
				System.out.println("1." + u.getSubject());
				System.out.println(u.getName());
				int result = service.getOutUserOk(u);
				System.out.println("2." + u.getSubject());
				System.out.println(u.getName());
				service.allUserUpdateUserList(u);
				System.out.println("3." + u.getSubject());
				System.out.println(u.getName());

				if (result == 1) {
					service.getoutUserResult(u);
				}

			} else if (u.getProtocol().equals(GreenProtocol.EXIT_GUI)) {
				service.ExitGUI(u);
			}

			// 민석
			else if (u.getProtocol().equals(GreenProtocol.CHANGE_PROFILE_WITH_PHOTO)) {
				service.changeUserInforWithPhoto(u);
			}
			// 사진 변경 없이 프로필 체인지
			else if (u.getProtocol().equals(GreenProtocol.CHANGE_PROFILE)) {
				service.changeUserInforNoPhoto(u);
			} else if (u.getProtocol().equals(GreenProtocol.REPAINT_MYPROFILE)) {
				service.getMyProfile(u);
			}
			// -----------------------------------------------------------------------------------------
		} else if (o instanceof ChatMessage) {
			ChatMessage cm = (ChatMessage) o;
			// 석현
			if (cm.getProtocol().equals(GreenProtocol.HOST_CHAT_ALL)) {
				service.updatechatlog(cm);// DB에 로그업데이트
				service.allUserSendMessage(cm);
			} else if (cm.getProtocol().equals(GreenProtocol.HOST_CHAT_ONE)) {
				service.oneUserSendMessage(cm);
			} else if (cm.getProtocol().equals(GreenProtocol.CHAT_ALL)) {
				service.updatechatlog(cm);// DB에 로그업데이트
				service.allUserSendMessage(cm);
			} else if (cm.getProtocol().equals(GreenProtocol.CHAT_ONE)) {
				service.oneUserSendMessage(cm);
			} else if (cm.getProtocol().equals(GreenProtocol.INPUT_FILE)) {
				service.outputFile(cm);
			} else if (cm.getProtocol().equals(GreenProtocol.INPUT_PICTURE)) {
				service.outputFile(cm);
			} else if (cm.getProtocol().equals(GreenProtocol.Emoticon)) {
				service.allUserSendMessage(cm);
			}
			// -----------------------------------------------------------------------------------------
		} else if (o instanceof ChatRoom) {
			ChatRoom cr = (ChatRoom) o;
			if (cr.getProtocol().equals(GreenProtocol.MAKE_CHAT)) {
				service.createRoom(cr);
			}
			// -----------------------------------------------------------------------------------------
		} else if (o instanceof String) {
			String str = (String) o;
			if (str.equals(GreenProtocol.MAKE_CHAT)) {
				service.makeChatList();
//			} else if (str.equals(GreenProtocol.REPAINT)) {
//				service.refresh2();
			} else if (str.equals(GreenProtocol.CHANGE_TITLE)) {
				service.changeTitleResult(ois);
			} else if (str.equals(GreenProtocol.FIND_CHATROOM_USER)) {
				service.findChatRoomUser(ois);
			} else if (str.equals(GreenProtocol.CHANGE_HOST)) {
				service.changeHostOk(ois);
			} else if (str.equals(GreenProtocol.EXIT_CHAT)) {
				service.exitChatOk(ois);
			} else if (str.equals(GreenProtocol.HOST_EXIT_CHAT)) {
				service.hostExitChatOk(ois);
			} else if (str.equals(GreenProtocol.SELECT_CALENDAR)) { // 세호
				service.getChatLogList(oos, ois);
			} else if (str.equals(GreenProtocol.REFRESH_CHATLIST)) {
				service.makeChatListRefresh();
			} else if (str.equals(GreenProtocol.REPAINT_TEACHER_LIST)) {
				service.repaintTeacherList();
			}
		}
	}
}
