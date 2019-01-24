package com.irar.arkiatech.item;

import java.util.List;

import com.irar.arkiatech.tileentity.ATEnergyStorage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ATMagnet extends ATToggleEnergyItem {

	private int range = 5;
	
	public ATMagnet(String registryName) {
		super(registryName, 512000, true, false);
	}

	@Override
	protected void doEnabledAction(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if(!player.isSneaking()) {
				List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(entity.posX-range, entity.posY-range, entity.posZ-range, entity.posX+range, entity.posY+range, entity.posZ+range));
				if(!items.isEmpty()) {
					for(EntityItem item : items) {
						if(item.getEntityData().getBoolean("PreventRemoteMovement")) continue;
						if(!item.isDead && !item.cannotPickup()) {
							int energyRequired = 33 * item.getItem().getCount();
							if(this.getEnergy(stack) > energyRequired) {
								ItemStack old = item.getItem().copy();
								
								item.onCollideWithPlayer(player);
								
//								if(player.capabilities.isCreativeMode) {
									if(item.isDead || !ItemStack.areItemStacksEqual(item.getItem(), old)) {
										((ATEnergyStorage) this.getEnergyStorage(stack)).useEnergy(energyRequired);
									}
//								}
							}
						}
					}
				}
			}
		}
	}

}
