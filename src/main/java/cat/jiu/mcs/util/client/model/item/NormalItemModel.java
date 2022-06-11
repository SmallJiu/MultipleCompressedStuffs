package cat.jiu.mcs.util.client.model.item;

import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.BaseModel;

public class NormalItemModel extends BaseModel {
	protected final int meta;
	public NormalItemModel(JsonElement originalTexture, String owner, String name, int meta, long i) {
		this(originalTexture, "item/generated", owner, name, meta, i);
	}
	public NormalItemModel(JsonElement originalTexture, String defaultParent, String owner, String name, int meta, long i) {
		super(originalTexture, defaultParent, owner, name, i);
		this.meta = meta;
	}

	@Override
	protected void genData(JsonObject json) {
		JsonObject textures = new JsonObject();
		
		if(this.originalTexture.isJsonPrimitive()) {
			textures.addProperty("layer0", this.originalTexture.getAsString());
		}else if(this.originalTexture.isJsonArray()) {
			JsonArray textureArray = this.originalTexture.getAsJsonArray();
			for(int i = 0; i < textureArray.size(); i++) {
				textures.addProperty("layer"+i, textureArray.get(i).getAsString());
			}
		}else if(this.originalTexture.isJsonObject()) {
			int i = 0;
			for (Entry<String, JsonElement> texture : this.originalTexture.getAsJsonObject().entrySet()) {
				textures.addProperty("layer"+i, texture.getValue().getAsString());
				i += 1;
			}
		}
		
		textures.addProperty("layer"+json.size(), this.getCompressedTexture(this.meta+1));
		json.add("textures", textures);
	}
	
	protected String getCompressedTexture(int compressedState) {
		if(compressedState == 32767) {
			return "mcs:items/infinity";
		}else {
			return "mcs:items/compressed_"+ + compressedState;
		}
	}
}
