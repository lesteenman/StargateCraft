package net.minecraft.src.sgc;

public class TileEntityDHD extends net.minecraft.src.TileEntity{
	
	public TileEntityDHD() {
		System.out.println("Tile Entity Initialized");
	}
	
	public String toString() {
		return String.format("x:%d y:%d z:%d", xCoord, yCoord, zCoord);
	}
}
