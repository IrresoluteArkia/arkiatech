package com.irar.arkiatech.tileentity;

import java.util.Collections;
import java.util.List;

import com.irar.arkiatech.handlers.ATItems;
import com.irar.arkiatech.item.ATMachineUpgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class UpgradeInventory implements INBTInventory {

	private TileMachineBase tile;
	private int stackLimit;
	private List<ItemStack> inventory;

	public UpgradeInventory(TileMachineBase tile, int numSlots, int stackLimit) {
		this.tile = tile;
		this.stackLimit = stackLimit;
		this.inventory = NonNullList.<ItemStack>withSize(numSlots, ItemStack.EMPTY);
	}

	@Override
	public String getName() {
		return "Upgrades";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public int getSizeInventory() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack stack : inventory) {
			if(!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.inventory, index, count);

        if (!itemstack.isEmpty())
        {
            this.markDirty();
        }

        return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.getStackInSlot(index);
	    this.setInventorySlotContents(index, null);
        this.markDirty();
	    return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventory.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return stackLimit;
	}

	@Override
	public void markDirty() {
		tile.markDirty();
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
		return stack.getItem() instanceof ATMachineUpgrade;
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
		Collections.fill(inventory, ItemStack.EMPTY);
	}
	
	public int getNumEnergyUpgrades() {
		return getNumUpgrades(ATItems.upgradeEnergy);
	}

	public int getNumUpgrades(Item upgrade) {
		int upgrades = 0;
		for(ItemStack stack : inventory) {
			if(stack.getItem().equals(upgrade)) {
				upgrades += stack.getCount();
			}
		}
		return upgrades;
	}

	public int getNumSpeedUpgrades() {
		return getNumUpgrades(ATItems.upgradeSpeed);
	}

}
