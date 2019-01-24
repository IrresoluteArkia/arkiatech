package com.irar.arkiatech.recipe;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.handlers.ATItems;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UpgraderRecipes {

public static List<UpgraderRecipe> allRecipes = new ArrayList<>();
	
	public static void init() {
		Item circuitCore = ATItems.getItemByName("basic_circuit_core");
		allRecipes.add(new UpgraderRecipe(new ItemStack(ATItems.upIronZephyr), new Object[] {ATItems.zeIron, ATItems.zeIron, ATItems.zeStable, ATItems.zeIron, ATItems.zeIron}));
		allRecipes.add(new UpgraderRecipe(new ItemStack(ATItems.upGoldZephyr), new Object[] {ATItems.zeGold, ATItems.zeGold, ATItems.zeStable, ATItems.zeGold, ATItems.zeGold}));
		allRecipes.add(new UpgraderRecipe(new ItemStack(ATItems.upQuartzZephyr), new Object[] {ATItems.zeQuartz, ATItems.zeQuartz, ATItems.zeStable, ATItems.zeQuartz, ATItems.zeQuartz}));
		allRecipes.add(new UpgraderRecipe(new ItemStack(ATItems.upDiamondZephyr), new Object[] {ATItems.zeDiamond, ATItems.zeDiamond, ATItems.zeStable, ATItems.zeDiamond, ATItems.zeDiamond}));
		allRecipes.add(new UpgraderRecipe(new ItemStack(ATItems.upEmeraldZephyr), new Object[] {ATItems.zeEmerald, ATItems.zeEmerald, ATItems.zeStable, ATItems.zeEmerald, ATItems.zeEmerald}));
		allRecipes.add(new UpgraderRecipe(new ItemStack(ATItems.upZephyr), new Object[] {ATItems.zeDust, ATItems.zeDust, ATItems.zeStable, ATItems.zeDust, ATItems.zeDust}));
		allRecipes.add(new UpgraderRecipe(new ItemStack(circuitCore, 7), new Object[] {ATItems.upQuartz, ATItems.upEmerald, ATItems.upDiamond, ATItems.upGold, ATItems.upIron}));
	}
	
}
