package cat.jiu.mcs.util.client.model.block;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.BaseModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SingleParentBlockModel extends BaseModel {
	protected final JsonObject texture;
	protected final int meta;
	protected final boolean isHas;
	public SingleParentBlockModel(JsonObject originalTexture, String owner, String name, int meta, boolean isHas, long i) {
		super(originalTexture, originalTexture.get("parent").getAsString(), owner, name, i);
		this.texture = originalTexture;
		this.meta = meta;
		this.isHas = isHas;
	}

	@Override
	protected void genData(JsonObject json) {
		JsonObject model;
		if(this.texture.has("meta") && this.texture.getAsJsonObject("meta").has(String.valueOf(this.meta))) {
			model = this.texture.getAsJsonObject("meta").getAsJsonObject(String.valueOf(this.meta));
		}else {
			model = this.texture.getAsJsonObject("model");
		}
		
		for(Entry<String, JsonElement> e : model.entrySet()) {
			JsonElement value = e.getValue();
			if(value.isJsonPrimitive()) {
				String s = value.getAsString().replace("%level%", String.valueOf(this.meta+1));
				json.addProperty(e.getKey(), s);
			}else if("textures".equals(e.getKey())){
				JsonObject textures = new JsonObject();
				for(Entry<String, JsonElement> texture : e.getValue().getAsJsonObject().entrySet()) {
					if(texture.getValue().isJsonPrimitive()) {
						String s = texture.getValue().getAsString().replace("%level%", String.valueOf(this.meta+1));
						textures.addProperty(texture.getKey(), s);
					}else {
						textures.add(texture.getKey(), texture.getValue());
					}
				}
				json.add(e.getKey(), textures);
			}else {
				json.add(e.getKey(), e.getValue());
			}
		}
	}
}
