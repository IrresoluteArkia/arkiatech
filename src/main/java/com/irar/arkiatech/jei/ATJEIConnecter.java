package com.irar.arkiatech.jei;

import com.irar.arkiatech.gui.client.GuiGrinder;
import com.irar.arkiatech.gui.client.GuiUpgrader;
import com.irar.arkiatech.gui.container.ContainerUpgrader;
import com.irar.arkiatech.handlers.ATBlocks;
import com.irar.arkiatech.jei.machines.GrinderUid;
import com.irar.arkiatech.jei.machines.GrinderWrapper;
import com.irar.arkiatech.jei.machines.UpgraderTransferHandler;
import com.irar.arkiatech.jei.machines.UpgraderUid;
import com.irar.arkiatech.jei.machines.UpgraderWrapper;
import com.irar.arkiatech.recipe.GrinderRecipe;
import com.irar.arkiatech.recipe.GrinderRecipes;
import com.irar.arkiatech.recipe.UpgraderRecipe;
import com.irar.arkiatech.recipe.UpgraderRecipes;
import com.irar.arkiatech.tileentity.ATTEGui;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class ATJEIConnecter implements IModPlugin{

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(
				new GrinderUid(guiHelper),
				new UpgraderUid(guiHelper)
		);
	}
	
	@Override
	public void register(IModRegistry registry) {
		final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		final IRecipeTransferHandlerHelper transferHelper = jeiHelpers.recipeTransferHandlerHelper();
		final IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();

		registry.addRecipes(GrinderRecipes.allRecipes, ATTEGui.GRINDER.toString());
		registry.addRecipes(UpgraderRecipes.allRecipes, ATTEGui.UPGRADER.toString());
		
		registry.handleRecipes(GrinderRecipe.class, recipe -> new GrinderWrapper(jeiHelpers, recipe), ATTEGui.GRINDER.toString());
		registry.handleRecipes(UpgraderRecipe.class, recipe -> new UpgraderWrapper(jeiHelpers, recipe), ATTEGui.UPGRADER.toString());
		
		registry.addRecipeClickArea(GuiGrinder.class, 77, 17, 24, 16, ATTEGui.GRINDER.toString());
		registry.addRecipeClickArea(GuiUpgrader.class, 117, 51, 24, 16, ATTEGui.UPGRADER.toString());
		
		registry.addRecipeCatalyst(new ItemStack(ATBlocks.grinder), ATTEGui.GRINDER.toString());
		registry.addRecipeCatalyst(new ItemStack(ATBlocks.upgrader), ATTEGui.UPGRADER.toString());
		registry.addRecipeCatalyst(new ItemStack(ATBlocks.pFurnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(ATBlocks.generator), VanillaRecipeCategoryUid.FUEL);
		
		
		transferRegistry.addRecipeTransferHandler(new UpgraderTransferHandler(transferHelper), ATTEGui.UPGRADER.toString());
	}
	
}
