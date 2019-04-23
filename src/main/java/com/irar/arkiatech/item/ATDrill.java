package com.irar.arkiatech.item;

import com.irar.arkiatech.tileentity.ATEnergyStorage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing.Axis;
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
	
//	private boolean destroying = false;
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
		ATEnergyStorage storage = (ATEnergyStorage) this.getEnergyStorage(stack);
		if(storage != null) {
			storage.useEnergy(this.getEnergyUsePerBlock(stack));
		}
//		if(!destroying) {
//			destroying = true;
//			if(entityLiving instanceof EntityPlayerMP) {
//				EntityPlayerMP player = (EntityPlayerMP) entityLiving;
//				int radius = this.getDrillRadius(stack);
//				int size = radius*2-1;
//				for(int i = radius-size; i <= size-radius; i++) {
//					for(int j = radius-size; j <= size-radius; j++) {
//						BlockPos newPos;
////						if(e.getTarget().sideHit.getAxis().equals(Axis.X)) {
//							newPos = pos.add(new BlockPos(0, i, j));
////						}else if(e.getTarget().sideHit.getAxis().equals(Axis.Y)) {
////							newPos = pos.add(new BlockPos(i, 0, j));
////						}else {
////							newPos = pos.add(new BlockPos(i, j, 0));
////						}
//						if(i == 0 && j == 0) {
//							continue;
//						}
//						if(!ATDrill.canTargetSpot(player, stack, worldIn, newPos)) {
//							continue;
//						}
//						player.interactionManager.tryHarvestBlock(newPos);
//					}
//				}
//			}
//			destroying = false;
//		}
        return true;
    }

	public static int getDrillRadius(ItemStack stack) {
		//TODO
		return 2;
	}

	public static boolean canTargetSpot(EntityPlayer player, ItemStack stack, World world, BlockPos pos) {
		return stack.getStrVsBlock(world.getBlockState(pos)) == efficiencyOnProperMaterial;
	}

}
