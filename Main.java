package calendar;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame myframe=new JFrame("日历记事本");
		myframe.setLayout(new BorderLayout());
		myframe.setLocation(200, 300);
		myframe.setContentPane(new CalendarWindow());
		myframe.setVisible(true);
		myframe.pack();

	}

}
