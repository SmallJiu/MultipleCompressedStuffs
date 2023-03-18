package cat.jiu.mcs.util.client.model.block;

import com.google.gson.JsonObject;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.client.model.BaseModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockStateModel extends BaseModel {
	private final String isHas;
	public BlockStateModel(String owner, String name, boolean isHas, long i) {
		super(null, null, owner, name, i);
		this.isHas = isHas ? "has" : "normal";
	}
	@Override
	protected void genData(JsonObject json) {
		JsonObject variants = new JsonObject();
		int max = ModSubtypes.MAX <= 16 ? ModSubtypes.MAX : 16;
		for(int meta = 0; meta < max; meta++) {
			JsonObject model = new JsonObject();
			model.addProperty("model", "mcs:" + JiuUtils.other.addJoins("/", new Object[] {owner, isHas, name, ".", name+"."+meta}));
			variants.add("level=level_" + meta, model);
		}
		json.add("variants", variants);
	}
}
