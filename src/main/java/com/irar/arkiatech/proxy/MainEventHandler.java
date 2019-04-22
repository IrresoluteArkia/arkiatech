package com.irar.arkiatech.proxy;

import com.irar.arkiatech.block.ATEnergyPipe;
import com.irar.arkiatech.block.ATLeaf;
import com.irar.arkiatech.block.ATSapling;
import com.irar.arkiatech.block.ICustomBoundingBox;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MainEventHandler {

	@SubscribeEvent
	public void drawSelectionBox(DrawBlockHighlightEvent e) {
		if(e.getTarget().typeOfHit == RayTraceResult.Type.BLOCK)
		{
			BlockPos blockpos = e.getTarget().getBlockPos();
			World world = e.getPlayer().world;
			IBlockState state = world.getBlockState(blockpos);
			if(state.getBlock() instanceof ICustomBoundingBox) {
				state = state.getActualState(world, blockpos);
				ICustomBoundingBox pipe = (ICustomBoundingBox) state.getBlock();
				for(AxisAlignedBB aabb : pipe.getCollisionBoxes(state)) {
					pipe.setBoundingBox(aabb);
					e.getContext().drawSelectionBox(e.getPlayer(), e.getTarget(), 0, e.getPartialTicks());
				}
				e.setCanceled(true);
			}
		}
	}
	
}
