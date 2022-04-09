package cat.jiu.mcs.util.init;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import cat.jiu.core.util.base.BaseAdvancement;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CraftCompressedStuffTrigger extends BaseAdvancement.BaseCriterionTrigger<CraftCompressedStuffTrigger> {
	public static final CraftCompressedStuffTrigger instance = new CraftCompressedStuffTrigger(null,0);
	private CraftCompressedStuffTrigger(Item item, int meta) {
		super(new ResourceLocation(MCS.MODID, "craft_compressed"), new CompressedFactory<CraftCompressedStuffTrigger>(item, meta) {
			@Override
			public CraftCompressedStuffTrigger deserializeInstance(JsonObject json, JsonDeserializationContext context) {
				int meta = json.has("meta") ? json.get("meta").getAsInt() : -1;
				Item item = json.has("item") ? Item.getByNameOrId(json.get("item").getAsString()) : null;
				
				return new CraftCompressedStuffTrigger(item, meta);
			}
		});
	}

	public static abstract class CompressedFactory<I extends BaseAdvancement.BaseCriterionTrigger<I>> implements ICriterionTriggerFactory<I> {
		protected final Item item;
		protected final int meta;
		public CompressedFactory() {
			this(null,-1);
		}
		public CompressedFactory(ItemStack stack) {
			this(stack.getItem(), stack.getMetadata());
		}
		public CompressedFactory(Item item, int meta) {
			this.meta = meta > -1 ? meta : -1;
			this.item = item != null || item != Items.AIR ? item : null;
		}
		@Override
		public boolean check(Object... args) {
			if(args.length >= 1) {
				if(args[0] instanceof ItemStack) {
					ItemStack stack = (ItemStack) args[0];
					if(!stack.isEmpty()) {
						if(stack.getItem() instanceof ICompressedStuff) {
							if(this.item != null) {
								if(this.meta > -1) {
									if(stack.getMetadata() == this.meta) {
										return true;
									}
								}else if(stack.getItem() == this.item) {
									return true;
								}
							}else if(this.meta > -1) {
								return this.meta == stack.getMetadata();
							}else {
								return true;
							}
						}
					}
				}else if(args[0] instanceof InventoryPlayer) {
					InventoryPlayer inv = (InventoryPlayer) args[0];
					for(int slot = 0; slot < inv.getSizeInventory(); slot++) {
						ItemStack stack = inv.getStackInSlot(slot);
						if(!stack.isEmpty()) {
							if(stack.getItem() instanceof ICompressedStuff) {
								if(this.item != null) {
									if(this.meta > -1) {
										if(stack.getMetadata() == this.meta) {
											return true;
										}
									}else if(stack.getItem() == this.item) {
										return true;
									}
								}else if(this.meta > -1) {
									return this.meta == stack.getMetadata();
								}else {
									return true;
								}
							}
						}
					}
				}else if(args[0] instanceof Integer) {
					return (int)args[0] == this.meta;
				}
			}
			return false;
		}
		@Override
		public abstract I deserializeInstance(JsonObject json, JsonDeserializationContext context);
	}
}
