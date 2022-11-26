package cat.jiu.mcs.util.client.model.block;

import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.BaseModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SideBlockModel extends BaseModel {
	protected final int meta;
	protected final boolean isHas;
	protected final String top;
	protected final String down;
	protected final String side;
	
	public SideBlockModel(JsonObject originalTexture, String owner, String name, int meta, boolean isHas, long i) {
		super(originalTexture, "mcs:block/overlay_model_side", owner, name, i);
		this.meta = meta;
		this.isHas = isHas;
		this.top = originalTexture.get("top").getAsString();
		this.down = originalTexture.get("down").getAsString();
		this.side = originalTexture.get("side").getAsString();
	}

	@Override
	protected void genData(JsonObject json) {
		JsonObject textures = new JsonObject();
		
		textures.addProperty("top", this.top);
		textures.addProperty("down", this.down);
		textures.addProperty("side", this.side);
		textures.addProperty("overlay", "mcs:blocks/compressed_" + (this.meta+1));
		
		json.add("textures", textures);
	}
}
