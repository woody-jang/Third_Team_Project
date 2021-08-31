package client;

import java.io.ObjectInputStream;

import shared.ChatMessage;
import shared.ChatRoom;
import shared.GreenProtocol;
import shared.User;

// client꺼
public class RequestAndGet {

	public RequestAndGet(Object o, ObjectInputStream ois, Service service) {
		//여기에 넣을거 알아서 넣으세요
		if(o instanceof User) {
			User u = (User) o;
		} else if(o instanceof ChatMessage) {
			ChatMessage cm = (ChatMessage) o;
		} else if(o instanceof ChatRoom) {
			ChatRoom cr = (ChatRoom) o;
		} else if(o instanceof String) {
			String str = (String) o;
			if (str.equals(GreenProtocol.SELECT_CALENDAR_RESULT)) {
				service.getChatLogList(ois);
			}
		}
// -----------------------------------------------------------------------------------------
		// 로그인 - 서윤
		if (o.equals(GreenProtocol.LOGIN_RESULT)) {
			
		} else if (o.equals(GreenProtocol.JOIN_RESULT)) {

		} else if (o.equals(GreenProtocol.FIND_ID_RESULT)) {

		} else if (o.equals(GreenProtocol.FIND_PW_RESULT)) {

		}
// -----------------------------------------------------------------------------------------
		// 채팅방 만들기 - 형수
		else if (o.equals(GreenProtocol.MAKE_CHAT_RESULT)) {

		} else if (o.equals(GreenProtocol.REPAINT_RESULT)) {

		} else if (o.equals(GreenProtocol.LOGOUT_RESULT)) {

		} else if (o.equals(GreenProtocol.CHAT_JOIN_RESULT)) {

		}
// -----------------------------------------------------------------------------------------
		// 채팅방 - 석현
		else if (o.equals(GreenProtocol.HOST_CHAT_ALL)) {

		} else if (o.equals(GreenProtocol.HOST_CHAT_ONE)) {

		} else if (o.equals(GreenProtocol.CHAT_ALL)) {

		} else if (o.equals(GreenProtocol.CHAT_ONE)) {

		}
		// 영균
		else if (o.equals(GreenProtocol.OUTPUT_FILE)) {

		} else if (o.equals(GreenProtocol.USER_LIST_RESULT)) {

		} else if (o.equals(GreenProtocol.CHANGE_TITLE_RESULT)) {

		} else if (o.equals(GreenProtocol.GET_OUT_USER_RESULT)) {

		}
		// 민주
		else if (o.equals(GreenProtocol.CHANGE_HOST_RESULT)) {

		} else if (o.equals(GreenProtocol.EXIT_CHAT_RESULT)) {

		} else if (o.equals(GreenProtocol.HOST_EXIT_CHAT_RESULT)) {

		} else if (o.equals(GreenProtocol.SERVER_ANNOUNCE)) {

		}
// -----------------------------------------------------------------------------------------
		// 달력 - 세호
		else if (o.equals(GreenProtocol.SELECT_CALENDAR_RESULT)) {
			// server에서 준 리스트를 받음(subjectCode와 chatLog가 들어있음)
		}
// -----------------------------------------------------------------------------------------
		// 마이페이지 - 민석
		else if (o.equals(GreenProtocol.SHOW_PROFILE_RESULT)) {

		} else if (o.equals(GreenProtocol.CHANGE_PROFILE_RESULT)) {

		} 
	}

}
