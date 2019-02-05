package com.irar.arkiatech.worldgen;

import java.util.Map;
import java.util.Random;

import com.irar.arkiatech.Ref;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class ZephyrianTreeStructure extends WorldGenerator{

	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		WorldServer worldserver = (WorldServer) world;
		MinecraftServer minecraftserver = world.getMinecraftServer();
		TemplateManager templatemanager = worldserver.getStructureTemplateManager();
		Template template = templatemanager.getTemplate(minecraftserver, new ResourceLocation(Ref.MODID+":zephyrian_tree"));
		
		if(template == null)
		{
			System.out.println("NO STRUCTURE");
			return false;
		}
		
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE/*values()[rand.nextInt(Mirror.values().length)]*/)
				.setRotation(Rotation.NONE/*values()[rand.nextInt(Rotation.values().length)]*/).setIgnoreEntities(false).setChunk((ChunkPos) null)
				.setReplacedBlock((Block) null).setIgnoreStructureBlock(false);
		
		if(WorldGenZephyrianTree.canSpawnHere(template, worldserver, position, new BlockPos(-6, 0, -5), placementsettings, false)) {
			IBlockState iblockstate = world.getBlockState(position);
			world.notifyBlockUpdate(position, iblockstate, iblockstate, 3);
			
			template.addBlocksToWorld(world, position.add(-6, 0, -5), new ZephyrianTreeManager(), placementsettings, 2);
			return true;
		}
		
		return false;
	}
	
	public boolean generateSap(World world, Random rand, BlockPos position) {
		WorldServer worldserver = (WorldServer) world;
		MinecraftServer minecraftserver = world.getMinecraftServer();
		TemplateManager templatemanager = worldserver.getStructureTemplateManager();
		Template template = templatemanager.getTemplate(minecraftserver, new ResourceLocation(Ref.MODID+":zephyrian_tree"));
		
		if(template == null)
		{
			System.out.println("NO STRUCTURE");
			return false;
		}
		
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE/*values()[rand.nextInt(Mirror.values().length)]*/)
				.setRotation(Rotation.NONE/*values()[rand.nextInt(Rotation.values().length)]*/).setIgnoreEntities(false).setChunk((ChunkPos) null)
				.setReplacedBlock((Block) null).setIgnoreStructureBlock(false);

		if(WorldGenZephyrianTree.canSpawnHere(template, worldserver, position, new BlockPos(-6, 0, -5), placementsettings, true)) {
			IBlockState iblockstate = world.getBlockState(position);
			world.notifyBlockUpdate(position, iblockstate, iblockstate, 3);
			
			template.addBlocksToWorld(world, position.add(-6, 0, -5), new ZephyrianTreeManager(), placementsettings, 2);
			return true;
		}
		
		return false;
	}
	
}
