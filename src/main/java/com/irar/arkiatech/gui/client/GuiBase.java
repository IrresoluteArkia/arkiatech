package com.irar.arkiatech.gui.client;

import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.Ref;
import com.irar.arkiatech.gui.container.ContainerBase;
import com.irar.arkiatech.gui.container.ContainerGrinder;
import com.irar.arkiatech.tileentity.TileGrinder;
import com.irar.arkiatech.tileentity.TileMachineBase;
import com.irar.arkiatech.tileentity.ATTEGui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiBase<T extends TileMachineBase> extends GuiContainer{

	protected InventoryPlayer playerInv;
	protected T te;
	protected int xSize;
	protected int ySize;
	protected int textColor = 0;
	private List<int[]> energyDisplayAreas = new ArrayList<>();
	protected ResourceLocation progressLoc = new ResourceLocation(Ref.MODID + ":textures/gui/container/progress_bar_1.png");
	protected ResourceLocation progressLoc2 = new ResourceLocation(Ref.MODID + ":textures/gui/container/progress_bar_2.png");
	protected ResourceLocation slotLoc = new ResourceLocation(Ref.MODID + ":textures/gui/container/slot.png");
	protected ResourceLocation energyBarLoc = new ResourceLocation(Ref.MODID + ":textures/gui/container/energy_bar.png");
	protected ResourceLocation base1 = new ResourceLocation(Ref.MODID + ":textures/gui/container/gui_base_1.png");
	protected ResourceLocation base2 = new ResourceLocation(Ref.MODID + ":textures/gui/container/gui_base_2.png");
	protected ResourceLocation upgradeGui = new ResourceLocation(Ref.MODID + ":textures/gui/container/upgrades.png");
	protected ResourceLocation currentBase = base1;

	public GuiBase(InventoryPlayer inventory, T tileEntity, ATTEGui<T, ?, ?> tileATTE) {
		super(instantiateContainer(tileATTE.containerClass, inventory, tileEntity, tileATTE.tileClass));
		this.playerInv = inventory;
	    this.te = tileEntity;

	    this.xSize = 176;
	    this.ySize = 166;
	}
	
	private static ContainerBase instantiateContainer(Class<? extends ContainerBase> containerClass, InventoryPlayer playerInv2, TileMachineBase te2, Class<?> tileClass) {
		try {
			return containerClass.getDeclaredConstructor(InventoryPlayer.class, tileClass).newInstance(playerInv2, te2);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
    }

	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(currentBase);
        guiTop = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if(this.te.upgrades.getSizeInventory() > 0) {
        	renderUpgrades();
        }
        this.mc.getTextureManager().bindTexture(slotLoc);
        for(Slot s : this.inventorySlots.inventorySlots) {
        	this.drawTexturedModalRect(s.xPos + this.guiLeft - 1, s.yPos + this.guiTop - 1, 0, 0, 18, 18);
        }
        this.mc.getTextureManager().bindTexture(energyBarLoc);
        for(int[] energyDisplayArea : this.energyDisplayAreas) {
        	int x = energyDisplayArea[0];
        	int y = energyDisplayArea[1];
        	int height = energyDisplayArea[2];
        	int width = 10;
        	int borderSize = 1;
        	
        	int guiSheetX1 = 0;
        	int guiSheetX2 = 10;
            this.drawTexturedModalRect(this.guiLeft + x, this.guiTop + y, guiSheetX1, 0, width, height);
        	int energyLevel = this.getEnergyAmountScaled(height - borderSize * 2);
            this.drawTexturedModalRect(this.guiLeft + x + borderSize, this.guiTop + y + borderSize + (height - borderSize * 2 - energyLevel), guiSheetX2, 0, width - borderSize * 2, energyLevel);
        }
    }
	
	private int getEnergyAmountScaled(int pixels)
    {
        int i = getEnergy();
        int j = getMaxEnergy();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        for(int[] energyDisplayArea : this.energyDisplayAreas) {
        	int x = energyDisplayArea[0];
        	int y = energyDisplayArea[1];
        	int width = 10;
        	int height = energyDisplayArea[2];
        	if(isMouseIn(mouseX, mouseY, x, y, width, height)) {
        		renderEnergyToolTip(mouseX, mouseY);
        	}
        }
    }

    private void renderUpgrades() {
        this.mc.getTextureManager().bindTexture(upgradeGui);
        int[] upgradeLoc = ((ContainerBase) this.inventorySlots).getUpgradeLocation();
        int x = upgradeLoc[0];
        int y = upgradeLoc[1];
        this.drawTexturedModalRect(this.guiLeft + x, this.guiTop + y, 0, 0, 26, 80);
	}

	private void renderEnergyToolTip(int mouseX, int mouseY) {
    	NumberFormat format = NumberFormat.getInstance();
    	FontRenderer font = fontRenderer;
    	List<String> text = new ArrayList<>();
    	text.add(format.format(this.getEnergy()) + "/" + format.format(this.getMaxEnergy()) + " RF");
    	if(te.getEnergyUsedPerTick() < 0) {
    		text.add("Produces " + format.format(-te.getEnergyUsedPerTick()) + " RF/t");
    	}else {
    		text.add(getEnergyUsedText());
    	}
        this.drawHoveringText(text, mouseX, mouseY, (font == null ? fontRenderer : font));
	}

	protected String getEnergyUsedText() {
    	NumberFormat format = NumberFormat.getInstance();
		return "Uses " + format.format(te.getEnergyUsedPerTick()) + " RF/t";
	}

	protected boolean isMouseIn(int mouseX, int mouseY, int x, int y, int width, int height) {
		x += this.guiLeft;
		y += this.guiTop;
		return mouseX >= x && mouseX <= x + width - 1 && mouseY >= y && mouseY <= y + height - 1;
	}

	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(this.te.getName(), 8, 4, textColor );
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, textColor);
    }
    
    protected void addEnergyDisplayArea(int x, int y, int height) {
    	this.energyDisplayAreas.add(new int[] {x, y, height});
    }
    
    protected int getEnergy() {
    	return te.getEnergyStored();
    }
    
    protected int getMaxEnergy() {
    	return te.getMaxEnergyStored();
    }

}
