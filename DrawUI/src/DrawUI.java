import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;

//��ͼ��
public class DrawUI {
	public void ShowUI()
	{
		JFrame draw_plate = new JFrame();
		draw_plate.setSize(1000,700);
		draw_plate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		draw_plate.setLocationRelativeTo(null);
		
		//������������(���+ѡ��)
		PenListener pen = new PenListener();
		//���Ӱ�ťѡ���߻�Բ
		FlowLayout layout = new FlowLayout();
		draw_plate.setLayout(layout);
		JButton choice1 = new JButton("Blue Line");
		JButton choice2 = new JButton("Circle");
		Dimension button_size = new Dimension(150,50);
		choice1.setPreferredSize(button_size);
		choice2.setPreferredSize(button_size);
		//������ť���Ӽ�����
//		choice1.addActionListener(pen);
//		choice2.addActionListener(pen);
		
		draw_plate.add(choice1);
		draw_plate.add(choice2);//���������ť
		
		//���¼�Դ��Ӽ����������ĵ�������ڴ����ϣ�
		draw_plate.addMouseListener(pen);
		draw_plate.addMouseMotionListener(pen);
		//����ɼ�
		draw_plate.setVisible(true);
		
		//���ô���ɼ���ſ��Խ�����������
		//��draw_plate�ϻ�ͼ
		Graphics g = draw_plate.getGraphics();
		pen.graph = g;	
		
	}
	public static void main(String[] args)
	{
		DrawUI ui= new DrawUI();
		ui.ShowUI();
	}
}
