package cat.jiu.mcs.util;

import moze_intel.projecte.emc.EMCMapper;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.item.ItemStack;

public class EMCUtil{
	public static final EMCUtil instance = new EMCUtil();
	
	public long getRegEMC(ItemStack stack) {
		SimpleStack key = new SimpleStack(stack);
		
		if(EMCMapper.mapContains(key)) {
			return EMCMapper.getEmcValue(key);
		}else {
			return 0;
		}
	}
}
