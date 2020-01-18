package chess_game;

import java.util.HashMap;
/**
 * �˻���ս
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
		aiBoard = Board;	//ȡ��ַ
		board_size = Size;
		ai_chess = color;
		setValue();
	}
	
	// ��ǰΪ�ҷ���--���ȹ�
	//"2"�ҷ��� "1"�췽
	public void setValue(){
		//��
		Judge.put("2222", (int) 1e7);	Judge.put("22221", (int) 1e7);
		Judge.put("222", (int) 1e6 * 4);	Judge.put("2221", (int) 1e5 * 8);
		Judge.put("22", (int) 1e3 * 3);		Judge.put("221", (int) 1e2 * 6);
		Judge.put("2", (int) 1e2 * 2);		Judge.put("21", (int) 40);
		//��
		Judge.put("1111", (int) 1e6);		Judge.put("11112", (int) 1e6 * 6);
		Judge.put("111", (int) 1e6 * 3);	Judge.put("1112", (int) 1e5);
		Judge.put("11", (int) 1e2 * 6);			Judge.put("112", (int) 1e2 * 3);
		Judge.put("1", (int) 1e2);			Judge.put("12", (int) 20);
		return;
	}
	

	//ÿһ�յ���չ�õ�Ȩֵ��Ͱ�ŵõ�����Ȩֵλ�ã�ȡ��λ��Ϊ���Ӵ�
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
	//ͨ��keyֵ��Judge�л�ȡ��Ӧ��Ȩֵ
	public int get_value(String state){
		if(state.length() == 0)	return 0;
		else{
			if(Judge.get(state) == null) return 0;
			else return Judge.get(state);
		}
	}
	/**@note��
	 * ��xyλ�ý��а˸��������չ�����Ȩֵ
	 * while��չ���������ֿ��ܣ�Խ�磬��ɫ���ա�
	 * ��ɫ�������while���Ѿ������˴��������账��,Խ��Ҫ���д���һ��,��������һ����ɫ�������赲
	 * ע��ÿ����չ�µķ���ʱ��Ҫ��һ���µ�state����¼
	 * �����ǿյ���ɫ���ӣ���¼һ��������
	 */
	public int expand(int x, int y){
		int nx, ny, pre;
		int tmpValue = 0;
		for(int d = 0; d < 8; ++d)//����
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
				if(pre != aiBoard[nx][ny]){//����һ����ͬ����¼��֮�������
					if(aiBoard[nx][ny] == ai_chess) state += same;
					else state += diff;
					break;
				}
				pre = aiBoard[nx][ny];	//��¼��һ������
				nx += Dir[d][0];
				ny += Dir[d][1];
			}
			if(nx < 0 || nx >= board_size || ny < 0 || ny >= board_size){//Խ������Ҫ���⴦��
				if(state.charAt(state.length() - 1) == diff) state += same;
				else state += diff;
			}
			tmpValue += get_value(state);
		}
		return tmpValue;
	}
}
