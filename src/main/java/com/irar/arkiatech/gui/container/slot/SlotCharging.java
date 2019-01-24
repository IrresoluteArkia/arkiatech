package com.irar.arkiatech.gui.container.slot;

import com.irar.arkiatech.item.ATMachineUpgrade;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;

public class SlotCharging extends Slot {

	public SlotCharging(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.hasCapability(CapabilityEnergy.ENERGY, null);
	}

}
