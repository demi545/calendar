 package calendar;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class NotePad extends JPanel implements ActionListener,MouseListener,ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPopupMenu popup=new JPopupMenu();
	JMenuItem cut=new JMenuItem("剪切");
	JMenuItem copy=new JMenuItem("复制");
	JMenuItem paste=new JMenuItem("粘贴");
	JTextField ShowMessage=new JTextField();
	JTextField time=new JTextField(10);
	JTextArea ta=new JTextArea(5,10);
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	JPanel p3=new JPanel();
	Choice list1=new Choice();
	Choice list2=new Choice();
	JButton btn=new JButton("颜色");
	JLabel labl=new JLabel("事件发生时间（hh:mm）：");
	String Size[]={"10","12","14","16","18","20","22","24","26","28","30","32","34","36"};
	
	
	public NotePad(){
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontname[]=ge.getAvailableFontFamilyNames();
		for(int i=0;i<fontname.length;i++){
			list1.add(fontname[i]);
		}
		for(int i=0;i<Size.length;i++){
			list2.add(Size[i]);
		}
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));
		popup.add(cut);
		popup.add(copy);
		popup.add(paste);
		p1.add(list1);
		p1.add(list2);
		p1.add(btn);
		p3.add(labl);
		p3.add(time);
		setLayout(new BorderLayout());
		add(ShowMessage,BorderLayout.NORTH);
		p2.setLayout(new BorderLayout());
		p2.add(new JScrollPane(ta),BorderLayout.CENTER);
		p2.add(p3,BorderLayout.SOUTH);
		add(p2,BorderLayout.CENTER);
		add(p1,BorderLayout.SOUTH);
		setBounds(0,0,600,600);
		setVisible(true);
		btn.addActionListener(this);
		list1.addItemListener(this);
		list2.addItemListener(this);
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		ta.addMouseListener(this);
		
	}
	

	public void setMessage(int year,int month,int day){
		ShowMessage.setText(year+"年"+month+"月"+day+"日");
		ShowMessage.setForeground(Color.blue);
		ShowMessage.setFont(new Font("宋体",Font.BOLD,15));
		
	}
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		String n1=list2.getSelectedItem();
		String name=list1.getSelectedItem();
		int n2=Integer.parseInt(n1);
		Font f=new Font(name,Font.PLAIN,n2);
		ta.setFont(f);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==MouseEvent.BUTTON3){
			popup.show(ta,e.getX(),e.getY());
		}
		if(e.getButton()==MouseEvent.BUTTON1){
			popup.setVisible(false);
		}
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
		if(e.getSource()==cut){
			cut();
		}
		if(e.getSource()==copy){
			copy();
		}
		if(e.getSource()==paste){
			paste();
		}
		if(e.getSource()==btn){
			Color newColor=JColorChooser.showDialog(this, "选择颜色", ta.getForeground());
			if(newColor!=null){
				ta.setForeground(newColor);
			}
		}
		
	}
	public void cut(){
		ta.cut();
		popup.setVisible(false);
	}
	public void copy(){
		ta.copy();
		popup.setVisible(false);
	}
	public void paste(){
		ta.paste();
		popup.setVisible(false);
	}
	
	public void savefile(File dir,int year,int month,int day){
		String dailyRecord=time.getText()+"#"+ta.getText()+"#";
		String fileName=""+year+""+month+""+day+".txt";
		String key=""+year+""+month+""+day;
		String dialyFile[]=dir.list();
		boolean b=false;
		for(int i=0;i<dialyFile.length;i++){
			if(dialyFile[i].startsWith(key)){
				b=true;
				break;
			}
		}
		if(b){
			int n=JOptionPane.showConfirmDialog(this, ""+year+"年"+month+"月"+day+"日"+
		     "已经"+ "有日志存在，是否添加日志？","确认对话框",JOptionPane.YES_NO_OPTION);
			if(n==JOptionPane.YES_OPTION){
				try{
					File file=new File(dir,fileName);
					RandomAccessFile out=new RandomAccessFile(file,"rw");
					long end=out.length();
					byte[] bb=dailyRecord.getBytes();
					out.seek(end);
					out.write(bb);
					out.close();
				}catch(IOException e){}
				ta.setText("");
			}
			else{
				ta.setText("");
			}
		}
		else{
			try{
				File file=new File(dir,fileName);
				FileWriter fw=new FileWriter(file);
				BufferedWriter bw=new BufferedWriter(fw);
				bw.write(dailyRecord);
				bw.close();
				fw.close();
			}catch(IOException e){}
			JOptionPane.showMessageDialog(this, "添加日志成功", "消息对话框", JOptionPane.INFORMATION_MESSAGE);
			ta.setText("");
			time.setText("");
		}
	}
	
	public void deletefile(File dir,int year,int month,int day){
		String key=""+year+""+month+""+day;
		String dialyFile[]=dir.list();
		boolean b=false;
		for(int i=0;i<dialyFile.length;i++){
			if(dialyFile[i].startsWith(key)){
				b=true;
				break;
			}
		}
		if(b){
			int n=JOptionPane.showConfirmDialog(this, "是否删除"+year+"年"+month+"月"+day+"日的日志？"
		     ,"确认对话框",JOptionPane.YES_NO_OPTION);
			if(n==JOptionPane.YES_OPTION){
				try{
					String fileName=""+year+""+month+""+day+".txt";
					File file=new File(dir,fileName);
					file.delete();
				}catch(Exception e){}
				ta.setText("");
				}
		}
		else{
			JOptionPane.showMessageDialog(this, ""+year+"年"+month+"月"+day+"日无日志！",
					"消息对话框", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public void readfile(File dir,int year,int month,int day){
		String fileName=""+year+""+month+""+day+".txt";
		String key=""+year+""+month+""+day;
		String dialyFile[]=dir.list();
		boolean b=false;
		for(int i=0;i<dialyFile.length;i++){
			if(dialyFile[i].startsWith(key)){
				b=true;
				break;
			}
		}
		if(b){
			ta.setText("");
			time.setText("");
			try{
				File file=new File(dir,fileName);
				FileReader inOne=new FileReader(file);
				BufferedReader inTwo=new BufferedReader(inOne);
				String s;
				while((s=inTwo.readLine())!=null){
					ta.append(s+"\n");
				}
				inOne.close();
				inTwo.close();
			}catch(IOException e){}
		}
		else{
			JOptionPane.showMessageDialog(this, ""+year+"年"+month+"月"+day+"日无日志！",
					"消息对话框", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
