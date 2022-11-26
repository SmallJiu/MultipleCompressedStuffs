package cat.jiu.mcs.util.client.model.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.BaseModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockHasModel extends BaseModel {
	protected final int meta;
	public BlockHasModel(JsonElement originalTexture, String owner, String name, int meta, long i) {
		super(originalTexture, "mcs:block/normal_model", owner, name, i);
		this.meta = meta;
	}

	@Override
	protected void genData(JsonObject json) {
		if(this.meta == 0) {
			JsonObject textures = new JsonObject();
			textures.add("all", this.originalTexture);
			json.add("textures", textures);
		}else {
			json.addProperty("parent", "mcs:block/overlay_model");
			JsonObject textures = new JsonObject();
			textures.add("all", this.originalTexture);
			textures.addProperty("overlay", "mcs:blocks/compressed_" + this.meta);
			json.add("textures", textures);
		}
	}
}
