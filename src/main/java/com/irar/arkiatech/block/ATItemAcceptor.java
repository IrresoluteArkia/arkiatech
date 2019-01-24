package com.irar.arkiatech.block;

import com.irar.arkiatech.tileentity.ATTE;
import com.irar.arkiatech.tileentity.TileItemAcceptor;

import net.minecraft.block.material.Material;

public class ATItemAcceptor extends ATContainerBlockBase<TileItemAcceptor> implements IItemPipe{

	public ATItemAcceptor(Material materialIn, String registryName) {
		super(materialIn, registryName, ATTE.ITEMACCEPTOR);
	}

}
