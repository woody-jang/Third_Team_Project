package gui.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainChatDialog extends JDialog {

	private JPanel pnl;

	private int flag = 0;//색바꾸는거 확인해보게
	
	public MainChatDialog(String subject) {

		setTitle(subject);//생성될 채팅창 제목 정하기

		pnl = new JPanel(new GridLayout(0, 1, 0, 20));//채팅 메인화면에 그리드레이아웃으로 채팅이 쌓인다.
		// pnl.setBackground(Color.pink);
		JPanel southpnl = new JPanel();//밑에 채팅 치는 곳용 패널
		JScrollPane sp = new JScrollPane(pnl);//중앙에 스크롤패널

		// 스크롤 확인용
		for (int i = 0; i < 50; i++) {
			newPnl(i+"님 안녕하세요");

		}
		
		//이건 패널레이블을 만드는 메소들에서 문장의 길이를 확인하여 너무 길면 짤라서 레이블을 2줄로 만들어야 한다.
		//newPnl("엄청나게 길게 만들어진 문장은 어떻게 보이는지 확인하기 위해서 만든 패널의 레이블입니다. 과연 어떻게나올까요");

		
		JMenuBar mb = new JMenuBar();//메뉴바를 하나 만든다
		mb.setBackground(Color.GREEN);

		JMenu screenMenu = new JMenu("첨부");//메뉴를 만든다

		JMenuItem miPicture = new JMenuItem("사진");//메뉴 안에 들어갈 아이템을 만든다
		JMenuItem miFile = new JMenuItem("파일");//메뉴 안에 들어갈 아이템을 만든다
		
		screenMenu.add(miPicture);
		screenMenu.add(miFile);

		
		JMenu screenMenu2 = new JMenu("대화상대");//메뉴를 만든다

		JMenuItem miUserList = new JMenuItem("목록");//메뉴 안에 들어갈 아이템을 만든다
		miUserList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShowUserListDialog(10);
			}
		});
		screenMenu2.add(miUserList);

		
		JMenu screenMenu3 = new JMenu("호스트");//메뉴를 만든다

		JMenuItem miChangeTitle = new JMenuItem("방제변경");//메뉴 안에 들어갈 아이템을 만든다
		miChangeTitle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangeChatTitleDialog(10);
			}
		}); 
		JMenuItem miExpulsion = new JMenuItem("강퇴");//메뉴 안에 들어갈 아이템을 만든다
		miExpulsion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangeChatUserDialog(10);
			}
		});
		JMenuItem miMandate = new JMenuItem("위임");//메뉴 안에 들어갈 아이템을 만든다
		miMandate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChageChatHostDialog(10);
			}
		});
		
		screenMenu3.add(miChangeTitle);
		screenMenu3.add(miExpulsion);
		screenMenu3.add(miMandate);

		JMenu screenMenu4 = new JMenu("나가기");//메뉴를 만든다
		JMenuItem miExit = new JMenuItem("채팅방 나가기");//메뉴 안에 들어갈 아이템을 만든다
		miExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		screenMenu4.add(miExit);

		String[] userid = { "모두에게", "aaa", "bbb", "ccc", "ddd" };//현재 채팅방에 접속해있는 학생들이름이 전부 들어갈수 있도록 해야함 지금은 임시
		JComboBox<String> idcombo = new JComboBox(userid);//콤보박스 생성

		JTextField tf = new JTextField(15);//채팅용 텍스트 필드 생성
		
		tf.addKeyListener(new KeyAdapter() {//엔터를 치면 작동하는 리스너를 하나 만들었음
			
			@Override
			public void keyReleased(KeyEvent e) {//안에 내용은 나중에 네트워크랑 전송되는 방식으로 바꿀예정
				if (isEnter(e)) {
					//pressEnter(tf.getText().replaceAll("\n", ""));
					newPnl(tf.getText());
					tf.setText("");
					revalidate();
					repaint();
				}
			}
		});

		mb.add(screenMenu);//메뉴바에 메뉴추가
		mb.add(screenMenu2);
		mb.add(screenMenu3);
		mb.add(screenMenu4);

		southpnl.add(idcombo);//밑에 콤보박스랑 텍스트필드 추가
		southpnl.add(tf);

		add(sp);//프레임에 모든 컴포넌트 추가
		add(mb, "North");
		add(southpnl, "South");

		showGUI();//보이기

	}
	
	private boolean isEnter(KeyEvent e) {//엔터를 쳤는지 확인하기
		return e.getKeyCode() == KeyEvent.VK_ENTER;
	}

	private void newPnl(String st) {//채팅창에 보이게 할때 패널을 하나 만들어서 거기에 레이블추가하는 방식으로 하기 그러면 나중에 이미지나 여러 레이블을 넣기 편하다
		JPanel newPnl = new JPanel();
		newPnl.setLayout(new BorderLayout());
		newPnl.setBackground(Color.pink);
		JLabel lbl = new JLabel(st);
		lbl.setOpaque(true);
		newPnl.setOpaque(true);
		lbl.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		if (flag==0) {//요건 잘되는지 확인해볼려고 그냥 만든거
			lbl.setBackground(Color.GRAY);
			lbl.setHorizontalAlignment(JLabel.LEFT);
			flag=1;
		}else if(flag==1) {
			lbl.setBackground(Color.YELLOW);
			lbl.setHorizontalAlignment(JLabel.RIGHT);
			flag=0;
		}
		newPnl.add(lbl);
		
		pnl.add(newPnl);
	}
	
	public void exit() {//종료하기
		dispose();
	}

	public void showGUI() {//보이기
		setSize(300, 500);
		setVisible(true);

	}

	public static void main(String[] args) {//이 클래스가 잘되는지 확인용 메인 메소드
		new MainChatDialog("자바");
	}

}
