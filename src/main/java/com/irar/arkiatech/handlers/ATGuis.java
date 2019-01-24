package com.irar.arkiatech.handlers;

import java.lang.reflect.InvocationTargetException;

import com.irar.arkiatech.gui.client.GuiGenerator;
import com.irar.arkiatech.gui.client.GuiGrinder;
import com.irar.arkiatech.gui.container.ContainerGenerator;
import com.irar.arkiatech.gui.container.ContainerGrinder;
import com.irar.arkiatech.tileentity.TileGenerator;
import com.irar.arkiatech.tileentity.TileGrinder;
import com.irar.arkiatech.tileentity.ATTEGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ATGuis implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		ATTEGui teID = ATTEGui.fromInt(ID);
		if(teID == null) {
			return null;
		}
		return instantiateContainer(teID, player.inventory, world.getTileEntity(new BlockPos(x, y, z)));
	}

	public static Container instantiateContainer(ATTEGui teID, InventoryPlayer inventory, TileEntity tileEntity) {
		try {
			return (Container) teID.containerClass.getDeclaredConstructor(InventoryPlayer.class, teID.tileClass).newInstance(inventory, tileEntity);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		ATTEGui teID = ATTEGui.fromInt(ID);
		if(teID == null) {
			return null;
		}
		return instantiateGui(teID, player.inventory, world.getTileEntity(new BlockPos(x, y, z)));
	}

	private Object instantiateGui(ATTEGui teID, InventoryPlayer inventory, TileEntity tileEntity) {
		try {
			return teID.guiClass.getDeclaredConstructor(InventoryPlayer.class, teID.tileClass).newInstance(inventory, tileEntity);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
}
