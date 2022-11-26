package cat.jiu.mcs.util.client.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.MCS;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class BaseModel {
	private final JsonObject stateJson;
	protected final JsonElement originalTexture;
	protected final String owner;
	protected final String name;
	private final long i;
	
	public BaseModel(@Nullable JsonElement originalTexture, @Nullable String defaultParent, String owner, String name, long i) {
		this.stateJson = new JsonObject();
		this.originalTexture = originalTexture;
		this.owner = owner;
		this.name = name;
		this.i = i;
		if(defaultParent != null) this.stateJson.addProperty("parent", defaultParent);
	}

	protected abstract void genData(JsonObject json);

	public final InputStream toStream() {
		this.genData(this.stateJson);
		InputStream steam = new ByteArrayInputStream(this.stateJson.toString().getBytes(StandardCharsets.UTF_8));
		MCS.startmodel += System.currentTimeMillis() - i;
		return steam;
	}
}
