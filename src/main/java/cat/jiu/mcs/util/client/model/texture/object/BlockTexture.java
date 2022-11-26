package cat.jiu.mcs.util.client.model.texture.object;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import cat.jiu.mcs.util.client.model.texture.MCSTexture;

public class BlockTexture extends MCSTexture {
	public BlockTexture(String all) {
		this.lays.put("all", all);
	}
	public BlockTexture(String all, String... overlays) {
		this(all);
		for(int i = 0; i < overlays.length; i++) {
			this.lays.put("overlay_"+(i+1), overlays[i]);
		}
	}
	public BlockTexture(Map<String, String> textures) {
		for(Entry<String, String> texture : textures.entrySet()) {
			this.lays.put(texture.getKey(), texture.getValue());
		}
	}
	public BlockTexture(String top, String side, String down) {
		this.lays.put("top", top);
		this.lays.put("side", side);
		this.lays.put("down", down);
	}
	
	public BlockTexture(JsonElement e) {
		if(e.isJsonObject()) {
			for(Entry<String, JsonElement> texture : e.getAsJsonObject().entrySet()) {
				this.lays.put(texture.getKey(), texture.getValue().getAsString());
			}
		}else if(e.isJsonArray()) {
			JsonArray texture = e.getAsJsonArray();
			for(int i = 0; i < texture.size(); i++) {
				if(i==0) {
					this.lays.put("all", texture.get(i).getAsString());
				}else {
					this.lays.put("overlay_"+(i-1), texture.get(i).getAsString());
				}
			}
		}else if(e.isJsonPrimitive()) {
			this.lays.put("all", e.getAsString());
		}
	}
	
	public boolean isSideBlock() {
		return this.lays.containsKey("top")
			&& this.lays.containsKey("side")
			&& this.lays.containsKey("down");
	}
	public boolean isFaceBlock() {
		return this.lays.containsKey("top")
			&& this.lays.containsKey("side")
			&& this.lays.containsKey("north")
			&& this.lays.containsKey("south")
			&& this.lays.containsKey("east")
			&& this.lays.containsKey("west");
	}
	@Override
	public JsonElement toJson() {
		if(this.lays.size()<2) {
			return new JsonPrimitive(this.lays.get("all"));
		}
		return super.toJson();
	}
}
