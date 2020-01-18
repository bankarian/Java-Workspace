package arrayList;
/**
 * 自定义数组队列
 * @author Bankarian
 */
public class arrayList <DEF>{
	private int iniLen = 1000;
	private int incLen = 1000;
	private int iter = 0;
	public Object[] arr;
	
	public arrayList(){
		arr = new Object[iniLen];
	}
	public arrayList(int cus_iniLen) {
		iniLen = cus_iniLen;
		arr = new Object[iniLen];
	}
	public arrayList(int cus_iniLen, int cus_incLen) {
		iniLen = cus_iniLen;
		incLen = cus_incLen;
		arr = new Object[iniLen];
	}
	//末尾增加单个元素
	public void add(DEF stuff) {
		if(iter < iniLen) {
			this.arr[iter++] = stuff;
			return;
		}
		//拓容新拷贝一个数组，再拷贝回去
		Object[] tmpArr = new Object[this.arr.length + incLen];
		tmpArr[iter++] = stuff;
		System.arraycopy(arr, 0, tmpArr, 0, this.arr.length);
		
		this.arr = tmpArr;
		return;
	}
	//末尾增加一组数据
	public void add(DEF[] stuff) {
		int stuffLen = stuff.length;
		int needLen = 0;
		//拓容直至足够为止
		while(this.arr.length + needLen < stuffLen + iter) {
			needLen += incLen;
		}
		if(needLen == 0) {//不需要拓容
			for(int i=0; i<stuff.length; ++i) {
				this.arr[iter++] = stuff[i];
			}
			return;
		}
		Object[] tmpArr = new Object[this.arr.length + needLen];
		System.arraycopy(arr, 0, tmpArr, 0, this.iter);	//复制有值的长度
		for(int i=0; i<stuff.length; ++i) {
			tmpArr[iter++] = stuff[i];
		}
		
		this.arr = tmpArr;
		return;
	}
	//在indx之前插入一个元素
	public void insert(int indx, DEF obj) {
		//无需拓容
		if(iter < this.arr.length){
			Object[] tmpArr = new Object[this.arr.length];
			System.arraycopy(arr, 0, tmpArr, 0, indx); //先取indx之前的值，到indx位置有indx+1个，所以长度取indx
			tmpArr[indx] = obj;
			System.arraycopy(arr, indx, tmpArr, indx + 1, this.iter - indx);//之前已经取了原数组的indx个元素，取剩下的个数(原本只填充了iter个元素)
			iter += 1;
			
			this.arr = tmpArr;
			return;
		}
		//拓容后再插入
		Object[] tmpArr = new Object[this.arr.length + incLen];
		System.arraycopy(arr, 0, tmpArr, 0, indx);
		tmpArr[indx] = obj;
		System.arraycopy(arr, indx, tmpArr, indx + 1, this.iter - indx);
		iter += 1;
		
		this.arr = tmpArr;
		return;
	}
	//在indx之前插入一组元素
	public void insert(int indx, DEF[] obj) {
		int needLen = 0;
		while(needLen + this.arr.length < obj.length + iter) {
			needLen += incLen;
		}
		
		Object[] tmpArr = new Object[this.arr.length + needLen];
		System.arraycopy(arr, 0, tmpArr, 0, indx); //0-indx-1先存储过来
		
		int tmpIter = this.iter;
		for(int i=0; i<obj.length; ++i) {
			tmpArr[indx + i] = obj[i];
			this.iter += 1;//记录增加当前的存储数量
		}
		
		System.arraycopy(arr, indx, tmpArr, iter, this.arr.length - indx);	//arr的后半有值部分拷贝过来
		arr = tmpArr;
		return;

	}
	//删除某个位置的元素
	public void remove(int indx) {
		if(indx >= this.arr.length) {
			System.out.println("Removing Exception !");
			return;
		}
		System.arraycopy(this.arr, indx + 1, this.arr, indx, this.iter - 1 - indx);
		this.iter -= 1;
		return;
	}
	//删除某个位置多个元素
	public void remove(int indx, int cnt) {
		if(indx + cnt >= this.arr.length){
			System.out.println("Removing Exception !");
			return;
		}
		//将后边的往前覆盖
		System.arraycopy(this.arr, indx + cnt, this.arr, indx, this.iter - cnt - indx);
		iter -= cnt;
		return;
	}
	//按位置查找元素,查找不到返回null
	public Object find(int indx) {
		if(indx >= iter)
			return null;
		return this.arr[indx];
	}
	//按元素查找第一次出现的位置,查找不到返回-1
	public int findIndx(DEF obj) {
		for(int i=0; i<iter; ++i) {
			System.out.println("finding " + obj + ", we find " + this.arr[i]);
			if(this.arr[i].equals(obj))//java中的比较用equal
				return i;
		}
		return -1;
	}
	//返回当前存储的元素的多少
	public int size() {
		return iter;
	}
	
	public static void main(String[] args) {
		arrayList<String> list = new arrayList<String>(1, 4);
		
		String[] str = new String [12];
		
		for(int i=0; i<str.length; ++i) {
			str[i] = "qiqife" + i;
		}
		System.out.println("Initial:");
		list.insert(0, str);
		for(int i=0; i<list.size(); ++i) {
			System.out.println("No" + i + " " + list.arr[i]);
		}
		
		list.remove(0, 2);
		System.out.println("");
		System.out.println("after removing:");
		for(int i=0; i<list.size(); ++i) {
			System.out.println("No" + i + " " + list.arr[i]);
		}
		System.out.println("list size == " + list.size());
		System.out.println("find(1)" + list.find(1));
		System.out.println("findIndx(qiqife6) at " + list.findIndx("qiqife6"));
		
//		java.util.ArrayList<>
	}
}
