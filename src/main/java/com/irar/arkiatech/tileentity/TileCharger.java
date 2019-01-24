package com.irar.arkiatech.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.item.ATEnergyItem;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileCharger extends TileMachineBase{

	public TileCharger() {
		super(4, 100000, 8192, 2048, ATTEGui.CHARGER);
	}

	@Override
	public int getField(int id) {
		if(id == 0) {
			return storage.getEnergyStored();
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if(id == 0) {
			storage.setEnergyLevel(value);
		}
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public String getName() {
		return "Charger";
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int[] slots = new int[this.getSizeInventory()];
		for(int i = 0; i < slots.length; i++) {
			slots[i] = i;
		}
		return slots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return itemStackIn.hasCapability(CapabilityEnergy.ENERGY, null);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if(stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			if(ATEnergyItem.getEnergy(stack) < ATEnergyItem.getMaxEnergy(stack)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getBaseEnergyUsedPerTick() {
		return 0;
	}

	@Override
	public void update() {
		super.update();
		List<IEnergyStorage> ess = new ArrayList<>();
		for(int i = 0; i < this.getSizeInventory(); i++) {
			ItemStack stack = this.getStackInSlot(i);
			if(!stack.isEmpty() && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage eStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);
				if(eStorage != null) {
					ess.add(eStorage);
				}
			}
		}
		if(ess.size() > 0) {
			storage.pushEnergyEquallyTo(ess);
		}
	}

	@Override
	public int getBaseProcessingTime(int i) {
		return 0;
	}

}
