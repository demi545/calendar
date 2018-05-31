package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
//import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class CalendarWindow extends JPanel implements ActionListener,MouseListener,FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int year;
	int month;
	int day;
	CalendarMessage calendarMessage=new CalendarMessage();
	CalendarPad calendarPad=new CalendarPad();
	CalendarImage calendarImage=new CalendarImage();
	NotePad notePad=new NotePad();
	JTextField showYear,showMonth;
	JTextField showDay[];
	String picturename;
	String getPicture_address;
	Clock clock=new Clock();
	JButton nextYear=new JButton("下年");
	JButton previousYear=new JButton("上年");
	JButton nextMonth=new JButton("下月");
	JButton previousMonth=new JButton("上月");
	
	JButton saveDailyRecord=new JButton("保存日志");
	JButton deleteDailyRecord=new JButton("删除日志");
	JButton readDailyRecord=new JButton("读取日志");
	
	File dir;
	Color backColor=Color.white;
	
	public CalendarWindow(){
		dir=new File("./dailyRecord");
		dir.mkdir();
		showDay=new JTextField[42];
		for(int i=0;i<showDay.length;i++){
			showDay[i]=new JTextField();
			showDay[i].setBackground(backColor);
			showDay[i].setLayout(new GridLayout(3,3));
			showDay[i].addMouseListener(this);
			showDay[i].addFocusListener(this);
		}
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		year=calendar.get(Calendar.YEAR);
		month=calendar.get(Calendar.MONTH)+1;
		day=calendar.get(Calendar.DAY_OF_MONTH);
		calendarMessage.setYear(year);
		calendarMessage.setMonth(month);
		calendarMessage.setDay(day);
		calendarPad.setCalendarMessage(calendarMessage);
		calendarPad.setShowDayTextField(showDay);
		notePad.setMessage(year,month,day);
		calendarPad.showMonthCalendar();
		doMark();
		
		picturename=getPicture_address();
		calendarImage.setImageFile(new File(picturename));
		JMenuBar menuBar=new JMenuBar();
		JMenu menusetting=new JMenu();
		JMenuItem changepicture=new JMenuItem();
		menusetting.setText("图像");
		changepicture.setText("更改图片背景");
		menusetting.add(changepicture);
		menuBar.add(menusetting);
		setJMenuBar(menuBar);
		changepicture.addActionListener(this);
		
		setLayout(new BorderLayout());
		JSplitPane splitV1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,calendarPad,calendarImage);
		JSplitPane splitV2=new JSplitPane(JSplitPane.VERTICAL_SPLIT,notePad,clock);
		JSplitPane splitH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitV1,splitV2);
		this.add(splitH,BorderLayout.CENTER);
		
		showYear=new JTextField(""+year,6);
		showYear.setFont(new Font("TimesRoman",Font.BOLD,12));
		showYear.setHorizontalAlignment(JTextField.CENTER);
		
		showMonth=new JTextField(""+month,6);
		showMonth.setFont(new Font("TimesRoman",Font.BOLD,12));
		showMonth.setHorizontalAlignment(JTextField.CENTER);
		
		nextYear.addActionListener(this);
		previousYear.addActionListener(this);
		nextMonth.addActionListener(this);
		previousMonth.addActionListener(this);
		showYear.addActionListener(this);
		
		JPanel north=new JPanel();
		north.add(previousYear);
		north.add(showYear);
		north.add(nextYear);
		north.add(previousMonth);
		north.add(showMonth);
		north.add(nextMonth);
		this.add(north,BorderLayout.NORTH);
		
		saveDailyRecord.addActionListener(this);
		deleteDailyRecord.addActionListener(this);
		readDailyRecord.addActionListener(this);
		
		JPanel pSouth=new JPanel();
		pSouth.add(saveDailyRecord);
		pSouth.add(deleteDailyRecord);
		pSouth.add(readDailyRecord);
		this.add(pSouth,BorderLayout.SOUTH);
		
		
		/*
		Box b_date=Box.createHorizontalBox();
		b_date.add(previousYear);
		b_date.add(showYear);
		b_date.add(nextYear);
		b_date.add(previousMonth);
		b_date.add(showMonth);
		b_date.add(nextMonth);
		
		Box b_daily=Box.createHorizontalBox();
		b_daily.add(saveDailyRecord);
		b_daily.add(deleteDailyRecord);
		b_daily.add(readDailyRecord);
		
		Box b_cpad=Box.createVerticalBox();
		b_cpad.add(calendarPad);
		
		Box b_npad=Box.createVerticalBox();
		b_npad.add(notePad);
		b_npad.add(clock);
		
		Box b_center=Box.createHorizontalBox();
		b_center.add(b_cpad);
		b_center.add(b_npad);
		
		Box b_all=Box.createVerticalBox();
		b_all.add(b_date);
		b_all.add(b_center);
		b_all.add(b_daily);
		
		this.add(b_all);*/
		
		setBounds(0,0,550,500);
		setVisible(true);
		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setJMenuBar(JMenuBar menuBar) {
		// TODO Auto-generated method stub
		
	}

	private String getPicture_address() {
		// TODO Auto-generated method stub
		String address=null;
		try{
			FileInputStream outOne=new FileInputStream("picture_address.txt");
			ObjectInputStream outTwo=new ObjectInputStream(outOne);
			try{
				address=(String)outTwo.readObject();
			}catch(Exception e){}
			outTwo.close();
		}catch(IOException eee){}
		if(address!=null){
			return address;
		}
		else
			return "picture.jpg";
	}

	public void setDefaultCloseOperation(int exitOnClose) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		Component com=(Component)e.getSource();
		com.setBackground(Color.pink);
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		Component com=(Component)e.getSource();
		com.setBackground(backColor);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		JTextField text=(JTextField)e.getSource();
		String str=text.getText().trim();
		try{
			day=Integer.parseInt(str);
		}
		catch(NumberFormatException exp){}
		calendarMessage.setDay(day);
		notePad.setMessage(year,month,day);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/*if(e.getActionCommand().equals("更改图片背景")){
			FileDialog dia=new FileDialog(this,"选定图片",FileDialog.LOAD);
			dia.setModal(true);
			dia.setVisible(true);
			if((dia.getDirectory()!=null)&&(dia.getFile()!=null)){
				try{
					FileOutputStream inOne=new FileOutputStream("picture_address.txt");
					ObjectOutputStream inTwo=new ObjectOutputStream(inOne);
					inTwo.writeObject(dia.getDirectory()+dia.getFile());
					inTwo.close();
				}catch(IOException ee){}
			picturename=getPicture_address();
			calendarImage.setImageFile(new File(picturename));
			}
		}*/
		if(e.getSource()==nextYear){
			year++;
			showYear.setText(""+year);
			calendarMessage.setYear(year);
			calendarPad.setCalendarMessage(calendarMessage);
			calendarPad.showMonthCalendar();
			notePad.setMessage(year,month,day);
			doMark();
		}
		else if(e.getSource()==previousYear){
			year--;
			showYear.setText(""+year);
			calendarMessage.setYear(year);
			calendarPad.setCalendarMessage(calendarMessage);
			calendarPad.showMonthCalendar();
			notePad.setMessage(year,month,day);
			doMark();
		}
		else if(e.getSource()==nextMonth){
			month++;
			if(month>12) month=1;
			showMonth.setText(""+month);
			calendarPad.setCalendarMessage(calendarMessage);
			calendarPad.showMonthCalendar();
			notePad.setMessage(year,month,day);
			doMark();
		}
		else if(e.getSource()==previousMonth){
			month--;
			if(month<1) month=12;
			showMonth.setText(""+month);
			calendarPad.setCalendarMessage(calendarMessage);
			calendarPad.showMonthCalendar();
			notePad.setMessage(year,month,day);
			doMark();
		}
		else if(e.getSource()==showYear){
			String s=showYear.getText().trim();
			char a[]=s.toCharArray();
			boolean boo=false;
			for(int i=0;i<a.length;i++){
				if(!(Character.isDigit(a[i])))
					boo=true;
			}
			if(boo==true)
				JOptionPane.showMessageDialog(this,"你输入了非法年份","警告！",JOptionPane.WARNING_MESSAGE);
			else if(boo==false)
				year=Integer.parseInt(s);
			showYear.setText(""+year);
			calendarMessage.setYear(year);
			calendarPad.setCalendarMessage(calendarMessage);
			calendarPad.showMonthCalendar();
			notePad.setMessage(year,month,day);
			doMark();
		}
		else if(e.getSource()==saveDailyRecord){
			notePad.savefile(dir,year,month,day);
			doMark();
		}
		else if(e.getSource()==deleteDailyRecord){
			notePad.deletefile(dir,year,month,day);
			doMark();
		}
		else if(e.getSource()==readDailyRecord){
			notePad.readfile(dir,year,month,day);
			doMark();
		}
		
	}
	
	public void doMark(){
		for(int i=0;i<showDay.length;i++){
			showDay[i].removeAll();
			String str=showDay[i].getText().trim();
			try{
				int n=Integer.parseInt(str);
				if(isHaveDailyRecord(n)==true){
					JLabel mess=new JLabel("yes");
					mess.setFont(new Font("TimesRoman",Font.PLAIN,11));
					mess.setForeground(Color.blue);
					showDay[i].add(mess);
				}
			}catch(Exception exp){}
		}
		calendarPad.repaint();
		calendarPad.validate();
		
	}
	
	public boolean isHaveDailyRecord(int n){
		String key=""+year+""+month+""+n;
		String[] dayFile=dir.list();
		boolean boo=false;
		for(int k=0;k<dayFile.length;k++){
			if(dayFile[k].equals(key+".txt")){
				boo=true;
				break;
			}
		}
		return boo;
	}
	
	
	

}
