package cat.jiu.mcs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		while(true) {
			System.out.println("===============================");
			System.out.println("	block or item or stop	   ");
			System.out.println("===============================");
			Scanner in = new Scanner(System.in);
			String s0 = in.nextLine();
			
			if(s0.equals("block")) {
				System.out.println("===============================");
				System.out.println("	is has block?			   ");
				System.out.println("===============================");
				String s1 = in.nextLine();
				
				System.out.println("===============================");
				System.out.println("	the block modid			   ");
				System.out.println("===============================");
				String modid = in.nextLine();
				
				System.out.println("===============================");
				System.out.println("	the block name			   ");
				System.out.println("===============================");
				String name = in.nextLine();
				
				System.out.println("===============================");
				System.out.println("	the block texture ResourceLocation	   ");
				System.out.println("===============================");
				String textureSrc = in.nextLine();
				
				if(s1.equals("true") || s1.equals("yes")) {
					for(int meta = 0; meta < 16; meta++) {
						if(meta == 0) {
							writeHasBlockModel(modid, name, textureSrc, meta);
						}else {
							writeBlockModel(modid, name, textureSrc, meta);
						}
					}
					writeBlockStateModel(modid, name, textureSrc, true);
				}else {
					for(int meta = 0; meta < 16; meta++) {
						writeBlockModel(modid, name, textureSrc, meta+1);
						writeItemBlockModel(modid, name, textureSrc, meta, false);
					}
					writeBlockStateModel(modid, name, textureSrc, false);
				}
			}else if(s0.equals("item")) {
				System.out.println("===============================");
				System.out.println("	is food?			   ");
				System.out.println("===============================");
				String s1 = in.nextLine().toLowerCase();
				
				boolean isFood = false;
				if(s1.equals("true") || s1.equals("yes") || s1.equals("t")) {
					isFood = true;
				}
				
				System.out.println("===============================");
				System.out.println("	the item modid			   ");
				System.out.println("===============================");
				String modid = in.nextLine();
				
				System.out.println("===============================");
				System.out.println("	the item name			   ");
				System.out.println("===============================");
				String name = in.nextLine();
				
				System.out.println("===============================");
				System.out.println("	the item texture ResourceLocation	   ");
				System.out.println("===============================");
				String textureSrc = in.nextLine();
				
				for(int i = 0; i < 16; i++) {
					writeItemModel(modid, name, textureSrc, i, isFood);
				}
				writeItemModel(modid, name, textureSrc, Short.MAX_VALUE, isFood);
			}else if(s0.equals("stop")) {
				System.exit(0);
			}
		}
	}
	
	private static void writeItemModel(String modid, String itemName, String itemTextureSrc, int meta, boolean isFood) throws IOException {
		String food = isFood ? "food" : "normal";
		String path = "run/main/mcs/models/item/" + modid + "/item/" + food + "/" + itemName;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		File file = new File(path + "/" + itemName + "." + meta + ".json");
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n\"parent\":\"item/generated\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"layer0\": \"" + modid +  ":" + itemTextureSrc +  "\",");
		if(meta == 65535) {
			out.write("\n		\"layer1\": \"mcs:items/infinity\"");
		}else {
			out.write("\n		\"layer1\": \"mcs:items/compressed_" + (meta+1) + "\"");
		}
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}
	
	private static void writeBlockModel(String modid, String name, String blockTextureSrc, int meta) throws IOException {
		String path = "run/main/mcs/models/block/" + modid + "/block/normal/" + name;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		File file = new File(path + "/" + name + "." + (meta-1) + ".json");
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n\"parent\": \"mcs:block/overlay_model\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"all\": \"" + modid +  ":" + blockTextureSrc +  "\",");
		out.write("\n		\"overlay\": \"mcs:blocks/compressed_" + meta + "\"");
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}
	
	private static void writeHasBlockModel(String modid, String name, String blockTextureSrc, int meta) throws IOException {
		String path = "run/main/mcs/models/block/" + modid + "/block/has/" + name;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		File file = new File(path + "/" + name + "." + (meta-1) + ".json");
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n\"parent\":\"mcs:block/normal_model\",");
		out.write("\n	\"textures\": {");
		out.write("\n		\"all\": \"mcs:blocks/un/" + blockTextureSrc +  "\"");
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}
	
	private static void writeItemBlockModel(String modid, String name, String blockTextureSrc, int meta, boolean isHas) throws IOException {
		String has = isHas ? "has" : "normal";
		String path = "run/main/mcs/models/item/" + modid + "/block/" + has + "/" + name;
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		File file = new File(path + "/" + name + "." + meta + ".json");
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n   \"parent\": \"mcs:block/" + modid + "/" + has + "/" + name + "/" + name + "." + meta + "\"");
		out.write("\n}");
		out.close();
		
	}
	
	private static void writeBlockStateModel(String modid, String name, String blockTextureSrc, boolean isHas) throws IOException {
		String has = isHas ? "has" : "normal";
		String path = "run/main/mcs/blockstates";
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		File file = new File(path + "/" + name + ".json");
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("{");
		out.write("\n \"variants\": {");
		out.write("\n	\"level=level_0\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".0\"");
		out.write("\n		},");
		out.write("\n	\"level=level_1\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".1\"");
		out.write("\n		},");
		out.write("\n	\"level=level_2\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".2\"");
		out.write("\n		},");
		out.write("\n	\"level=level_3\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".3\"");
		out.write("\n		},");
		out.write("\n	\"level=level_4\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".4\"");
		out.write("\n		},");
		out.write("\n	\"level=level_5\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".5\"");
		out.write("\n		},");
		out.write("\n	\"level=level_6\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".6\"");
		out.write("\n		},");
		out.write("\n	\"level=level_7\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".7\"");
		out.write("\n		},");
		out.write("\n	\"level=level_8\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".8\"");
		out.write("\n		},");
		out.write("\n	\"level=level_9\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".9\"");
		out.write("\n		},");
		out.write("\n	\"level=level_10\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".10\"");
		out.write("\n		},");
		out.write("\n	\"level=level_11\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".11\"");
		out.write("\n		},");
		out.write("\n	\"level=level_12\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".12\"");
		out.write("\n		},");
		out.write("\n	\"level=level_13\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".13\"");
		out.write("\n		},");
		out.write("\n	\"level=level_14\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".14\"");
		out.write("\n		},");
		out.write("\n	\"level=level_15\": {");
		out.write("\n		\"model\": \"mcs:" + modid + "/" + has + "/" + name +"/" + name + ".15\"");
		out.write("\n		}");
		out.write("\n	}");
		out.write("\n}");
		out.close();
	}
}
