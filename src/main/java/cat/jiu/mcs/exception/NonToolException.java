package cat.jiu.mcs.exception;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NonToolException extends RuntimeException {
	private static final long serialVersionUID = -8178472781176288334L;
	public NonToolException(Item item, String toolName) {
		super(item.getRegistryName().toString() + " is NOT " + toolName);
	}
	public NonToolException(ItemStack stack, String toolName) {
		this(stack.getItem(), toolName);
	}
}
