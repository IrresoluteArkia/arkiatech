package com.irar.arkiatech.gui.container;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.gui.container.ContainerPFurnace.SmeltArrow;
import com.irar.arkiatech.gui.container.slot.SlotGrinderOutput;
import com.irar.arkiatech.tileentity.TilePFurnace;
import com.irar.arkiatech.tileentity.TilePFurnaceSingle;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerPFurnace<T extends TilePFurnace> extends ContainerBase<T> {

	private int numFurnaces;
	public List<SmeltArrow> smeltArrows = new ArrayList<>();

	public ContainerPFurnace(InventoryPlayer playerInventory, T te) {
		super(playerInventory, te);
		this.numFurnaces = te.getNumFurnaces();
		int width = 176;
		int slotWidth = 18;
		int funcWidth = width - numFurnaces * slotWidth;
		int padding = funcWidth / (numFurnaces + 1);
		for(int i = 0; i < numFurnaces; i++) {
			int iPadding = (i + 1) * padding + (i * slotWidth);
			this.addSlotToContainer(new Slot(te, i, iPadding, 17));
		}
		for(int i = 0; i < numFurnaces; i++) {
			int iPadding = (i + 1) * padding + (i * slotWidth);
			new SmeltArrow(iPadding, 17 + 1 + 18, i);
		}
		for(int i = 0; i < numFurnaces; i++) {
			int iPadding = (i + 1) * padding + (i * slotWidth);
			this.addSlotToContainer(new SlotGrinderOutput(te, i + numFurnaces, iPadding, 17 + 2 + 24 + 18));
		}
	}

	public class SmeltArrow {
		
		private int furnaceNum;
		public final int x, y;

		public SmeltArrow(int x, int y, int correspondingFurnaceNum) {
			ContainerPFurnace.this.smeltArrows.add(this);
			this.x = x;
			this.y = y;
			this.furnaceNum = correspondingFurnaceNum;
		}
		
		public int getProgress() {
			return te.getSmeltProgress(furnaceNum);
		}
		
	}

}
