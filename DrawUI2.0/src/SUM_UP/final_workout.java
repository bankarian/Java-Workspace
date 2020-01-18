package SUM_UP;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import fractal_MOUNT.MDrawer;
import fractal_MOUNT.Mount3D;
import fractal_MOUNT.SkyDrawer;
import fractal_TREE.tree_Main;
import ini_DRAWPLATE.Cantor;
import ini_DRAWPLATE.Pythagoras;
/**
 * 作品总和
 * @author Bankarian
 *
 */
public class final_workout extends JWindow{
	
	private static final long serialVersionUID = 1L;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int)screenSize.getWidth(), HEIGHT = (int)screenSize.getHeight();
	public static final int Exit_buttonW = 80, Next_buttonW = 100, Next_buttonH = 50, button_shift_time = 100;
	
	public final String backGround_1 = "resource/back_pic1.jpg";
	public final String exit_address_1 = "resource/buttonWhite.png", exit_address_2 = "resource/buttonBlack.png";
	public final String next_address_1 = "resource/nextLabel_1.png", next_address_2 = "resource/nextLabel_2.png";
	public final String music_file_address = "resource/backMusic.wav";
	public ImageIcon backGround_inter, exit_button, next_button;
	public AudioClip music_player;
	
	BufferedImage buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public Graphics myG;
	public static int step = 1;
	
	final_workout(){
		this.setSize(screenSize);
		this.setVisible(true);
		myG = this.getContentPane().getGraphics();
		
		this.play_music();
		backGround_inter = new ImageIcon(backGround_1);
		myG.drawImage(backGround_inter.getImage(), 0, 0, WIDTH, HEIGHT, null);
		this.show_Button();
		this.show_name(myG);	
		/**@note
		 * 自定义图片为按钮，鼠标点击到按钮区域，进行相关变换
		 * 两种button : next_button 和 exit_button
		 */
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int curC = e.getX(), curR = e.getY();
//				System.out.println("get in curX="+curC+" curY="+curR);
				if(curR>=HEIGHT-Exit_buttonW && curR<HEIGHT && curC>=0 && curC<Exit_buttonW){
					exit_Button_shift();
					try {
						Thread.sleep(button_shift_time);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
				else if(curR>=HEIGHT-Next_buttonH && curR<HEIGHT && curC>=WIDTH-Next_buttonW && curC<WIDTH){
					next_Button_shift();
					switch(step)
					{
						case 1:{
							show_Mount(myG);
							show_Button();
							step += 1;
							break;
						}
						case 2:{
							shift_step(Color.BLACK);					
							show_3DMount(myG);
							show_Button();
							step += 1;
							break;
						}
						case 3:{
							shift_step(new Color(250,245,225));
							show_Cantor_Ptree(myG);
							show_Button();
							step += 1;
							break;
						}
						case 4:{
							show_fractal_tree();
							break;
						}
					}
				}
			}
		});	
	}
	public void shift_step(Color color){
		myG.setColor(color);
		myG.fillRect(0, 0, WIDTH, HEIGHT);
	}
	public void Sleep(int len){
		try {
			Thread.sleep(len);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void exit_Button_shift(){
		exit_button = new ImageIcon(exit_address_2);
		myG.drawImage(exit_button.getImage(), 0, HEIGHT-Exit_buttonW, Exit_buttonW,Exit_buttonW, null);
	}
	public void next_Button_shift(){
		next_button = new ImageIcon(next_address_2);
		myG.drawImage(next_button.getImage(), WIDTH-Next_buttonW, HEIGHT-Next_buttonH, Next_buttonW, Next_buttonH, null);
		try {
			Thread.sleep(button_shift_time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		next_button = new ImageIcon(next_address_1);
		myG.drawImage(next_button.getImage(), WIDTH-Next_buttonW, HEIGHT-Next_buttonH, Next_buttonW, Next_buttonH, null);
	}
	public void show_Button(){
		exit_button = new ImageIcon(exit_address_1);
		next_button = new ImageIcon(next_address_1);
		myG.drawImage(exit_button.getImage(), 0, HEIGHT-Exit_buttonW, Exit_buttonW,Exit_buttonW, null);
		myG.drawImage(next_button.getImage(), WIDTH-Next_buttonW, HEIGHT-Next_buttonH, Next_buttonW, Next_buttonH, null);
	}
	public void show_name(Graphics g){
		SkyDrawer name_drawer = new SkyDrawer();
		name_drawer.animatingTime = 10;		name_drawer.firstColor = Color.BLUE;
		name_drawer.secondColor = Color.BLACK;
		name_drawer.WIDTH = WIDTH;	name_drawer.HEIGHT = HEIGHT;
		name_drawer.first_starShape = "Beny";
		name_drawer.second_starShape = "哲~";
		name_drawer.DrawStar(g);
	}
	public void show_Mount(Graphics g){
		int x1 = 0, x2 = WIDTH;
    	int y1 = (int)(HEIGHT/2)+(int)(200*Math.random());
    	int y2 = (int)(HEIGHT/2 + 200)+(int)(100*Math.random());
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		show_Button();
		SkyDrawer sky_drawer = new SkyDrawer();
		sky_drawer.WIDTH = WIDTH;	sky_drawer.HEIGHT = HEIGHT;		sky_drawer.backColor = Color.BLACK;
		sky_drawer.animatingTime = 15;
		sky_drawer.starsCnt = 100;	sky_drawer.first_stars = 40;
		sky_drawer.DrawMoon(g); 	sky_drawer.DrawStar(g);
		
		MDrawer mount_drawer = new MDrawer();
		mount_drawer.HRIGHT = HEIGHT;
		mount_drawer.animatingTime = 10;
		mount_drawer.mountColor = new Color(0, 51, 5);
		mount_drawer.DrawMount(g, x1, y1, x2, y2, 8, 400);
	}
	public void show_3DMount(Graphics g){
		Mount3D drawer = new Mount3D();
		drawer.animatingTime = 0;	drawer.color = Color.YELLOW;
		drawer.draw(g, (double)50, (double)HEIGHT-300, (double)(WIDTH/2+200), (double)(HEIGHT/2-200), (double)WIDTH-20, (double)(HEIGHT-200), 8);
	}
	public void show_Cantor_Ptree(Graphics g){
		Cantor drawer = new Cantor();
		drawer.animatingTime = 100;
		Color[] colors = {Color.gray, Color.BLUE, Color.green};
		float[] curY = { 100f, (float)(HEIGHT/2 - 100), (float)(HEIGHT-200)};
		float[] sx = { 50f, (float)(WIDTH/2-500), (float)(WIDTH/2 + 50)};
		float[] ex = { 200f, (float)(WIDTH/2-500 + 420), (float)(WIDTH/2 + 50 + 700)};
		float[] diff = { 4f, 20f, 60f};
		float[] stroke = { 1f, 8f, 20f};	
		for(int i=0;i<3;++i){
			drawer.color = colors[i];
			drawer.diff = diff[i];
			drawer.draw_2d((Graphics2D)g, sx[i], ex[i], curY[i], 3, stroke[i]);
		}
		Color[] colors2 = {Color.BLUE, Color.ORANGE, Color.WHITE};
		double[] orderA = { (double)(Math.PI/12), (double)(Math.PI/8), (double)(Math.PI/3)};
		int[] depth = {9,10,11};
		int[] d = {10, 100, 400};
		Pythagoras tree = new Pythagoras();
		tree.buffer = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		drawer.animatingTime = 0;
		for(int i=0;i<3;++i){
			drawer.color = colors[i];
			drawer.diff = diff[i];
			drawer.draw_2d((Graphics2D)tree.buffer.getGraphics(), sx[i], ex[i], curY[i], 3, stroke[i]);
			tree.curColor = colors2[i];
			if(i==0)
				tree.draw_fixA(g, (double)(sx[i]/4+ex[i]/4+d[i]), (double)curY[i], (double)(sx[i]*3/9+ex[i]*3/9+d[i]), (double)curY[i], depth[i], orderA[i]);
			else if(i==1){
				tree.draw_fixA(g, (double)(sx[i]/4+ex[i]/4+d[i]), (double)curY[i], (double)(sx[i]*3/9+ex[i]*3/9+d[i]), (double)curY[i]+40, depth[i], orderA[i]);			
			}
			else{
				tree.draw_fixA(g, (double)(sx[i]/4+ex[i]/4+d[i]), (double)curY[i]+150, (double)(sx[i]*3/9+ex[i]*3/9+d[i]), (double)curY[i], depth[i], orderA[i]);
			}
		}
	}
	public void show_fractal_tree(){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				new tree_Main();
			}
		});
	}
	/**
	 * @note java 只能播放无损音质,如.wav
	 */
	public void play_music(){
		try {
			File f = new File(music_file_address);
			URI uri = f.toURI();
			URL url = uri.toURL();
			music_player = Applet.newAudioClip(url);
			music_player.play();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		new final_workout();
	}
}
