package calendar;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;  
public class Year extends Box implements ActionListener{
	
    int year;   
    JTextField showYear=null;              
    JButton nextYear=new JButton("下年");
    JButton previousYear=new JButton("上年");  
    CalendarPad calendar; 
 
    public Year(CalendarPad calendar){
        super(BoxLayout.X_AXIS);              
        showYear=new JTextField(4); 
        showYear.setForeground(Color.blue); 
        showYear.setFont(new Font("TimesRomn",Font.BOLD,14));      
        this.calendar=calendar; 
        year=calendar.getYear(); 
        add(previousYear); 
        add(showYear);     
        add(nextYear); 
        showYear.addActionListener(this);      
        previousYear.addActionListener(this);     
        nextYear.addActionListener(this);   
    } 
    public void setYear(int year){
        this.year=year; 
        showYear.setText(""+year);   
   } 
    public int getYear(){ 
        return year;   
   }  
    public void actionPerformed(ActionEvent e){ 
        if(e.getSource()==previousYear){
        	year=year-1; 
            showYear.setText(""+year);         
            calendar.setYear(year); 
            calendar.setCalendar(year,calendar.getMonth());      
        } 
        else if(e.getSource()==nextYear){ 
            year=year+1; 
            showYear.setText(""+year);        
            calendar.setYear(year); 
            calendar.setCalendar(year,calendar.getMonth());      
       } 
       else if(e.getSource()==showYear){
    	  try{
    		   year=Integer.parseInt(showYear.getText());//把字符串转换成整型
    		   showYear.setText(""+year);              
               calendar.setYear(year); 
               calendar.setCalendar(year,calendar.getMonth());
             }catch(NumberFormatException ee)            
             { 
               showYear.setText(""+year);              
               calendar.setYear(year); 
               calendar.setCalendar(year,calendar.getMonth());            
             }     
        }   
   }   
} 
