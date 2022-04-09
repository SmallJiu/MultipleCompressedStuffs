package cat.jiu.mcs.tools;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ToCase {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setTitle("ToCase");
		f.setBounds(0, 0, 480, 120);
		f.setLayout(new FlowLayout());

		Container c = f.getContentPane();
		JButton start = new JButton("Start");
		JTextField tf = new JTextField(20);
		JComboBox<String> strCase = new JComboBox<String>(new String[]{" toLowerCase", " toUpperCase"});

		tf.setFont(new Font(null, 10, 25));

		start.addActionListener(e -> {
			if(strCase.getSelectedIndex() == 0) {
				tf.setText(tf.getText().toLowerCase());
			}else {
				tf.setText(tf.getText().toUpperCase());
			}
		});
		tf.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					start.doClick();
				}
				if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
					tf.setText("");
				}
			}
		});
		c.add(strCase, BorderLayout.NORTH);
		c.add(start, BorderLayout.NORTH);
		c.add(tf, BorderLayout.CENTER);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
