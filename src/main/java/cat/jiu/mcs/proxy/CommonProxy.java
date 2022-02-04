//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.proxy;

import cat.jiu.core.exception.UnsupportedException;
import cat.jiu.core.util.JiuCoreEvents;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.GuiHandler;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;
import cat.jiu.mcs.blocks.tileentity.TileEntityCreativeEnergy;
import cat.jiu.mcs.recipes.MCSRecipe;
import cat.jiu.mcs.util.TestModel;
import cat.jiu.mcs.util.event.CatEvent;
import cat.jiu.mcs.util.event.OtherModBlockChange;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;
import cat.jiu.mcs.util.init.MCSOreDict;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;;

public class CommonProxy {
	public long startrecipe;
	public long startore;
	public long startblock;
	public long startitem;
	
	public void preInit(FMLPreInitializationEvent event) {
		PreInit.preInit();
		GuiHandler.register();
		
		JiuCoreEvents.addEvent(new CatEvent());
		JiuCoreEvents.addEvent(new TestModel());
		JiuCoreEvents.addEvent(new OtherModBlockChange());
		
		startblock = System.currentTimeMillis();
		new MCSBlocks();
		
		try {
			MCSBlocks.registerCustom();
			MCSBlocks.initChangeBlock();
		} catch (Throwable e) {
//			e.printStackTrace();
			this.makeCrashReport(e.getMessage(), e);
		}
		startblock = System.currentTimeMillis() - startblock;
		
		startitem = System.currentTimeMillis();
		new MCSItems();
		MCSItems.normal.CAT_HAMMER.setMaxStackSize(1);
		MCSItems.registerCustom();
		startitem = System.currentTimeMillis() - startitem;
		
		GameRegistry.registerTileEntity(TileEntityChangeBlock.class, new ResourceLocation(MCS.MODID + ":" + "change_block"));
		GameRegistry.registerTileEntity(TileEntityCompressor.class, new ResourceLocation(MCS.MODID + ":" + "compressor"));
		GameRegistry.registerTileEntity(TileEntityCreativeEnergy.class, new ResourceLocation(MCS.MODID + ":" + "creative_energy"));
	}
	
	public void init(FMLInitializationEvent event) {
		startore = System.currentTimeMillis();
		try {
			MCSOreDict.register();
		} catch (Exception e) {e.printStackTrace();}
		
		startore = System.currentTimeMillis() - startore;
		
		startrecipe = System.currentTimeMillis();
		MCSRecipe.register();
		startrecipe = System.currentTimeMillis() - startrecipe;
	}
	
	public void makeCrashReport(String msg, Throwable causeThrowable) {
		try {
			throw new UnsupportedException(msg, causeThrowable);
		} catch (UnsupportedException e) {
			
		}
	}
	
	public World getClientWorld() {
		
		return null;
	}
	
	public boolean isClient() {
		return false;
	}

	
}
