package cat.jiu.mcs.util.type;

import java.util.List;

import com.google.common.collect.Lists;

import cat.jiu.core.api.ITimer;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class CustomStuffType {
	public static class CustomItemType {
		public final String name;
		public final String unItem;
		public final int unMeta;
		public final boolean isFood;
		public final float foodAmount;
		public final float foodSaturation;
		public final boolean isWolfFood;

		public CustomItemType(String name, String unItem, int unMeta, boolean isFood, float foodAmount, float foodSaturation, boolean isWolfFood) {
			this.name = name;
			this.unItem = unItem;
			this.unMeta = unMeta;
			this.isFood = isFood;
			this.foodAmount = foodAmount;
			this.foodSaturation = foodSaturation;
			this.isWolfFood = isWolfFood;
		}
	}
	
	public static class ChangeBlockType {
		public final List<ItemStack> drops;
		public final ITimer times;
		public final boolean dropBlock;

		public ChangeBlockType(ItemStack[] drops, ITimer time, boolean dropBlock) {
			this(Lists.newArrayList(drops), time, dropBlock);
		}
		public ChangeBlockType(List<ItemStack> drops, ITimer time, boolean dropBlock) {
			this.drops = drops;
			this.times = time;
			this.dropBlock = dropBlock;
		}
	}

	public static class PotionEffectType {
		private final PotionEffect effect;

		public PotionEffectType(PotionEffectType effect) {
			this.effect = new PotionEffect(effect.effect);
		}
		
		public PotionEffectType(PotionEffect effect) {
			this.effect = new PotionEffect(effect);
		}

		public PotionEffectType(Potion potion, int tick, int level) {
			this.effect = new PotionEffect(potion, tick, level);
		}
		
		public PotionEffectType(Potion potion, ITimer time, int level) {
			this.effect = new PotionEffect(potion, (int) time.getTicks(), level);
		}

		public PotionEffect getEffect() {
			return new PotionEffect(this.effect);
		}
	}

	public static class ToolModifiersType {
		public final double speed;
		public final double damage;

		public ToolModifiersType(double speed, double damage) {
			this.speed = speed;
			this.damage = damage;
		}
	}
	
	public static class HarvestType {
		public final int level;
		public final String tool;

		public HarvestType(String tool, int level) {
			this.level = level;
			this.tool = tool;
		}
	}
}
