package cat.jiu.mcs.util.client.model.texture;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.texture.object.ItemTexture;

public class ItemTextures {
	protected Map<String, ItemTexture> normal;
	public ItemTextures(JsonObject items) {
		this.normal = Maps.newHashMap();
		this.parse(items);
	}
	
	public ItemTextures() {}
	public ItemTextures(Map<String, ItemTexture> normal) {
		this.normal = normal;
	}

	protected void parse(JsonObject items) {
		for(Entry<String, JsonElement> item : items.entrySet()) {
			String name = item.getKey();
			JsonElement e = item.getValue();
			if(name.contains("compressed_")) {
				this.normal.put(name, new ItemTexture(e));
			}else if(e.isJsonObject()) {
				this.parse(e.getAsJsonObject());
			}
		}
	}
	
	public Set<String> getAllTexture() {
		if(this.normal==null) return Collections.emptySet();
		return Sets.newHashSet(this.normal.keySet());
	}
	public boolean hasTexture(String name) {
		if(this.normal==null) return false;
		return normal.containsKey(name);
	}
	public ItemTexture getTexture(String name) {
		if(this.normal==null) return null;
		return normal.get(name);
	}
	public void addTexture(String name, ItemTexture texture) {
		if(this.normal==null) this.normal = Maps.newHashMap();
		this.normal.put(name, texture);
	}
	public void addAll(ItemTextures items) {
		if(items.normal!=null) {
			if(this.normal==null) this.normal = Maps.newHashMap();
			this.normal.putAll(items.normal);
		}
	}
	
	public JsonObject toJson() {
		JsonObject o = new JsonObject();
		if(this.normal==null) return o;
		
		JsonObject normal = new JsonObject();
		for(Entry<String, ItemTexture> lay : this.normal.entrySet()) {
			normal.add(lay.getKey(), lay.getValue().toJson());
		}
		o.add("normal", normal);
		
		return o;
	}
	@Override
	public String toString() {
		return this.toJson().toString();
	}
}
