package cat.jiu.mcs.tools;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonToModel {
	static int count = -1;

	public static void main(String[] args) {
		JFrame f = new JFrame("");
		f.setLayout(null);

		JTextField file = new JTextField("D:/Minecraft_MDK/MultipleCompressedStuffs-1.12.2-14.23.5.2847-eclipse/src/main/resources/assets/mcs/textures/mode_textures.json", 9999999);
		file.setBounds(5, 5, 99999999, 50);
		file.setFont(new Font(null, 0, 20));

		JButton start = new JButton("Start");
		start.setBounds(5, 60, 85, 30);

		JTextField log = new JTextField(200);
		log.setBounds(90, 60, 500, 35);
		log.setFont(new Font(null, 0, 15));

		start.addActionListener(e -> {
			if(file.getText().isEmpty())
				return;
			File json = new File(file.getText());
			if(!json.exists())
				return;

			count = -1;
			try {
				toModel(json);
			}catch(JsonIOException e1) {
				e1.printStackTrace();
			}catch(JsonSyntaxException e1) {
				e1.printStackTrace();
			}catch(FileNotFoundException e1) {
				e1.printStackTrace();
			}catch(IOException e1) {
				e1.printStackTrace();
			}

			if(count == -1) {
				log.setText("Fail");
			}else {
				log.setText("Success, create " + count + "Model");
			}
		});

		f.getContentPane().add(file);
		f.getContentPane().add(start);
		f.getContentPane().add(log);
		f.setBounds(0, 0, 200, 200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	private static void toModel(File file) throws JsonIOException, JsonSyntaxException, IOException {
		JsonElement e = new JsonParser().parse(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
		if(e.isJsonObject()) {
			JsonObject obj = (JsonObject) e;
			for(Entry<String, JsonElement> entrys : obj.entrySet()) {
				String modid = entrys.getKey();
				JsonElement bois = entrys.getValue();
				if(bois.isJsonObject()) {
					for(Entry<String, JsonElement> boisEntry : bois.getAsJsonObject().entrySet()) {
						String boi = boisEntry.getKey();
						JsonElement item = boisEntry.getValue();
						if(item.isJsonObject()) {
							if(boi.equals("item")) {
								for(Entry<String, JsonElement> items : item.getAsJsonObject().entrySet()) {
									String name = items.getKey();
									JsonElement entry = items.getValue();
									if(entry.isJsonPrimitive()) {
										// modid, boi tools, name, texture, isHas
										ModelWriter.main(new String[]{modid, boi, null, name, entry.getAsString(), ""});
										count += 17;
									}else if(entry.isJsonObject()) {
										if(name.equals("tool")) {
											for(Entry<String, JsonElement> tools : entry.getAsJsonObject().entrySet()) {
												String tool = tools.getKey();
												JsonElement toolsEntry = tools.getValue();
												if(toolsEntry.isJsonObject()) {
													for(Entry<String, JsonElement> toolEntry : toolsEntry.getAsJsonObject().entrySet()) {
														String toolName = toolEntry.getKey();
														JsonElement toolTexture = toolEntry.getValue();
														if(toolTexture.isJsonPrimitive()) {
															// // modid, boi tools, name, texture, isHas
															ModelWriter.main(new String[]{modid, boi, tool, toolName, toolTexture.getAsString(), "not"});
															count += 17;
														}
													}
												}
											}
										}
									}
								}
							}else if(boi.equals("block")) {
								for(Entry<String, JsonElement> blocks : item.getAsJsonObject().entrySet()) {
									String isHas = blocks.getKey();
									JsonElement block = blocks.getValue();
									if(block.isJsonObject()) {
										for(Entry<String, JsonElement> blockEntry : block.getAsJsonObject().entrySet()) {
											String blockName = blockEntry.getKey();
											JsonElement blockTexture = blockEntry.getValue();
											if(blockTexture.isJsonPrimitive()) {
												// System.out.println(blockTexture.getAsString());
												// // modid, boi tools, name, texture, isHas
												ModelWriter.main(new String[]{modid, boi, null, blockName, blockTexture.getAsString(), isHas});
												count += 16;
											}else if(blockTexture.isJsonObject()) {
												ModelWriter.main(new String[]{modid, boi, null, blockName, "TempTexture", isHas});
												JsonObject sides = (JsonObject) blockTexture;
												
												JsonElement top = sides.get("top");
												JsonElement side = sides.get("side");
												JsonElement down = sides.get("down");
												
												if(top.isJsonPrimitive() && side.isJsonPrimitive() && down.isJsonPrimitive()) {
													for(int meta = 0; meta < 16; meta++) {
														ModelWriter.writeFaceBlockModel(modid, blockName, isHas.equals("has"), meta, top.getAsString(), side.getAsString(), down.getAsString(), false);
													}
												}
												count += 16;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
