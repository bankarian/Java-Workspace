package SUM_UP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * 
 * @author Bankarian
 * choice_Panel(JSlider, JButton)	·ÅÖÃÔÚ×ó²à£¬ ¿í220
 * main_Panel(change under the control of choice_Panel)
 * 
 */
public class Sum_up_2 extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1400, HEIGHT = 1000, choice_size = 220, choice_sr = WIDTH - choice_size;
	public static final Dimension this_size = new Dimension(WIDTH, HEIGHT);
	public static final Dimension main_size = new Dimension(WIDTH - choice_size - 80, HEIGHT);
	
	public static final int Slider_ini = 0, Slider_max = 100, Slider_len = 200;
	public static final Rectangle Slider_shape = new Rectangle( 50, 50, Slider_len, 20);
	public JSlider choiceSlider;
	public Container cp;
	choiceListener Listener = new choiceListener();
	
	public String[] order = {"Pytha", "Mount", "Mount_3d", "fractal"};
	public String[] order_address = {
			"resource/pytha_b.png", "resource/mount_b.png", "resource/mount3d_b.png", "resource/fractal_b.png"
	};

	Sum_up_2(){
		this.setSize(this_size);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		
		cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		class Main extends JPanel{
			Main(){
				this.setPreferredSize(main_size);
				this.setVisible(true);
			}
		}
		Main mainPanel = new Main();
		this.add(mainPanel, BorderLayout.WEST);
		
		Listener.panel = mainPanel;
		Listener.WIDTH = main_size.width;		Listener.HEIGHT = main_size.height;
		
		class Choice extends JPanel{
			Choice(){
				this.setPreferredSize(new Dimension(choice_size, HEIGHT));
				this.setBackground(Color.black);
				this.setVisible(true);
			}
		}
		Choice choicePanel = new Choice();
		this.add(choicePanel, BorderLayout.EAST);
		makeButton(choicePanel);
		
		
		
		this.setVisible(true);
		
	}
	
	public void makeSlier(JPanel p){
		choiceSlider = new JSlider(0, Slider_max, Slider_ini);
		choiceSlider.setPaintTicks(true);
		choiceSlider.setMajorTickSpacing((Slider_max - Slider_ini) / 10);
		choiceSlider.setMinorTickSpacing((Slider_max - Slider_ini) / 20);
		
		choiceSlider.setBounds(Slider_shape);
		choiceSlider.setOpaque(false);
		p.add(choiceSlider);
	}
	public void makeButton(JPanel p){
		for(int i=0; i<order.length; ++i){
			Icon icon = new ImageIcon(order_address[i]);
			JButton button = new JButton();
			button.setActionCommand(order[i]);
			button.setPreferredSize(new Dimension(160, 80));
			button.setIcon(icon);
			button.addActionListener(Listener);
			
			p.add(button);
		}
	}
	public static void main(String[] args){
		new Sum_up_2();
	}
}
