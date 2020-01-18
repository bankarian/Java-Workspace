package thread_ball;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * 
 * ����С����߳�
 * @author Bankarian
 */
public class MakeBall extends Thread {
	public ArrayList<Ball> list = new ArrayList<Ball>();
	public Graphics g;
	public BallFrame bf;
	public int ball_mx = 3;
	
	public void ini(Graphics g, BallFrame bf, ArrayList<Ball> list) {
		this.g = g;
		this.bf = bf;
		this.list = list;
		return;
	}
	
	// ��������С��
	public void run() {
		while(true) {
			Ball b = new Ball();
			b.ini(g, bf, bf.getWidth(), bf.getHeight());
			this.list.add(b); //����Ž�����
			
			try {
				sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list.size() == ball_mx) break; // ����С��ĸ���
		}
		return;
	}
}
