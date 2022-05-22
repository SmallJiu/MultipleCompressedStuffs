package cat.jiu.mcs.util.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.core.util.JiuUtils;

import cat.jiu.mcs.util.type.CustomStuffType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InitChangeBlock {
	public static void init(Map<Integer, CustomStuffType.ChangeBlockType> typeMap, JsonObject obj, String name, int[] metas) throws NumberFormatException {
		if(obj.has("drops")) {
			List<Integer> loadMeta = new ArrayList<>();
			for(Map.Entry<String, JsonElement> dropobj : obj.getAsJsonObject("drops").entrySet()) {
				JsonElement dropE = dropobj.getValue();
				List<ItemStack> drop = new ArrayList<>();
				int meta = Integer.parseInt(dropobj.getKey());
				boolean canDrop = true;
				int[] time = new int[3];
				loadMeta.add(meta);

				if(dropE instanceof JsonObject) {
					JsonObject dropo = (JsonObject) dropE;
					if(dropo.has("time")) {
						time = parseTime(dropo.get("time").getAsString());
					}else {
						time = parseTime(obj.get("time").getAsString());
					}
					if(dropo.has("item")) {
						JsonArray dropa = dropo.getAsJsonArray("item");
						for(int k = 0; k < dropa.size(); ++k) {
							drop.add(JiuUtils.item.toStack(dropa.get(k)));
						}
					}else {
						drop.add(new ItemStack(Item.getByNameOrId("mcs:" + name), 1, meta));
					}

					if(dropo.has("canDrop")) {
						canDrop = dropo.get("canDrop").getAsBoolean();
					}else if(obj.has("canDrop")) {
						canDrop = obj.get("canDrop").getAsBoolean();
					}
				}else if(dropE instanceof JsonArray) {
					JsonArray dropa = (JsonArray) dropE;
					time = parseTime(dropa.get(0).getAsString());

					if(dropa.size() == 1) {
						if(MCSResources.BLOCKS_NAME.contains(name)) {
							drop.add(new ItemStack(Item.getByNameOrId("mcs:" + name), 1, meta));
						}else {
							drop.add(new ItemStack(Item.getByNameOrId(name), 1, meta));
						}
					}else if(dropa.size() == 2) {
						drop.add(JiuUtils.item.toStack(dropa.get(1)));
					}else {
						for(int k = 1; k < dropa.size(); ++k) {
							drop.add(JiuUtils.item.toStack(dropa.get(k)));
						}
					}
				}else {
					drop.add(JiuUtils.item.toStack(dropE));
				}

				typeMap.put(meta, new CustomStuffType.ChangeBlockType(drop, time, canDrop));
			}

			int[] time = parseTime(obj.get("time").getAsString());
			boolean canDrop = true;
			if(obj.has("canDrop")) {
				canDrop = obj.get("canDrop").getAsBoolean();
			}
			for(int meta : metas) {
				if(!loadMeta.contains(meta)) {
					List<ItemStack> drop = new ArrayList<>();
					if(MCSResources.BLOCKS_NAME.contains(name)) {
						drop.add(new ItemStack(Item.getByNameOrId("mcs:" + name), 1, meta));
					}else {
						drop.add(new ItemStack(Item.getByNameOrId(name), 1, meta));
					}
					typeMap.put(meta, new CustomStuffType.ChangeBlockType(drop, time, canDrop));
				}
			}
		}else {
			int[] time = parseTime(obj.get("time").getAsString());
			boolean canDrop = true;
			if(obj.has("canDrop")) {
				canDrop = obj.get("canDrop").getAsBoolean();
			}
			for(int meta : metas) {
				List<ItemStack> drop = new ArrayList<>();
				if(MCSResources.BLOCKS_NAME.contains(name)) {
					drop.add(new ItemStack(Item.getByNameOrId("mcs:" + name), 1, meta));
				}else {
					drop.add(new ItemStack(Item.getByNameOrId(name), 1, meta));
				}
				typeMap.put(meta, new CustomStuffType.ChangeBlockType(drop, time, canDrop));
			}
		}
	}

	private static int[] parseTime(String times) throws NumberFormatException {
		String[] name = JiuUtils.other.custemSplitString(times, ":");
		int tick_temp = Integer.parseInt(name[2]);
		int tick = tick_temp > 20 ? 19 : tick_temp;
		int s = Integer.parseInt(name[1]) > 60 ? 59 : Integer.parseInt(name[1]);
		int m = Integer.parseInt(name[0]);
		int[] time = {tick, s, m};

		return time;
	}
}
