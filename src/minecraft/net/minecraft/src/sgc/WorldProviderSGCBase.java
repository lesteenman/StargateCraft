package net.minecraft.src.sgc;

import net.minecraft.src.WorldProviderBase;

public abstract class WorldProviderSGCBase extends WorldProviderBase {

	private SGCDimensionModel dimensionModel;
	private String name;
	private int dimensionID;
	
	/**
	 * Represents a dimension or 'Planet'
	 * @param name The name of this planet; Used to save this particular planet
	 */
	public WorldProviderSGCBase(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the model for the dimension/planet this worldprovider represents
	 * @param model
	 */
	public void setDimensionModel(SGCDimensionModel model) {
		this.dimensionModel = model;
		this.dimensionID = model.getDimensionID();
		System.out.println("Set dimensionID to " + dimensionID);
	}

	@Override
	public int getDimensionID() {
		return dimensionID;
	}
	
	public String getSaveFolderName()
	{
		return (new StringBuilder()).append("PLANET-").append(name).toString();
	}
	
	@Override
	public void registerWorldChunkManager() {
		worldChunkMgr = new WorldChunkManagerSGCBase(worldObj, dimensionModel);
	}
	
}
