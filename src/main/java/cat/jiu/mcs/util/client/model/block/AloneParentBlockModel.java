package cat.jiu.mcs.util.client.model.block;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.client.model.BaseModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AloneParentBlockModel extends BaseModel {
	protected final JsonElement models;
	protected final int meta;
	protected final boolean isHas;
	
	public AloneParentBlockModel(JsonElement originalTexture, String owner, String name, int meta, boolean isHas, long i) {
		super(originalTexture, null, owner, name, i);
		this.models = originalTexture.getAsJsonObject().get("models");
		this.meta = meta;
		this.isHas = isHas;
	}
	
	@Override
	protected void genData(JsonObject json) {
		JsonObject models = null;
		
		if(this.models.isJsonArray() && this.models.getAsJsonArray().size() >= this.meta+1) {
			models = this.models.getAsJsonArray().get(this.meta).getAsJsonObject();
		}else if(this.models.isJsonObject() && this.models.getAsJsonObject().has(String.valueOf(this.meta))) {
			models = this.models.getAsJsonObject().getAsJsonObject(String.valueOf(this.meta));
		}
		
		if(models!=null) {
			for(Entry<String, JsonElement> e : models.entrySet()) {
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
}
