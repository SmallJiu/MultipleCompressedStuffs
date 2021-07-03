package cat.jiu.multiple_compressed_blocks.server.init;

import java.util.ArrayList;
import java.util.List;

import cat.jiu.multiple_compressed_blocks.blocks.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class InitBlock {
	
	public static final List<Block> BLOCKS = new ArrayList<>();
	
	public static final Block C_BONE_B = new BlockCompressedBoneBlock(Blocks.BONE_BLOCK, "minecraft");
	public static final Block C_COAL_B = new BlockCompressedCoal(Blocks.COAL_BLOCK, "minecraft");
	public static final Block C_COBBLE_STONE_B = new BlockCompressedCobbleStone(Blocks.COBBLESTONE, "minecraft");
	public static final Block C_DIAMOND_B = new BlockCompressedDiamond(Blocks.DIAMOND_BLOCK, "minecraft");
	public static final Block C_DIRT_B = new BlockCompressedDirt(Blocks.DIRT, "minecraft");
	public static final Block C_EMERALD_B = new BlockCompressedEmerald(Blocks.EMERALD_BLOCK, "minecraft");
	public static final Block C_GLASS_B = new BlockCompressedGlass(Blocks.GLASS, "minecraft");
	public static final Block C_GLOW_STONE_B = new BlockCompressedGlowStone(Blocks.GLOWSTONE, "minecraft");
	public static final Block C_GOLD_B = new BlockCompressedGold(Blocks.GOLD_BLOCK, "minecraft");
	public static final Block C_GRAVEL_B = new BlockCompressedGravel(Blocks.GRAVEL, "minecraft");
	public static final Block C_ICE_B = new BlockCompressedIce(Blocks.ICE, "minecraft");
	public static final Block C_IRON_B = new BlockCompressedIron(Blocks.IRON_BLOCK, "minecraft");
	public static final Block C_MELON_B = new BlockCompressedMelonBlock(Blocks.MELON_BLOCK, "minecraft");
	public static final Block C_NETHERRACK_B = new BlockCompressedNetherrack(Blocks.NETHERRACK, "minecraft");
	public static final Block C_PUMPKIN_B = new BlockCompressedPumpkin(Blocks.PUMPKIN, "minecraft");
	public static final Block C_RED_STONE_B = new BlockCompressedRedStone(Blocks.REDSTONE_BLOCK, "minecraft");
	public static final Block C_SAND_B = new BlockCompressedSand(Blocks.SAND, "minecraft");
	public static final Block C_SNOW_B = new BlockCompressedSnow(Blocks.SNOW, "minecraft");
	public static final Block C_STONE_B = new BlockCompressedStone(Blocks.STONE, "minecraft");
	public static final Block C_WOOL_B = new BlockCompressedWool(Blocks.WOOL, "minecraft");
}
