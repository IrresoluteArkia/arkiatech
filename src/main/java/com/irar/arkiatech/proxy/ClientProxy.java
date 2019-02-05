package com.irar.arkiatech.proxy;

import com.irar.arkiatech.block.ATLeaf;
import com.irar.arkiatech.handlers.ATBlocks;
import com.irar.arkiatech.handlers.ATItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ClientProxy extends ServerProxy{

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ATItems.registerRenders();
		ATBlocks.registerRenders();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		ATItems.registerItemColors();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override 
	public void setGraphicsLevel(ATLeaf atLeaf, boolean b) {
		atLeaf.setGraphicsLevel(b);
	}
	
	@Override
	public void sendToClients(IMessage message) {}


	
}
