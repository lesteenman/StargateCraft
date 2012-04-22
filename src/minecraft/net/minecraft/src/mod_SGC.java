package net.minecraft.src;
import net.minecraft.src.sgc.*;

public class mod_SGC extends BaseMod {

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public void load() {
		System.out.println("mod_SGC loaded!");
		
		new net.minecraft.src.sgc.SGCBlocks();
		
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

}
