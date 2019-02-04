package com.irar.arkiatech.tileentity;

import com.irar.arkiatech.config.ConfigDoubles;
import com.irar.arkiatech.config.ConfigInts;
import com.irar.arkiatech.gui.container.ContainerGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;

public class TileGenerator extends TileMachineBase {


	private int burnTime = 0;
	private int maxBurnTime = 1;

	public TileGenerator() {
		super(1, ConfigInts.MAX_GENERATOR_ENERGY.currentValue, 0, 2048, ATTEGui.GENERATOR);
	}

	@Override
	public int getField(int id) {
		switch(id) {
		case 0:
			return burnTime;
		case 1:
			return maxBurnTime;
		case 2:
			return storage.getEnergyStored();
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			burnTime = value;
			break;
		case 1:
			maxBurnTime = value;
			break;
		case 2:
			storage.setEnergyLevel(value);
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public String getName() {
		return "Generator";
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {0};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return TileEntityFurnace.isItemFuel(itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return true;
	}

	@Override
	public void update() {
		super.update();
		if(this.storage.getEnergyStored() > 0) {
			this.storage.pushEnergyEquallyToSurrounding(world, pos);
		}
		if(burnTime <= 0) {
			ItemStack stack = this.getStackInSlot(0);
			if(!stack.isEmpty() && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
				burnTime = getBurnTime(stack);
				maxBurnTime = burnTime;
				burnTime = getProcessingTime(0);
				this.decrStackSize(0, 1);
			}
		}
		if(burnTime > 0) {
			burnTime--;
			storage.produceEnergy(this.getEnergyProducedPerTick());
		}
	}

	public int getMaxEnergyStored() {
		return storage.getMaxEnergyStored();
	}

	@Override
	public int getBaseEnergyUsedPerTick() {
		return -ConfigInts.GENERATOR_ENERGY_PRODUCED.currentValue;
	}

	@Override
	public int getBaseProcessingTime(int i) {
		return maxBurnTime;
	}

	public int getBurnTime(ItemStack stack) {
		return (int) Math.max(TileEntityFurnace.getItemBurnTime(stack) * ConfigDoubles.GENERATOR_BURN_TIME_MODIFIER.currentValue, 1);
	}

}
