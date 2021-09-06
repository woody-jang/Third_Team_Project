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

public class JoinCard extends JPanel {

//	private Service service;
	private CardLayout cardLayout;
	private JComboBox<String> cbSubject;
	private JTextField tfName, tfBirth;
	private JPasswordField tfPw, tfPw2;
	private JButton btnJoin;
	private JButton btnReset;
	private JButton btnBack;
	private JFormattedTextField tfPhone;
	private ObjectOutputStream oos;

	public JoinCard(CardLayout cardLayout, ObjectOutputStream oos) {
		this.cardLayout = cardLayout;
		this.oos = oos;

		JPanel pnlAll = new JPanel();
		pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));

		JPanel pnlTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTitle = new JLabel("회원 가입");
		lblTitle.setFont(new Font("굴림", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pnlTitle.add(lblTitle);

		JPanel pnlSubject = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSubject = new JLabel("강의선택");
		cbSubject = new JComboBox<String>(new String[] { "JAVA", "PYTHON", "C" });
		pnlSubject.add(lblSubject);
		pnlSubject.add(cbSubject);

		JPanel pnlName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblName = new JLabel("이름");
		tfName = new JTextField(10);
		pnlName.add(lblName);
		pnlName.add(tfName);

		JPanel pnlBirth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblBirth = new JLabel("생년월일");
		MaskFormatter formatterBirth = null;
		try {
			formatterBirth = new MaskFormatter("######");
			formatterBirth.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tfBirth = new JFormattedTextField(formatterBirth);
		tfBirth.setColumns(10);
		pnlBirth.add(lblBirth);

		// TODO 생년월일 확인하는거 넣어주세요
//		if(month > 12 || month < 1) {
//			e.consume(); // 안써지게함
//		}
//		int day = birth % 100;
//		if(day > 31 || day < 1 ) {
//			e.consume(); // 안써지게함
//		}

		pnlBirth.add(tfBirth);
		
		JPanel pnlPhone = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPhone = new JLabel("전화번호");
		MaskFormatter formatterPhone = null;
		try {
			formatterPhone = new MaskFormatter("010-####-####");
			formatterPhone.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tfPhone = new JFormattedTextField(formatterPhone);
		tfPhone.setColumns(10);
		pnlPhone.add(lblPhone);
		pnlPhone.add(tfPhone);

		JPanel pnlPw = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPw = new JLabel("비밀번호");
		tfPw = new JPasswordField(10);
		pnlPw.add(lblPw);
		pnlPw.add(tfPw);

		JPanel pnlPw2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPw2 = new JLabel("비밀번호 확인");
		tfPw2 = new JPasswordField(10);
		tfPw2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnJoin.doClick();
			}
		});
		pnlPw2.add(lblPw2);
		pnlPw2.add(tfPw2);

		JPanel pnlBtn = new JPanel();
		btnJoin = new JButton("회원 가입");
		pnlBtn.add(btnJoin);

		JPanel pnlBtn2 = new JPanel();
		btnReset = new JButton("Reset");
		btnBack = new JButton("뒤로 가기");

		pnlBtn2.add(btnReset);
		pnlBtn2.add(btnBack);

		pnlAll.add(pnlTitle);
		pnlAll.add(pnlSubject);
		pnlAll.add(pnlName);
		pnlAll.add(pnlBirth);
		pnlAll.add(pnlPhone);
		pnlAll.add(pnlPw);
		pnlAll.add(pnlPw2);
		pnlAll.add(pnlBtn);
		pnlAll.add(pnlBtn2);

		add(pnlAll);

		btnJoin.addActionListener(al);
		btnReset.addActionListener(al);
		btnBack.addActionListener(al);
	}

	ActionListener al = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnJoin) {
				if (tfPw.getText().trim().equals(tfPw2.getText())) {
					if (e.getSource() == btnJoin) {
						User user = new User();
						user.setSubject((cbSubject.getSelectedIndex() + 1) * 10);// 과목 콤보박스 고른거 저장
						user.setProtocol(GreenProtocol.JOIN);
						user.setName(tfName.getText());
						user.setBirth(tfBirth.getText());
						user.setPassword(tfPw.getText());
						user.setPhone(tfPhone.getText());
						
						Client.service.join(user);
					}
				}

			} else {
				if (e.getSource() == btnBack) {
					cardLayout.show(getParent(), "login");
				}
				reset();
			}
		}
	};

	private void reset() {
		tfName.setText("");
		tfBirth.setText("");
		tfPw.setText("");
		tfPw2.setText("");
		tfPhone.setValue("");
	}
	
	public JButton getBtnBack() {
		return btnBack;
	}
}
