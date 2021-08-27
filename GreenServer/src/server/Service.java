package server;

import java.io.File;

public class Service {
	private DataBaseDAO dao;

//-----------------------------------------------------------------------------------------
	// 로그인 - 서윤
	public String loginResult() {
		return null;
//		 LOGIN_OK / LOGIN_FAIL
//		 로그인 조건으로 성공실패 
	}

	public void joinResult() {
		// JOIN_OK / JOIN_FAIL
		// 회원가입 조건으로 성공실패 
	}

	public void findIdResult() {
		// FIND_ID_OK / FIND_ID_FAIL
		// 아이디찾기 조건으로 성공실패 
	}

	public void findPwResult() {
		// FIND_PW_OK / FIND_PW_FAIL
		// 패스워드 찾기 조건으로 성공실패 
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 만들기 - 형수
	public void createRoom(int subjcetCode) {
		// MAKE_CHAT_OK // MAKE_CHAT_FAIL
		// 서버는 ChatRoom Set을 가지고 있고(ChatRoom에 과목코드로 equals 오버라이드)
		// set에 추가 되는지 확인하여 클라이언트에 MAKE_CHAT_OK 혹은 MAKE_CHAT_FAIL 클라이언트에 전송
		// Map활성화
	}

	public void refresh() {
		// REPAINT_OK
		// 채팅방목록, 달력 새로고침
		// 채팅방목록 : ChatRoom set에 있는 방들의 목록을 클라이언트에 전송
	}

	public void logOut(int id) {
		// LOGOUT_OK
		// 채팅방에 접속한 상태면 나가고, 호스트면 방 폭파
		// 로그아웃시 로인그인페이지로 가기
	}

	public void chatJoin(int subjcetCode) {
		// CHAT_JOIN_OK // CHAT_JOIN_FAIL
		// db조회해서 채팅 로그 클라이언트 전송
		// Map에 user 추가
		// serverAnnounce(민주) 사용
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 - 3명
	// 석현
	public void allUserSendMessage() {
		// CHAT_ALL
		// 서버에 있는 맵에 있는 유저와 스레드 안에 있는 유저를 비교
		// 해서 모두에게 뿌리기
	}

	public void oneUserSendMessage() {
		// CHAT_ONE
		// 귓속말 대상이 있을때 그사람에게 귓속말하기
	}

	public void hostToAllSendmessage() {
		// HOST_CHAT_ALL
		// 방장이 모두에게 메세지 보내기
	}

	public void hostToOneSendmessage() {
		// HOST_CHAT_ONE
		// 방장의 귓말
	}

	// 영균
	public void outputFile() {
		// OUTPUT_FILE
		// 서버에서 클라이언트들에게 UPload된것들을 보냄
	}

	public void userListOk() {
		// USER_LIST_OK
		// 사용자 목록 확인해서 알려주기 (맵에서 확인)
	}

	public void changeTitleResult() {
		// CHANGE_TITLE_RESULT
		// 방 제목 변경 결과
	}

	public void getOutUserOk() {
		// GET_OUT_USER_OK
		// 유저 추방 시키고 (Map에서 해당 유저 삭제)
	}

	// 민주
	public void changeHostOk() {
		// CHANGE_HOST_OK
		// 방장위임결과
	}

	public void exitChatOk() {
		// EXIT_CHAT_OK
		// map에서 나간 유저 빼기
		// 채팅방꺼지기(유저)
		// SERVER_ANNOUNCE하기
	}

	public void serverAnnounce() {
		// SERVER_ANNOUNCE
		// 유저in,out 채팅장에 메세지 보내기
	}

	public void hostExitChatOk() {
		// HOST_EXIT_CHAT_OK
		// 방장을빼고 다른 사람들에게 방이 없어진거 알려줌
	}

//-----------------------------------------------------------------------------------------
	// 달력 - 세호
	public static void getChatLogList(String selectedDate) {
		// SELECT_CALENDAR_OK
		// db 조회 요청함
		// dao에 있는 getChatLogList 호출

		// client에 있는 getChatLogList 호출
		// 반환받은 List client에 전달
	}

//-----------------------------------------------------------------------------------------
	// 마이페이지 - 민석
	public void lookProfile() {
		// SHOW_PROFILE_OK
		// 클릭한 사람의 사진, 이름, 상메 보내주기
	}

	public void changeUserInformation() {
		// CHANGE_PROFILE_OK / CHANGE_PROFILE_FAIL
		// 성공, 실패 나눠서 리턴값 보내기
		// 성공 : 마이프로필 정보 db에 수정 , gui repaint
	}
	
	public void ExitGUI() {
		// EXIT_GUI
		// 리스트에서 쓰레드 지우고 소켓 닫고 스트림 다닫어 ㅂ2
	}
}
