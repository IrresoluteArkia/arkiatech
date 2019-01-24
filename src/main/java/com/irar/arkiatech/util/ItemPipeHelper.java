package com.irar.arkiatech.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.irar.arkiatech.block.ATItemPipe;
import com.irar.arkiatech.block.IItemPipe;
import com.irar.arkiatech.tileentity.IItemAcceptor;
import com.irar.arkiatech.tileentity.TileItemSource;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class ItemPipeHelper {

	public static IInventory[] getInventoriesAtLocs(World world, BlockPos[] neighbors) {
		List<IInventory> inventories = new ArrayList<>();
		for(BlockPos pos : neighbors) {
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof IInventory) {
				inventories.add((IInventory) te);
			}
		}
		return inventories.toArray(new IInventory[0]);
	}

	public static BlockPos[] getNeighborLocations(BlockPos pos) {
		return new BlockPos[] {pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 1, 0), pos.add(0, -1, 0), pos.add(0, 0, 1), pos.add(0, 0, -1)};
	}

	public static List<IItemAcceptor> searchNetworkForAcceptors(World world, BlockPos origin) {
		List<IItemAcceptor> acceptors = new ArrayList<>();
		List<BlockPos> toCheck = new ArrayList<>();
		toCheck.addAll(Arrays.asList(getNeighborLocations(origin)));
		Set<BlockPos> add = new HashSet<>();
		List<BlockPos> checked = new ArrayList<>();
		int i = 0;
		while(toCheck.size() > 0 && i < 1000) {
			i++;
			for(BlockPos pos : toCheck) {
				Block blockAt = world.getBlockState(pos).getBlock();
				if(blockAt instanceof IItemPipe) {
					add.addAll(Arrays.asList(getNeighborLocations(pos)));
					checked.add(pos);
					TileEntity te = world.getTileEntity(pos);
					if(te instanceof IItemAcceptor) {
						acceptors.add((IItemAcceptor) te);
					}
				}
			}
			toCheck.clear();
			for(BlockPos pos : checked) {
				add.remove(pos);
			}
			toCheck.addAll(add);
			add.clear();
			if(i > 500) {
				System.out.println("Long");
			}
		}
		return acceptors;
	}

	public static HashMap<IInventory, BlockPos> getMapInventoriesAtLocs(World world, BlockPos[] neighbors) {
		HashMap<IInventory, BlockPos> inventories = new HashMap<>();
		for(BlockPos pos : neighbors) {
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof IInventory) {
				inventories.put((IInventory) te, pos);
			}
		}
		return inventories;
	}

	public static void updateSources(World world, BlockPos origin) {
		List<BlockPos> toCheck = new ArrayList<>();
		toCheck.addAll(Arrays.asList(getNeighborLocations(origin)));
		Set<BlockPos> add = new HashSet<>();
		List<BlockPos> checked = new ArrayList<>();
		int i = 0;
		while(toCheck.size() > 0 && i < 1000) {
			i++;
			for(BlockPos pos : toCheck) {
				Block blockAt = world.getBlockState(pos).getBlock();
				if(blockAt instanceof IItemPipe) {
					add.addAll(Arrays.asList(getNeighborLocations(pos)));
					checked.add(pos);
					TileEntity te = world.getTileEntity(pos);
					if(te instanceof TileItemSource) {
						((TileItemSource) te).setShouldSearch(true);
					}
				}
			}
			toCheck.clear();
			for(BlockPos pos : checked) {
				add.remove(pos);
			}
			toCheck.addAll(add);
			add.clear();
			if(i > 500) {
				System.out.println("Long");
			}
		}
	}
	
}
