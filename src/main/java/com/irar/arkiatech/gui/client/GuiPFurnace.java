package com.irar.arkiatech.gui.client;

import com.irar.arkiatech.gui.container.ContainerPFurnace;
import com.irar.arkiatech.gui.container.ContainerPFurnace.SmeltArrow;
import com.irar.arkiatech.tileentity.ATTEGui;
import com.irar.arkiatech.tileentity.TilePFurnace;

import net.minecraft.entity.player.InventoryPlayer;

public class GuiPFurnace<T extends TilePFurnace> extends GuiBase<T>{

	public GuiPFurnace(InventoryPlayer inventory, T tileEntity, ATTEGui<T, ?, ?> tileATTE) {
		super(inventory, tileEntity, tileATTE);
	    this.addEnergyDisplayArea(152, 16, 50);
	}

	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(progressLoc2);
		for(Object o : ((ContainerPFurnace) this.inventorySlots).smeltArrows) {
			SmeltArrow sa = (SmeltArrow) o;
			drawArrow(sa);
		}
    }

	private void drawArrow(SmeltArrow sa) {
        this.drawTexturedModalRect(this.guiLeft + sa.x, this.guiTop + sa.y, 0, 0, 16, 24);
        int grindWidth = this.getSmeltProgressScaled(24, sa.getProgress());
        if(grindWidth != 0) {
            this.drawTexturedModalRect(this.guiLeft + sa.x, this.guiTop + sa.y, 16, 0, 16, grindWidth + 1);
        }
	}

	private int getSmeltProgressScaled(int pixels, int i) {
		int j = this.te.getProcessingTime(i);
        return j != 0 && i != 0 ? i * pixels / j : 0;
	}
	
}
