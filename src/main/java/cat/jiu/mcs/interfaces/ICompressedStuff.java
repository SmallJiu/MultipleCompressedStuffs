package cat.jiu.mcs.interfaces;

import net.minecraft.item.ItemStack;

public interface ICompressedStuff {
	ItemStack getUnCompressedStack();
	int getUnCompressedBurnTime();
	boolean canMakeDefaultStackRecipe();
	String getOwnerMod();
}
