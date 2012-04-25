package net.minecraft.src.sgc;

import java.io.*;
import java.util.Vector;

import org.xml.sax.InputSource;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;

public class SGCDimensions {
	
	public static WorldProviderSGCBase getProviderForClassName(String className) {
		return new WorldProviderSGCTest("P5C8T-Test");
	}
	
	public Vector<Integer> getMinecraftiaStargateLocation() {
		SGCJSONProcessor sjr = SGCJSONProcessor.getInstance();
		JSONObject json = sjr.getJSONObject(sjr.getWorldJSONFile());
		if (json == null)
			return null;
		
		JSONObject minecraftia = json.getJSONObject("minecraftia");
		if (minecraftia != null) {
			int x = minecraftia.getInt("sgX");
			int y = minecraftia.getInt("sgY");
			int z = minecraftia.getInt("sgZ");
			Vector<Integer> v = new Vector<Integer>();
			v.add(x); v.add(y); v.add(z);
			return v;
		}
		return null;
	}
	
	public void setMinecraftiaStargateLocation(Vector<Integer> stargateLocation) {
		SGCJSONProcessor sjr = SGCJSONProcessor.getInstance();
		JSONObject json = sjr.getJSONObject(sjr.getWorldJSONFile());
		if (json == null)
			json = new JSONObject(false);
		
		if (getMinecraftiaStargateLocation() == null) {
			JSONObject minecraftia = new JSONObject(false);
			json.set("minecraftia", minecraftia);
			sjr.writeJSONFile(json, sjr.getWorldJSONFile());
		}
		JSONObject minecraftia = json.getJSONObject("minecraftia");
		minecraftia.set("sgX", stargateLocation.get(0));
		minecraftia.set("sgY", stargateLocation.get(1));
		minecraftia.set("sgZ", stargateLocation.get(2));
		minecraftia.set("address", "abcdef");
		json.set("minecraftia", minecraftia);
		System.out.println("Should be about to write the JSON to " + sjr.getWorldJSONFile().getAbsolutePath() + " > " + json.serialize());
		sjr.writeJSONFile(json, sjr.getWorldJSONFile());
	}
	
	public static SGCDimensionModel getModelForAddress(String address) {
		
		SGCJSONProcessor jsr = SGCJSONProcessor.getInstance();
		JSONObject json = jsr.getJSONObject(jsr.getWorldJSONFile());
		//TODO Dit verder implementeren
		
		SGCDimensionModel dimensionModel = new SGCDimensionModel();
		dimensionModel.setAddress("AadEdeSbvK");
		dimensionModel.setDimensionID(8);
		dimensionModel.setName("P3G5C-A231D");
		dimensionModel.setRandomSeed(Long.decode("RandomSeed"));
		return dimensionModel;
	}
	
	public SGCDimensionModel createModelForAddress(String address) {
		SGCDimensionModel model = new SGCDimensionModel();
		
		SGCJSONProcessor jsr = SGCJSONProcessor.getInstance();
		JSONObject json = jsr.getJSONObject(jsr.getTemplateJSONFile());
		if (json.allKeys().contains(address)) {
			//Was a template, use that template to create the new model!
			JSONObject planet = json.getJSONObject(address);
		} else {
			model.setAddress(address);
			
		}
		
		return model;
	}
	
}
