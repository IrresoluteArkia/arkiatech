package com.irar.arkiatech.handlers;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ATCrafting {

	public static void registerCrafting() {
		
	}
	
	public static void registerSmelting() {
		GameRegistry.addSmelting(ATItems.zeDust, new ItemStack(ATItems.zeIngot), 1.0F);
		GameRegistry.addSmelting(ATItems.zeIron, new ItemStack(Items.IRON_INGOT), 1.0F);
		GameRegistry.addSmelting(ATItems.zeGold, new ItemStack(Items.GOLD_INGOT), 1.0F);
		GameRegistry.addSmelting(ATItems.zeQuartz, new ItemStack(Items.QUARTZ), 1.0F);
		GameRegistry.addSmelting(ATItems.zeDiamond, new ItemStack(Items.DIAMOND), 1.0F);
		GameRegistry.addSmelting(ATItems.zeEmerald, new ItemStack(Items.EMERALD), 1.0F);
		GameRegistry.addSmelting(ATItems.upIronZephyr, new ItemStack(ATItems.upIron), 1.0F);
		GameRegistry.addSmelting(ATItems.upGoldZephyr, new ItemStack(ATItems.upGold), 1.0F);
		GameRegistry.addSmelting(ATItems.upQuartzZephyr, new ItemStack(ATItems.upQuartz), 1.0F);
		GameRegistry.addSmelting(ATItems.upDiamondZephyr, new ItemStack(ATItems.upDiamond), 1.0F);
		GameRegistry.addSmelting(ATItems.upEmeraldZephyr, new ItemStack(ATItems.upEmerald), 1.0F);
		GameRegistry.addSmelting(ATItems.upZephyr, new ItemStack(ATItems.coZephyr), 1.0F);
	}

}
