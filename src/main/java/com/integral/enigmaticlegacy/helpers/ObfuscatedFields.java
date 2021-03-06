package com.integral.enigmaticlegacy.helpers;

import java.lang.reflect.Field;

import cpw.mods.modlauncher.api.INameMappingService;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.WorldOptionsScreen;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.FoodStats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

 /**
  * List of obfuscated private/protected fields from various classes,
  * that are required to be alterable by the mod.
  * @author Integral
  */

public class ObfuscatedFields {
	
	/**
	 * Class: CreateWorldScreen.class,
	 * Deobfuscated Name: worldName
	 */
	public static Field worldNameField;
	
	/**
	 * Class: CreateWorldScreen.class,
	 * Deobfuscated Name: worldSeed
	 */
	public static Field worldSeedField;
	
	/**
	 * Class: FoodStats.class,
	 * Deobfuscated Name: foodSaturationLevel
	 */
	public static Field foodSaturationField;
	
	/**
	 * Class: BeaconTileEntity.class,
	 * Deobfuscated Name: beamSegments
	 */
	public static Field beamSegmentsField;
	
	public static void extractCommonFields() {		
		foodSaturationField = ObfuscationReflectionHelper.findField(FoodStats.class, "field_75125_b");
		beamSegmentsField = ObfuscationReflectionHelper.findField(BeaconTileEntity.class, "field_174909_f");
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void extractClientFields() {
		
		worldNameField = ObfuscationReflectionHelper.findField(CreateWorldScreen.class, "field_146330_J");
		worldSeedField = ObfuscationReflectionHelper.findField(WorldOptionsScreen.class, "field_239041_o_");
	}

}
