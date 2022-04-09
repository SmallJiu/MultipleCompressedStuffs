package cat.jiu.mcs.util;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import cat.jiu.mcs.util.base.BaseBlock;
import cat.jiu.mcs.util.base.BaseBlockItem;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Test extends ItemOverrideList {

	private static final List<ItemOverride> overrides = Lists.<ItemOverride>newArrayList();

	public Test() {
		super(overrides);
	}

	@SuppressWarnings("unused")
	@Override
	public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
		IBakedModel subModel = originalModel;

		if(stack.getItem() instanceof BaseBlockItem) {
			BaseBlockItem item = (BaseBlockItem) stack.getItem();
			BaseBlock block = (BaseBlock) item.getBlock();

			if(block instanceof BaseBlockSub) {
				BaseBlockSub subblock = (BaseBlockSub) block;
			}
		}

		return subModel;
	}
}
