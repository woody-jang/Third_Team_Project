package gui.admin;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import client.Client;
import client.Service;
import shared.GreenProtocol;
import shared.User;

public class AddTeacher extends JDialog {
	private AdminFrame af;
	
	private JTextField tfName;
	private JTextField tfBirth;
	private JPasswordField tfPw;
	private JFormattedTextField tfPhone;
	public AddTeacher() {
		JPanel pnlAll = new JPanel();
		pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));

		JPanel pnlTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lblTitle = new JLabel("선생님 정보 추가");
		lblTitle.setFont(new Font("굴림", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pnlTitle.add(lblTitle);

		JPanel pnlName = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lblName = new JLabel("이름");
		tfName = new JTextField(10);
		pnlName.add(lblName);
		pnlName.add(tfName);

		JPanel pnlBirth = new JPanel(new FlowLayout(FlowLayout.CENTER));
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

		JPanel pnlPw = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lblPw = new JLabel("비밀번호");
		tfPw = new JPasswordField(10);
		pnlPw.add(lblPw);
		pnlPw.add(tfPw);

		JPanel pnlPw2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lblPw2 = new JLabel("비밀번호 확인");
		JPasswordField tfPw2 = new JPasswordField(10);
		pnlPw2.add(lblPw2);
		pnlPw2.add(tfPw2);

		JPanel pnlPhone = new JPanel(new FlowLayout(FlowLayout.CENTER));
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

		JPanel pnlBtn = new JPanel();
		JButton btnJoin = new JButton("만들기");
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User newTeacher = new User();
				newTeacher.setProtocol(GreenProtocol.JOIN_TEACHER);
				newTeacher.setName(tfName.getText());
				newTeacher.setBirth(tfBirth.getText());
				newTeacher.setPassword(tfPw.getText());
				newTeacher.setPhone(tfPhone.getText());
				
				Client.service.join_teacher(newTeacher);
				dispose();
			}
		});
		
		pnlBtn.add(btnJoin);

		JPanel pnlBtn2 = new JPanel();
		JButton btnReset = new JButton("Reset");

		pnlBtn2.add(btnReset);
		pnlAll.add(pnlTitle);
		pnlAll.add(pnlName);
		pnlAll.add(pnlBirth);
		pnlAll.add(pnlPw);
		pnlAll.add(pnlPw2);
		pnlAll.add(pnlPhone);
		pnlAll.add(pnlBtn);
		pnlAll.add(pnlBtn2);

		add(pnlAll);
		
		showGUI();
	}
	private void showGUI() {
		setModal(true);
		setSize(400, 500);
		setVisible(true);
	}
}
