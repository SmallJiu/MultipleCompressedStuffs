package cat.jiu.mcs.blocks.net.client.gui;

import cat.jiu.core.util.JiuUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

public class CompressorRenderItem extends RenderItem {
	public CompressorRenderItem(TextureManager tm, ModelManager mm, ItemColors ic) {
		super(tm, mm, ic);
	}

	@Override
	public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text) {
		if(!stack.isEmpty()) {
			if(stack.getCount() >= 1000) {
				String s = JiuUtils.big_integer.format(JiuUtils.big_integer.create(String.valueOf(stack.getCount())), 1).toString();
				super.renderItemOverlayIntoGUI(fr, stack, xPosition - 1, yPosition - 1, s);
			}else {
				super.renderItemOverlayIntoGUI(fr, stack, xPosition, yPosition, text);
			}
		}
	}
}
