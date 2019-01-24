package com.irar.arkiatech.gui.container;

import com.irar.arkiatech.gui.container.slot.SlotCharging;
import com.irar.arkiatech.tileentity.TileCharger;

import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCharger extends ContainerBase<TileCharger> {

	public ContainerCharger(InventoryPlayer playerInventory, TileCharger te) {
		super(playerInventory, te);
		int[] chargingLoc = getChargingLoc();
		int x = chargingLoc[0] - (te.getSizeInventory() * 18) / 2;
		int y = chargingLoc[1];
		for(int i = 0; i < te.getSizeInventory(); i++) {
			this.addSlotToContainer(new SlotCharging(te, i, x + i * 18, y));
		}
	}

	private int[] getChargingLoc() {
		return new int[] {88, 30};
	}

}
