package cat.jiu.mcs.items.compressed.ae2;

import java.util.List;

import appeng.api.AEApi;
import appeng.api.config.FuzzyMode;
import appeng.api.definitions.IItemDefinition;
import appeng.api.implementations.items.IStorageCell;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.items.contents.CellConfig;
import appeng.items.contents.CellUpgrades;
import appeng.util.Platform;

import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

@SuppressWarnings({"all"})
public abstract class AEStorageCell<T extends IAEStack<T>> extends BaseCompressedItem implements IStorageCell<T> {
	protected final IStorageCell baseCell;

	public AEStorageCell(String name, ItemStack baseItem) {
		super(name, baseItem);
		super.setInfoStack(ItemStack.EMPTY);
		if(baseItem.getItem() instanceof IStorageCell) {
			this.baseCell = (IStorageCell) baseItem.getItem();
		}else {
			throw new RuntimeException(baseItem.toString() + " is NOT AEStorageCell");
		}
		super.setCanShowBaseStackInfo(false);
	}

	public AEStorageCell(String name, IItemDefinition item) {
		this(name, item.maybeStack(1).get());
	}

	@Override
	public boolean createOreDictionary() {
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		AEApi.instance().client().addCellInformation(AEApi.instance().registries().cell().getCellInventory(stack, null, this.getChannel()), tooltip);
		super.addInformation(stack, world, tooltip, advanced);
	}

	@Override
	public boolean isEditable(ItemStack p0) {
		return true;
	}

	@Override
	public IItemHandler getUpgradesInventory(ItemStack p0) {
		if(this.baseCell != null) {
			return this.baseCell.getUpgradesInventory(this.unCompressedItem);
		}
		return new CellUpgrades(p0, 2);
	}

	@Override
	public IItemHandler getConfigInventory(ItemStack p0) {
		if(this.baseCell != null) {
			return this.baseCell.getConfigInventory(this.unCompressedItem);
		}
		return new CellConfig(p0);
	}

	@Override
	public FuzzyMode getFuzzyMode(ItemStack p0) {
		if(this.baseCell != null) {
			return this.baseCell.getFuzzyMode(p0);
		}
		return FuzzyMode.IGNORE_ALL;
	}

	@Override
	public void setFuzzyMode(ItemStack is, FuzzyMode fzMode) {
		Platform.openNbtData(is).setString("FuzzyMode", fzMode.name());
	}

	@Override
	public int getBytes(ItemStack p0) {
		if(this.baseCell != null) {
			double bytes = MCSUtil.item.getMetaValue(this.baseCell.getBytes(this.unCompressedItem), p0);
			return (int) (bytes >= Integer.MAX_VALUE ? Integer.MAX_VALUE : bytes);
		}
		return 0;
	}

	@Override
	public int getBytesPerType(ItemStack p0) {
		if(this.baseCell != null) {
			return this.baseCell.getBytesPerType(this.unCompressedItem);
		}
		return Integer.MAX_VALUE;
	}

	@Override
	public final int getTotalTypes(ItemStack p0) {
		int baseValue = this.baseCell.getTotalTypes(this.unCompressedItem);
		return baseValue + (int) MCSUtil.item.getMetaValue(this.getBaseTypes(), p0);
	}
	
	protected abstract double getBaseTypes();

	public boolean isBlackListed(ItemStack p0, T p1) {return false;}
	public boolean storableInStorageCell() {return false;}
	public boolean isStorageCell(ItemStack p0) {return true;}

	@Override
	public double getIdleDrain() {
		if(this.baseCell != null) {
			return this.baseCell.getIdleDrain() * 2;
		}
		return 0;
	}

	@Override
	public IStorageChannel<T> getChannel(){
		if(this.baseCell != null) {
			return this.baseCell.getChannel();
		}
		return null;
	}
}
