package cat.jiu.mcs;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

import cat.jiu.mcs.tools.ToCase;

public class MCSToolsMain {
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
		c.add(strCase);
		f.setVisible(true);
	}
}
