package cat.jiu.mcs.util.event;

import java.util.List;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseItemTool;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.client.gui.GUICompressor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class CountTooltip {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void addCountTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		List<String> tooltip = event.getToolTip();
		if(GuiScreen.isShiftKeyDown() && stack.getCount() >= 1000 && Minecraft.getMinecraft().currentScreen instanceof GUICompressor) {
			tooltip.add(I18n.format("info.mcs.count") + ": " + stack.getCount());
		}
		if(stack.getItem() instanceof BaseItemTool.MetaTool && stack.getMetadata() >= 32766) {
			tooltip.add(tooltip.size()-1, TextFormatting.BLUE + I18n.format("item.unbreakable"));
		}
		if(MCS.dev() && !stack.isEmpty()) {
//			tooltip.add(JiuUtils.nbt.getItemNBT(stack).toString());
			List<String> ores = JiuUtils.item.getOreDict(stack);
			if(!ores.isEmpty()) {
				tooltip.add("OreDictionary:");
				for(String ore : ores) {
					tooltip.add(" > " + ore);
				}
			}
		}
	}
}
