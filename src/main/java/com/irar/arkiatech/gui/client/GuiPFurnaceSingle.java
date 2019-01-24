package com.irar.arkiatech.gui.client;

import com.irar.arkiatech.tileentity.ATTEGui;
import com.irar.arkiatech.tileentity.TilePFurnaceSingle;

import net.minecraft.entity.player.InventoryPlayer;

public class GuiPFurnaceSingle extends GuiPFurnace<TilePFurnaceSingle> {

	public GuiPFurnaceSingle(InventoryPlayer inventory, TilePFurnaceSingle tileEntity) {
		super(inventory, tileEntity, ATTEGui.PFURNACE);
	}
	
	

}
