package net.minecraft.src.sgc;

import java.io.*;
import java.util.Random;
import java.util.Vector;

import org.xml.sax.InputSource;

import net.minecraft.client.Minecraft;
import net.minecraft.src.DimensionAPI;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;

public class SGCDimensions {

	public static WorldProviderSGCBase getProviderForClassName(String className) {
		System.out.println("Trying to get a provider for " + className);
		WorldProviderSGCBase p = null;
		try {
			Class providerClass = Class.forName(className);
			p = (WorldProviderSGCBase) providerClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
				
		return p;
	}

	public static final String minecraftiaAddress = "abcdef";
	
	public Vector<Integer> getStargateLocation(String name) {
		SGCJSON sjr = SGCJSON.getInstance();
		JSONObject json = sjr.getJSONObject(sjr.getWorldJSONFile());
		if (json == null)
			return null;

		JSONObject planet = json.getJSONObject(name);
		if (planet != null) {
			int x = planet.getInt("sgX");
			int y = planet.getInt("sgY");
			int z = planet.getInt("sgZ");
			Vector<Integer> v = new Vector<Integer>();
			v.add(x);
			v.add(y);
			v.add(z);
			return v;
		}
		return null;
	}

	public void setStargateLocation(String name, Vector<Integer> stargateLocation) {
		SGCJSON sjr = SGCJSON.getInstance();
		JSONObject worldJson = sjr.getJSONObject(sjr.getWorldJSONFile());
		
		if (worldJson == null)
			worldJson = new JSONObject(false);

		//Check if this save had already created a local file and if not, do so,
		//with Minecraftia's seed in it.
		if (!worldJson.allKeys().contains("minecraftiaSeed")) {
			worldJson.set("minecraftiaSeed", ""+ModLoader.getMinecraftInstance().theWorld.getWorldInfo().getSeed());
		}
		
		if (getStargateLocation(name) == null) {
			JSONObject p = new JSONObject(false);
			worldJson.set(name, p);
			sjr.writeJSONFile(worldJson, sjr.getWorldJSONFile());
		}
		JSONObject minecraftia = worldJson.getJSONObject(name);
		minecraftia.set("sgX", (int)stargateLocation.get(0));
		minecraftia.set("sgY", (int)stargateLocation.get(1));
		minecraftia.set("sgZ", (int)stargateLocation.get(2));
		worldJson.set(name, minecraftia);

		sjr.writeJSONFile(worldJson, sjr.getWorldJSONFile());
	}

	/**
	 * Returns an SGCDimensionModel with all the information of that dimension, as found in 
	 * the global JSON.
	 * @param address
	 * @return the model
	 */
	public static SGCDimensionModel getModelForAddress(String address) {

		SGCJSON jsr = SGCJSON.getInstance();
		JSONObject json = jsr.getJSONObject(jsr.getGlobalJSONFile());
		JSONObject jsonModel;
		System.out.println("Checking  if " + address + " is equal to " + minecraftiaAddress);
		if ((json.allKeys() == null || !json.allKeys().contains(address)) && !address.equals(minecraftiaAddress)) {
			if (!addressValid(address))
				return null;
			
			System.out.println("And now making a dimensionModel for it");
			
			createJSONForAddress(address);

			SGCDimensionModel m = getModelForAddress(address);
			WorldProviderSGCBase p = new WorldProviderSGCTest();
			p.setName(m.getName());
			p.setDimensionModel(m);
			DimensionAPI.registerDimension(p);
		}

		json = jsr.getJSONObject(jsr.getGlobalJSONFile());
		JSONObject p = json.getJSONObject(address);
		
		SGCDimensionModel dimensionModel = new SGCDimensionModel();

		JSONObject templateJson = jsr.getJSONObject(jsr.getTemplateJSONFile());
		if (address.equals(minecraftiaAddress)) {
			JSONObject planetJson = jsr.getJSONObject(jsr.getWorldJSONFile());
			
			dimensionModel.setAddress(address);
			dimensionModel.setDimensionID(0);
			dimensionModel.setName("Minecraftia");
			dimensionModel.setRandomSeed(Long.valueOf(planetJson.getString("minecraftiaSeed")));
		} else {
			dimensionModel.setAddress(address);
			dimensionModel.setDimensionID(p.getInt("id"));
			dimensionModel.setName(p.getString("name"));
			dimensionModel.setWorldProvider(p.getString("worldProvider"));
			dimensionModel.setRandomSeed(Long.parseLong(p.getString("seed")));
		}
		return dimensionModel;
	}
	
	public static String getAddressForName(String name) {
		String address = null;
		SGCJSON jsr = SGCJSON.getInstance();
		JSONObject json = jsr.getJSONObject(jsr.getGlobalJSONFile());
		
		for (String key:json.allKeys()) {
			if (json.get(key)!= null && json.get(key) instanceof JSONObject && json.getJSONObject(key).getString("name").equals(name))
				return json.getJSONObject(key).getString("address");
		}
		
		return address;
	}

	private static boolean addressValid(String address) {
		return true;
	}

	public static void createJSONForAddress(String address) {
		System.out.println("Creating a JSON file for address " + address);
		
		SGCJSON jsr = SGCJSON.getInstance();
		JSONObject globalJSON = jsr.getJSONObject(jsr.getGlobalJSONFile());
		
		if (globalJSON.get("maxID") == null)
			globalJSON.set("maxID", 5);
		
		JSONObject templateJson = jsr.getJSONObject(jsr.getTemplateJSONFile());
		if (templateJson.allKeys() != null && templateJson.allKeys().contains(address)) {
			System.out.println("It was a template so creating the planet from that");
			// Was a template, use that template to create the new model!
			JSONObject planet = templateJson.getJSONObject(address);
			
			JSONObject newModel = new JSONObject(false);
			globalJSON.set(address, newModel);
			
			newModel.set("name", planet.getString("name"));
			newModel.set("id", globalJSON.getInt("maxID") + 1);
			globalJSON.set("maxID", globalJSON.getInt("maxID") + 1);
			
			String provider = planet.getString("worldProvider");
			try {
				Class.forName(provider);
			} catch (ClassNotFoundException e) {
				System.out.println("Template contained an invalid worldProvider: " + provider);
				e.printStackTrace();
			}
			
			newModel.set("worldProvider", provider);

			//If the template contains a seed, use that; Else generate one from the address.
			Long seed = (new Random()).nextLong();
			try {
				seed = Long.parseLong(planet.getString("seed"));
			} catch (NumberFormatException e) {
				if (!MathHelper.stringNullOrLengthZero(address)) {
					try {
						long l1 = Long.parseLong(address);

						if (l1 != 0L) {
							seed = l1;
						}
					} catch (NumberFormatException numberformatexception) {
						seed = (long) address.hashCode();
					}
				}
			}
			newModel.set("seed", seed);
		} else {
			System.out.println("No similar template found so let's make a new one!");
			JSONObject newModel = new JSONObject(false);
			globalJSON.set(address, newModel);
			
			newModel.set("name", SGCHelper.nameForAddress(address));
			newModel.set("id", globalJSON.getInt("maxID") + 1);
			globalJSON.set("maxID", globalJSON.getInt("maxID") + 1);
			String[] providers = templateJson.getStringArray("worldProviders");
			newModel.set("worldProvider", providers[(int) (Math.random() * providers.length)]);

			Long seed = (new Random()).nextLong();
			System.out.println("Seed1: " + seed);
			if (!MathHelper.stringNullOrLengthZero(address)) {
				try {
					long l1 = Long.parseLong(address);

					if (l1 != 0L) {
						seed = l1;
						System.out.println("Seed2: " + seed);
					}
				} catch (NumberFormatException numberformatexception) {
					seed = (long) address.hashCode();
					System.out.println("Seed3: " + seed);
				}
			}
			newModel.set("seed", seed);
			System.out.println("Set the seed, is now " + newModel.get("seed"));
		}
		
		SGCJSON.getInstance().writeJSONFile(globalJSON, jsr.getGlobalJSONFile());
	}

	/**
	 * Registers all planets in the global JSON with the Dimension API
	 */
	public void registerPlanets() {
		SGCJSON jsr = SGCJSON.getInstance();
		
		JSONObject globalJson = jsr.getJSONObject(jsr.getGlobalJSONFile());
		for (String key:globalJson.allKeys()) {
			if (globalJson.get(key) instanceof JSONObject) {
				System.out.println("Registering address " + key);
				SGCDimensionModel m = getModelForAddress(key);
				WorldProviderSGCBase p = new WorldProviderSGCTest();
				p.setName(m.getName());
				p.setDimensionModel(m);
				DimensionAPI.registerDimension(p);
			}
		}
	}

}
