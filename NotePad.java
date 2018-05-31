package calendar;

import java.awt.*; 
import java.awt.event.*; import java.util.*; 

import javax.swing.*; 
import javax.swing.event.*; 

import java.io.*;  
public class NotePad extends JPanel implements ActionListener,ItemListener { 
	
    JTextArea text;                 
    JButton saveDaily=new JButton("保存日志");
    JButton deleteDaily=new JButton("删除日志");   
    Hashtable table;                
    JLabel jl;   //信息条
    JPanel p1=new JPanel();
    JPanel p2=new JPanel();
    int year,month,day;            
    File file;                     
    CalendarPad calendar;
    Choice list1=new Choice();   //列表表示字体
	Choice list2=new Choice();     //列表表示字大小
	JButton btn=new JButton("颜色");
	String Size[]={"10","12","14","16","18","20","22","24","26","28","30","32","34","36"};
	
    public NotePad(CalendarPad calendar){
    	GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontname[]=ge.getAvailableFontFamilyNames();
		for(int i=0;i<fontname.length;i++){
			list1.add(fontname[i]);
		}
		for(int i=0;i<Size.length;i++){
			list2.add(Size[i]);
		}
        this.calendar=calendar;     
        year=calendar.getYear();    
        month=calendar.getMonth();   
        day=calendar.getDay();; 
        table=calendar.getHashtable();     
        file=calendar.getFile(); 
        jl=new JLabel(""+year+"年"+month+"月"+day+"日",JLabel.CENTER);    
        jl.setFont(new Font("TimesRoman",Font.BOLD,16));   
        jl.setForeground(Color.blue);
        text=new JTextArea(10,10); 
        saveDaily.addActionListener(this);     
        deleteDaily.addActionListener(this); 
        btn.addActionListener(this);
		list1.addItemListener(this);
		list2.addItemListener(this);
        
        p1.add(list1);
        p1.add(list2);
        p1.add(btn);
        p2.setLayout(new BorderLayout());
        p2.add(new JScrollPane(text),BorderLayout.CENTER);
        p2.add(p1,BorderLayout.SOUTH);
        setLayout(new BorderLayout()); 
        JPanel pSouth=new JPanel();          
        add(jl,BorderLayout.NORTH);
        pSouth.add(saveDaily);     
        pSouth.add(deleteDaily); 
        add(pSouth,BorderLayout.SOUTH); 
        add(p2,BorderLayout.CENTER);   
     } 
     public void actionPerformed(ActionEvent e){  
        if(e.getSource()==saveDaily){ 
    	    saveDaily(year,month,day);      
        } 
        else if(e.getSource()==deleteDaily){ 
    	    deleteDaily(year,month,day);      
    	} 
        if(e.getSource()==btn){
			Color newColor=JColorChooser.showDialog(this, "选择颜色", text.getForeground());
			if(newColor!=null){
				text.setForeground(newColor);
			}
		}
     } 
     public void setYear(int year){ 
         this.year=year;  
     } 
     public int getYear(){ 
         return year;  
     } 
     public void setMonth(int month){ 
    	 this.month=month;  
     }  
     public int getMonth(){ 
         return month;   
     }  
     public void setDay(int day){ 
         this.day=day; 
     } 
     public int getDay(){ 
         return day;   
     } 
     public void setJL(int year,int month,int day){ //设置信息条
         jl.setText(""+year+"年"+month+"月"+day+"日");    
     } 
     public void setNote(String s){             //设置文本内容
         text.setText(s);   
     } 
     public void getDaily(int year,int month,int day){ //获取日志
         String key=""+year+""+month+""+day;       
         try 
          { 
              FileInputStream   inOne=new FileInputStream(file); 
              ObjectInputStream inTwo=new ObjectInputStream(inOne);              
              table=(Hashtable)inTwo.readObject();                      
              inOne.close();              
              inTwo.close();             
           }catch(Exception ee) { }              
             
       if(table.containsKey(key)){ 
              String m=""+year+"年"+month+"月"+day+"这一天有日志记载,想看吗?"; 
              int ok=JOptionPane.showConfirmDialog(this,m,"询问",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
              if(ok==JOptionPane.YES_OPTION){ 
                  text.setText((String)table.get(key));                 
              }                
              else                  
                 text.setText("");
                                     
                          
        }        
        else          
            text.setText("无记录");
                      
     } 
     public void saveDaily(int year,int month,int day){ 
	     String daily=text.getText();   //日志内容
         String key=""+year+""+month+""+day; 
         String m=""+year+"年"+month+"月"+day+"日"+"保存日志吗?"; 
         int ok=JOptionPane.showConfirmDialog(this,m,"询问",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);        
         if(ok==JOptionPane.YES_OPTION){  
        	  try{ 
        		  FileInputStream   inOne=new FileInputStream(file); 
        		  ObjectInputStream inTwo=new ObjectInputStream(inOne);             
        		  table=(Hashtable)inTwo.readObject();             
        		  inOne.close();              
        		  inTwo.close(); 
        		  table.put(key,daily);                                             
        		  FileOutputStream out=new FileOutputStream(file); 
        		  ObjectOutputStream objectOut=new ObjectOutputStream(out);              
        		  objectOut.writeObject(table);             
        		  objectOut.close();             
        		  out.close();           
              } catch(Exception ee){}
          }
     }
    
  
     public void deleteDaily(int year,int month,int day){
    	 String key=""+year+""+month+""+day;         
         if(table.containsKey(key)) { 
        	 String m="删除"+year+"年"+month+"月"+day+"日的日志吗?"; 
             int ok=JOptionPane.showConfirmDialog(this,m,"询问",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);            
             if(ok==JOptionPane.YES_OPTION){
            	 try               
                  { 
                   FileInputStream   inOne=new FileInputStream(file); 
                   ObjectInputStream inTwo=new ObjectInputStream(inOne);                
                   table=(Hashtable)inTwo.readObject();                
                   inOne.close();                 
                   inTwo.close(); 
                   table.remove(key);                                                        
                   FileOutputStream out=new FileOutputStream(file); 
                   ObjectOutputStream objectOut=new ObjectOutputStream(out);               
                   objectOut.writeObject(table);                
                   objectOut.close();                 
                   out.close(); 
                   text.setText(null);               
            	 } catch(Exception ee){}             
            }          
         }        
         else          
         {              
            String m=""+year+"年"+month+"月"+day+"日"+"无日志记录"; 
            JOptionPane.showMessageDialog(this,m,"提示",JOptionPane.WARNING_MESSAGE);          
         }   
      }
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		String n1=list2.getSelectedItem();
		String name=list1.getSelectedItem();
		int n2=Integer.parseInt(n1);
		Font f=new Font(name,Font.PLAIN,n2);
		text.setFont(f);
	}  
  } 