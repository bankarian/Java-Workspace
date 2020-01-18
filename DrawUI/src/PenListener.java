import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;

//画笔监听器
//先继承类，后继承接口 （同时具有两种属性），既是按钮的监听器也是鼠标的监听器
public class PenListener extends MouseMotionAdapter implements MouseListener {
	Graphics graph;
	String choice = "Line";//最初始的是画直线
	int x1,x2,y1,y2;//记录坐标
	//动作监听器
	public void actionPerformed(ActionEvent e){
		JButton button= (JButton)e.getSource();//返回事件源类型
		if(button.getText() != ""){
			choice = button.getText();
		}
	}
    //鼠标监听器
	public void mousePressed(MouseEvent e){
		System.out.println("按下了鼠标");
		x1= e.getX();
		y1= e.getY();
	}
	
//    public void mouseReleased(MouseEvent e){
//    	System.out.println("松开了鼠标");
//    	x2= e.getX();
//    	y2= e.getY();
//    	if(choice.equals("Blue Line")){
//    		System.out.println("画直线");
//    		graph.setColor(Color.BLUE);
//    		graph.drawLine(x1, y1, x2, y2);
//    	}
//    	else if(choice.equals("Circle")){ //画圆
//    		System.out.println("画圆");
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
