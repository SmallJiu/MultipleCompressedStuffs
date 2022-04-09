package cat.jiu.mcs.tools;

import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BatchRenames {
	public static void main(String[] args) {
		JFrame f = new JFrame("Batch Renames");// 创建一个窗体
		f.setLayout(null);// 设置窗体不布局为null布局(绝对布局)

		JTextField dir = new JTextField(99999999);// 创建一个文本输入框
		dir.setFont(new Font(null, 10, 20));// 设置文本输入框的字体
		dir.setBounds(70, 5, 999999, 30);// 设置文本输入框的所在x轴y轴以及长宽

		JTextField preName = new JTextField(99999999);// 同上
		preName.setFont(new Font(null, 10, 20));// 同上
		preName.setBounds(70, 35, 999999, 30);// 同上

		JTextField postName = new JTextField(99999999);// 同上
		postName.setFont(new Font(null, 10, 20));// 同上
		postName.setBounds(70, 65, 999999, 30);// 同上

		JTextField took = new JTextField(50);// 同上
		took.setFont(new Font(null, 10, 20));// 同上
		took.setBounds(105, 105, 300, 30);// 同上

		JTextArea logs = new JTextArea();// 创建文本域
		logs.setFont(new Font(null, 10, 20));// 同上

		JButton start = new JButton("Start");// 创建按钮
		start.setBounds(5, 105, 100, 40);// 同上

		start.addActionListener(event -> {// 使用lambda表达式给按钮添加点击事件
			// 判断路径文本，要替换的字的文本和替换后的字的文本是不是空的
			if(dir.getText().isEmpty() || preName.getText().isEmpty() || postName.getText().isEmpty()) {
				String s = "Can not be Empty!";
				took.setText(s);
				logs.append(s + "\n");
				return;// 不是则结束方法
			}

			File dirFile = new File(dir.getText());// 新建文件对象
			if(!dirFile.exists()) {// 判断文件是否存在
				String s = "Directory can not found! ( " + dir.getText() + " )";
				took.setText(s);
				logs.append(s + "\n");
				return;// 不是则结束方法
			}

			if(!dirFile.isDirectory()) {// 判断文件是否为文件夹
				String s = "Is not Directory! ( " + dir.getText() + " )";
				took.setText(s);
				logs.append(s + "\n");
				return;// 不是则结束方法
			}

			System.gc();// 清理内存
			try {
				long time = System.currentTimeMillis();// 获取进程运行的毫秒
				int[] renames = {0};// 定义已重命名的数组，因为另一个方法内无法直接修改外部变量，所以使用数组
				String textureSrc = dir.getText();// 从文本输入框获取String
				if(textureSrc.contains("\\")) {// 如果路径有\字符
					String[] src = textureSrc.split("\\\\");// 使str使用\为分隔符进行切割并返回数组
					String s = "";// 定义临时变量
					for(int i = 0; i < src.length; i++) {// for
						String str = src[i];
						s += str;// 等价于 s = s + str;
						if(i != src.length - 1) {
							s += "/";// 如果i不是数组的最后一个元素，则加上str
						}
					}
					textureSrc = s;// 使外部变量等于此变量
				}

				// 进入方法并传参
				rename(dirFile, preName.getText(), postName.getText(), renames, logs);
				time = System.currentTimeMillis() - time;// 获取进程运行的毫秒
				String s = "Finish. Rename " + renames[0] + " Files. (took " + time + " ms)";
				took.setText(s);
				logs.append(s + "\n");
			}catch(IOException e) {
				String s = "Fail." + e.getLocalizedMessage();
				took.setText(s);
				logs.append(s + "\n");
				e.printStackTrace();
			}
			System.gc();
		});

		Container c = f.getContentPane();// 获取窗体的容器
		JLabel dirInfo = new JLabel("\u8def\u5f84");// 创建一个文本
		dirInfo.setFont(new Font(null, 10, 20));// 同上
		dirInfo.setBounds(0, dir.getY(), 60, 20);// 同上
		c.add(dir);// 添加组件到容器内
		c.add(dirInfo);// 添加组件到容器内

		JLabel preNameInfo = new JLabel("\u8981\u66ff\u6362\u7684");
		preName.setFont(new Font(null, 10, 20));// 同上
		preNameInfo.setBounds(0, preName.getY(), 60, 20);// 同上
		c.add(preName);// 添加组件到容器内
		c.add(preNameInfo);// 添加组件到容器内

		JLabel postNameInfo = new JLabel("\u66ff\u6362\u540e\u7684");
		postName.setFont(new Font(null, 10, 20));// 同上
		postNameInfo.setBounds(0, postName.getY(), 60, 20);// 同上

		new Thread(() -> {// 创建新线程，并使用lambda表达式传参
			while(true) {
				System.gc();// 清理系统内存
				try {
					Thread.sleep(2000);// 进程休眠2秒
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();// 使进程开始运行

		c.add(postName);// 同上
		c.add(postNameInfo);// 同上
		c.add(start);// 同上
		c.add(took);// 同上
		JScrollPane log = new JScrollPane(logs);// 创建滚动组件
		log.setFont(new Font(null, 10, 20));// 同上
		log.setBounds(105, 145, 1000, 250);// 同上
		c.add(log);// 同上
		f.setBounds(0, 0, 400, 200);// 同上
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置窗体的默认关闭规则
		f.setVisible(true);// 设置窗体是否可见
	}

	private static void rename(File dirPath, String absName, String postName, int[] renames, JTextArea logs) throws IOException {
		if(!dirPath.isDirectory()) {
			return;// 如果文件不是文件夹则结束方法
		}

		for(File subFile : dirPath.listFiles()) {// 遍历文件夹的所有文件
			if(subFile.isDirectory()) {
				// 如果子文件是文件夹，则自循环方法，并传入不同的参数，防止死循环
				rename(new File(subFile.getCanonicalPath()), absName, postName, renames, logs);
			}else {// 如果文件不是文件夹
					// 获取子文件的名字
				String preSubFileName = subFile.getName();
				if(preSubFileName.contains(absName)) {// 如果文件名含有替换前的字符串
					String postSubFileName = "";// 定义替换后的名字的str
					String[] sa = preSubFileName.split(absName);// 使str使用 absName 为分隔符切割字符串并返回数组
					for(int i = 0; i < sa.length; i++) {
						if(i == sa.length - 1) {
							postSubFileName += sa[i];
						}else {
							postSubFileName += (sa[i] + postName);
						}
					}
					// 定义重命名后的文件的完整路径
					String path = subFile.getParent() + "/" + postSubFileName;
					logs.append(preSubFileName + " -> " + postSubFileName + "\n");

					renames[0] += 1;// 已重命名的文件数加一
					subFile.renameTo(new File(path));// 使文件重命名至 变量path 所指向的路径以及名字
				}
			}
		}
	}
}
