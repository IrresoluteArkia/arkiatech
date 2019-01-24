package com.irar.arkiatech.proxy;

import com.irar.arkiatech.ArkiaTech;
import com.irar.arkiatech.block.ATLeaf;
import com.irar.arkiatech.handlers.ATBlocks;
import com.irar.arkiatech.handlers.ATCrafting;
import com.irar.arkiatech.handlers.ATGuis;
import com.irar.arkiatech.handlers.ATItems;
import com.irar.arkiatech.network.ATPacketHandler;
import com.irar.arkiatech.network.FieldMessage;
import com.irar.arkiatech.network.FieldMessageHandler;
import com.irar.arkiatech.network.TransferMessage;
import com.irar.arkiatech.network.TransferMessageHandler;
import com.irar.arkiatech.recipe.GrinderRecipes;
import com.irar.arkiatech.recipe.UpgraderRecipes;
import com.irar.arkiatech.tileentity.TileEntities;
import com.irar.arkiatech.worldgen.WorldGenZephyrianTree;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

public class ServerProxy implements IProxy{

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		final MainEventHandler handler = new MainEventHandler();
        MinecraftForge.EVENT_BUS.register((Object)handler);
		
		ATBlocks.init();
		ATItems.init();
		ATBlocks.register();
		ATItems.register();
		GameRegistry.registerWorldGenerator(new WorldGenZephyrianTree(), 0);
		registerOres();
		TileEntities.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(ArkiaTech.instance, new ATGuis());
		
		ATPacketHandler.INSTANCE.registerMessage(TransferMessageHandler.class, TransferMessage.class, 0, Side.SERVER);
		ATPacketHandler.INSTANCE.registerMessage(FieldMessageHandler.class, FieldMessage.class, 1, Side.CLIENT);
	}

	private void registerOres() {
		OreDictionary.registerOre("logWood", ATBlocks.zeLog);
		OreDictionary.registerOre("plankWood", ATBlocks.zePlanks);
		OreDictionary.registerOre("treeSapling", ATBlocks.zeSapling);
		OreDictionary.registerOre("treeLeaves", ATBlocks.zeLeaf);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		ATBlocks.postInit();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		ATCrafting.registerCrafting();
		ATCrafting.registerSmelting();
		GrinderRecipes.init();
		UpgraderRecipes.init();
	}

	@Override public void setGraphicsLevel(ATLeaf atLeaf, boolean b) {}

	@Override
	public void sendToClients(IMessage message) {
		ATPacketHandler.INSTANCE.sendToAll(message);
	}

}
