package cat.jiu.mcs.proxy;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cat.jiu.core.JiuCore;
import cat.jiu.core.events.game.ResourceReloadEvent;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.client.CompressedStuffResourcePack;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends ServerProxy {
	private JsonObject texture;
	public ClientProxy() {
		JiuCore.proxy.getAsClientProxy().addCustomResourcePack(new CompressedStuffResourcePack());
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().world;
	}
	
	@SubscribeEvent
	public void onResourcePreReload(ResourceReloadEvent.Pre event) {
		JsonObject textures = null;
		try {
			long time = System.currentTimeMillis();
			textures = new JsonParser().parse(new InputStreamReader(MCS.class.getResourceAsStream("/assets/mcs/textures/model_textures.json"), StandardCharsets.UTF_8)).getAsJsonObject();
			MCS.getLogOS().info("Format ModTextures json. (took {} ms)", System.currentTimeMillis() - time);
		}catch(JsonParseException e) {
			e.printStackTrace();
		}
		this.texture = textures;
	}
	
	public boolean hasModTexture(String modid) {
		if(this.texture==null) return false;
		return this.texture.has(modid) && this.texture.get(modid).isJsonObject();
	}
	public boolean hasBlockTexture(String modid, boolean isHas, String name) {
		JsonElement e = this.getBlockTexture(modid, isHas, name);
		return e!=null && !e.isJsonNull();
	}
	public boolean hasItemTexture(String modid, String name) {
		JsonElement e = this.getItemTexture(modid, name);
		return e!=null && !e.isJsonNull();
	}
	
	public JsonElement getBlockTexture(String modid, boolean isHas, String name) {
		if(!this.hasModTexture(modid)) return JsonNull.INSTANCE;
		
		JsonObject mod = this.texture.getAsJsonObject(modid);
		if(!mod.has("block")) return JsonNull.INSTANCE;
		
		JsonObject block = mod.getAsJsonObject("block");
		if(!block.has(isHas ? "has" : "normal")) return JsonNull.INSTANCE;
		
		return block.getAsJsonObject(isHas ? "has" : "normal").get(name);
	}
	
	public JsonElement getItemTexture(String modid, String name) {
		if(!this.hasModTexture(modid)) return JsonNull.INSTANCE;
		
		JsonObject mod = this.texture.getAsJsonObject(modid);
		if(!mod.has("block")) return JsonNull.INSTANCE;
		
		return mod.getAsJsonObject("item").get(name);
	}
}
