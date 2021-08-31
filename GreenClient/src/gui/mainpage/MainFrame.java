package gui.mainpage;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import gui.chat.MainChatDialog;

public class MainFrame extends JFrame {

	public MainFrame() {

		JPanel btnspnl = new JPanel();
		JButton btn1 = new JButton("대화");
		JButton btn2 = new JButton("달력");
		JButton btn3 = new JButton("마이");
		JButton btn4 = new JButton("아웃");
		JButton btn5 = new JButton("새로");

		btnspnl.add(btn1);
		btnspnl.add(btn2);
		btnspnl.add(btn3);
		btnspnl.add(btn4);
		btnspnl.add(btn5);
		add(btnspnl, "West");
		btnspnl.setBackground(Color.GRAY);
		btnspnl.setLayout(new BoxLayout(btnspnl, BoxLayout.Y_AXIS));

		CardLayout card = new CardLayout();
		JPanel cardpnl = new JPanel(card);
		JPanel mainpnl = new JPanel();
		mainpnl.setLayout(new BoxLayout(mainpnl, BoxLayout.Y_AXIS));
		JPanel calendar = new Calendar();
		Mypage mypage = new Mypage();
		cardpnl.add(mainpnl, "메인");
		cardpnl.add(calendar, "달력");
		cardpnl.add(mypage, "마이페이지");

		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				card.show(cardpnl, "메인");
			}
		});

		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				card.show(cardpnl, "달력");
			}
		});

		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				card.show(cardpnl, "마이페이지");
			}
		});

		JPanel toppnl = new JPanel();
		JLabel lbl = new JLabel("대화");
		JButton create = new JButton("채팅방만들기");
		toppnl.add(lbl);
		toppnl.add(create);
		
		mainpnl.add(toppnl);

		JPanel midpnl = new JPanel();
		midpnl.setLayout(new BoxLayout(midpnl, BoxLayout.Y_AXIS));
		JPanel pnllbl = new JPanel();
		JButton btnjava = new JButton("2021-09-10  자바");
		btnjava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainChatDialog("hello");
			}
		});
		pnllbl.add(btnjava);
		midpnl.add(pnllbl);

		mainpnl.add(midpnl);

		add(cardpnl);

		showGUI();
	}

	private void showGUI() {
		setSize(400, 500);
//	  pack(); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}

}