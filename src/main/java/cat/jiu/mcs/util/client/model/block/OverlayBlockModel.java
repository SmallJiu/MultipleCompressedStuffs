package cat.jiu.mcs.util.client.model.block;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.BaseModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class OverlayBlockModel extends BaseModel {
	protected final int meta;
	protected final boolean isHas;

	public OverlayBlockModel(JsonElement originalTexture, String owner, String name, int meta, boolean isHas, long i) {
		super(originalTexture, "mcs:block/overlay_model", owner, name, i);
		this.meta = meta;
		this.isHas = isHas;
	}

	@Override
	protected void genData(JsonObject json) {
		JsonObject original = this.originalTexture.getAsJsonObject();
		json.addProperty("parent", "mcs:block/overlay_model_" + original.size());
		
		JsonObject textures = new JsonObject();
		textures.addProperty("all", original.get("all").getAsString());
		
		if(this.isHas) {
			if(original.size() == 2) {
				if(this.meta != 0) {
					textures.addProperty("overlay_1", original.get("overlay_1").getAsString());
					textures.addProperty("overlay_2", "mcs:blocks/compressed_" + (this.meta+1));
				}else {
					textures.addProperty("overlay", original.get("overlay_1").getAsString());
				}
			}else {
				for(Entry<String, JsonElement> overlays :original.entrySet()) {
					textures.addProperty(overlays.getKey(), overlays.getValue().getAsString());
				}
				textures.addProperty("overlay_" + original.size(), "mcs:blocks/compressed_" + (this.meta+1));
			}
		}else {
			if(original.size() == 2) {
				textures.addProperty("overlay_1", original.get("overlay_1").getAsString());
				textures.addProperty("overlay_2", "mcs:blocks/compressed_" + (this.meta+1));
			}else {
				for(Entry<String, JsonElement> overlays :original.entrySet()) {
					textures.addProperty(overlays.getKey(), overlays.getValue().getAsString());
				}
				textures.addProperty("overlay_" + original.size(), "mcs:blocks/compressed_" + (this.meta+1));
			}
		}
		json.add("textures", textures);
	}
}
