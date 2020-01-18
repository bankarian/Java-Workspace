package uiDeveloper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class Mac_style {

	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);

		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	
	public static void set_style(Font font) {
		InitGlobalFont(font);

		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
//			BeautyEyeLNFHelper.translucencyAtFrameInactive = true;
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("set skin fail!");

		}
		return;
	}
	
	public static void main(String[] args) {
		
		Mac_style.set_style(new Font("����", Font.PLAIN, 16));
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(600, 800));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFont(new Font("����", Font.PLAIN, 16));
		
		//���ж��䣬 ���֮����10������
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		JLabel label = new JLabel("Name:");
		label.setFont(new Font("����", Font.PLAIN, 14));
		frame.add(label);
		
		JTextField textf = new JTextField();
		textf.setPreferredSize(new Dimension(300, 30));
		frame.add(textf);
		
		// frame�Ŀɼ�Ҫ����������ӽ���֮��
		// ��Ҫʹ��frame��graphics��Ҫ��frame�ɼ�֮��
		frame.setVisible(true);
		return;
	}
}
