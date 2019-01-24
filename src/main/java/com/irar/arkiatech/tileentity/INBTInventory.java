package com.irar.arkiatech.tileentity;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public interface INBTInventory extends IInventory {

	public default NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		for(int i = 0; i < this.getSizeInventory(); i++) {
			stacks.set(i, this.getStackInSlot(i));
		}
		ItemStackHelper.saveAllItems(compound, stacks);
		return compound;
	}
	
	public default void readFromNBT(NBTTagCompound compound) {
		NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, stacks);
		for(int i = 0; i < this.getSizeInventory(); i++) {
			this.setInventorySlotContents(i, stacks.get(i));
		}
	}
	
}
