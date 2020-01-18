package vedioCam;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

/**
 * 视频通信 + 简单视频处理
 * @author Bankarian
 * 
 */
public class VideoCam {
	
	public static Webcam webcam;
	public static Graphics g;
	public static String destIP = "10.68.141.61", srcIP = "10.68.141.61";
//	public static int destPort = 6000, srcPort = 5000;
	public static int sx = 50, sy = 50;
	public static boolean isSending  = false, isReceving = false;
	private static DatagramSocket sender, receiver;
	public static int destPort = 5000, srcPort = 6000;
	
	public VideoCam() {}
	
	public VideoCam(String dst_ip, int dst_port, String src_ip, int src_port, int sX, int sY) {
		destIP = dst_ip;	destPort = dst_port;
		srcIP = src_ip;		srcPort = src_port;
		sx = sX; 	sy = sY;
		return;
	}

	
	Thread getCamSend = new Thread() {
		@Override
		public void run() {
			webcam = Webcam.getDefault(); 
			//设置镜头大小为默认
			webcam.setViewSize(WebcamResolution.VGA.getSize());	
			webcam.open();
			
			while(isSending) {
				BufferedImage inig = webcam.getImage();
				
				if(inig == null) {
					System.out.println("fail to catch image");
					return;
				}
//				BufferedImage sendImage = mod_Baiye(inig);
				BufferedImage sendImage = inig;
				try {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					ImageIO.write(sendImage, "jpg", out);
					
					//目标的地址
					SocketAddress destAddr = new InetSocketAddress(destIP, destPort);
					sender = new DatagramSocket();
					byte[] buff = out.toByteArray();
					DatagramPacket packet = new DatagramPacket(buff, buff.length, destAddr);
					
					sender.send(packet);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				g.drawImage(inig, 50, 50, null);
			}
			webcam.close();
			return;
			
		};
	};
	//接收视频
	@SuppressWarnings("resource")
	Thread  getCamReceiver = new Thread() {
		public void run() {
			try {
				SocketAddress srcAddr = new InetSocketAddress(srcIP, srcPort);
				receiver = new DatagramSocket(srcAddr);
				
				
				while(isReceving) {
					byte[] buff = new byte[receiver.getReceiveBufferSize()];
					DatagramPacket packet = new DatagramPacket(buff, buff.length);
					
					if(receiver.isClosed()) return;
					receiver.receive(packet);
					
					ByteArrayInputStream in = new ByteArrayInputStream(buff);
					if(in.available() == 0) return;
					BufferedImage img_get = ImageIO.read(in);
					
					configFace(img_get);
					g.drawImage(img_get, sx, sy, null);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	public void startReceive() {
		isReceving = true;
		this.getCamReceiver.start();
		return;
	}
	public void startSend() {
		isSending = true;
		this.getCamSend.start();
		return;
	}
	public void stopAll() {
		if(sender != null) sender.close();
		if(receiver != null) receiver.close();
		isReceving = isSending = false;
		return;
	}
	
	//bufferedimage 百叶特效
	public static BufferedImage mod_Baiye(BufferedImage bg) {
		int width = bg.getWidth(), height = bg.getHeight();
		//建立新的buffer来进行处理
		BufferedImage newg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics tmpg = newg.getGraphics();	//获取图像的画布
		
		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; y += 3) {
				int rgb = bg.getRGB(x, y); //获取源图像素点pixel
				Color color = new Color(rgb);
				
				tmpg.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
				tmpg.drawLine(x, y, x, y); //绘点
			}
		}
		
		return newg; 
	}
	
	public static void configFace(BufferedImage buffg) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Graphics bg = buffg.getGraphics();
		
		CascadeClassifier faceDetector = new CascadeClassifier();
		faceDetector.load("opencv/lbpcascades/lbpcascade_frontalface.xml");
		Mat mat = ImageFilters.bufImg2Mat(buffg);
		
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(mat, faceDetections);

		Rect[] rects = faceDetections.toArray();
		for (Rect rect : rects) {
		//绘制矩形
			bg.setColor(Color.BLACK);
			bg.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
		return;
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		g = frame.getGraphics();
		
		
		VideoCam camer = new VideoCam();
//		camer.startReceive();
		camer.startSend();
		return;
	}

}
