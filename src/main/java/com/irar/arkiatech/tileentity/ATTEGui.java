package com.irar.arkiatech.tileentity;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.gui.client.GuiBase;
import com.irar.arkiatech.gui.client.GuiCharger;
import com.irar.arkiatech.gui.client.GuiGrinder;
import com.irar.arkiatech.gui.client.GuiPFurnaceSingle;
import com.irar.arkiatech.gui.client.GuiUpgrader;
import com.irar.arkiatech.gui.client.GuiGenerator;
import com.irar.arkiatech.gui.container.ContainerBase;
import com.irar.arkiatech.gui.container.ContainerCharger;
import com.irar.arkiatech.gui.container.ContainerGrinder;
import com.irar.arkiatech.gui.container.ContainerPFurnaceSingle;
import com.irar.arkiatech.gui.container.ContainerUpgrader;
import com.irar.arkiatech.gui.container.ContainerGenerator;

public class ATTEGui<T extends TileMachineBase, V extends ContainerBase, G extends GuiBase> extends ATTE<T>{
	
	static List<ATTEGui> allGuiTiles = new ArrayList<>();
	public static final ATTEGui<TileGrinder, ContainerGrinder, GuiGrinder> GRINDER = new ATTEGui<>("grinder_tile_entity", TileGrinder.class, ContainerGrinder.class, GuiGrinder.class);
	public static final ATTEGui<TileGenerator, ContainerGenerator, GuiGenerator> GENERATOR = new ATTEGui<>("generator_tile_entity", TileGenerator.class, ContainerGenerator.class, GuiGenerator.class);
	public static final ATTEGui<TileUpgrader, ContainerUpgrader, GuiUpgrader> UPGRADER = new ATTEGui<>("upgrader_tile_entity", TileUpgrader.class, ContainerUpgrader.class, GuiUpgrader.class);
	public static final ATTEGui<TilePFurnaceSingle, ContainerPFurnaceSingle, GuiPFurnaceSingle> PFURNACE = new ATTEGui<>("p_furnace_tile_entity", TilePFurnaceSingle.class, ContainerPFurnaceSingle.class, GuiPFurnaceSingle.class);
	public static final ATTEGui<TileCharger, ContainerCharger, GuiCharger> CHARGER = new ATTEGui<>("charger_tile_entity", TileCharger.class, ContainerCharger.class, GuiCharger.class);
	
	public Class<V> containerClass;
	public Class<G> guiClass;
	
	 ATTEGui(String name, Class<T> tileClass, Class<V> containerClass, Class<G> guiClass) {
		super(name, tileClass);
		this.containerClass = containerClass;
		this.guiClass = guiClass;
		allGuiTiles.add(this);
	}


	public static ATTEGui fromInt(int i) {
		for(ATTE e : allTiles) {
			if(e instanceof ATTEGui && i == e.uid) {
				return (ATTEGui) e;
			}
		}
		return null;
	}
}
