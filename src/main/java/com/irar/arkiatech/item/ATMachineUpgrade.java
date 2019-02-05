package com.irar.arkiatech.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ATMachineUpgrade extends ATItemBase implements IItemColor {

	protected int color;
	
	public ATMachineUpgrade(String registryName, int color) {
		super(registryName);
		this.color = color;
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return tintIndex < 1 || tintIndex > 2 ? -1 : color;
	}

}
