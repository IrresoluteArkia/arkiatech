package com.irar.arkiatech.network;

import com.irar.arkiatech.tileentity.TileEnergyBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class FieldMessage implements IMessage{

	public BlockPos pos;
	public int[] fields;

	public FieldMessage(TileEnergyBase tile) {
		pos = tile.getPos();
		fields = new int[tile.getFieldCount()];
		for(int i = 0; i < fields.length; i++) {
			fields[i] = tile.getField(i);
		}
	}

	@Override
	public void fromBytes(ByteBuf bb) {
		PacketBuffer buf = new PacketBuffer(bb);
		int x = buf.readVarInt();
		int y = buf.readVarInt();
		int z = buf.readVarInt();
		pos = new BlockPos(x, y, z);
		int fieldNum = buf.readVarInt();
		fields = new int[fieldNum];
		for(int i = 0; i < fieldNum; i++) {
			fields[i] = buf.readVarInt();
		}
		
	}

	@Override
	public void toBytes(ByteBuf bb) {
		PacketBuffer buf = new PacketBuffer(bb);
		buf.writeVarInt(pos.getX());
		buf.writeVarInt(pos.getY());
		buf.writeVarInt(pos.getZ());
		buf.writeVarInt(fields.length);
		for(int field : fields) {
			buf.writeVarInt(field);
		}
	}

}
