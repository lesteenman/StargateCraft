package net.minecraft.src.sgc;

public class WorldProviderSGCTest extends WorldProviderSGCBase {

	public WorldProviderSGCTest(String name, int id) {
		super(name, id);
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
