package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CalendarPad extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int year,month,day;
	CalendarMessage calendarMessage;
	JTextField[] showDay;
	JLabel title[];
	String[] 星期={"SUN 日","MON 一","TUE 二","WED 三","THU 四","FRI 五","SAT 六"};
	JPanel north,center;
	public CalendarPad(){
		setLayout(new BorderLayout());
		north=new JPanel();
		north.setLayout(new GridLayout(1,7));
		center=new JPanel();
		center.setLayout(new GridLayout(6,7));
		add(center,BorderLayout.CENTER);
		add(north,BorderLayout.NORTH);
		title=new JLabel[7];
		for(int j=0;j<7;j++){
			title[j]=new JLabel();
			title[j].setFont(new Font("TimesRoman",Font.BOLD,12));
			title[j].setText(星期[j]);
			title[j].setHorizontalAlignment(JLabel.CENTER);
			title[j].setBorder(BorderFactory.createRaisedBevelBorder());
			north.add(title[j]);
		}
		title[0].setForeground(Color.red);
		title[6].setForeground(Color.blue);
	}
	public void setShowDayTextField(JTextField[] text){
		showDay=text;
		for(int i=0;i<showDay.length;i++){
			showDay[i].setFont(new Font("TimesRoman",Font.BOLD,15));
			showDay[i].setHorizontalAlignment(JTextField.CENTER);
			showDay[i].setEditable(false);
			center.add(showDay[i]);
		}
	}
	public void setCalendarMessage(CalendarMessage calendarMessage){
		this.calendarMessage=calendarMessage;
	}
	public void showMonthCalendar(){
		String[] a=calendarMessage.getMonthCalendar();
		for(int i=0;i<42;i++)
			showDay[i].setText(a[i]);
		validate();
	}
}
