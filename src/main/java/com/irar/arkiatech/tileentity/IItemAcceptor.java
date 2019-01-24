package com.irar.arkiatech.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IItemAcceptor {

	/**
	 * Checks if the inventory behind this acceptor can accept an ItemStack
	 * @param stack to transfer
	 * @return true if it can accept the ItemStack
	 */
	public boolean canAccept(IInventory source, ItemStack stack);
	
	/**
	 * Transfers an ItemStack to this acceptor
	 * @param stack to transfer
	 * @return leftover items after transfer
	 */
	public ItemStack accept(IInventory source, ItemStack stack);
	
}
