package UDP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.baidu.translate.demo.TransApi;

@SuppressWarnings("serial")
public class Translater extends JComboBox<String> {
	
    private static final String APP_ID = "20190725000321349";
    private static final String SECURITY_KEY = "ujEpVKLXEgb1Dz4zb4Kn";
    public HashMap<String, String> hashmap;
    public String orgin = "auto";
    public static String preI = "-";
    
    public Translater(int x, int y, int width, int height) {
    	String[] to = {
    		"zh", "en", "yue", "wyw", "jp", "kor", "fra",
    		"spa", "th", "ara", "ru", "pt", "de", "it", "el", "nl", "pl"
    	};
    	String[] order = {
    		"中文", "英文", "粤语", "文言文","日语", "韩语", "法语",
    		"西班牙语", "̩泰语", "阿拉伯语", "俄语", "葡萄牙语", "德语", "意大利语", 
    		"希腊语", "荷兰语", "波兰语"
    	};
    	hashmap = new HashMap<String, String>();
    	
    	this.setBounds(new Rectangle(x, y, width, height));
    	for(int i=0; i<order.length; ++i) {
    		this.addItem(order[i]);
    		hashmap.put(order[i], to[i]);
    	}
    	return;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	System.out.println(this.getSelectedIndex());
    	System.out.println(this.getSelectedItem());
    	return;
    }
    
    public static String Trans(String query, String from, String to) {
    	TransApi api = new TransApi(APP_ID, SECURITY_KEY);
    
    	return api.getTransResult(query, from, to);
    }
    
    public static void main(String[] args) {
//    	String str = Trans("����˧������", "auto", "en");
//    	System.out.println(str);
    	
    	JFrame jf = new JFrame();
    	jf.setSize(new Dimension(400, 600));
    	jf.setLocationRelativeTo(null);
    	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	jf.setBackground(Color.GREEN);
    	jf.setLayout(null);
    	Translater tran = new Translater(0, 0, 70, 20);
    	jf.add(tran);
    	
    	JTextField jt = new JTextField();
    	jt.setBounds(0, 100, 100, 30);
    	jf.add(jt);
    	
    	JButton btn = new JButton();
    	btn.setBounds(0, 200, 30, 30);
    	btn.setActionCommand("aaa");
    	
    	btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("aaa")) { System.out.println("����");
					String to = (String) tran.getSelectedItem();
					if(to == preI) return;
					else preI = to;
					to = tran.hashmap.get(to);
					String query = jt.getText();
					if(query.length() > 0) {
						query = Trans(query, "auto", to);
						jt.setText(query);
					}
				}
				return;
			}
		});
    	jf.add(btn);
    	
    	jf.setVisible(true);
    	
    	
    	return;
    }
}
