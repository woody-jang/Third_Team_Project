package client;

import java.io.File;

import shared.ChatMessage;

public class Service {
//-----------------------------------------------------------------------------------------
	// 로그인 - 서윤
	private void login() {
		// LOGIN
		// 클라이언트가 아이디 비번 입력후 서버로 요청
	}

	private void join() {
		// JOIN
		// 회원가입 정보입력후 서버로 요청
	}

	private void findId() {
		// FIND_ID
		// 아이디 찾기 서버로 요청
	}

	private void findPw() {
		// FIND_PW
		// 비번 찾기 서버로 요청
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 만들기 - 형수
	public void createRoom(int subjcetCode) {
		// MAKE_CHAT
		// 서버에 선택한 과목번호 보내서 방 만들라고 요청
	}

	public void refresh() {
		// REPAINT
		// 서버에 개설된 방목록 조회
	}

	public void logOut() {
		// LOGOUT
		// 서버에 로그아웃 프로토콜과 아이디 전송후 메인프레임 닫고 로그인 프레임 생성
	}

	public void chatJoin(int subjcetCode) {
		// CHAT_JOIN
		// 서버에 채팅방 들어간다는 프로토콜 전송 후 채팅로그 받아와서 채팅방 다이얼로그 생성
	}

//-----------------------------------------------------------------------------------------
	// 채팅방 - 3명
	// 석현
	public void clientToServer(String s, ChatMessage message) {
		// HOST_CHAT_ALL // HOST_CHAT_ONE // CHAT_ALL // CHAT_ONE
		// s는 대상(모두 또는 귓속말),message는 ChatMessage클래스
		// 메세지 종류는 프로토콜로 정해진다.(저는 일단 메세지들만)
	}

	// 영균
	public int inputFile(File file) {
		// INPUT_FILE
		// 첨부버튼을 눌러서 사진+파일을 서버에 보내기
		return 0;
	}

	public void userList() {
		// USER_LIST
		// 사용자 목록 확인
	}

	public void changeTitle() {
		// CHANGE_TITLE
		// 방 제목 변경하는 메소드
	}

	public void getOutUser() {
		// GET_OUT_USER
		// 유저 추방 시켜달라고 요청
	}

	// 민주
	public void changeHost() {
		// CHANGE_HOST
		// 방장위임
	}

	public void exitChat() {
		// EXIT_CHAT
		// 나가기
	}

	public void hostExitChat() {
		// HOST_EXIT_CHAT
		// 나가기
	}

//-----------------------------------------------------------------------------------------
	// 달력 - 세호
	public static void chatLogList(String selectedDate) {
		// SELECT_CALENDAR
		// 서버에 요청함
		// 서버에 있는 getChatLog 호출
	}

	public static String showChatLog(int index) {
		return null;
		// 스윙에서 요청하면 자신이 들고있는 List의 해당 index값을 바로 돌려줌
	}

//-----------------------------------------------------------------------------------------
	// 마이페이지 - 민석
	public void LookProfile() {
		// SHOW_PROFILE
		// 클릭한사람의 프로필을 요청
	}

	public void changeUserInformation() {
		// CHANGE_PROFILE
		// 마이페이지 정보 수정 요청
	}

	public void ExitGUI() {
		// GUI 끄기 >> 서버에 알려주기
	}
}
