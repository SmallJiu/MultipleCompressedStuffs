package cat.jiu.mcs.util.client.model.texture;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.texture.object.BlockTexture;
import cat.jiu.mcs.util.client.model.texture.object.ItemTexture;

public class MCSTexture {
	protected final LinkedHashMap<String, String> lays = Maps.newLinkedHashMap();
	
	public JsonElement toJson() {
		JsonObject o = new JsonObject();
		for(Entry<String, String> lay : this.lays.entrySet()) {
			o.addProperty(lay.getKey(), lay.getValue());
		}
		return o;
	}
	public ItemTexture getAsItemTexture() {
		return (ItemTexture) this;
	}
	public BlockTexture getAsBlockTexture() {
		return (BlockTexture) this;
	}
	public boolean isItemTexture() {
		return this instanceof ItemTexture;
	}
	public boolean isBlockTexture() {
		return this instanceof BlockTexture;
	}
	@Override
	public String toString() {
		return this.toJson().toString();
	}
}
