package com.irar.arkiatech.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class UpgraderCraftingInventory implements IInventory{

	NonNullList<ItemStack> inventory;
	private TileUpgrader te;

	public UpgraderCraftingInventory(int numSlots, TileUpgrader te) {
    	this.inventory = NonNullList.<ItemStack>withSize(numSlots, ItemStack.EMPTY);
    	this.te = te;
	}
	
	@Override
	public String getName() {
		return "";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public int getSizeInventory() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if(count > 0) {
			inventory.set(index, ItemStack.EMPTY);
		}
		this.markDirty();
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		inventory.set(index, ItemStack.EMPTY);
		this.markDirty();
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		inventory.set(index, stack);
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		te.markDirty();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for(int i = 0; i < inventory.size(); i++) {
			this.setInventorySlotContents(i, ItemStack.EMPTY);
		}
	}

	public void setItems(List<ItemStack> stacks) {
		for(int i = 0; i < Math.min(stacks.size(), inventory.size()); i++) {
			this.setInventorySlotContents(i, stacks.get(i));
		}
	}

}
