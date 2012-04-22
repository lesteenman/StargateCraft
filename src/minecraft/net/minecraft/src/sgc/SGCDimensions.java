package net.minecraft.src.sgc;

public class SGCDimensions {
	
	public static WorldProviderSGCBase getProviderForClassName(String className) {
		return new WorldProviderSGCTest("P5C8T-Test");
	}
	
	public static SGCDimensionModel getModelForAddress(String address) {
		SGCDimensionModel dimensionModel = new SGCDimensionModel();
		dimensionModel.setAddress("AadEdeSbvK");
		dimensionModel.setDimensionID(8);
		dimensionModel.setName("P3G5C-A231D");
		dimensionModel.setRandomSeed("RandomSeed");
		return dimensionModel;
	}
	
}
