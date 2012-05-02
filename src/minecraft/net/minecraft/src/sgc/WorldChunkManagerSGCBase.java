package net.minecraft.src.sgc;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BiomeGenDesert;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.WorldProvider;

public class WorldChunkManagerSGCBase extends WorldChunkManager {
	
	private static String lastWorldName = "";

	/**
	 * Should construct a WorldChunkManager with the seed from the model instead of the default seed.
	 * @param par1World
	 * @param dimensionModel
	 */
    public WorldChunkManagerSGCBase(World par1World, SGCDimensionModel dimensionModel)
    {
    	super(dimensionModel.getRandomSeed(), par1World.getWorldInfo().getTerrainType());
    }

    /**
     * Returns the BiomeGenBase related to the x, z position on the world.
     */
    public BiomeGenBase getBiomeGenAt(int par1, int par2)
    {
    	Minecraft mc = ModLoader.getMinecraftInstance();
    	WorldProvider wp = mc.theWorld.worldProvider;
    	if (wp.getClass().isAssignableFrom(WorldProviderSGCBase.class)) {
        	String worldName = ((WorldProviderSGCBase)wp).getDimensionModel().getName();
        	if (!lastWorldName.equals(worldName))
        	    System.out.println("Generating in " + worldName);
    	}
        return BiomeGenBase.desert;
    }

    /**
     * Gets the list of valid biomes for the player to spawn in.
     */
    public List getBiomesToSpawnIn()
    {
    	List b = new ArrayList();
    	b.add(BiomeGenBase.desert);
        return b;
    }
}
