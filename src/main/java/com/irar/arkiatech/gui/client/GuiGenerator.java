package com.irar.arkiatech.gui.client;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.Ref;
import com.irar.arkiatech.gui.container.ContainerBase;
import com.irar.arkiatech.gui.container.ContainerGenerator;
import com.irar.arkiatech.gui.container.ContainerGrinder;
import com.irar.arkiatech.tileentity.TileGenerator;
import com.irar.arkiatech.tileentity.TileGrinder;
import com.irar.arkiatech.tileentity.ATTEGui;
import com.irar.arkiatech.util.StringHelper;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class GuiGenerator extends GuiBase<TileGenerator>{

	private ResourceLocation burnLoc = new ResourceLocation(Ref.MODID + ":textures/gui/container/fuel_burn.png");

	public GuiGenerator(InventoryPlayer inventory, TileGenerator tileEntity) {
		super(inventory, tileEntity, ATTEGui.GENERATOR);
	    this.addEnergyDisplayArea(152, 16, 50);
	}
	
	@Override
	public List<String> getItemToolTip(ItemStack stack){
		List<String> toolTip = super.getItemToolTip(stack);
		if(TileEntityFurnace.isItemFuel(stack)) {
			int burnTime = TileEntityFurnace.getItemBurnTime(stack);
			int energyProduce = te.getEnergyProducedPerTick();
			toolTip.add(TextFormatting.GRAY + "Will generate " + TextFormatting.WHITE + TextFormatting.BOLD + (burnTime * energyProduce) + " RF" + TextFormatting.RESET + TextFormatting.GRAY + " at " + energyProduce + " RF/t" + TextFormatting.WHITE);
		}
		return toolTip;
	}

	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		drawBurn(this.guiLeft + 82, this.guiTop + 36);
    }
	
	private void drawBurn(int i, int j) {
		this.mc.getTextureManager().bindTexture(burnLoc);
        this.drawTexturedModalRect(i, j, 0, 0, 14, 14);
        int burnTime = this.getBurnTimeScaled(15);
        if(burnTime > 0) {
            this.drawTexturedModalRect(i - 1, j + 14 - burnTime - 1, 0, 14 + 14 - burnTime, 14, burnTime);
        }
	}

	private int getBurnTimeScaled(int pixels)
    {
        int i = this.te.getField(0);
        int j = this.te.getField(1);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}
