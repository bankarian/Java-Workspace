package ini_DRAWPLATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @note
 * ��סҪ��������burreredimage�Ĵ�С����Ӧ��ͬ�Ļ���*/
public class Pythagoras {
	double orderA = Math.PI/3;
	int finalX,finalY;
	public Color curColor;
	public int WIDTH = 1000, HEIGHT = 7400;
	public BufferedImage buffer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
	long Cnt = 0;
	//��BufferedImage��ͼ�����ػ�
	public void draw(Graphics g, double x1,double y1,double x2,double y2,int depth){
		if(depth>0){
			Cnt++;
			Graphics tempG = buffer.getGraphics(); //�õ�buffer�ϵĻ���
			tempG.setColor(curColor); //Ҫ��buffer�ϵĻ���������ɫ
			if(depth%3==0) orderA = Math.PI/3;
			else if(depth%3==1) orderA = Math.PI/4;
			else if(depth%3==2) orderA = Math.PI/3;
			double len = getLen(x1,y1,x2,y2);
			double angle = getAngle(x1,y1,x2,y2); //�Ƕ�
			double x3 = x2 - len*Math.sin(angle);	double y3 = y2 - len*Math.cos(angle);
			double x4 = x1 - len*Math.sin(angle);  	double y4 = y1 - len*Math.cos(angle);
			int[] x = {(int)x1,(int)x2,(int)x3,(int)x4}; //(x1,y1) (x2,y2) (x3,y3) (x4,y4)
			int[] y = {(int)y1,(int)y2,(int)y3,(int)y4};
			//����~
			tempG.fillPolygon(x, y, 4);
			//g.fillPolygon(x, y, 4);
			if(Cnt%80000==0)
				g.drawImage(buffer, 0, 0, null);//��JPanel��(0,0)��չ��ͼ
			if(depth%6==0) len = len*2/3;
			double x5 = x4 + Math.cos(angle+orderA)*Math.cos(orderA)*len;
			double y5 = y4 - Math.sin(angle+orderA)*Math.cos(orderA)*len;
			draw(g, x4,y4, x5,y5, depth-1);
			draw(g, x5,y5, x3,y3, depth-1);
		}
		else {
			g.drawImage(buffer, 0, 0, null);
			return;
		}
	}
	/**
	 * @note:ÿ�������εĽǶȹ̶�����汾
	 * @param orderA ����һ����ֱ�ǵĽǶ�
	 * @param depth ���8-9�Ƽ�
	 * @param x1,y1 x2,y2 �����������εĵײ�������
	 */
	public void draw_fixA(Graphics g, double x1,double y1,double x2,double y2,int depth, double orderA){
		if(depth>0){
			Cnt++;
			Graphics tempG = buffer.getGraphics(); //�õ�buffer�ϵĻ���
			tempG.setColor(curColor); //Ҫ��buffer�ϵĻ���������ɫ
			double len = getLen(x1,y1,x2,y2);
			double angle = getAngle(x1,y1,x2,y2); //�Ƕ�
			double x3 = x2 - len*Math.sin(angle);	double y3 = y2 - len*Math.cos(angle);
			double x4 = x1 - len*Math.sin(angle);  	double y4 = y1 - len*Math.cos(angle);
			int[] x = {(int)x1,(int)x2,(int)x3,(int)x4}; //(x1,y1) (x2,y2) (x3,y3) (x4,y4)
			int[] y = {(int)y1,(int)y2,(int)y3,(int)y4};
			//����~
			tempG.fillPolygon(x, y, 4);
			//g.fillPolygon(x, y, 4);
			if(Cnt%800000==0)
				g.drawImage(buffer, 0, 0,null);//��JPanel��(0,0)��չ��ͼ
			if(depth%6==0) len = len*2/3;
			double x5 = x4 + Math.cos(angle+orderA)*Math.cos(orderA)*len;
			double y5 = y4 - Math.sin(angle+orderA)*Math.cos(orderA)*len;
			draw(g, x4,y4, x5,y5, depth-1);
			draw(g, x5,y5, x3,y3, depth-1);
		}
		else {
			g.drawImage(buffer, 0, 0, null);
			return;
		}
	}
	//���ػ������ڴ洢
	public Image returnImage(){
		return buffer;
	}
	private double getLen(double x1,double y1,double x2,double y2){
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	private double getAngle(double x1,double y1,double x2,double y2){
//		return Math.atan((y2-y1)/(x2-x1)); //�㲻������ĸ��Ҫ�����
		//atan�ķ���ֵΪ -pi/2-->pi/2
		double angle = 0.0;
		if(x1 == x2){//������ͬһ��ֱ����
			if(y1 > y2){
				angle = Math.PI/2;
			}else if(y2 > y1){
				angle = Math.PI*3/2;
			}
		}
		else{//���㲻��ͬһֱ��
			double tempAngle = Math.atan((y2-y1)/(x2-x1));
			if(x2 > x1){
				angle = -tempAngle;
			}else if(x2 < x1){ //atan�ķ���ֵ�� -pi/2��pi/2֮��
				angle = -tempAngle + Math.PI;
			}
		}
		return angle;
	}
}
