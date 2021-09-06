package gui.mainpage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.*;

import client.Client;
import gui.chat.ShowChatLogDialog;
import shared.ChatRoom;

public class Calendar extends JPanel {
	LocalDate localdate;
	private LocalDate today;
	private JPanel happnl;
	private LocalDate today2; // 현재 날짜 비교용
	private JPanel classpnl;

	public Calendar() {
		happnl = new JPanel();
		happnl.setLayout(new BoxLayout(happnl, BoxLayout.Y_AXIS));

		// 달력
		today = LocalDate.now();
		happnl.add(makeCalendar());

		classpnl = new JPanel();
		JScrollPane scrl = new JScrollPane(classpnl);
//		classpnl.setPreferredSize(new Dimension(270, 200));
//		classpnl.setOpaque(true);
		classpnl.setLayout(new BoxLayout(classpnl, BoxLayout.Y_AXIS));
		createChatLogListPnl();
		scrl.setPreferredSize(new Dimension(270, 250));
		scrl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		happnl.add(scrl);

		add(happnl);
	}

	private void createChatLogListPnl() {
		List<ChatRoom> chatRoomList = Client.getChatRoomList();

		if (chatRoomList == null) {
			return;
		}

		for (ChatRoom chatRoom : chatRoomList) {
			JPanel chatRoomPnl = new JPanel();
			JLabel chatRoomLbl = new JLabel(chatRoom.getTitle());
			chatRoomLbl.setPreferredSize(new Dimension(240, 30));
			chatRoomLbl.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			chatRoomLbl.setFont(new Font(chatRoomLbl.getFont().getName(), Font.PLAIN, 15));
			chatRoomLbl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					new ShowChatLogDialog(chatRoom.getChatLog(), chatRoomLbl.getText());
				}
			});
			chatRoomPnl.add(chatRoomLbl);
			classpnl.add(chatRoomPnl);
		}
		
		revalidate();
		repaint();

	}

	private JPanel makeCalendar() {

		JPanel calpnl = new JPanel();
		calpnl.setLayout(new BoxLayout(calpnl, BoxLayout.Y_AXIS));
		calpnl.setMaximumSize(new Dimension(200, 200));
		calpnl.setPreferredSize(new Dimension(200, 200));

		JPanel yearMonth = new JPanel();
		yearMonth.setPreferredSize(new Dimension(200, 25));
		yearMonth.setMinimumSize(new Dimension(200, 25));
		yearMonth.setMaximumSize(new Dimension(200, 25));
		JLabel left = new JLabel("   <<   ");
		left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				happnl.removeAll();
				today = today.minusMonths(1);
				happnl.add(makeCalendar());

				revalidate();
				repaint();
			}
		});
		int year = today.getYear();
		int month = today.getMonthValue();
		JLabel yearAndMonth = new JLabel(year + "년 " + month + "월");
		JLabel right = new JLabel("   >>   ");
		today2 = LocalDate.now();
		// 현재 날짜보다 날짜를 더 옮길 수 없게
		if (today.equals(today2)) {
			right.setEnabled(false);
		} else {
			right.setEnabled(true);
			right.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					happnl.removeAll();
					today = today.plusMonths(1);
					happnl.add(makeCalendar());

					revalidate();
					repaint();
				}
			});
		}

		yearMonth.add(left);
		yearMonth.add(yearAndMonth);
		yearMonth.add(right);

		JPanel whenAndDate = new JPanel();
		whenAndDate.setLayout(new BoxLayout(whenAndDate, BoxLayout.Y_AXIS));
		JPanel title = new JPanel(new GridLayout(0, 7));
		title.setPreferredSize(new Dimension(200, 25));
		title.setMinimumSize(new Dimension(200, 25));
		title.setMaximumSize(new Dimension(200, 25));
		String titleStr[] = { "일", "월", "화", "수", "목", "금", "토" };
		for (int i = 0; i < titleStr.length; i++) {
			JLabel titlelbl = new JLabel(titleStr[i]);
			titlelbl.setHorizontalAlignment(JLabel.CENTER);
			title.add(titlelbl);
		}

		whenAndDate.add(title);

		JPanel date = new JPanel(new GridLayout(0, 7));
		int when = today.withDayOfMonth(1).getDayOfWeek().getValue();
		for (int i = 0; i < when; i++) {
			if (when == 7) {
				break;
			}
			JLabel nulllbl = new JLabel();
			date.add(nulllbl);
		}
		for (int i = 1; i <= today.lengthOfMonth(); i++) {
			JLabel datelbl = new JLabel(String.valueOf(i));
			datelbl.setHorizontalAlignment(JLabel.CENTER);
			String nowdate = null;
			String today2s = null;
			if (month >= 10) {
				nowdate = String.valueOf(year) + String.valueOf(month) + String.valueOf(i);
			} else {
				nowdate = String.valueOf(year) + "0" + String.valueOf(month) + String.valueOf(i);
			}
			if (today2.getMonthValue() >= 10) {
				today2s = String.valueOf(today2.getYear()) + String.valueOf(today2.getMonthValue())
						+ String.valueOf(today2.getDayOfMonth());
			} else {
				today2s = String.valueOf(today2.getYear()) + "0" + String.valueOf(today2.getMonthValue())
						+ String.valueOf(today2.getDayOfMonth());
			}
			if (Integer.valueOf(today2s) < Integer.valueOf(nowdate)) {
				datelbl.setEnabled(false);
			} else {
				datelbl.setEnabled(true);
				datelbl.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int year = today.getYear();
						int month = today.getMonthValue();
						int day = Integer.parseInt(((JLabel) e.getSource()).getText());
						StringBuilder sb = new StringBuilder();
						sb.append(year);
						sb.append("-");
						sb.append((month > 9) ? month : "0" + month);
						sb.append("-");
						sb.append((day > 9) ? day : "0" + day);
						classpnl.removeAll();
						Client.service.requestChatLogList(sb.toString(), Client.user.getSubject());
						try {
							Thread.sleep(700);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						createChatLogListPnl();
					}
				});
			}
			date.add(datelbl);
		}

		whenAndDate.add(date);

		calpnl.add(yearMonth);
		calpnl.add(whenAndDate);

		return calpnl;
	}

}