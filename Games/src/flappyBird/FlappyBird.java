package flappyBird;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Game flappy bird 
 * @author Bankarian
 * 
 */
public class FlappyBird extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public static final Dimension screenSize = new Dimension(800, 900);
	public static final int WIDTH = 800, HEIGHT= 900;
	
	public Rectangle bird = new Rectangle(WIDTH/2 - 30, HEIGHT/2 -40, 20, 20);
	public Rectangle grass = new Rectangle(0, HEIGHT - 100, WIDTH, 20);
	public Rectangle terrain = new Rectangle(0, HEIGHT - 100 + grass.height, WIDTH, 60 - grass.height);
	List<Rectangle> barriers;
	
	public int animatingTime = 10, bird_diff = 40;
	public Timer timer;
	public updateTask update = new updateTask();
	
	FlappyBird(){
		JFrame jframe = new JFrame();
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setLocationRelativeTo(null);
		
		this.setPreferredSize(screenSize);
		this.setBackground(Color.CYAN);
		this.setLayout(null);
		this.setVisible(true);
		
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				bird.y -= bird_diff;
			}
		});
		jframe.add(this);
		
		jframe.setVisible(true);
		update.flappybird = this;
		timer = new Timer(animatingTime, update);
		timer.start();
	}
	
	/**@note
	 * 在调用repaint()的时候就已经调用了paintComponen(Graphics g)
	 */
	public void repaint(Graphics g){
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		g.setColor(Color.green);
		g.fillRect(grass.x, grass.y, grass.width, grass.height);
		g.setColor(Color.green.darker());
		g.fillRect(terrain.x, terrain.y, terrain.width, terrain.height);

	}
	
	public static void main(String[] args){
		new FlappyBird();
	}
	
}
