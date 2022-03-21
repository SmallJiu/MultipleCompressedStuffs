package cat.jiu.mcs.items.compressed.ic;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ICReflector extends ICReactorAssembly {
	public ICReflector(String name, String baseItem) {
		this(name, baseItem, 0);
	}
	
	public ICReflector(String name, String baseItem, int meta) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta));
	}

	public ICReflector(String name, ItemStack baseItem) {
		super(name, baseItem);
	}
	
	@Override
	public boolean canBePlacedIn(ItemStack p0, IReactor p1) {
		return true;
	}
	
	@Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        if (!heatrun) {
            IReactorComponent source = (IReactorComponent)pulsingStack.getItem();
            source.acceptUraniumPulse(pulsingStack, reactor, stack, pulseX, pulseY, youX, youY, heatrun);
        }
        else if (this.getCustomDamage(stack) + 1 >= this.getMaxCustomDamage(stack)) {
            reactor.setItemAt(youX, youY, null);
        }
        else {
            this.setCustomDamage(stack, this.getCustomDamage(stack) + 1);
        }
        return true;
    }
	
	@Override
    public float influenceExplosion(ItemStack stack, IReactor reactor) {
        return -1.0f;
    }
}
