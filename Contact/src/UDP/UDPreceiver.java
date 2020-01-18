package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPreceiver {
	
	public static void main(String[] args){	
		try {
			System.out.print("waiting...");
			java.net.InetSocketAddress re_address = new InetSocketAddress("192.168.31.153", 8000);
			@SuppressWarnings("resource")
			java.net.DatagramSocket receiver = new DatagramSocket(re_address);
			
			while(true){
				byte[] buff = new byte[receiver.getReceiveBufferSize()];
				java.net.DatagramPacket packet = new DatagramPacket(buff, buff.length);
				receiver.receive(packet);
				
				String str = new String(buff,0,buff.length);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
