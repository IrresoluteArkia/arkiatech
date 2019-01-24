package com.irar.arkiatech.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.tileentity.TileMachineBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TransferMessage implements IMessage{

	public BlockPos pos;
	public List<ItemStack> stacks;

	public TransferMessage() {}
	
	public TransferMessage(TileMachineBase tile, List<ItemStack> stacks) {
		this.pos = tile.getPos();
		this.stacks = stacks;
	}
	
	@Override
	public void fromBytes(ByteBuf bb) {
		PacketBuffer buf = new PacketBuffer(bb);
		int x = buf.readVarInt();
		int y = buf.readVarInt();
		int z = buf.readVarInt();
		int numItemStacks = buf.readVarInt();
		List<ItemStack> stacks = new ArrayList<>();
		for(int i = 0; i < numItemStacks; i++) {
			try {
				stacks.add(buf.readItemStack());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		pos = new BlockPos(x, y, z);
		this.stacks = stacks;
	}

	@Override
	public void toBytes(ByteBuf bb) {
		PacketBuffer buf = new PacketBuffer(bb);
		buf.writeVarInt(pos.getX());
		buf.writeVarInt(pos.getY());
		buf.writeVarInt(pos.getZ());
		buf.writeVarInt(stacks.size());
		for(ItemStack stack : stacks) {
			buf.writeItemStack(stack);
		}
	}

}
