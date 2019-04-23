package com.irar.arkiatech.gui.container;

import com.irar.arkiatech.gui.container.slot.SlotDriftInput;
import com.irar.arkiatech.gui.container.slot.SlotGrinderOutput;
import com.irar.arkiatech.gui.container.slot.UpgradeSlot;
import com.irar.arkiatech.item.ATMachineUpgrade;
import com.irar.arkiatech.tileentity.TileGenerator;
import com.irar.arkiatech.tileentity.TileMachineBase;
import com.irar.arkiatech.tileentity.UpgradeInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBase<T extends TileMachineBase> extends Container {

	protected T te;

	public ContainerBase(InventoryPlayer playerInventory, T te) {
		this.te = te;
		addPlayerInv(playerInventory, getPlayerInvLocation());
		addUpgradeInv(te.upgrades, getUpgradeLocation());
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            stack = itemstack1.copy();
            if(stack.getItem() instanceof ATMachineUpgrade) {
            	if(index < 36) {
                	if(!this.mergeItemStack(itemstack1, 36, 40, false)) {
                		return ItemStack.EMPTY;
                	}
				} /*
					 * else if(index >= 36 && index < 40) { if(!this.mergeItemStack(itemstack1, 0,
					 * 36, false)) { return ItemStack.EMPTY; } }
					 */
            }else 
            if(index < 36) {
            	if(!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), false)) {
            		return ItemStack.EMPTY;
            	}
            }else {
            	if(!this.mergeItemStack(itemstack1, 0, 36, false)) {
            		return ItemStack.EMPTY;
            	}
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return stack;
	}

	/**
	 * @return two ints which determine the top left corner of the upgrade inventory
	 */
	public int[] getUpgradeLocation() {
		return new int[] {180, 0};
	}

	/**
	 * @return two ints which determine the top left corner of the player inventory
	 */
	protected int[] getPlayerInvLocation() {
		return new int[] {8, 84};
	}
	
	protected void addUpgradeInv(UpgradeInventory upgrades, int... xy) {
		int x = xy[0] + 5;
		int y = xy[1] + 5;
	    for (int i = 0; i < upgrades.getSizeInventory(); ++i) {
	        this.addSlotToContainer(new UpgradeSlot(upgrades, i, x, y + i * 18));
	    }
	}

	protected void addPlayerInv(InventoryPlayer playerInventory, int... xy) {
		int x = xy[0];
		int y = xy[1];
		addMainInv(playerInventory, x, y);
		addHotBar(playerInventory, x, y + 58);
	}

	protected void addHotBar(InventoryPlayer playerInventory, int i, int j) {
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInventory, x, i + x * 18, j));
	    }
	}

	protected void addMainInv(InventoryPlayer playerInventory, int i, int j) {
		for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, i + x * 18, j + y * 18));
	        }
	    }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        te.setField(id, data);
    }
	
	@Override
	public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.te);
    }

}
