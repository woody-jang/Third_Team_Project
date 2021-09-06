package gui.chat;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import client.Client;
import shared.ChatList;
import shared.ChatRoom;
import shared.GreenProtocol;

public class CreateChatRoomDialog extends JDialog {
	private JTextField tf;
	String today = LocalDate.now().toString();
	public CreateChatRoomDialog(ChatList cl) {
		setLayout(new GridLayout(0, 1));

		JPanel pnlRbs = new JPanel();
		JRadioButton rb1 = new JRadioButton("자바");
		JRadioButton rb2 = new JRadioButton("파이썬");
		JRadioButton rb3 = new JRadioButton("C언어");

		ButtonGroup group = new ButtonGroup();
		group.add(rb1);
		group.add(rb2);
		group.add(rb3);

		pnlRbs.add(rb1);
		pnlRbs.add(rb2);
		pnlRbs.add(rb3);

		add(pnlRbs);

		JPanel pnl2 = new JPanel();
		JLabel lbl = new JLabel("방제 : ");
		JLabel lblDate = new JLabel(today);
		lblSubject = new JLabel();
		lblSubject.setPreferredSize(new Dimension(50, 30));
		tf = new JTextField(10);
		tf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char enter = e.getKeyChar();
				if(enter==KeyEvent.VK_ENTER) {
					btn.doClick();
				}
			}
		});
		pnl2.add(lbl);
		pnl2.add(lblDate);
		pnl2.add(lblSubject);
		pnl2.add(tf);
		add(pnl2);

		JPanel pnl3 = new JPanel();
		btn = new JButton("생성");
		pnl3.add(btn);
		add(pnl3);
		
		for (Map.Entry<String, Integer> entry : cl.getMap().entrySet()) {
			int value = entry.getValue();
			switch (value) {
			case 10:	rb1.setEnabled(false);	break;
			case 20:	rb2.setEnabled(false);	break;
			case 30:	rb3.setEnabled(false);	break;
			}
		}
		
		rb1.addActionListener(actionListener);
		rb2.addActionListener(actionListener);
		rb3.addActionListener(actionListener);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String title = tf.getText().trim();
				if (title.isEmpty()) {
					JOptionPane.showMessageDialog(null, "방제를 입력해주세요");
					return;
				}
				if (!rb1.isSelected()&&!rb2.isSelected()&&!rb3.isSelected()) {
					JOptionPane.showMessageDialog(null, "과목을 선택해주세요");
					return;
				}

				int subjectCode = 0;
				if (rb1.isSelected()) {
					subjectCode = 10;
				} else if (rb2.isSelected()) {
					subjectCode = 20;
				} else if (rb3.isSelected()) {
					subjectCode = 30;
				}

				title = today+" "+lblSubject.getText()+"_"+title;
				
				ChatRoom cr = new ChatRoom(title, subjectCode, "", Client.user);
				Client.service.makeChat(cr);
			
				dispose();
			}
		});

		setModal(true);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	ActionListener actionListener = new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	    	  lblSubject.setText(((JRadioButton)e.getSource()).getText());
	      }
	   };
	private JLabel lblSubject;
	private JButton btn;
}
