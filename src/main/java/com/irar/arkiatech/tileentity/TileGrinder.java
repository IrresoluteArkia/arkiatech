package com.irar.arkiatech.tileentity;

import com.irar.arkiatech.Ref;
import com.irar.arkiatech.config.ConfigInts;
import com.irar.arkiatech.gui.container.ContainerGrinder;
import com.irar.arkiatech.handlers.ATItems;
import com.irar.arkiatech.recipe.GrinderRecipe;
import com.irar.arkiatech.recipe.GrinderRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileGrinder extends TileMachineBase{

	private int currentGrindTime;
	private int currentDriftLevel;
	private boolean inventoryChanged = true;
	private GrinderRecipe currentRecipe;

	public TileGrinder() {
		super(3, ConfigInts.MAX_GRINDER_ENERGY.currentValue, 2048, 0, ATTEGui.GRINDER);
	}

	@Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return currentGrindTime;
            case 1:
                return currentDriftLevel;
            case 2:
            	return storage.getEnergyStored();
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.currentGrindTime = value;
                break;
            case 1:
                this.currentDriftLevel = value;
                break;
            case 2:
            	this.storage.setEnergyLevel(value);
                break;
        default:
            break;
        }
    }
    
    @Override
    public int getFieldCount()
    {
        return 3;
    }

	@Override
	public String getName() {
		return getDisplayName_();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		switch(side) {
		case DOWN:
			return new int[] {2, 1, 0};
		default:
		case UP:
		case EAST:
		case NORTH:
		case SOUTH:
		case WEST:
			return new int[] {2, 1, 0};
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		if(index == 2) {
			return false;
		}else if(index == 1) {
			if(itemStackIn.getItem().equals(ATItems.zeDrift)) {
				return true;
			}
			return false;
		}else {
			return true;
		}
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if(index == 2) {
			return true;
		}
		return false;
	}

	@Override
	public void update() {
		super.update();
		/*if(this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
			this.storage.pullEnergyFromSurrounding(world, pos);
		}*/
		if(this.currentDriftLevel + this.getDriftPerDriftItem() <= this.getMaxDrift() && !this.getStackInSlot(1).isEmpty() && this.getStackInSlot(1).getItem().equals(ATItems.zeDrift)) {
			this.decrStackSize(1, 1);
			this.currentDriftLevel += this.getDriftPerDriftItem();
		}
		if(inventoryChanged) {
			if(!isRecipeValid(currentRecipe)) {
				currentRecipe = getRecipe();
			}
		}
		if(currentGrindTime >= getProcessingTime(0) && currentRecipe != null) {
			finishGrind();
		}
		if(inventoryChanged) {
			if(!isRecipeValid(currentRecipe)) {
				currentRecipe = getRecipe();
			}
			inventoryChanged = false;
		}
		if(currentRecipe != null) {
			if(this.storage.getEnergyStored() > this.getEnergyUsedPerTick()) {
				this.storage.useEnergy(this.getEnergyUsedPerTick());
				currentGrindTime++;
			}
		}else {
			if(currentGrindTime > 0) {
				currentGrindTime--;
			}
		}
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
		inventoryChanged  = true;
	}
	
	private GrinderRecipe getRecipe() {
		for(GrinderRecipe recipe : GrinderRecipes.allRecipes) {
			if(isRecipeValid(recipe)) {
				return recipe;
			}
		}
		return null;
	}

	private void finishGrind() {
		decrStackSize(0, 1);
		this.currentDriftLevel -= this.getDriftUsedPerOperation();
		if(this.getStackInSlot(2).isEmpty()) {
			this.setInventorySlotContents(2, currentRecipe.getResultItemStack());
		}else {
			ItemStack result = currentRecipe.getResultItemStack();
			result.setCount(result.getCount() + this.getStackInSlot(2).getCount());
			this.setInventorySlotContents(2, result);
		}
		currentGrindTime = 0;
	}

	private boolean isRecipeValid(GrinderRecipe recipe) {
		if(recipe == null) {
			return false;
		}
		if(recipe.matches(this.getStackInSlot(0))) {
			if(currentDriftLevel >= this.getDriftUsedPerOperation()) {
				if(this.getStackInSlot(2).isEmpty()) {
					return true;
				}else {
					ItemStack is1 = this.getStackInSlot(2);
					ItemStack is2 = recipe.getResultItemStack();
					if(is1.getItem().equals(is2.getItem()) && is1.getMetadata() == is2.getMetadata() && is1.getCount() + is2.getCount() <= this.getInventoryStackLimit()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public int getCurrentGrindTime() {
		return this.getField(0);
	}
	
	public int getMaxDrift() {
		return 10000;
	}

	public int getCurrentDriftLevel() {
		return this.getField(1);
	}
	
	public int getDriftPerDriftItem() {
		return 100;
	}
	
	public int getDriftUsedPerOperation() {
		return 35;
	}

	public int getMaxEnergyStored() {
		return storage.getMaxEnergyStored();
	}

	@Override
	public int getBaseEnergyUsedPerTick() {
		return 20;
	}

	public static String getDisplayName_() {
		return "Mechanical Zephyri Integrator";
	}

	/**
	 * amount of time to grind one item
	 */
	@Override
	public int getBaseProcessingTime(int i) {
		return 200;
	}

}
