package com.irar.arkiatech.proxy;

import com.irar.arkiatech.block.ATLeaf;
import com.irar.arkiatech.block.ATSapling;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MainEventHandler {

/*	@SubscribeEvent
	public void fuel(FurnaceFuelBurnTimeEvent e) {
		ItemStack is = e.getItemStack();
		if(is.getItem() instanceof ItemBlock) {
			ItemBlock ib = (ItemBlock) is.getItem();
			if(ib.getBlock() instanceof ATSapling || ib.getBlock() instanceof ATLeaf) {
				e.setBurnTime(200);
			}
		}
	}*/
	
}
