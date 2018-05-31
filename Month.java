package calendar;

import javax.swing.*; 

import java.awt.*; 
import java.awt.event.*;  
public class Month extends Box implements ActionListener { 
	 int month; 
     JTextField showMonth=null; 
     JButton nextMonth=new JButton("下月");
     JButton previousMonth=new JButton("上月");   
     CalendarPad calendar; 
     
     public Month(CalendarPad calendar){ 
         super(BoxLayout.X_AXIS); 
         this.calendar=calendar; 
         showMonth=new JTextField(2);     
         month=calendar.getMonth(); 
         showMonth.setForeground(Color.blue); 
         showMonth.setFont(new Font("TimesRomn",Font.BOLD,16));            
         add(previousMonth); 
         add(showMonth);     
         add(nextMonth); 
         previousMonth.addActionListener(this);     
         nextMonth.addActionListener(this);     
         showMonth.addActionListener(this);
     } 
     public void setMonth(int month){ 
         if(month<=12&&month>=1){
             this.month=month;      
         }    
        // else      
        //   this.month=1;	     
         showMonth.setText(""+month);  
     } 
     public int getMonth(){ 
         return month;   
     }  
     public void actionPerformed(ActionEvent e){ 
         if(e.getSource()==previousMonth){ 
             if(month>=2){ 
            	 month=month-1; 
            	 calendar.setMonth(month); 
            	 calendar.setCalendar(calendar.getYear(),month);         
             } 
             else if(month==1){ 
                 month=12;
                 calendar.setMonth(month);
                 calendar.setCalendar(calendar.getYear(), month);
                 
            } 
            showMonth.setText(""+month);      
        } 
        else if(e.getSource()==nextMonth){ 
            if(month<12){ 
                 month=month+1; 
                 calendar.setMonth(month); 
                 calendar.setCalendar(calendar.getYear(),month);          
            } 
            else if(month==12){ 
                 month=1; 
                 calendar.setMonth(month); 
                 calendar.setCalendar(calendar.getYear(),month);          
            } 
            showMonth.setText(""+month);      
        } 
        else if(e.getSource()==showMonth){
      	  try{
      		   month=Integer.parseInt(showMonth.getText()); //将字符串转换成整型           
      		   showMonth.setText(""+month);              
               calendar.setMonth(month); 
               calendar.setCalendar(calendar.getYear(),month);       
               }catch(NumberFormatException ee)            
               { 
                 showMonth.setText(""+month);              
                 calendar.setMonth(month); 
                 calendar.setCalendar(calendar.getYear(),month);            
               }     
          }   
    }  
 } 
