package com.irar.arkiatech.block;

import com.irar.arkiatech.tileentity.TileUpgrader;
import com.irar.arkiatech.tileentity.ATTEGui;

import net.minecraft.block.material.Material;

public class ATUpgrader extends ATMachineBlockBase<TileUpgrader>{

	public ATUpgrader(Material materialIn, String registryName) {
		super(materialIn, registryName, ATTEGui.UPGRADER);
	}

}
