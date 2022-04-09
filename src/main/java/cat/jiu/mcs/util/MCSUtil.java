package cat.jiu.mcs.util;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.base.BaseBlockItem;
import cat.jiu.mcs.util.base.sub.BaseItemFood;
import cat.jiu.mcs.util.base.sub.tool.BaseItemAxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemHoe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemPickaxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemShovel;
import cat.jiu.mcs.util.base.sub.tool.BaseItemSword;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCSUtil {
	public static final InfoUtil info = new InfoUtil();
	public static final ItemUtil item = new ItemUtil();

	public static class ItemUtil {
		public ItemStack getStack(String stack) throws NumberFormatException {
			if(stack.contains("@")) {
				String[] name = JiuUtils.other.custemSplitString(stack.toLowerCase(), "@");
				Item item = null;
				int meta = 0;
				int amount = 1;

				if(name.length == 3) {
					item = Item.getByNameOrId(name[0]);
					amount = Integer.parseInt(name[1]);
					meta = Integer.parseInt(name[2]);
				}else if(name.length == 2) {
					item = Item.getByNameOrId(name[0]);
					amount = Integer.parseInt(name[1]);
				}else {
					item = Item.getByNameOrId(name[0]);
				}
				if(item != null) {
					return new ItemStack(item, amount, meta);
				}
			}else {
				return new ItemStack(Item.getByNameOrId(stack));
			}

			return null;
		}

		public double getMetaValue(double baseValue, ItemStack stack) {
			return this.getMetaValue(baseValue, stack.getMetadata());
		}

		public double getMetaValue(double baseValue, int meta) {
			return baseValue + (baseValue * ((meta + 1) * 1.527));
		}

		public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> items) {
			if(item.getHasSubtypes()) {
				if(this.isInCreativeTab(item, tab)) {
					for(ModSubtypes type : ModSubtypes.values()) {
						items.add(new ItemStack(item, 1, type.getMeta()));
					}
					items.add(new ItemStack(item, 1, Short.MAX_VALUE - 1));
				}
			}else {
				item.getSubItems(tab, items);
			}
		}

		private boolean isInCreativeTab(Item item, CreativeTabs targetTab) {
			for(CreativeTabs tab : item.getCreativeTabs())
				if(tab == targetTab)
					return true;
			CreativeTabs creativetabs = item.getCreativeTab();
			return creativetabs != null && (targetTab == CreativeTabs.SEARCH || targetTab == creativetabs);
		}

		public ItemStack getUnCompressed(ItemStack item) {
			if(item == null) {
				return ItemStack.EMPTY;
			}
			return this.getUnCompressed(item.getItem());
		}

		public ItemStack getUnCompressed(Item item) {
			if(item == null || item == Items.AIR) {
				return ItemStack.EMPTY;
			}
			if(item instanceof ICompressedStuff) {
				return ((ICompressedStuff) item).getUnCompressedStack();
			}
			return ItemStack.EMPTY;
		}

		public boolean isCompressedItem(ItemStack stack) {
			if(item == null) {
				return false;
			}
			return this.isCompressedItem(stack.getItem());
		}

		public boolean isCompressedItem(Item item) {
			if(item == null || item == Items.AIR) {
				return false;
			}
			if(item instanceof ICompressedStuff) {
				return true;
			}
			return false;
		}

		public int getUnCompressedBurnTime(ItemStack item) {
			if(item == null) {
				return -1;
			}
			return this.getUnCompressedBurnTime(item.getItem());
		}

		public int getUnCompressedBurnTime(Item item) {
			if(item == null || item == Items.AIR) {
				return -1;
			}
			if(item instanceof ICompressedStuff) {
				return ((ICompressedStuff) item).getUnCompressedBurnTime();
			}
			return -1;
		}

		public boolean canMakeDefaultStackRecipe(ItemStack item) {
			if(item == null) {
				return false;
			}
			return this.canMakeDefaultStackRecipe(item.getItem());
		}

		public boolean canMakeDefaultStackRecipe(Item item) {
			if(item == null || item == Items.AIR) {
				return false;
			}
			if(item instanceof ICompressedStuff) {
				return ((ICompressedStuff) item).canMakeDefaultStackRecipe();
			}
			return false;
		}
	}

	public static class InfoUtil {
		@SideOnly(Side.CLIENT)
		public void addMetaInfo(int meta, List<String> tooltip, List<String> infos, Map<Integer, List<String>> metaInfos) {
			if(infos != null && !infos.isEmpty()) {
				for(String info : infos) {
					tooltip.add(info);
				}
			}else if(metaInfos != null && !metaInfos.isEmpty()) {
				if(metaInfos.containsKey(meta)) {
					for(String str : metaInfos.get(meta)) {
						tooltip.add(str);
					}
				}
			}
		}

		@SideOnly(Side.CLIENT)
		public void addShiftInfo(int meta, List<String> tooltip, List<String> shiftInfos, Map<Integer, List<String>> metaShiftInfos) {
			if(shiftInfos != null && !shiftInfos.isEmpty()) {
				if(GuiScreen.isShiftKeyDown()) {
					for(String info : shiftInfos) {
						tooltip.add(info);
					}
				}else {
					tooltip.add(I18n.format("info.mcs.shift"));
				}
			}else if(metaShiftInfos != null && !metaShiftInfos.isEmpty()) {
				if(metaShiftInfos.containsKey(meta)) {
					if(GuiScreen.isShiftKeyDown()) {
						for(String info : metaShiftInfos.get(meta)) {
							tooltip.add(info);
						}
					}else {
						tooltip.add(I18n.format("info.mcs.shift"));
					}
				}
			}
		}

		@SideOnly(Side.CLIENT)
		public void addInfoStackInfo(int meta, ItemStack infoStack, World world, List<String> tooltip, ITooltipFlag advanced, ItemStack baseToolStack, Map<Integer, ItemStack> infoStacks) {
			if(infoStack != null) {
				infoStack.getItem().addInformation(infoStack, world, tooltip, advanced);
				return;
			}else if(infoStacks != null && !infoStacks.isEmpty()) {
				if(infoStacks.containsKey(meta) && infoStacks.get(meta) != null) {
					infoStacks.get(meta).getItem().addInformation(infoStacks.get(meta), world, tooltip, advanced);
					return;
				}
			}
			baseToolStack.getItem().addInformation(baseToolStack, world, tooltip, advanced);
			return;
		}

		@SideOnly(Side.CLIENT)
		public void addCompressedInfo(int meta, List<String> tooltip, String name, Item item) {
			int level = meta + 1;
			if(Configs.use_3x3_recipes) {
				if(Configs.Tooltip_Information.show_specific_number) {
					if(!Configs.Tooltip_Information.can_custom_specific_number) {
						switch(meta) {
							case 0:
								tooltip.add("9 x " + name);
								break;
							case 1:
								tooltip.add("81 x " + name);
								break;
							case 2:
								tooltip.add("729 x " + name);
								break;
							case 3:
								tooltip.add("6,561 x " + name);
								break;
							case 4:
								tooltip.add("59,049 x " + name);
								break;
							case 5:
								tooltip.add("531,441 x " + name);
								break;
							case 6:
								tooltip.add("4,782,969 x " + name);
								break;
							case 7:
								tooltip.add("43,046,721 x " + name);
								break;
							case 8:
								tooltip.add("387,420,489 x " + name);
								break;
							case 9:
								tooltip.add("3,486,784,401 x " + name);
								break;
							case 10:
								tooltip.add("31,381,059,609 x " + name);
								break;
							case 11:
								tooltip.add("282,429,536,481 x " + name);
								break;
							case 12:
								tooltip.add("2,541,865,828,329 x " + name);
								break;
							case 13:
								tooltip.add("22,876,792,454,961 x " + name);
								break;
							case 14:
								tooltip.add("205,891,132,094,649 x " + name);
								break;
							case 15:
								tooltip.add("1,853,020,188,851,841 x " + name);
								break;
						}
					}else {
						tooltip.add(I18n.format("info.compressed_3x3_" + level + ".name", 1) + " x " + name);
					}
				}else {
					tooltip.add(new BigInteger("9").pow(level) + " x " + name);
				}
			}else {
				if(Configs.Tooltip_Information.show_specific_number) {
					if(!Configs.Tooltip_Information.can_custom_specific_number) {
						switch(meta) {
							case 0:
								tooltip.add("4 x " + name);
								break;
							case 1:
								tooltip.add("16 x " + name);
								break;
							case 2:
								tooltip.add("64 x " + name);
								break;
							case 3:
								tooltip.add("256 x " + name);
								break;
							case 4:
								tooltip.add("1,024 x " + name);
								break;
							case 5:
								tooltip.add("4,096 x " + name);
								break;
							case 6:
								tooltip.add("16,384 x " + name);
								break;
							case 7:
								tooltip.add("65,536 x " + name);
								break;
							case 8:
								tooltip.add("262,144 x " + name);
								break;
							case 9:
								tooltip.add("1,048,876 x " + name);
								break;
							case 10:
								tooltip.add("4,194,304 x " + name);
								break;
							case 11:
								tooltip.add("16,777,216 x " + name);
								break;
							case 12:
								tooltip.add("67,108,864 x " + name);
								break;
							case 13:
								tooltip.add("268,435,456 x " + name);
								break;
							case 14:
								tooltip.add("1,073,741,824 x " + name);
								break;
							case 15:
								tooltip.add("4,294,967,296 x " + name);
								break;
						}
					}else {
						tooltip.add(I18n.format("info.compressed_2x2_" + level + ".name", 1) + " x " + name);
					}
				}else {
					tooltip.add(new BigInteger("4").pow(level) + " x " + name);
				}
			}

			if(Configs.Tooltip_Information.show_owner_type) {
				String entry = "item";
				if(item instanceof BaseBlockItem) {
					entry = "block";
				}else if(item instanceof BaseItemFood) {
					entry = "food";
				}else if(item instanceof BaseItemSword) {
					entry = "sword";
				}else if(item instanceof BaseItemPickaxe) {
					entry = "pickaxe";
				}else if(item instanceof BaseItemShovel) {
					entry = "shovel";
				}else if(item instanceof BaseItemAxe) {
					entry = "axe";
				}else if(item instanceof BaseItemHoe) {
					entry = "hoe";
				}

				tooltip.add(I18n.format("info.mcs.owner_entry") + ": " + TextFormatting.AQUA.toString() + I18n.format("info.mcs.entry." + entry + ".name"));
			}
		}
	}
}
