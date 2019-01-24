package com.irar.arkiatech.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.irar.arkiatech.ArkiaTech;
import com.irar.arkiatech.handlers.ATBlocks;
import com.irar.arkiatech.handlers.ATCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ATLeaf extends BlockLeaves implements IFuelBlock {

	protected ItemStack extraItem = ItemStack.EMPTY;
	protected int extraChance = 1;
	
	public ATLeaf(String registryName) {
		super();
		this.setUnlocalizedName(registryName);
		this.setRegistryName(registryName);
		this.setCreativeTab(ATCreativeTabs.ARKIATECHMAIN);
		ArkiaTech.proxy.setGraphicsLevel(this, true);
		setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
	}
	
	public ATLeaf setExtraItemDropped(ItemStack stack) {
		extraItem = stack;
		return this;
	}
	
	public ATLeaf setExtraItemDropChance(int chance) {
		extraChance = chance;
		return this;
	}
	
	public ItemStack getExtraItemDropped() {
		return extraItem;
	}
	
	public int getExtraItemDropChance() {
		return extraChance;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this));
	}

	@Override
	public EnumType getWoodType(int meta) {
		return null;
	}
	
	protected BlockStateContainer createBlockState()
	{
	    return new BlockStateContainer(this, new IProperty[] {DECAYABLE, CHECK_DECAY});
	}
	/**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!((Boolean)state.getValue(DECAYABLE)).booleanValue())
        {
            i |= 4;
        }

        if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }
    
    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    } 
    
    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            player.addStat(StatList.getBlockStats(this));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }
    
    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this));
    }
    
    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this));
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ATBlocks.zeSapling);
    }
    
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
    	super.getDrops(drops, world, pos, state, fortune);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if(rand.nextInt(getExtraItemDropChance()) == 0) {
        	drops.add(getExtraItemDropped().copy());
        }
    }

	@Override
	public int getBurnTime(ItemStack stack) {
		return 200;
	}

}
