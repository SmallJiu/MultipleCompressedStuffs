package cat.jiu.mcs.util.type;

import cat.jiu.core.util.JiuUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEffectType {
	private final PotionEffect effect;
	
	public PotionEffectType(PotionEffect effect) {
		this.effect = effect;
	}
	
	public PotionEffectType(Potion potion, int tick, int level) {
		this.effect = new PotionEffect(potion, tick, level);
	}
	
	public PotionEffectType(Potion potion, int m, int s, int tick, int level) {
		this.effect = new PotionEffect(potion, (int)JiuUtils.other.parseTick(m, s, tick), level);
	}

	public PotionEffect getEffect() {
		return new PotionEffect(this.effect);
	}
}
