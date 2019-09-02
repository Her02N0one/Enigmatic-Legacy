package com.integral.enigmaticlegacy.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.integral.enigmaticlegacy.EnigmaticLegacy;
import com.integral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.integral.enigmaticlegacy.helpers.IPerhaps;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HastePotion extends Item implements IPerhaps {
	
 public static Properties integratedProperties = new Item.Properties();
 public List<EffectInstance> effectList;

 public HastePotion(Properties properties, int duration, int amplifier) {
		super(properties);
		
		this.effectList = new ArrayList<EffectInstance>();
		this.effectList.add(new EffectInstance(Effects.HASTE, duration, amplifier, false, true));
 }
 
 public static Properties setupIntegratedProperties(Rarity rarity) {
	 integratedProperties.group(ItemGroup.BREWING);
	 integratedProperties.maxStackSize(1);
	 integratedProperties.rarity(rarity);
	 
	 return integratedProperties;
 
 }
 
 public static void initConfigValues() {}
 
 @Override
 public boolean isForMortals() {
 	return EnigmaticLegacy.configLoaded ? EnigmaticLegacy.configHandler.HASTE_POTION_ENABLED.get() : false;
 }
 
 @OnlyIn(Dist.CLIENT)
 public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
	 SuperpositionHandler.addPotionTooltip(this.effectList, stack, list, 1.0F);
 }

 @Override
 public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
     PlayerEntity player = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;
     if (player == null || !player.abilities.isCreativeMode) {
        stack.shrink(1);
     }

     if (player instanceof ServerPlayerEntity) {
        CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)player, stack);
     }

     if (!worldIn.isRemote)
    	 for (EffectInstance instance : effectList) {
    		 player.addPotionEffect(new EffectInstance(instance));
    	 }

     if (player == null || !player.abilities.isCreativeMode) {
        if (stack.isEmpty()) {
           return new ItemStack(Items.GLASS_BOTTLE);
        }

        if (player != null) {
           player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
        }
     }

     return stack;
  }

  /**
   * How long it takes to use or consume an item
   */
 @Override
  public int getUseDuration(ItemStack stack) {
     return 32;
  }

  /**
   * returns the action that specifies what animation to play when the items is being used
   */
 @Override
  public UseAction getUseAction(ItemStack stack) {
     return UseAction.DRINK;
  }

  /**
   * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
   * {@link #onItemUse}.
   */
 @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
     playerIn.setActiveHand(handIn);
     return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
  }

  @OnlyIn(Dist.CLIENT)
  public boolean hasEffect(ItemStack stack) {
     return true;
  }
  
}
