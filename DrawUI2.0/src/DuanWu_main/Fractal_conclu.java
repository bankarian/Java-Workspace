package DuanWu_main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Fractal_conclu extends JFrame{
	private static final long serialVersionUID = 1L;
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth(), HEIGTH = (int) screenSize.getHeight();
	
	public static void main(String []args){
		new Fractal_conclu();
	}
	public Fractal_conclu(){
//		this.setLocationRelativeTo(null);
		this.setSize(screenSize);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setVisible(true);
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		class myPanel extends JPanel{
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
			}
		}
		
		/**
		 * 布局管理器为空--才可以自己设置组件坐标位置，
		 * 否则重绘时，会按照布局管理器重新排版
		 **/
		myPanel drawPanel = new myPanel();
		drawPanel.setPreferredSize(new Dimension(cp.getWidth(), cp.getHeight() - 100));
		drawPanel.setBackground(Color.BLACK);
		drawPanel.setLayout(null);    
//		drawPlate.setVisible(true);
		cp.add(drawPanel,BorderLayout.CENTER);
		
		JPanel choicePanel = new JPanel();
		choicePanel.setPreferredSize(new Dimension(cp.getWidth(),50));
		choicePanel.setBackground(Color.CYAN);
		cp.add(choicePanel, BorderLayout.NORTH);
		
		JSlider mySlider = new JSlider(0, 500, 0);
		mySlider.setPaintTicks(true);
		mySlider.setMajorTickSpacing(25);
		mySlider.setMinorTickSpacing(5);
		mySlider.setBounds(10,10,150,50);
		mySlider.setOpaque(false);
		mySlider.setVisible(true);
		drawPanel.add(mySlider);
		
		JButton button = new JButton("choice");
		button.setPreferredSize(new Dimension(80,40));
		choicePanel.add(button);
		
		this.setVisible(true);  //最后设置可见
	}
}
