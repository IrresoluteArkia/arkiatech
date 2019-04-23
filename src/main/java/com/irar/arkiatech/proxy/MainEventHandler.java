package com.irar.arkiatech.proxy;

import com.irar.arkiatech.block.ATEnergyPipe;
import com.irar.arkiatech.block.ATLeaf;
import com.irar.arkiatech.block.ATSapling;
import com.irar.arkiatech.block.ICustomBoundingBox;
import com.irar.arkiatech.item.ATDrill;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MainEventHandler {
	private static boolean drawing = false;

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
		if(!drawing) {
			drawing = true;
			if(e.getPlayer().getHeldItemMainhand().getItem() instanceof ATDrill) {
				int radius = ATDrill.getDrillRadius(e.getPlayer().getHeldItemMainhand());
				int size = radius*2-1;
				for(int i = radius-size; i <= size-radius; i++) {
					for(int j = radius-size; j <= size-radius; j++) {
						BlockPos old = e.getTarget().getBlockPos();
						BlockPos pos;
						if(e.getTarget().sideHit.getAxis().equals(Axis.X)) {
							pos = old.add(new BlockPos(0, i, j));
						}else if(e.getTarget().sideHit.getAxis().equals(Axis.Y)) {
							pos = old.add(new BlockPos(i, 0, j));
						}else {
							pos = old.add(new BlockPos(i, j, 0));
						}
						if(i == 0 && j == 0) {
							continue;
						}
						if(!ATDrill.canTargetSpot(e.getPlayer(), e.getPlayer().getHeldItemMainhand(), e.getPlayer().world, pos)) {
							continue;
						}
						RayTraceResult r2 = new RayTraceResult(RayTraceResult.Type.BLOCK, e.getTarget().hitVec, e.getTarget().sideHit, pos);
			            if (!net.minecraftforge.client.ForgeHooksClient.onDrawBlockHighlight(e.getContext(), e.getPlayer(), r2, 0, e.getPartialTicks()))
			                e.getContext().drawSelectionBox(e.getPlayer(), r2, 0, e.getPartialTicks());
					}
				}
			}
			drawing = false;
		}
	}
	
}
