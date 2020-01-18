package JPanel_repaint_pra;
import java.awt.Graphics;

public class eShape {
	int x1,x2,y1,y2;
	public eShape(int x1,int y1,int x2,int y2){
		this.x1 = x1;	this.x2 = x2;
		this.y1 = y1;	this.y2 = y2;
	}
	public void Draw(Graphics g){
		g.drawLine(x1, y1, x2, y2);
	}
}
