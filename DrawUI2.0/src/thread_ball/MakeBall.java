package thread_ball;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * 
 * 创建小球的线程
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
	
	// 连续创建小球
	public void run() {
		while(true) {
			Ball b = new Ball();
			b.ini(g, bf, bf.getWidth(), bf.getHeight());
			this.list.add(b); //把球放进队列
			
			try {
				sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list.size() == ball_mx) break; // 设置小球的个数
		}
		return;
	}
}
