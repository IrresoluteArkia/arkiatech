package com.irar.arkiatech.tileentity;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class ATEnergyStorage extends EnergyStorage{

	private TileEnergyBase tile;

	public ATEnergyStorage(TileEnergyBase tile, int energy, int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract, energy);
		this.tile = tile;
	}
    
    public void setEnergyLevel(int energy) {
    	this.energy = energy;
    }
    
    public void useEnergy(int energy) {
    	this.energy -= energy;
    }
    
    public void pullEnergyFromSurrounding(World world, BlockPos pos) {
		BlockPos[] pullPositions = new BlockPos[] {pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 1, 0), pos.add(0, -1, 0), pos.add(0, 0, 1), pos.add(0, 0, -1)};
		for(BlockPos pullPos : pullPositions) {
			int amountNeeded = Math.min(this.maxReceive, this.getMaxEnergyStored() - this.energy);
			TileEntity te2 = world.getTileEntity(pullPos);
			BlockPos facingVec = pos.subtract(pullPos);
			if(te2 != null && te2.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()))) {
				IEnergyStorage energySource = te2.getCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()));
				if(energySource.canExtract() && this.canReceive()) {
					int energyExtracted = energySource.extractEnergy(amountNeeded, false);
					this.receiveEnergy(energyExtracted, false);
				}
			}
		}
	}
    
    public void pushEnergyToSurrounding(World world, BlockPos pos) {
		BlockPos[] pushPositions = new BlockPos[] {pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 1, 0), pos.add(0, -1, 0), pos.add(0, 0, 1), pos.add(0, 0, -1)};
		for(BlockPos pushPos : pushPositions) {
			int amountNeeded = Math.min(this.maxReceive, this.getMaxEnergyStored() - this.energy);
			TileEntity te2 = world.getTileEntity(pushPos);
			BlockPos facingVec = pos.subtract(pushPos);
			if(te2 != null && te2.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()))) {
				IEnergyStorage energyTarget = te2.getCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()));
				if(energyTarget.canReceive() && this.canExtract()) {
					int energyTryExtract = this.maxExtract;
					int energyTryRecieve = this.extractEnergy(energyTryExtract, true);
					int energyExtract = energyTarget.receiveEnergy(energyTryRecieve, true);
					int energyRecieve = this.extractEnergy(energyExtract, false);
					energyTarget.receiveEnergy(energyRecieve, false);
				}
			}
		}
    }

	public void produceEnergy(int energy) {
		if(energy + this.energy > this.getMaxEnergyStored()) {
			this.energy = this.getMaxEnergyStored();
		}else {
			this.energy += energy;
		}
	}

	public void pushEnergyEquallyToSurrounding(World world, BlockPos pos) {
		if(this.energy >= this.maxExtract * 6) {
			this.pushEnergyToSurrounding(world, pos);
			return;
		}
		BlockPos[] pushPositions = new BlockPos[] {pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 1, 0), pos.add(0, -1, 0), pos.add(0, 0, 1), pos.add(0, 0, -1)};
		int[] energiesNeeded = new int[6];
		for(int i = 0; i < 6; i++) {
			BlockPos pushPos = pushPositions[i];
			int amountNeeded = Math.min(this.maxReceive, this.getMaxEnergyStored() - this.energy);
			TileEntity te2 = world.getTileEntity(pushPos);
			BlockPos facingVec = pos.subtract(pushPos);
			if(te2 != null && te2.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()))) {
				IEnergyStorage energyTarget = te2.getCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()));
				if(energyTarget.canReceive() && this.canExtract()) {
					int energyTryExtract = this.maxExtract;
					int energyTryRecieve = this.extractEnergy(energyTryExtract, true);
					int energyNeeded = energyTarget.receiveEnergy(energyTryRecieve, true);
					energiesNeeded[i] = energyNeeded;
				}
			}
		}
		int totalEnergyNeeded = sum(energiesNeeded);
		if(totalEnergyNeeded <= this.energy) {
			this.pushEnergyToSurrounding(world, pos);
			return;
		}
		float ratioGiven = (float) totalEnergyNeeded / energy;
		int[] energiesToGive = multiply(energiesNeeded, ratioGiven);
		for(int i = 0; i < 6; i++) {
			BlockPos pushPos = pushPositions[i];
			TileEntity te2 = world.getTileEntity(pushPos);
			BlockPos facingVec = pos.subtract(pushPos);
			if(te2 != null && te2.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()))) {
				IEnergyStorage energyTarget = te2.getCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()));
				if(energyTarget.canReceive() && this.canExtract()) {
					int energyExtract = energiesToGive[i];
					int energyRecieve = this.extractEnergy(energyExtract, false);
					energyTarget.receiveEnergy(energyRecieve, false);
				}
			}
		}
	}

	private int[] multiply(int[] is, float f) {
		int[] products = new int[is.length];
		for(int i = 0; i < is.length; i++) {
			products[i] = (int) (is[i] * f);
		}
		return products;
	}

	private int sum(int[] is) {
		int total = 0;
		for(int i : is) {
			total += i;
		}
		return total;
	}

	public void pipePushEnergyEquallyToSurrounding(World world, BlockPos pos) {
		if(this.energy >= this.maxExtract * 6) {
			this.pipePushEnergyToSurrounding(world, pos);
			return;
		}
		BlockPos[] pushPositions = new BlockPos[] {pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 1, 0), pos.add(0, -1, 0), pos.add(0, 0, 1), pos.add(0, 0, -1)};
		int[] energiesNeeded = new int[6];
		for(int i = 0; i < 6; i++) {
			BlockPos pushPos = pushPositions[i];
			int amountNeeded = Math.min(this.maxReceive, this.getMaxEnergyStored() - this.energy);
			TileEntity te2 = world.getTileEntity(pushPos);
			BlockPos facingVec = pos.subtract(pushPos);
			if(te2 != null && te2.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()))) {
				IEnergyStorage energyTarget = te2.getCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()));
				if(isPipe(energyTarget)) {
					if(getPowerRatio(this) > getPowerRatio(energyTarget)) {
						int energyTryExtract = this.maxExtract;
						int energyTryRecieve = this.extractEnergy(energyTryExtract, true);
						int energyNeeded = energyTarget.receiveEnergy(energyTryRecieve, true);
						energiesNeeded[i] = energyNeeded;
					}
				}else if(energyTarget.canReceive() && this.canExtract()) {
					int energyTryExtract = this.maxExtract;
					int energyTryRecieve = this.extractEnergy(energyTryExtract, true);
					int energyNeeded = energyTarget.receiveEnergy(energyTryRecieve, true);
					energiesNeeded[i] = energyNeeded;
				}
			}
		}
		int totalEnergyNeeded = sum(energiesNeeded);
		if(totalEnergyNeeded <= this.energy) {
			this.pipePushEnergyToSurrounding(world, pos);
			return;
		}
		float ratioGiven = (float) energy / totalEnergyNeeded;
		int[] energiesToGive = multiply(energiesNeeded, ratioGiven);
		for(int i = 0; i < 6; i++) {
			BlockPos pushPos = pushPositions[i];
			TileEntity te2 = world.getTileEntity(pushPos);
			BlockPos facingVec = pos.subtract(pushPos);
			if(te2 != null && te2.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()))) {
				IEnergyStorage energyTarget = te2.getCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()));
				if(isPipe(energyTarget)) {
					if(getPowerRatio(this) > getPowerRatio(energyTarget)) {
						int energyExtract = energiesToGive[i];
						int energyRecieve = this.extractEnergy(energyExtract, false);
						energyTarget.receiveEnergy(energyRecieve, false);
					}
				}else if(energyTarget.canReceive() && this.canExtract()) {
					int energyExtract = energiesToGive[i];
					int energyRecieve = this.extractEnergy(energyExtract, false);
					energyTarget.receiveEnergy(energyRecieve, false);
				}
			}
		}
	}

	public void pipePushEnergyToSurrounding(World world, BlockPos pos) {
		BlockPos[] pushPositions = new BlockPos[] {pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 1, 0), pos.add(0, -1, 0), pos.add(0, 0, 1), pos.add(0, 0, -1)};
		for(BlockPos pushPos : pushPositions) {
			int amountNeeded = Math.min(this.maxReceive, this.getMaxEnergyStored() - this.energy);
			TileEntity te2 = world.getTileEntity(pushPos);
			BlockPos facingVec = pos.subtract(pushPos);
			if(te2 != null && te2.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()))) {
				IEnergyStorage energyTarget = te2.getCapability(CapabilityEnergy.ENERGY, EnumFacing.getFacingFromVector(facingVec.getX(), facingVec.getY(), facingVec.getZ()));
				if(isPipe(energyTarget)) {
					if(getPowerRatio(this) > getPowerRatio(energyTarget)) {
						int energyTryExtract = this.maxExtract;
						int energyTryRecieve = this.extractEnergy(energyTryExtract, true);
						int energyExtract = energyTarget.receiveEnergy(energyTryRecieve, true);
						int energyRecieve = this.extractEnergy(energyExtract, false);
						energyTarget.receiveEnergy(energyRecieve, false);
					}
				}else if(energyTarget.canReceive() && this.canExtract()) {
					int energyTryExtract = this.maxExtract;
					int energyTryRecieve = this.extractEnergy(energyTryExtract, true);
					int energyExtract = energyTarget.receiveEnergy(energyTryRecieve, true);
					int energyRecieve = this.extractEnergy(energyExtract, false);
					energyTarget.receiveEnergy(energyRecieve, false);
				}
			}
		}
	}

	private boolean isPipe(IEnergyStorage storage) {
		if(storage instanceof ATEnergyStorage) {
			if(((ATEnergyStorage) storage).tile instanceof TileEnergyPipe) {
				return true;
			}
		}
		return false;
	}

	private float getPowerRatio(IEnergyStorage storage) {
		float ratio = (float) storage.getEnergyStored() / storage.getMaxEnergyStored();
		return ratio;
	}

	public void pushEnergyEquallyTo(List<IEnergyStorage> storages) {
		if(this.energy >= this.maxExtract * storages.size()) {
			this.pushEnergyTo(storages);
			return;
		}
		int[] energiesNeeded = new int[storages.size()];
		for(int i = 0; i < storages.size(); i++) {
			IEnergyStorage energyTarget = storages.get(i);
			if(energyTarget.canReceive() && this.canExtract()) {
				int energyTryExtract = this.maxExtract;
				int energyTryRecieve = this.extractEnergy(energyTryExtract, true);
				int energyNeeded = energyTarget.receiveEnergy(energyTryRecieve, true);
				energiesNeeded[i] = energyNeeded;
			}
		}
		int totalEnergyNeeded = sum(energiesNeeded);
		if(totalEnergyNeeded <= this.energy) {
			this.pushEnergyTo(storages);
			return;
		}
		float ratioGiven = (float)  energy / totalEnergyNeeded;
		int[] energiesToGive = multiply(energiesNeeded, ratioGiven);
		for(int i = 0; i < storages.size(); i++) {
			IEnergyStorage energyTarget = storages.get(i);
			if(energyTarget.canReceive() && this.canExtract()) {
				int energyExtract = energiesToGive[i];
				int energyRecieve = this.extractEnergy(energyExtract, false);
				energyTarget.receiveEnergy(energyRecieve, false);
			}
		}
	}

	private void pushEnergyTo(List<IEnergyStorage> storages) {
		for(IEnergyStorage energyTarget : storages) {
			if(energyTarget.canReceive() && this.canExtract()) {
				int energyTryExtract = this.maxExtract;
				int energyTryRecieve = this.extractEnergy(energyTryExtract, true);
				int energyExtract = energyTarget.receiveEnergy(energyTryRecieve, true);
				int energyRecieve = this.extractEnergy(energyExtract, false);
				energyTarget.receiveEnergy(energyRecieve, false);
			}
		}
	}

}
