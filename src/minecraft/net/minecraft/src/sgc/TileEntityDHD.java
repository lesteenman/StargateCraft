package net.minecraft.src.sgc;

import java.util.Vector;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.mod_SGC;

public class TileEntityDHD extends net.minecraft.src
.TileEntity{
	
	private String address;
	private Vector<Integer> stargateLocation;
	
	public TileEntityDHD() {
		address = "";
		System.out.println("Tile Entity Initialized");
	}
	
	public boolean buttonPressed(int button) {
		if (address.length() < 6) {
			address = address + mod_SGC.glyphs[button - 1];
			System.out.println("Total address is now " + address);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean dial() {
		if (address.length() == 6) {
			
			//Can't dial minecraftia if it's on it
			int currentID = ModLoader.getMinecraftInstance().thePlayer.dimension;
			if (currentID < 1 && address.equals(mod_SGC.dimensions.minecraftiaAddress))
					return false;
			
			System.out.println("Dialing " + address);
			SGCDimensionModel model = mod_SGC.dimensions.getModelForAddress(address);
			
			//Can't dial the current planet
			if (currentID == model.getDimensionID())
				return false;
				
			if (model == null)
				System.out.println("Model was still null!");
			else {
				System.out.println("Wow, actually found a model with name " + model.getName());
				SGCBlocks.blockEventHorizon.setDimensionModel(model);
				World world = ModLoader.getMinecraftInstance().theWorld;
				world.setBlock(stargateLocation.get(0), stargateLocation.get(1) + 1, stargateLocation.get(2), SGCBlocks.eventHorizonBlockId);
				world.setBlock(stargateLocation.get(0), stargateLocation.get(1) + 2, stargateLocation.get(2), SGCBlocks.eventHorizonBlockId);
				world.setBlock(stargateLocation.get(0) - 1, stargateLocation.get(1) + 1, stargateLocation.get(2), SGCBlocks.eventHorizonBlockId);
				world.setBlock(stargateLocation.get(0) - 1, stargateLocation.get(1) + 2, stargateLocation.get(2), SGCBlocks.eventHorizonBlockId);
				world.setBlock(stargateLocation.get(0) + 1, stargateLocation.get(1) + 1, stargateLocation.get(2), SGCBlocks.eventHorizonBlockId);
				world.setBlock(stargateLocation.get(0) + 1, stargateLocation.get(1) + 2, stargateLocation.get(2), SGCBlocks.eventHorizonBlockId);
			}
			address = "";
			
			
			return true;
		} else
			return false;
	}
	
	public String toString() {
		return String.format("x:%d y:%d z:%d", xCoord, yCoord, zCoord);
	}

	/**
	 * @return the stargateLocation
	 */
	public Vector<Integer> getStargateLocation() {
		return stargateLocation;
	}

	/**
	 * @param stargateLocation the stargateLocation to set
	 */
	public void setStargateLocation(Vector<Integer> stargateLocation) {
		System.out.println("Set the stargate location in TileEntityDHD");
		this.stargateLocation = stargateLocation;
		
		String planetName;
		WorldProvider wp = ModLoader.getMinecraftInstance().theWorld.worldProvider;
		if (wp.getClass().isAssignableFrom(WorldProviderSGCBase.class)) {
			planetName = ((WorldProviderSGCBase)wp).getDimensionModel().getName();
		} else {
			planetName = "Minecraftia";
		}
		
		if (mod_SGC.dimensions.getStargateLocation(planetName) == null)
			mod_SGC.dimensions.setStargateLocation(planetName, stargateLocation);
	}
	
	/**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	Vector<Integer> loc = new Vector<Integer>();
    	loc.add(par1NBTTagCompound.getInteger("gateX"));
    	loc.add(par1NBTTagCompound.getInteger("gateY"));
    	loc.add(par1NBTTagCompound.getInteger("gateZ"));
        super.readFromNBT(par1NBTTagCompound);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	par1NBTTagCompound.setInteger("gateX", stargateLocation.get(0));
    	par1NBTTagCompound.setInteger("gateY", stargateLocation.get(1));
    	par1NBTTagCompound.setInteger("gateZ", stargateLocation.get(2));
        super.writeToNBT(par1NBTTagCompound);
    }
}
