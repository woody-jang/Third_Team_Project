package gui.login;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import shared.GreenProtocol;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

public class FindCard extends JPanel implements ItemListener ,ActionListener{
	private CardLayout cardLayout;
	private String protocol;
	private JTextField tfName, tfBirth, tfID;
	private JRadioButton findID, findPW;
	private JButton btnFind;
	private JButton btnBack;
	private JFormattedTextField tfPhone;

	public FindCard(CardLayout cardLayout) {
		this.cardLayout = cardLayout;

		JPanel pnlAll = new JPanel();
		pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));
		
		JPanel pnlTitle = new JPanel();
		JLabel lblTitle = new JLabel("ID/PW 찾기");
		lblTitle.setFont(new Font("굴림", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pnlTitle.add(lblTitle);
		
		JPanel pnlRdBtn = new JPanel();
		findID = new JRadioButton("ID 찾기");
		findID.setHorizontalAlignment(SwingConstants.CENTER);
		findPW = new JRadioButton("PW 찾기");
		findPW.setHorizontalAlignment(SwingConstants.CENTER);
		pnlRdBtn.add(findID);
		pnlRdBtn.add(findPW);
		ButtonGroup group = new ButtonGroup();
		group.add(findID);
		group.add(findPW);

		JPanel pnlName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblName = new JLabel("이름");
		tfName = new JTextField(10);
		pnlName.add(lblName);
		pnlName.add(tfName);

		JPanel pnlBirth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblBirth = new JLabel("생년월일");
		tfBirth = new JTextField(10);
		pnlBirth.add(lblBirth);
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

		JPanel pnlID = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblID = new JLabel("아이디");
		tfID = new JTextField(10);
		pnlID.add(lblID);
		pnlID.add(tfID);

		findID.addItemListener(this);
		findPW.addItemListener(this);
		findID.setSelected(true);
		
		JPanel pnlBtn = new JPanel();
		btnFind = new JButton("찾기");
		btnBack = new JButton("뒤로 가기");
		pnlBtn.add(btnFind);
		pnlBtn.add(btnBack);
		
		pnlAll.add(pnlTitle);
		pnlAll.add(pnlRdBtn);
		pnlAll.add(pnlName);
		pnlAll.add(pnlBirth);
		pnlAll.add(pnlPhone);
		pnlAll.add(pnlID);
		pnlAll.add(pnlBtn);
		
		add(pnlAll);
		
		btnFind.addActionListener(this);
		btnBack.addActionListener(this);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (findID.isSelected()) {
			tfID.setText("");
			tfID.setEditable(false);
			protocol=GreenProtocol.FIND_ID;
		} else {
			tfID.setEditable(true);
			protocol=GreenProtocol.FIND_PW;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btnFind) {
			// TODO 액션리스너로 아이디 찾기 구현해야함
			
		}
		if (e.getSource()==btnBack) {
			cardLayout.show(getParent(), "login");
			reset();
		}
	}

	private void reset() {
		tfName.setText("");
		tfBirth.setText("");
		tfPhone.setValue("");
		tfID.setText("");
		findID.setSelected(true);
	}
}
