package com.irar.arkiatech.item;

import java.text.NumberFormat;
import java.util.List;

import com.irar.arkiatech.tileentity.ATEnergyStorage;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ATEnergyItem extends ATItemBase{

	protected int maxEnergy;

	public ATEnergyItem(String registryName, int maxEnergy) {
		super(registryName);
		this.maxEnergy = maxEnergy;
		
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new ATItemEnergyProvider(maxEnergy);
	}
	
	public static int getMaxEnergy(ItemStack stack) {
		if(stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if(storage != null) {
				return storage.getMaxEnergyStored();
			}
		}
		return -1;
	}
	
	@Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list){
		if(this.isInCreativeTab(tabs)) {
			ItemStack stackCharged = new ItemStack(this);
			if(stackCharged.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage storage = stackCharged.getCapability(CapabilityEnergy.ENERGY, null);
				if(storage != null) {
					this.setEnergy(stackCharged, storage.getMaxEnergyStored());
					list.add(stackCharged);
				}
			}
			
			ItemStack stackUncharged = new ItemStack(this);
			setEnergy(stackUncharged, 0);
			list.add(stackUncharged);
		}
	}
	
	public static boolean hasEnergy(ItemStack stack) {
		if(stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if(storage != null) {
				return true;
			}
		}
		return false;
	}

	public static void setEnergy(ItemStack stack, int energy) {
		if(stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if(storage instanceof ATEnergyStorage) {
				((ATEnergyStorage) storage).setEnergyLevel(energy);
			}
		}
	}
	
	public static int getEnergy(ItemStack stack) {
		if(stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if(storage != null) {
				return storage.getEnergyStored();
			}
		}
		return 0;
	}
	
	public static IEnergyStorage getEnergyStorage(ItemStack stack) {
		if(stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			return storage;
		}
		return null;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		int energy = getEnergy(stack);
		int max = getMaxEnergy(stack);
		double durabilityPercent = (double) energy / max;
		return 1 - durabilityPercent;
	}
	
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return 16711680;
	}
	
	@Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag){
		int energy = getEnergy(stack);
		int max = getMaxEnergy(stack);
		NumberFormat format = NumberFormat.getInstance();
		tooltip.add(String.format("%s/%s RF", format.format(energy), format.format(max)));
	}

}
