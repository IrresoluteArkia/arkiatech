package com.irar.arkiatech.item;

import com.irar.arkiatech.block.IFuelBlock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFuel extends ItemBlock {

	public ItemBlockFuel(Block block) {
		super(block);
	}
	
	@Override
	public int getItemBurnTime(ItemStack stack) {
		if(this.block instanceof IFuelBlock) {
			IFuelBlock fuelBlock = (IFuelBlock) this.block;
			return fuelBlock.getBurnTime(stack);
		}
		return 0;
	}

}
