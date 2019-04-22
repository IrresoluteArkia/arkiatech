package com.irar.arkiatech.block;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;

public interface ICustomBoundingBox {

	List<AxisAlignedBB> getCollisionBoxes(IBlockState state);

	void setBoundingBox(AxisAlignedBB aabb);

}
