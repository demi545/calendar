package calendar;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Date;

import javax.swing.JPanel;

public class Clock extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Date date;
	javax.swing.Timer secondTime;
	int hour,minute,second;
	Line2D secondLine,minuteLine,hourLine;
	int a,b,c,width,height;
	double[] pointSX=new double[60];
	double[] pointSY=new double[60];
	double[] pointMX=new double[60];
	double[] pointMY=new double[60];
	double[] pointHX=new double[60];
	double[] pointHY=new double[60];
	
	public Clock(){
		setBackground(Color.cyan);
		initPoint();
		secondTime=new javax.swing.Timer(1000,this);
		secondLine=new Line2D.Double(0, 0,0,0);
		minuteLine=new Line2D.Double(0,0, 0, 0);
		hourLine=new Line2D.Double(0, 0, 0, 0);
		secondTime.start();
	}
	
	public void initPoint(){
		width=getBounds().width;
		height=getBounds().height;
		pointSX[0]=0;
		pointSY[0]=height/2*5/6;
		pointMX[0]=0;
		pointMY[0]=-(height/2*4/5);
		pointHX[0]=0;
		pointHY[0]=-(height/2*2/3);
		double angle=6*Math.PI/180;
		for(int i=0;i<59;i++){
			pointSX[i+1]=pointSX[i]*Math.cos(angle)-Math.sin(angle)*pointSY[i];
			pointSY[i+1]=pointSY[i]*Math.cos(angle)+pointSX[i]*Math.sin(angle);
			pointMX[i+1]=pointMX[i]*Math.cos(angle)-Math.sin(angle)*pointMY[i];
			pointMY[i+1]=pointMY[i]*Math.cos(angle)+pointMX[i]*Math.sin(angle);
			pointHX[i+1]=pointHX[i]*Math.cos(angle)-Math.sin(angle)*pointHY[i];
			pointHY[i+1]=pointHY[i]*Math.cos(angle)+pointHX[i]*Math.sin(angle);
		}
		for(int i=0;i<60;i++){
			pointSX[i]=pointSX[i]+width/2;
			pointSY[i]=pointSY[i]+height/2;
			pointMX[i]=pointMX[i]+width/2;
			pointMY[i]=pointMY[i]+height/2;
			pointHX[i]=pointHX[i]+width/2;
			pointHY[i]=pointHY[i]+height/2;
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		initPoint();
		for(int i=0;i<60;i++){
			int m=(int)pointSX[i];
			int n=(int)pointSY[i];
			if(i%5==0){
				if(i==0||i==15||i==30||i==45){
					int k=10;
					g.setColor(Color.orange);
					g.fillOval(m-k/2, n-k/2, k, k);
				}
				else{
					int k=7;
					g.setColor(Color.orange);
					g.fillOval(m-k/2, n-k/2, k, k);
				}
			}
			else{
				int k=2;
				g.setColor(Color.black);
				g.fillOval(m-k/2, n-k/2, k, k);
			}
		}
		g.fillOval(width/2-5, height/2-5, 10, 10);
		Graphics2D g_2d=(Graphics2D)g;
		g_2d.setColor(Color.red);
		g_2d.draw(secondLine);
		BasicStroke bs=
		new BasicStroke(2f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
		g_2d.setStroke(bs);
		
		g_2d.setColor(Color.blue);
		g_2d.draw(minuteLine);
		bs=new BasicStroke(4f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
		g_2d.setStroke(bs);
		
		g_2d.setColor(Color.orange);
		g_2d.draw(hourLine);
		if((minute==59)&&(second==50)){
			try{
				File f=new File("±¨Ê±.wav");
				URI uri=f.toURI();
				URL url=uri.toURL();
				AudioClip aau;
				aau=Applet.newAudioClip(url);
				aau.play();
			}catch(MalformedURLException e){
				e.printStackTrace();
			}
		}
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==secondTime){
			date=new Date();
			String s=date.toString();
			hour=Integer.parseInt(s.substring(11,13));
			minute=Integer.parseInt(s.substring(14,16));
			second=Integer.parseInt(s.substring(17,19));
			int h=hour%12;
			a=second;
			b=minute;
			c=h*5+minute/12;
			secondLine.setLine(width/2, height/2, (int)pointSX[a], (int)pointSY[a]);
			minuteLine.setLine(width/2, height/2, (int)pointMX[b], (int)pointMY[b]);
			hourLine.setLine(width/2, height/2, (int)pointHX[c], (int)pointHY[c]);
			repaint();
		}
	}
	

}
