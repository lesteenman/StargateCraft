package net.minecraft.src.sgc;

/**
 * Container class used to store all information regarding this planet/dimension
 * @author dolor
 *
 */

public class SGCDimensionModel {

	private String name;
	private String address;
	private int dimensionID;
	private long randomSeed;
	private String worldProvider;

	/**
	 * 
	 * @return The name of the planet this model represents
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 
	 * @param The name of the planet this model represent
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the address
	 * @ensure address in string format
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 * Addresses need to be formatted as one character per glyph, starting with all
	 * lower case letters and then all upper case letters.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the dimensionID
	 */
	public int getDimensionID() {
		return dimensionID;
	}

	/**
	 * @param dimensionID the dimensionID to set
	 */
	public void setDimensionID(int dimensionID) {
		this.dimensionID = dimensionID;
	}

	/**
	 * @return the randomSeed
	 */
	public long getRandomSeed() {
		return randomSeed;
	}

	/**
	 * @param randomSeed the randomSeed to set
	 */
	public void setRandomSeed(Long randomSeed) {
		this.randomSeed = randomSeed;
	}

	/**
	 * @return the worldProvider
	 */
	public WorldProviderSGCBase getWorldProvider() {
		System.out.println("Going to return a WorldProvider, provider=" + worldProvider + ", name=" + name);
		WorldProviderSGCBase p = SGCDimensions.getProviderForClassName(worldProvider);
		p.setName(name);
		p.setDimensionModel(this);
		return p;
	}

	/**
	 * @param worldProvider the worldProvider to set
	 */
	public void setWorldProvider(String worldProvider) {
		this.worldProvider = worldProvider;
	}
	
}
