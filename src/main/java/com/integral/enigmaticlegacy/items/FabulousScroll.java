package com.integral.enigmaticlegacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.enigmaticlegacy.EnigmaticLegacy;
import com.integral.enigmaticlegacy.config.ConfigHandler;
import com.integral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.integral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.integral.enigmaticlegacy.items.generic.ItemBaseCurio;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.Item.Properties;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FabulousScroll extends HeavenScroll {

	public FabulousScroll() {
		super(ItemBaseCurio.getDefaultProperties().rarity(Rarity.EPIC));
		this.setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "fabulous_scroll"));
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
		ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");

		if (Screen.func_231173_s_()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll4");
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll5");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
		}
	}
	
	@Override
	public void curioTick(String identifier, int index, LivingEntity living) {
		if (living.world.isRemote)
			return;

		if (living instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) living;
			
			boolean inRange = SuperpositionHandler.isInBeaconRange(player);
			
			if (!SuperpositionHandler.isInBeaconRange(player))
				if (Math.random() <= (baseXpConsumptionProbability*8) * ConfigHandler.HEAVEN_SCROLL_XP_COST_MODIFIER.getValue() && player.abilities.isFlying)
					player.giveExperiencePoints(-1);

			this.handleFabulousFlight(player, inRange);
		}

	}
	
	protected void handleFabulousFlight(PlayerEntity player, boolean inRange) {
		try {
			if (player.experienceTotal > 0 || inRange) {

				if (!player.abilities.allowFlying)
					player.abilities.allowFlying = true;
				
				player.sendPlayerAbilities();
				this.flyMap.put(player, 100);
			
			} else if (this.flyMap.get(player) > 1) {
				this.flyMap.put(player, this.flyMap.get(player)-1);
			} else if (this.flyMap.get(player) == 1) {
				if (!player.isCreative()) {
					player.abilities.allowFlying = false;
					player.abilities.isFlying = false;
					player.sendPlayerAbilities();
					player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 200, 0, true, false));
				}

				this.flyMap.put(player, 0);
			}

		} catch (NullPointerException ex) {
			this.flyMap.put(player, 0);
		}
	}
	
	@Override
	public void onUnequip(String identifier, int index, LivingEntity entityLivingBase) {

		if (entityLivingBase instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLivingBase;

			if (!player.isCreative()) {
				player.abilities.allowFlying = false;
				player.abilities.isFlying = false;
				player.sendPlayerAbilities();
			}

			this.flyMap.put(player, 0);

		}
	}
	
}
