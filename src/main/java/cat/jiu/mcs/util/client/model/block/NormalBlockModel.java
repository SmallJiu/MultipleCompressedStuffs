package cat.jiu.mcs.util.client.model.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.BaseModel;

public class NormalBlockModel extends BaseModel {
	protected final int meta;
	public NormalBlockModel(JsonElement originalTexture, String owner, String name, int meta, long i) {
		super(originalTexture, "mcs:block/overlay_model", owner, name, i);
		this.meta = meta;
	}
	@Override
	protected void genData(JsonObject json) {
		JsonObject textures = new JsonObject();
		if("torcherino".equals(this.owner)) {
			json.addProperty("parent", "mcs:block/compressed_torcherino");
			switch(this.meta) {
				case 0: 
					textures.addProperty("all", "torcherino:blocks/compressed_torcherino");
					break;
				case 1: 
					textures.addProperty("all", "torcherino:blocks/double_compressed_torcherino");
					break;
				default:
					textures.addProperty("all", "mcs:blocks/un/torcherino/compressed_" + (this.meta+1));
					break;
			}
		}else {
			textures.add("all", this.originalTexture);
			textures.addProperty("overlay", "mcs:blocks/compressed_" + (this.meta+1));
		}
		json.add("textures", textures);
	}
}
