package net.minecraft.src.sgc.blocks;
import javax.jws.Oneway;

import net.minecraft.src.*;
import net.minecraft.src.sgc.SGCBlocks;
import net.minecraft.src.sgc.SGCDimensionModel;
import net.minecraft.src.sgc.SGCDimensions;
import net.minecraft.src.sgc.WorldProviderSGCBase;

public class BlockStargate extends BlockObsidian {

	public BlockStargate(int par1, int par2) {
		super(par1, par2);
	}
	
	@Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
    	/*if (world.getBlockId(x, y + 1, z) == 0) {
    		world.setBlock(x, y + 1, z, SGCBlocks.eventHorizonBlockId);
    		BlockPortalEventHorizon eventHorizon = SGCBlocks.blockEventHorizon;
    		
    		SGCDimensionModel model = SGCDimensions.getModelForAddress("AAAAAA");
    		WorldProviderSGCBase provider = model.getWorldProvider();
    		provider.setDimensionModel(model);
    	
    		//dimensionModel.setWorldProvider(provider);
    		System.out.println("Gave the event horizon a model");
    		System.out.println("Event horizon's model dimension ID: " + model.getDimensionID());
    		eventHorizon.setDimensionModel(model);
    		return true;
    	}*/
    	return false;
    }
	
}
