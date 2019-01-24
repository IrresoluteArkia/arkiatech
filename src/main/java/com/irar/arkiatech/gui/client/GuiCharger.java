package com.irar.arkiatech.gui.client;

import java.text.NumberFormat;

import com.irar.arkiatech.tileentity.ATTEGui;
import com.irar.arkiatech.tileentity.TileCharger;

import net.minecraft.entity.player.InventoryPlayer;

public class GuiCharger extends GuiBase<TileCharger>{

	public GuiCharger(InventoryPlayer inventory, TileCharger tileEntity) {
		super(inventory, tileEntity, ATTEGui.CHARGER);
	    this.addEnergyDisplayArea(152, 16, 50);
	}
	
	@Override
	protected String getEnergyUsedText() {
    	NumberFormat format = NumberFormat.getInstance();
    	// TODO: change that magic number
		return "Charges at max " + format.format(2048) + " RF/t per item";
	}

}
