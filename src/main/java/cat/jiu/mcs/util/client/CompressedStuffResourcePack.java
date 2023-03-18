package cat.jiu.mcs.util.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.proxy.ClientProxy;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
		
		if(resource.contains("blockstates")) {
			List<String> list = Lists.newArrayList(resource.split("/"));
			return new BlockStateModel(this.getOwnerMod(resource, list), this.getResourceName(list), this.isHasModel(resource), i).toStream();
		}else if(resource.contains("models/block")) {
			List<String> list = Lists.newArrayList(resource.split("/"));
			JsonElement originalTexture = this.getOriginalTexture(resource, list);
			if(originalTexture != null && !originalTexture.isJsonNull()) {
				String owner = this.getOwnerMod(resource, list);
				String name = this.getResourceName(list);
				int meta = this.getMeta(list);
				boolean isHas = this.isHasModel(resource);
				
				if(this.isSingleParentModel(originalTexture)) {
					return new SingleParentBlockModel(originalTexture.getAsJsonObject(), owner, name, meta, isHas, i).toStream();
				}else if(this.isAloneParentModel(originalTexture)) {
					return new AloneParentBlockModel(originalTexture, owner, name, meta, isHas, i).toStream();
				}else if(this.isSideModel(originalTexture)) {
					return new SideBlockModel(originalTexture.getAsJsonObject(), owner, name, meta, isHas, i).toStream();
				}else if(this.isOverlayModel(originalTexture)) {
					return new OverlayBlockModel(originalTexture, owner, name, meta, isHas, i).toStream();
				}else if(this.isItemBlock(resource)) {
					return new ItemBlockModel(owner, name, meta, isHas, i).toStream();
				}else {
					if(isHas) {
						return new BlockHasModel(originalTexture, owner, name, meta, i).toStream();
					}
					return new NormalBlockModel(originalTexture, owner, name, meta, i).toStream();
				}
			}
		}else if(this.isItemBlock(resource)) {
			List<String> list = Lists.newArrayList(resource.split("/"));
			String owner = this.getOwnerMod(resource, list);
			String name = this.getResourceName(list);
			int meta = this.getMeta(list);
			if(this.isHasModel(resource)) {
				return new ItemBlockModel(owner, name, meta, true, i).toStream();
			}else {
				return new ItemBlockModel(owner, name, meta, false, i).toStream();
			}
		}else if(resource.contains("models/item") || resource.contains("item/food")) {
			List<String> list = Lists.newArrayList(resource.split("/"));
			JsonElement originalTexture = this.getOriginalTexture(resource, list);
			if(originalTexture != null && !originalTexture.isJsonNull()) {
				String owner = this.getOwnerMod(resource, list);
				String name = this.getResourceName(list);
				int meta = this.getMeta(list);
				
				if(resource.contains("item/tools")) {
					return new NormalItemModel(originalTexture, "item/handheld", owner, name, meta, i).setDontAddCompressedTexture().toStream();
				}else {
					return new NormalItemModel(originalTexture, owner, name, meta, i).toStream();
				}
			}
		}

		InputStream stream = modPack.getInputStream(location);
		MCS.startmodel += System.currentTimeMillis() - i;
		return stream;
	}
	
	protected JsonElement getOriginalTexture(String resource, List<String> list) {
		ClientProxy proxy = MCS.proxy.getAsClientProxy();
		String name = this.getResourceName(list);
		
		if(customTextures.containsKey(name)) {
			return customTextures.get(name);
		}
		
		String owner = this.getOwnerMod(resource, list);
		boolean ItemOrBlock = resource.contains("models/item");
		
		if(proxy.hasModTexture(owner)) {
			if(ItemOrBlock) {
				if(proxy.hasItemTexture(owner, name)) {
					return proxy.getItemTexture(owner, name);
				}
			}else {
				boolean isHas = this.isHasModel(resource);
				if(proxy.hasBlockTexture(owner, isHas, name)) {
					return proxy.getBlockTexture(owner, isHas, name);
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
	
	protected boolean isSingleParentModel(JsonElement e) {
		if(e.isJsonObject()) {
			JsonObject o = e.getAsJsonObject();
			return o.has("parent") && o.has("model");
		}
		return false;
	}
	
	protected boolean isAloneParentModel(JsonElement e) {
		if(e.isJsonObject()) {
			JsonObject o = e.getAsJsonObject();
			return o.has("models");
		}
		return false;
	}
	
	protected String getOwnerMod(String resource, List<String> list) {
		if(resource.contains("blockstates")) {
			return list.get(3);
		}else if(resource.contains("models/item") || resource.contains("models/block")) {
			return list.get(4);
		}
		
		return "minecraft";
	}
	
	protected boolean isHasModel(String resource) {
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
		}
		return null;
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
			if(resource.endsWith(".json.mcmeta")) return false;
			String name = this.getResourceName(Lists.newArrayList(resource.split("/")));
            if(MCSResources.STUFF_NAME.contains(name)) {
    			MCS.startmodel += System.currentTimeMillis() - i;
            	return true;
            }
		}

		MCS.startmodel += System.currentTimeMillis() - i;
		return false;
	}
	
	public String getPackName() {
		return "MCS Models";
	}
	public BufferedImage getPackImage() throws IOException {
		return modPack.getPackImage();
	}
	public Set<String> getResourceDomains() {
		return modPack.getResourceDomains();
	}
	public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
		return this.modPack.getPackMetadata(metadataSerializer, metadataSectionName);
	}
}
