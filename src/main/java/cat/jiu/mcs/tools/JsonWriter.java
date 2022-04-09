package cat.jiu.mcs.tools;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class JsonWriter {
	public static final Logger log = new Logger();
	public static final Filter JSON = new Filter("json", "(.json) Json File");
	public static final Filter BACKUP = new Filter("json.backup", "(.json.backup) Json Backup File");
	public static Language lang = null;

	public static void main(String[] args) {
		if(args.length == 1) {
			if(args[0].toLowerCase().equals("-help") || args[0].toLowerCase().equals("/help")) {
				System.out.println("\"/help\" or \"-help\": Help information");
				System.out.println("\"-p\" or \"-path\" or \"/p\" or \"/path\": The file path");
				System.out.println("\"-l\" or \"-log\" or \"/l\" or \"/log\": Can write log to file; use \"true\" or \"false\", default true");
			}
		}else if(args.length == 2 || args.length == 4) {
			String path = null;
			if(args.length == 2) {
				if(args[0].toLowerCase().equals("-path") || args[0].toLowerCase().equals("-p") || args[0].toLowerCase().equals("/path") || args[0].toLowerCase().equals("/p")) {
					path = args[1];
				}
			}

			if(args.length == 4) {
				if(args[0].toLowerCase().equals("-path") || args[0].toLowerCase().equals("-p") || args[0].toLowerCase().equals("/path") || args[0].toLowerCase().equals("/p")) {
					path = args[1];
				}else if(args[2].toLowerCase().equals("-path") || args[2].toLowerCase().equals("-p") || args[2].toLowerCase().equals("/path") || args[2].toLowerCase().equals("/p")) {
					path = args[3];
				}

				if(args[0].toLowerCase().equals("-l") || args[0].toLowerCase().equals("-log") || args[0].toLowerCase().equals("/l") || args[0].toLowerCase().equals("/log")) {
					log.setCanWriteLog(new Boolean(args[1]));
				}else if(args[2].toLowerCase().equals("-l") || args[2].toLowerCase().equals("-log") || args[2].toLowerCase().equals("/l") || args[2].toLowerCase().equals("/log")) {
					log.setCanWriteLog(new Boolean(args[3]));
				}
			}

			if(path == null) {
				start(args);
			}else {
				try {
					File file = new File(path);

					if(!file.exists()) {
						Tools.toJsonFile(path, new JsonObject());
					}

					if(file.getName().toLowerCase().endsWith("json") || file.getName().toLowerCase().endsWith("json.backup")) {
						JsonObject obj = new JsonParser().parse(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)).getAsJsonObject();
						new HelperFrame(args, obj, path).setVisible(true);
					}else {
						start(args);
					}
				}catch(Exception e) {
					Tools.createErrorDialog(e.getMessage());
					start(args);
				}
			}
		}else {
			start(args);
		}
	}

	private static void start(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLayout(new FlowLayout());
		f.setBounds(200, 200, 250, 115);
		f.setTitle("Choice");

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.gc();
				System.gc();
				System.gc();
				System.gc();
				System.gc();
				System.gc();
			}
		});

		Container c = f.getContentPane();

		JComboBox<String> cb = new JComboBox<String>(new String[]{"\u7b80\u4f53\u4e2d\u6587", "English"});
		cb.setEditable(false);
		c.add(cb);

		JTextField tf = new JTextField("D:\\Users\\small_jiu\\Desktop\\custom.json", 10);

		File history = new File("history");
		if(history.exists()) {
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				fis = new FileInputStream(history);
				isr = new InputStreamReader(fis);
				br = new BufferedReader(isr);

				String s = br.readLine();
				if(!(s == null || "".equals(s))) {
					tf.setText(s);
				}
			}catch(IOException e1) {
				e1.printStackTrace();
			}finally {
				try {
					fis.close();
					isr.close();
					br.close();
				}catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		JFileChooser fileC = new JFileChooser();
		fileC.addChoosableFileFilter(JSON);
		fileC.addChoosableFileFilter(BACKUP);

		JButton choosFile = new JButton("File");
		choosFile.setBounds(20, 20, 50, 50);

		choosFile.addActionListener(e -> {
			fileC.showOpenDialog(f);
			if(fileC.getSelectedFile() != null) {
				tf.setText(fileC.getSelectedFile().getPath());
			}
		});

		JButton btn = new JButton("Start");
		btn.setBounds(20, 20, 50, 50);
		btn.addActionListener(env -> {
			String langKey = null;
			if(cb.getSelectedIndex() == 0) {
				langKey = "zh_cn";
			}else if(cb.getSelectedIndex() == 1) {
				langKey = "en_us";
			}
			if(langKey != null) {
				lang = new Language(LangType.format(langKey));
			}else {
				lang = new Language(LangType.EN_US);
			}

			try {
				String path = tf.getText();

				if(!path.toLowerCase().endsWith(".json")) {
					path += ".json";
				}

				if(path.toLowerCase().endsWith("\\") || path.toLowerCase().endsWith("/")) {
					path += "Default.json";
				}

				File file = new File(path);

				if(!file.exists()) {
					JsonObject obj = new JsonObject();
					obj.add("main", new JsonObject());
					Tools.toJsonFile(path, obj);
				}

				if(file.getName().toLowerCase().endsWith(".json") || file.getName().toLowerCase().endsWith(".json.backup")) {
					JsonObject obj = new JsonParser().parse(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)).getAsJsonObject();
					f.dispose();
					new HelperFrame(args, obj, path).setVisible(true);
				}else {
					Tools.createErrorDialog(f, lang.format("error.not_json_file.info"));
				}
			}catch(Exception e) {
				String eMsg = "Exception";
				if(e instanceof JsonIOException || e instanceof JsonSyntaxException || e instanceof FileNotFoundException) {
					eMsg = e.getMessage();
				}else {
					eMsg = lang.format("error.unknown_error.info");
				}

				log.error(e);
				Tools.createErrorDialog(f, eMsg);
				e.printStackTrace();
			}
		});

		c.add(tf);
		c.add(choosFile);
		c.add(btn);
		f.setVisible(true);
	}

	public static class Filter extends FileFilter implements java.io.FileFilter {
		final String end;
		final String arg;

		public Filter(String end, String arg) {
			this.end = end;
			this.arg = arg;
		}

		@Override
		public boolean accept(File file) {
			String fileName = file.getName();
			if(fileName.toUpperCase().endsWith(this.end.toUpperCase())) {
				return true;
			}else {
				return false;
			}
		}

		@Override
		public String getDescription() {
			return this.arg;
		}
	}

	public static class Language {
		private final HashMap<String, String> lang = new HashMap<>();
		private final LangType type;

		public Language(LangType t) {
			this.type = t;
			try(InputStream is = Tools.getFileStream("/assets/jiucore/json_writer_lang/" + t.name().toLowerCase() + ".lang")) {
				try(InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
					try(BufferedReader br = new BufferedReader(isr)) {
						String s = "";
						while((s = br.readLine()) != null) {
							if(!s.isEmpty() && !s.equals("")) {
								String[] lang = s.split("=");
								this.lang.put(lang[0], lang[1]);
							}
						}
					}
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

		public String format(String key) {
			if(this.lang.containsKey(key)) {
				return this.lang.get(key);
			}else {
				return key;
			}
		}

		public LangType getType() {
			return this.type;
		}

		@SuppressWarnings("unchecked")
		public HashMap<String, String> getLangs() {
			return (HashMap<String, String>) this.lang.clone();
		}
	}

	public static enum LangType {
		EN_US, ZH_CN;
		public static LangType format(String lang) {
			for(LangType type : values()) {
				if(type.name().toLowerCase().equals(lang.toLowerCase())) {
					return type;
				}
			}
			return EN_US;
		}
	}

	public static enum Level {
		DEBUG, INFO, WARN, ERROR, FATAL;
	}

	public static class Logger {
		private final File file;
		private boolean writeLog = true;

		public Logger() {
			File file = new File("log.log");
			if(!file.exists()) {
				try {
					file.createNewFile();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			this.file = file;
		}

		public void log(Level level, String msg, Object... params) {
			StringBuilder sb = new StringBuilder();

			sb.append(this.getDate());
			sb.append(" [Helper] [ ");
			sb.append(level.name().toLowerCase());
			sb.append(" ] ");
			sb.append(msg);
			for(Object obj : params) {
				sb.append(obj.toString());
			}
			System.out.println(sb.toString());
			if(writeLog) {
				this.writeLog(sb.toString());
			}
		}

		public boolean canWriteLog() {
			return this.writeLog;
		}

		public void setCanWriteLog(boolean write) {
			this.writeLog = write;
		}

		public void log(Level level, String msg) {
			this.log(level, msg, new Object[0]);
		}

		public void debug(String msg) {
			this.log(Level.DEBUG, msg);
		}

		public void debug(String msg, Object... params) {
			this.log(Level.DEBUG, msg, params);
		}

		public void info(String msg) {
			this.log(Level.INFO, msg);
		}

		public void info(String msg, Object... params) {
			this.log(Level.INFO, msg, params);
		}

		public void warning(String msg) {
			this.log(Level.WARN, msg);
		}

		public void warning(String msg, Object... params) {
			this.log(Level.WARN, msg, params);
		}

		public void error(Exception e) {
			this.log(Level.ERROR, e.getLocalizedMessage());
			for(StackTraceElement ste : e.getStackTrace()) {
				this.log(Level.ERROR, "   " + ste.toString());
			}
		}

		public void error(String msg) {
			this.log(Level.ERROR, msg);
		}

		public void error(String msg, Object... params) {
			this.log(Level.ERROR, msg, params);
		}

		public void fatal(String msg) {
			this.log(Level.FATAL, msg);
		}

		public void fatal(String msg, Object... params) {
			this.log(Level.FATAL, msg, params);
		}

		@SuppressWarnings("deprecation")
		public String getDate() {
			Date date = new Date();
			return "[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "]";
		}

		private void writeLog(String text) {
			try {
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(this.file, true));
				out.write(text + "\n");
				out.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class JWI18n {
		public static String getLangName() {
			return lang.getType().name().toLowerCase();
		}

		public static String format(String key) {
			return lang.format(key);
		}
	}

	public static class HelperFrame extends JFrame {
		private static final long serialVersionUID = -6142031965714724663L;
		private final String path;
		private final Map<String, String> normalEntrys;
		private final Map<String, String[]> arrayEntrys;
		private final JsonObject mainObject;
		private boolean isClose = false;
		private final File file;
		private final File dir;

		@SuppressWarnings("deprecation")
		public HelperFrame(String[] args, JsonObject mainObject, String path) {
			super("Helpers-" + path);
			this.path = path;
			this.file = new File(path);
			this.dir = new File("backup/" + file.getName());
			this.normalEntrys = new HashMap<>();
			this.arrayEntrys = new HashMap<>();
			this.mainObject = mainObject;
			this.setLayout(new BorderLayout());
			this.setBounds(0, 0, 520, 440);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			Container c = this.getContentPane();

			DefaultMutableTreeNode root = new DefaultMutableTreeNode("Main", true);

			this.excuteTree(root, mainObject);

			JTree tree = new JTree(root);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

			JTextField objName = new JTextField(5);
			JTextField objValue = new JTextField(5);

			objName.setNextFocusableComponent(objValue);// 按下Tab切换到另一个文本框
			objValue.setNextFocusableComponent(objName);// 按下Tab切换到另一个文本框

			tree.addTreeSelectionListener(event -> {
				JTree t = (JTree) event.getSource();// 获取选中的树
				TreePath selecPath = t.getSelectionPath();// 获取选中节点的路径
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) selecPath.getLastPathComponent();// 获取节点名称

				String name = node.getPath()[node.getPath().length - 1].toString().split(":")[0];// 分割节点名称
				if(normalEntrys.containsKey(name)) {
					objName.setText(name);
					objValue.setText(normalEntrys.get(name));
				}else {
					objName.setText(name);
					objValue.setText("");
				}
			});

			JPanel south = new JPanel();

			JButton add = new JButton(JWI18n.format("button.add.name"));
			JButton change = new JButton(JWI18n.format("button.change.name"));
			JButton rename = new JButton(JWI18n.format("button.rename.name"));
			JButton remove = new JButton(JWI18n.format("button.remove.name"));
			JButton plus = new JButton(JWI18n.format("button.plus.name"));
			JButton subtract = new JButton(JWI18n.format("button.subtract.name"));
			add.setBounds(0, 0, 10, 10);
			this.saveFile(this, south, path);

			objName.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER) {
						add.doClick();
						objValue.setText("");
						objName.setText("");
					}
				}
			});

			objValue.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER) {
						add.doClick();
						objName.grabFocus();// 切换焦点
						objValue.setText("");
						objName.setText("");
					}
				}
			});

			this.excuteButton0(this, add, change, rename, remove, plus, subtract, tree, objName, objValue, new DefaultTreeModel(root, true));

			JScrollPane treePane = new JScrollPane(tree);
			south.add(plus);
			south.add(subtract);
			south.add(objName);
			south.add(objValue);
			south.add(add);
			south.add(rename);
			south.add(change);
			south.add(remove);

			JPanel north = new JPanel();
			JTextField objname = new JTextField(5);
			JButton addObject = new JButton(JWI18n.format("button.add.object.name"));
			JButton addArray = new JButton(JWI18n.format("button.add.array.name"));
			JButton reload = new JButton(JWI18n.format("button.reload.name"));

			objname.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER) {
						addObject.doClick();
					}
				}
			});

			reload.addActionListener(event -> {
				this.dispose();// 关闭窗口
				isClose = true;// 设置已关闭
				System.gc();
				main(new String[]{"-path", path, "-log", Boolean.toString(log.canWriteLog())});// 写入启动参数
			});
			JButton restart = new JButton(JWI18n.format("button.restart.name"));
			restart.addActionListener(event -> {
				this.dispose();// 关闭窗口
				isClose = true;// 设置已关闭
				System.gc();
				this.onObjectChange();// 保存文件
				main(new String[0]);
			});

			north.add(restart);
			north.add(addObject);
			north.add(objname);
			north.add(reload);

			this.excuteButton1(this, addObject, addArray, tree, objname, new DefaultTreeModel(root, true));

			c.add(south, BorderLayout.SOUTH);
			c.add(north, BorderLayout.NORTH);
			c.add(treePane, BorderLayout.CENTER);

			System.out.println("ElementSize: " + this.normalEntrys.size());

			this.saveBackup(path);

			new Thread(() -> {
				while(!this.isClose) {
					if(dir.listFiles(BACKUP).length > 10) {
						dir.listFiles()[0].delete();
					}
					try {
						Thread.sleep(5000);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, "Auto Delete Backup File Thread").start();

			try {
				File file = new File("history");
				if(file.exists()) {
					file.delete();
				}
				file.createNewFile();
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
				out.write(path);
				out.close();
			}catch(IOException e) {
				e.printStackTrace();
			}

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					this.write(path);
					onObjectChange();
					isClose = true;
				}

				private void write(String text) {
					try {
						File file = new File("history");
						if(file.exists()) {
							file.delete();
						}
						file.createNewFile();
						OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
						out.write(text);
						out.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			});
		}

		private void excuteTree(DefaultMutableTreeNode root, JsonObject mainObject) {
			for(Map.Entry<String, JsonElement> jobj : mainObject.entrySet()) {
				JsonElement element = jobj.getValue();
				String name = jobj.getKey();
				this.subTree(root, name, element);
			}
		}

		private void subTree(DefaultMutableTreeNode root, String key, JsonElement element) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(key);

			if(element.isJsonPrimitive()) {
				String s = element.getAsString().toLowerCase();
				this.normalEntrys.put(key, s);
				node = new DefaultMutableTreeNode(key + ": " + s.toLowerCase());
			}else if(element.isJsonObject()) {
				for(Map.Entry<String, JsonElement> subEntry : element.getAsJsonObject().entrySet()) {
					this.subTree(node, subEntry.getKey(), subEntry.getValue());
				}
			}else if(element.isJsonArray()) {
				this.excuteArray(node, key, (JsonArray) element);
			}
			root.add(node);
		}

		private void excuteArray(DefaultMutableTreeNode root, String key, JsonArray array) {
			if(!array.isJsonNull()) {
				List<String> arrayList = new ArrayList<>();

				for(int i = 0; i < array.size(); i++) {
					JsonElement subE = array.get(i);
					DefaultMutableTreeNode subnode = new DefaultMutableTreeNode("...(" + i + ")");
					if(!subE.isJsonNull()) {
						if(subE.isJsonObject()) {
							this.subTree(root, "...(" + i + ")", subE.getAsJsonObject());
						}else if(subE.isJsonArray()) {
							this.excuteArray(subnode, "...(" + i + ")", subE.getAsJsonArray());
							root.add(subnode);
						}else {
							String s = subE.getAsString().toLowerCase();
							arrayList.add(s.toLowerCase());
							subnode = new DefaultMutableTreeNode(s);
							root.add(subnode);
						}
					}
				}

				if(!arrayList.isEmpty()) {
					this.arrayEntrys.put(key, arrayList.toArray(new String[arrayList.size()]));
				}
			}
		}

		private void excuteButton1(HelperFrame main, JButton obj, JButton array, JTree tree, JTextField objName, DefaultTreeModel treeModel) {
			obj.addActionListener(event -> {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(objName.getText());// 创建欲添加节点
				TreePath selectionPath = tree.getSelectionPath();// 获得选中的父节点路径
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();// 获得选中的父节点
				treeModel.insertNodeInto(node, parentNode, parentNode.getIndex(parentNode) + 1);// 插入节点到所有子节点之后
				TreePath path = selectionPath.pathByAddingChild(node);// 获得新添加节点的路径
				if(!tree.isVisible(path)) {
					tree.makeVisible(path);// 如果该节点不可见则令其可见
				}
				Tools.addObject(this, this.mainObject, this.getStringPath(parentNode), parentNode.toString(), objName.getText(), new JsonObject());
				this.onObjectChange();
			});

			array.addActionListener(event -> {
				this.errorDialog(JWI18n.format("button.array.error"));
				/*
				 * DefaultMutableTreeNode node = new DefaultMutableTreeNode(objName.getText());// 创建欲添加节点 TreePath selectionPath = tree.getSelectionPath();// 获得选中的父节点路径 DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();// 获得选中的父节点 treeModel.insertNodeInto(node, parentNode, parentNode.getIndex(parentNode)+1);// 插入节点到所有子节点之后 TreePath path = selectionPath.pathByAddingChild(node);// 获得新添加节点的路径 if (!tree.isVisible(path)) { tree.makeVisible(path);// 如果该节点不可见则令其可见 } Tools.addObject(this, this.mainObject, this.getStringPath(parentNode), parentNode.toString(),objName.getText(), new JsonArray()); this.onObjectChange();
				 */
			});
		}

		private void excuteButton0(JFrame main, JButton add, JButton change, JButton rename, JButton remove, JButton plus, JButton subtract, JTree tree, JTextField objName, JTextField objValue, DefaultTreeModel treeModel) {
			add.addActionListener(event -> {
				if(!objName.getText().isEmpty()) {
					if(objValue.getText().isEmpty()) {
						objValue.setText("1");
					}
					if(!this.normalEntrys.containsKey(objName.getText())) {
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(objName.getText());// 创建欲添加节点
						TreePath selectionPath = tree.getSelectionPath();// 获得选中的父节点路径
						DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();// 获得选中的父节点
						treeModel.insertNodeInto(node, parentNode, parentNode.getIndex(parentNode) + 1);// 插入节点到所有子节点之后
						TreePath path = selectionPath.pathByAddingChild(node);// 获得新添加节点的路径
						if(!tree.isVisible(path)) {
							tree.makeVisible(path);// 如果该节点不可见则令其可见
						}
						this.normalEntrys.put(objName.getText(), objValue.getText());
						Tools.addObject(this, this.mainObject, this.getStringPath(parentNode), parentNode.toString(), parentNode.toString() + " " + objName.getText(), objValue.getText());
						this.onObjectChange();
					}
				}else {
					this.errorDialog(JWI18n.format("info.error.empty"));
				}
			});
			change.addActionListener(event -> {
				if(!objName.getText().isEmpty() && !objValue.getText().isEmpty()) {
					this.normalEntrys.replace(objName.getText(), objValue.getText());
					Tools.changeJsonValue(this, this.mainObject, objName.getText(), objValue.getText());
					this.onObjectChange();
				}else {
					this.errorDialog(JWI18n.format("info.error.empty"));
				}
			});
			rename.addActionListener(event -> {
				if(!objName.getText().isEmpty()) {
					// 获得选中的欲修改节点的路径
					TreePath selectionPath = tree.getSelectionPath();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();// 获得选中的欲修改节点
					Tools.renameObject(this, this.mainObject, node.toString().split(":")[0], objName.getText());
					if(!node.toString().split(":")[0].endsWith(")")) {
						node.setUserObject(objName.getText() + ": " + this.normalEntrys.get(node.toString().split(":")[0]));// 修改节点的用户标签
						treeModel.nodeChanged(node);// 通知树模型该节点已经被修改
						tree.setSelectionPath(selectionPath);// 选中被修改的节点
						this.onObjectChange();
					}
				}else {
					this.errorDialog(JWI18n.format("info.error.empty"));
				}
			});
			remove.addActionListener(event -> {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();// 获得选中的欲删除节点
				// 查看欲删除的节点是否为根节点，根节点不允许删除
				if(node != null) {
					if(!node.isRoot()) {
						DefaultMutableTreeNode nextSelectedNode = node.getNextSibling();// 获得下一个兄弟节点，以备选中
						if(nextSelectedNode == null) {// 查看是否存在兄弟节点
							nextSelectedNode = (DefaultMutableTreeNode) node.getParent();// 如果不存在则选中其父节点
							Tools.removeObject(this, this.mainObject, nextSelectedNode.toString().split(":")[0], node.toString().split(":")[0]);
							treeModel.removeNodeFromParent(node);// 删除节点
							tree.setSelectionPath(new TreePath(nextSelectedNode.getPath()));// 选中节点
							this.onObjectChange();
						}
					}
				}
			});
			plus.addActionListener(event -> {
				if(!objName.getText().isEmpty()) {
					if(objValue.getText().isEmpty()) {
						objValue.setText("1");
					}
					try {
						int value = Integer.parseInt(this.normalEntrys.get(objName.getText()));
						this.normalEntrys.replace(objName.getText(), Integer.toString(value + 1));
						Tools.changeJsonValue(this, this.mainObject, objName.getText(), new JsonPrimitive(value + 1));
						this.onObjectChange();
						objValue.setText(Integer.toString(value + 1));
					}catch(Exception e) {
						Tools.createErrorDialog(this, JWI18n.format("info.error.number"));
					}
				}else {
					this.errorDialog(JWI18n.format("info.error.empty"));
				}
			});
			subtract.addActionListener(event -> {
				if(!objName.getText().isEmpty()) {
					if(objValue.getText().isEmpty()) {
						objValue.setText("1");
					}
					try {
						int value = Integer.parseInt(this.normalEntrys.get(objName.getText()));
						this.normalEntrys.replace(objName.getText(), Integer.toString(value - 1));
						Tools.changeJsonValue(this, this.mainObject, objName.getText(), new JsonPrimitive(value - 1));
						this.onObjectChange();
						objValue.setText(Integer.toString(value - 1));
					}catch(Exception e) {
						Tools.createErrorDialog(this, JWI18n.format("info.error.number"));
					}
				}else {
					this.errorDialog(JWI18n.format("info.error.empty"));
				}
			});
		}

		private void errorDialog(String text) {
			JDialog errorDialog = new JDialog(this, "Error!", true);
			errorDialog.getContentPane().add(new JLabel(text));
			errorDialog.setBounds(this.getX() + 1, this.getY() + 1, (int) this.getSize().getWidth() - 1, 100);
			errorDialog.setVisible(true);
		}

		private String[] getStringPath(DefaultMutableTreeNode parentNode) {
			TreeNode[] tree = parentNode.getPath();
			List<String> path = new ArrayList<>();
			for(int i = 0; i < tree.length; i++) {
				path.add(tree[i].toString());
			}
			return path.toArray(new String[tree.length]);
		}

		private void saveBackup(String path) {
			File file = new File(path);
			File copyed = new File("backup/" + file.getName() + "/" + file.getName() + "-" + this.getDate() + ".json.backup");
			try {
				copyed.getParentFile().mkdirs();
				copyed.createNewFile();
				copyFile(file, copyed);
			}catch(IOException e1) {
				e1.printStackTrace();
			}
		}

		private void saveFile(JFrame f, JPanel south, String path) {
			new Thread(() -> {
				while(!this.isClose) {
					int s = 61;
					while(s > 0 && !this.isClose) {
						s -= 1;
						System.gc();
						try {
							Thread.sleep(1000);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
						System.gc();
					}
					this.saveBackup(path);
				}
			}, "Save Thread").start();
		}

		@SuppressWarnings("deprecation")
		public String getDate() {
			Date date = new Date();
			return (date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate() + "." + date.getHours() + "." + date.getMinutes() + "." + date.getSeconds();
		}

		private void copyFile(File in, File out) throws IOException {
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new FileInputStream(in);
				output = new FileOutputStream(out);
				byte[] buf = new byte[1024];
				int bytesRead;
				while((bytesRead = input.read(buf)) != -1) {
					output.write(buf, 0, bytesRead);
				}
			}finally {
				try {
					input.close();
					output.close();
				}catch(Exception e) {
				}
			}
		}

		public void onObjectChange() {
			Tools.toJsonFile(this.path, this.mainObject);
		}

		public Map<String, String> getEntry() {
			return this.normalEntrys;
		}
	}
	public static class Tools {
		private static final Tools tools = new Tools();

		public static boolean openOnBrowse(String url) {
			return openOnBrowse((JFrame) null, url);
		}

		public static boolean openOnBrowse(JFrame f, String url) {
			try {
				Desktop.getDesktop().browse(new URI(url));
				return true;
			}catch(IOException | URISyntaxException e) {
				e.printStackTrace();
				log.fatal(e.getMessage());
				Tools.createErrorDialog(f, e.getMessage());
				return false;
			}
		}

		public static URL getFileURL(String path) {
			return getFileURL(tools, path);
		}

		public static InputStream getFileStream(String path) {
			return getFileStream(tools, path);
		}

		public static <O> URL getFileURL(O obj, String path) {
			return obj.getClass().getResource(path);
		}

		public static <O> InputStream getFileStream(O obj, String path) {
			return obj.getClass().getResourceAsStream(path);
		}

		public static void changeJsonValue(JFrame f, JsonElement element, String name, String newvalue) {
			try {
				int i = Integer.parseInt(newvalue);
				changeJsonValue(f, element, name, new JsonPrimitive(i));
			}catch(Exception e) {
				if(newvalue.equals("true") || newvalue.equals("false")) {
					boolean b = Boolean.parseBoolean(newvalue);
					changeJsonValue(f, element, name, new JsonPrimitive(b));
				}else {
					changeJsonValue(f, element, name, new JsonPrimitive(newvalue));
				}
			}
		}

		public static void changeJsonValue(JFrame f, JsonElement element, String name, JsonElement newvalue) {
			if(element.isJsonArray()) {
				// JsonArray array = (JsonArray) element;
				// for (int i = 0; i < array.size(); i++) {
				// JsonElement arrayE = array.get(i);
				// if(arrayE.isJsonPrimitive()) {
				// if(arrayE.getAsJsonPrimitive().toString().equals(newvalue.toString())) {
				// replace(arrayE, name, newvalue);
				// break;
				// }
				// }else {
				// changeJsonValue(f, arrayE, name, newvalue);
				// }
				// }
			}else if(element.isJsonObject()) {
				JsonObject obj = (JsonObject) element;
				if(obj.has(name)) {
					replace(element, name, newvalue);
				}else {
					for(Entry<String, JsonElement> objE : obj.entrySet()) {
						changeJsonValue(f, objE.getValue(), name, newvalue);
					}
				}
			}
		}

		private static void replace(JsonElement element, String name, JsonElement newvalue) {
			if(element.isJsonObject()) {
				JsonObject obj = (JsonObject) element;
				if(obj.has(name)) {
					obj.remove(name);
					obj.add(name, newvalue);
				}
			}else if(element.isJsonArray()) {
				// JsonArray array = (JsonArray) element;
				// for(int i = 0; i < array.size(); i++) {
				// if(array.isJsonPrimitive()) {
				// if(array.get(i).getAsJsonPrimitive().getAsString().equals(name)) {
				// array.remove(i);
				// array.add(newvalue);
				// break;
				// }
				// }
				// }
			}
		}

		public static String upperCaseToFirstLetter(String arg) {
			return arg.substring(0, 1).toUpperCase() + arg.substring(1);
		}

		public static void removeObject(JFrame f, JsonElement element, String pathName, String name) {
			if(pathName.endsWith(")")) {
				int length = new Integer(name.substring(2, name.length()));
				element.getAsJsonArray().remove(length);
			}else if(element.isJsonArray()) {
				// JsonArray array = (JsonArray) element;
				// for(int i = 0; i < array.size(); i++) {
				// JsonElement arrayElement = array.get(i);
				// if(arrayElement.isJsonArray() || arrayElement.isJsonObject()) {
				// removeObject(f, arrayElement, pathName, name);
				// }else if(arrayElement.isJsonPrimitive()) {
				// if(arrayElement.getAsString().equals(name)) {
				// array.remove(i);
				// break;
				// }
				// }
				// }
			}else if(element.isJsonObject()) {
				JsonObject obj = (JsonObject) element;
				if(obj.has(name)) {
					obj.remove(name);
				}else {
					for(Entry<String, JsonElement> objEntrys : obj.entrySet()) {
						removeObject(f, objEntrys.getValue(), pathName, name);
					}
				}
			}
		}

		public static void renameObject(JFrame f, JsonElement element, String oldname, String newname) {
			if(oldname.endsWith(")")) {
				createErrorDialog(f, "can't rename");
			}else if(element.isJsonArray()) {
				// for(int i = 0; i < element.getAsJsonArray().size(); i++) {
				// JsonElement arrayEement = element.getAsJsonArray().get(i);
				// if(arrayEement.isJsonPrimitive()) {
				// if(arrayEement.getAsString().equals(oldname)) {
				// element.getAsJsonArray().set(i, new JsonPrimitive(newname));
				// break;
				// }
				// }else {
				// renameObject(f, arrayEement, oldname, newname);
				// }
				// }
			}else if(element.isJsonObject()) {
				for(Entry<String, JsonElement> objEntrys : element.getAsJsonObject().entrySet()) {
					if(objEntrys.getKey().equals(oldname)) {
						JsonElement e = objEntrys.getValue();
						element.getAsJsonObject().add(newname, e);
						element.getAsJsonObject().remove(oldname);
						break;
					}else {
						renameObject(f, objEntrys.getValue(), oldname, newname);
					}
				}
			}
		}

		public static void addObject(JFrame f, JsonElement element, String[] path, String pathName, String name, String value) {
			try {
				int i = Integer.parseInt(value);
				addObject(f, element, path, pathName, name, new JsonPrimitive(i));
			}catch(Exception e) {
				if(value.equals("true") || value.equals("false")) {
					addObject(f, element, path, pathName, name, new JsonPrimitive(Boolean.parseBoolean(value)));
				}else {
					addObject(f, element, path, pathName, name, new JsonPrimitive(value));
				}
			}
		}

		public static void addObject(JFrame f, JsonElement element, String[] path, String pathName, String name, JsonElement value) {
			if(pathName.endsWith(")")) {
				// JsonElement pathObj = ((JsonObject) element).get(path[1]);
				// pathObj.getAsJsonArray().add(value);
			}else if(path.length <= 1) {
				if(element.isJsonObject()) {
					element.getAsJsonObject().add(name, value);
				}else if(element.isJsonArray()) {
					// element.getAsJsonArray().add(value);
				}
			}else if(element.isJsonArray()) {
				// element.getAsJsonArray().add(value);
			}else if(element.isJsonObject()) {
				JsonObject obj = (JsonObject) element;
				if(obj.has(path[1])) {
					JsonElement pathObj = ((JsonObject) element).get(path[1]);
					if(pathObj.isJsonPrimitive()) {
						createErrorDialog(f, "can't add to " + element.toString());
					}else if(pathObj.isJsonObject()) {
						addObject(f, ((JsonObject) element).get(path[1]).getAsJsonObject(), getPath(path), pathName, name, value);
					}else if(pathObj.isJsonArray()) {
						((JsonObject) element).get(path[1]).getAsJsonArray().add(value);
					}
				}else {
					obj.add(name, value);
				}
			}
		}

		public static void createErrorDialog(String msg) {
			createErrorDialog((JFrame) null, msg);
		}

		public static void createErrorDialog(JFrame f, String eMsg) {
			JDialog jd = new JDialog(f, true);
			jd.setTitle("Exception!");
			log.fatal(eMsg);
			jd.getContentPane().add(new JLabel(eMsg));
			jd.setBounds(f.getX() + 1, f.getY() + 1, (int) f.getSize().getWidth() - 1, 60);
			jd.setVisible(true);
		}

		private static String[] getPath(String[] args) {
			String[] arg = new String[args.length - 1];
			for(int i = 1; i < arg.length; i++) {
				arg[i - 1] = args[i];
			}
			return arg;
		}

		public static boolean isString(JsonElement e) {
			if(e.isJsonPrimitive()) {
				return ((JsonPrimitive) e).isString();
			}
			return false;
		}

		public static boolean isNumber(JsonElement e) {
			if(e.isJsonPrimitive()) {
				return ((JsonPrimitive) e).isNumber();
			}
			return false;
		}

		public static boolean isBoolean(JsonElement e) {
			if(e.isJsonPrimitive()) {
				return ((JsonPrimitive) e).isBoolean();
			}
			return false;
		}

		public static boolean isString(JsonObject obj, String path) {
			if(obj.has(path)) {
				return isString(obj.get(path));
			}
			return false;
		}

		public static boolean isNumber(JsonObject obj, String path) {
			if(obj.has(path)) {
				return isNumber(obj.get(path));
			}
			return false;
		}

		public static boolean isBoolean(JsonObject obj, String path) {
			if(obj.has(path)) {
				return isBoolean(obj.get(path));
			}
			return false;
		}

		public static Map<String, String> toMap(InputStream in) {
			return toMap(new JsonParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject());
		}

		public static Map<String, String> toMap(File obj) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
			return toMap(new JsonParser().parse(new InputStreamReader(new FileInputStream(obj), StandardCharsets.UTF_8)).getAsJsonObject());
		}

		public static Map<String, String> toMap(JsonObject obj) {
			Map<String, String> map = new HashMap<>();
			for(Map.Entry<String, JsonElement> jobj : obj.entrySet()) {
				JsonElement element = jobj.getValue();
				String name = jobj.getKey();
				subTree(map, name, element);
			}
			return map;
		}

		private static void subTree(Map<String, String> map, String key, JsonElement element) {
			if(element.isJsonPrimitive()) {
				String s = element.getAsString().toLowerCase();
				map.put(key, s);
			}else if(element.isJsonObject()) {
				for(Map.Entry<String, JsonElement> subEntry : element.getAsJsonObject().entrySet()) {
					subTree(map, subEntry.getKey(), subEntry.getValue());
				}
			}
		}

		public static boolean toJsonFile(String path, JsonObject obj) {
			try {
				File file = new File(path);
				if(!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				if(file.exists()) {
					file.delete();
				}

				file.createNewFile();
				Writer write = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
				write.write(formatJson(obj.toString()));
				write.flush();
				write.close();
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		private static String formatJson(String json) {
			StringBuilder result = new StringBuilder();
			int length = json.length();
			int number = 0;
			char key = 0;

			for(int i = 0; i < length; i++) {
				key = json.charAt(i);

				if((key == '[') || (key == '{')) {
					result.append(key);

					result.append('\n');

					number++;
					result.append(indent(number));

					continue;
				}

				if(key == ':') {
					if(json.charAt(i - 1) == '"') {
						result.append(key);
						result.append(' ');
						continue;
					}
				}

				if((key == ']') || (key == '}')) {
					result.append('\n');

					number--;
					result.append(indent(number));

					result.append(key);
					continue;
				}

				if((key == ',')) {
					result.append(key);
					result.append('\n');
					result.append(indent(number));
					continue;
				}
				result.append(key);
			}

			result.append('\n');
			return result.toString();
		}

		private static String indent(int number) {
			StringBuilder result = new StringBuilder();
			for(int i = 0; i < number; i++) {
				result.append("	");
			}
			return result.toString();
		}

		public static String downLoadFile(String url, String fileName, String fileDir, String exName) {
			String method = "GET";
			File saveFilePath = new File(fileDir);

			if(!saveFilePath.exists()) {
				saveFilePath.mkdirs();
			}

			FileOutputStream fileOut = null;
			HttpURLConnection conn = null;
			InputStream inputStream = null;
			String savePath = null;

			try {
				URL httpUrl = new URL(url);
				conn = (HttpURLConnection) httpUrl.openConnection();

				conn.setRequestMethod(method);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.connect();
				inputStream = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(inputStream);

				if(!fileDir.endsWith("/")) {
					fileDir += "/";
				}

				savePath = fileDir + fileName + "." + exName;
				fileOut = new FileOutputStream(savePath);
				BufferedOutputStream bos = new BufferedOutputStream(fileOut);

				byte[] buf = new byte[8192];
				int length = bis.read(buf);

				while(length != -1) {
					bos.write(buf, 0, length);
					length = bis.read(buf);
				}
				bos.close();
				bis.close();
				conn.disconnect();
			}catch(Exception e) {
				e.printStackTrace();
				e.fillInStackTrace();
			}
			return fileName + "." + exName;
		}
	}
}
