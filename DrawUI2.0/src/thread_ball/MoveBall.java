package thread_ball;

import java.util.ArrayList;

public class MoveBall extends Thread{
	public ArrayList<Ball> list;
	
	public void ini(ArrayList<Ball> list){
		this.list = list;
		return;
	}
	public void run() {
		try {
			sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			for(int i=0; i<list.size(); ++i) {
				list.get(i).draw_ball();
				try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
