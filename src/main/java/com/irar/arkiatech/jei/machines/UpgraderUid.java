package com.irar.arkiatech.jei.machines;

import java.util.List;

import com.irar.arkiatech.Ref;
import com.irar.arkiatech.handlers.ATItems;
import com.irar.arkiatech.tileentity.TileGrinder;
import com.irar.arkiatech.tileentity.TileUpgrader;
import com.irar.arkiatech.tileentity.ATTEGui;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class UpgraderUid implements IRecipeCategory<IRecipeWrapper> {

	private IGuiHelper guiHelper;
	private ICraftingGridHelper craftingGridHelper;

	public UpgraderUid(IGuiHelper guiHelper) {
		this.guiHelper = guiHelper;
		craftingGridHelper = guiHelper.createCraftingGridHelper(0, 5);
	}

	@Override
	public String getUid() {
		return ATTEGui.UPGRADER.toString();
	}

	@Override
	public String getTitle() {
		return TileUpgrader.getDisplayName_();
	}

	@Override
	public String getModName() {
		return Ref.MODID;
	}

	@Override
	public IDrawable getBackground() {
		return guiHelper.createDrawable(new ResourceLocation("arkiatech:textures/gui/container/jei_upgrader.png"), 0, 0, 149, 104);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiIngredientGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(0, true, 43, 7);
		guiItemStacks.init(1, true, 7, 43);
		guiItemStacks.init(2, true, 43, 43);
		guiItemStacks.init(3, true, 79, 43);
		guiItemStacks.init(4, true, 43, 79);
		guiItemStacks.init(5, false, 124, 43);
		
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		craftingGridHelper.setInputs(guiItemStacks, inputs);
		guiItemStacks.set(5, outputs.get(0));
	}

}
