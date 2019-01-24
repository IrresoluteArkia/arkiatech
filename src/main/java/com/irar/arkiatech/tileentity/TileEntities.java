package com.irar.arkiatech.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {

	public static void init() {
		for(ATTE tile : ATTE.allTiles) {
			GameRegistry.registerTileEntity(tile.tileClass, tile.toString());
		}
	}
	
}
