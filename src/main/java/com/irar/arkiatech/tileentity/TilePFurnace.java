package com.irar.arkiatech.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.irar.arkiatech.handlers.ATItems;
import com.irar.arkiatech.recipe.GrinderRecipe;
import com.irar.arkiatech.recipe.GrinderRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TilePFurnace extends TileMachineBase {

	private static String[] nameDesignations = new String[] {"Broken", "Powered", "Double", "Triple", "Quadruple", "Multi"};
	private int numFurnaces;
	private List<Integer> furnaceProgresses;
	private boolean inventoryChanged = true;
	private List<Entry<ItemStack, ItemStack>> currentRecipes;
	private static Set<Entry<ItemStack, ItemStack>> allRecipes = FurnaceRecipes.instance().getSmeltingList().entrySet();

	public TilePFurnace(ATTEGui tileATTE, int numFurnaces) {
		super(numFurnaces * 2, 100000, 2048, 0, tileATTE);
		this.numFurnaces = numFurnaces;
		furnaceProgresses = NonNullList.<Integer>withSize(numFurnaces, 0);
		currentRecipes = new ArrayList<>();
		for(int i = 0; i < numFurnaces; i++) {
			currentRecipes.add(null);
		}
	}

	@Override
	public int getField(int id) {
		if(id < numFurnaces) {
			return furnaceProgresses.get(id);
		}else if(id == numFurnaces) {
			return storage.getEnergyStored();
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if(id < numFurnaces) {
			furnaceProgresses.set(id, value);
		}else if(id == numFurnaces) {
			storage.setEnergyLevel(value);;
		}
	}

	@Override
	public int getFieldCount() {
		return numFurnaces * 2 + 1;
	}

	@Override
	public String getName() {
		if(numFurnaces > 0 && numFurnaces < nameDesignations.length) {
			return nameDesignations[numFurnaces] + " Furnace";
		}
		return nameDesignations[nameDesignations.length - 1] + " Furnace";
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int[] slots = new int[this.getSizeInventory()];
		for(int i = 0; i < this.getSizeInventory(); i++) {
			slots[i] = i;
		}
		return slots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return index < numFurnaces;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index >= numFurnaces;
	}

	@Override
	public int getBaseEnergyUsedPerTick() {
		return 20;
	}

	@Override
	public void update() {
		super.update();
		/*if(this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
			this.storage.pullEnergyFromSurrounding(world, pos);
		}*/
		for(int i = 0; i < this.numFurnaces; i++) {
			Entry<ItemStack, ItemStack> currentRecipe = currentRecipes.get(i);
			int currentSmeltTime = this.furnaceProgresses.get(i);
			if(inventoryChanged) {
				if(!isRecipeValid(currentRecipe, i)) {
					currentRecipe = getRecipe(i);
					currentRecipes.set(i, currentRecipe);
				}
			}
			if(currentSmeltTime >= getProcessingTime(i) && currentRecipe != null) {
				finishSmelt(i, currentRecipe);
				currentSmeltTime = this.furnaceProgresses.get(i);
			}
			if(inventoryChanged) {
				if(!isRecipeValid(currentRecipe, i)) {
					currentRecipe = getRecipe(i);
					currentRecipes.set(i, currentRecipe);
				}
			}
			if(currentRecipe != null) {
				if(this.storage.getEnergyStored() > this.getEnergyUsedPerTick()) {
					this.storage.useEnergy(this.getEnergyUsedPerTick());
					currentSmeltTime++;
					this.furnaceProgresses.set(i, currentSmeltTime);
				}
			}else {
				if(currentSmeltTime > 0) {
					currentSmeltTime--;
					this.furnaceProgresses.set(i, currentSmeltTime);
				}
			}
		}
		inventoryChanged = false;
	}
	
	private void finishSmelt(int i, Entry<ItemStack, ItemStack> currentRecipe) {
		decrStackSize(i, 1);
		if(this.getStackInSlot(i + this.numFurnaces).isEmpty()) {
			this.setInventorySlotContents(i + this.numFurnaces, currentRecipe.getValue().copy());
		}else {
			ItemStack result = currentRecipe.getValue().copy();
			result.setCount(result.getCount() + this.getStackInSlot(i + this.numFurnaces).getCount());
			this.setInventorySlotContents(i + this.numFurnaces, result);
		}
		this.furnaceProgresses.set(i, 0);
	}

	private Entry<ItemStack, ItemStack> getRecipe(int i) {
		for(Entry<ItemStack, ItemStack> recipe : allRecipes) {
			if(isRecipeValid(recipe, i)) {
				return recipe;
			}
		}
		return null;
	}

	private boolean isRecipeValid(Entry<ItemStack, ItemStack> recipe, int i) {
		ItemStack stackIn = this.getStackInSlot(i);
		if(recipe == null) {
			return false;
		}
		if(this.compareItemStacks(stackIn, recipe.getKey())) {
			if(this.getStackInSlot(i + numFurnaces).isEmpty()) {
				return true;
			}else {
				ItemStack is1 = this.getStackInSlot(i + numFurnaces);
				ItemStack is2 = recipe.getValue();
				if(this.compareItemStacks(is1, is2) && is1.getCount() + is2.getCount() <= this.getInventoryStackLimit()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void markDirty() {
		super.markDirty();
		this.inventoryChanged  = true;
	}
	
    /**
     * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
     * 
     * Copied from FurnaceRecipes.
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
    
    public int getNumFurnaces() {
    	return this.numFurnaces;
    }
    
    public int getSmeltProgress(int i) {
    	return this.furnaceProgresses.get(i);
    }

	@Override
	public int getBaseProcessingTime(int i) {
		return 100;
	}

}
