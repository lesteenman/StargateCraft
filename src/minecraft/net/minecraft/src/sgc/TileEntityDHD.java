package net.minecraft.src.sgc;

import java.util.Vector;

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
			System.out.println("Dialing " + address);
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
		this.stargateLocation = stargateLocation;
		
		if (mod_SGC.dimensions.getMinecraftiaStargateLocation() == null)
			mod_SGC.dimensions.setMinecraftiaStargateLocation(stargateLocation);
	}
}
