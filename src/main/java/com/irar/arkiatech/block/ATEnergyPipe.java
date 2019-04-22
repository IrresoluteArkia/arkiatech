package com.irar.arkiatech.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.irar.arkiatech.tileentity.ATTE;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ATEnergyPipe extends ATContainerBlockBase implements ICustomBoundingBox{

    /** Whether this pipe connects in the northern direction */
    public static final PropertyBool NORTH = PropertyBool.create("north");
    /** Whether this pipe connects in the eastern direction */
    public static final PropertyBool EAST = PropertyBool.create("east");
    /** Whether this pipe connects in the southern direction */
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    /** Whether this pipe connects in the western direction */
    public static final PropertyBool WEST = PropertyBool.create("west");
    /** Whether this pipe connects in the upwards direction */
    public static final PropertyBool UP = PropertyBool.create("up");
    /** Whether this pipe connects in the downwards direction */
    public static final PropertyBool DOWN = PropertyBool.create("down");
    
    private static final AxisAlignedBB CENTERBB = new AxisAlignedBB(5d/16d, 5d/16d, 5d/16d, 11d/16d, 11d/16d, 11d/16d);
    private static final AxisAlignedBB SOUTHBB = new AxisAlignedBB(5d/16d, 5d/16d, 11d/16d, 11d/16d, 11d/16d, 16d/16d);
    private static final AxisAlignedBB NORTHBB = new AxisAlignedBB(5d/16d, 5d/16d, 0d/16d, 11d/16d, 11d/16d, 5d/16d);
    private static final AxisAlignedBB EASTBB = new AxisAlignedBB(11d/16d, 5d/16d, 5d/16d, 16d/16d, 11d/16d, 11d/16d);
    private static final AxisAlignedBB WESTBB = new AxisAlignedBB(0d/16d, 5d/16d, 5d/16d, 5d/16d, 11d/16d, 11d/16d);
    private static final AxisAlignedBB UPBB = new AxisAlignedBB(5d/16d, 11d/16d, 5d/16d, 11d/16d, 16d/16d, 11d/16d);
    private static final AxisAlignedBB DOWNBB = new AxisAlignedBB(5d/16d, 0d/16d, 5d/16d, 11d/16d, 5d/16d, 11d/16d);
	private AxisAlignedBB boundingBox = new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 1d);
	
	public ATEnergyPipe(Material materialIn, String registryName) {
		super(materialIn, registryName, ATTE.ENERGYPIPE);
	}
	
    @Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        if (!p_185477_7_)
        {
            state = state.getActualState(worldIn, pos);
        }
    	for(AxisAlignedBB aabb : getCollisionBoxes(state)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb);
    	}
	}

	public List<AxisAlignedBB> getCollisionBoxes(IBlockState state) {
		List<AxisAlignedBB> aabbs = new ArrayList<>();
		aabbs.add(CENTERBB);
		if(state.getValue(NORTH).booleanValue()) {
			aabbs.add(NORTHBB);
		}
		if(state.getValue(SOUTH).booleanValue()) {
			aabbs.add(SOUTHBB);
		}
		if(state.getValue(EAST).booleanValue()) {
			aabbs.add(EASTBB);
		}
		if(state.getValue(WEST).booleanValue()) {
			aabbs.add(WESTBB);
		}
		if(state.getValue(UP).booleanValue()) {
			aabbs.add(UPBB);
		}
		if(state.getValue(DOWN).booleanValue()) {
			aabbs.add(DOWNBB);
		}
		return aabbs;
	}
	
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.<RayTraceResult>newArrayList();

        for (AxisAlignedBB axisalignedbb : getCollisionBoxes(this.getActualState(blockState, worldIn, pos)))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;

        for (RayTraceResult raytraceresult : list)
        {
            if (raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);

                if (d0 > d1)
                {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
    }

	public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    public boolean isFullCube(IBlockState state){
        return false;
    }
    
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos){
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return true;
    }
    
    public int getMetaFromState(IBlockState state){
        return 0;
    }
    
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(NORTH, canPipeConnectTo(worldIn, pos, EnumFacing.NORTH))
                    .withProperty(EAST,  canPipeConnectTo(worldIn, pos, EnumFacing.EAST))
                    .withProperty(SOUTH, canPipeConnectTo(worldIn, pos, EnumFacing.SOUTH))
                    .withProperty(WEST,  canPipeConnectTo(worldIn, pos, EnumFacing.WEST))
                    .withProperty(UP,  canPipeConnectTo(worldIn, pos, EnumFacing.UP))
                    .withProperty(DOWN,  canPipeConnectTo(worldIn, pos, EnumFacing.DOWN));
    }
    
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, UP, DOWN});
    }

	private boolean canPipeConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        TileEntity te = world.getTileEntity(other);
		return te != null && te.hasCapability(CapabilityEnergy.ENERGY, invert(facing));
	}

	private EnumFacing invert(EnumFacing facing) {
		switch(facing) {
		case DOWN:
			return EnumFacing.UP;
		case EAST:
			return EnumFacing.WEST;
		case NORTH:
			return EnumFacing.SOUTH;
		case SOUTH:
			return EnumFacing.NORTH;
		case UP:
			return EnumFacing.DOWN;
		case WEST:
			return EnumFacing.EAST;
		default:
			return EnumFacing.UP;
		}
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    	return boundingBox ;
    }
    
    public void setBoundingBox(AxisAlignedBB aabb) {
    	this.boundingBox = aabb;
    }

}
