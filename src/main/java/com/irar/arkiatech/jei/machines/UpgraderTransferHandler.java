package com.irar.arkiatech.jei.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.irar.arkiatech.gui.container.ContainerUpgrader;
import com.irar.arkiatech.network.ATPacketHandler;
import com.irar.arkiatech.network.TransferMessage;

import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class UpgraderTransferHandler implements IRecipeTransferHandler<ContainerUpgrader> {

	private IRecipeTransferHandlerHelper transferHelper;

	public UpgraderTransferHandler(IRecipeTransferHandlerHelper transferHelper) {
		this.transferHelper = transferHelper;
	}

	@Override
	public Class<ContainerUpgrader> getContainerClass() {
		return ContainerUpgrader.class;
	}

	@Override
	public IRecipeTransferError transferRecipe(ContainerUpgrader container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
		Map<Integer, ? extends IGuiIngredient<ItemStack>> ingredients_ = recipeLayout.getItemStacks().getGuiIngredients();
		if(doTransfer) {
			List<ItemStack> stacks = new ArrayList<>();
			for(int i = 0; i < 5; i++) {
				ItemStack stack = ingredients_.get(i).getDisplayedIngredient();
				container.setCraftingStack(i, stack);
				stacks.add(stack);
			}
			ATPacketHandler.INSTANCE.sendToServer(new TransferMessage(container.getTile(), stacks));
		}
		return null;
	}

}
