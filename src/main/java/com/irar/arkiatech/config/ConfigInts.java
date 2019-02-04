package com.irar.arkiatech.config;

public enum ConfigInts {
	MAX_CHARGER_ENERGY("MaxChargerEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in a charger"),
	MAX_GENERATOR_ENERGY("MaxGeneratorEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in a generator"),
	MAX_GRINDER_ENERGY("MaxGrinderEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in an integrator"),
	MAX_FURNACE_ENERGY("MaxFurnaceEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in a powered furnace"),
	MAX_UPGRADER_ENERGY("MaxUpgraderEnergy", "MaxEnergy", 100000, 1, Integer.MAX_VALUE, "Maximum energy that can be stored in a restructurer"),
	GENERATOR_ENERGY_PRODUCED("GeneratorEnergyProduced", "EnergyProduced", 40, 1, Integer.MAX_VALUE / 20, "Energy produced by a generator each tick")
	;

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
