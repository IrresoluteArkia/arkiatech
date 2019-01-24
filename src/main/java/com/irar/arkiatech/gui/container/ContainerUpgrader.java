package com.irar.arkiatech.gui.container;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.gui.container.slot.SlotGrinderOutput;
import com.irar.arkiatech.gui.container.slot.SlotUpgraderCrafting;
import com.irar.arkiatech.tileentity.TileMachineBase;
import com.irar.arkiatech.tileentity.TileUpgrader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerUpgrader extends ContainerBase<TileUpgrader>{

	List<SlotUpgraderCrafting> craftingSlots = new ArrayList<>();
	
	public ContainerUpgrader(InventoryPlayer playerInventory, TileUpgrader te) {
		super(playerInventory, te);
		craftingSlots.add((SlotUpgraderCrafting) this.addSlotToContainer(new SlotUpgraderCrafting(te.crInv, 0, 61, 14)));
		craftingSlots.add((SlotUpgraderCrafting) this.addSlotToContainer(new SlotUpgraderCrafting(te.crInv, 1, 25, 50)));
		craftingSlots.add((SlotUpgraderCrafting) this.addSlotToContainer(new SlotUpgraderCrafting(te.crInv, 2, 61, 50)));
		craftingSlots.add((SlotUpgraderCrafting) this.addSlotToContainer(new SlotUpgraderCrafting(te.crInv, 3, 97, 50)));
		craftingSlots.add((SlotUpgraderCrafting) this.addSlotToContainer(new SlotUpgraderCrafting(te.crInv, 4, 61, 86)));
		this.addSlotToContainer(new SlotGrinderOutput(te, 0, 142, 50));
		for(int i = 0; i < 10; i++) {
			this.addSlotToContainer(new Slot(te, i + 1, 7 + 18 * i, 114));
		}
	}
	
	@Override
	public int[] getUpgradeLocation() {
		return new int[] {199, 0};
	}
	
	@Override
	protected int[] getPlayerInvLocation() {
		return new int[] {16, 150};
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer player) {
		if(slotId >= 0 && slotId < this.inventorySlots.size()) {
			Slot slot = this.getSlot(slotId);
			if(slot instanceof SlotUpgraderCrafting) {
				SlotUpgraderCrafting suc = (SlotUpgraderCrafting) slot;
				ItemStack held = player.inventory.getItemStack();
				if(held == ItemStack.EMPTY) {
					slot.putStack(ItemStack.EMPTY);
				}else {
					ItemStack heldCopy = held.copy();
					heldCopy.setCount(1);
					slot.putStack(heldCopy);
				}
				return held;
			}
		}
		return super.slotClick(slotId, dragType, clickType, player);
	}

	public void setCraftingStack(int i, ItemStack displayedIngredient) {
		craftingSlots.get(i).putStack(displayedIngredient.copy());
		te.markDirty();
	}

	public TileMachineBase getTile() {
		return te;
	}
	
}
