package cat.jiu.mcs.util.init;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.JiuUtils;
import cat.jiu.mcs.util.JiuUtils.ItemUtils;
import cofh.thermalfoundation.block.BlockGlass;
import cofh.thermalfoundation.block.BlockGlassAlloy;
import cofh.thermalfoundation.block.BlockRockwool;
import cofh.thermalfoundation.init.TFBlocks;
import cofh.thermalfoundation.init.TFItems;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class MCSOreDict {
	private static final ItemUtils item = JiuUtils.item;
	
	public static void register() {
		try {
			mc();
			item();
			block();
			mod();
			MCSItems.registerOreDict();
			MCSBlocks.registerOreDict();
			custom();
		} catch (Exception e) { }
	}
	
	private static void mc() {
		mcItem();
		mcBlock();
	}
	
	private static void item() {
		food();
		tool();
	}
	
	private static void mod() {
		modItem();
		modBlock();
	}
	
	private static void modItem() {
		itemThermalFoundation();
	}

	private static void block() {
		has();
		blockMinecraft();
	}
	
	private static void modBlock() {
		blockThermalFoundation();
		blockProjectE();
		blockBotania();
		blockDraconicEvolution();
	}
	
	private static void has() {
		blockHasMinecraft();
		blockHasThermalFoundation();
		blockHasProjectE();
		blockHasBotania();
	}
	
	private static void mcItem() {
		mcFood();
		/*
		item.registerOre("coal", Items.COAL, 0);
		item.registerOre("charcoal", Items.COAL, 1);
		item.registerOre("tearghast", Items.GHAST_TEAR);
		item.registerOre("bucketMilk", Items.MILK_BUCKET);
		*/
	}
	
	private static void mcFood() {
		/*
		item.registerOre("beef", Items.BEEF);
		item.registerOre("cookedBeef", Items.COOKED_BEEF);
		item.registerOre("cookie", Items.COOKIE);
		item.registerOre("mutton", Items.MUTTON);
		item.registerOre("cookedMutton", Items.COOKED_MUTTON);
		item.registerOre("porkchop", Items.PORKCHOP);
		item.registerOre("cookedPorkchop", Items.COOKED_PORKCHOP);
		item.registerOre("chicken", Items.CHICKEN);
		item.registerOre("cookedChicken", Items.COOKED_CHICKEN);
		item.registerOre("rabbit", Items.RABBIT);
		item.registerOre("cookedRabbit", Items.COOKED_RABBIT);
		item.registerOre("fishSalmon", Items.FISH, 1);
		item.registerOre("cookedFishSalmon", Items.COOKED_FISH, 1);
		item.registerOre("fish", Items.FISH);
		item.registerOre("rawFish", Items.FISH);
		item.registerOre("cookedFish", Items.COOKED_FISH);
		item.registerOre("bakedPotato", Items.BAKED_POTATO);
		item.registerOre("fishPuffer", Items.FISH, 3);
		item.registerOre("stewRabbit", Items.RABBIT_STEW);
		item.registerOre("goldenCarrot", Items.GOLDEN_CARROT);
		item.registerOre("fishClown", Items.FISH, 2);
		item.registerOre("enchantedGoldenApple", Items.GOLDEN_APPLE, 1);
		item.registerOre("goldenApple", Items.GOLDEN_APPLE, 0);
		item.registerOre("apple", Items.APPLE);
		item.registerOre("melonSpeckled", Items.SPECKLED_MELON);
		item.registerOre("melon", Items.MELON);
		item.registerOre("bread", Items.BREAD);
		item.registerOre("fleshRotten", Items.ROTTEN_FLESH);
		item.registerOre("pumpkinPie", Items.PUMPKIN_PIE);
		item.registerOre("stewMushrom", Items.MUSHROOM_STEW);
		item.registerOre("soupBeetroot", Items.BEETROOT_SOUP);
		item.registerOre("beetroot", Items.BEETROOT);
		*/
	}
	
	private static void mcBlock() {
		item.registerOre("blockConcretePowder", Blocks.CONCRETE_POWDER, OreDictionary.WILDCARD_VALUE);
		item.registerOre("blockConcrete", Blocks.CONCRETE, OreDictionary.WILDCARD_VALUE);
		item.registerOre("hardenedClay", Blocks.STAINED_HARDENED_CLAY, OreDictionary.WILDCARD_VALUE);
		/*
		item.registerOre("soulSand", Blocks.SOUL_SAND);
		item.registerOre("cropPumpkin", Blocks.PUMPKIN);
		item.registerOre("cropMelon", Blocks.MELON_BLOCK);
		item.registerOre("ice", Blocks.ICE);
		
		item.registerOre("blockBrick", Blocks.BRICK_BLOCK);
		item.registerOre("blockPurpur", Blocks.PURPUR_BLOCK);
		item.registerOre("blockHay", Blocks.HAY_BLOCK);
		item.registerOre("blockBone", Blocks.BONE_BLOCK);
		item.registerOre("blockSnow", Blocks.SNOW);
		item.registerOre("blockNetherBrick", Blocks.NETHER_BRICK);
		item.registerOre("blockClay", Blocks.CLAY);
		item.registerOre("blockNetherWart", Blocks.NETHER_WART_BLOCK);
		item.registerOre("blockSponge", Blocks.SPONGE);
		
		*/
	}
	
	private static void tool() {
		
	}
	
	private static void food() {
		/*
		item.registerCompressedOre("Beef", MCSItems.minecraft.normal.food.C_BEEF_F, false);
		item.registerCompressedOre("CookedBeef", MCSItems.minecraft.normal.food.C_COOKED_BEEF_F, false);
		item.registerCompressedOre("Chicken", MCSItems.minecraft.normal.food.C_CHICKEN_F, false);
		item.registerCompressedOre("CookedChicken", MCSItems.minecraft.normal.food.C_COOKED_CHICKEN_F, false);
		item.registerCompressedOre("Potato", MCSItems.minecraft.normal.food.C_POTATO_F, false);
		item.registerCompressedOre("BakedPotato", MCSItems.minecraft.normal.food.C_BAKED_POTATO_F, false);
		item.registerCompressedOre("Rabbit", MCSItems.minecraft.normal.food.C_RABBIT_F, false);
		item.registerCompressedOre("CookedRabbit", MCSItems.minecraft.normal.food.C_COOKED_RABBIT_F, false);
		item.registerCompressedOre("Mutton", MCSItems.minecraft.normal.food.C_MUTTON_F, false);
		item.registerCompressedOre("CookedMutton", MCSItems.minecraft.normal.food.C_COOKED_MUTTON_F, false);
		item.registerCompressedOre("Porkchop", MCSItems.minecraft.normal.food.C_PORKCHOP_F, false);
		item.registerCompressedOre("CookedPorkchop", MCSItems.minecraft.normal.food.C_COOKED_PORKCHOP_F, false);
		item.registerCompressedOre("Fish", MCSItems.minecraft.normal.food.C_FISH_F, false);
		item.registerCompressedOre("CookedFish", MCSItems.minecraft.normal.food.C_COOKED_FISH_F, false);
		item.registerCompressedOre("SalmonFish", MCSItems.minecraft.normal.food.C_SALMON_FISH_F, false);
		item.registerCompressedOre("CookedSalmonFish", MCSItems.minecraft.normal.food.C_COOKED_SALMON_FISH_F, false);
		item.registerCompressedOre("Apple", MCSItems.minecraft.normal.food.C_APPLE_F, false);
		item.registerCompressedOre("Bread", MCSItems.minecraft.normal.food.C_BREAD_F, false);
		item.registerCompressedOre("ClownFish", MCSItems.minecraft.normal.food.C_CLOWN_FISH_F, false);
		item.registerCompressedOre("PufferFish", MCSItems.minecraft.normal.food.C_PUFFER_FISH_F, false);
		item.registerCompressedOre("Cookie", MCSItems.minecraft.normal.food.C_COOKIE_F, false);
		item.registerCompressedOre("RottenFlesh", MCSItems.minecraft.normal.food.C_ROTTEN_FLESH_F, false);
		item.registerCompressedOre("Carrot", MCSItems.minecraft.normal.food.C_CARROT_F, false);
		item.registerCompressedOre("GoldenCarrot", MCSItems.minecraft.normal.food.C_GOLDEN_CARROT_F, false);
		item.registerCompressedOre("PumpkinPie", MCSItems.minecraft.normal.food.C_PUMPKIN_PIE_F, false);
		item.registerCompressedOre("Beetroot", MCSItems.minecraft.normal.food.C_BEETROOT_F, false);
		item.registerCompressedOre("GoldApple", MCSItems.minecraft.normal.food.C_GOLD_APPLE_F, false);
		item.registerCompressedOre("EnchantedGoldApple", MCSItems.minecraft.normal.food.C_ENCHANTED_GOLD_APPLE_F, false);
		item.registerCompressedOre("SpeckledMelon", MCSItems.minecraft.normal.food.C_SPECKLED_MELON_F, false);
		item.registerCompressedOre("MushromStew", MCSItems.minecraft.normal.food.C_MUSHROOM_STEW_F, false);
		item.registerCompressedOre("RabbitStew", MCSItems.minecraft.normal.food.C_RABBIT_STEW_F, false);
		item.registerCompressedOre("BeetrootSoup", MCSItems.minecraft.normal.food.C_BEETROOT_SOUP_F, false);
		*/
	}
	
	@SuppressWarnings("unused")
	private static void orePlate(String oreDictName, Item itemIn) {
		item.registerCompressedOre(oreDictName, itemIn, "Plate");
	}
	
	private static void itemThermalFoundation() {
		/*
		item.registerCompressedOre("Dye", MCSItems.thermal_foundation.normal.C_DYE_I, false);
		
		orePlate("Aluminum", MCSItems.thermal_foundation.normal.C_PLATE_ALUMINUM_I);
		orePlate("Bronze", MCSItems.thermal_foundation.normal.C_PLATE_BRONZE_I);
		orePlate("Constantan", MCSItems.thermal_foundation.normal.C_PLATE_CONSTANTAN_I);
		orePlate("Copper", MCSItems.thermal_foundation.normal.C_PLATE_COPPER_I);
		orePlate("Electrum", MCSItems.thermal_foundation.normal.C_PLATE_ELECTRUM_I);
		orePlate("Enderium", MCSItems.thermal_foundation.normal.C_PLATE_ENDERIUM_I);
		orePlate("Gold", MCSItems.thermal_foundation.normal.C_PLATE_GOLD_I);
		orePlate("Invar", MCSItems.thermal_foundation.normal.C_PLATE_INVAR_I);
		orePlate("Iridium", MCSItems.thermal_foundation.normal.C_PLATE_IRIDIUM_I);
		orePlate("Iron", MCSItems.thermal_foundation.normal.C_PLATE_IRON_I);
		orePlate("Lead", MCSItems.thermal_foundation.normal.C_PLATE_LEAD_I);
		orePlate("Lumium", MCSItems.thermal_foundation.normal.C_PLATE_LUMIUM_I);
		orePlate("Mithril", MCSItems.thermal_foundation.normal.C_PLATE_MITHRIL_I);
		orePlate("Nickl", MCSItems.thermal_foundation.normal.C_PLATE_NICKL_I);
		orePlate("Platinum", MCSItems.thermal_foundation.normal.C_PLATE_PLATINUM_I);
		orePlate("Signalum", MCSItems.thermal_foundation.normal.C_PLATE_SIGNALUM_I);
		orePlate("Silver", MCSItems.thermal_foundation.normal.C_PLATE_SILVER_I);
		orePlate("Steel", MCSItems.thermal_foundation.normal.C_PLATE_STEEL_I);
		orePlate("Tin", MCSItems.thermal_foundation.normal.C_PLATE_TIN_I);
		
		item.registerCompressedOre("Ender", MCSItems.thermal_foundation.normal.C_CRYSTAL_ENDER_I, "Crystal");
		item.registerCompressedOre("Glowstone", MCSItems.thermal_foundation.normal.C_CRYSTAL_GLOWSTONE_I, "Crystal");
		item.registerCompressedOre("Redstone", MCSItems.thermal_foundation.normal.C_CRYSTAL_REDSTONE_I, "Crystal");
		
		item.registerCompressedOre("Basalz", MCSItems.thermal_foundation.normal.C_ROD_BASALZ_I, "Rod");
		item.registerCompressedOre("Blitz", MCSItems.thermal_foundation.normal.C_ROD_BLITZ_I, "Rod");
		item.registerCompressedOre("Blizz", MCSItems.thermal_foundation.normal.C_ROD_BLIZZ_I, "Rod");
		*/
		item.registerOre("tfDyes", TFItems.itemDye, OreDictionary.WILDCARD_VALUE);
	}
	
	private static void blockMinecraft() {
		/*
		item.registerCompressedOre("Prismarine", MCSBlocks.minecraft.normal.C_PRISMARINE_B, false);
		item.registerCompressedOre("BoneBlock", MCSBlocks.minecraft.normal.C_BONE_B, false);
		item.registerCompressedOre("Bedrock", MCSBlocks.minecraft.normal.C_BEDROCK_B, false);
		item.registerCompressedOre("CoalBlock", MCSBlocks.minecraft.normal.C_COAL_B, false);
		item.registerCompressedOre("CobbleStone", MCSBlocks.minecraft.normal.C_COBBLE_STONE_B, false);
		item.registerCompressedOre("DiamondBlock", MCSBlocks.minecraft.normal.C_DIAMOND_B, false);
		item.registerCompressedOre("Dirt", MCSBlocks.minecraft.normal.C_DIRT_B, false);
		item.registerCompressedOre("EmeraldBlock", MCSBlocks.minecraft.normal.C_EMERALD_B, false);
		item.registerCompressedOre("Glass", MCSBlocks.minecraft.normal.C_GLASS_B, false);
		item.registerCompressedOre("GlowStone", MCSBlocks.minecraft.normal.C_GLOW_STONE_B, false);
		item.registerCompressedOre("GoldBlock", MCSBlocks.minecraft.normal.C_GOLD_B, false);
		item.registerCompressedOre("Gravel", MCSBlocks.minecraft.normal.C_GRAVEL_B, false);
		item.registerCompressedOre("Ice", MCSBlocks.minecraft.normal.C_ICE_B, false);
		item.registerCompressedOre("IronBlock", MCSBlocks.minecraft.normal.C_IRON_B, false);
		item.registerCompressedOre("MelonBlock", MCSBlocks.minecraft.normal.C_MELON_B, false);
		item.registerCompressedOre("MagmaBlock", MCSBlocks.minecraft.normal.C_MAGMA_B, false);
		item.registerCompressedOre("Netherrack", MCSBlocks.minecraft.normal.C_NETHERRACK_B, false);
		item.registerCompressedOre("PumpkinBlock", MCSBlocks.minecraft.normal.C_PUMPKIN_B, false);
		item.registerCompressedOre("QuartzBlock", MCSBlocks.minecraft.normal.C_QUARTZ_BLOCK_B, false);
		item.registerCompressedOre("RedstoneBlock", MCSBlocks.minecraft.normal.C_RED_STONE_B, false);
		item.registerCompressedOre("Sand", MCSBlocks.minecraft.normal.C_SAND_B, false);
		item.registerCompressedOre("SnowBlock", MCSBlocks.minecraft.normal.C_SNOW_B, false);
		item.registerCompressedOre("Stone", MCSBlocks.minecraft.normal.C_STONE_B, false);
		item.registerCompressedOre("Wool", MCSBlocks.minecraft.normal.C_WOOL_B, false);
		item.registerCompressedOre("RedMushrom", MCSBlocks.minecraft.normal.C_RED_MUSHROOM_B, false);
		item.registerCompressedOre("BrownMushroom", MCSBlocks.minecraft.normal.C_BROWN_MUSHROOM_B, false);
		item.registerCompressedOre("Prismarine", MCSBlocks.minecraft.normal.C_PRISMARINE_B, false);
		item.registerCompressedOre("TNT", MCSBlocks.minecraft.normal.C_TNT_B, false);
		item.registerCompressedOre("Brick", MCSBlocks.minecraft.normal.C_BRICK_BLOCK_B, false);
		item.registerCompressedOre("SoulSand", MCSBlocks.minecraft.normal.C_SOUL_SAND_B, false);
		item.registerCompressedOre("StoneBrick", MCSBlocks.minecraft.normal.C_STONE_BRICK_B, false);
		item.registerCompressedOre("Clay", MCSBlocks.minecraft.normal.C_CLAY_B, false);
		item.registerCompressedOre("PurpurBlock", MCSBlocks.minecraft.normal.C_PURPUR_BLOCK_B, false);
		item.registerCompressedOre("SlimeBlock", MCSBlocks.minecraft.normal.C_SLIME_BLOCK_B, false);
		item.registerCompressedOre("ConcretePowderBlock", MCSBlocks.minecraft.normal.C_CONCRETE_POWDER_B, false);
		item.registerCompressedOre("ConcreteBlock", MCSBlocks.minecraft.normal.C_CONCRETE_B, false);
		item.registerCompressedOre("HardenedClayBlock", MCSBlocks.minecraft.normal.C_HARDENED_CLAY_B, false);
		item.registerCompressedOre("HayBlock", MCSBlocks.minecraft.normal.C_HAY_B, false);
		item.registerCompressedOre("EndStone", MCSBlocks.minecraft.normal.C_END_STONE_B, false);
		item.registerCompressedOre("NetherBrick", MCSBlocks.minecraft.normal.C_NETHER_BRICK_B, false);
		item.registerCompressedOre("NetherWartBlock", MCSBlocks.minecraft.normal.C_NETHER_WART_B, false);
		item.registerCompressedOre("Sponge", MCSBlocks.minecraft.normal.C_SPONGE_B, false);
		item.registerCompressedOre("PlankWood", MCSBlocks.minecraft.normal.C_PLANKS_B, false);
		item.registerCompressedOre("LogWood", MCSBlocks.minecraft.normal.C_LOG_B, false);
		item.registerCompressedOre("Obsidian", MCSBlocks.minecraft.normal.C_OBSIDIAN_B, false);
		*/
	}
	
	private static void blockThermalFoundation() {
		/*
		item.registerCompressedOre("RockWool", MCSBlocks.thermal_foundation.normal.C_ROCKWOOL_B, false);
		item.registerCompressedOre("HardenedGlass", MCSBlocks.thermal_foundation.normal.C_HARDENED_GLASS_B, false);
		item.registerCompressedOre("FuelCoke", MCSBlocks.thermal_foundation.normal.C_FUEL_COKE_B, false);
		
		item.registerCompressedOre("Aluminum", MCSBlocks.thermal_foundation.normal.C_ALUMINUM_B, false);
		item.registerCompressedOre("Copper", MCSBlocks.thermal_foundation.normal.C_COPPER_B, false);
		item.registerCompressedOre("Iridium", MCSBlocks.thermal_foundation.normal.C_IRIDIUM_B, false);
		item.registerCompressedOre("Lead", MCSBlocks.thermal_foundation.normal.C_LEAD_B, false);
		item.registerCompressedOre("Mithril", MCSBlocks.thermal_foundation.normal.C_MITHRIL_B, false);
		item.registerCompressedOre("Nickel", MCSBlocks.thermal_foundation.normal.C_NICKEL_B, false);
		item.registerCompressedOre("Platinum", MCSBlocks.thermal_foundation.normal.C_PLATINUM_B, false);
		item.registerCompressedOre("Sliver", MCSBlocks.thermal_foundation.normal.C_SILVER_B, false);
		item.registerCompressedOre("Tin", MCSBlocks.thermal_foundation.normal.C_TIN_B, false);
		
		item.registerCompressedOre("Bronze", MCSBlocks.thermal_foundation.normal.C_BRONZE_B, false);
		item.registerCompressedOre("Constantan", MCSBlocks.thermal_foundation.normal.C_CONSTANTAN_B, false);
		item.registerCompressedOre("Electrum", MCSBlocks.thermal_foundation.normal.C_ELECTRUM_B, false);
		item.registerCompressedOre("Enderium", MCSBlocks.thermal_foundation.normal.C_ENDERIUM_B, false);
		item.registerCompressedOre("Invar", MCSBlocks.thermal_foundation.normal.C_INVAR_B, false);
		item.registerCompressedOre("Lumium", MCSBlocks.thermal_foundation.normal.C_LUMIUM_B, false);
		item.registerCompressedOre("Signalum", MCSBlocks.thermal_foundation.normal.C_SIGNALUM_B, false);
		item.registerCompressedOre("Steel", MCSBlocks.thermal_foundation.normal.C_STEEL_B, false);
		*/
		for(BlockGlass.Type type : BlockGlass.Type.values()) {
//			item.registerOre("hardenGlass", TFBlocks.blockGlass, type.getMetadata());
			item.registerOre(type.getName() + "HardenedGlass", TFBlocks.blockGlass, type.getMetadata());
		}
		
		for(BlockGlassAlloy.Type type : BlockGlassAlloy.Type.values()) {
//			item.registerOre("hardenGlass", TFBlocks.blockGlassAlloy, type.getMetadata());
			item.registerOre(type.getName() + "HardenedGlass", TFBlocks.blockGlassAlloy, type.getMetadata());
		}
		
		for(BlockRockwool.Type type : BlockRockwool.Type.values()) {
			item.registerOre("rockWool", TFBlocks.blockRockwool, type.getMetadata());
			item.registerOre(type.getName() + "RockWool", TFBlocks.blockRockwool, type.getMetadata());
		}
		
	}
	
	private static void blockProjectE() {
		/*
		item.registerCompressedOre("AeternalisFuel", MCSBlocks.projecte.normal.C_AETERNALIS_FULE_B, false);
		item.registerCompressedOre("AlchemicalCoal", MCSBlocks.projecte.normal.C_ALCHEMICAL_COAL_B, false);
		item.registerCompressedOre("DarkMatter", MCSBlocks.projecte.normal.C_DARK_MATTER_B, false);
		item.registerCompressedOre("MobiusFuel", MCSBlocks.projecte.normal.C_MOBIUS_FUEL_B, false);
		item.registerCompressedOre("RedMatter", MCSBlocks.projecte.normal.C_RED_MATTER_B, false);
		*/
		item.registerOre("blockDarkMatter", ObjHandler.matterBlock, 0);
		item.registerOre("blockRedMatter", ObjHandler.matterBlock, 1);
		item.registerOre("blockAlchemicalCoal", ObjHandler.fuelBlock, 0);
		item.registerOre("blockMobiusFuel", ObjHandler.fuelBlock, 1);
		item.registerOre("blockAeternalisFuel", ObjHandler.fuelBlock, 2);
		
	}
	
	private static void blockBotania() {
		for(EnumDyeColor type : EnumDyeColor.values()) {
			String name = type.getUnlocalizedName();
			String colorName = name.substring(0, 1).toUpperCase() + name.substring(1);
			
			item.registerOre("block" + colorName + "Petal", vazkii.botania.common.block.ModBlocks.petalBlock, type.getMetadata());
		}
		item.registerOre("petal", vazkii.botania.common.item.ModItems.petal, OreDictionary.WILDCARD_VALUE);
		item.registerOre("botDye", vazkii.botania.common.item.ModItems.dye, OreDictionary.WILDCARD_VALUE);
		item.registerOre("blockPetal", vazkii.botania.common.block.ModBlocks.petalBlock, OreDictionary.WILDCARD_VALUE);
	}
	
	private static void blockDraconicEvolution() {
		/*
		item.registerCompressedOre("DraconicBlock", MCSBlocks.draconic_evolution.normal.C_DRACONIC_BLOCK_B, false);
		item.registerCompressedOre("DraconiumBlock", MCSBlocks.draconic_evolution.normal.C_DRACONIUM_BLOCK_B, false);
		item.registerCompressedOre("InfusedObsidian", MCSBlocks.draconic_evolution.normal.C_INFUSED_OBSIDIAN_BLOCK_B, false);
		*/
	}
	
	private static void blockHasMinecraft() {
		/*
		item.registerCompressedOre("Flint", MCSBlocks.minecraft.has.C_FLINT_B, true);
		item.registerCompressedOre("ChorusFruit", MCSBlocks.minecraft.has.C_CHORUS_FRUIT_B, true);
		item.registerCompressedOre("Blaze", MCSBlocks.minecraft.has.C_BLAZE_ROD_B, true);
		item.registerCompressedOre("Charcoal", MCSBlocks.minecraft.has.C_CHAR_COAL_B, true);
		item.registerCompressedOre("EnderPearl", MCSBlocks.minecraft.has.C_ENDER_PEARL_B, true);
		item.registerCompressedOre("NetherStar", MCSBlocks.minecraft.has.C_NETHER_STAR_B, true);
		item.registerCompressedOre("GhastTear", MCSBlocks.minecraft.has.C_GHAST_TEAR_B, true);
		*/
	}
	
	private static void blockHasThermalFoundation() {
		
	}
	
	private static void blockHasProjectE(){
		/*
		item.registerOre("dustLowCovalence", ObjHandler.covalence, 0);
		item.registerOre("dustMediumCovalence", ObjHandler.covalence, 1);
		item.registerOre("dustHighCovalence", ObjHandler.covalence, 2);
		
		item.registerCompressedOre("HighCovalenceDust", MCSBlocks.projecte.has.C_HIGH_COVALENCE_B, true);
		item.registerCompressedOre("LowCovalenceDust", MCSBlocks.projecte.has.C_LOW_COVALENCE_B, true);
		item.registerCompressedOre("MediumCovalenceDust", MCSBlocks.projecte.has.C_MEDIUM_COVALENCE_B, true);
		*/
	}
	
	private static void blockHasBotania() {
		
	}
	
	private static void custom() {
		String[] ore = Configs.custom.register_ore.ore_dict_register;
		for(int i = 1; i < Configs.custom.register_ore.ore_dict_register.length; ++i) {
			String[] strore = JiuUtils.other.custemSplitString(ore[i], "|");
			
			if(strore.length == 3) {
				String name = strore[0];
				int meta = -1;
				String oredict = strore[2];
				
				try {
					meta = new Integer(strore[1]);
				}catch (Exception e) {
					MCS.instance.log.fatal(strore[0] + ": " + (strore[1]) + " is not Number!");
				}
				
				if(meta != -1) {
					item.registerOre(oredict, Item.getByNameOrId(name), meta);
				}
			}else {
				MCS.instance.log.fatal(strore[0] + ": " + (strore.length - 1) + " is not multiple of 3!");
			}
		}
	}
}
