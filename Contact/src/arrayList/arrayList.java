package arrayList;
/**
 * �Զ����������
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
	//ĩβ���ӵ���Ԫ��
	public void add(DEF stuff) {
		if(iter < iniLen) {
			this.arr[iter++] = stuff;
			return;
		}
		//�����¿���һ�����飬�ٿ�����ȥ
		Object[] tmpArr = new Object[this.arr.length + incLen];
		tmpArr[iter++] = stuff;
		System.arraycopy(arr, 0, tmpArr, 0, this.arr.length);
		
		this.arr = tmpArr;
		return;
	}
	//ĩβ����һ������
	public void add(DEF[] stuff) {
		int stuffLen = stuff.length;
		int needLen = 0;
		//����ֱ���㹻Ϊֹ
		while(this.arr.length + needLen < stuffLen + iter) {
			needLen += incLen;
		}
		if(needLen == 0) {//����Ҫ����
			for(int i=0; i<stuff.length; ++i) {
				this.arr[iter++] = stuff[i];
			}
			return;
		}
		Object[] tmpArr = new Object[this.arr.length + needLen];
		System.arraycopy(arr, 0, tmpArr, 0, this.iter);	//������ֵ�ĳ���
		for(int i=0; i<stuff.length; ++i) {
			tmpArr[iter++] = stuff[i];
		}
		
		this.arr = tmpArr;
		return;
	}
	//��indx֮ǰ����һ��Ԫ��
	public void insert(int indx, DEF obj) {
		//��������
		if(iter < this.arr.length){
			Object[] tmpArr = new Object[this.arr.length];
			System.arraycopy(arr, 0, tmpArr, 0, indx); //��ȡindx֮ǰ��ֵ����indxλ����indx+1�������Գ���ȡindx
			tmpArr[indx] = obj;
			System.arraycopy(arr, indx, tmpArr, indx + 1, this.iter - indx);//֮ǰ�Ѿ�ȡ��ԭ�����indx��Ԫ�أ�ȡʣ�µĸ���(ԭ��ֻ�����iter��Ԫ��)
			iter += 1;
			
			this.arr = tmpArr;
			return;
		}
		//���ݺ��ٲ���
		Object[] tmpArr = new Object[this.arr.length + incLen];
		System.arraycopy(arr, 0, tmpArr, 0, indx);
		tmpArr[indx] = obj;
		System.arraycopy(arr, indx, tmpArr, indx + 1, this.iter - indx);
		iter += 1;
		
		this.arr = tmpArr;
		return;
	}
	//��indx֮ǰ����һ��Ԫ��
	public void insert(int indx, DEF[] obj) {
		int needLen = 0;
		while(needLen + this.arr.length < obj.length + iter) {
			needLen += incLen;
		}
		
		Object[] tmpArr = new Object[this.arr.length + needLen];
		System.arraycopy(arr, 0, tmpArr, 0, indx); //0-indx-1�ȴ洢����
		
		int tmpIter = this.iter;
		for(int i=0; i<obj.length; ++i) {
			tmpArr[indx + i] = obj[i];
			this.iter += 1;//��¼���ӵ�ǰ�Ĵ洢����
		}
		
		System.arraycopy(arr, indx, tmpArr, iter, this.arr.length - indx);	//arr�ĺ����ֵ���ֿ�������
		arr = tmpArr;
		return;

	}
	//ɾ��ĳ��λ�õ�Ԫ��
	public void remove(int indx) {
		if(indx >= this.arr.length) {
			System.out.println("Removing Exception !");
			return;
		}
		System.arraycopy(this.arr, indx + 1, this.arr, indx, this.iter - 1 - indx);
		this.iter -= 1;
		return;
	}
	//ɾ��ĳ��λ�ö��Ԫ��
	public void remove(int indx, int cnt) {
		if(indx + cnt >= this.arr.length){
			System.out.println("Removing Exception !");
			return;
		}
		//����ߵ���ǰ����
		System.arraycopy(this.arr, indx + cnt, this.arr, indx, this.iter - cnt - indx);
		iter -= cnt;
		return;
	}
	//��λ�ò���Ԫ��,���Ҳ�������null
	public Object find(int indx) {
		if(indx >= iter)
			return null;
		return this.arr[indx];
	}
	//��Ԫ�ز��ҵ�һ�γ��ֵ�λ��,���Ҳ�������-1
	public int findIndx(DEF obj) {
		for(int i=0; i<iter; ++i) {
			System.out.println("finding " + obj + ", we find " + this.arr[i]);
			if(this.arr[i].equals(obj))//java�еıȽ���equal
				return i;
		}
		return -1;
	}
	//���ص�ǰ�洢��Ԫ�صĶ���
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
