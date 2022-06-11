package cat.jiu.mcs.util.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.client.model.block.*;
import cat.jiu.mcs.util.client.model.item.*;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLFileResourcePack;
import net.minecraftforge.fml.client.FMLFolderResourcePack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class CompressedStuffResourcePack implements IResourcePack {
	protected final IResourcePack modPack;
	public static final HashMap<String, JsonElement> customTextures = Maps.newHashMap();
	
	public CompressedStuffResourcePack() {
		ModContainer mod = Loader.instance().getIndexedModList().get(MCS.MODID);
		if(mod.getSource().isDirectory()) {
			// for dev
			this.modPack = new FMLFolderResourcePack(mod);
		}else {
			this.modPack = new FMLFileResourcePack(mod);
		}
	}
	
	@Override
	public InputStream getInputStream(ResourceLocation location) throws IOException {
		long i = System.currentTimeMillis();
		String resource = String.format("%s/%s/%s", "assets", location.getResourceDomain(), location.getResourcePath());
		
		if(this.modPack.resourceExists(location)) {
			InputStream steam = this.modPack.getInputStream(location);
			MCS.startmodel += System.currentTimeMillis() - i;
			return steam;
		}
		
		if(location.toString().equals("mcs:models/block/compressed_torcherino.json")) {
			InputStream steam = this.getClass().getResourceAsStream("/" + resource);
			MCS.startmodel += System.currentTimeMillis() - i;
			return steam;
		}else if(resource.contains("blockstates")) {
			ArrayList<String> list = Lists.newArrayList(Lists.newArrayList(resource.split("/")));
			return new BlockStateJson(this.getOwnerMod(resource, list), this.getResourceName(list), this.getHasState(resource), i).toStream();
		}else if(resource.contains("models/block")) {
			ArrayList<String> list = Lists.newArrayList(Lists.newArrayList(resource.split("/")));
			JsonElement originalTexture = this.getOriginalTexture(resource, list);
			if(originalTexture != null) {
				String owner = this.getOwnerMod(resource, list);
				String name = this.getResourceName(list);
				int meta = this.getMeta(list);
				
				if(this.getHasState(resource)) {
					if(this.isSideModel(originalTexture)) {
						return new SideBlockModel(originalTexture.getAsJsonObject(), owner, name, meta, true, i).toStream();
					}else if(this.isOverlayModel(originalTexture)) {
						return new OverlayBlockModel(originalTexture, owner, name, meta, true, i).toStream();
					}else if(this.isItemBlock(resource)) {
						return new ItemBlockModel(owner, name, meta, true, i).toStream();
					}else {
						return new BlockHasModel(originalTexture, owner, name, meta, i).toStream();
					}
				}else if(this.isSideModel(originalTexture)) {
					return new SideBlockModel(originalTexture.getAsJsonObject(), owner, name, meta, false, i).toStream();
				}else if(this.isOverlayModel(originalTexture)) {
					return new OverlayBlockModel(originalTexture, owner, name, meta, false, i).toStream();
				}else if(this.isItemBlock(resource)) {
					return new ItemBlockModel(owner, name, meta, false, i).toStream();
				}else {
					return new NormalBlockModel(originalTexture, owner, name, meta, i).toStream();
				}
			}
		}else if(this.isItemBlock(resource)) {
			ArrayList<String> list = Lists.newArrayList(Lists.newArrayList(resource.split("/")));
			String owner = this.getOwnerMod(resource, list);
			String name = this.getResourceName(list);
			int meta = this.getMeta(list);
			if(this.getHasState(resource)) {
				return new ItemBlockModel(owner, name, meta, true, i).toStream();
			}else {
				return new ItemBlockModel(owner, name, meta, false, i).toStream();
			}
		}else if(resource.contains("models/item") || resource.contains("item/food")) {
			ArrayList<String> list = Lists.newArrayList(Lists.newArrayList(resource.split("/")));
			JsonElement originalTexture = this.getOriginalTexture(resource, list);
			if(originalTexture != null) {
				String owner = this.getOwnerMod(resource, list);
				String name = this.getResourceName(list);
				int meta = this.getMeta(list);
				
				if(this.isTool(resource)) {
					return new ToolItemModel(originalTexture, owner, name, meta, i).toStream();
				}else {
					return new NormalItemModel(originalTexture, owner, name, meta, i).toStream();
				}
			}
		}
		
		return modPack.getInputStream(location);
	}
	
	protected JsonElement getOriginalTexture(String resource, List<String> list) {
		JsonObject file = MCS.getTextures();
		String owner = this.getOwnerMod(resource, list);
		boolean ItemOrBlock = resource.contains("models/item");
		String has = this.getHasState(resource) ? "has" : "normal";
		String name = this.getResourceName(list);
		
		if(customTextures.containsKey(name)) {
			return customTextures.get(name);
		}
		
		if(file.has(owner)) {
			JsonObject mod = file.getAsJsonObject(owner);
			if(ItemOrBlock) {
				JsonObject items = mod.getAsJsonObject("item");
				
				if(this.isTool(resource)) {
					JsonObject tools = items.getAsJsonObject("tools").getAsJsonObject(this.getToolClass(list));
					if(tools.has(name)) {
						return tools.get(name);
					}
				}else if(this.isFood(resource)) {
					JsonObject foods = items.getAsJsonObject("foods");
					if(foods.has(name)) {
						return foods.get(name);
					}
				}else {
					return items.get(name);
				}
			}else {
				JsonObject blocks = mod.getAsJsonObject("block");
				if(blocks.has(has)) {
					JsonObject hasObj = blocks.getAsJsonObject(has);
					if(hasObj.has(name)) {
						return hasObj.get(name);
					}
				}
			}
		}
		return null;
	}
	
	protected boolean isItemBlock(String resource) {
		return resource.contains("models/item") && (resource.contains("block/has") || resource.contains("block/normal"));
	}
	
	protected boolean isOverlayModel(JsonElement originalTexture) {
		if(originalTexture.isJsonObject()) {
			return originalTexture.getAsJsonObject().has("all");
		}
		return false;
	}
	
	protected boolean isSideModel(JsonElement originalTexture) {
		if(originalTexture.isJsonObject()) {
			JsonObject obj = originalTexture.getAsJsonObject();
			return obj.has("top") || obj.has("down") || obj.has("side");
		}
		return false;
	}
	
	protected String getToolClass(List<String> list) {
		int index = this.startIndexOf(list, "tools");
		if(index == -1) {
			return "sword";
		}
		return list.get(index + 1);
	}
	
	protected boolean isFood(String resource) {
		return resource.contains("item/food");
	}
	
	protected boolean isTool(String resource) {
		return resource.contains("item/tools");
	}
	
	protected String getOwnerMod(String resource, List<String> list) {
		if(resource.contains("blockstates")) {
			return list.get(3);
		}else if(resource.contains("models/item") || resource.contains("models/block")) {
			return list.get(4);
		}
		
		return "minecraft";
	}
	
	protected boolean getHasState(String resource) {
		return resource.contains("has/compressed");
	}
	
	protected int getMeta(List<String> list) {
        return Integer.parseInt(this.getLast(list).split("\\.")[1]);
	}
	
	protected String getResourceName(List<String> list) {
		if(list.size() >= 0) {
			String str = this.getLast(list);
			if(str != null) {
		        return str.split("\\.")[0];
			}
		}
        return "";
	}
	
	protected <T> T getLast(List<T> list) {
		if(list.size() > 0) {
			return list.get(list.size()-1);
		}else if(list.size() == 0) {
			return list.get(0);
		}
		return null;
	}
	
	protected int startIndexOf(List<String> list, String o) {
		if (o == null) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i) == null) return i;
			}
        }else {
            for (int i = 0; i < list.size(); i++)
                if(o.equalsIgnoreCase(list.get(i))) return i;
        }
        return -1;
	}
	
	@Override
	public boolean resourceExists(ResourceLocation location) {
		long i = System.currentTimeMillis();
		if(this.modPack.resourceExists(location)) {
			MCS.startmodel += System.currentTimeMillis() - i;
			return true;
		}
		
		String resource = String.format("%s/%s/%s", "assets", location.getResourceDomain(), location.getResourcePath());
		if(resource.startsWith("assets/mcs/blockstates/") || resource.startsWith("assets/mcs/models/")) {
			if(resource.endsWith(".json.mcmeta")) {
				MCS.startmodel += System.currentTimeMillis() - i;
				return false;
			}
			String name = this.getResourceName(Lists.newArrayList(resource.split("/")));
            if(MCSResources.STUFF_NAME.contains(name)) {
    			MCS.startmodel += System.currentTimeMillis() - i;
            	return true;
            }
		}
		
		return false;
	}
	
	@Override
	public Set<String> getResourceDomains() {
		return Sets.newHashSet("mcs");
	}
	
	@Override
	public BufferedImage getPackImage() throws IOException {
		return modPack.getPackImage();
	}
	
	@Override
	public String getPackName() {
		return "MCS Models";
	}

	@Override
	public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
		return this.modPack.getPackMetadata(metadataSerializer, metadataSectionName);
	}
}
