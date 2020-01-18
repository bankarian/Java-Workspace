package fractal_MOUNT;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class MDrawer {
	Random ran = new Random();//�����������
	public int HRIGHT = 800, animatingTime = 0;
	public Color mountColor = new Color(0,90,70);
	/**@note:
	 * @param x1,y1 left boundary position
	 * @param x2,y2 right boundary position
	 * @param cnt recursion times
	 * @param H the roughness or the max changing height, keep decreasing through the recursion
	 */
	public void DrawMount(Graphics g,int x1,int y1,int x2,int y2,int cnt,double H){
		if(cnt==0){	//�����󻭳�--��������仭
			g.drawLine(x1, y1, x2, y2);
			int[] px = {x1,x2,x2,x1};
			int[] py = {y1,y2,HRIGHT,HRIGHT};
			g.setColor(mountColor);
			g.fillPolygon(px, py, 4);
			return;
		}
//		g.setColor(Color.WHITE);
		int dy = ran.nextInt((int)H+1)+1; //ɽ�߶ȵ�ƫ����
		if(ran.nextInt(100)%3 == 0){ //��������ɣ���3��������为��
			dy = -dy;
		}
		int nx = (x1+x2)/2;	
		int ny = (y1+y2)/2 + dy;//y���ƫ��  
		if(ran.nextInt(100)%4==0) H *= 0.7; //ɽ�ĸ߶�ƫ��Ҫ�ı�,��Ȼ�������
		else H *= 0.5;
		Sleep();
		DrawMount(g,x1,y1,nx,ny,cnt-1,H);
		DrawMount(g,x2,y2,nx,ny,cnt-1,H);
	}
	
	public void Sleep(){
		try {
			Thread.sleep(animatingTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
