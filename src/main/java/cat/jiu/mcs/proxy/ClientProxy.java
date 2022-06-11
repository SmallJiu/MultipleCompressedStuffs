package cat.jiu.mcs.proxy;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cat.jiu.core.JiuCore;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.client.CompressedStuffResourcePack;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {
	public ClientProxy() {
		long time = System.currentTimeMillis();
		JsonObject obj = null;
		try {
			obj = new JsonParser().parse(new InputStreamReader(MCS.class.getResourceAsStream("/assets/mcs/textures/model_textures.json"), StandardCharsets.UTF_8)).getAsJsonObject();
		}catch(JsonParseException e) {
			e.printStackTrace();
		}
		if(MCS.setTextures(obj)) {
			LogManager.getLogger(MCS.MODID).info("Format Textures json. (took " + (System.currentTimeMillis() - time) + " ms)");
		}
		((cat.jiu.core.proxy.ClientProxy)JiuCore.proxy)
			.addCustomResourcePack(new CompressedStuffResourcePack());
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
