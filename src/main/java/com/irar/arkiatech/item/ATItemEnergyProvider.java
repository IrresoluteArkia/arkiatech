package com.irar.arkiatech.item;

import com.irar.arkiatech.tileentity.ATEnergyStorage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ATItemEnergyProvider implements ICapabilitySerializable<NBTTagCompound> {

	public ATEnergyStorage storage;
	
	public ATItemEnergyProvider(int maxEnergy) {
		storage = new ATEnergyStorage(null, 0, maxEnergy, 2048, 2048);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return this.getCapability(capability, facing) != null;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY){
            IEnergyStorage storage = this.storage;
            if(storage != null){
                return (T)storage;
            }
        }
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("ENERGY_STORED", storage.getEnergyStored());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("ENERGY_STORED")) {
			storage.setEnergyLevel(nbt.getInteger("ENERGY_STORED"));
		}
	}

}
