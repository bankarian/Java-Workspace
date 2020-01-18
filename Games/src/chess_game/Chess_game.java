package chess_game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Chess_game extends JFrame{
	
	public Chess_game chess_game;
	private static final long serialVersionUID = 1L;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int screenWIDTH = (int)screenSize.getWidth(), screenHEIGHT = (int)screenSize.getHeight();
	
	public static final int grid_size = 49, board_size = 19, chess_size = 40, chess_R = 20;
	public static final int board_sx = 150, board_sy = 22;
	public int[][] Board_chess = new int[board_size][board_size];	//0--no chess, 1--white, 2--black
	public static final int none = 0, white = 1, black = 2;
	Color[] chessColorW = new Color[]{Color.WHITE, Color.BLACK};
	Color[] chessColorB = new Color[]{Color.DARK_GRAY, Color.BLACK};
	Color[] chessColorF = new Color[]{Color.GREEN, Color.GRAY};
	float[] fraction_W = new float[]{0.1f, 1f};
	float[] fraction_B = new float[]{0f, 0.2f};
	float[] fraction_F = new float[]{0f, 0.5f};		
	public boolean is_white = true, startOnce = true, regretW = false, regretB = false;
	public boolean gameOver = true;	//to Begin or End the game
	public int preX, preY;    	//用于悔棋的辅助变量
	public int preAIX, preAIY;
	
	public static final float LineStroke = (float) 1.488;
	public final String backGroundAddress = "res/back_pic2.jpg";
	public final String whiteAddress = "res/resultW.jpg";
	public final String blackAddress = "res/resultB.jpg";
	public final String musicAddress = "res/backMusic.wav";
	public final ImageIcon curBlack = new ImageIcon("res/bchess_cur.png"), curWhite = new ImageIcon("res/wchess_cur.png");
	public final int IconWidth = 100, IconHeight = 100;

	public final String blackResult = "Black Win!", whiteResult = "White Win!";
	public static final Dimension buttonSize = new Dimension(105, 50);
	public AudioClip music_player;
	public chess_AI ai_player;
	public boolean play_with_ai = false;
	
	//初始化棋盘的信息
	public void InitialBoard(){
		for(int i=0;i<board_size;++i)
			for(int j=0;j<board_size;++j)
				Board_chess[i][j] = none;
		is_white = true;	gameOver = true;
		regretB = regretW = false;
		play_with_ai = false;
		return;
	}
	//绘制当前棋子颜色的提示标签
	public void paintLabel(Graphics g){
		if(is_white) g.drawImage(curWhite.getImage(), 1200, 0, IconWidth, IconHeight, null);
		else g.drawImage(curBlack.getImage(), 1200, 0, IconWidth, IconHeight, null);
		return;
	}
	//画白棋
	public void drawW(float curX, float curY, Graphics2D main_g, int tmpX, int tmpY){
		RadialGradientPaint chessPaint = new RadialGradientPaint(curX-4f, curY-5f, (float)chess_size*2f, fraction_W, chessColorW);
		main_g.setPaint(chessPaint);
		curX -= chess_R;	curY -= chess_R;
		main_g.fillOval((int)curX, (int)curY, chess_size, chess_size);
		
		Board_chess[tmpX][tmpY] = white;
		if(!play_with_ai) is_white = false;
		return;
	}
	//画黑棋
	public void drawB(float curX, float curY, Graphics2D main_g, int tmpX, int tmpY){
		RadialGradientPaint chessPaint = new RadialGradientPaint(curX-4f, curY-5f, (float)chess_size*2f, fraction_B, chessColorB);
		main_g.setPaint(chessPaint);
		curX -= chess_R;	curY -= chess_R;
		main_g.fillOval((int)curX, (int)curY, chess_size, chess_size);
		
		Board_chess[tmpX][tmpY] = black;
		is_white = true;
		return;
	}
	//显示结果的弹窗
	public void showResult(int Result, JPanel mainPanel){
		gameOver = true;
		ImageIcon resultIcon;
		if(Result == black){
			resultIcon = new ImageIcon(blackAddress);
			JOptionPane.showConfirmDialog(mainPanel, "", "Black Win!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, resultIcon);
			System.out.println("black win!");
		}
		else{
			resultIcon = new ImageIcon(whiteAddress);
			JOptionPane.showConfirmDialog(mainPanel, "", "White Win!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, resultIcon);
			System.out.println("white win!");
		}return;
	}
	//显示开始游戏的弹窗
	public void showStart_Inter(JPanel mainPanel){
		ImageIcon startIcon = new ImageIcon("res/start.jpg");
		JOptionPane.showConfirmDialog(mainPanel, "", "Hint", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, startIcon);
		return;
	}
	//显示与人机对战的提示
	public void show_play_with_AI(JPanel mainPanel){
		ImageIcon showIcon = new ImageIcon("res/ai_show.png");
		JOptionPane.showConfirmDialog(mainPanel, "Play With The AI~", "Hint", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, showIcon);
		return;
	}
	//显示悔棋的提示
	public void show_regret(JPanel mainPanel){
		ImageIcon showIcon = new ImageIcon("res/regret.png");
		if(is_white && !regretB){//当前要下的是白子，则悔棋的是黑子
			JOptionPane.showConfirmDialog(mainPanel, "", "The Only Time Is Used, Good Luck!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, showIcon);
		}
		else if(!is_white && !regretW){//当前要下的是黑子，则悔棋的是白子
			JOptionPane.showConfirmDialog(mainPanel, "", "The Only Time Is Used, Good Luck!", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, showIcon);
		}
		else{
			showIcon = new ImageIcon("res/sorry.png");
			JOptionPane.showConfirmDialog(mainPanel, "", "Sorry, You Have Already Taken Back A Move~", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, showIcon);
		}
		
		return;
	}
	public Chess_game()
	{
		this.setSize(screenSize);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		InitialBoard();
		this.MusicControl();
		
		/**@PART_I:  create main chess board画出初始的棋盘
		 * chessPanel is for repainting the Board && Chess
		 *loops row paint Columns. loops columns paint Rows.
		 *loops row,columns, check if a chess occurs.
		 */
		class chessPanel extends JPanel
		{
			private static final long serialVersionUID = 1L;
			ImageIcon BackGround = new ImageIcon(backGroundAddress);
			RadialGradientPaint chessPaint;
			
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				int Row_st = board_sy, Row_en = board_sy + (board_size-1)*grid_size;
				int Col_st = board_sx, Col_en = board_sx + (board_size-1)*grid_size;
				
				g.drawImage(BackGround.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(LineStroke, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));  	//设置线宽
				for(int i=0;i<board_size;++i){
					g2d.draw(new Line2D.Double((double)Col_st, (double)board_sy + i*grid_size, (double)Col_en, (double)board_sy + i*grid_size));
					g2d.draw(new Line2D.Double((double)board_sx + i*grid_size, (double)Row_st, (double)board_sx + i*grid_size, (double)Row_en));
				}
				//画棋子
				for(int i=0;i<board_size;++i){
					for(int j=0;j<board_size;++j){
						if(Board_chess[i][j] != none){
							float curX = board_sx+i*grid_size;
							float curY = board_sy+j*grid_size;
							if(Board_chess[i][j] == white){
								chessPaint = new RadialGradientPaint(curX-4f, curY-5f, (float)chess_size*2f, fraction_W, chessColorW);
							}
							else{
								chessPaint = new RadialGradientPaint(curX-4f, curY-5f, (float)chess_size*2f, fraction_B, chessColorB);
							}
							curX -= chess_R;	curY -= chess_R;
							g2d.setPaint(chessPaint);
							g2d.fillOval((int)curX, (int)curY, chess_size, chess_size);
						}
					}
				}
				paintLabel(g);
			}
			chessPanel(){
				this.setLayout(null);
				this.setPreferredSize(new Dimension(screenWIDTH - 200, screenHEIGHT));
				
			}
		}
		
		chessPanel mainPanel = new chessPanel();
		mainPanel.setVisible(true);
		
		/**@note:
		 * 
		 * execute Only when !gameOver
		 * Once clik on the board get the real_chessX, real_chessY
		 * using tmpX && tmpY to judge the Exact Point the chess should occur
		 * the dx > grid/2, really exist on dx+1
		 * the dy > grid/2, really exist on dy+1
		 * before drawing, judge if the position is blank.
		 * then draw the chess on the exact point
		 * then Judge if a winner occurs
		 * @param real_chessX the opaque mouse kick X we get
		 * @param real_chessY the opaque mouse kick Y we get
		 * @param tmpX the maximum integral grid width the real_chessX pass
		 * @param tmpY the maximum integral grid height the real_chessY pass
		 * @param curX the exact X the chess should occur
		 * @param curY the exact Y the chess should occur
		 * @param Result type of chess who wins
		 * @Note_that : x由左往右由0逐渐变大， y由上往下由0之间变大
		 */
		mainPanel.addMouseListener(new MouseAdapter(){
			Graphics2D main_g;	float curX=0, curY=0;
			
			@Override
			public void mouseClicked(MouseEvent e){
				if(gameOver) return;
				main_g = (Graphics2D)mainPanel.getGraphics();
				double real_chessX= (double)e.getX()-(double)board_sx;
				double real_chessY = (double)e.getY()-(double)board_sy;
				
				int tmpX = (int)(real_chessX/grid_size), tmpY = (int)(real_chessY/grid_size);
				
				double dx = real_chessX - (double)tmpX*grid_size;
				double dy = real_chessY - (double)tmpY*grid_size;
				if(dx > grid_size/2) tmpX += 1;
				if(dy > grid_size/2) tmpY += 1;
				if(tmpX >= board_size || tmpY >= board_size) return;
				preX = tmpX;	preY = tmpY;
				curX = board_sx+tmpX*grid_size;
				curY = board_sy+tmpY*grid_size;
				
				if(Board_chess[tmpX][tmpY] != none) return;
				if(is_white){
					drawW(curX, curY, main_g, tmpX, tmpY);
				}
				else{
					drawB(curX, curY, main_g, tmpX, tmpY);
				}
				int Result = JudgeResult();
				if(Result != none)	showResult(Result, mainPanel);

				if(!play_with_ai)
					paintLabel(mainPanel.getGraphics());
				else 
					if(!gameOver){
						ai_player.play_search();
						preAIX = ai_player.mx;
						preAIY = ai_player.my;
						mainPanel.repaint();
						Result = JudgeResult();
						if(Result != none)
							showResult(Result, mainPanel);
					}
			}
		});
		cp.add(mainPanel,BorderLayout.WEST);
		
		/**@PART_II:create choice board
		 * the "Start" button can Only execute once
		 */
		ActionListener ButtonListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String order = e.getActionCommand();
				if(order.equals("Start")){
					if(startOnce){
						gameOver = false;
						startOnce = false;
					}
					showStart_Inter(mainPanel);
				}
				else if(order.equals("Exit")) System.exit(0);
				else if(order.equals("Regret Once")){
					if(gameOver) return;
					show_regret(mainPanel);
					if(is_white){
						if(regretB == true) return;
						else regretB = true;
					}
					else{
						if(regretW == true) return;
						else regretW = true;
					}
					Board_chess[preX][preY] = none;
					if(play_with_ai) Board_chess[preAIX][preAIY] = none;
					
					if(!play_with_ai) is_white = !is_white;
					mainPanel.repaint();
				}
				else if(order.equals("ReStart")){
					InitialBoard();
					mainPanel.repaint();
					gameOver = false;
					showStart_Inter(mainPanel);
				}
				else if(order.equals("stopMusic")){
					music_player.stop();
				}
				else if(order.equals("playMusic")){
					music_player.loop();
				}
				else if(order.equals("aiPlayer")){
					ai_player = new chess_AI(Board_chess, board_size, black);
					play_with_ai = true;
					show_play_with_AI(mainPanel);
				}
				return;
			}
		};
		class choicePanel extends JPanel{
			private static final long serialVersionUID = 1L;
			
			choicePanel(){
				this.setPreferredSize(new Dimension(200, screenHEIGHT));
				this.setBackground(Color.darkGray);
				String[] choice = { "Start", "Regret Once", "ReStart", "aiPlayer", "playMusic", "stopMusic", "Exit"};
				for(int i=0;i<choice.length;++i){
					JButton button = new JButton(choice[i]);
					button.setPreferredSize(buttonSize);
					button.setActionCommand(choice[i]);
					button.addActionListener(ButtonListener);
					this.add(button);
				}
			}
		}
		choicePanel choiceP = new choicePanel();
		choiceP.setVisible(true);
		cp.add(choiceP, BorderLayout.EAST);
		
		this.setVisible(true);		
	}
	
	/**@PART_III: judge if a winner comes out
	 * for each chess, if it can reach another 4 same chess --> the chess win.
	 * note that each case the chess just move towards 1 direction.
	 * @param finalX finalY, the predicted position of the winner's final chess
	 * 		  note that starts from board_sx & board_sy
	 * @return if can win, the recursion will return total value of 4
	 * 		   otherwise, the returned value < 4
	 */
	public int[][] DIR = {{1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
	
	public int Dfs(int chessType, int step, int curX, int curY, int dir, boolean haveFore){
		int nX = curX + DIR[dir][0], nY = curY + DIR[dir][1];
		if((nX>=0&&nX<board_size) && (nY>=0&&nY<board_size))
		{
			if(Board_chess[nX][nY] == none){
//				if(step == 3 && !haveFore){
//					finalX = nX*grid_size + board_sx;	finalY = nY*grid_size + board_sy;
//					return (int)2;
//				}
				return (int)0;
			}
			else if(Board_chess[nX][nY] == chessType){
				if(step == 4) 
					return (int)1;
				return (int)(1 + Dfs(chessType, step+1, nX, nY, dir, haveFore));
			}
			else return (int)0;
		}
		else return (int)0;
		
	}
	/**@note:
	 * cnt == 4--> a chess can reach the other 4 chess, Win!
	 * judge forePosition to predict the result
	 * if forePosition has other type or is the boundary, then must reach 4 chess to win
	 * otherwise reaching 3 chess confirm the victory
	 * @param haveFore true-->the head of the chess line reach boundary or the other chess.
	 * 		  false--> the head reach nothing, means the line consisting of 4 chess can guarantee the victor!
	 */
	public int JudgeResult(){
		int cnt = 0;	boolean haveFore = true;
		int i=0, j=0;
		for(i=0;i<board_size;++i){
			for(j=0;j<board_size;++j){
				if(Board_chess[i][j] != none){
					for(int d=0;d<8;++d){
						int foreX = i-DIR[d][0];
						int foreY = j-DIR[d][1];
						if(foreX < 0 || foreX >= board_size || foreY < 0 || foreY >= board_size)  //reach boundary
							haveFore = true;
						else if(Board_chess[foreX][foreY] == none)   //nothing ahead
							haveFore = false;
						else if(Board_chess[foreX][foreY] != Board_chess[i][j])  //different type
							haveFore = true;
						else
							haveFore = false;   //same type
						cnt = Dfs(Board_chess[i][j], 0, i, j, d, haveFore);
						if(cnt >= 4) break;
					}
					if(cnt >= 4) break;
				}
				else continue;
			}if(cnt >= 4) break;
		}
		if(cnt >= 4) return Board_chess[i][j];
		else return none;
	}
	
	public void MusicControl(){
		try {
			File f = new File(musicAddress);
			URI uri = f.toURI();
			URL url = uri.toURL();
			music_player = Applet.newAudioClip(url);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Sleep(int a){
		try {
			Thread.sleep(a);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		new Chess_game();
	}
}
