package calendar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JPanel;

public class CalendarImage extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	File imageFile;
	Image image;
	Toolkit tool;
	
	public  CalendarImage(){
		tool=getToolkit();
	}
	public void setImageFile(File f){
		imageFile=f;
		try{
			image=tool.getImage(imageFile.toURI().toURL());
		}catch(Exception eeee){}
		repaint();  //重绘
	}
	//重载paintComponent方法，覆盖原来的，供repaint（）调用；
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int w=getBounds().width;
		int h=getBounds().height;
		g.drawImage(image, 0, 0, w, h, this);
		
	}

}
