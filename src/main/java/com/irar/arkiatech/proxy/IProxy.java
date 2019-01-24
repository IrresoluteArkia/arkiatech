package com.irar.arkiatech.proxy;

import com.irar.arkiatech.block.ATLeaf;
import com.irar.arkiatech.network.FieldMessage;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public interface IProxy {

	void preInit(FMLPreInitializationEvent event);

	void init(FMLInitializationEvent event);

	void postInit(FMLPostInitializationEvent event);

	void setGraphicsLevel(ATLeaf atLeaf, boolean b);

	void sendToClients(IMessage message);

}
