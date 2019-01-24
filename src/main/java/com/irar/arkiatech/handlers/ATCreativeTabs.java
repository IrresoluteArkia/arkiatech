package com.irar.arkiatech.handlers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ATCreativeTabs {
	public static CreativeTabs ARKIATECHMAIN = new CreativeTabs("arkiatechmain") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ATBlocks.zeSapling);
		}
		
	};
}
