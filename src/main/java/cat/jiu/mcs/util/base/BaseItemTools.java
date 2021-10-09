package cat.jiu.mcs.util.base;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class BaseItemTools {
	
	public static class BaseItemTool extends ItemTool{
		protected final String name;
		protected final CreativeTabs tab;
		protected final ItemStack baseItem;
		protected final String langModID;

		public BaseItemTool(float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn, Set<Block> effectiveBlocksIn, String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
			super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
			this.name = name;
			this.tab = tab;
			this.baseItem = baseItem;
			this.langModID = langModId;
		}
		
		public BaseItemTool(ToolMaterial materialIn, Set<Block> effectiveBlocksIn, String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
			this(0.0F, 0.0F, materialIn, effectiveBlocksIn, langModId, baseItem, langModId, tab, hasSubtypes);
		}
	}
	
	public static class BaseItemToolSword extends ItemSword{
		protected final String name;
		protected final CreativeTabs tab;
		protected final ItemStack baseItem;
		protected final String langModID;

		public BaseItemToolSword(ToolMaterial material, String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
			super(material);
			this.name = name;
			this.tab = tab;
			this.baseItem = baseItem;
			this.langModID = langModId;
		}
	}
	
	public static class BaseItemToolPickaxe extends ItemPickaxe{
		protected final String name;
		protected final CreativeTabs tab;
		protected final ItemStack baseItem;
		protected final String langModID;

		public BaseItemToolPickaxe(ToolMaterial material, String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
			super(material);
			this.name = name;
			this.tab = tab;
			this.baseItem = baseItem;
			this.langModID = langModId;
		}
	}
	
	public static class BaseItemToolShovel extends ItemSpade{
		protected final String name;
		protected final CreativeTabs tab;
		protected final ItemStack baseItem;
		protected final String langModID;

		public BaseItemToolShovel(ToolMaterial material, String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
			super(material);
			this.name = name;
			this.tab = tab;
			this.baseItem = baseItem;
			this.langModID = langModId;
		}
	}
	
	public static class BaseItemToolAxe extends ItemAxe{
		protected final String name;
		protected final CreativeTabs tab;
		protected final ItemStack baseItem;
		protected final String langModID;

		public BaseItemToolAxe(ToolMaterial material, String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
			super(material);
			this.name = name;
			this.tab = tab;
			this.baseItem = baseItem;
			this.langModID = langModId;
			this.getToolMaterialName();
		}
	}
	
	public static class BaseItemToolHoe extends ItemHoe{
		protected final String name;
		protected final CreativeTabs tab;
		protected final ItemStack baseItem;
		protected final String langModID;

		public BaseItemToolHoe(String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
			super(getMaterial(baseItem.getItem().getToolClasses(baseItem).toString()));
			this.name = name;
			this.tab = tab;
			this.baseItem = baseItem;
			this.langModID = langModId;
		}
	}
	
	private static ToolMaterial getMaterial(String name) {
		if(ToolMaterial.WOOD.name().equals(name)) {
			return ToolMaterial.WOOD;
		}else if(ToolMaterial.STONE.name().equals(name)) {
			return ToolMaterial.STONE;
		}else if(ToolMaterial.IRON.name().equals(name)) {
			return ToolMaterial.IRON;
		}else if(ToolMaterial.GOLD.name().equals(name)) {
			return ToolMaterial.GOLD;
		}else if(ToolMaterial.DIAMOND.name().equals(name)) {
			return ToolMaterial.DIAMOND;
		}else {
			return ToolMaterial.WOOD;
		}
	}
}
