package cat.jiu.mcs.proxy;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;

import appeng.api.AEApi;
import appeng.api.definitions.IBlockDefinition;
import appeng.api.definitions.IDefinitions;

import cat.jiu.core.util.JiuCoreEvents;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.GuiHandler;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressedChest;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;
import cat.jiu.mcs.blocks.tileentity.TileEntityCreativeEnergy;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.exception.ItemNotFoundException;
import cat.jiu.mcs.recipes.MCSRecipe;
import cat.jiu.mcs.util.TestModel;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import cat.jiu.mcs.util.event.CatEvent;
import cat.jiu.mcs.util.event.OtherModBlockChange;
import cat.jiu.mcs.util.init.InitCustom;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;
import cat.jiu.mcs.util.init.MCSOreDict;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ServerProxy {
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
		GameRegistry.registerTileEntity(TileEntityCompressedChest.class, new ResourceLocation(MCS.MODID + ":" + "compressed_chest"));
	}

	@SuppressWarnings("unchecked")
	public void postInit(FMLPostInitializationEvent event) {
		this.setUnStack();

		if(!InitCustom.unRegisterCustom.isEmpty()) {
			HashMap<String, JsonElement> entrys = ((HashMap<String, JsonElement>) InitCustom.unRegisterCustom.clone());
			for(Entry<String, JsonElement> res : entrys.entrySet()) {
				ItemStack unItem = JiuUtils.item.toStack(res.getValue());
				BaseBlockSub b = InitCustom.unSetUnItem.get(res.getKey());
				if(unItem != null && !unItem.isEmpty()) {
					b.setUnCompressed(unItem);
				}
				if(b.getUnCompressedStack() != null && !b.getUnCompressedStack().isEmpty()) {
					InitCustom.unSetUnItem.remove(res.getKey());
					InitCustom.unRegisterCustom.remove(res.getKey());
				}
			}
			if(!InitCustom.unRegisterCustom.isEmpty()) {
				String crashMsg = "\n\ncustom.json -> unknown item:";
				for(Entry<String, JsonElement> res : InitCustom.unRegisterCustom.entrySet()) {
					String name = res.getValue().isJsonObject() ? res.getValue().getAsJsonObject().get("name").getAsString() : res.getValue().getAsString();
					crashMsg += "\n  -> ID: \"" + res.getKey() + "\", unItem: \"" + name + "\"";
				}
				throw new ItemNotFoundException(crashMsg + "\n");
			}
		}

		
		
		startore = System.currentTimeMillis();
		MCSOreDict.register();
		startore = System.currentTimeMillis() - startore;

		startrecipe = System.currentTimeMillis();
		MCSRecipe.register();
		startrecipe = System.currentTimeMillis() - startrecipe;
		try {
			MCSBlocks.initChangeBlock();
		}catch(Throwable e) {
			this.makeCrashReport(e.getMessage(), e);
		}
	}

	private void setUnStack() {
		if(Configs.Custom.Mod_Stuff.AppliedEnergistics2) {
			IDefinitions ae = AEApi.instance().definitions();
			MCSBlocks.AppliedEnergistics2.Normal ae2 = MCSBlocks.ae2.normal;
			ae2.C_certus_quartz_glass_B.setUnCompressed(this.getAEStack(ae.blocks().quartzGlass()));
			ae2.C_certus_quartz_block_B.setUnCompressed(this.getAEStack(ae.blocks().quartzBlock()));
			ae2.C_certus_quartz_vibrant_glass_B.setUnCompressed(this.getAEStack(ae.blocks().quartzVibrantGlass()));
			ae2.C_fluix_block_B.setUnCompressed(this.getAEStack(ae.blocks().fluixBlock()));
			ae2.C_sky_stone_block_B.setUnCompressed(this.getAEStack(ae.blocks().skyStoneBlock()));
			ae2.C_smooth_sky_stone_block_B.setUnCompressed(this.getAEStack(ae.blocks().smoothSkyStoneBlock()));
		}
		if(Configs.Custom.Mod_Stuff.Botania) {
			MCSBlocks.BotaniaBlock.Normal bot = MCSBlocks.botania.normal;
			bot.C_MANA_STEEL_B.setUnCompressed(this.getBotStack("storage"));
			bot.C_TERRASTELL_STEEL_B.setUnCompressed(this.getBotStack("storage", 1));
			bot.C_ELEMENTIUM_STELL_B.setUnCompressed(this.getBotStack("storage", 2));
			bot.C_MANA_DIAMOND_B.setUnCompressed(this.getBotStack("storage", 3));
			bot.C_DRAGONSTONE_B.setUnCompressed(this.getBotStack("storage", 4));
			bot.C_DREAMWOOD_B.setUnCompressed(this.getBotStack("dreamwood"));
			bot.C_ELFGLASS_B.setUnCompressed(this.getBotStack("elfGlass"));
			bot.C_LIVING_ROCK_B.setUnCompressed(this.getBotStack("livingrock"));
			bot.C_LIVING_WOOD_B.setUnCompressed(this.getBotStack("livingwood"));
			bot.C_MANA_GLASS_B.setUnCompressed(this.getBotStack("managlass"));
			bot.C_QUARTZ_TYPE_BLAZE_B.setUnCompressed(this.getBotStack("quartztypeblaze"));
			bot.C_QUARTZ_TYPE_DARK_B.setUnCompressed(this.getBotStack("quartztypedark"));
			bot.C_QUARTZ_TYPE_EELF_B.setUnCompressed(this.getBotStack("quartztypeelf"));
			bot.C_QUARTZ_TYPE_LAVENDER_B.setUnCompressed(this.getBotStack("quartztypelavender"));
			bot.C_QUARTZ_TYPE_MANA_B.setUnCompressed(this.getBotStack("quartztypemana"));
			bot.C_QUARTZ_TYPE_RED_B.setUnCompressed(this.getBotStack("quartztypered"));
			bot.C_QUARTZ_TYPE_SUNNY_B.setUnCompressed(this.getBotStack("quartztypesunny"));
			bot.C_SHIMMERROCK_B.setUnCompressed(this.getBotStack("shimmerrock"));
		}
		if(Configs.Custom.Mod_Stuff.EnvironmentalTech) {
			MCSBlocks.EnvironmentalTechBlock.Normal env = MCSBlocks.environmental_tech.normal;
			env.C_AETHIUM_BLOCK_B.setUnCompressed(this.getEnvStack("aethium"));
			env.C_MICA_BLOCK_B.setUnCompressed(this.getEnvStack("mica"));
			env.C_LITHERITE_BLOCK_B.setUnCompressed(this.getEnvStack("litherite"));
			env.C_ERODIUM_BLOCK_B.setUnCompressed(this.getEnvStack("erodium"));
			env.C_KYRONITE_BLOCK_B.setUnCompressed(this.getEnvStack("kyronite"));
			env.C_PLADIUM_BLOCK_B.setUnCompressed(this.getEnvStack("pladium"));
			env.C_IONITE_BLOCK_B.setUnCompressed(this.getEnvStack("ionite"));
		}
		if(Configs.Custom.Mod_Stuff.ProjectE) {
			MCSBlocks.ProjectEBlock.Normal pe = MCSBlocks.projecte.normal;
			pe.C_DARK_MATTER_B.setUnCompressed(this.getPEStack("matter_block"));
			pe.C_RED_MATTER_B.setUnCompressed(this.getPEStack("matter_block", 1));
			pe.C_ALCHEMICAL_COAL_B.setUnCompressed(this.getPEStack("fuel_block"));
			pe.C_MOBIUS_FUEL_B.setUnCompressed(this.getPEStack("fuel_block", 1));
			pe.C_AETERNALIS_FULE_B.setUnCompressed(this.getPEStack("fuel_block", 2));
		}
		if(Configs.Custom.Mod_Stuff.Tconstruct) {
			MCSBlocks.TconstructBlock.Normal tc = MCSBlocks.tconstruct.normal;
			tc.C_SOIL_B.setUnCompressed(this.getTCStack("soil"));
			tc.C_SEARED_B.setUnCompressed(this.getTCStack("seared", 3));
			tc.C_Cobalt_B.setUnCompressed(this.getTCStack("metal"));
			tc.C_Ardite_B.setUnCompressed(this.getTCStack("metal", 1));
			tc.C_Manyullyn_B.setUnCompressed(this.getTCStack("metal", 2));
			tc.C_Knightslime_B.setUnCompressed(this.getTCStack("metal", 3));
			tc.C_Pigiron_B.setUnCompressed(this.getTCStack("metal", 4));
			tc.C_Alubrass_B.setUnCompressed(this.getTCStack("metal", 5));
			tc.C_pearl_B.setUnCompressed(this.getTCStack("metal", 6));
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

	private ItemStack getTCStack(String name) {
		return this.getTCStack(name, 0);
	}

	private ItemStack getTCStack(String name, int meta) {
		return this.getStack("tconstruct", name, meta);
	}

	private ItemStack getPEStack(String name) {
		return this.getPEStack(name, 0);
	}

	private ItemStack getPEStack(String name, int meta) {
		return this.getStack("projecte", name, meta);
	}

	private ItemStack getEnvStack(String name) {
		return this.getEnvStack(name, 0);
	}

	private ItemStack getEnvStack(String name, int meta) {
		return this.getStack("environmentaltech", name, meta);
	}

	private ItemStack getBotStack(String name) {
		return this.getBotStack(name, 0);
	}

	private ItemStack getBotStack(String name, int meta) {
		return this.getStack("botania", name, meta);
	}

	private ItemStack getAEStack(IBlockDefinition aeBlock) {
		return aeBlock.maybeStack(1).get();
	}

	private ItemStack getStack(String modid, String name, int meta) {
		return new ItemStack(Item.getByNameOrId(modid + ":" + name), 1, meta);
	}
}