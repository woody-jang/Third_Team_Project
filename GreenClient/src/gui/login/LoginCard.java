package gui.login;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ObjectOutputStream;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import client.Client;
import shared.GreenProtocol;
import shared.User;

public class LoginCard extends JPanel implements ActionListener {
	
	
	private CardLayout cardLayout;
	private JTextField tf;
	private JPasswordField pf;
	private JButton btnLogin, btnJoin, btnFind;
	private FirstFrame firstFrame;
	private ObjectOutputStream oos;

	public LoginCard(CardLayout cardLayout, FirstFrame firstFrame, ObjectOutputStream oos) {
		
		this.cardLayout = cardLayout;
		this.firstFrame = firstFrame;
		this.oos = oos;
		JPanel pnlAll = new JPanel();
		pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));
		
		JPanel pnlTitle = new JPanel();
		JLabel lblTitle = new JLabel("그린 아카데미");
		lblTitle.setFont(new Font("굴림", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pnlTitle.add(lblTitle);
		pnlAll.add(pnlTitle);
		
		JPanel pnlID = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("굴림", Font.PLAIN, 15));
		MaskFormatter formatterId = null;
		try {
			formatterId = new MaskFormatter("#######");
			formatterId.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tf = new JFormattedTextField(formatterId);
		tf.setColumns(20);
		tf.setFont(new Font("굴림", Font.PLAIN, 20));
		pnlID.add(lblID);
		pnlID.add(tf);
		pnlAll.add(pnlID);

		JPanel pnlPW = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPW = new JLabel("PW");
		lblPW.setFont(new Font("굴림", Font.PLAIN, 15));
		pf = new JPasswordField(20);
		pf.setFont(new Font("굴림", Font.PLAIN, 20));
		pnlPW.add(lblPW);
		pnlPW.add(pf);
		pnlAll.add(pnlPW);

		JPanel pnlBtn = new JPanel();
		btnLogin = new JButton("로그인");
		
		pf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
		});
		
		btnJoin = new JButton("회원가입");
		btnFind = new JButton("ID/PW 찾기");
		pnlBtn.add(btnLogin);
		pnlBtn.add(btnJoin);
		pnlBtn.add(btnFind);
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		btnFind.addActionListener(this);
		pnlAll.add(pnlBtn);
		
		add(pnlAll);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btnLogin) {
			
			if (tf.getText().trim().equals("") || pf.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(LoginCard.this, "빈칸없이 입력해주세요");
			} else if (!tf.getText().trim().equals("") && !pf.getText().trim().equals("")) {
				int id = Integer.parseInt(tf.getText());
				String pw = new String(pf.getPassword());
				User user = new User();
				user.setProtocol(GreenProtocol.LOGIN);// "login"
				user.setId(id);
				user.setPassword(pw);
				
				Client.service.login(user);
			}
//			new MainFrame();
//			firstFrame.dispose();
//			int id = Integer.parseInt(tf.getText());
//			String pw = new String(pf.getPassword());
			//TODO 로그인 기능 구현
		}
		if (e.getSource()==btnJoin) {
			cardLayout.show(getParent(), "join");
		}
		if (e.getSource()==btnFind) {
			cardLayout.show(getParent(), "find");
		}
	}
	
	public JTextField getTf() {
		return tf;
	}

}
