package cat.jiu.mcs.items.compressed.ic;

import cat.jiu.mcs.util.MCSUtil;
import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.ItemReactorPlating;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IC2HeatPlating extends IC2ReactorAssembly {
	private final int maxHeatAdd;
	private final float effectModifier;
	protected final ItemReactorPlating baseExchanger;

	public IC2HeatPlating(String name, String baseItem) {
		this(name, baseItem, 0);
	}

	public IC2HeatPlating(String name, String baseItem, int meta) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta));
	}

	public IC2HeatPlating(String name, ItemStack baseItem) {
		super(name, baseItem);
		if(this.baseComponent instanceof ItemReactorPlating) {
			this.baseExchanger = (ItemReactorPlating) this.baseComponent;
		}else {
			throw new RuntimeException(baseItem.toString() + " is NOT ReactorPlating");
		}
		String baseName = baseItem.getItem().getRegistryName().toString().toLowerCase();
		if(baseName.equals("ic2:plating")) {
			this.maxHeatAdd = 1000;
			this.effectModifier = 0.95F;
		}else if(baseName.equals("ic2:heat_plating")) {
			this.maxHeatAdd = 2000;
			this.effectModifier = 0.99f;
		}else if(baseName.equals("ic2:containment_plating")) {
			this.maxHeatAdd = 500;
			this.effectModifier = 0.9F;
		}else {
			this.maxHeatAdd = 0;
			this.effectModifier = 0;
		}
	}

	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
		if(heatrun) {
			reactor.setMaxHeat((int) (reactor.getMaxHeat() + MCSUtil.item.getMetaValue(this.maxHeatAdd, stack)));
			reactor.setHeatEffectModifier((float) (reactor.getHeatEffectModifier() * MCSUtil.item.getMetaValue(this.effectModifier, stack)));
		}
	}

	@Override
	public float influenceExplosion(final ItemStack stack, final IReactor reactor) {
		if(MCSUtil.item.getMetaValue(this.effectModifier, stack) >= 1.0f) {
			return 0.0f;
		}
		return (float) MCSUtil.item.getMetaValue(this.effectModifier, stack);
	}
}
