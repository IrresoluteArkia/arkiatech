package com.irar.arkiatech.block;

import com.irar.arkiatech.ArkiaTech;
import com.irar.arkiatech.handlers.ATCreativeTabs;
import com.irar.arkiatech.tileentity.TileGrinder;
import com.irar.arkiatech.tileentity.ATTEGui;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ATGrinder extends ATMachineBlockBase<TileGrinder>{

	public ATGrinder(Material materialIn, String registryName) {
		super(materialIn, registryName, ATTEGui.GRINDER);
	}
	
}
