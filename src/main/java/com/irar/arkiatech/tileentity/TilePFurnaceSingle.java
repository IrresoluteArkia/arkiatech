package com.irar.arkiatech.tileentity;

import com.irar.arkiatech.config.ConfigInts;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TilePFurnaceSingle extends TilePFurnace {

	public TilePFurnaceSingle() {
		super(ATTEGui.PFURNACE, ConfigInts.BASIC_FURNACE_NUM.currentValue);
	}

}
