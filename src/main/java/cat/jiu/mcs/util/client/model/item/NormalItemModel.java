package cat.jiu.mcs.util.client.model.item;

import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.BaseModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NormalItemModel extends BaseModel {
	protected final int meta;
	public NormalItemModel(JsonElement originalTexture, String owner, String name, int meta, long i) {
		this(originalTexture, "item/generated", owner, name, meta, i);
	}
	public NormalItemModel(JsonElement originalTexture, String defaultParent, String owner, String name, int meta, long i) {
		super(originalTexture, defaultParent, owner, name, i);
		this.meta = meta;
	}
	protected boolean dontAddCompressedTexture;
	public NormalItemModel setDontAddCompressedTexture() {
		this.dontAddCompressedTexture = true;
		return this;
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
			for (Entry<String, JsonElement> texture : this.originalTexture.getAsJsonObject().entrySet()) {
				textures.addProperty(texture.getKey(), texture.getValue().getAsString());
			}
		}
		
		if(!this.dontAddCompressedTexture) textures.addProperty("layer"+json.size(), this.getCompressedTexture(this.meta+1));
		json.add("textures", textures);
	}
	
	protected String getCompressedTexture(int level) {
		if(level >= Short.MAX_VALUE) {
			return "mcs:items/infinity";
		}else {
			return "mcs:items/compressed_"+ + level;
		}
	}
}
