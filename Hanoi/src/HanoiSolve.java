//��ŵ��
//�������ӣ�N��Բ�̣�aȫ���Ƶ�c��һ��һ��Բ��
public class HanoiSolve {
	public void Move(int N,char st,char en){
		System.out.println("Move "+N+" from "+st+" to "+en);
	}
	
	public void solve(int N,char a,char b,char c)
	{//��N��Բ��a->b->c��bΪ�н�
		if(N==1){ //���ϲ���У�1�ţ�ֱ��a�ƶ���c
			Move(N,a,c); //��a->c
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
