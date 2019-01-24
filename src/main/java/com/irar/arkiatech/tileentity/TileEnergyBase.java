package com.irar.arkiatech.tileentity;

import java.util.List;

import com.irar.arkiatech.ArkiaTech;
import com.irar.arkiatech.Ref;
import com.irar.arkiatech.gui.container.ContainerGrinder;
import com.irar.arkiatech.handlers.ATGuis;
import com.irar.arkiatech.handlers.ATItems;
import com.irar.arkiatech.network.ATPacketHandler;
import com.irar.arkiatech.network.FieldMessage;
import com.irar.arkiatech.recipe.GrinderRecipe;
import com.irar.arkiatech.recipe.GrinderRecipes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
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

public abstract class TileEnergyBase extends TileBase {
	
	protected ATEnergyStorage storage;
	protected ATTE tileATTE;
	protected boolean justLoaded = true;
	
	public TileEnergyBase(int capacity, int maxReceive, int maxExtract, ATTE tileATTE) {
    	this.storage = new ATEnergyStorage(this, 0, capacity, maxReceive, maxExtract);
    	this.tileATTE = tileATTE;
	}

    public abstract int getField(int id);

    public abstract void setField(int id, int value);

    public abstract int getFieldCount();
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        saveFieldsToNBT(compound);
        return compound;
	}
	
	protected void saveFieldsToNBT(NBTTagCompound compound) {
		for(int i = 0; i < this.getFieldCount(); i++) {
			String fieldKey = getFieldNBTKey(i);
			compound.setInteger(fieldKey, this.getField(i));
		}
	}

	protected String getFieldNBTKey(int i) {
		return "ARKIATECH_" + this.getSuggestedGuiId().toUpperCase() + "_FIELD_" + i;
	}

	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        readFieldsFromNBT(compound);
    }
    
    protected void readFieldsFromNBT(NBTTagCompound compound) {
		for(int i = 0; i < this.getFieldCount(); i++) {
			String fieldKey = getFieldNBTKey(i);
			if(compound.hasKey(fieldKey)) {
				this.setField(i, compound.getInteger(fieldKey));
			}
		}
	}

	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return this.getCapability(capability, facing) != null;
    }
    
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(capability == CapabilityEnergy.ENERGY){
            IEnergyStorage storage = this.storage;
            if(storage != null){
                return (T)storage;
            }
        }
        return super.getCapability(capability, facing);
    }
    
	protected final String getSuggestedGuiId() {
		return Ref.MODID + ":tile_" + tileATTE.toString();
	}

	public int getMaxEnergyStored() {
		return Math.max(storage.getMaxEnergyStored(), 1);
	}
	
	public int getEnergyStored() {
		return storage.getEnergyStored();
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();
		compound = this.writeToNBT(compound);
		return compound;
	} 
	
	@Override
	public void handleUpdateTag(NBTTagCompound compound) {
		this.readFromNBT(compound);
	}

}
