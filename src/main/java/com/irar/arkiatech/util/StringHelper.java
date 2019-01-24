package com.irar.arkiatech.util;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;

public class StringHelper {

	public static String applyStyle(String string, Style style) {
		TextComponentString stringText = new TextComponentString(string);
		stringText.setStyle(style);
		return stringText.getText();
	}

}
