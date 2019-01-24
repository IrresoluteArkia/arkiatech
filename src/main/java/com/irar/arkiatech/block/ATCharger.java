package com.irar.arkiatech.block;

import com.irar.arkiatech.tileentity.ATTEGui;
import com.irar.arkiatech.tileentity.TileCharger;

import net.minecraft.block.material.Material;

public class ATCharger extends ATMachineBlockBase<TileCharger>{

	public ATCharger(Material materialIn, String registryName) {
		super(materialIn, registryName, ATTEGui.CHARGER);
	}

}
