package thread_ball;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

public class BallFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public ArrayList<Ball> list;
	public Graphics g;
	public static final int width = 800, height = 600;
	
	public void ini() {
		list = new ArrayList<Ball>();
	}
	
	public void showF() {
		ini();
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
		g = this.getGraphics();
	}
	
	public static void main(String[] args) {
		BallFrame bf = new BallFrame();
		bf.showF();
		MakeBall maker = new MakeBall();
		maker.ini(bf.g, bf, bf.list);
		
		MoveBall mb = new MoveBall();
		mb.ini(bf.list);
		
		maker.start();
		mb.start();
		return;
	}
}
