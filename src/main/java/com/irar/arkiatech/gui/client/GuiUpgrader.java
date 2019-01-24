package com.irar.arkiatech.gui.client;

import com.irar.arkiatech.gui.container.ContainerBase;
import com.irar.arkiatech.tileentity.TileUpgrader;
import com.irar.arkiatech.tileentity.ATTEGui;

import net.minecraft.entity.player.InventoryPlayer;

public class GuiUpgrader extends GuiBase<TileUpgrader>{

	public GuiUpgrader(InventoryPlayer inventory, TileUpgrader tileEntity) {
		super(inventory, tileEntity, ATTEGui.UPGRADER);
		this.currentBase = this.base2;
		this.xSize = 194;
		this.ySize = 233;
	    this.addEnergyDisplayArea(176, 32, 50);
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		drawProcessProgress(this.guiLeft + 117, this.guiTop + 51);
    }

	private void drawProcessProgress(int i, int j) {
		this.mc.getTextureManager().bindTexture(progressLoc);
        this.drawTexturedModalRect(i, j, 0, 0, 24, 16);
        int grindWidth = this.getProcessProgressScaled(24);
        this.drawTexturedModalRect(i, j, 0, 16, grindWidth + 1, 16);
	}

	private int getProcessProgressScaled(int pixels) {
		int i = this.te.getCurrentProcessTime();
        int j = this.te.getProcessingTime(0);
        return j != 0 && i != 0 ? i * pixels / j : 0;
	}

}
