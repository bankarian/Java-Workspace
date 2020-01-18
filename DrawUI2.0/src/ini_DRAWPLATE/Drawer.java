package ini_DRAWPLATE;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//升级版绘图板
public class Drawer extends JPanel{
	Shape[] sharr = new Shape[1000];
//	int index = 0;
	public void show_plate()
	{
		JFrame Plate = new JFrame(); //JFrame默认的是BorderLayout
		Plate.setSize(1000,800);
		Plate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Plate.setLocationRelativeTo(null);
		
		//建立分区，将绘图区与按钮区分开.JPanel默认FlowLayout
		JPanel NorthP = new JPanel(); 
		//重复显示的是CentreP,将其设置为Drawer类型
		
		NorthP.setBackground(Color.CYAN);
		NorthP.setSize(1000, 60);  //按钮选项区
		this.setSize(1000,740); //中间的画布
		Plate.add(NorthP, BorderLayout.NORTH); //分区增加到顶部
		Plate.add(this,BorderLayout.CENTER);
		
//		//创建画笔监听器
		DrawerListener Pen = new DrawerListener();
		this.addMouseListener(Pen);
		
		//创建按钮选项 添加到 NorthPanel
		String[] choice = {
				"Line", "Oval","Magic","Cantor","Blanket","Pythagoras","Stereography"
		}; //按钮指令
		Color[] color = {Color.BLACK,Color.BLUE, Color.GREEN}; //颜色类型
		Dimension button_size = new Dimension(50,50);
		for(int i=0; i<choice.length; ++i){
			JButton button = new JButton();
			Icon surf = new ImageIcon("D:\\pic_store\\"+(i+1)+".jpg");
			button.setActionCommand(choice[i]); //设置动作命令
			button.setIcon(surf);
			button.addActionListener(Pen);
			button.setPreferredSize(button_size);
			NorthP.add(button);
		}
		for(int i=0; i<color.length; ++i){
			JButton button = new JButton();
			button.setBackground(color[i]);
			button.setPreferredSize(button_size);
			button.addActionListener(Pen);
			NorthP.add(button);
		}
		Plate.setVisible(true);
		//在中间的Panel上绘画 监听器获取Panel上的画布.容器可见后才能获取画布
		Pen.graph = this.getGraphics();
		sharr = Pen.sharr;
	}
	public void paint(Graphics g){
		super.paint(g);
//		System.out.println("paint function, sharr[0]"+ sharr[0]);
		for(int i=0;i<sharr.length;++i){
			if(sharr[i] == null) break;
			System.out.println("reDrawing");
			sharr[i].DrawShape(g);
		}
	}
	
	public static void main(String[] args){
		Drawer drawer = new Drawer();
		drawer.show_plate();
	}
}
