package com.irar.arkiatech.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.irar.arkiatech.util.ItemPipeHelper;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileItemSource extends TileBase{

	public IInventory source = null;
	public BlockPos sourceLoc = null;
	private boolean shouldSearch = true;
	List<IItemAcceptor> acceptors = new ArrayList<>();
	
	@Override
	public void update() {
		super.update();
		
		// Searching for valid source
		if(source == null) {
			if(!findSource()) {
				// Failed
				return;
			}
		}
		
		// Found valid source
		if(shouldSearch) {
			searchForAcceptors();
			shouldSearch = false;
		}
		
		if(acceptors.size() <= 0) {
			// Nowhere to send items
			return;
		}
		
		// Send an ItemStack
		sendItemStacks(1);
	}

	/**
	 * Sends numToSend ItemStacks to IItemAcceptor's
	 * @param numToSend
	 */
	private void sendItemStacks(int numToSend) {
		if(numToSend <= 0) {
			return;
		}
		int sent = 0;
		for(int i = 0; i < source.getSizeInventory(); i++) {
			ItemStack stack = source.getStackInSlot(i);
			if(source instanceof ISidedInventory) {
				ISidedInventory sSource = (ISidedInventory) source;
				if(!sSource.canExtractItem(i, stack, getFacing(pos, sourceLoc))) {
					continue;
				}
			}
			if(!stack.isEmpty()) {
				for(IItemAcceptor acceptor : acceptors) {
					if(acceptor.canAccept(source, stack)) {
						source.setInventorySlotContents(i, acceptor.accept(source, source.removeStackFromSlot(i)));
						sent++;
						break;
					}
				}
			}
			if(sent >= numToSend) {
				break;
			}
		}
	}

	private void searchForAcceptors() {
		acceptors = ItemPipeHelper.searchNetworkForAcceptors(world, pos);
	}

	private boolean findSource() {
		BlockPos[] neighbors = ItemPipeHelper.getNeighborLocations(pos);
		HashMap<IInventory, BlockPos> invs = ItemPipeHelper.getMapInventoriesAtLocs(world, neighbors);
		if(invs.size() > 0) {
			source = invs.keySet().toArray(new IInventory[0])[0];
			sourceLoc = invs.values().toArray(new BlockPos[0])[0];
			return true;
		}
		return false;
	}

	
	
	public void setShouldSearch(boolean b) {
		shouldSearch  = b;
	}
	
	public EnumFacing getFacing(BlockPos pos1, BlockPos pos2) {
		BlockPos dif = pos1.subtract(pos2);
		return EnumFacing.getFacingFromVector(dif.getX(), dif.getY(), dif.getZ());
	}
	
}
