package com.irar.arkiatech.tileentity;

import java.util.ArrayList;
import java.util.List;

public class ATTE<T extends TileBase> {

	static List<ATTE> allTiles = new ArrayList<>();
	
	public static ATTE<TileEnergyPipe> ENERGYPIPE = new ATTE<>("energy_pipe_tile_entity", TileEnergyPipe.class);
	public static ATTE<TileItemSource> ITEMSOURCE = new ATTE<>("item_source_tile_entity", TileItemSource.class);
	public static ATTE<TileItemAcceptor> ITEMACCEPTOR = new ATTE<>("item_acceptor_tile_entity", TileItemAcceptor.class);
	
	protected final String name;
	public final Class<T> tileClass;
	public final int uid;
	protected static int id = 0;

	
	public ATTE(String name, Class<T> tileClass) {
		this.name = name;
		this.tileClass = tileClass;
		this.uid = id;
		id++;
		allTiles.add(this);
	}

	@Override
	public String toString() {
		return name;
	}
	
	public static ATTE fromInt(int i) {
		for(ATTE e : allTiles) {
			if(i == e.uid) {
				return e;
			}
		}
		return null;
	}
}
