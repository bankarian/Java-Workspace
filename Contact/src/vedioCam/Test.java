package vedioCam;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Test {
	public static int x1, y1, x2, y2;
	
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		jf.setSize(new Dimension(600, 700));
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// a method i found on the internet to set jframe to be not opaque
//		jf.setUndecorated(true);
//		jf.setBackground(new Color(0, 0, 0, 0));

//		WindowUtils.setWindowTransparent(jf,true);
		
		// here i add an button 
		// to show me where my jframe is when the frame is not opaque
		jf.setLayout(null);
		JButton btn = new JButton();
		btn.setBounds(new Rectangle(0, 0, 50, 100));
		jf.add(btn);
		
		jf.setVisible(true);
		
		//i also add an JPanel to check
		// if i can see the component when the frame is not opaque
		JPanel jp = new JPanel();
		jp.setBackground(Color.black);
		jp.setSize(new Dimension(200, 300));
		jp.setOpaque(true);
		jp.setBounds(new Rectangle(100, 100, 200, 300));
		jf.add(jp);
		
		// here is to check if the Graphics of the jpanel still work
		Graphics g = jp.getGraphics();
		g.setColor(Color.BLACK);
		jf.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				x1 = e.getX();
				y1 = e.getY();
			}
		});
		jf.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				x2 = e.getX();
				y2 = e.getY();
				g.drawLine(x1, y1, x2, y2);
				
				x1 = x2; y1 = y2;
			}
		});
//		VideoCam cam = new VideoCam();
//		cam.g = jf.getGraphics();
//		cam.startReceive();
//		cam.startSend();
		return;
	}
}
