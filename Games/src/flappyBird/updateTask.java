package flappyBird;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class updateTask implements ActionListener{
	FlappyBird flappybird;
	
	@Override
	public void actionPerformed(ActionEvent e){
		flappybird.repaint(flappybird.getGraphics());
		flappybird.bird.y += 2;
	}
}
