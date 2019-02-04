package com.irar.arkiatech.config;

public enum ConfigInts {
	MAX_CHARGER_ENERGY("MaxChargerEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in a charger"),
	MAX_GENERATOR_ENERGY("MaxGeneratorEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in a generator"),
	MAX_GRINDER_ENERGY("MaxGrinderEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in an integrator"),
	MAX_FURNACE_ENERGY("MaxFurnaceEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in a powered furnace"),
	MAX_UPGRADER_ENERGY("MaxUpgraderEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in a restructurer"),
	GENERATOR_ENERGY_PRODUCED("GeneratorEnergyProduced", "EnergyProduced", 40, 1, Integer.MAX_VALUE / 20, "Energy produced by a generator each tick"),
	MAX_GRINDER_DRIFT("MaxGrinderDrift", "MaxDrift", 10000, 100, Integer.MAX_VALUE, "Maximum drift that can be stored in an integrator"),
	GRINDER_DRIFT_USED("GrinderDriftUsed", "DriftUsed", 35, 1, Integer.MAX_VALUE, "Drift used per integrator operation"),
	DRIFT_PER_ITEM("DriftPerItem", "Items", 100, 1, Integer.MAX_VALUE, "Drift per item of Zephyrian Drift"),
	GRINDER_ENERGY_USED("GrinderEnergyUsed", "EnergyUsed", 20, 1, Integer.MAX_VALUE / 20, "Base energy used by an integrator each tick"),
	FURNACE_ENERGY_USED("FurnaceEnergyUsed", "EnergyUsed", 20, 1, Integer.MAX_VALUE / 20, "Base energy used by a powered furnace each tick"),
	UPGRADER_ENERGY_USED("UpgraderEnergyUsed", "EnergyUsed", 80, 1, Integer.MAX_VALUE / 20, "Base energy used by a restructurer each tick"),
	GRINDER_PROCESS_TIME("GrinderProcessTime", "ProcessTime", 200, 1, Integer.MAX_VALUE, "Base processing time of an integrator (in ticks)"),
	FURNACE_PROCESS_TIME("FurnaceProcessTime", "ProcessTime", 100, 1, Integer.MAX_VALUE, "Base processing time of a powered furnace (in ticks)"),
	UPGRADER_PROCESS_TIME("UpgraderProcessTime", "ProcessTime", 400, 1, Integer.MAX_VALUE, "Base processing time of a restructurer (in ticks)"),
	BASIC_FURNACE_NUM("BasicFurnaceNum", "FurnaceNum", 1, 1, 3, "Number of furnaces per basic powered furnace");

	public final String name;
    public final String category;
    public final int def;
    public final int min;
    public final int max;
    public final String desc;
    public int currentValue;
    
    ConfigInts(String name, String category, int def, int min, int max, String desc){
    	this.name = name;
    	this.category = category;
    	this.def = def;
    	this.min = min;
    	this.max = max;
    	this.desc = desc;
    }
	
}
