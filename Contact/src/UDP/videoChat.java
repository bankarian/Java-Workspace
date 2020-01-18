package UDP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.platform.WindowUtils;

import vedioCam.VideoCam;

/**
 * 视频聊天的界面
 * @author Bankarian
 * @note JFrame 设置透明：
 * 		要先Underorated，即将最上边的选项栏去掉
 * 		setBackground 的第四个参数就是设置透明度
 * @note 将窗体的选项栏去掉之后，窗体不能移动
 * 		要处理鼠标拖动事件，注意要在“窗体可见之前”设置
 * @ques Receiver结束时如何停止
 */
@SuppressWarnings("serial")
public class videoChat extends JFrame implements ActionListener{
	
	public static final int WIDTH = (int) (600 * 1.414), HEIGHT = 600;
	public static final int choiceW = (int) (WIDTH * (0.2/1.414)), viewW = WIDTH - choiceW;
	public String backAddr = "res/timg.jpg";
	public JPanel choicePanel, viewPanel;
	public VideoCam cam;
	public static videoChat frame;
	public final int bw = (int) (choiceW*0.618), bh = (int) (bw/1.414);
	
	public static String destIP = "10.63.145.44", srcIP = "10.63.145.44";
	public static int destPort = 5000, srcPort = 6000;
//	public static int destPort = 6000, srcPort = 5000;
	
	//用于处理去掉边框后的frame的移动问题
	private int xOld, yOld;
	
	public videoChat(String dst_ip, int dest_port, String src_ip, int src_port) {
		destIP = dst_ip;	destPort = dest_port;
		srcIP = src_ip;		srcPort = src_port;
		return;
	}
	public videoChat() {}
	
	public void ini() {
		frame = new videoChat();
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);
		
		set_choicePanel();
		set_viewPanel();
		frame.setUndecorated(true);
		
		System.setProperty("sun.java2d.noddraw", "true");
		WindowUtils.setWindowAlpha(frame, 0.9f);
		
		Frame_setMovable();
		frame.setVisible(true);
		cam = new VideoCam(destIP, destPort, srcIP, srcPort, 50, 50);
		cam.g = viewPanel.getGraphics();
		
		return;
	}
	
	public void set_choicePanel() {
		
		choicePanel = new OpaquePanel(0.8f, OpaquePanel.BLACK);
		choicePanel.setBackground(Color.black);
		choicePanel.setSize(new Dimension(choiceW, HEIGHT));
		choicePanel.setOpaque(false);
		set_Button();
		
		choicePanel.setBounds(frame.getWidth()-choiceW, 0, choiceW, HEIGHT);
		frame.add(choicePanel);
		return;
	}
	public void set_viewPanel() {
		viewPanel = new OpaquePanel(0.1f, OpaquePanel.WHITE, frame.getWidth()-choiceW, HEIGHT);
		viewPanel.setOpaque(true);
//		viewPanel = new JPanel();
		viewPanel.setSize(new Dimension(frame.getWidth()-choiceW, HEIGHT));
		viewPanel.setBounds(0, 0, frame.getWidth()-choiceW, HEIGHT);
		frame.add(viewPanel);
		return;
	}
	//设置背景图片透明化
	@Override
	public void paint(java.awt.Graphics g) {
		super.paint(g);
//		ImageIcon img = new ImageIcon(backAddr);
//		g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		return;
	};
	
	//处理窗体去掉边框后的拖动事件
	public void Frame_setMovable() {
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOld = e.getX();
				yOld = e.getY();
			}
		});
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int xOnScreen =  e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int dx = xOnScreen - xOld;
				int dy = yOnScreen - yOld;
				
				frame.setLocation(dx, dy);
			}
		});
		return;
	}
	
	public void set_Button() {
		String[] oper = { "Exit", "Send", "Recei"};
		for(int i=0; i<oper.length; ++i) {
			JButton btn = new JButton(oper[i]);
			btn.setActionCommand(oper[i]);
			btn.setPreferredSize(new Dimension(bw, bh));
			btn.addActionListener((ActionListener) this);
			
			choicePanel.add(btn);
		}
		return;
	}
	
	public void actionPerformed(ActionEvent e) {
		String order = e.getActionCommand();
		if(order.equals("Exit")) {
//			System.exit(0);
			frame.dispose();
			this.cam.stopAll();
		} else if(order.equals("Send")) {
			this.cam.startSend();
		} else if(order.equals("Recei")) {
			this.cam.startReceive();
		}
		return;
	}

	public static void main(String[] args) {
		videoChat aa = new videoChat();
		aa.ini();
		
		return;
	}
}
