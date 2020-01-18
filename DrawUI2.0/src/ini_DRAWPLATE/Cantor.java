package ini_DRAWPLATE;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
/**
 * 康托尔集
 * @author Bankarian
 */
public class Cantor {
	BufferedImage buffer = new BufferedImage(1000,740,BufferedImage.TYPE_INT_ARGB);
	Graphics tempG = buffer.getGraphics();
	public Color color;
	public int animatingTime = 0;
	public float diff = 6f;
	
	public	void draw(Graphics g,int sx,int ex,int curY,int cnt){
		if(cnt==0){
//			g.drawImage(buffer, 0, 0, null);
			return;
		}
		g.drawLine(sx, curY, ex, curY);
		g.drawLine(sx, curY+1, ex, curY+1);
		
		tempG.setColor(color);
		tempG.drawLine(sx, curY, ex, curY);
		tempG.drawLine(sx, curY+1, ex, curY+1);
		
		int len = (ex-sx)/3;
		draw(g,sx,sx+len,curY+6,cnt-1);
		draw(g,ex-len,ex,curY+6,cnt-1);
	}
	/**
	 *@param cnt the recursion time
	 *@param sx,ex the boundary column
	 *@param curY the heigtht
	 *@param diff diff 两线偏移间距
	 */
	public void draw_2d(Graphics g,float sx,float ex,float curY,int cnt, float stroke){
		if(cnt==0){
//			g.drawImage(buffer, 0, 0, null);
			return;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
		g2d.draw(new Line2D.Double(sx, curY, ex, curY));
		
		float len = (ex-sx)/3f;
		Sleep();
		draw_2d(g,sx,sx+len,curY+diff,cnt-1,stroke);
		draw_2d(g,ex-len,ex,curY+diff,cnt-1,stroke);		
	}
	public void Sleep(){
		try {
			Thread.sleep(animatingTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Image returnImage(){
		return buffer;
	}
}

