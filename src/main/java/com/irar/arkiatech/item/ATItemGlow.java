package com.irar.arkiatech.item;

import net.minecraft.item.ItemStack;

public class ATItemGlow extends ATItemBase {

	public ATItemGlow(String registryName) {
		super(registryName);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

}
