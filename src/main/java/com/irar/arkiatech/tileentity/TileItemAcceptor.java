package com.irar.arkiatech.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.irar.arkiatech.util.ItemPipeHelper;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileItemAcceptor extends TileBase implements IItemAcceptor{

	private HashMap<IInventory, BlockPos> targets = new HashMap<>();
	private boolean shouldUpdateSources = true;

	@Override
	public void update() {
		Set<IInventory> previous = targets.keySet();
		findTarget();
		if(previous.equals(targets.keySet())) {
			shouldUpdateSources = true;
		}
		if(shouldUpdateSources) {
			ItemPipeHelper.updateSources(world, pos);
		}
	}
	
	private boolean findTarget() {
		BlockPos[] neighbors = ItemPipeHelper.getNeighborLocations(pos);
		HashMap<IInventory, BlockPos> invs = ItemPipeHelper.getMapInventoriesAtLocs(world, neighbors);
		targets = invs;
		return !invs.keySet().isEmpty();
	}
	
	@Override
	public boolean canAccept(IInventory source, ItemStack stack) {
		if(targets == null || targets.keySet().isEmpty()) {
			// Nowhere to put items
			return false;
		}
		List<IInventory> modifiedTargets = new ArrayList<>();
		for(IInventory inv : targets.keySet()) {
			if(!inv.equals(source)) {
				// Only add if the inventory is a different one
				modifiedTargets.add(inv);
			}
		}
		if(modifiedTargets.isEmpty()) {
			// No valid targets
			return false;
		}
		
		for(IInventory target : modifiedTargets) {
			for(int i = 0; i < target.getSizeInventory(); i++) {
				ItemStack inSlot = target.getStackInSlot(i);
				if(inSlot.isEmpty()) {
					return true;
				}
				// Check if items are stackable with each other; TODO: probably an easier, built-in way to do this
				if(ItemStack.areItemsEqual(inSlot, stack) && ItemStack.areItemStackTagsEqual(inSlot, stack) && inSlot.getItemDamage() == stack.getItemDamage() && inSlot.getCount() < inSlot.getMaxStackSize()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public EnumFacing getFacing(BlockPos pos1, BlockPos pos2) {
		BlockPos dif = pos1.subtract(pos2);
		return EnumFacing.getFacingFromVector(dif.getX(), dif.getY(), dif.getZ());
	}

	@Override
	public ItemStack accept(IInventory source, ItemStack stack) {
		for(IInventory target : targets.keySet()) {
			if(target.equals(source)) {
				// Don't transfer to the same inventory; causes all kinds of problems
				continue;
			}
			stack = TileEntityHopper.putStackInInventoryAllSlots(source, target, stack, getFacing(pos, targets.get(target)));
			if(stack.isEmpty()) {
				break;
			}
		}
		return stack;
	}

}
