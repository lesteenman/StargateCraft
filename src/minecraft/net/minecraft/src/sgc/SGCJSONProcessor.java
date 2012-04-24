package net.minecraft.src.sgc;

import java.io.*;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;

public class SGCJSONProcessor extends SGCDimensions {
	
	private static SGCJSONProcessor instance;
	
	public static SGCJSONProcessor getInstance() {
		if (instance == null)
			instance = new SGCJSONProcessor();
		return instance;
	}
	
	/**
	 * 
	 * @param f The file that contains the JSON
	 * @return the deserialized JSONObject
	 */
	public JSONObject getJSONObject(File f) {
		StringBuilder sb = null;
	    try {
			FileInputStream is = new FileInputStream(f);
			sb = new StringBuilder(512);
	        Reader r = new InputStreamReader(is, "UTF-8");
	        int c = 0;
	        while (c != -1) {
	            c = r.read();
	            sb.append((char) c);
	        }
	    } catch (FileNotFoundException e) {
	    	System.out.println("ERROR: World JSON File was not found! " + e.getMessage());
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    if (sb == null) {
	    	System.out.println("ERROR: World JSON File was empty!");
	    	return null;
	    }
	    return JSONObject.deserialize(sb.toString());
	}
	
	public File getWorldJSONFile() {
		Minecraft mc = ModLoader.getMinecraftInstance();
		File path = new File(mc.mcDataDir.getAbsolutePath(), "saves/"+mc.theWorld.getWorldInfo().getWorldName()+"/minecraftia.sgcp");
		try {
			path.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public File getGlobalJSONFile() {
		Minecraft mc = ModLoader.getMinecraftInstance();
		File path = new File(mc.mcDataDir.getAbsolutePath(), "mods/sgc/sgcPlanets.sgcp");
		try {
			path.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public File getTemplateJSONFile() {
		Minecraft mc = ModLoader.getMinecraftInstance();
		File path = new File(mc.mcDataDir.getAbsolutePath(), "mods/sgc/sgcTemplatePlanets.sgcp");
		try {
			path.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * 
	 * @param json File to write
	 * @param path To write to
	 */
	public void writeJSONFile(JSONObject json, File path) {
		String serializedJSON = json.serialize();
		System.out.println("Writing: " + serializedJSON);
		FileOutputStream os;
		try {
			path.createNewFile();
			Writer w = new FileWriter(path);
			w.write(serializedJSON);
			w.flush();
		} catch (FileNotFoundException e) {
			System.out.println("File was not found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
