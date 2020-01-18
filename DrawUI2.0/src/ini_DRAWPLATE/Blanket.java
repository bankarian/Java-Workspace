package ini_DRAWPLATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

//Blanket
public class Blanket {
	BufferedImage buffer = new BufferedImage(1000,740,BufferedImage.TYPE_INT_ARGB);
	Graphics tempG = buffer.getGraphics();
	Color color;
	public void draw(Graphics g,int x1,int y1,int size,int cnt){
		--cnt;
		if(cnt==0){
//			g.drawImage(buffer, 0, 0, null);
			return;
		}
		g.fillRect(x1, y1, size, size);
		tempG.setColor(color);
		tempG.fillRect(x1, y1, size, size);
		int[][]dir = {
				{size,0},{0,size},{-size,0},{0,-size},
				{size,size},{-size,size},{size,-size},{-size,-size}
		};
		size = (int)(size/3);
		for(int i=0;i<8;++i){
			int nx = x1+dir[i][0]+size;
			int ny = y1+dir[i][1]+size;
			draw(g,nx,ny,size,cnt);
		}
	}
	public BufferedImage returnImage(){
		return buffer;
	}
}
