package com.integral.enigmaticlegacy.triggers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.integral.enigmaticlegacy.EnigmaticLegacy;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

/**
 * Special trigger that activates when player drinks from Unholy Grail.
 * @author Integral
 */

public class UseUnholyGrailTrigger implements ICriterionTrigger<UseUnholyGrailTrigger.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(EnigmaticLegacy.MODID, "unholy_grail_drink");
    public static final UseUnholyGrailTrigger INSTANCE = new UseUnholyGrailTrigger();
    private final Map<PlayerAdvancements, PlayerTracker> playerTrackers = new HashMap<>();

    private UseUnholyGrailTrigger() {
    	// Insert existential void here
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(@Nonnull PlayerAdvancements player, @Nonnull ICriterionTrigger.Listener<UseUnholyGrailTrigger.Instance> listener) {
        this.playerTrackers.computeIfAbsent(player, PlayerTracker::new).listeners.add(listener);
    }

    @Override
    public void removeListener(@Nonnull PlayerAdvancements player, @Nonnull ICriterionTrigger.Listener<UseUnholyGrailTrigger.Instance> listener) {
        PlayerTracker tracker = this.playerTrackers.get(player);

        if(tracker != null) {
            tracker.listeners.remove(listener);

            if(tracker.listeners.isEmpty()) {
                this.playerTrackers.remove(player);
            }
        }
    }

    @Override
    public void removeAllListeners(@Nonnull PlayerAdvancements player) {
        playerTrackers.remove(player);
    }
    
    @Override
	public Instance func_230307_a_(JsonObject p_230307_1_, ConditionArrayParser p_230307_2_) {
		return new Instance();
	}

    static class PlayerTracker {
        private final PlayerAdvancements playerAdvancements;
        final Set<ICriterionTrigger.Listener<Instance>> listeners = new HashSet<>();

        PlayerTracker(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public void trigger() {
            List<ICriterionTrigger.Listener<Instance>> list = new ArrayList<>();

            for(ICriterionTrigger.Listener<UseUnholyGrailTrigger.Instance> listener : this.listeners) {
                if(listener.getCriterionInstance().test()) {
                    list.add(listener);
                }
            }

            for(ICriterionTrigger.Listener<UseUnholyGrailTrigger.Instance> listener : list) {
                listener.grantCriterion(this.playerAdvancements);
            }
        }
    }

    public void trigger(ServerPlayerEntity player) {
        PlayerTracker tracker = playerTrackers.get(player.getAdvancements());
        if(tracker != null) {
            tracker.trigger();
        }
    }

    static class Instance implements ICriterionInstance {

        Instance() {
        }

        @Nonnull
        @Override
        public ResourceLocation getId() {
            return ID;
        }

        boolean test() {
            return true;
        }

		@Override
		public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_) {
			return new JsonObject();
		}
    }

}
