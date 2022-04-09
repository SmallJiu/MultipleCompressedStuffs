package cat.jiu.mcs.recipes.mod;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import appeng.api.features.IInscriberRecipe;
import appeng.api.features.InscriberProcessType;
import net.minecraft.item.ItemStack;

public class AEInscriberRecipe implements IInscriberRecipe {
	protected final ItemStack input;
	protected final ItemStack output;
	protected final ItemStack top;
	protected final ItemStack down;
	protected final InscriberProcessType type;

	public AEInscriberRecipe(ItemStack input, ItemStack output, ItemStack top, ItemStack down, InscriberProcessType type) {
		this.input = input;
		this.output = output;
		this.top = top;
		this.down = down;
		this.type = type;
	}

	@Override
	public List<ItemStack> getInputs() {
		return Lists.newArrayList(this.input);
	}

	@Override
	public ItemStack getOutput() {
		return this.output;
	}

	@Override
	public Optional<ItemStack> getTopOptional() {
		return Optional.ofNullable(this.top);
	}

	@Override
	public Optional<ItemStack> getBottomOptional() {
		return Optional.ofNullable(this.down);
	}

	@Override
	public InscriberProcessType getProcessType() {
		return this.type;
	}
}
