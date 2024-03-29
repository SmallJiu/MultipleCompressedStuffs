package cat.jiu.mcs.util.client.model.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.BaseModel;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FaceBlockModel extends BaseModel {
	protected final JsonElement texture;
	protected final int meta;
	protected final boolean isHas;
	
	public FaceBlockModel(JsonElement texture, String owner, String name, int meta, boolean isHas, long i) {
		super(texture, "mcs:block/overlay_model_face", owner, name, i);
		this.texture = texture;
		this.meta = meta;
		this.isHas = isHas;
	}

	@Override
	protected void genData(JsonObject json) {
		JsonObject textures = this.texture.getAsJsonObject();
		textures.addProperty("overlay_" + textures.size(), "mcs:blocks/compressed_" + (this.meta+1));
		json.add("textures", textures);
	}
}
