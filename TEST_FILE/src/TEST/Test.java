package TEST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Test {

	// ������Ϣ
		public static void main(String[] args) {
			String ip = "192.168.31.153";        // ���÷��͵�ַ�Ͷ˿ں�
			int port = 8000;
			Socket socket = null;
			try {
				// ���ӷ�����
				socket = new Socket(ip, port);
				// ��ȡ������
				InputStream in = socket.getInputStream();   //��ȡ����
				// ��ȡ�����
				OutputStream out = socket.getOutputStream(); // ��������
				// ��װ�����������������װһ�¿���ֱ�Ӵ����ַ���������װ�Ļ�ֱ��ʹ��InputStream��OutputStreamֻ��ֱ�Ӵ���byte[]��������
				BufferedReader inRead = new BufferedReader(new InputStreamReader(in));
//				PrintWriter outWriter = new PrintWriter(out);
				String info = "neutral";
				byte[] buff = info.getBytes();
				// ��������
				out.write(buff);
			
				// ����Ӧ��
				String result = inRead.readLine();  // ʹ���˰�װ��������������ȡ��Ϣ
				System.out.println(result);
			} catch(UnknownHostException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

}
