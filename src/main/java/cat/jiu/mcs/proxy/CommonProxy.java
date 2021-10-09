package cat.jiu.mcs.proxy;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		PreInit.preInit();
	}

	public void init(FMLInitializationEvent event) {
		
	}
	
	public World getClientWorld() {
		return null;
	}
	
	public boolean isClient() {
		return false;
	}
}
