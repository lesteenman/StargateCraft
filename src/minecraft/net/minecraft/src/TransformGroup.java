package net.minecraft.src;

public abstract class TransformGroup
{
	public abstract double getWeight();
	public abstract Vec3D doTransformation(PositionTransformVertex vertex);
}
