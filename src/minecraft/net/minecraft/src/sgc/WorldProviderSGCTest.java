package net.minecraft.src.sgc;

public class WorldProviderSGCTest extends WorldProviderSGCBase {

	public WorldProviderSGCTest(String name) {
		super(name);
	}

	public WorldProviderSGCTest() {
		super();
	}
	
	@Override
	public void registerWorldChunkManager() {
		System.out.println("REGISTERING CHUNK MANAGER");
		worldChunkMgr = new WorldChunkManagerSGCBase(worldObj, dimensionModel);
	}

}
