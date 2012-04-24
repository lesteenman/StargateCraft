package net.minecraft.src.sgc;

import net.minecraft.src.mod_SGC;

public class TileEntityDHD extends net.minecraft.src.TileEntity{
	
	private String address;
	
	public TileEntityDHD() {
		address = "";
		System.out.println("Tile Entity Initialized");
	}
	
	public boolean buttonPressed(int button) {
		if (address.length() == 6) {
			address = address + mod_SGC.glyphs[button - 1];
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
}
