package cat.jiu.mcs.proxy;

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
import cat.jiu.mcs.util.init.InitCustom;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;
import cat.jiu.mcs.util.init.MCSOreDict;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;;

public class CommonProxy {
	public long startrecipe;
	public long startore;
	public long startblock;
	public long startitem;
	public long startcustom;
	
	public void preInit(FMLPreInitializationEvent event) {
		PreInit.preInit();
		GuiHandler.register();
		
		this.startblock = System.currentTimeMillis();
		new MCSBlocks();
		this.startblock = System.currentTimeMillis() - this.startblock;
		
		this.startcustom = System.currentTimeMillis();
		InitCustom.registerCustom();
		this.startcustom = System.currentTimeMillis() - this.startcustom;
		
		this.startitem = System.currentTimeMillis();
		new MCSItems();
		this.startitem = System.currentTimeMillis() - this.startitem;
		
		JiuCoreEvents.addEvent(new CatEvent());
		JiuCoreEvents.addEvent(new TestModel());
		JiuCoreEvents.addEvent(new OtherModBlockChange());
		
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
	
	public void postInit(FMLPostInitializationEvent event) {
		try {
			MCSBlocks.initChangeBlock();
		} catch (Throwable e) {
			this.makeCrashReport(e.getMessage(), e);
		}
	}
	
	public void makeCrashReport(String msg, Throwable causeThrowable) {
		throw new RuntimeException(msg, causeThrowable);
	}
	
	public World getClientWorld() {
		return null;
	}
	
	public boolean isClient() {
		return false;
	}
}
