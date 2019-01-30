package com.irar.arkiatech.block;

import com.irar.arkiatech.handlers.ATCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ATBlockBase extends Block{

	protected boolean renderSeeThrough = false;

	public ATBlockBase(Material materialIn, String registryName) {
		super(materialIn);
		this.setUnlocalizedName(registryName);
		this.setRegistryName(registryName);
		this.setCreativeTab(ATCreativeTabs.ARKIATECHMAIN);
	}

	public boolean isOpaqueCube(IBlockState state)
    {
        return !renderSeeThrough;
    }
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return renderSeeThrough ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }
	
	/**
	 * Set to true to allow parts of this block to be rendered transparent
	 * @param b
	 * @return
	 */
	public ATBlockBase setRenderSeeThrough(boolean b) {
		renderSeeThrough = b;
		return this;
	}
	
}
