package com.irar.arkiatech.network;

import com.irar.arkiatech.tileentity.TileEnergyBase;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FieldMessageHandler implements IMessageHandler<FieldMessage, IMessage>{

	@Override
	public IMessage onMessage(FieldMessage message, MessageContext ctx) {
		TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(message.pos);
		if(tile != null && tile instanceof TileEnergyBase) {
			TileEnergyBase tileEnergy = (TileEnergyBase) tile;
			for(int i = 0; i < message.fields.length; i++) {
				int field = message.fields[i];
				tileEnergy.setField(i, field);
			}
		}
		return null;
	}

}
