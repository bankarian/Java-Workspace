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

//�������ͼ��
public class Drawer extends JPanel{
	Shape[] sharr = new Shape[1000];
//	int index = 0;
	public void show_plate()
	{
		JFrame Plate = new JFrame(); //JFrameĬ�ϵ���BorderLayout
		Plate.setSize(1000,800);
		Plate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Plate.setLocationRelativeTo(null);
		
		//��������������ͼ���밴ť���ֿ�.JPanelĬ��FlowLayout
		JPanel NorthP = new JPanel(); 
		//�ظ���ʾ����CentreP,��������ΪDrawer����
		
		NorthP.setBackground(Color.CYAN);
		NorthP.setSize(1000, 60);  //��ťѡ����
		this.setSize(1000,740); //�м�Ļ���
		Plate.add(NorthP, BorderLayout.NORTH); //�������ӵ�����
		Plate.add(this,BorderLayout.CENTER);
		
//		//�������ʼ�����
		DrawerListener Pen = new DrawerListener();
		this.addMouseListener(Pen);
		
		//������ťѡ�� ��ӵ� NorthPanel
		String[] choice = {
				"Line", "Oval","Magic","Cantor","Blanket","Pythagoras","Stereography"
		}; //��ťָ��
		Color[] color = {Color.BLACK,Color.BLUE, Color.GREEN}; //��ɫ����
		Dimension button_size = new Dimension(50,50);
		for(int i=0; i<choice.length; ++i){
			JButton button = new JButton();
			Icon surf = new ImageIcon("D:\\pic_store\\"+(i+1)+".jpg");
			button.setActionCommand(choice[i]); //���ö�������
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
		//���м��Panel�ϻ滭 ��������ȡPanel�ϵĻ���.�����ɼ�����ܻ�ȡ����
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
