package cat.jiu.mcs.util.client.model.texture.object;

import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import cat.jiu.mcs.util.client.model.texture.MCSTexture;

public class ItemTexture extends MCSTexture {
	public ItemTexture(String... lays) {
		for(int i = 0; i < lays.length; i++) {
			this.addLay(lays[i]);
		}
	}
	
	public ItemTexture(JsonElement texture) {
		if(texture.isJsonObject()) {
			for(Entry<String, JsonElement> textures : texture.getAsJsonObject().entrySet()) {
				this.lays.put(textures.getKey(), textures.getValue().getAsString());
			}
		}else if(texture.isJsonArray()) {
			JsonArray texures = texture.getAsJsonArray();
			for(int i = 0; i < texures.size(); i++) {
				this.lays.put("lay"+i, texures.get(i).getAsString());
			}
		}else if(texture.isJsonPrimitive()) {
			this.lays.put("lay0", texture.getAsString());
		}
	}
	
	public void addLay(String lay) {
		this.lays.put("lay"+this.lays.size(), lay);
	}
	
	@Override
	public JsonElement toJson() {
		if(this.lays.size()==1) {
			return new JsonPrimitive(this.lays.get("lay0"));
		}
		return super.toJson();
	}
}
