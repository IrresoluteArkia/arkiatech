package com.irar.arkiatech.handlers;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.block.ATBlockBase;
import com.irar.arkiatech.block.ATCharger;
import com.irar.arkiatech.block.ATEnergyPipe;
import com.irar.arkiatech.block.ATGenerator;
import com.irar.arkiatech.block.ATGrinder;
import com.irar.arkiatech.block.ATItemAcceptor;
import com.irar.arkiatech.block.ATItemPipe;
import com.irar.arkiatech.block.ATItemSource;
import com.irar.arkiatech.block.ATLeaf;
import com.irar.arkiatech.block.ATLog;
import com.irar.arkiatech.block.ATPFurnace;
import com.irar.arkiatech.block.ATSapling;
import com.irar.arkiatech.block.ATUpgrader;
import com.irar.arkiatech.block.IFuelBlock;
import com.irar.arkiatech.item.ItemBlockFuel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ATBlocks {

	public static Block zeLog;
	public static Block zeLeaf;
	public static Block zeSapling;
	public static Block zePlanks;
	public static Block grinder;
	public static Block generator;
	public static Block upgrader;
	public static Block pFurnace;
	public static Block charger;
	public static Block energyPipe;
	public static Block machineFrame;
	public static Block reMachineFrame;
	public static Block zephyrBlock;
	public static Block upIronBlock;
	public static Block upGoldBlock;
	public static Block upQuartzBlock;
	public static Block upDiamondBlock;
	public static Block upEmeraldBlock;
	public static Block coZephyrBlock;
	public static Block itemPipe;
	public static Block itemSource;
//	public static Block itemAcceptor;
	private static List<Block> allBlocks = new ArrayList<>();
	
	public static void init() {
		zeLog = new ATLog("zephyrian_log");
		zePlanks = new ATBlockBase(Material.WOOD, "zephyrian_planks");
		zeLeaf = new ATLeaf("zephyrian_leaves");
		zeSapling = new ATSapling("zephyrian_sapling");
		grinder = new ATGrinder(Material.ROCK, "grinder");
		generator = new ATGenerator(Material.ROCK, "generator");
		upgrader = new ATUpgrader(Material.ROCK, "upgrader");
		pFurnace = new ATPFurnace(Material.ROCK, "p_furnace");
		charger = new ATCharger(Material.ROCK, "charger");
		energyPipe = new ATEnergyPipe(Material.ROCK, "energy_pipe");
		machineFrame = new ATBlockBase(Material.ROCK, "machine_frame");
		reMachineFrame = new ATBlockBase(Material.ROCK, "machine_frame_reinforced");
		zephyrBlock = new ATBlockBase(Material.ROCK, "zephyr_block").setRenderSeeThrough(true);
		upIronBlock = new ATBlockBase(Material.ROCK, "up_iron_block");
		upGoldBlock = new ATBlockBase(Material.ROCK, "up_gold_block");
		upQuartzBlock = new ATBlockBase(Material.ROCK, "up_quartz_block");
		upDiamondBlock = new ATBlockBase(Material.ROCK, "up_diamond_block");
		upEmeraldBlock = new ATBlockBase(Material.ROCK, "up_emerald_block");
		coZephyrBlock = new ATBlockBase(Material.ROCK, "co_zephyr_block").setRenderSeeThrough(true);
		itemPipe = new ATItemAcceptor(Material.ROCK, "item_pipe");
		itemSource = new ATItemSource(Material.ROCK, "item_source");
//		itemAcceptor = new ATItemAcceptor(Material.ROCK, "item_acceptor");
		
		allBlocks.add(zeLog);
		allBlocks.add(zePlanks);
		allBlocks.add(zeLeaf);
		allBlocks.add(zeSapling);
		allBlocks.add(grinder);
		allBlocks.add(generator);
		allBlocks.add(upgrader);
		allBlocks.add(pFurnace);
		allBlocks.add(charger);
		allBlocks.add(energyPipe);
		allBlocks.add(machineFrame);
		allBlocks.add(reMachineFrame);
		allBlocks.add(zephyrBlock);
		allBlocks.add(upIronBlock);
		allBlocks.add(upGoldBlock);
		allBlocks.add(upQuartzBlock);
		allBlocks.add(upDiamondBlock);
		allBlocks.add(upEmeraldBlock);
		allBlocks.add(coZephyrBlock);
		allBlocks.add(itemPipe);
		allBlocks.add(itemSource);
//		allBlocks.add(itemAcceptor);
		
		sendItemBlocksToATItems(allBlocks);
	}
	
	public static void postInit() {
		((ATLeaf) zeLeaf).setExtraItemDropped(new ItemStack(ATItems.zeDrift)).setExtraItemDropChance(4);
	}

	private static void sendItemBlocksToATItems(List<Block> blocks) {
		for(Block block : blocks) {
			ItemBlock ib;
			if(block instanceof IFuelBlock) {
				ib = new ItemBlockFuel(block);
			}else {
				ib = new ItemBlock(block);
			}
			ib.setRegistryName(block.getRegistryName());
			ATItems.allItems.add(ib);
		}
	}

	public static void register() {
		for(Block block : allBlocks) {
			ForgeRegistries.BLOCKS.register(block);
		}
	}

	public static void registerRenders() {
		for(Block block : allBlocks) {
			registerRender(block);
		}
	}

	private static void registerRender(Block block) {
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

}
