package cat.jiu.mcs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.init.MCSItems;

import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;

@Pseudo
@Mixin(value = TileEntityLiquidHeatExchanger.class, remap = false)
public class MixinTileEntityLiquidHeatExchanger {
	private MixinTileEntityLiquidHeatExchanger() {
		throw new RuntimeException();
	}

	private InvSlotConsumable heatexchangerslots;

	@Inject(
		at = {@At("HEAD")},
		cancellable = true,
		method = "getMaxHeatEmittedPerTick()I",
		remap = false
	)
	private void mixin_getMaxHeatEmittedPerTick(CallbackInfoReturnable<Integer> cir) {
		int count = 0;
		for(int i = 0; i < this.heatexchangerslots.size(); ++i) {
			if(!this.heatexchangerslots.isEmpty(i)) {
				if(this.heatexchangerslots.get(i).getItem() == MCSItems.ic2.normal.C_heat_conductor_I) {
					count += MCSUtil.item.getMetaValue(10, this.heatexchangerslots.get(i));
				}else {
					count += 10;
				}
			}
		}
		cir.setReturnValue(count);
	}
}
