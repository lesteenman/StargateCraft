package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public abstract class BlockPortalBase extends Block
	implements ISpecialTeleportation
{
    public BlockPortalBase(int i, int j, Material material)
    {
        super(i, j, material);
    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
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
    }

    public abstract WorldProviderBase getDimension();

    public abstract Teleporter getTeleporter();

    public abstract String getEnteringMessage();

    public abstract String getLeavingMessage();

    public boolean isPortalImmediate()
    {
        return false;
    }
	
	public boolean displayPortalOverlay()
	{
		return true;
	}

	public int getOverlayTexture()
	{
		return Block.portal.blockIndexInTexture;
	}
	
	public String getOverlayTextureOverride()
	{
		return "/terrain.png";
	}

    public List canTeleportFromDimension()
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add(Integer.valueOf(0));
        return arraylist;
    }

    public int returnsPlayerToDimension()
    {
        return 0;
    }

    public double getDistanceRatio()
    {
        return 1.0D;
    }

    public int getPortalDelay()
    {
        return 150;
    }

    public Achievement triggerAchievement()
    {
        return mod_SGC.sgAchievement;
    }
}
