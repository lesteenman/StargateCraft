package net.minecraft.src.sgc.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Achievement;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockPortalBase;
import net.minecraft.src.DimensionAPI;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Teleporter;
import net.minecraft.src.World;
import net.minecraft.src.WorldProviderBase;
import net.minecraft.src.sgc.SGCDimensionModel;
import net.minecraft.src.sgc.SGCDimensions;
import net.minecraft.src.sgc.SGCTeleporter;
import net.minecraft.src.sgc.WorldProviderSGCBase;

/**
 * Used for the portal blocks of the stargate, or the Event Horizon.
 * Contains the dimension it is connected to, and teleports the player
 * to that dimension when necessary.
 * @author dolor
 *
 */

public class BlockPortalEventHorizon extends BlockPortalBase {
	
	private SGCDimensionModel dimensionModel;

	public BlockPortalEventHorizon(int i, int j, Material material) {
		super(i, j, material);
	}

    /*public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
    	System.out.println("Setting in dimension " + getDimension().getDimensionID());
		GuiIngame.currentPortal = null;
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (!world.isRemote)
            {
                DimensionAPI.setInPortal(getDimension(), ModLoader.getMinecraftInstance(), entity, this, this);
            }
			if (displayPortalOverlay() && DimensionAPI.isInPortal((EntityPlayerSP)entityplayer, blockID))
			{
				GuiIngame.currentPortal = this;
				entityplayer.setInPortal();
				if(entityplayer.timeInPortal >= 0.9F)
				{
					entityplayer.timeInPortal = 0.0F;
				}
			}
        }
    }*/

	/**
	 * Set to immediate because stargates teleport you instantly
	 */
	@Override
    public boolean isPortalImmediate()
    {
        return true;
    }
	
	public List canTeleportFromDimension()
    {
		int i = ModLoader.getMinecraftInstance().thePlayer.dimension;
        ArrayList arraylist = new ArrayList();
        arraylist.add(Integer.valueOf(i));
        return arraylist;
    }

	@Override
	public WorldProviderBase getDimension() {
		if (dimensionModel.getAddress().equals(SGCDimensions.minecraftiaAddress)) {
			return (WorldProviderBase) DimensionAPI.getProviderByDimension(0);
		} else {
			WorldProviderSGCBase provider = (WorldProviderSGCBase) DimensionAPI.getProviderByDimension(dimensionModel.getWorldProvider().getDimensionID());
			return provider;
		}
	}

	@Override
	public Teleporter getTeleporter() {
		return new SGCTeleporter(dimensionModel.getName());
	}

	@Override
	public String getEnteringMessage() {
		//TODO Try setting the seed right here
		System.out.println("Could actually set the seed here! Would set it to " + dimensionModel.getRandomSeed());

		/*SGCJSON jsr = SGCJSON.getInstance();
		JSONObject json = jsr.getJSONObject(jsr.getGlobalJSONFile());
		String address = mod_SGC.dimensions.getAddressForName(targetName);
		Long seed = Long.valueOf(json.getJSONObject(address).getString("seed"));
		System.out.println("Would now set the world to use seed " + seed);*/
		
		String enteringMessage = "Now traveling to " + dimensionModel.getName();
		return enteringMessage;
	}

	@Override
	public String getLeavingMessage() {
		String leavingMessage = "Now leaving " + dimensionModel.getName();
		return leavingMessage;
	}

	/**
	 * Used together with getOverlayTextureOverride() to define the
	 * texture of the portal block
	 */
	@Override
	public int getOverlayTexture()
	{
		return Block.portal.blockIndexInTexture;
	}
	
	/**
	 * Used together with getOverlayTexture() to define the
	 * texture of the portal block
	 */
	@Override
	public String getOverlayTextureOverride()
	{
		return "/terrain.png";
	}

	/**
	 * @return the dimensionModel representing the planet this event horizon is going to
	 */
	public SGCDimensionModel getDimensionModel() {
		return dimensionModel;
	}

	/**
	 * @param dimensionModel the dimensionModel to set representing the planet this event horizon
	 * 		  should go to
	 */
	public void setDimensionModel(SGCDimensionModel dimensionModel) {
		this.dimensionModel = dimensionModel;
	}

    /**
     * Return null it is walk-through
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int i)
    {
        return null;
    }

    public Achievement triggerAchievement()
    {
        return null;
    }
}
