package com.irar.arkiatech.item;

import java.text.NumberFormat;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public abstract class ATToggleEnergyItem extends ATEnergyItem{

	private boolean initialState;
	private boolean glowWhenEnabled;
	
	public ATToggleEnergyItem(String registryName, int maxEnergy, boolean glowWhenEnabled, boolean initialState) {
		super(registryName, maxEnergy);
		this.initialState = initialState;
		this.glowWhenEnabled = glowWhenEnabled;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		if(glowWhenEnabled) {
			return this.isTurnedOn(stack);
		}
		return super.hasEffect(stack);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.onUpdate(stack, world, entity, slot, selected);
		if (/* !world.isRemote && */isTurnedOn(stack)) {
			this.doEnabledAction(stack, world, entity, slot, selected);
		}
	}

	/**
	 * Called each tick in an inventory if the item is enabled
	 * @param stack
	 * @param world
	 * @param entity
	 * @param slot
	 * @param selected
	 */
	protected abstract void doEnabledAction(ItemStack stack, World world, Entity entity, int slot, boolean selected);

	private boolean isTurnedOn(ItemStack stack) {
		NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : stack.serializeNBT();
		if(tag != null && tag.hasKey("AT_TOGGLE_STATE")) {
			return tag.getBoolean("AT_TOGGLE_STATE");
		}
		return initialState;
	}
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand){
        if(!worldIn.isRemote && player.isSneaking()){
            toggle(player, hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return super.onItemRightClick(worldIn, player, hand);
    }

	private void toggle(EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		setTurnedOn(stack, !isTurnedOn(stack));
	}

	private void setTurnedOn(ItemStack stack, boolean on) {
		NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : stack.serializeNBT();
		if(tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setBoolean("AT_TOGGLE_STATE", on);
		stack.setTagCompound(tag);
	}
	
	@Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag){
		super.addInformation(stack, world, tooltip, flag);
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			boolean on = this.isTurnedOn(stack);
			if(on) {
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Currently Turned " + "" + TextFormatting.BOLD + "On");
			}else {
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Currently Turned " + "" + TextFormatting.BOLD + "Off");
			}
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Shift + Right-Click to Toggle State");
		}else {
			tooltip.add(TextFormatting.DARK_GRAY + "<<Press Shift>>");
		}
	}

}
