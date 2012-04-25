package net.minecraft.src.sgc;

import java.util.Random;
import java.util.Vector;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TeleporterCustom;
import net.minecraft.src.World;
import net.minecraft.src.mod_SGC;

public class SGCTeleporter extends TeleporterCustom {

	private int heightLimit;
	private Random random = new Random();
	private String targetName;
	
	public SGCTeleporter(String targetName) {
		super(SGCBlocks.stargateBlockID, SGCBlocks.eventHorizonBlockId);
		this.targetName = targetName;
		heightLimit = 127;
	}

	public boolean placeInExistingPortal(World world, Entity entity)
    {
		System.out.println("Placing in existing portal");
		Vector<Integer> target = mod_SGC.dimensions.getStargateLocation(targetName); 
		if  (target != null) {
			int i = target.get(0);	int j = target.get(1);	int k = target.get(2);
			entity.setLocationAndAngles(i, j + 1, k, entity.rotationYaw, 0.0F);
            entity.motionX = entity.motionY = entity.motionZ = 0.0D;
			return true;
		} else {
			createPortal(world, entity);
			target = mod_SGC.dimensions.getStargateLocation(targetName); 
			int i = target.get(0);	int j = target.get(1);	int k = target.get(2);
			entity.setLocationAndAngles(i, j + 1, k, entity.rotationYaw, 0.0F);
            entity.motionX = entity.motionY = entity.motionZ = 0.0D;
			return true;
		}
    }

    /**
     * Create a new portal near an entity.
     */
    public boolean createPortal(World world, Entity entity)
    {
		System.out.println("Creating the portal");
        byte byte0 = 16;
        double d = -1D;
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        
        Vector<Integer> v = SGCBlocks.blockStargate.generateStargateAround(world, i, j, k, true);
        mod_SGC.dimensions.setStargateLocation(targetName, v);

        return true;
    }
}
