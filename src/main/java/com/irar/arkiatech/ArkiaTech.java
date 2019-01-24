package com.irar.arkiatech;

import com.irar.arkiatech.proxy.IProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Ref.MODID, version = Ref.VERSION)

public class ArkiaTech {
	@SidedProxy(clientSide = Ref.CLIENT_PROXY, serverSide=Ref.SERVER_PROXY)
	public static IProxy proxy;
	
	@Instance(Ref.MODID)
	public static ArkiaTech instance = new ArkiaTech();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}
	
	@EventHandler
	public void preInit(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void preInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
}
