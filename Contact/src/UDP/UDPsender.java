package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class UDPsender {
	@SuppressWarnings("resource")
	
	public static void main(String[] args){
		try {
			java.net.DatagramSocket socket = new DatagramSocket();
			System.out.println("����");
			//�Է��ĵ�ַ
			SocketAddress Dest_address = new InetSocketAddress("192.168.31.153", 8000); //�����ȷ��͸����Լ�
			String str = "hello there~";
			byte[] buff = str.getBytes();
			java.net.DatagramPacket packet = new DatagramPacket(buff, buff.length, Dest_address);
			socket.send(packet);
			System.out.println("���ͳɹ�");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
