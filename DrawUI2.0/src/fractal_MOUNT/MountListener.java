package fractal_MOUNT;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MountListener implements MouseListener,ActionListener{
	int x1,y1,x2,y2;
	String order;
	Graphics g;
	@Override
	public void actionPerformed(ActionEvent e){
		order = e.getActionCommand();
	}
	@Override
    public void mouseReleased(MouseEvent e){
		x1 = 0;	x2 = 1000;
    	y1 = 600+(int)(100*Math.random());
    	y2 = 300+(int)(200*Math.random());
    	if(order.equals("Mountain")){
    		g.setColor(Color.darkGray);
    		g.fillRect(0, 0, 1000, 700);
    		SkyDrawer sky = new SkyDrawer();
    		sky.DrawStar(g);
    		sky.DrawMoon(g);
    		MDrawer mountain = new MDrawer();
    		mountain.DrawMount(g, x1, y1, x2, y2, 8,200.0);
    	}
    	else if(order.equals("3DMountain")){
    		g.setColor(Color.WHITE);
    		g.fillRect(0, 0, 1000, 700);
    		Mount3D mountain = new Mount3D();
    		mountain.color = Color.BLACK;
    		mountain.draw(g, 200, 300, 905, 243, 552, 414, 7);
    		mountain.draw(g, 40, 600, 600, 650, 219, 402, 7);
//    		g.setColor(Color.red);
//    		g.drawLine(300, 300, 505, 243);
//    		g.drawLine(300, 300, 452, 414);
//    		g.drawLine(505, 243, 452, 414);
    	}
    }
	public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}
