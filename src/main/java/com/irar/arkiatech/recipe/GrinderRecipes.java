package com.irar.arkiatech.recipe;

import java.util.ArrayList;
import java.util.List;

import com.irar.arkiatech.handlers.ATItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

public class GrinderRecipes {

	public static List<GrinderRecipe> allRecipes = new ArrayList<>();
	
	public static void init() {
		allRecipes.add(new GrinderRecipe(new ItemStack(ATItems.zeStable), new ItemStack(ATItems.zeDust, 2)));
		allRecipes.add(new GrinderRecipe("oreIron", new ItemStack(ATItems.zeIron, 2)));
		allRecipes.add(new GrinderRecipe("oreGold", new ItemStack(ATItems.zeGold, 2)));
		allRecipes.add(new GrinderRecipe("oreQuartz", new ItemStack(ATItems.zeQuartz, 2)));
		allRecipes.add(new GrinderRecipe("oreDiamond", new ItemStack(ATItems.zeDiamond, 2)));
		allRecipes.add(new GrinderRecipe("oreEmerald", new ItemStack(ATItems.zeEmerald, 2)));
		allRecipes.add(new GrinderRecipe("oreRedstone", new ItemStack(Items.REDSTONE, 10)));
		allRecipes.add(new GrinderRecipe("oreLapis", new ItemStack(Items.DYE, 15, 4)));
		allRecipes.add(new GrinderRecipe("oreCoal", new ItemStack(Items.COAL, 5)));
		allRecipes.add(new GrinderRecipe("ingotIron", new ItemStack(ATItems.zeIron, 1)));
		allRecipes.add(new GrinderRecipe("ingotGold", new ItemStack(ATItems.zeGold, 1)));
		allRecipes.add(new GrinderRecipe("gemQuartz", new ItemStack(ATItems.zeQuartz, 1)));
		allRecipes.add(new GrinderRecipe("gemDiamond", new ItemStack(ATItems.zeDiamond, 1)));
		allRecipes.add(new GrinderRecipe("gemEmerald", new ItemStack(ATItems.zeEmerald, 1)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.IRON_BOOTS), new ItemStack(ATItems.zeIron, 3)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.IRON_LEGGINGS), new ItemStack(ATItems.zeIron, 6)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(ATItems.zeIron, 7)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.IRON_HELMET), new ItemStack(ATItems.zeIron, 4)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.GOLDEN_BOOTS), new ItemStack(ATItems.zeGold, 3)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.GOLDEN_LEGGINGS), new ItemStack(ATItems.zeGold, 6)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.GOLDEN_CHESTPLATE), new ItemStack(ATItems.zeGold, 7)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.GOLDEN_HELMET), new ItemStack(ATItems.zeGold, 4)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.DIAMOND_BOOTS), new ItemStack(ATItems.zeDiamond, 3)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.DIAMOND_LEGGINGS), new ItemStack(ATItems.zeDiamond, 6)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.DIAMOND_CHESTPLATE), new ItemStack(ATItems.zeDiamond, 7)));
		allRecipes.add(new GrinderRecipe(new ItemStack(Items.DIAMOND_HELMET), new ItemStack(ATItems.zeDiamond, 4)));
		
		addOreToDustRecipes();
	}

	private static void addOreToDustRecipes() {
		String[] ores = OreDictionary.getOreNames();
		String[] oreOres = getStringsStartingWith(ores, "ore");
		String[] dustOres = getStringsStartingWith(ores, "dust");
		subtractBeginningChars(oreOres, 3);
		subtractBeginningChars(dustOres, 4);
		String[] matching = getMatching(oreOres, dustOres);
		
		for(String ore : matching) {
			if(!isIngredientFor("ore" + ore)) {
				NonNullList<ItemStack> outputs = OreDictionary.getOres("dust" + ore);
				if(outputs.size() > 0) {
					ItemStack output = outputs.get(0).copy();
					output.setCount(2);
					allRecipes.add(new GrinderRecipe("ore" + ore, output));
				}
			}
		}
	}

	private static boolean isIngredientFor(String ore) {
		NonNullList<ItemStack> stacks = OreDictionary.getOres(ore);
		for(ItemStack stack : stacks) {
			for(GrinderRecipe recipe : allRecipes) {
				if(recipe.matches(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	private static String[] getMatching(String[] s1, String[] s2) {
		List<String> s3 = new ArrayList<>();
		List<String> s2a = Arrays.asList(s2);
		for(String s : s1) {
			if(s.length() == 0) {
				// Pointless to add empty
				continue;
			}
			if(s2a.contains(s)) {
				s3.add(s);
			}
		}
		return s3.toArray(new String[0]);
	}

	private static void subtractBeginningChars(String[] ss, int num) {
		for(int i = 0; i < ss.length; i++) {
			String s = ss[i];
			if(s.length() <= num) {
				ss[i] = "";
			}else {
				ss[i] = s.substring(num);
			}
		}
	}

	private static String[] getStringsStartingWith(String[] ss, String string) {
		List<String> starting = new ArrayList<>();
		for(String s : ss) {
			if(s.startsWith(string)) {
				starting.add(s);
			}
		}
		return starting.toArray(new String[0]);
	}
	
}
