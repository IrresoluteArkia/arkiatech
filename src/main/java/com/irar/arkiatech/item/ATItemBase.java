package com.irar.arkiatech.item;

import com.irar.arkiatech.handlers.ATCreativeTabs;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class ATItemBase extends Item{

	public ATItemBase(String registryName) {
		this.setUnlocalizedName(registryName);
		this.setRegistryName(registryName);
		this.setCreativeTab(ATCreativeTabs.ARKIATECHMAIN);
	}
	
}
