package cat.jiu.multiple_compressed_blocks.server.init;

import cat.jiu.multiple_compressed_blocks.blocks.BlockCompressedDiamond.BlockType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InitOreDict {
	public static void register() {
		registerModOreDict();
	}
	
	private static void registerModOreDict() {
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xBoneBlock", InitBlock.C_BONE_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xCoalBlock", InitBlock.C_COAL_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xCobbleStone", InitBlock.C_COBBLE_STONE_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xDiamondBlock", InitBlock.C_DIAMOND_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xDirt", InitBlock.C_DIRT_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xEmeraldBlock", InitBlock.C_EMERALD_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xGlass", InitBlock.C_GLASS_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xGlowStone", InitBlock.C_GLOW_STONE_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xGoldBlock", InitBlock.C_GOLD_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xGravel", InitBlock.C_GRAVEL_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xIce", InitBlock.C_ICE_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xIronBlock", InitBlock.C_IRON_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xMelonBlock", InitBlock.C_MELON_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xNetherrack", InitBlock.C_NETHERRACK_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xPumpkinBlock", InitBlock.C_PUMPKIN_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xRedstoneBlock", InitBlock.C_RED_STONE_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xSand", InitBlock.C_SAND_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xSnowBlock", InitBlock.C_SNOW_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xStone", InitBlock.C_STONE_B, meta);
		}
		
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			register("compressed" + (meta+1) + "xWool", InitBlock.C_WOOL_B, meta);
		}
	}
	
	public static void register(String oreDict, Item itemIn, int meta) {
		OreDictionary.registerOre(oreDict, new ItemStack(itemIn, 1, meta));
	}
	
	public static void register(String oreDict, Block blockIn, int meta) {
		register(oreDict, Item.getItemFromBlock(blockIn), meta);
	}
}
