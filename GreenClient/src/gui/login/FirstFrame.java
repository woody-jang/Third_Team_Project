package gui.login;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FirstFrame extends JFrame {
	private CardLayout cardLayout;
	private LoginCard loginCard;
	private JoinCard joinCard;
	private FindCard findCard;
	
	public FirstFrame() {
		setTitle("그린 아카데미");
		
		Container pnl = getContentPane();

		cardLayout = new CardLayout();
		setLayout(cardLayout);
		loginCard = new LoginCard(cardLayout,this);
		joinCard = new JoinCard(cardLayout);
		findCard = new FindCard(cardLayout);
		
		add("login", loginCard);
		add("join", joinCard);
		add("find", findCard);
		
		
		showGUI();
	}

	

	private void showGUI() {
		setSize(400,500);
//		setLocationRelativeTo(null);		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new FirstFrame();
	}

}
