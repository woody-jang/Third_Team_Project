package gui.mainpage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import client.Client;
import gui.chat.MainChatDialog;
import shared.ChatMessage;
import shared.User;

public class MainFrame extends JFrame implements ActionListener {

	private CardLayout card;
	private Calendar calendar;
	private Mypage mypage;
	private JPanel cardpnl, mainpnl, midpnl;
	public JButton btn1, btn2, btn3, btnRepaint, btnLogOut, btnCreate;
	private User user;
	private MainChatDialog mcd;
	public Map<Integer, MainChatDialog> mcdMap;

	public MainFrame(User user) {
		this.user = user;
		if (user.isTeacher())
			mcdMap = new HashMap<Integer, MainChatDialog>();

		JPanel btnspnl = new JPanel();
		btn1 = new JButton("대화");
		btn2 = new JButton("채팅기록");
		btn3 = new JButton("마이");
		btnRepaint = new JButton(" 새로고침 ");
		btnLogOut = new JButton(" 로그아웃 ");

		btnspnl.add(btn1);
		btnspnl.add(btn2);
		btnspnl.add(btn3);
		btnspnl.add(btnRepaint);
		btnspnl.add(btnLogOut);
		add(btnspnl, "West");
		btnspnl.setBackground(Color.GRAY);
		btnspnl.setLayout(new BoxLayout(btnspnl, BoxLayout.Y_AXIS));
		card = new CardLayout();
		cardpnl = new JPanel(card);
		mainpnl = new JPanel();
		mainpnl.setLayout(new BorderLayout());
		calendar = new Calendar();
		mypage = new Mypage(user);
		cardpnl.add(mainpnl, "메인");
		cardpnl.add(calendar, "채팅기록");
		cardpnl.add(mypage, "마이페이지");

		JPanel toppnl = new JPanel();
		JLabel lbl = new JLabel("대화");
		btnCreate = new JButton("채팅방만들기");
		toppnl.add(lbl);
		toppnl.add(btnCreate);

		mainpnl.add(toppnl, "North");

		midpnl = new JPanel();
		midpnl.setLayout(new GridLayout(0, 1));

		mainpnl.add(midpnl);

		add(cardpnl);

		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btnRepaint.addActionListener(this);
		btnLogOut.addActionListener(this);
		btnCreate.addActionListener(this);

		btnCreate.setEnabled(user.isTeacher());

		showGUI();

		addWindowListener(new WindowAdapter() {
			@Override // 9/6수정
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(MainFrame.this, "정말로 종료 하시겠습니까?", "",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					Client.service.ExitGUI();
					System.exit(0);
				}
			}
		});
	}

	private void showGUI() {
		setSize(400, 500);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		btnRepaint.doClick();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn1) {
			card.show(cardpnl, "메인");
		} else if (e.getSource() == btn2) {
			card.show(cardpnl, "채팅기록");
		} else if (e.getSource() == btn3) {
			card.show(cardpnl, "마이페이지");
		} else if (e.getSource() == btnRepaint) { // 새로 고침
			Client.service.requestBtnRefresh(); // 채팅방버튼 새로고침
		} else if (e.getSource() == btnLogOut) { // 로그아웃
			Client.service.logOut();
		} else if (e.getSource() == btnCreate) {
			Client.service.makeChat();
		}
	}

	public JPanel getMidpnl() {
		return midpnl;
	}

	public User getUser() {
		return user;
	}

	public void passMCDannounce(String announce) {
		mcd.announce(announce);
	}

	public void passMCDmymessage(ChatMessage message) {
		mcd.myMessage(message);
	}

	public void passMCDothermessage(ChatMessage message) {
		mcd.otherMessage(message);
	}

	public void passMCDmyFile(ChatMessage cm) {
		mcd.myFile(cm);
	}

	public void passMCDmyPicture(ChatMessage cm) {
		mcd.myPicture(cm);

	}

	public MainChatDialog getMcd() {
		return mcd;
	}

	public void setMcd(MainChatDialog mcd) {
		this.mcd = mcd;
	}

	public Mypage getMypage() {
		return mypage;
	}

}