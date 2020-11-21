package com.irar.arkiatech.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.irar.arkiatech.item.ATDrill;
import com.irar.arkiatech.item.ATEnergyItem;
import com.irar.arkiatech.item.ATItemBase;
import com.irar.arkiatech.item.ATMachineUpgrade;
import com.irar.arkiatech.item.ATMagnet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ATItems {

	public static Item zeDrift;
	public static Item zeStable;
	public static Item zeIron;
	public static Item zeGold;
	public static Item zeQuartz;
	public static Item zeDiamond;
	public static Item zeEmerald;
	public static Item zeDust;
	public static Item zeIngot;
	public static Item upIronZephyr;
	public static Item upGoldZephyr;
	public static Item upQuartzZephyr;
	public static Item upDiamondZephyr;
	public static Item upEmeraldZephyr;
	public static Item upZephyr;
	public static Item upIron;
	public static Item upGold;
	public static Item upQuartz;
	public static Item upDiamond;
	public static Item upEmerald;
	public static Item coZephyr;
	public static Item pulse;
	public static Item moeru;
	public static Item luki;
	public static Item upgradeEnergy;
	public static Item upgradeSpeed;
	public static Item battery;
	public static Item drill;
	public static Item magnet;
	
	public static List<Item> allItems = new ArrayList<>();
	
	private static HashMap<String, Item> itemDict = new HashMap<>();

	public static void init() {
		zeDrift = new ATItemBase("zephyrian_drift");
		zeStable = new ATItemBase("zephyr_stable");
		zeIron = new ATItemBase("iron_zephyr");
		zeGold = new ATItemBase("gold_zephyr");
		zeQuartz = new ATItemBase("quartz_zephyr");
		zeDiamond = new ATItemBase("diamond_zephyr");
		zeEmerald = new ATItemBase("emerald_zephyr");
		zeDust = new ATItemBase("zephyr_dust");
		zeIngot = new ATItemBase("zephyr_ingot");
		upIronZephyr = new ATItemBase("up_iron_zephyr");
		upGoldZephyr = new ATItemBase("up_gold_zephyr");
		upQuartzZephyr = new ATItemBase("up_quartz_zephyr");
		upDiamondZephyr = new ATItemBase("up_diamond_zephyr");
		upEmeraldZephyr = new ATItemBase("up_emerald_zephyr");
		upZephyr = new ATItemBase("up_zephyr");
		upIron = new ATItemBase("up_iron");
		upGold = new ATItemBase("up_gold");
		upQuartz = new ATItemBase("up_quartz");
		upDiamond = new ATItemBase("up_diamond");
		upEmerald = new ATItemBase("up_emerald");
		coZephyr = new ATItemBase("co_zephyr");
		pulse = new ATItemBase("pulse");
		moeru = new ATItemBase("moeru");
		luki = new ATItemBase("luki");
		upgradeEnergy = new ATMachineUpgrade("upgrade_energy", 51200);
		upgradeSpeed = new ATMachineUpgrade("upgrade_speed", 13107200);
		battery = new ATEnergyItem("battery", 1000000);
		drill = new ATDrill("drill");
		magnet = new ATMagnet("magnet");
		createBasicItem("basic_circuit_core");
		createBasicItem("basic_circuit");
		
		allItems.add(zeDrift);
		allItems.add(zeStable);
		allItems.add(zeIron);
		allItems.add(zeGold);
		allItems.add(zeQuartz);
		allItems.add(zeDiamond);
		allItems.add(zeEmerald);
		allItems.add(zeDust);
		allItems.add(zeIngot);
		allItems.add(upIronZephyr);
		allItems.add(upGoldZephyr);
		allItems.add(upQuartzZephyr);
		allItems.add(upDiamondZephyr);
		allItems.add(upEmeraldZephyr);
		allItems.add(upZephyr);
		allItems.add(upIron);
		allItems.add(upGold);
		allItems.add(upQuartz);
		allItems.add(upDiamond);
		allItems.add(upEmerald);
		allItems.add(coZephyr);
		allItems.add(pulse);
		allItems.add(moeru);
		allItems.add(luki);
		allItems.add(upgradeEnergy);
		allItems.add(upgradeSpeed);
		allItems.add(battery);
		allItems.add(drill);
		allItems.add(magnet);
		
		createLookupDict();
	}

	private static void createLookupDict() {
		for(Item item : allItems) {
			itemDict.put(item.getRegistryName().getResourcePath(), item);
		}
	}

	private static void createBasicItem(String string) {
		Item item = new ATItemBase(string);
		allItems.add(item);
	}

	public static void register() {
		for(Item item : allItems) {
			ForgeRegistries.ITEMS.register(item);
		}
	}

	public static void registerRenders() {
		for(Item item : allItems) {
			registerRender(item);
		}
	}

	private static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	public static Item getItemByName(String name) {
		if(itemDict.containsKey(name)) {
			return itemDict.get(name);
		}
		return null;
	}

	public static void registerItemColors() {
		for(Item item : allItems) {
			registerItemColor(item);
		}
	}

	private static void registerItemColor(Item item) {
		if(item instanceof IItemColor) {
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler((IItemColor) item, item);
		}
	}

}
