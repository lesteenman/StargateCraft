package net.minecraft.src;

import java.util.List;

public interface ISpecialTeleportation
{
    public abstract WorldProviderBase getDimension();

    public abstract Teleporter getTeleporter();

    public abstract String getEnteringMessage();

    public abstract String getLeavingMessage();

    public abstract boolean isPortalImmediate();
	
	public abstract boolean displayPortalOverlay();
	
	public abstract int getOverlayTexture();
	
	public abstract String getOverlayTextureOverride();

    public abstract List canTeleportFromDimension();

    public abstract int returnsPlayerToDimension();

    public abstract double getDistanceRatio();

    public abstract int getPortalDelay();

    public abstract Achievement triggerAchievement();
	
}
