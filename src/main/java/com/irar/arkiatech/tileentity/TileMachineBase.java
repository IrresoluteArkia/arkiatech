package com.irar.arkiatech.tileentity;

import java.util.List;

import com.irar.arkiatech.Ref;
import com.irar.arkiatech.gui.container.ContainerGrinder;
import com.irar.arkiatech.handlers.ATGuis;
import com.irar.arkiatech.handlers.ATItems;
import com.irar.arkiatech.recipe.GrinderRecipe;
import com.irar.arkiatech.recipe.GrinderRecipes;

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

public abstract class TileMachineBase extends TileEnergyBase implements ISidedInventory, ITickable {
	
    protected NonNullList<ItemStack> inventory;
	protected ATEnergyStorage storage;
	protected ATTEGui tileATTE;
	public UpgradeInventory upgrades = new UpgradeInventory(this, 4, 16);
	
	public TileMachineBase(int numSlots, int capacity, int maxReceive, int maxExtract, ATTEGui tileATTE) {
		super(capacity, maxReceive, maxExtract, tileATTE);
    	this.inventory = NonNullList.<ItemStack>withSize(numSlots, ItemStack.EMPTY);
    	if(this.getEnergyProducedPerTick() >= 0) {
    		upgrades = new UpgradeInventory(this, 0, 16);
    	}else {
    		upgrades = new UpgradeInventory(this, 4, 16);
    	}
    	this.storage = new ATEnergyStorage(this, 0, capacity, maxReceive, maxExtract);
    	this.tileATTE = tileATTE;
	}

	@Override
	public int getSizeInventory() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack stack : inventory) {
			if(!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.inventory, index, count);

        if (!itemstack.isEmpty())
        {
            this.markDirty();
        }

        return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.getStackInSlot(index);
	    this.setInventorySlotContents(index, ItemStack.EMPTY);
        this.markDirty();
	    return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventory.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override public void openInventory(EntityPlayer player) {}
	@Override public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == 2) {
			return false;
		}else if(index == 1) {
			if(stack.getItem().equals(ATItems.zeDrift)) {
				return true;
			}
			return false;
		}else {
			return true;
		}
	}

	@Override
    public abstract int getField(int id);

    @Override
    public abstract void setField(int id, int value);

    @Override
    public abstract int getFieldCount();

	@Override
	public void clear() {
		for (int i = 0; i < this.getSizeInventory(); i++)
	        this.setInventorySlotContents(i, null);
		this.markDirty();
	}

	@Override
	public abstract String getName();

	@Override
	public boolean hasCustomName() {
		return false;
	}

	/*@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return ATGuis.instantiateContainer(tileATTE, playerInventory, this);
	};

	@Override
	public String getGuiID() {
		return this.getSuggestedGuiId();
	}*/

	@Override
	public abstract int[] getSlotsForFace(EnumFacing side);

	@Override
	public abstract boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction);

	@Override
	public abstract boolean canExtractItem(int index, ItemStack stack, EnumFacing direction);
	
	/**
	 * 
	 * @return amount of energy used per tick, negative to indicate energy produced
	 */
	public abstract int getBaseEnergyUsedPerTick();
	
	public int getEnergyUsedPerTick() {
		if(this.getBaseEnergyUsedPerTick() < 0) {
			return this.getBaseEnergyUsedPerTick();
		}
		int powerPenalty = this.getNumSpeedUpgrades() - this.getNumEnergyUpgrades();
		float actual = 1;
		if(powerPenalty < 0) {
			actual = (float) Math.pow(1.1, powerPenalty);
		}else if(powerPenalty > 0) {
			actual = (float) ((powerPenalty + 1) * Math.pow(1.1, powerPenalty));
		}
		return (int) Math.max(1, this.getBaseEnergyUsedPerTick() * (actual));
	}
	
	protected int getNumEnergyUpgrades() {
		return upgrades.getNumEnergyUpgrades();
	}

	protected int getNumSpeedUpgrades() {
		return upgrades.getNumSpeedUpgrades();
	}

	/**
	 * to change, change energy used per tick
	 * 
	 * @return amount of energy produced per tick; by default, the negative of energy used
	 */
	public final int getEnergyProducedPerTick() {
		return -getBaseEnergyUsedPerTick();
	}

	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        saveFieldsToNBT(compound);
        compound.setTag("ARKIATECH_UPGRADES", this.upgrades.writeToNBT());
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
        this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        readFieldsFromNBT(compound);
        if(compound.hasKey("ARKIATECH_UPGRADES")) {
        	this.upgrades.readFromNBT((NBTTagCompound) compound.getTag("ARKIATECH_UPGRADES"));
        }
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

	public int getMaxEnergyStored() {
		return Math.max(storage.getMaxEnergyStored(), 1);
	}
	
	public int getEnergyStored() {
		return storage.getEnergyStored();
	}

	public void transferItems(List<ItemStack> stacks) {}
	
	public abstract int getBaseProcessingTime(int i);
	
	public int getProcessingTime(int i) {
		return Math.max(1, this.getBaseProcessingTime(i) / (this.getNumSpeedUpgrades() + 1));
	}

}
