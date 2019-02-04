package com.irar.arkiatech.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigStore {
	public static void setConfigs(Configuration config) {
        for(ConfigBooleans currConf : ConfigBooleans.values()){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.def, currConf.desc).getBoolean();
        }
        
        for(ConfigInts currConf : ConfigInts.values()){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.def, currConf.desc, currConf.min, currConf.max).getInt();
        }
        
        for(ConfigDoubles currConf : ConfigDoubles.values()){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.def, currConf.desc, currConf.min, currConf.max).getDouble();
        }
	}
}
