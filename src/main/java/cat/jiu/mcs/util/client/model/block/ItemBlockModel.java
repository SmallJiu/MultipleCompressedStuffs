package cat.jiu.mcs.util.client.model.block;

import com.google.gson.JsonObject;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.client.model.BaseModel;

public class ItemBlockModel extends BaseModel {
	public ItemBlockModel(String owner, String name, int meta, boolean isHas, long i) {
		super(null,
			JiuUtils.other.addJoins("/", "mcs:block", owner, (isHas ? "has" : "normal"), name, name + "." + meta),
			null, null, i);
	}

	@Override
	protected void genData(JsonObject json) {}
}
