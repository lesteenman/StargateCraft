package net.minecraft.src.sgc;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;

public class SGCBlocks {

	public static BlockStargate blockStargate;
	public static BlockPortalEventHorizon blockEventHorizon;
	
	public static final int stargateBlockID = 220;
	public static final int eventHorizonBlockId = 221;
	
	public SGCBlocks() {
		blockStargate = new BlockStargate(stargateBlockID, 37);
		blockStargate.setBlockName("StargateBlock");
		blockEventHorizon = new BlockPortalEventHorizon(eventHorizonBlockId, 38, Material.portal);
		
		ModLoader.registerBlock(blockStargate);
		ModLoader.registerBlock(blockEventHorizon);
		
		ModLoader.addName(blockStargate, "StargateBlock");
		ModLoader.addRecipe(new ItemStack(blockStargate, 4), new Object[]{
		    "DD", "DD", "DD", 
		    'D', Block.dirt
		});
	}
}
