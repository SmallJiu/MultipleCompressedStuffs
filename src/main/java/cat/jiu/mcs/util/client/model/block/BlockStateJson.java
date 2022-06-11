package cat.jiu.mcs.util.client.model.block;

import com.google.gson.JsonObject;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.client.model.BaseModel;

public class BlockStateJson extends BaseModel {
	private final String isHas;
	public BlockStateJson(String owner, String name, boolean isHas, long i) {
		super(null, null, owner, name, i);
		this.isHas = isHas ? "has" : "normal";
	}
	@Override
	protected void genData(JsonObject json) {
		JsonObject variants = new JsonObject(); 
		for(int meta = 0; meta < 16; meta++) {
			JsonObject model = new JsonObject();
			model.addProperty("model", "mcs:" + JiuUtils.other.addJoins("/", owner, isHas, name, JiuUtils.other.addJoins(0, ".", name, meta)));
			variants.add("level=level_" + meta, model);
		}
		json.add("variants", variants);
	}
}
