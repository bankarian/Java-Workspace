package UDP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 聊天界面 + 画图版 + 视频
 * @author Bankarian
 * @note 所有组件使用的都是setbound的绝对位置设置
 * @note 文本聊天支持多种语言翻译
 * 		容器使用绝对布局的方式，使用ComponentResize函数监听容器的大小变化-->同时重新设置其组件的位置大小
 * @problem_left OPaquePanel 随容器大小变化失败
 */
public class myBoard extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public static final int boardW = 600, boardH = (int) (600 * 1.414);
	public static final int btnSize = 30, chara = 1, draw = 2, clear = 3;
	public static String srcName = "Beney";
//	public static String srcName = "Hank";
	public static Font font;
	public static JFrame frame;
	
	public JPanel drawPanel, chatPanel;
	public OpaquePanel OPanel;
	public JButton btnSend, btnClear, btnMore;
	public JScrollPane inSpace, chatSpace;
	public JTextArea inText,chatText;
	public int x1,y1, x2,y2;
	public int[] Info;
	public String dest = "192.168.31.152", src = "192.168.31.152";
	public String backAddr = "res/back.jpg";

//	public int dest_port = 9000, src_port = 8000;
	public int dest_port = 8000, src_port = 9000;
	
	public int xOld, yOld;
	public String framIcon = "res/upIcon.jpg";

	
	public void iniBoard() {
		frame = new JFrame();
		frame.setSize(new Dimension(boardW, boardH));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		
		setDrawPane();
		font = new Font("宋体", Font.BOLD, 16);
		setInSpace();
		setChatSpace();
		
		listener.start(); //接收线程
		
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				int width = frame.getWidth(),
						height = frame.getHeight();
				int drawW = width, 
						drawH =(int) (height*(0.618*0.9));
				drawPanel.setBounds(0, height-drawH, drawW, drawH);
				chatPanel.setBounds(0, 0, width, height - drawH);
			}
		});
		frame.setVisible(true);
	}
	
	
	//发送信息
	@SuppressWarnings("resource")
	public void sendPacket(byte[] buff) {
		try {
			// 发送端的socket可以不用指定监听地址
			DatagramSocket sender = new DatagramSocket();
			
			//packet的传送地址为监听端
			SocketAddress destAddr = new InetSocketAddress(dest, dest_port);
			DatagramPacket packet = new DatagramPacket(buff, buff.length, destAddr);
			
			packet.setLength(buff.length);
			sender.send(packet);
//			System.out.println("锟斤拷锟酵成癸拷");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//设置画图区，边画边传送画笔的坐标
	//注意MouseDragged要使用MotionListener
	public void setDrawPane() {
		int drawW = frame.getWidth(), drawH =(int) (frame.getHeight()*(0.618*0.9));
		drawPanel = new JPanel();
		drawPanel.setBounds(0, boardH-drawH, drawW, drawH);
		drawPanel.setBackground(new Color(250, 245, 235));
		drawPanel.setLayout(null);
		
		drawPanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				x1 = e.getX();	y1 = e.getY();
			}
		});
		drawPanel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {}
			@Override
			public void mouseDragged(MouseEvent e) {
				x2 = e.getX();	y2 = e.getY();
				Graphics g = drawPanel.getGraphics();
				g.setColor(Color.black);
				g.drawLine(x1, y1, x2, y2);
				
				byte[] bytes = new byte[20]; // 前4位为协议，后16位是两个坐标的xy值
				byte[] flag = intToBytes(draw); // 转换协议
				System.arraycopy(flag, 0, bytes, 0, flag.length);
				
				//拷贝成一个完整的byte数组
				byte[] pos = intToBytes(x1);	System.arraycopy(pos, 0, bytes, 4, 4);
				pos = intToBytes(y1);	System.arraycopy(pos, 0, bytes, 8, 4);
				pos = intToBytes(x2);	System.arraycopy(pos, 0, bytes, 12, 4);
				pos = intToBytes(y2);	System.arraycopy(pos, 0, bytes, 16, 4);	
				sendPacket(bytes);
	
				x1 = x2; 	y1 = y2;
			}
		});
		return;
	}
	
	/** 设置输入框以及功能按钮 **/
	public void setInSpace() {
		int gap = 4;
		
		inText = new JTextArea(100, 100);
		inText.setFont(font);
		inText.setLineWrap(true);
		inSpace = new JScrollPane(inText);
		inSpace.setBounds(new Rectangle(2*btnSize+2*gap, 0, frame.getWidth() - 3*btnSize - 26, (int) (frame.getHeight()*(0.618*0.06))));
		drawPanel.add(inSpace);	
		
		//发送按钮图标
		ImageIcon Isend = new ImageIcon("res/send.png");
		Isend.setImage(Isend.getImage().getScaledInstance(btnSize, btnSize, Image.SCALE_DEFAULT));//图片自适应大小
		
		btnSend = new JButton();
		btnSend.setActionCommand("send");
		btnSend.addActionListener(this);
		btnSend.setIcon(Isend);
		btnSend.setBounds(new Rectangle(inSpace.getWidth()+2*btnSize+ 2*gap, 0, btnSize, btnSize));
		drawPanel.add(btnSend);
		
		//清理画板图标
		ImageIcon Iclear = new ImageIcon("res/clear.png");
		Iclear.setImage(Iclear.getImage().getScaledInstance(btnSize, btnSize, Image.SCALE_DEFAULT));
		
		btnClear = new JButton();
		btnClear.setActionCommand("clear");
		btnClear.addActionListener(this);
		btnClear.setIcon(Iclear);
		btnClear.setBounds(new Rectangle(0, 0, btnSize, btnSize));
		drawPanel.add(btnClear);
		
		ImageIcon Imore = new ImageIcon("res/choice.png");
		Imore.setImage(Imore.getImage().getScaledInstance(btnSize, btnSize, Image.SCALE_DEFAULT));
		
		btnMore = new JButton();
		btnMore.setActionCommand("more");
		btnMore.addActionListener(this);
		btnMore.setIcon(Imore);
		btnMore.setBounds(new Rectangle(btnSize + gap, 0, btnSize, btnSize));
		drawPanel.add(btnMore);
		
		drawPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				int width = drawPanel.getWidth(), 
						height = drawPanel.getHeight();
				inSpace.setBounds(new Rectangle(2*btnSize+2*gap, 0, frame.getWidth() - 3*btnSize - 26, (int) (frame.getHeight()*(0.618*0.06))));
				btnSend.setBounds(new Rectangle(inSpace.getWidth()+2*btnSize+3*gap, 0, btnSize, btnSize));
				btnClear.setBounds(new Rectangle(0, 0, btnSize, btnSize));
				btnMore.setBounds(new Rectangle(btnSize + gap, 0, btnSize, btnSize));
				inText.setText("                                                             ");
				inText.setText("");
			}
		});
//		frame.add(drawPanel, BorderLayout.SOUTH);
		frame.add(drawPanel);
		return;
	}
	
	/**
	 * 设置聊天交流区域界面
	 * @param chatPanel layout为空，使用绝对位置添加
	 * @param chatSpace 可滑动文本区，内含一个全透明的文本区，最上层是一层半透明的Panel
	 * 					实现半透明的效果
	 * @param chatText 设置为不可编辑且全透明
	 */
	public void setChatSpace() {
		int chatW = frame.getWidth()-30, chatH = (int) (frame.getHeight()*0.44);
		
		chatPanel = new myBoard();
		chatPanel.setBounds(0, 0, frame.getWidth(), (int) (frame.getHeight()*0.5));
		chatPanel.setLayout(null);
		
		chatText = new JTextArea(10, 10);
		chatText.setEditable(false);	//不可编辑
		chatText.setOpaque(false);
		chatText.setFont(font);
		chatText.setLineWrap(true);
		
		chatSpace = new JScrollPane(chatText);
//		chatSpace.setBounds((int) ((frame.getWidth()-chatW)/2 - 2*4), 8, chatW, chatH);
		chatSpace.setOpaque(false);
		chatSpace.getViewport().setOpaque(false);
		
		OPanel = new OpaquePanel(0.6f, OpaquePanel.WHITE, chatW, chatH);
		OPanel.setBounds((frame.getWidth()-chatW)/2 - 2*4, 8, chatW, chatH);
		OPanel.setOpaque(false);
		
		chatPanel.add(chatSpace);
		chatPanel.add(OPanel);	//盖上一层半透明的OPanel
		
		chatPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				int chatW = chatPanel.getWidth()-30,
						chatH = frame.getHeight() - drawPanel.getHeight() - 8;
				chatSpace.setBounds((int) ((chatPanel.getWidth()-chatW)/2 - 2*4), 8, chatW, chatH);
				OPanel = new OpaquePanel(0.6f, OpaquePanel.WHITE, chatW, chatH);
				OPanel.setOpaque(false);
				OPanel.setBounds((frame.getWidth()-chatW)/2 - 2*4, 8, frame.getWidth(), chatH);
			}
		});
		frame.add(chatPanel);
		return;
	}

	/**
	 * 接收消息的线程
	 * @note 接收到的前4个byte为协议内容，要先处理，之后分情况继续
	 * 因为接收packet存在阻塞，所以另开线程
	 */
	Thread listener = new Thread() {
		@SuppressWarnings("resource")	
		public void run() {
			try {
				SocketAddress srcAddr = new InetSocketAddress(src, src_port);
				DatagramSocket receiver = new DatagramSocket(srcAddr);	//监听本地地址
				
				while(true) {	
					byte[] buff = new byte[receiver.getReceiveBufferSize()];
					DatagramPacket packet = new DatagramPacket(buff, buff.length);
					receiver.receive(packet);
//					System.out.println("buffer length = " + buff.length);
					
					byte[] flag = new byte[4];
					System.arraycopy(buff, 0, flag, 0, flag.length);
					int comm = bytesToint(flag);
					
					if(comm == chara) {
						String content = new String(buff, 4, buff.length-4).trim(); //要记得使用trim将多余的bytes信息去除
						chatText.append(content + "\r\n");//在原文基础上增添、换行
					} 
					else if(comm == draw){
						byte[] sx  = new byte[4];
						byte[] sy = new byte[4];
						byte[] ex = new byte[4];
						byte[] ey = new byte[4];
						//前4个byte为协议
						System.arraycopy(buff, 4, sx, 0, 4);
						System.arraycopy(buff, 8, sy, 0, 4);
						System.arraycopy(buff, 12, ex, 0, 4);
						System.arraycopy(buff, 16, ey, 0, 4);
						x1 = bytesToint(sx);	y1 = bytesToint(sy);
						x2 = bytesToint(ex);	y2 = bytesToint(ey);
						Graphics g = drawPanel.getGraphics();
						g.setColor(Color.black);
						g.drawLine(x1, y1, x2, y2);
					} 
					else if(comm == clear) {
						drawPanel.repaint();
					}
					
				}
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			
		}
	};
	
	//按钮的命令设置
	@Override
	public void actionPerformed(ActionEvent e) {
		String order = e.getActionCommand();
		
		//将int类型协议转化为byte，存在前4位，之后存入内容
		if(order.equals("send")) {
			String content = srcName + ": " + inText.getText();
			
			byte[] tmp = content.getBytes();
			byte[] flag = intToBytes(chara);
			byte[] sendCont = new byte[tmp.length + flag.length];
			System.arraycopy(flag, 0, sendCont, 0, flag.length);
			System.arraycopy(tmp, 0, sendCont, flag.length, tmp.length);
			sendPacket(sendCont);
			
			inText.setText("");
			chatText.append(content + "\r\n");
		} 
		else if(order.equals("clear")) {
			drawPanel.repaint();
			byte[] ord = intToBytes(clear);
			sendPacket(ord);
		}
		else if(order.equals("more")) {
			if(!c_on)
				show_choice();
		}
		
		//将选项cpanel设置为不可见,可以达到不可视的效果，之后再将其remove
		//因为remove之后用户还能够看到cpanel，若重绘则会将绘图板上的图像清除
		else if(order.equals("close_c")) {
			cpanel.setVisible(false); 
			drawPanel.remove(cpanel);
			c_on = false;
		}
		else if(order.equals("video_c")) {
			videoChat vchat = new videoChat(dest, dest_port + 100, src, src_port + 100);
			vchat.ini();
		}
		else if(order.equals("trans_c")) {
			String to = trans.hashmap.get(trans.getSelectedItem());
			if(Translater.preI == to) return;
			else Translater.preI = to;
			String query = inText.getText();
			if(query.length() > 0) {
				String result = Translater.Trans(query, trans.orgin, to);
				inText.setText(result);
			}				
		}
		return;
	}
	
	
	public JPanel cpanel;
	public static final int cheight = 40, cwidth = 300, cgap = 4;
	public Translater trans;
	public boolean c_on; /* 用于防止重复创建选择框 */
	/**
	 * 设置选项栏 添加翻译器
	 * @note 将其他功能的按钮都存放在这
	 */
	public void show_choice() {
		c_on = true;
		
		cpanel = new JPanel();
		cpanel.setBackground(Color.cyan);
//		cpanel = new OpaquePanel(0.6f, OpaquePanel.BLACK, cwidth, cheight);
		cpanel.setSize(new Dimension(cwidth, cheight));
		cpanel.setLayout(null);
		cpanel.setBounds(0, btnSize, cwidth, cheight);
		
		String[] order = { "close_c", "video_c", "trans_c"};
		String[] pic = { "res/close.png", "res/video.png", "res/trans.png"};
		for(int i=0; i<order.length; ++i) {
			JButton btn = new JButton();
			btn.setActionCommand(order[i]);
			btn.setBounds(cgap+i*(cgap+btnSize), (int) (cheight-btnSize)/2, btnSize, btnSize);
			btn.addActionListener(this);
			
			ImageIcon icon = new ImageIcon(pic[i]);
			icon.setImage(icon.getImage().getScaledInstance(btnSize, btnSize, Image.SCALE_DEFAULT));
			btn.setIcon(icon);
			
			cpanel.add(btn);
		}
		trans = new Translater(order.length*(cgap+btnSize), (int) (cheight-btnSize)/2, 76, btnSize);
		//添加翻译器
		cpanel.add(trans);
		drawPanel.add(cpanel);
		
		/*添加之后要绘出才能可见, setVisible没有用
		    只需要调用paintComponents即可
		    调用repaint()会等价调用所有重绘的函数
		 */
		drawPanel.paintComponents(drawPanel.getGraphics());
		return;
	}
	
	public byte[] intToBytes(int x) {
		byte[] ans = new byte[4];
		ans[0] = (byte) ((x >> 24) & 0xFF); //高位在前
		ans[1] = (byte) ((x >> 16) & 0xFF);
		ans[2] = (byte) ((x >> 8) & 0xFF);
		ans[3] = (byte) ((x >> 0) & 0xFF); //低位在后
		return ans;
	}

	public int bytesToint(byte[] bytes) {	
		int num = (int) (bytes[3] & 0xFF);
		num |= (int) ((bytes[2] << 8) & 0xFF00);
		num |= (int) ((bytes[1] << 16) & 0xFF0000);
		num |= (int) ((bytes[0] << 24) & 0xFF000000);
		return num;
	}
	
	/* chatSpace区域的背景
	      若要在Panel上初始一张背景图片，要在paintcomponent中进行
	      因为只有当父层JFrame的graphics实现后才能使用Panel的graphics
	   super.方法() 保证了JFrame的graphcis的实现
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon background = new ImageIcon(backAddr);	
		
		g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public static void main(String[] args) {
		new myBoard().iniBoard();
		return;
	}

}
