package com.irar.arkiatech.block;

import com.irar.arkiatech.tileentity.ATTEGui;
import com.irar.arkiatech.tileentity.TilePFurnaceSingle;

import net.minecraft.block.material.Material;

public class ATPFurnace extends ATMachineBlockBase<TilePFurnaceSingle> {

	public ATPFurnace(Material materialIn, String registryName) {
		super(materialIn, registryName, ATTEGui.PFURNACE);
	}

}
