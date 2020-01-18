package Plane;

import java.awt.Dimension;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import javax.swing.JFrame;

public class VideoReceiver {
	public String src_ip = "0.0.0.0";
	public int src_port = 11111;
	public JFrame jf;
	
	public void _ini_() {
		jf = new JFrame();
		jf.setSize(new Dimension(800, 600));
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jf.setVisible(true);
	}
	
	public PipedOutputStream pos;
	public FFmpegDecoder decoder;
	public Thread decodeThread;
	public boolean closed = false;
	
	Thread videoThread = new Thread() {
			public void run(){
				DatagramSocket videoSocket = null;
				try {
					videoSocket = new DatagramSocket(src_port);
				} catch (SocketException e1) {
					e1.printStackTrace();
				}
				while(!Thread.interrupted() && !closed) {
					try {
						//一直接收数据
						byte[] recvBytes = new byte[64*1024];
						//接收数据包的对象
						DatagramPacket packet = new DatagramPacket(recvBytes, recvBytes.length);
						
						videoSocket.receive(packet);
						
						//从数据包中取出数据
						byte[] bytes = packet.getData();
						
						int index = bytes.length-1;
						
						//去掉后面多余的0
						for(int i=bytes.length-1; i>=0; i--) {
							if(bytes[i] != 0) {
								index = i;
								break;
							}
						}
						
						if(pos == null) {
							pos = new PipedOutputStream();
							PipedInputStream pis = new PipedInputStream(pos);
							decoder = new FFmpegDecoder(pis);
							decodeThread = new Thread(decoder);
							decodeThread.start();
						}
						pos.write(bytes, 0, index+1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		//画视频到界面线程
		Thread drawThread = new Thread() {
			public void run() {
				while(!Thread.interrupted() && !closed) {
					if(decoder != null && decoder.getBufferedImage() != null) {
						jf.getGraphics().drawImage(decoder.getBufferedImage(), 0, 0, null);
					}
					Thread.yield();
				}
			}
		};
	
	public static void main(String[] args) {
		VideoReceiver receiv = new VideoReceiver();
		receiv._ini_();
		receiv.videoThread.start();
		receiv.drawThread.start();
		return;
	}
}
