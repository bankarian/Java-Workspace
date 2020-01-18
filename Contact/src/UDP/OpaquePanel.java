package UDP;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Bankarian
 * 本质上是在panel上画一个透明度可调的白色图片
 * 所以要达到透明的效果，该panel要设置为纯透明
 */
@SuppressWarnings("serial")
public class OpaquePanel extends JPanel{
	public float alpha;
	public String imageAddr = "res/white.png";
	public static final String WHITE = "res/white.png", BLACK = "res/black.jpg";
	private int pw = 0, ph = 0;
	
	public OpaquePanel(float opa, String imgAddr) {
		this.alpha = opa;
		this.imageAddr = imgAddr;
	}
	//指定画出的颜色步的大小
	public OpaquePanel(float opa, String imgAddr, int width, int height) {
		this.alpha = opa;
		this.imageAddr = imgAddr;
		this.pw = width;
		this.ph = height;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//画一张白色的图片，设置透明度
		ImageIcon imag = new ImageIcon(imageAddr);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		if(pw == 0 || ph == 0)
			g2d.drawImage(imag.getImage(), 0, 0, null);
		else{
			g2d.drawImage(imag.getImage(), 0, 0, pw, ph, null);
		}
		return;
	}
	
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		jf.getContentPane().setBackground(Color.black);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(500, 600);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		
		JPanel jp = new OpaquePanel(0.9f, OpaquePanel.WHITE, 900, 100);
		jp.setPreferredSize(new Dimension(900, 100));
		jp.setOpaque(false);
		jf.add(jp, BorderLayout.NORTH);
		
		return;
	}
}
