package linkedList;

public class LLinkedList {

	class Node {
		Object data;
		Node next;
		Node() {}
		Node(Object a) {
			this.data = a;
		}
	}
	public static Node root, last;
	public static int size;
	
	public void add(Object stuff) {
		Node tmp = new Node();
		tmp.data = stuff;
		if(root == null) {
			root = tmp;
			last = root;
			size += 1;
		} else {
			last.next = tmp;
			last = tmp;
			size += 1;
		}
		return;
	}
	//在index之前插入
	///fajioja gi warhiweanhwnqo
	public void insert(int index, Object stuff) {
		if(index == 0) {
			Node tmp = new Node(stuff);
			tmp.next = root;
			root = tmp;
		} else {
			int iter = 1;
			Node tmp1 = root.next;

			if(iter != index) {
				System.out.println("fail to insert");
				return;
			}
			
		}
		
		size += 1;
		return;
	}
	//查找index位置的元素
	public Object find(int index) {
		int iter = 0;
		last = root;
		while(iter < index && last != null) {
			last = last.next;
			iter += 1;
		}
		return last.data;
	}
	public int size() {
		return size;
	}
	
	public static void main(String[] args) {
		LLinkedList list = new LLinkedList();
		String str = "aaa";
		for (int i=0; i<5; ++i) {
			list.add(str + i);
		}
		System.out.println((String) root.data); 
//		LinkedList
//		list.insert(2, "bbb");
		for (int i=0; i<5; ++i)
			System.out.println(list.find(4));
		return;
	}
}
