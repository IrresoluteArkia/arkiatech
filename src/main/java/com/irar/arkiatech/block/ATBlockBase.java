package com.irar.arkiatech.block;

import com.irar.arkiatech.handlers.ATCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ATBlockBase extends Block{

	public ATBlockBase(Material materialIn, String registryName) {
		super(materialIn);
		this.setUnlocalizedName(registryName);
		this.setRegistryName(registryName);
		this.setCreativeTab(ATCreativeTabs.ARKIATECHMAIN);
	}

	
	
}
