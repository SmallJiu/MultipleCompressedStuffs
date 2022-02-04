//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().world;
	}
	
	@Override
	public boolean isClient() {
		return true;
	}
	
	@Override
	public void makeCrashReport(String msg, Throwable causeThrowable) {
		Minecraft.getMinecraft().crashed(new CrashReport(msg, causeThrowable));
	}
}
