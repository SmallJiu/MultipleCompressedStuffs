package cat.jiu.mcs.util.client.model.texture;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.texture.object.BlockTexture;

public class BlockTextures {
	private Map<String, BlockTexture> has;
	private Map<String, BlockTexture> normal;
	
	public BlockTextures() {}
	protected BlockTextures(Map<String, BlockTexture> has, Map<String, BlockTexture> normal) {
		this.has = has;
		this.normal = normal;
	}

	public BlockTextures(JsonObject textures) {
		if(textures.has("has")) {
			this.parse(true, textures.getAsJsonObject("has"));
		}
		if(textures.has("normal")) {
			this.parse(false, textures.getAsJsonObject("normal"));
		}
	}
	
	protected void parse(boolean isHas, JsonObject textures) {
		for(Entry<String, JsonElement> block : textures.entrySet()) {
			String name = block.getKey();
			JsonElement e = block.getValue();
			if(name.startsWith("compressed_")) {
				this.addTexture(isHas, name, new BlockTexture(e));
			}else if(e.isJsonObject()) {
				this.parse(isHas, e.getAsJsonObject());
			}
		}
	}
	
	public void addTexture(boolean isHas, String name, BlockTexture texture) {
		this.getTextureMap(isHas).put(name, texture);
	}
	
	public void addAll(BlockTextures blocks) {
		if(blocks.has!=null) {
			this.getTextureMap(true).putAll(blocks.has);
		}
		if(blocks.normal!=null) {
			this.getTextureMap(false).putAll(blocks.normal);
		}
	}
	
	public Set<String> getAllTexture(boolean isHas){
		return Sets.newHashSet(getTextureMap(isHas).keySet());
	}
	
	public boolean hasTexture(boolean isHas, String name) {
		return getTextureMap(isHas).containsKey(name);
	}
	
	public BlockTexture getTexture(boolean isHas, String name) {
		return getTextureMap(isHas).get(name);
	}
	
	protected Map<String, BlockTexture> getTextureMap(boolean isHas) {
		if(isHas) {
			if(this.has==null) this.has = Maps.newHashMap();
			return this.has;
		}else {
			if(this.normal==null) this.normal = Maps.newHashMap();
			return this.normal;
		}
	}
	
	public JsonObject toJson() {
		JsonObject o = new JsonObject();
		if(this.has!=null) {
			JsonObject has = new JsonObject();
			for(Entry<String, BlockTexture> lay : this.has.entrySet()) {
				has.add(lay.getKey(), lay.getValue().toJson());
			}
			o.add("has", has);
		}
		if(this.normal!=null) {
			JsonObject normal = new JsonObject();
			for(Entry<String, BlockTexture> lay : this.normal.entrySet()) {
				normal.add(lay.getKey(), lay.getValue().toJson());
			}
			o.add("normal", normal);
		}
		return o;
	}
	@Override
	public String toString() {
		return this.toJson().toString();
	}
}
