package fractal_MOUNT;
import java.awt.Color;
import java.awt.Graphics;

/**@note drawing the nightsky
 * @param animatingTime is for drawing the stars, sleep a while
 * @param starCnt the total number of stars
 * Initially there are 40 white stars, the others are yellow stars.
 * */
public class SkyDrawer {
	public int WIDTH=1000, HEIGHT=800, animatingTime = 0;
	public int starsCnt = 70, first_stars = 40;
	public Color backColor = Color.darkGray, firstColor = Color.YELLOW, secondColor = Color.WHITE;
	public String first_starShape = "*", second_starShape = "*";
	//Math.random()随机产生0.0--1.0的小数
	public void DrawStar(Graphics g){
		for(int i=0;i<starsCnt;++i){
			int posX = (int)(WIDTH*Math.random());
			int posY = (int)(HEIGHT*Math.random());
			if(i < first_stars){
				g.setColor(firstColor);
				g.drawString(first_starShape, posX, posY);
			}
			else{
				g.setColor(secondColor);
				g.drawString(second_starShape, posX, posY);
			}
			
			Sleep();
		}	
	}
	public void DrawMoon(Graphics g){
		int posX = (int)(WIDTH*Math.random() - 50);
		int posY = (int)(100*Math.random()) + 90;//月亮至少再距顶端40的位置
		g.setColor(Color.WHITE);
		g.fillOval(posX, posY, 80, 80);
		g.setColor(backColor);//用背景颜色覆盖,形成月亮
		g.fillOval(posX-(10+(int)(4*Math.random())), posY-(10+(int)(4*Math.random())), 80, 80);
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
