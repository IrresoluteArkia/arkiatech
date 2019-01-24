package com.irar.arkiatech.gui.container;

import com.irar.arkiatech.gui.container.slot.SlotDriftInput;
import com.irar.arkiatech.gui.container.slot.SlotGrinderOutput;
import com.irar.arkiatech.tileentity.TileGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerGenerator extends ContainerBase<TileGenerator> {

	public ContainerGenerator(InventoryPlayer playerInventory, TileGenerator te) {
		super(playerInventory, te);
	    this.addSlotToContainer(new SlotFurnaceFuel(te, 0, 62 + 18, 17 + 36));
	}

}
