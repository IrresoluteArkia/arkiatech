package com.irar.arkiatech.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileEnergyPipe extends TileEnergyBase{

	public TileEnergyPipe() {
		super(4096, 2048, 2048, ATTE.ENERGYPIPE);
	}

	@Override
	public int getField(int id) {
		if(id == 0) {
			return this.getEnergyStored();
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if(id == 0) {
			this.storage.setEnergyLevel(value);
		}
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void update() {
		super.update();
//		if(this.storage.getEnergyStored() == this.storage.getMaxEnergyStored()) {
			storage.pipePushEnergyEquallyToSurrounding(world, pos);
//		}
	}

}
