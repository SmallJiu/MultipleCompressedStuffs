package cat.jiu.mcs.proxy;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cat.jiu.core.JiuCore;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.client.CompressedStuffResourcePack;
import cat.jiu.mcs.util.client.model.texture.ModTextures;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {
	public ClientProxy() {
		try {
			long time = System.currentTimeMillis();
			for(Entry<String, JsonElement> texture : new JsonParser().parse(new InputStreamReader(MCS.class.getResourceAsStream("/assets/mcs/textures/model_textures.json"), StandardCharsets.UTF_8)).getAsJsonObject().entrySet()) {
				MCS.addModTextures(texture.getKey(), new ModTextures(texture.getValue().getAsJsonObject()));
			}
			MCS.getLogOS().info("Format ModTextures json. (took {} ms)", System.currentTimeMillis() - time);
		}catch(JsonParseException e) {
			e.printStackTrace();
		}
		JiuCore.proxy.getAsClientProxy().addCustomResourcePack(new CompressedStuffResourcePack());
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().world;
	}
}
