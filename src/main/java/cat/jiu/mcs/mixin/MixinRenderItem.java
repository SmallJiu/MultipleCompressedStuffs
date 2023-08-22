package cat.jiu.mcs.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cat.jiu.mcs.api.ICompressedStuff;

import morph.avaritia.util.TextUtils;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

@Pseudo
@Mixin(value = RenderItem.class)
public class MixinRenderItem {
	private MixinRenderItem() {
		throw new RuntimeException();
	}
	
	@Inject(
		at = {@At(
			value = "HEAD")},
		method = {"renderItemOverlayIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"})
	private void mixin_renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int x, int y, @Nullable String text, CallbackInfo ci) {
		if(!stack.isEmpty() && stack.getItem() instanceof ICompressedStuff) {
			fr.drawString(TextUtils.makeFabulous(String.valueOf(stack.getMetadata()+1)), x+16, y, 0);
		}
	}
}
