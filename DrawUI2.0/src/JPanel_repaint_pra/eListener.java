package JPanel_repaint_pra;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class eListener implements MouseListener{
	Graphics g;
	eShape sharr[];
	int index = 0;
	int x1,y1,x2,y2;
	public void setArr(eShape[] sharr2){
		sharr = sharr2;
	}
	public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e){
    	x1 = e.getX();
    	y1 = e.getY();
    }

    public void mouseReleased(MouseEvent e){
    	x2 = e.getX();
    	y2 = e.getY();
    	eShape line = new eShape(x1,y1,x2,y2);
    	line.Draw(g);
    	sharr[index++] = line;
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}
