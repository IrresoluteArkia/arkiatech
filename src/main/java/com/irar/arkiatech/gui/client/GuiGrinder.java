package com.irar.arkiatech.gui.client;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.Ref;
import com.irar.arkiatech.gui.container.ContainerGrinder;
import com.irar.arkiatech.tileentity.TileGrinder;
import com.irar.arkiatech.tileentity.ATTEGui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiGrinder extends GuiBase<TileGrinder>{

	private ResourceLocation driftLevelLoc = new ResourceLocation(Ref.MODID + ":textures/gui/container/drift_gauge.png");

	public GuiGrinder(InventoryPlayer inventory, TileGrinder tileEntity) {
		super(inventory, tileEntity, ATTEGui.GRINDER);
	    this.addEnergyDisplayArea(152, 16, 50);
	}

	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		drawGrindProgress(this.guiLeft + 77, this.guiTop + 17);
		drawDriftLevel(this.guiLeft + 79, this.guiTop + 34);
    }
	
	private void drawDriftLevel(int i, int j) {
		this.mc.getTextureManager().bindTexture(driftLevelLoc);
        this.drawTexturedModalRect(i, j, 0, 0, 18, 18);
        int driftLevel = this.getDriftAmountScaled(15);
        if(driftLevel > 0) {
            this.drawTexturedModalRect(i + 1, j + 16 - driftLevel, 18, 0, 16, driftLevel + 1);
        }
	}

	private void drawGrindProgress(int i, int j) {
		this.mc.getTextureManager().bindTexture(progressLoc);
        this.drawTexturedModalRect(i, j, 0, 0, 24, 16);
        int grindWidth = this.getGrindProgressScaled(24);
        this.drawTexturedModalRect(i, j, 0, 16, grindWidth + 1, 16);
	}

	private int getGrindProgressScaled(int pixels)
    {
        int i = this.te.getCurrentGrindTime();
        int j = this.te.getProcessingTime(0);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
    
	private int getDriftAmountScaled(int pixels)
    {
        int i = this.te.getCurrentDriftLevel();
        int j = this.te.getMaxDrift();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
	
	private void renderDriftAmountToolTip(int mouseX, int mouseY) {
    	FontRenderer font = fontRenderer;
    	List<String> text = new ArrayList<>();
    	text.add("Drift: " + te.getCurrentDriftLevel());
        this.drawHoveringText(text, mouseX, mouseY, (font == null ? fontRenderer : font));
	}
	
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    	if(isMouseIn(mouseX, mouseY, 79, 34, 18, 18)) {
    		renderDriftAmountToolTip(mouseX, mouseY);
    	}
    }

}
