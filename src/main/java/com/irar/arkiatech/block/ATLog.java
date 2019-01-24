package com.irar.arkiatech.block;

import com.irar.arkiatech.handlers.ATCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class ATLog extends BlockLog {

	public ATLog(String registryName) {
		super();
		this.setUnlocalizedName(registryName);
		this.setRegistryName(registryName);
		this.setCreativeTab(ATCreativeTabs.ARKIATECHMAIN);
	}
	 protected BlockStateContainer createBlockState()
	    {
	        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
	    }
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        BlockLog.EnumAxis enumfacing$axis = BlockLog.EnumAxis.Y;
        int i = meta & 12;

        if (i == 4)
        {
            enumfacing$axis = BlockLog.EnumAxis.X;
        }
        else if (i == 8)
        {
            enumfacing$axis = BlockLog.EnumAxis.Z;
        }

        return this.getDefaultState().withProperty(LOG_AXIS, enumfacing$axis);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        BlockLog.EnumAxis enumfacing$axis = (BlockLog.EnumAxis)state.getValue(LOG_AXIS);

        if (enumfacing$axis == BlockLog.EnumAxis.X)
        {
            i |= 4;
        }
        else if (enumfacing$axis == BlockLog.EnumAxis.Z)
        {
            i |= 8;
        }

        return i;
    }
}
