package com.irar.arkiatech.gui.container;

import com.irar.arkiatech.gui.container.slot.SlotDriftInput;
import com.irar.arkiatech.gui.container.slot.SlotGrinderOutput;
import com.irar.arkiatech.tileentity.TileGenerator;
import com.irar.arkiatech.tileentity.TileGrinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerGrinder extends ContainerBase<TileGrinder>{

	public ContainerGrinder(InventoryPlayer playerInventory, TileGrinder te) {
		super(playerInventory, te);
		this.addSlotToContainer(new Slot(te, 0, 62 - 8, 17));
        this.addSlotToContainer(new SlotGrinderOutput(te, 2, 62 + 36 + 8, 17));
        this.addSlotToContainer(new SlotDriftInput(te, 1, 62 + 18, 17 + 36));
	}

}
