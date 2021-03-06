package com.integral.enigmaticlegacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.enigmaticlegacy.EnigmaticLegacy;
import com.integral.enigmaticlegacy.config.ConfigHandler;
import com.integral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.integral.enigmaticlegacy.items.generic.ItemBaseCurio;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EscapeScroll extends ItemBaseCurio {

	public EscapeScroll() {
		super(ItemBaseCurio.getDefaultProperties().rarity(Rarity.RARE).maxStackSize(1));
		this.setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "escape_scroll"));
	}

	@Override
	public boolean isForMortals() {
		return ConfigHandler.ESCAPE_SCROLL_ENABLED.getValue();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {

		ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");

		if (Screen.func_231173_s_()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.escapeTome1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.escapeTome2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.escapeTome3");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
		}
	}

}
