package com.irar.arkiatech.config;

public enum ConfigDoubles {
	GENERATOR_BURN_TIME_MODIFIER("GeneratorBurnTimeModifier", "TimeModifier", 1.0, 1e-6, 1e+6, "Multiplied by the default burn time for an item to get its burn time in the generator");
	
	public final String name;
    public final String category;
    public final double def;
    public final double min;
    public final double max;
    public final String desc;
    public double currentValue;
    
    ConfigDoubles(String name, String category, double def, double min, double max, String desc){
    	this.name = name;
    	this.category = category;
    	this.def = def;
    	this.min = min;
    	this.max = max;
    	this.desc = desc;
    }
	
}
