package server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import shared.ChatMessage;
import shared.ChatRoom;
import shared.GreenProtocol;
import shared.User;

public class RequestRead {
	Service service = new Service();

	public RequestRead(Object o, ObjectOutputStream oos) throws IOException {
		//여기에 넣을거 알아서 넣으세요
		if(o instanceof User) {
			User u = (User) o;
		} else if(o instanceof ChatMessage) {
			ChatMessage cm = (ChatMessage) o;
		} else if(o instanceof ChatRoom) {
			ChatRoom cr = (ChatRoom) o;
		} else if(o instanceof String) {
			String str = (String) o;
		}
//-----------------------------------------------------------------------------------------
		// 로그인 - 서윤
		if (o.equals(GreenProtocol.LOGIN)) {
			
		} else if (o.equals(GreenProtocol.JOIN)) {

		} else if (o.equals(GreenProtocol.FIND_ID)) {

		} else if (o.equals(GreenProtocol.FIND_PW)) {

		}
//-----------------------------------------------------------------------------------------
		// 채팅방 만들기 - 형수
		else if (o.equals(GreenProtocol.MAKE_CHAT)) {
			
		} else if (o.equals(GreenProtocol.REPAINT)) {

		} else if (o.equals(GreenProtocol.LOGOUT)) {

		} else if (o.equals(GreenProtocol.CHAT_JOIN)) {

		}
//-----------------------------------------------------------------------------------------
		// 채팅방 - 석현
		else if (o.equals(GreenProtocol.HOST_CHAT_ALL)) {

		} else if (o.equals(GreenProtocol.HOST_CHAT_ONE)) {

		} else if (o.equals(GreenProtocol.CHAT_ALL)) {

		} else if (o.equals(GreenProtocol.CHAT_ONE)) {

		}
		// 영균
		else if(o.equals(GreenProtocol.INPUT_FILE)) {
			   
		} else if(o.equals(GreenProtocol.USER_LIST)) {
		   
		} else if(o.equals(GreenProtocol.CHANGE_TITLE)) {
		   
		} else if(o.equals(GreenProtocol.GET_OUT_USER)) {
		   
		} 
		// 민주
		else if(o.equals(GreenProtocol.CHANGE_HOST)) {
		   
		} else if(o.equals(GreenProtocol.EXIT_CHAT)) {
		   
		} else if(o.equals(GreenProtocol.HOST_EXIT_CHAT)) {
		   
		} 
//-----------------------------------------------------------------------------------------
		// 달력 - 세호
		else if(o.equals(GreenProtocol.SELECT_CALENDAR)) {
		   
		} 
//-----------------------------------------------------------------------------------------
		// 마이페이지 - 민석
		else if(o.equals(GreenProtocol.SHOW_PROFILE)) {
		   
		} else if(o.equals(GreenProtocol.CHANGE_PROFILE)) {
		   
		} else if(o.equals(GreenProtocol.EXIT_GUI)) {
			
		}
	}
}
