package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MakeButton extends JButton implements ActionListener{
	private String subject;
	private int subjectCode;
	
	public MakeButton(String title, int subjectCode) {
		this.subjectCode = subjectCode; 
		setText(title);
		switch (subjectCode) {
		case 10: subject ="java";	break;
		case 20: subject ="python";	break;
		case 30: subject ="c";		break;
		}

		addActionListener(this);
	}
	
	public String getSubject() {
		return subject;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Client.service.chatJoin(subjectCode);
	}

	public int getSubjectCode() {
		return subjectCode;
	}
	
	
}