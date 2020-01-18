package chess_game;

import java.util.HashMap;
/**
 * 人机对战
 * @author Bankarian
 */
public class chess_AI {
	int[][] Dir = { {1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {1,-1}, {-1,1}, {-1,-1} };
	HashMap<String, Integer> Judge = new HashMap<String, Integer>();
	public static final char diff = '1', same = '2';
	public static int ai_chess;
	public static final int none = 0, white = 1, black = 2;
	public static int maxValue, mx, my;
	public static int board_size;
	public int[][] aiBoard;
	
	public chess_AI(int[][] Board, int Size, int color){
		aiBoard = Board;	//取地址
		board_size = Size;
		ai_chess = color;
		setValue();
	}
	
	// 当前为我方下--优先攻
	//"2"我方， "1"异方
	public void setValue(){
		//攻
		Judge.put("2222", (int) 1e7);	Judge.put("22221", (int) 1e7);
		Judge.put("222", (int) 1e6 * 4);	Judge.put("2221", (int) 1e5 * 8);
		Judge.put("22", (int) 1e3 * 3);		Judge.put("221", (int) 1e2 * 6);
		Judge.put("2", (int) 1e2 * 2);		Judge.put("21", (int) 40);
		//防
		Judge.put("1111", (int) 1e6);		Judge.put("11112", (int) 1e6 * 6);
		Judge.put("111", (int) 1e6 * 3);	Judge.put("1112", (int) 1e5);
		Judge.put("11", (int) 1e2 * 6);			Judge.put("112", (int) 1e2 * 3);
		Judge.put("1", (int) 1e2);			Judge.put("12", (int) 20);
		return;
	}
	

	//每一空点拓展得到权值，桶排得到最大的权值位置，取该位置为下子处
	public void play_search(){
		maxValue = mx = my = 0;
		for(int r = 0; r < board_size; ++r){
			for(int c = 0; c < board_size; ++c){
				if(aiBoard[r][c] != none) continue;
				int tmpValue = expand(r, c);
//				System.out.println("get value on" + r + "," + c + ", " + tmpValue);
				if(maxValue < tmpValue){
					maxValue = tmpValue;
					mx = r;		my = c;
				}
			}
		}
//		System.out.println("final value = " + maxValue + " on " + mx + "," + my);
		aiBoard[mx][my] = ai_chess;
		return;
	}
	//通过key值在Judge中获取对应的权值
	public int get_value(String state){
		if(state.length() == 0)	return 0;
		else{
			if(Judge.get(state) == null) return 0;
			else return Judge.get(state);
		}
	}
	/**@note：
	 * 对xy位置进行八个方向的拓展获得其权值
	 * while拓展跳出有三种可能：越界，异色、空。
	 * 异色的情况在while中已经进行了处理，空无需处理,越界要特判处理一下,看作是有一个异色的棋子阻挡
	 * 注意每次拓展新的方向时都要用一个新的state来记录
	 * 遇到非空的异色棋子，记录一个后跳出
	 */
	public int expand(int x, int y){
		int nx, ny, pre;
		int tmpValue = 0;
		for(int d = 0; d < 8; ++d)//八向
		{
			String state = "";	
			nx = x + Dir[d][0];
			ny = y + Dir[d][1];
			if(nx <0 || nx >= board_size || ny < 0 || ny >= board_size) continue;
			pre = aiBoard[nx][ny];
			while((nx>=0 && nx<board_size) && (ny>=0 && ny<board_size) && aiBoard[nx][ny] != none)
			{
				if(pre == aiBoard[nx][ny]){
					if(aiBoard[nx][ny] == ai_chess) state += same;
					else state += diff;
				}
				if(pre != aiBoard[nx][ny]){//出现一个不同，记录完之后就跳出
					if(aiBoard[nx][ny] == ai_chess) state += same;
					else state += diff;
					break;
				}
				pre = aiBoard[nx][ny];	//记录上一个棋子
				nx += Dir[d][0];
				ny += Dir[d][1];
			}
			if(nx < 0 || nx >= board_size || ny < 0 || ny >= board_size){//越界的情况要特殊处理
				if(state.charAt(state.length() - 1) == diff) state += same;
				else state += diff;
			}
			tmpValue += get_value(state);
		}
		return tmpValue;
	}
}
