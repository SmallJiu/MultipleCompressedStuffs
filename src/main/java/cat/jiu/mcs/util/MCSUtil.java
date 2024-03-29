package cat.jiu.mcs.util;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.helpers.JsonUtil;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.api.ITooltipString;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.base.BaseCompressedBlockItem;
import cat.jiu.mcs.util.base.sub.BaseCompressedFood;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedAxe;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedHoe;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedPickaxe;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedShovel;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedSword;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCSUtil {
	public static final InfoUtil info = new InfoUtil();
	public static final ItemUtil item = new ItemUtil();

	public static class ItemUtil {
		public List<String> getOreDict(ItemStack stack){
			List<String> ores = Lists.newArrayList();
			for(String ore : JiuUtils.item.getOreDict(stack)) {
				if(this.test(ore)) {
					ores.add(ore);
				}
			}
			return ores;
		}
		private boolean test(String ore) {
			boolean flag = false;
			for(String ores : Configs.cancel_oredict_for_recipe) {
				if(ores.equalsIgnoreCase(ore)) {
					flag = true;
					break;
				}
			}
			return !flag;
		}
		
		public double getMetaValue(double baseValue, ItemStack stack) {
			return this.getMetaValue(baseValue, stack.getMetadata());
		}
		
		public double getMetaValue(double baseValue, int meta) {
			double value = baseValue + (baseValue * ((meta + 1) * 1.527));
			return value > Integer.MAX_VALUE ? Integer.MAX_VALUE : value;
		}
		
		public double getMetaValue(double baseValue, ItemStack stack, double custom) {
			return this.getMetaValue(baseValue, stack.getMetadata(), custom);
		}
		
		public double getMetaValue(double baseValue, int meta, double custom) {
			double value = baseValue * ((meta + 1) * custom);
			return value > Integer.MAX_VALUE ? Integer.MAX_VALUE : value;
		}
		
		public void getSubItems(ICompressedStuff compitem, CreativeTabs tab, NonNullList<ItemStack> items) {
			Item item = compitem.getAsItem();
			if(item == null || item == Items.AIR) return;
			if(this.isInCreativeTab(item, tab)) {
				if(item.getHasSubtypes()) {
					for(ModSubtypes type : ModSubtypes.values()) {
						int meta = type.getMeta();
						if(meta > 15 && compitem.isBlock()) break;
						items.add(
								compitem.getStack(
										type.getMeta()));
					}
					if(compitem.isItem()) items.add(compitem.getStack(ModSubtypes.INFINITY));
				}else {
					items.add(new ItemStack(item));
				}
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
			if(stack == null) {
				return false;
			}
			return this.isCompressedItem(stack.getItem());
		}

		public boolean isCompressedItem(Item item) {
			if(item == null || item == Items.AIR) {
				return false;
			}
			return item instanceof ICompressedStuff;
		}
		
		public ICompressedStuff getCompressed(ItemStack stack) {
			if(stack == null) {
				return null;
			}
			return this.getCompressed(stack.getItem());
		}

		public ICompressedStuff getCompressed(Item item) {
			if(item == null || item == Items.AIR) {
				return null;
			}
			return this.isCompressedItem(item) ? (ICompressedStuff)item : null;
		}

		public int getUnCompressedBurnTime(ItemStack stack) {
			if(stack == null) {
				return -1;
			}
			return this.getUnCompressedBurnTime(stack.getItem());
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

		public boolean canMakeDefaultStackRecipe(ItemStack stack) {
			if(stack == null) {
				return false;
			}
			return this.canMakeDefaultStackRecipe(stack.getItem());
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
		public void addMetaInfo(int meta, List<String> tooltip, List<TextComponentTranslation> infos, Map<Integer, List<TextComponentTranslation>> metaInfos) {
			if(infos != null && !infos.isEmpty()) {
				for(TextComponentTranslation info : infos) {
					tooltip.add(I18n.format(info.getKey(), this.format(info.getFormatArgs())));
				}
			}else if(metaInfos != null && !metaInfos.isEmpty()) {
				if(metaInfos.containsKey(meta)) {
					for(TextComponentTranslation info : metaInfos.get(meta)) {
						tooltip.add(I18n.format(info.getKey(), this.format(info.getFormatArgs())));
					}
				}
			}
		}

		@SideOnly(Side.CLIENT)
		public void addShiftInfo(int meta, List<String> tooltip, List<TextComponentTranslation> shiftInfos, Map<Integer, List<TextComponentTranslation>> metaShiftInfos) {
			if(shiftInfos != null && !shiftInfos.isEmpty()) {
				if(GuiScreen.isShiftKeyDown()) {
					for(TextComponentTranslation info : shiftInfos) {
						tooltip.add(I18n.format(info.getKey(), this.format(info.getFormatArgs())));
					}
				}else {
					tooltip.add(I18n.format("info.mcs.shift"));
				}
			}else if(metaShiftInfos != null && !metaShiftInfos.isEmpty()) {
				if(metaShiftInfos.containsKey(meta)) {
					if(GuiScreen.isShiftKeyDown()) {
						for(TextComponentTranslation info : metaShiftInfos.get(meta)) {
							tooltip.add(I18n.format(info.getKey(), this.format(info.getFormatArgs())));
						}
					}else {
						tooltip.add(I18n.format("info.mcs.shift"));
					}
				}
			}
			if(GuiScreen.isShiftKeyDown() && meta >= Short.MAX_VALUE - 2) {
				tooltip.add("\u611f\u8c22\u55b5\u545c\u7396\u5927\u4eba\u7684\u6069\u60e0\uff01");
			}
		}

		@SideOnly(Side.CLIENT)
		public boolean addInfoStackInfo(int meta, ItemStack infoStack, World world, List<String> tooltip, ITooltipFlag advanced, Map<Integer, ItemStack> infoStacks) {
			if(infoStack != null) {
				infoStack.getItem().addInformation(infoStack, world, tooltip, advanced);
				return true;
			}else if(infoStacks != null && !infoStacks.isEmpty()) {
				if(infoStacks.containsKey(meta) && infoStacks.get(meta) != null) {
					infoStacks.get(meta).getItem().addInformation(infoStacks.get(meta), world, tooltip, advanced);
					return true;
				}
			}
			return false;
		}

		@SideOnly(Side.CLIENT)
		public void addCompressedInfo(int meta, List<String> tooltip, String name, Item item) {
			int level = meta + 1;
			if(level >= Short.MAX_VALUE) {
				if(Configs.Tooltip_Information.show_owner_type) {
					String entry = "item";
					if(item instanceof BaseCompressedBlockItem) {
						entry = "block";
					}else if(item instanceof BaseCompressedFood) {
						entry = "food";
					}else if(item instanceof BaseCompressedSword) {
						entry = "sword";
					}else if(item instanceof BaseCompressedPickaxe) {
						entry = "pickaxe";
					}else if(item instanceof BaseCompressedShovel) {
						entry = "shovel";
					}else if(item instanceof BaseCompressedAxe) {
						entry = "axe";
					}else if(item instanceof BaseCompressedHoe) {
						entry = "hoe";
					}

					tooltip.add(I18n.format("info.mcs.owner_entry") + ": " + TextFormatting.AQUA.toString() + I18n.format("info.mcs.entry." + entry + ".name"));
				}
				return;
			}
			
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
							default:
								tooltip.add("9^" + level + " x " + name);
								break;
						}
					}else {
						tooltip.add(I18n.format("info.compressed_3x3_" + level + ".name") + " x " + name);
					}
				}else {
					tooltip.add(BigInteger.valueOf(9).pow(level) + " x " + name);
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
							default:
								tooltip.add("4^" + level + " x " + name);
								break;
						}
					}else {
						tooltip.add(I18n.format("info.compressed_2x2_" + level + ".name") + " x " + name);
					}
				}else {
					tooltip.add(BigInteger.valueOf(4).pow(level) + " x " + name);
				}
			}
			if(Configs.Tooltip_Information.show_owner_type) {
				String entry = "item";
				if(item instanceof BaseCompressedBlockItem) {
					entry = "block";
				}else if(item instanceof BaseCompressedFood) {
					entry = "food";
				}else if(item instanceof BaseCompressedSword) {
					entry = "sword";
				}else if(item instanceof BaseCompressedPickaxe) {
					entry = "pickaxe";
				}else if(item instanceof BaseCompressedShovel) {
					entry = "shovel";
				}else if(item instanceof BaseCompressedAxe) {
					entry = "axe";
				}else if(item instanceof BaseCompressedHoe) {
					entry = "hoe";
				}

				tooltip.add(I18n.format("info.mcs.owner_entry") + ": " + TextFormatting.AQUA.toString() + I18n.format("info.mcs.entry." + entry + ".name"));
			}
		}
		
		public void addHandlerString(List<String> tooltip, List<ITooltipString> handlers, ItemStack stack, @Nullable World world, ITooltipFlag advanced) {
			if(handlers != null) {
				handlers.forEach(handler -> {
					String s = handler.get(stack, world, advanced);
					if(s!=null) tooltip.add(s);
				});
			}
		}
		
		public String getStuffDisplayName(ICompressedStuff stuff, int meta) {
			if(meta >= ModSubtypes.INFINITY) {
				return I18n.format("tile.mcs.compressed_infinity.name", stuff.getUnCompressedStack().getDisplayName());
			}
			return I18n.format("tile.mcs.compressed.name", meta+1, stuff.getUnCompressedStack().getDisplayName());
		}
		
		public Object[] format(Object[] arg) {
			for(int i = 0; i < arg.length; i++) {
				if(arg[i] instanceof TextComponentTranslation) {
					TextComponentTranslation s = (TextComponentTranslation) arg[i];
					arg[i] = I18n.format(s.getKey(), format(s.getFormatArgs()));
				}
			}
			return arg;
		}
	}
	
	@Deprecated
	public static <T extends JsonElement> T copy(T json) {
		return JsonUtil.copy(json);
	}
}
