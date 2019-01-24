package com.irar.arkiatech.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.util.ItemPipeHelper;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileItemSource extends TileBase{

	public IInventory source = null;
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
			if(!stack.isEmpty()) {
				for(IItemAcceptor acceptor : acceptors) {
					if(acceptor.canAccept(stack)) {
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
		IInventory[] invs = ItemPipeHelper.getInventoriesAtLocs(world, neighbors);
		if(invs.length > 0) {
			source = invs[0];
			return true;
		}
		return false;
	}

	
	
	public void setShouldSearch(boolean b) {
		shouldSearch  = b;
	}
	
}
