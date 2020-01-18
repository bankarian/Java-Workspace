//汉诺塔
//三哥柱子，N个圆盘，a全部移到c，一次一根圆盘
public class HanoiSolve {
	public void Move(int N,char st,char en){
		System.out.println("Move "+N+" from "+st+" to "+en);
	}
	
	public void solve(int N,char a,char b,char c)
	{//将N个圆盘a->b->c，b为中介
		if(N==1){ //最上层的行，1号，直接a移动到c
			Move(N,a,c); //从a->c
			return;
		}
		else{
			solve(N-1,a,c,b);
			Move(N,a,c);
			solve(N-1,b,a,c);
		}
	}
	
	public static void main(String[] args){
		HanoiSolve hh = new HanoiSolve();
		hh.solve(4, 'A', 'B', 'C');
	}

}
