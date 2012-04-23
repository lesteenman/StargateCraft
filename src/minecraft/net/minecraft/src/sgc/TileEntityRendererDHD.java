package net.minecraft.src.sgc;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraft.src.mod_SGC;

public class TileEntityRendererDHD extends TileEntitySpecialRenderer{

	public ModelDHD model;
	
	public TileEntityRendererDHD() {
		model = new ModelDHD();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1,
			double d2, float f) {
		if(tileentity instanceof TileEntityDHD)
		{
			renderDHD((TileEntityDHD)tileentity, d, d1, d2, f);
		}
		else
		{
			System.out.println("Tried to use DHD Renderer to render a Tile Entity that isn't actually a DHD.");
		}
	}
	
	private void renderDHD(TileEntityDHD entity, double x, double y, double z, float f)
	{
		//bindTextureByName(mod_SGC.modFolder + "/images/dhd.png");
		bindTextureByName("sgc/dhd.png");
		GL11.glPushMatrix();
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        //GL11.glColor4f(0.278431F, 0.266667F, 0.329412F, 1.0F);
        GL11.glTranslatef((float)x + 0.5f, (float)y + 2.3f, (float)z + 0.5f);
        GL11.glScalef(0.25F, -0.15F, -0.25F);
        //GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        //GL11.glRotatef(90, -1, 0, 0);
        //GL11.glRotatef(22.5f, 0, 0, 1);
        
        model.render();
		
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
