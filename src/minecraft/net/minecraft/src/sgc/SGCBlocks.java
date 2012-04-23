package net.minecraft.src.sgc;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.sgc.blocks.*;

public class SGCBlocks {

	public static BlockStargate blockStargate;
	public static BlockPortalEventHorizon blockEventHorizon;
	public static BlockDHD blockDHD;
	
	public static final int stargateBlockID = 220;
	public static final int eventHorizonBlockId = 221;
	public static final int dhdBlockId = 222;
	
	public SGCBlocks() {
		blockStargate = new BlockStargate(stargateBlockID, 37);
		blockStargate.setBlockName("StargateBlock");
		blockEventHorizon = new BlockPortalEventHorizon(eventHorizonBlockId, 38, Material.portal);
		blockDHD = new BlockDHD(dhdBlockId, Material.wood);
		blockDHD.setBlockName("DHD");
		
		ModLoader.registerBlock(blockStargate);
		ModLoader.registerBlock(blockEventHorizon);
		ModLoader.registerBlock(blockDHD);
		
		ModLoader.addName(blockStargate, "StargateBlock");
		ModLoader.addRecipe(new ItemStack(blockStargate, 4), new Object[]{
		    "DD", "DD", "DD", 
		    'D', Block.dirt
		});
		
		ModLoader.addName(blockDHD, "DHD");
		ModLoader.addRecipe(new ItemStack(blockDHD, 1), new Object[]{
			"DD", "DD", 
			'D', Block.dirt
		});
	}
}
