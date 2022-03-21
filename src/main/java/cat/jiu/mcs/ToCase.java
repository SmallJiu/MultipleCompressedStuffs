package cat.jiu.mcs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class ToCase {
	private static boolean isUpperCase = false;
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setBounds(0, 0, 460, 85);
		f.setLayout(new FlowLayout());
		
		Container c = f.getContentPane();
		JTextField tf = new JTextField(20);
		
		tf.setFont(new Font(null, 10, 25));
		tf.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER ) {
					if(isUpperCase) {
						tf.setText(tf.getText().toLowerCase());
						isUpperCase = !isUpperCase;
					}else {
						tf.setText(tf.getText().toUpperCase());
						isUpperCase = !isUpperCase;
					}
				}
				if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
					tf.setText("");
				}
			}
		});
		c.add(tf, BorderLayout.CENTER);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
