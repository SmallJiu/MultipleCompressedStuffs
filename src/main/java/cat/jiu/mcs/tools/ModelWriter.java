package cat.jiu.mcs.tools;

import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ModelWriter {
	public static void main(String[] args) throws IOException {
		if(args.length >= 4) {
			String modid = args[0];
			String bois = args[1];
			String tools = args[2];
			String name = args[3];
			String texture = args[4];
			String isHas = args[5];
			int boi = -1;
			if(bois.toLowerCase().equals("item")) {
				boi = 0;
			}else if(bois.toLowerCase().equals("block")) {
				boi = 1;
			}
			if(boi == -1) {
				System.out.println("Error: Unknown Entry: " + bois);
				throw new RuntimeException();
			}
			execute(boi, isHas, tools, modid, name, texture, new String[5]);
		}else {
			JFrame f = new JFrame("ModelWriter");
			f.setBounds(0, 0, 285, 400);
			f.setLayout(null);
			Container con = f.getContentPane();

			JComboBox<String> boi = new JComboBox<String>(new String[]{"item", "block"});
			JComboBox<String> state = new JComboBox<String>(new String[]{"normal", "food / has", "tool"});
			JComboBox<String> modids = new JComboBox<String>(new String[]{"minecraft", "thermalfoundation", "draconicevolution", "avaritia", "ic2", "appliedenergistics2", "enderio", "projecte", "environmentaltech", "tconstruct", "botania"});
			JComboBox<String> tools = new JComboBox<String>(new String[]{"sword", "pickaxe", "shovel", "axe", "hoe"});

			JLabel savePath = new JLabel("SavePath: " + new File("run/main/mcs").getCanonicalPath() + "\\");
			savePath.setFont(new Font(null, 0, 15));

			JTextField modid = new JTextField();
			modid.setFont(new Font(null, 0, 24));

			JTextField name = new JTextField();
			name.setFont(new Font(null, 0, 24));

			JTextField modelDir = new JTextField();
			modelDir.setFont(new Font(null, 0, 24));

			JTextField modelRes = new JTextField();
			modelRes.setFont(new Font(null, 0, 24));

			JButton start = new JButton("Start");
			JTextField finish = new JTextField();
			finish.setFont(new Font(null, 0, 20));

			start.addActionListener(event -> {
				try {
					boolean block = boi.getSelectedItem().equals("block");
					String dir = modelDir.getText();
					if(!dir.isEmpty()) {
						dir = (modelDir.getText().endsWith("/") ? modelDir.getText() : modelDir.getText() + "/");
					}
					String textureSrc = dir + modelRes.getText();
					if(textureSrc.contains("\\")) {
						String[] src = textureSrc.split("\\\\");
						String s = "";
						for(int i = 0; i < src.length; i++) {
							String str = src[i];
							s += str;
							if(i != src.length - 1) {
								s += "/";
							}
						}
						textureSrc = s;
					}
					String actModid = modid.getText();
					if(actModid == null || actModid.isEmpty()) {
						actModid = (String) modids.getSelectedItem();
					}

					boolean lag = false;
					long time = System.currentTimeMillis();
					String[] failMsg = {""};

					if(block) {
						lag = executeBlock(state, actModid, name, textureSrc, failMsg);
						time = System.currentTimeMillis() - time;
					}else {
						lag = executeItem(state, tools, actModid, name, textureSrc, failMsg);
						time = System.currentTimeMillis() - time;
					}

					if(lag) {
						name.setText("");
						String res = "";
						String[] ress = textureSrc.split("/");
						for(int i = 0; i < ress.length - 1; i++) {
							String str = ress[i];
							res += str + "/";
						}

						modelRes.setText("");
						modelDir.setText(res);

						finish.setText("Finish. (took " + time + " ms)");
					}else {
						finish.setText("Fail. " + failMsg[0]);
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
			});

			savePath.setBounds(100, 5, 999999, 30);
			con.add(savePath);

			JLabel boiText = new JLabel("Block / Item");
			boiText.setBounds(10, 5, 100, 30);
			con.add(boiText);

			boi.setBounds(10, 35, 80, 20);
			con.add(boi);

			JLabel stateText = new JLabel("State");
			stateText.setBounds(10, 55, 100, 30);
			con.add(stateText);

			state.setBounds(10, 80, 100, 20);
			con.add(state);

			tools.setBounds(120, 80, 70, 20);
			con.add(tools);

			JLabel ModIDText = new JLabel("ModID");
			ModIDText.setBounds(10, 97, 100, 30);
			con.add(ModIDText);

			modids.setBounds(10, 120, f.getWidth() - 30, 25);
			con.add(modids);
			modid.setBounds(10, 152, 99999, 30);
			con.add(modid);

			JLabel NameText = new JLabel("Name");
			NameText.setBounds(10, 175, 100, 30);
			con.add(NameText);

			name.setBounds(10, 200, 99999, 30);
			con.add(name);

			JLabel ModelResText = new JLabel("Textures ResourceLocation");
			ModelResText.setBounds(10, 225, 200, 30);
			con.add(ModelResText);

			JLabel ModelDirText = new JLabel("Dir");
			ModelDirText.setBounds(5, 250, 200, 30);
			con.add(ModelDirText);

			modelDir.setBounds(40, 250, 99999, 30);
			con.add(modelDir);

			JLabel ModelNameText = new JLabel("Name");
			ModelNameText.setBounds(5, 280, 200, 30);
			con.add(ModelNameText);

			modelRes.setBounds(40, 280, 99999, 30);
			con.add(modelRes);

			start.setBounds(10, 320, 70, 30);
			con.add(start);

			finish.setBounds(85, 320, f.getWidth() - 105, 30);
			con.add(finish);

			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
		}
	}

	static boolean execute(int boi, String state, String tool, String modid, String name, String textureSrc, String[] failMsg) throws IOException {
		if(boi == 0) {
			return executeItem(state, tool, modid, name, textureSrc);
		}else if(boi == 1) {
			return executeBlock(state, modid, name, textureSrc, false);
		}else {
			return false;
		}
	}

	static boolean executeBlock(String state, String modid, String name, String textureSrc, boolean ca) throws IOException {
		boolean isHas = state.toLowerCase().equals("has");
		if(modid.isEmpty() || name.isEmpty() || textureSrc.isEmpty()) {
			return false;
		}

		if(ca) {
			if(!textureSrc.startsWith("blocks")) {
				textureSrc = "blocks/" + textureSrc;
			}else if(!textureSrc.startsWith("blocks/")) {
				textureSrc = "blocks/" + textureSrc;
			}
		}

		System.out.println(modid + ": " + name);

		for(int meta = 0; meta < 16; meta++) {
			if(isHas) {
				if(meta == 0) {
					writeHasBlockModel(modid, name, textureSrc, ca);
				}else {
					writeBlockModel(modid, name, textureSrc, meta, isHas, ca);
				}
			}else {
				writeBlockModel(modid, name, textureSrc, meta + 1, isHas, ca);
			}
			writeItemBlockModel(modid, name, meta, isHas);
			if(meta == 15) {
				writeBlockStateModel(modid, name, isHas);
			}
		}
		return true;
	}

	static boolean executeBlock(JComboBox<String> state, String modid, JTextField name, String textureSrc, String[] failMsg) throws IOException {
		return executeBlock((String) state.getSelectedItem(), modid, name.getText(), textureSrc, true);
	}

	static boolean executeItem(String state, String tools, String modid, String name, String textureSrc) throws IOException {
		if(modid.isEmpty() || name.isEmpty() || textureSrc.isEmpty()) {
			return false;
		}

		System.out.println(modid + ": " + name);

		boolean isFood = state.toLowerCase().equals("food");
		if(tools != null) {
			for(int i = 0; i < 16; i++) {
				writeItemToolModel(tools, modid, name, textureSrc, i, false);
			}
			writeItemToolModel(tools, modid, name, textureSrc, Short.MAX_VALUE, false);
		}else {
			for(int i = 0; i < 16; i++) {
				writeItemModel(modid, name, textureSrc, i, isFood, false);
			}
			writeItemModel(modid, name, textureSrc, Short.MAX_VALUE, isFood, false);
		}

		return true;
	}

	static boolean executeItem(JComboBox<String> state, JComboBox<String> tools, String modid, JTextField name, String textureSrc, String[] failMsg) throws IOException {
		if(modid.isEmpty() || name.getText().isEmpty() || textureSrc.isEmpty()) {
			failMsg[0] = "Name or Texture is Empty";
			return false;
		}

		if(!textureSrc.startsWith("items")) {
			textureSrc = "items/" + textureSrc;
		}else if(!textureSrc.startsWith("items/")) {
			textureSrc = "items/" + textureSrc;
		}

		System.out.println(modid + ": " + name);

		boolean isFood = state.getSelectedIndex() == 1;
		if(state.getSelectedIndex() == 2) {
			for(int i = 0; i < 16; i++) {
				writeItemToolModel(tools, modid, name.getText(), textureSrc, i);
			}
			writeItemToolModel(tools, modid, name.getText(), textureSrc, Short.MAX_VALUE);
		}else {
			for(int i = 0; i < 16; i++) {
				writeItemModel(modid, name.getText(), textureSrc, i, isFood, true);
			}
			writeItemModel(modid, name.getText(), textureSrc, Short.MAX_VALUE, isFood, true);
		}

		return true;
	}

	static void writeItemModel(String modid, String itemName, String itemTextureSrc, int meta, boolean isFood, boolean ca) throws IOException {
		String food = isFood ? "food" : "normal";
		String path = "run/main/mcs/models/item/" + modid + "/item/" + food + "/" + itemName;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		File file = new File(path + "/" + itemName + "." + (meta == Short.MAX_VALUE ? Short.MAX_VALUE - 1 : meta) + ".json");

		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();

		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n	\"parent\":\"item/generated\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"layer0\": \"" + (ca ? modid + ":" : "") + itemTextureSrc + "\",");
		if(meta == Short.MAX_VALUE) {
			out.write("\n		\"layer1\": \"mcs:items/infinity\"");
		}else {
			out.write("\n		\"layer1\": \"mcs:items/compressed_" + (meta + 1) + "\"");
		}
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}

	static void writeItemToolModel(String tools, String modid, String itemName, String itemTextureSrc, int meta, boolean ca) throws IOException {
		String path = "run/main/mcs/models/item/" + modid + "/item/tool/" + tools + "/" + itemName;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		File file = new File(path + "/" + itemName + "." + (meta == Short.MAX_VALUE ? Short.MAX_VALUE - 1 : meta) + ".json");

		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();

		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n	\"parent\":\"item/handheld\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"layer0\": \"" + (ca ? modid + ":" : "") + itemTextureSrc + "\",");
		if(meta == Short.MAX_VALUE) {
			out.write("\n		\"layer1\": \"mcs:items/infinity\"");
		}else {
			out.write("\n		\"layer1\": \"mcs:items/compressed_" + (meta + 1) + "\"");
		}
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}

	static void writeItemToolModel(JComboBox<String> tools, String modid, String itemName, String itemTextureSrc, int meta) throws IOException {
		writeItemToolModel((String) tools.getSelectedItem(), modid, itemName, itemTextureSrc, meta, true);
	}
	
	static void writeHasFaceBlockModel(String modid, String name, boolean isHas, int meta, String up, String side, String down, boolean ca) throws IOException {
		String path = "run/main/mcs/models/block/" + modid + "/" + (isHas ? "has" : "normal") + "/" + name;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		File file = new File(path + "/" + name + "." + (isHas ? meta : (meta - 1)) + ".json");

		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n	\"parent\": \"block/block\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"top\": \"" + (ca ? modid + ":" : "") + up + "\",");
		out.write("\n		\"down\": \"" + (ca ? modid + ":" : "") + down + "\",");
		out.write("\n		\"side\": \"" + (ca ? modid + ":" : "") + side + "\"");
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}
	
	static void writeOverlayModel(String modid, String name, boolean isHas, int meta, String all, List<String> overlays, boolean ca) throws IOException {
		String path = "run/main/mcs/models/block/" + modid + "/" + (isHas ? "has" : "normal") + "/" + name;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		File file = new File(path + "/" + name + "." + meta + ".json");

		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		String faModel = isHas && meta == 0 ? "mcs:block/overlay_model" : "mcs:block/overlay_model_" + (overlays.size()+1);
		out.write("{");
		out.write("\n	\"parent\": \"" + faModel + "\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"all\": \"" + (ca ? modid + ":" : "") + all + "\",");
		if(isHas && meta == 0) {
			out.write("\n		\"overlay\": \"" + overlays.get(0) + "\"");
		}else {
			for(int i = 0; i < overlays.size(); i++) {
				out.write("\n		\"overlay_" + (i+1) + "\": \"" + (ca ? modid + ":" : "") + overlays.get(i) + "\",");
			}
			out.write("\n		\"overlay_" + (overlays.size()+1) + "\": \"mcs:blocks/compressed_" + (isHas ? meta : meta + 1) + "\"");
		}
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}
	
	static void writeFaceBlockModel(String modid, String name, boolean isHas, int meta, String up, String side, String down, boolean ca) throws IOException {
		String path = "run/main/mcs/models/block/" + modid + "/" + (isHas ? "has" : "normal") + "/" + name;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		File file = new File(path + "/" + name + "." + (isHas ? meta-1 : meta) + ".json");

		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n	\"parent\": \"mcs:block/overlay_model_side\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"top\": \"" + (ca ? modid + ":" : "") + up + "\",");
		out.write("\n		\"down\": \"" + (ca ? modid + ":" : "") + down + "\",");
		out.write("\n		\"side\": \"" + (ca ? modid + ":" : "") + side + "\",");
		out.write("\n		\"overlay\": \"mcs:blocks/compressed_" + (meta+1) + "\"");
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}

	static void writeBlockModel(String modid, String name, String blockTextureSrc, int meta, boolean isHas, boolean ca) throws IOException {
		String path = "run/main/mcs/models/block/" + modid + "/" + (isHas ? "has" : "normal") + "/" + name;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		File file = new File(path + "/" + name + "." + (isHas ? meta : (meta - 1)) + ".json");

		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n	\"parent\": \"mcs:block/overlay_model\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"all\": \"" + (ca ? modid + ":" : "") + blockTextureSrc + "\",");
		out.write("\n		\"overlay\": \"mcs:blocks/compressed_" + meta + "\"");
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}

	static void writeHasBlockModel(String modid, String name, String blockTextureSrc, boolean ca) throws IOException {
		String path = "run/main/mcs/models/block/" + modid + "/has/" + name;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		File file = new File(path + "/" + name + ".0.json");

		if(!file.exists()) {
			file.createNewFile();
		}

		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n	\"parent\":\"mcs:block/normal_model\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"all\": \"" + (ca ? "mcs:blocks/un/" + blockTextureSrc : blockTextureSrc) + "\"");
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}

	static void writeItemBlockModel(String modid, String name, int meta, boolean isHas) throws IOException {
		String has = isHas ? "has" : "normal";
		String path = "run/main/mcs/models/item/" + modid + "/block/" + has + "/" + name;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		File file = new File(path + "/" + name + "." + meta + ".json");

		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();

		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n   \"parent\": \"mcs:block/" + modid + "/" + has + "/" + name + "/" + name + "." + meta + "\"");
		out.write("\n}");
		out.close();
	}

	static void writeBlockStateModel(String modid, String name, boolean isHas) throws IOException {
		String has = isHas ? "has" : "normal";
		String path = "run/main/mcs/blockstates/" + modid + "/" + has;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		File file = new File(path + "/" + name + ".json");

		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();

		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n 	\"variants\": {");
		out.write("\n		\"level=level_0\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".0\"");
		out.write("\n		},");
		out.write("\n		\"level=level_1\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".1\"");
		out.write("\n		},");
		out.write("\n		\"level=level_2\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".2\"");
		out.write("\n		},");
		out.write("\n		\"level=level_3\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".3\"");
		out.write("\n		},");
		out.write("\n		\"level=level_4\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".4\"");
		out.write("\n		},");
		out.write("\n		\"level=level_5\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".5\"");
		out.write("\n		},");
		out.write("\n		\"level=level_6\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".6\"");
		out.write("\n		},");
		out.write("\n		\"level=level_7\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".7\"");
		out.write("\n		},");
		out.write("\n		\"level=level_8\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".8\"");
		out.write("\n		},");
		out.write("\n		\"level=level_9\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".9\"");
		out.write("\n		},");
		out.write("\n		\"level=level_10\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".10\"");
		out.write("\n		},");
		out.write("\n		\"level=level_11\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".11\"");
		out.write("\n		},");
		out.write("\n		\"level=level_12\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".12\"");
		out.write("\n		},");
		out.write("\n		\"level=level_13\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".13\"");
		out.write("\n		},");
		out.write("\n		\"level=level_14\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".14\"");
		out.write("\n		},");
		out.write("\n		\"level=level_15\": {");
		out.write("\n			\"model\": \"mcs:" + modid + "/" + has + "/" + name + "/" + name + ".15\"");
		out.write("\n		}");
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}
}
