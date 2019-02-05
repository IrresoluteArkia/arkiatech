package com.irar.arkiatech.worldgen;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class ZephyrianTreeManager implements ITemplateProcessor {

	@Override
	public BlockInfo processBlock(World worldIn, BlockPos pos, BlockInfo blockInfoIn) {
		IBlockState state = worldIn.getBlockState(pos);
		return canReplace(blockInfoIn.blockState, state, worldIn, pos) ? blockInfoIn : null;
	}

	private boolean canReplace(IBlockState blockState, IBlockState state, World world, BlockPos pos) {
		return blockState.getBlock().isAir(blockState, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock().isReplaceable(world, pos) || state.getBlock().canBeReplacedByLeaves(state, world, pos) || state.getBlock() instanceof BlockBush;
	}

}
