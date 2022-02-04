//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.util.base;

import java.util.Set;

import cat.jiu.core.api.IHasModel;
import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.init.MCSItems;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemTool {
	protected final String name;
	protected final CreativeTabs tab;
	protected final ItemTool baseItem;
	protected final String modid;
	protected final Set<Block> harvestBlocks;
	
	public BaseItemTool(String name, ItemTool baseItem, String modid, CreativeTabs tab, Set<Block> harvestBlocks) {
		this.name = name;
		this.tab = tab;
		this.baseItem = baseItem;
		this.modid = modid;
		this.harvestBlocks = harvestBlocks;
		
		if(!modid.equals("custom")) {
			MCSItems.TOOLS.add(this);
			MCSItems.TOOLS_NAME.add(name);
			MCSItems.TOOLS_MAP.put(name, this);
		}
		
		this.LEVEL_1 = new MCSTool(name, 0, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_2 = new MCSTool(name, 1, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_3 = new MCSTool(name, 2, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_4 = new MCSTool(name, 3, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_5 = new MCSTool(name, 4, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_6 = new MCSTool(name, 5, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_7 = new MCSTool(name, 6, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_8 = new MCSTool(name, 7, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_9 = new MCSTool(name, 8, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_10 = new MCSTool(name, 9, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_11 = new MCSTool(name, 10, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_12 = new MCSTool(name, 11, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_13 = new MCSTool(name, 12, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_14 = new MCSTool(name, 13, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_15 = new MCSTool(name, 14, modid, baseItem, tab, harvestBlocks);
		this.LEVEL_16 = new MCSTool(name, 15, modid, baseItem, tab, harvestBlocks);
	}
	
	public MCSTool withMeta(int meta) {
		switch (meta) {
			default: return this.LEVEL_1;
			case 1: return this.LEVEL_2;
			case 2: return this.LEVEL_3;
			case 3: return this.LEVEL_4;
			case 4: return this.LEVEL_5;
			case 5: return this.LEVEL_6;
			case 6: return this.LEVEL_7;
			case 7: return this.LEVEL_8;
			case 8: return this.LEVEL_9;
			case 9: return this.LEVEL_10;
			case 10: return this.LEVEL_11;
			case 11: return this.LEVEL_12;
			case 12: return this.LEVEL_13;
			case 13: return this.LEVEL_14;
			case 14: return this.LEVEL_15;
			case 15: return this.LEVEL_16;
		}
	}
	
	public final MCSTool LEVEL_1;
	public final MCSTool LEVEL_2;
	public final MCSTool LEVEL_3;
	public final MCSTool LEVEL_4;
	public final MCSTool LEVEL_5;
	public final MCSTool LEVEL_6;
	public final MCSTool LEVEL_7;
	public final MCSTool LEVEL_8;
	public final MCSTool LEVEL_9;
	public final MCSTool LEVEL_10;
	public final MCSTool LEVEL_11;
	public final MCSTool LEVEL_12;
	public final MCSTool LEVEL_13;
	public final MCSTool LEVEL_14;
	public final MCSTool LEVEL_15;
	public final MCSTool LEVEL_16;
	
	public static class MCSTool extends ItemTool implements IHasModel {
		private final String name;
		private final int meta;
		private final String modid;
		private final ItemTool baseItem;
		
		public MCSTool(String name, int meta, String modid, ItemTool baseItem, CreativeTabs tab, Set<Block> effectiveBlocks) {
			super(null, effectiveBlocks);
			this.name = name;
			this.meta = meta;
			this.modid = modid;
			this.baseItem = baseItem;
			
			super.setCreativeTab(tab);
			super.setHasSubtypes(false);
			super.setRegistryName(new ResourceLocation(MCS.MODID, this.name + "_" + this.meta));
			
			if(!modid.equals("custom")) {
				MCSItems.ITEMS.add(this);
			}
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void getItemModel() {
			new RegisterModel(MCS.MODID).registerItemModel(this, this.modid + "/item/tools/tool/" + this.name + "/", this.name + "_" + this.meta);
		}
	}
}
