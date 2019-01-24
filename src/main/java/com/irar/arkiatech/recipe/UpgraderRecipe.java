package com.irar.arkiatech.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class UpgraderRecipe {

	private ItemStack result;
	private List<Ingredient> ingredients;

	public UpgraderRecipe(ItemStack result, Object... ingredients) {
		this.ingredients = parseIngredients(ingredients);
		this.result = result;
	}

	private List<Ingredient> parseIngredients(Object[] ingredients) {
		List<Ingredient> ingredientsParsed = new ArrayList<>();
		for(Object ingredient : ingredients) {
			Ingredient parsed;
			if(ingredient instanceof String) {
				parsed = parseIngredient((String) ingredient);
			}else if(ingredient instanceof ItemStack) {
				parsed = Ingredient.fromStacks((ItemStack) ingredient);
			}else if(ingredient instanceof Item) {
				parsed = Ingredient.fromItem((Item) ingredient);
			}else if(ingredient instanceof Block) {
				parsed = Ingredient.fromItem(Item.getItemFromBlock((Block) ingredient));
			}else {
				parsed = Ingredient.EMPTY;
			}
			ingredientsParsed.add(parsed);
		}
		return ingredientsParsed;
	}

	private Ingredient parseIngredient(String ingredient) {
		NonNullList<ItemStack> stacks = OreDictionary.getOres(ingredient);
		Ingredient parsed = Ingredient.fromStacks(stacks.toArray(new ItemStack[0]));
		return parsed;
	}
	
	public boolean hasIngredients(List<ItemStack> stacksRaw) {
		List<ItemStack> stacks = copyStacks(stacksRaw);
		for(Ingredient ingredient : ingredients) {
			boolean found = false;
			for(ItemStack stack : stacks) {
				if(ingredient.apply(stack)) {
					found = true;
					stack.shrink(1);
					break;
				}
			}
			if(!found) {
				return false;
			}
		}
		return true;
	}

	private List<Ingredient> getIngredientsClone() {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.addAll(this.ingredients);
		return ingredients;
	}

	private List<ItemStack> copyStacks(List<ItemStack> stacksRaw) {
		List<ItemStack> stacks = new ArrayList<>();
		for(ItemStack stack : stacksRaw) {
			stacks.add(stack.copy());
		}
		return stacks;
	}
	
	public boolean matches(List<ItemStack> stacks) {
		for(int i = 0; i < this.ingredients.size(); i++) {
			if(!this.ingredients.get(i).apply(stacks.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public ItemStack getResult() {
		return this.result.copy();
	}

	public void useIngredients(List<ItemStack> stacks) {
		for(Ingredient ingredient : ingredients) {
			for(ItemStack stack : stacks) {
				if(ingredient.apply(stack)) {
					stack.shrink(1);
					break;
				}
			}
		}
	}

	public List getIngredients() {
		return ingredients;
	}
	
}
