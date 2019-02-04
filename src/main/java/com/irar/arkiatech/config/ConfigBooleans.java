package com.irar.arkiatech.config;

public enum ConfigBooleans {
	;

	public final String name;
    public final String category;
    public final boolean def;
    public final String desc;
	public boolean currentValue;
	
	ConfigBooleans(String name, String category, boolean def, String desc){
		this.name = name;
		this.category = category;
		this.def = def;
		this.desc = desc;
	}

}
