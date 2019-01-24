package com.irar.arkiatech.item;

import com.irar.arkiatech.tileentity.ATEnergyStorage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ATDrill extends ATEnergyItem {

	public static final int baseHarvestLevel = 4;
	public static final int baseEnergyUse = 99;
	private static final float efficiencyOnProperMaterial = 9.0F;
	
	public ATDrill(String registryName) {
		super(registryName, 512000);
		this.setHarvestLevel("shovel", baseHarvestLevel);
		this.setHarvestLevel("pickaxe", baseHarvestLevel);
	}
	
	@Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }
	
	public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
		int energy = this.getEnergy(stack);
		if(energy < this.getEnergyUsePerBlock(stack)) {
			return 0.1F;
		}
        for (String type : getToolClasses(stack))
        {
            if (state.getBlock().isToolEffective(type, state))
                return efficiencyOnProperMaterial;
        }
        return 1.0F;
    }
	
	@Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack){
        Block block = state.getBlock();
        return this.getEnergy(stack) >= this.getEnergyUsePerBlock(stack) || state.getMaterial().isToolNotRequired() || (block == Blocks.SNOW_LAYER || block == Blocks.SNOW);
    }

	private int getEnergyUsePerBlock(ItemStack stack) {
		return baseEnergyUse;
	}
	
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
		ATEnergyStorage storage = (ATEnergyStorage) this.getEnergyStorage(stack);
		if(storage != null) {
			storage.useEnergy(this.getEnergyUsePerBlock(stack));
		}
        return true;
    }

}
