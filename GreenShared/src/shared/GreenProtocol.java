package shared;

public interface GreenProtocol {
	public static final int PORT = 7777;

	// 로그인, 회원가입, 찾기 - 서윤
	public static final String LOGIN = "LOGIN";
	public static final String LOGIN_RESULT = "LOGIN_RESULT";
	public static final String JOIN = "JOIN";
	public static final String JOIN_RESULT = "JOIN_RESULT";
	public static final String FIND_ID = "FIND_ID";
	public static final String FIND_ID_RESULT = "FIND_ID_RESULT";
	public static final String FIND_PW = "FIND_PW";
	public static final String FIND_PW_RESULT = "FIND_PW_RESULT";

	// ---------------------------------------------------------------------------------------------------

	// 채팅방 만들기 - 형수
	public static final String MAKE_CHAT = "MAKE_CHAT"; // 선생님이 채팅방 만들기
	public static final String MAKE_CHAT_RESULT = "MAKE_CHAT_RESULT";
	public static final String REPAINT = "REPAINT"; // 새로고침 버튼
	public static final String REPAINT_RESULT = "REPAINT_RESULT";
	public static final String LOGOUT = "LOGOUT";
	public static final String LOGOUT_RESULT = "LOGOUT_RESULT";
	public static final String CHAT_JOIN = "CHAT_JOIN"; // 채팅방 입장
	public static final String CHAT_JOIN_RESULT = "CHAT_JOIN_RESULT";

	// ---------------------------------------------------------------------------------------------------

	// 채팅방 - 3명
	// 석현
	public static final String HOST_CHAT_ALL = "HOST_CHAT_ALL"; // 방장님이 모두에게 채팅
	public static final String HOST_CHAT_ONE = "HOST_CHAT_ONE"; // 방장님이 귓속말
	public static final String CHAT_ALL = "CHAT_ALL"; // 모두에게 채팅
	public static final String CHAT_ONE = "CHAT_ONE"; // 귓속말

	// 영균
	public static final String INPUT_FILE = "INPUT_FILE"; // 사용자가 파일을 보냈을 때
	public static final String OUTPUT_FILE = "OUTPUT_FILE"; // 사용자들에게 파일을 보낼 때
	public static final String USER_LIST = "USER_LIST"; // 사용자목록
	public static final String USER_LIST_RESULT = "USER_LIST_RESULT";
	public static final String CHANGE_TITLE = "CHANGE_TITLE"; // 방제 변경
	public static final String CHANGE_TITLE_RESULT = "CHANGE_TITLE_RESULT";
	public static final String GET_OUT_USER = "GET_OUT_USER"; // 강퇴
	public static final String GET_OUT_USER_RESULT = "GET_OUT_USER_RESULT";

	// 민주
	public static final String CHANGE_HOST = "CHANGE_HOST"; // 방장 위임
	public static final String CHANGE_HOST_RESULT= "CHANGE_HOST_RESULT";
	public static final String EXIT_CHAT = "EXIT_CHAT"; // 나가기
	public static final String EXIT_CHAT_RESULT = "EXIT_CHAT_RESULT"; // 나가기
	public static final String HOST_EXIT_CHAT = "HOST_EXIT_CHAT"; // 방장 나갔을 때 방폭파
	public static final String HOST_EXIT_CHAT_RESULT = "HOST_EXIT_CHAT_RESULT";
	public static final String SERVER_ANNOUNCE = "SERVER_ANNOUNCE"; // 누가 들어왔는지 나갔는지 공지

	// ---------------------------------------------------------------------------------------------------

	// 세호
	public static final String SELECT_CALENDAR = "SELECT_CALENDAR"; // 달력 눌렀을때 그 날의 수업 기록
	public static final String SELECT_CALENDAR_RESULT = "SELECT_CALENDAR_RESULT";

	// ---------------------------------------------------------------------------------------------------

	// 민석
	public static final String SHOW_PROFILE = "SHOW_PROFILE"; // 개인 프로필 보기(대화목록눌렀을때)
	public static final String SHOW_PROFILE_RESULT = "SHOW_PROFILE_RESULT";
	public static final String CHANGE_PROFILE = "CHANGE_PROFILE"; // 마이프로필 수정 확인 눌렀을 때
	public static final String CHANGE_PROFILE_RESULT = "CHANGE_PROFILE_RESULT"; // 마이프로필 수정 성공 시

	
	// GUI 끄기
	public static final String EXIT_GUI = "EXIT_GUI";
}
