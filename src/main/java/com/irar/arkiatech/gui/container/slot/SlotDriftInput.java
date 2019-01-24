package com.irar.arkiatech.gui.container.slot;

import com.irar.arkiatech.handlers.ATItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDriftInput extends Slot {

	public SlotDriftInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem().equals(ATItems.zeDrift);
	}

}
