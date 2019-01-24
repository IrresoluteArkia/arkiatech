package com.irar.arkiatech.recipe;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class GrinderRecipe {

	private Ingredient in;
	private ItemStack result;

	public GrinderRecipe(ItemStack input, ItemStack output) {
		input = input.copy();
		input.setCount(1);
		in = Ingredient.fromStacks(input);
		result = output.copy();
	}
	
	public GrinderRecipe(String ore, ItemStack output) {
		NonNullList<ItemStack> ores = OreDictionary.getOres(ore);
		ItemStack[] stacks = ores.toArray(new ItemStack[0]);
		in = Ingredient.fromStacks(stacks);
		result = output.copy();
	}
	
	public boolean matches(ItemStack stack) {
		return in.apply(stack);
	}
	
	public ItemStack getResultItemStack() {
		return result.copy();
	}

	public List getIngredients() {
		return NonNullList.withSize(1, in);
	}
	
}
