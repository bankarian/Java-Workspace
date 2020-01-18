package SUM_UP;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ini_DRAWPLATE.Pythagoras;

public class choiceListener implements ActionListener{
	public String cur_order;
	public String[] order = {"Pytha", "Mount", "Mount_3d", "fractal"};
	JPanel panel;
	public int WIDTH, HEIGHT;
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		cur_order = e.getActionCommand();
		if(cur_order.equals(order[0])){
			panel.repaint();
			Pythagoras ptha = new Pythagoras();
			ptha.curColor = Color.blue;
			ptha.draw_fixA(panel.getGraphics(), WIDTH - 20, HEIGHT, WIDTH + 20, HEIGHT, 8, Math.PI/3);
		}
	}
}
