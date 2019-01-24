package com.irar.arkiatech.block;

import java.lang.reflect.InvocationTargetException;

import com.irar.arkiatech.ArkiaTech;
import com.irar.arkiatech.gui.container.ContainerBase;
import com.irar.arkiatech.handlers.ATCreativeTabs;
import com.irar.arkiatech.tileentity.TileGenerator;
import com.irar.arkiatech.tileentity.TileMachineBase;
import com.irar.arkiatech.tileentity.ATTEGui;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ATMachineBlockBase<T extends TileMachineBase> extends ATContainerBlockBase<T>{

	public ATMachineBlockBase(Material materialIn, String registryName, ATTEGui<T, ?, ?> tileEnum) {
		super(materialIn, registryName, tileEnum);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			playerIn.openGui(ArkiaTech.instance, tileEnum.uid, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	
}
