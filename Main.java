package calendar;

import java.util.Calendar;

import javax.swing.JFrame;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame myframe=new JFrame("日历记事本");
		Calendar calendar=Calendar.getInstance();           
        int y=calendar.get(Calendar.YEAR);            
        int m=calendar.get(Calendar.MONTH)+1;         
        int d=calendar.get(Calendar.DAY_OF_MONTH);
        myframe.setContentPane(new CalendarPad(y,m,d));
        myframe.setVisible(true);
        myframe.pack();

	}

}
