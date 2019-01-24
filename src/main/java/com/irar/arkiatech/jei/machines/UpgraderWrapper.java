package com.irar.arkiatech.jei.machines;

import java.util.List;

import com.irar.arkiatech.recipe.GrinderRecipe;
import com.irar.arkiatech.recipe.UpgraderRecipe;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public class UpgraderWrapper<T extends UpgraderRecipe> implements IRecipeWrapper{

	private IJeiHelpers jeiHelpers;
	private UpgraderRecipe recipe;

	public UpgraderWrapper(IJeiHelpers jeiHelpers, UpgraderRecipe recipe) {
		this.jeiHelpers = jeiHelpers;
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ItemStack recipeOutput = recipe.getResult();
		IStackHelper stackHelper = jeiHelpers.getStackHelper();

		try {
			List<List<ItemStack>> inputLists = stackHelper.expandRecipeItemStackInputs(recipe.getIngredients());
			ingredients.setInputLists(ItemStack.class, inputLists);
			ingredients.setOutput(ItemStack.class, recipeOutput);
		} catch (RuntimeException e) {
		}
	}


}
