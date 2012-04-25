package net.minecraft.src.sgc;

import net.minecraft.src.WorldProviderBase;

public abstract class WorldProviderSGCBase extends WorldProviderBase {

	protected SGCDimensionModel dimensionModel;
	private String name;
	private int dimensionID;
	
	/**
	 * Represents a dimension or 'Planet'
	 * @param name The name of this planet; Used to save this particular planet
	 */
	public WorldProviderSGCBase(String name) {
		super();
		this.name = name;
	}
	
	public WorldProviderSGCBase() {
		super();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the model for the dimension/planet this worldprovider represents
	 * @param model
	 */
	public void setDimensionModel(SGCDimensionModel model) {
		this.dimensionModel = model;
		this.dimensionID = model.getDimensionID();
		this.name = model.getName();
	}
	
	/**
	 * Gets the model for the dimension/planet this worldprovider represents
	 * @return model
	 */
	public SGCDimensionModel getDimensionModel() {
		return this.dimensionModel;
	}

	@Override
	public int getDimensionID() {
		return dimensionID;
	}
	
	public String getSaveFolderName()
	{
		return (new StringBuilder()).append(name).toString();
	}
	
	@Override
	public void registerWorldChunkManager() {
		worldChunkMgr = new WorldChunkManagerSGCBase(worldObj, dimensionModel);
	}
}
