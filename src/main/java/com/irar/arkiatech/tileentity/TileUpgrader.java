package com.irar.arkiatech.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.recipe.GrinderRecipe;
import com.irar.arkiatech.recipe.GrinderRecipes;
import com.irar.arkiatech.recipe.UpgraderRecipe;
import com.irar.arkiatech.recipe.UpgraderRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TileUpgrader extends TileMachineBase{
	
	public UpgraderCraftingInventory crInv;
	private boolean inventoryChanged = true;
	private UpgraderRecipe currentRecipe;
	private int currentProcessTime = 0;
	
	public TileUpgrader() {
		super(11, 100000, 2048, 0, ATTEGui.UPGRADER);
		crInv = new UpgraderCraftingInventory(5, this);
	}

	@Override
	public int getField(int id) {
		switch(id) {
		case 0:
			return currentProcessTime;
		case 2:
			return storage.getEnergyStored();
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			currentProcessTime = value;
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
		return getDisplayName_();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return index > 0;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 0;
	}

	@Override
	public int getBaseEnergyUsedPerTick() {
		return 80;
	}

	@Override
	public void update() {
		super.update();
		if(inventoryChanged ) {
			if(!isRecipeValid(currentRecipe)) {
				currentRecipe = getRecipe();
			}
		}
		if(currentProcessTime >= getProcessingTime(0) && currentRecipe != null) {
			finishProcess();
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
				currentProcessTime++;
			}
		}else {
			if(currentProcessTime > 0) {
				currentProcessTime--;
			}
		}
	}
	
	private void finishProcess() {
		currentRecipe.useIngredients(getIngredientStacks());
		if(this.getStackInSlot(0).isEmpty()) {
			this.setInventorySlotContents(0, currentRecipe.getResult());
		}else {
			ItemStack result = currentRecipe.getResult();
			result.setCount(result.getCount() + this.getStackInSlot(0).getCount());
			this.setInventorySlotContents(0, result);
		}
		currentProcessTime = 0;
	}

	private List<ItemStack> getIngredientStacks() {
		List<ItemStack> stacks = new ArrayList<>();
		stacks.addAll(inventory);
		stacks.remove(0);
		return stacks;
	}

	private UpgraderRecipe getRecipe() {
		for(UpgraderRecipe recipe : UpgraderRecipes.allRecipes) {
			if(isRecipeValid(recipe)) {
				return recipe;
			}
		}
		return null;
	}

	private boolean isRecipeValid(UpgraderRecipe recipe) {
		if(recipe == null) {
			return false;
		}
		if(recipe.matches(this.crInv.inventory) && recipe.hasIngredients(getIngredientStacks())) {
			if(this.getStackInSlot(0).isEmpty()) {
				return true;
			}else {
				ItemStack is1 = this.getStackInSlot(0);
				ItemStack is2 = recipe.getResult();
				if(is1.getItem().equals(is2.getItem()) && is1.getMetadata() == is2.getMetadata() && is1.getCount() + is2.getCount() <= this.getInventoryStackLimit()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        NBTTagCompound crInvComp = new NBTTagCompound();
        ItemStackHelper.saveAllItems(crInvComp, this.crInv.inventory);
        compound.setTag("crafting_inventory_compound", crInvComp);
        return compound;
	}

	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        if(compound.hasKey("crafting_inventory_compound")) {
        	NBTTagCompound crInvComp = (NBTTagCompound) compound.getTag("crafting_inventory_compound");
            ItemStackHelper.loadAllItems(crInvComp, this.crInv.inventory);
        }
    }
	
	@Override
	public void markDirty() {
		super.markDirty();
		inventoryChanged = true;
	}

	public int getCurrentProcessTime() {
		return this.currentProcessTime;
	}

	public static String getDisplayName_() {
		return "Restructurer";
	}
	
	@Override
	public void transferItems(List<ItemStack> stacks) {
		crInv.setItems(stacks);
	}

	@Override
	public int getBaseProcessingTime(int i) {
		return 200;
	}


}
