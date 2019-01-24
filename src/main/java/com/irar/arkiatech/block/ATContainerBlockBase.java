package com.irar.arkiatech.block;

import java.lang.reflect.InvocationTargetException;

import com.irar.arkiatech.ArkiaTech;
import com.irar.arkiatech.gui.container.ContainerBase;
import com.irar.arkiatech.handlers.ATCreativeTabs;
import com.irar.arkiatech.tileentity.TileGenerator;
import com.irar.arkiatech.tileentity.TileMachineBase;
import com.irar.arkiatech.tileentity.ATTE;
import com.irar.arkiatech.tileentity.ATTEGui;
import com.irar.arkiatech.tileentity.TileBase;
import com.irar.arkiatech.tileentity.TileEnergyBase;

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

public class ATContainerBlockBase<T extends TileBase> extends BlockContainer{

	protected Class<T> tileClass;
	protected ATTE<T> tileEnum;
	
	public ATContainerBlockBase(Material materialIn, String registryName, ATTE<T> tileEnum) {
		super(materialIn);
		this.setUnlocalizedName(registryName);
		this.setRegistryName(registryName);
		this.setCreativeTab(ATCreativeTabs.ARKIATECHMAIN);
		this.tileClass = tileEnum.tileClass;
		this.tileEnum = tileEnum;
	}
	
	private T instantiateTileEntity(Class<T> tileClass) {
		try {
			return tileClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return this.instantiateTileEntity(tileClass);
	}
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state) {
		return this.instantiateTileEntity(tileClass);
	}
	
	@Override
	public boolean hasTileEntity(){
		return true;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
	    return EnumBlockRenderType.MODEL;
	}
	
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
		if(this instanceof ATMachineBlockBase) {
			NBTTagCompound compound = stack.hasTagCompound() ? stack.getTagCompound() : stack.serializeNBT();
			
			if(compound.hasKey("MACHINE_TILE_ENTITY_DATA")) {
				NBTTagCompound machineData = (NBTTagCompound) compound.getTag("MACHINE_TILE_ENTITY_DATA");
				TileEntity te = worldIn.getTileEntity(pos);
				te.deserializeNBT(machineData);
			}
		}
	}
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack){
		if(this instanceof ATMachineBlockBase) {
			ItemStack dropped = new ItemStack(this);
			if(te != null) {
				NBTTagCompound machineData = te.serializeNBT();
				NBTTagCompound compound = dropped.hasTagCompound() ? dropped.getTagCompound() : dropped.serializeNBT();
				compound.setTag("MACHINE_TILE_ENTITY_DATA", machineData);
				dropped.setTagCompound(compound);
			}
			spawnAsEntity(worldIn, pos, dropped);
		}else {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}
	
}
