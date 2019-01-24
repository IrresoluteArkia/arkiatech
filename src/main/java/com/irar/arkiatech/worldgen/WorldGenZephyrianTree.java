package com.irar.arkiatech.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenZephyrianTree extends WorldGenerator implements IWorldGenerator
{
    public BlockPos chunkPos;
    public WorldGenZephyrianTree()
    {
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
       /* for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.hasNoSky() || blockpos.getY() < worldIn.getHeight() - 1) && this.block.canBlockStay(worldIn, blockpos, this.block.getDefaultState()))
            {
                worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
            }
        }*/
    	WorldGenerator structure = new ZephyrianTreeStructure();
		structure.generate(worldIn, rand, position);
        return true;
    }

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
	    WorldGenZephyrianTree gen = new WorldGenZephyrianTree();
	    chunkPos= new BlockPos(chunkX*16, 64, chunkZ*16);
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(world, random, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE)){
	
	        for (int l2 = 0; l2 < 5; ++l2)
	        {
	            int i7 = random.nextInt(16) + 16;
	            int l10 = random.nextInt(16) + 16;
	            int j14 = world.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;
	
	            if (j14 > 0)
	            {
	                int k17 = random.nextInt(j14);
	                BlockPos blockpos1 = this.chunkPos.add(i7, k17, l10);
                    gen.generate(world, random, blockpos1);
	            }
	        }
		}
	}

	public static boolean canSpawnHere(Template template, WorldServer worldserver, BlockPos position) {
		IBlockState state = worldserver.getBlockState(position.add(0, -1, 0));
		Block block = state.getBlock();
		boolean condition = block.equals(Blocks.GRASS);
		return condition;
	}
}