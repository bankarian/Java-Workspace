package Plane;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CommandSender implements ActionListener{
	public static String dest_ip = "192.168.10.1";
	public static int dest_port = 8889;
	public static String base = "command";
	
	public static void UDP_send(String order) {
		try {
			
			DatagramSocket socket = new DatagramSocket();
			SocketAddress dest_address = new InetSocketAddress(dest_ip, dest_port);
			
			byte[] buff = order.getBytes();
			DatagramPacket packet = new DatagramPacket(buff, buff.length, dest_address);
			socket.send(packet);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public static void main(String[] args) {

		JFrame jf = new JFrame();
		jf.setSize(new Dimension(800, 500));
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(new FlowLayout());
		
		String[] order = {base, "takeoff", "land", "streamon", "streamoff"};
		for(int i=0; i<order.length; ++i) {
			JButton btn = new JButton(order[i]);
			btn.setActionCommand(order[i]);
			btn.setPreferredSize(new Dimension(100, 80));
			btn.addActionListener(new CommandSender());
			jf.add(btn);
		}
		
		jf.setVisible(true);
		return;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UDP_send(e.getActionCommand());
		return;
	}
}
