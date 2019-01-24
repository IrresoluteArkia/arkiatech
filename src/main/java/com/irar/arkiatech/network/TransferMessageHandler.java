package com.irar.arkiatech.network;

import com.irar.arkiatech.tileentity.TileMachineBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TransferMessageHandler implements IMessageHandler<TransferMessage, IMessage>{

	@Override
	public IMessage onMessage(TransferMessage message, MessageContext ctx) {
		TileEntity te = ctx.getServerHandler().player.world.getTileEntity(message.pos);
		if(te != null && te instanceof TileMachineBase) {
			TileMachineBase teeib = (TileMachineBase) te;
			teeib.transferItems(message.stacks);
		}
		return null;
	}

}
