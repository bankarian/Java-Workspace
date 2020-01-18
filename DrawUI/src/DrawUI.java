import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;

//画图版
public class DrawUI {
	public void ShowUI()
	{
		JFrame draw_plate = new JFrame();
		draw_plate.setSize(1000,700);
		draw_plate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		draw_plate.setLocationRelativeTo(null);
		
		//创建鼠标监听器(鼠标+选择)
		PenListener pen = new PenListener();
		//增加按钮选择画线或画圆
		FlowLayout layout = new FlowLayout();
		draw_plate.setLayout(layout);
		JButton choice1 = new JButton("Blue Line");
		JButton choice2 = new JButton("Circle");
		Dimension button_size = new Dimension(150,50);
		choice1.setPreferredSize(button_size);
		choice2.setPreferredSize(button_size);
		//两个按钮增加监听器
//		choice1.addActionListener(pen);
//		choice2.addActionListener(pen);
		
		draw_plate.add(choice1);
		draw_plate.add(choice2);//添加两个按钮
		
		//给事件源添加监听器（鼠标的点击发生在窗体上）
		draw_plate.addMouseListener(pen);
		draw_plate.addMouseMotionListener(pen);
		//窗体可见
		draw_plate.setVisible(true);
		
		//设置窗体可见后才可以建立画布对象
		//在draw_plate上画图
		Graphics g = draw_plate.getGraphics();
		pen.graph = g;	
		
	}
	public static void main(String[] args)
	{
		DrawUI ui= new DrawUI();
		ui.ShowUI();
	}
}
