package com.integral.enigmaticlegacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.enigmaticlegacy.EnigmaticLegacy;
import com.integral.enigmaticlegacy.config.ConfigHandler;
import com.integral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.integral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.integral.enigmaticlegacy.items.generic.ItemBase;
import com.integral.enigmaticlegacy.packets.clients.PacketPortalParticles;
import com.integral.enigmaticlegacy.packets.clients.PacketRecallParticles;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

public class RecallPotion extends ItemBase {

	public RecallPotion() {
		super(ItemBase.getDefaultProperties().maxStackSize(1).rarity(Rarity.RARE));
		this.setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "recall_potion"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
		if (Screen.func_231173_s_()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.recallPotion1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.recallPotion2");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
		}
	}

	@Override
	public boolean isForMortals() {
		return ConfigHandler.RECALL_POTION_ENABLED.getValue();
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		PlayerEntity player = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;

		if (player instanceof ServerPlayerEntity) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, stack);
		}

		if (!worldIn.isRemote && player instanceof ServerPlayerEntity) {
			Vector3d vec = SuperpositionHandler.getValidSpawn((ServerWorld)worldIn, (ServerPlayerEntity)player);

			worldIn.playSound(null, player.func_233580_cy_(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, (float) (0.8F + (Math.random() * 0.2)));

			EnigmaticLegacy.packetInstance.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player.getPosX(), player.getPosY(), player.getPosZ(), 128, player.world.func_234923_W_())), new PacketPortalParticles(player.getPosX(), player.getPosY() + (player.getHeight() / 2), player.getPosZ(), 100, 1.25F, false));

			player.setPositionAndUpdate(vec.x, vec.y, vec.z);
			worldIn.playSound(null, player.func_233580_cy_(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, (float) (0.8F + (Math.random() * 0.2)));

			EnigmaticLegacy.packetInstance.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player.getPosX(), player.getPosY(), player.getPosZ(), 128, player.world.func_234923_W_())), new PacketRecallParticles(player.getPosX(), player.getPosY() + (player.getHeight() / 2), player.getPosZ(), 48, false));
		}

		if (player == null || !player.abilities.isCreativeMode) {
			stack.shrink(1);

			if (stack.isEmpty()) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}

			if (player != null) {
				player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}

		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		playerIn.setActiveHand(handIn);
		return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

}
