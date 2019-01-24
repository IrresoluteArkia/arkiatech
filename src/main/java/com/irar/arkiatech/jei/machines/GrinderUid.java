package com.irar.arkiatech.jei.machines;

import java.util.List;

import com.irar.arkiatech.Ref;
import com.irar.arkiatech.handlers.ATItems;
import com.irar.arkiatech.tileentity.TileGrinder;
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

public class GrinderUid implements IRecipeCategory<IRecipeWrapper> {

	private IGuiHelper guiHelper;
	private ICraftingGridHelper craftingGridHelper;

	public GrinderUid(IGuiHelper guiHelper) {
		this.guiHelper = guiHelper;
		craftingGridHelper = guiHelper.createCraftingGridHelper(0, 2);
	}

	@Override
	public String getUid() {
		return ATTEGui.GRINDER.toString();
	}

	@Override
	public String getTitle() {
		return TileGrinder.getDisplayName_();
	}

	@Override
	public String getModName() {
		return Ref.MODID;
	}

	@Override
	public IDrawable getBackground() {
		return guiHelper.createDrawable(new ResourceLocation("arkiatech:textures/gui/container/jei_grinder.png"), 0, 0, 176, 77);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiIngredientGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(0, true, 53, 16);
		guiItemStacks.init(1, true, 79, 52);
		guiItemStacks.init(2, false, 105, 16);
		
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		inputs.add(NonNullList.<ItemStack>withSize(1, new ItemStack(ATItems.zeDrift)));
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		craftingGridHelper.setInputs(guiItemStacks, inputs);
		guiItemStacks.set(2, outputs.get(0));
	}

}
