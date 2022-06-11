package cat.jiu.mcs.util.client.model.item;

import com.google.gson.JsonElement;

public class ToolItemModel extends NormalItemModel {
	public ToolItemModel(JsonElement originalTexture, String owner, String name, int meta, long i) {
		super(originalTexture, "item/handheld", owner, name, meta, i);
	}
	
	@Override
	protected String getCompressedTexture(int compressedState) {
		if(compressedState == 32767) {
			return "mcs:items/tools/infinity";
		}else {
			return "mcs:items/tools/compressed_" + compressedState;
		}
	}
}
