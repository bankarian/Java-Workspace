package mg_SPONGE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JFrame;

public class DrawSponge extends JFrame{
	int count = 0;
	Point[] pn = new Point[20];
	public static void main(String[] args){
		new DrawSponge().setUI();
	}
	public void setUI(){
		this.setSize(1000,800);
		this.setTitle("MGsponge");
		this.setDefaultCloseOperation(DrawSponge.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public Point[] getPointByP0(Point p0,int d,int dx,int dy) {
        Point p1 = new Point(p0.x+dx,p0.y-dy);
        Point p2 = new Point(p0.x+dx+d,p0.y-dy);
        Point p3 = new Point(p0.x+d,p0.y);
        Point p4 = new Point(p0.x+d,p0.y+d);
        Point p5 = new Point(p0.x,p0.y+d);
        Point p6 = new Point(p0.x+dx+d,p0.y+d-dy);

        Point[] ps = new Point[7];
        ps[0] = p0;
        ps[1] = p1;
        ps[2] = p2;
        ps[3] = p3;
        ps[4] = p4;
        ps[5] = p5;
        ps[6] = p6;
        return ps;
	}
	public void paint(Graphics g) {
        super.paint(g);
        //正方形的顶点坐标
        int x = 100,y = 500;
        int d = 120,dx = 81,dy = 60;

        Point p0 = new Point(x,y); //下
        drawBottomLevel(g,p0,d,dx,dy);
        Point p1 = new Point(x,y-d);//中
        drawMidLevel(g,p1,d,dx,dy);
        Point p2 = new Point(x,y-2*d);//上，逐渐覆盖
        drawTopLevel(g,p2,d,dx,dy);
        for(int i = 0;i<pn.length;i++) {
            Point ptem = pn[i];
            if(ptem!=null) {
//            	try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
                Point ptem0 = new Point(pn[i].x,pn[i].y+2*d/3); 
                drawBottomLevel(g,ptem0,d/3,dx/3,dy/3);

                Point ptem1 = new Point(pn[i].x,pn[i].y+d/3);
                drawMidLevel(g,ptem1,d/3,dx/3,dy/3);

                Point ptem2 = new Point(pn[i].x,pn[i].y);
                drawTopLevel(g,ptem2,d/3,dx/3,dy/3);
            }
        }

    }
	//六个点fill一个正方体
	public void draw(Graphics g,Point p0,Point p1,Point p2,Point p3,Point p4,Point p5,Point p6) {
        if(count<20) {
            pn[count] = p0;
        }
        count++;
        //三原色随机生成
//      int c1 = new java.util.Random().nextInt(255);
//      int c2 = new java.util.Random().nextInt(255);
//      int c3 = new java.util.Random().nextInt(255);

        Graphics2D gD = (Graphics2D) g;

        //填充面的颜色
        Polygon pon1 = new Polygon();
        //填充第一个可见面
        pon1.addPoint(p0.x, p0.y);
        pon1.addPoint(p1.x, p1.y);
        pon1.addPoint(p2.x, p2.y);
        pon1.addPoint(p3.x, p3.y);
        gD.setColor(new Color(230,0,0));
        gD.fillPolygon(pon1);
        //填充第二个可见面
        Polygon pon2 = new Polygon();
        pon2.addPoint(p0.x, p0.y);
        pon2.addPoint(p3.x, p3.y);
        pon2.addPoint(p4.x, p4.y);
        pon2.addPoint(p5.x, p5.y);
        gD.setColor(new Color(255,0,0));
        gD.fillPolygon(pon2);
        //填充第三个可见面
        Polygon pon3 = new Polygon();
        pon3.addPoint(p2.x, p2.y);
        pon3.addPoint(p3.x, p3.y);
        pon3.addPoint(p4.x, p4.y);
        pon3.addPoint(p6.x, p6.y);
        gD.setColor(new Color(200,0,0));
        gD.fillPolygon(pon3);

    }
	
	public void drawBottomLevel(Graphics g, Point p0, int d, int dx, int dy) {
        Point[] ps1 = getPointByP0(p0,d,dx,dy);
        Point[] ps2 = getPointByP0(ps1[3],d,dx,dy);
        Point[] ps3 = getPointByP0(ps2[3],d,dx,dy);
        Point[] ps4 = getPointByP0(ps3[1],d,dx,dy);

        Point[] ps5 = getPointByP0(ps4[1],d,dx,dy);

        Point[] ps6 = getPointByP0(ps1[1],d,dx,dy);
        Point[] ps7 = getPointByP0(ps6[1],d,dx,dy);
        Point[] ps8 = getPointByP0(ps7[3],d,dx,dy);

        draw(g,ps7[0],ps7[1],ps7[2],ps7[3],ps7[4],ps7[5],ps7[6]);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        draw(g,ps8[0],ps8[1],ps8[2],ps8[3],ps8[4],ps8[5],ps8[6]);
        draw(g,ps5[0],ps5[1],ps5[2],ps5[3],ps5[4],ps5[5],ps5[6]);
        draw(g,ps4[0],ps4[1],ps4[2],ps4[3],ps4[4],ps4[5],ps4[6]);
        draw(g,ps6[0],ps6[1],ps6[2],ps6[3],ps6[4],ps6[5],ps6[6]);
        draw(g,ps1[0],ps1[1],ps1[2],ps1[3],ps1[4],ps1[5],ps1[6]);
        draw(g,ps2[0],ps2[1],ps2[2],ps2[3],ps2[4],ps2[5],ps2[6]);
        draw(g,ps3[0],ps3[1],ps3[2],ps3[3],ps3[4],ps3[5],ps3[6]);
	    }
	public void drawMidLevel(Graphics g, Point p0, int d, int dx, int dy) {
        Point[] ps1 = getPointByP0(p0,d,dx,dy);
        Point[] ps2 = getPointByP0(ps1[3],d,dx,dy);
        Point[] ps3 = getPointByP0(ps2[3],d,dx,dy);
        Point[] ps4 = getPointByP0(ps3[1],d,dx,dy);

        Point[] ps5 = getPointByP0(ps4[1],d,dx,dy);

        Point[] ps6 = getPointByP0(ps1[1],d,dx,dy);
        Point[] ps7 = getPointByP0(ps6[1],d,dx,dy);
        Point[] ps8 = getPointByP0(ps7[3],d,dx,dy);
        draw(g,ps7[0],ps7[1],ps7[2],ps7[3],ps7[4],ps7[5],ps7[6]);
        draw(g,ps5[0],ps5[1],ps5[2],ps5[3],ps5[4],ps5[5],ps5[6]);
        draw(g,ps1[0],ps1[1],ps1[2],ps1[3],ps1[4],ps1[5],ps1[6]);
        draw(g,ps3[0],ps3[1],ps3[2],ps3[3],ps3[4],ps3[5],ps3[6]);

	        }
	public void drawTopLevel(Graphics g, Point p0, int d, int dx, int dy) {
        Point[] ps1 = getPointByP0(p0,d,dx,dy);
        Point[] ps2 = getPointByP0(ps1[3],d,dx,dy);
        Point[] ps3 = getPointByP0(ps2[3],d,dx,dy);
        Point[] ps4 = getPointByP0(ps3[1],d,dx,dy);

        Point[] ps5 = getPointByP0(ps4[1],d,dx,dy);

        Point[] ps6 = getPointByP0(ps1[1],d,dx,dy);
        Point[] ps7 = getPointByP0(ps6[1],d,dx,dy);
        Point[] ps8 = getPointByP0(ps7[3],d,dx,dy);

        draw(g,ps7[0],ps7[1],ps7[2],ps7[3],ps7[4],ps7[5],ps7[6]);
        draw(g,ps8[0],ps8[1],ps8[2],ps8[3],ps8[4],ps8[5],ps8[6]);
        draw(g,ps5[0],ps5[1],ps5[2],ps5[3],ps5[4],ps5[5],ps5[6]);
        draw(g,ps4[0],ps4[1],ps4[2],ps4[3],ps4[4],ps4[5],ps4[6]);
        draw(g,ps6[0],ps6[1],ps6[2],ps6[3],ps6[4],ps6[5],ps6[6]);
        draw(g,ps1[0],ps1[1],ps1[2],ps1[3],ps1[4],ps1[5],ps1[6]);
        draw(g,ps2[0],ps2[1],ps2[2],ps2[3],ps2[4],ps2[5],ps2[6]);
        draw(g,ps3[0],ps3[1],ps3[2],ps3[3],ps3[4],ps3[5],ps3[6]);
	    }
}
