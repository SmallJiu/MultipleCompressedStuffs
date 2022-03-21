package cat.jiu.mcs;

import java.awt.Container;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import cat.jiu.core.util.system.file.JsonWriter;

public class MCS extends MultipleCompressedStuffs {
	public static void main(String[] args) {
		JFrame f = new JFrame("Utils");
		f.setBounds(0, 0, 160, 170);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);
		
		Container c = f.getContentPane();
		
		JButton strCase = new JButton("ToSomeCase");
		strCase.setBounds(10, 10, 150, 30);
		strCase.addActionListener(event -> {
			f.dispose();
			ToCase.main(args);
		});
		
		JButton model = new JButton("Model Write");
		model.setBounds(10, 50, 150, 30);
		model.addActionListener(event -> {
			f.dispose();
			try {
				ModelWriter.main(args);
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
		
		JButton jsonUtil = new JButton("Json Util");
		jsonUtil.setBounds(10, 90, 150, 30);
		jsonUtil.addActionListener(event -> {
			f.dispose();
			JsonWriter.main(args);
		});
		
		c.add(jsonUtil);
		c.add(model);
		c.add(strCase);
		f.setVisible(true);
	}
}
