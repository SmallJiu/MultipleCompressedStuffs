package cat.jiu.mcs.items.compressed.ic;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.ItemReactorIridiumReflector;
import ic2.core.item.reactor.ItemReactorReflector;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IC2Reflector extends IC2ReactorAssembly {
	public IC2Reflector(String name, String baseItem) {
		this(name, baseItem, 0);
	}

	public IC2Reflector(String name, String baseItem, int meta) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta));
	}

	public IC2Reflector(String name, ItemStack baseItem) {
		super(name, baseItem);
	}

	@Override
	public boolean canBePlacedIn(ItemStack p0, IReactor p1) {
		return true;
	}

	@Override
	public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
		if(this.baseComponent instanceof ItemReactorReflector) {
			if(!heatrun) {
				IReactorComponent source = (IReactorComponent) pulsingStack.getItem();
				source.acceptUraniumPulse(pulsingStack, reactor, stack, pulseX, pulseY, youX, youY, heatrun);
			}else if(this.getBigDamage(stack) + 1 >= this.getMaxBigDamage(stack)) {
				reactor.setItemAt(youX, youY, null);
			}else {
				this.setBigDamage(stack, this.getBigDamage(stack) + 1);
			}
		}else if(this.baseComponent instanceof ItemReactorIridiumReflector) {
			IReactorComponent source = (IReactorComponent) pulsingStack.getItem();
			source.acceptUraniumPulse(pulsingStack, reactor, stack, pulseX, pulseY, youX, youY, heatrun);
		}
		return true;
	}

	@Override
	public float influenceExplosion(ItemStack stack, IReactor reactor) {
		return -1.0f;
	}
}
