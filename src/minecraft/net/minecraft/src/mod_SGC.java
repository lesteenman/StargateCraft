package net.minecraft.src;
import java.io.File;

import net.minecraft.src.sgc.*;

public class mod_SGC extends BaseMod {
	
	public static final String[] glyphs = {
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", 
			"t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
	};
	public static mod_SGC instance;
	public static SGCDimensions dimensions;
	public static File modFolder;
	public static Achievement sgAchievement;

	public mod_SGC() {
		instance = this;
	}
	
	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public void load() {
		modFolder = new File(ModLoader.getMinecraftInstance().mcDataDir.getAbsolutePath(), "mods/sgc");
		System.out.println("mod_SGC loaded!");
		
		new net.minecraft.src.sgc.SGCBlocks();
		
		dimensions = new SGCDimensions();
		dimensions.registerPlanets();
		
		//Some test code to try if we can load worldproviders based on a name
		try {
			Class c = Class.forName("net.minecraft.src.SGC_TestClass");
			SGC_TestClass testClass = (SGC_TestClass) c.newInstance();
			System.out.println(testClass.getInteger());
		} catch (ClassNotFoundException e) {
			System.out.println("The given class was not found!");
		} catch (InstantiationException e) {
			System.out.println("InstantiationException thrown! " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println("Illegal access exception: " + e.getMessage());
		}
	}

	public static void addAchievements() {
		sgAchievement = new Achievement(89, "sgAchievement", -5, -5, Item.goldNugget, null);
		sgAchievement.registerAchievement();
		ModLoader.addAchievementDesc(sgAchievement, "Cold bones", "You went through the stargate!");
	}
}
