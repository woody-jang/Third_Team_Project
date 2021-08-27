package gui.mainpage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class Mypage extends JPanel{
	public Mypage() {
		JPanel pnlAll = new JPanel();
		
		JPanel pnlImage = new JPanel();
//		URL Profile = Mypage.class.getClassLoader().getResource("profile.jpg");
		ImageIcon iconlogo = new ImageIcon(".\\profile.jpg");
		JLabel lblProfile = new JLabel(iconlogo);
		pnlImage.add(lblProfile);
		
		JPanel pnlInfo = new JPanel();
		JTextField tfName = new JTextField("꽥꽥이");
		JTextField tfMymessage = new JTextField("ㄴr는 ㄱr끔 눈물을 흘린ㄷr");
		JPanel pnlId = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblId1 = new JLabel("아이디");
		lblId1.setForeground(new Color(100,100,100));
		JLabel lblId2 = new JLabel("2110001");
		pnlId.add(lblId1);
		pnlId.add(lblId2);
		JPanel pnlPw = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setForeground(new Color(100,100,100));
		JPasswordField tfPw = new JPasswordField("qqqqq");
		pnlPw.add(lblPw);
		pnlPw.add(tfPw);
		JPanel pnlPhone = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPhone = new JLabel("전화번호");
		lblPhone.setForeground(new Color(100,100,100));
		MaskFormatter formatterPhone = null;
		try {
			formatterPhone = new MaskFormatter("010-####-####");
			formatterPhone.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JFormattedTextField tfPhone = new JFormattedTextField(formatterPhone);
		pnlPhone.add(lblPhone);
		pnlPhone.add(tfPhone);
		JPanel pnlBirth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblBirth1 = new JLabel("생년월일");
		lblBirth1.setForeground(new Color(100,100,100));
		JLabel lblBirth2 = new JLabel("940528");
		pnlBirth.add(lblBirth1);
		pnlBirth.add(lblBirth2);
		JPanel pnlSubject = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSubject1 = new JLabel("과정");
		lblSubject1.setForeground(new Color(100,100,100));
		JLabel lblSubject2 = new JLabel("JAVA");
		pnlSubject.add(lblSubject1);
		pnlSubject.add(lblSubject2);
		pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
		pnlInfo.add(tfName);
		pnlInfo.add(tfMymessage);
		pnlInfo.add(pnlId);
		pnlInfo.add(pnlPw);
		pnlInfo.add(pnlPhone);
		pnlInfo.add(pnlBirth);
		pnlInfo.add(pnlSubject);
		
		JPanel pnlBtn = new JPanel();
		JButton btnModify = new JButton("수정");
		JButton btnOk = new JButton("확인");
		pnlBtn.add(btnModify);
		pnlBtn.add(btnOk);
		
		pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));
		pnlAll.add(pnlImage);
		pnlAll.add(pnlInfo);
		pnlAll.add(pnlBtn);
		
		add(pnlAll);
	}
}

