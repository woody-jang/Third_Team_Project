package gui.login;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.Client;


public class FirstFrame extends JFrame {
	private CardLayout cardLayout;
	private LoginCard loginCard;
	private JoinCard joinCard;
	private FindCard findCard;
	private ObjectOutputStream oos;
	
	public FirstFrame(ObjectOutputStream oos) {
		this.oos =oos;
		setTitle("그린 아카데미");
		
		Container pnl = getContentPane();

		cardLayout = new CardLayout();
		setLayout(cardLayout);
		loginCard = new LoginCard(cardLayout,this, oos);
		joinCard = new JoinCard(cardLayout, oos);
		findCard = new FindCard(cardLayout);
		
		add("login", loginCard);
		add("join", joinCard);
		add("find", findCard);
		
		addWindowListener(new WindowAdapter() {
			@Override // 9/6수정
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(FirstFrame.this, "정말로 종료 하시겠습니까?", "",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					Client.service.ExitGUI();
					System.exit(0);
				}
			}
		});
		
		showGUI();
	}
	
	private void showGUI() {
		setSize(400,500);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	public LoginCard getLoginCard() {
		return loginCard;
	}

	public JoinCard getJoinCard() {
		return joinCard;
	}

}
