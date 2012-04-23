package net.minecraft.src.sgc;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRendererTurbo;
//Exported java file
//Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelDHD extends ModelBase
{
	public ModelDHD()
	{
		Pedestal = new ModelRendererTurbo(this, 0, 0, 64, 32);
		Pedestal.addBox(0F, 0F, 0F, 8, 10, 8, 0);
		Pedestal.setRotationPoint(-4F, 14F, -4F);
		Pedestal.rotateAngleX = 0F;
		Pedestal.rotateAngleY = 0F;
		Pedestal.rotateAngleZ = 0F;
		DHDPanel = new ModelRendererTurbo(this, 0, 0, 64, 32);
		DHDPanel.addBox(0F, 14F, -4F, 10, 3, 10, 0);
		DHDPanel.setRotationPoint(-5F, -1F, -5F);
		DHDPanel.rotateAngleX = 0.2792527F;
		DHDPanel.rotateAngleY = 0F;
		DHDPanel.rotateAngleZ = 0F;
		DialRing = new ModelRendererTurbo(this, 6, 18, 64, 32);
		DialRing.addBox(0F, 0F, 0F, 7, 3, 7, 0);
		DialRing.setRotationPoint(-3.5F, 12F, -4F);
		DialRing.rotateAngleX = 0.2792527F;
		DialRing.rotateAngleY = 0F;
		DialRing.rotateAngleZ = 0F;
		DialButton = new ModelRendererTurbo(this, 0, 18, 64, 32);
		DialButton.addBox(-1F, 0.5F, -1.5F, 2, 1, 1, 0);
		DialButton.setRotationPoint(0F, 10F, 0F);
		DialButton.rotateAngleX = 0.2792527F;
		DialButton.rotateAngleY = 0F;
		DialButton.rotateAngleZ = 0F;
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Pedestal.render(f5);
		DHDPanel.render(f5);
		DialRing.render(f5);
		DialButton.render(f5);
	}
	
	public void render() {
		float f = 0.625f;
		Pedestal.render(f);
		DHDPanel.render(f);
		DialRing.render(f);
		DialButton.render(f);
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5);
	}
	
	//fields
	public ModelRendererTurbo Pedestal;
	public ModelRendererTurbo DHDPanel;
	public ModelRendererTurbo DialRing;
	public ModelRendererTurbo DialButton;
}
