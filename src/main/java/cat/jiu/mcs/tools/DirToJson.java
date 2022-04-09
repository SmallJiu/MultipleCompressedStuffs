package cat.jiu.mcs.tools;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class DirToJson {
	public static void main(String[] args) {
		JFrame f = new JFrame("Dir To Json File");
		f.setBounds(10, 10, 200, 200);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextField dirPath = new JTextField(999999);
		dirPath.setFont(new Font(null, 10, 25));
		dirPath.setBounds(5, 5, 999999, 30);

		JTextField savePath = new JTextField(999999);
		savePath.setFont(new Font(null, 10, 25));
		savePath.setBounds(5, 40, 999999, 30);

		JButton start = new JButton("Start");
		start.setBounds(5, 80, 75, 25);

		start.addActionListener(e -> {
			File dir = new File(dirPath.getText());
			if(dir.isDirectory()) {
				long time = System.currentTimeMillis();
				try {
					toJsonFile(savePath.getText(), toJson(dir));
				}catch(JsonIOException e1) {
					e1.printStackTrace();
				}catch(JsonSyntaxException e1) {
					e1.printStackTrace();
				}catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				time = System.currentTimeMillis() - time;
			}
		});

		f.getContentPane().add(dirPath);
		f.getContentPane().add(savePath);
		f.getContentPane().add(start);

		f.setVisible(true);
	}

	private static JsonObject toJson(File dir) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonObject obj = new JsonObject();

		for(File subFile : dir.listFiles()) {
			if(subFile.isDirectory()) {
				obj.add(subFile.getName(), toJson(subFile));
			}else {
				if(subFile.getName().endsWith(".json")) {
					JsonElement e = new JsonParser().parse(new InputStreamReader(new FileInputStream(subFile), StandardCharsets.UTF_8));
					if(e.isJsonObject()) {
						if(subFile.getName().equals("compressed_creative_rf_source.0.json")) {
							System.out.println(subFile.getName());
						}
						JsonObject o = (JsonObject) e;
						if(o.has("textures")) {
							JsonObject textures = o.get("textures").getAsJsonObject();
							if(textures.has("layer0")) {
								obj.addProperty(subFile.getName().split("\\.")[0], textures.get("layer0").getAsString());
								System.out.println(subFile.getName().split("\\.")[0] + ": " + textures.get("layer0").getAsString());
								break;
							}else if(textures.has("all")) {
								obj.addProperty(subFile.getName().split("\\.")[0], textures.get("all").getAsString());
								System.out.println(subFile.getName().split("\\.")[0] + ": " + textures.get("all").getAsString());
								break;
							}
						}
					}
				}
			}
		}

		return obj;
	}

	public static boolean toJsonFile(String path, Object src) {
		String json = new GsonBuilder().create().toJson(src);

		try {
			File file = new File(path);
			if(!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
				file.getParentFile().mkdirs();
			}
			if(file.exists()) { // 如果已存在,删除旧文件
				file.delete();
			}

			file.createNewFile();
			Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			write.write(formatJson(json));
			write.flush();
			write.close();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String formatJson(String json) {
		StringBuffer result = new StringBuffer();
		int length = json.length();
		int number = 0;
		char key = 0;

		for(int i = 0; i < length; i++) {
			// 1、获取当前字符。
			key = json.charAt(i);

			// 2、如果当前字符是前方括号/前花括号做如下处理：
			if((key == '[') || (key == '{')) {
				// 增加str
				result.append(key);

				// （3）前方括号/前花括号，的后面必须换行。打印：换行。
				result.append('\n');

				// （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
				number++;
				result.append(indent(number));

				continue;
			}

			// 3、如果当前字符是后方括号、后花括号做如下处理：
			if((key == ']') || (key == '}')) {
				// （1）后方括号、后花括号，的前面必须换行。打印：换行。
				result.append('\n');

				// （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
				number--;
				result.append(indent(number));

				// 增加str
				result.append(key);
				continue;
			}

			// 如果是","，则换行并缩进，并且不改变缩进次数
			if((key == ',')) {
				result.append(key);
				result.append('\n');
				result.append(indent(number));
				continue;
			}

			if(key == ':') {
				result.append(key);
				result.append(' ');
				continue;
			}

			result.append(key);
		}

		result.append('\n');
		return result.toString();
	}

	private static String indent(int number) {
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < number; i++) {
			result.append("	");
		}
		return result.toString();
	}
}
