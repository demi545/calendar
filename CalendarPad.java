package calendar;
 
import java.util.Calendar; 

import javax.swing.*; 

import java.awt.*; 
import java.awt.event.*; 
import java.io.*; 
import java.util.Hashtable; 
public class CalendarPad extends JPanel implements MouseListener,FocusListener { 
	
    int year,month,day; 
    Hashtable hashtable;                
    File file;                           
    JTextField showDay[];                 
    JLabel title[];                      
    Calendar calendar;   
    int weekday;    //星期几
    NotePad notepad=null;                
    Month month_change;   //改变月
    Year  year_change;   //改变年
    String week[]={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"}; 
    JPanel leftPanel,rightPanel; 
    
    public  CalendarPad(int year,int month,int day) {  
        leftPanel=new JPanel(); 
        JPanel leftCenter=new JPanel();      
        JPanel leftNorth=new JPanel(); 
        leftCenter.setLayout(new GridLayout(7,7));           
        rightPanel=new JPanel();      
        this.year=year;     
        this.month=month;     
        this.day=day; 
        year_change=new Year(this);     
        year_change.setYear(year);      
        month_change=new Month(this);     
        month_change.setMonth(month);    
        title=new JLabel[7];                               
        showDay=new JTextField[42];                         
        for(int j=0;j<7;j++){
        	title[j]=new JLabel(); 
            title[j].setText(week[j]); 
            title[j].setBorder(BorderFactory.createRaisedBevelBorder()); 
            leftCenter.add(title[j]);        
         }  
         title[0].setForeground(Color.red);     
         title[6].setForeground(Color.blue);  
         for(int i=0;i<42;i++){ 
             showDay[i]=new JTextField(); 
             showDay[i].addMouseListener(this);
             showDay[i].addFocusListener(this);
             showDay[i].setEditable(false);         
             leftCenter.add(showDay[i]);       
         }           
         calendar=Calendar.getInstance(); 
         Box box=Box.createHorizontalBox();                
         box.add(year_change);     
         box.add(month_change);     
         leftNorth.add(box); 
         leftPanel.setLayout(new BorderLayout()); 
         leftPanel.add(leftNorth,BorderLayout.NORTH);      
         leftPanel.add(leftCenter,BorderLayout.CENTER);
         leftPanel.add(new Label("您还可以在年、月份输入框中输入所查年份或月份,并回车确定"), BorderLayout.SOUTH);     
         leftPanel.validate(); 
         JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,rightPanel);       
         add(split,BorderLayout.CENTER);     
         validate();      
         hashtable=new Hashtable(); 
         file=new File("日历记事本.txt");       
         if(!file.exists()){        
    	    try{
    	    	FileOutputStream  out=new FileOutputStream(file);  //写入到输出流
                ObjectOutputStream objectOut=new ObjectOutputStream(out);//对象串行化，序列化
                objectOut.writeObject(hashtable); 
                objectOut.close();
                out.close();
           }catch(IOException e){}          
    	}
        notepad=new NotePad(this);              
        rightPanel.add(notepad);       
        setCalendar(year,month); 
        setVisible(true); 
        setBounds(100,50,524,285);     
        validate();    //使生效
    } 
    
	public void setCalendar(int year,int month){        //设置日历牌
	    calendar.set(year,month-1,1);                     
	    weekday=calendar.get(Calendar.DAY_OF_WEEK)-1; 
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){ 
             number(weekday,31);                  
        } 
        else if(month==4||month==6||month==9||month==11)  { 
            number(weekday,30);        
        } 
        else if(month==2){ 
            if((year%4==0&&year%100!=0)||(year%400==0)){ 
            	number(weekday,29);         
            }         
            else 
        	    number(weekday,28);           
                    
       }    
    }  
    public void number(int weekday,int amount){    //排列日期 
        for(int i=weekday,n=1;i<weekday+amount;i++){
        	showDay[i].setText(""+n);    
            if(n==day){ 
            	showDay[i].setForeground(Color.green);  
                showDay[i].setFont(new Font("TimesRoman",Font.BOLD,20));                
            } 
            else{  
                showDay[i].setFont(new Font("TimesRoman",Font.BOLD,12)); 
                showDay[i].setForeground(Color.black); 
            } 
            if(i%7==6) { 
                showDay[i].setForeground(Color.blue);                
            } 
            if(i%7==0)  { 
                showDay[i].setForeground(Color.red);                
            }               
            n++;              
        } 
        for(int i=0;i<weekday;i++) { 
            showDay[i].setText("");
       } 
       for(int i=weekday+amount;i<42;i++) { 
            showDay[i].setText("");           
       }    
    } 
    public int getYear()   
    { 
       return year;   
    }  
    public void setYear(int y)    
    { 
        year=y; 
        notepad.setYear(year);  
     } 
    public int getMonth()    
    { 
       return month;   
    } 
    public void setMonth(int m)    
    { 
        month=m; 
        notepad.setMonth(month);     
    } 
    public int getDay()    
    { 
       return day;  
    } 
    public void setDay(int d)   
    { 
       day=d; 
       notepad.setDay(day);    
    }
    public Hashtable getHashtable()   
    { 
        return hashtable;  
    } 
    public File getFile()    
    { 
        return file; 
    } 
  public void mousePressed(MouseEvent e) { 
      JTextField source=(JTextField)e.getSource();     
      try{ 
          day=Integer.parseInt(source.getText());         
          notepad.setDay(day); 
          notepad.setJL(year,month,day);         
          notepad.setNote(null); 
          notepad.getDaily(year,month,day);         
          }catch(Exception ee) {}       
              
  } 
  public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
  } 
  public void mouseReleased(MouseEvent e){ 
	// TODO Auto-generated method stub
  } 
  public void mouseEntered(MouseEvent e){
	// TODO Auto-generated method stub
  } 
  public void mouseExited(MouseEvent e) { 
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
	com.setBackground(Color.getColor(null));
	
} 

}
  
 



 





 