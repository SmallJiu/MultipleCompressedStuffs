package cat.jiu.mcs.proxy;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;;

public class ClientProxy extends ServerProxy {
	public ClientProxy() {
		long time = System.currentTimeMillis();
		JsonObject obj = null;
		try {
			obj = new JsonParser().parse(new InputStreamReader(MCS.class.getResourceAsStream("/assets/mcs/textures/mode_textures.json"), StandardCharsets.UTF_8)).getAsJsonObject();
		}catch(JsonParseException e) {
			e.printStackTrace();
		}
		MCS.setTextures(obj);
		MCS.instance.log.info("Format Textures json. (took " + (System.currentTimeMillis() - time) + " ms)");	
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
//		for(Block block : MCSResources.BLOCKS) {
//			this.initTexture(block);
//		}
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@SuppressWarnings("unused")
	private void initTexture(Block block) {
		if(block instanceof ICompressedStuff) {
			ICompressedStuff com = (ICompressedStuff) block;
			String blockModid = com.getOwnerMod();
			String blockName = block.getRegistryName().getResourcePath();
			
			if(MCS.getTextures().has(blockModid)) {
				JsonObject modTextures = MCS.getTextures().getAsJsonObject(blockModid).getAsJsonObject("block");
				if(modTextures.has(com.isHas() ? "has" : "normal")) {
					JsonObject nh = modTextures.getAsJsonObject(com.isHas() ? "has" : "normal");
					if(nh.has(blockName)) {
						JsonElement blockT = nh.get(blockName);
						if(blockT.isJsonPrimitive()) {
							this.registerTexture(blockT.getAsString());
						}else if(blockT.isJsonObject()) {
							JsonObject blockS = blockT.getAsJsonObject();
							if(blockS.has("all")) {
								this.registerTexture(blockS.get("all").getAsString());
								for(int i = 1; i < 5; i++) {
									String over = "overlay_" + i;
									if(blockS.has(over)) {
										this.registerTexture(blockS.get(over).getAsString());
									}
								}
							}else if(blockS.has("top")) {
								this.registerTexture(blockS.get("top").getAsString());
								this.registerTexture(blockS.get("side").getAsString());
								this.registerTexture(blockS.get("down").getAsString());
							}
						}
					}
				}
				
			}
		}
	}
	
	private TextureAtlasSprite registerTexture(String loc) {
		return Minecraft.getMinecraft()
				.getTextureMapBlocks()
				.registerSprite(new ResourceLocation(loc));
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().world;
	}

	@Override
	public boolean isClient() {
		return true;
	}
}
