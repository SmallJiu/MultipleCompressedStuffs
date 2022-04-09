package cat.jiu.mcs.util.base;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class AdvancementBase {
	public static abstract class TriggerBase<I extends TriggerBase<I>> extends AbstractCriterionInstance implements ICriterionTrigger<I> {
		protected final ResourceLocation ID;
		private final Map<PlayerAdvancements, TriggerBase.Listeners<I>> listeners = Maps.newHashMap();

		protected TriggerBase(ResourceLocation id) {
			super(id);
			this.ID = id;
		}

		@Override
		public ResourceLocation getId() {
			return this.ID;
		}

		@Override
		public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<I> listener) {
			TriggerBase.Listeners<I> listeners = this.listeners.get(playerAdvancementsIn);
			if(listeners == null) {
				listeners = new TriggerBase.Listeners<I>(playerAdvancementsIn);
				this.listeners.put(playerAdvancementsIn, listeners);
			}
			listeners.add(listener);
		}

		@Override
		public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<I> listener) {
			TriggerBase.Listeners<I> listeners = this.listeners.get(playerAdvancementsIn);
			if(listeners != null) {
				listeners.remove(listener);
				if(listeners.isEmpty()) {
					this.listeners.remove(playerAdvancementsIn);
				}
			}
		}

		@Override
		public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
			this.listeners.remove(playerAdvancementsIn);
		}

		public void trigger(EntityPlayerMP player, Object... args) {
			TriggerBase.Listeners<I> listeners = this.listeners.get(player.getAdvancements());
			if(listeners != null) {
				listeners.trigger(args);
			}
		}

		public abstract boolean check(Object... args);
		public abstract I deserializeInstance(JsonObject json, JsonDeserializationContext context);

		protected static class Listeners<I extends TriggerBase<I>> {
			private final PlayerAdvancements playerAdvancements;
			private final Set<ICriterionTrigger.Listener<I>> listeners = Sets.newHashSet();

			public Listeners(PlayerAdvancements playerAdvancementsIn) {
				this.playerAdvancements = playerAdvancementsIn;
			}

			public boolean isEmpty() {
				return this.listeners.isEmpty();
			}

			public void add(ICriterionTrigger.Listener<I> listener) {
				this.listeners.add(listener);
			}

			public void remove(ICriterionTrigger.Listener<I> listener) {
				this.listeners.remove(listener);
			}

			public void trigger(Object... args) {
				List<ICriterionTrigger.Listener<I>> list = null;
				for(ICriterionTrigger.Listener<I> listener : this.listeners) {
					if(((TriggerBase<I>) listener.getCriterionInstance()).check(args)) {
						if(list == null) {
							list = Lists.newArrayList();
						}
						list.add(listener);
					}
				}
				if(list != null) {
					for(ICriterionTrigger.Listener<I> listener1 : list) {
						listener1.grantCriterion(this.playerAdvancements);
					}
				}
			}
		}
	}
}
