import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;

//���ʼ�����
//�ȼ̳��࣬��̳нӿ� ��ͬʱ�����������ԣ������ǰ�ť�ļ�����Ҳ�����ļ�����
public class PenListener extends MouseMotionAdapter implements MouseListener {
	Graphics graph;
	String choice = "Line";//���ʼ���ǻ�ֱ��
	int x1,x2,y1,y2;//��¼����
	//����������
	public void actionPerformed(ActionEvent e){
		JButton button= (JButton)e.getSource();//�����¼�Դ����
		if(button.getText() != ""){
			choice = button.getText();
		}
	}
    //��������
	public void mousePressed(MouseEvent e){
		System.out.println("���������");
		x1= e.getX();
		y1= e.getY();
	}
	
//    public void mouseReleased(MouseEvent e){
//    	System.out.println("�ɿ������");
//    	x2= e.getX();
//    	y2= e.getY();
//    	if(choice.equals("Blue Line")){
//    		System.out.println("��ֱ��");
//    		graph.setColor(Color.BLUE);
//    		graph.drawLine(x1, y1, x2, y2);
//    	}
//    	else if(choice.equals("Circle")){ //��Բ
//    		System.out.println("��Բ");
//    		graph.setColor(Color.RED);
//    		graph.drawOval(x2, y2, 100, 70);
//    	}
//    	
//    }
	 public void mouseDragged(MouseEvent e) {
		 x2 = e.getX(); y2 = e.getY();
		 if(choice.equals("Blue Line"))
	     graph.setColor(Color.black);
		 graph.drawLine(x1, y1, x2, y2);
		 
	 }
    
	public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
