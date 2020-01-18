package thread_ball;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;

public class Ball {
	public Random ran = new Random(); // 随机数生成器
	private int x, y, R;
	private int speedX, speedY;
	private int boundR, boundD;
	private Graphics g;
	private BallFrame bf;
	
	public void ini(Graphics g, BallFrame bf, int br, int bd) {
		this.g = g;
		this.bf = bf;
		this.boundR = br;
		this.boundD = bd;
		
		this.speedX = ran.nextInt(10);
		this.speedY = ran.nextInt(10);
		x = ran.nextInt(boundR);	y = ran.nextInt(boundD);
		R = ran.nextInt(100);
		return;
	}
	
	public void draw_ball() {
		g.setColor(bf.getContentPane().getBackground()); //切换为背景色，掩盖球
		g.fillOval(x-2*R-speedX, y-2*R-speedY, R, R); //掩盖上一个小球
		g.setColor(Color.black);
		g.fillOval(x-2*R, y-2*R, R, R);
		if(y >= boundD || (y-2*R) <= 0)
			speedY *= -1;
		if(x >= boundR || (x-2*R) <= 0)
			speedX *= -1;
		x += speedX;
		y += speedY;
	}
	
}
