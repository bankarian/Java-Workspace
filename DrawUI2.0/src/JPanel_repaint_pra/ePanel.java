package JPanel_repaint_pra;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ePanel extends JPanel{
	eShape[] sharr = new eShape[1000];
	
	public static void main(String[] args){
		new ePanel().showUI();
	}
	public void showUI(){
		JFrame Plate = new JFrame();
		Plate.setSize(1000, 800);
		Plate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Plate.setLocationRelativeTo(null);
		
//		ePanel centreP = new ePanel(); //�൱����ePanel�ﱾ���ٽ�����һ��e'Panel
		//������֮�󴫽�listener��sharr����e'Panel�е�sharr
//		ePanel cenreP = this; //������������Ķ������ePanel  Ҳ����ֱ����д
		JPanel northP = new JPanel();
		northP.setSize(1000,200);
		northP.setBackground(Color.CYAN);
		this.setSize(1000,600);
		Plate.add(northP,BorderLayout.NORTH);
		Plate.add(this,BorderLayout.CENTER);
		
		eListener PenListener = new eListener();
		this.addMouseListener(PenListener);
		
		Plate.setVisible(true);
		PenListener.g = this.getGraphics();
		PenListener.setArr(sharr);
	}
	public void paint(Graphics g){
		super.paint(g);
		for(int i=0;i<sharr.length;++i){
			System.out.println("i=="+i+" sharr[i]== "+ sharr[i]);
			if(sharr[i] == null) 	break;
			sharr[i].Draw(g);
		}
	}
}
