package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import java.util.Random;

public class RenderGlobalCustom extends RenderGlobal
{
	public RenderGlobalCustom(Minecraft minecraft, RenderEngine renderengine)
	{
		super(minecraft, renderengine);
		renderEngine = renderengine;
		mc = minecraft;
		theWorld = mc.theWorld;
		starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList(starGLCallList, GL11.GL_COMPILE);
		renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator tessellator = Tessellator.instance;
        glSkyList = starGLCallList + 1;
        GL11.glNewList(glSkyList, GL11.GL_COMPILE);
        byte byte2 = 64;
        int i = 256 / byte2 + 2;
        float f = 16F;

        for (int j = -byte2 * i; j <= byte2 * i; j += byte2)
        {
            for (int l = -byte2 * i; l <= byte2 * i; l += byte2)
            {
                tessellator.startDrawingQuads();
                tessellator.addVertex(j + 0, f, l + 0);
                tessellator.addVertex(j + byte2, f, l + 0);
                tessellator.addVertex(j + byte2, f, l + byte2);
                tessellator.addVertex(j + 0, f, l + byte2);
                tessellator.draw();
            }
        }

        GL11.glEndList();
        glSkyList2 = starGLCallList + 2;
        GL11.glNewList(glSkyList2, GL11.GL_COMPILE);
        f = -16F;
        tessellator.startDrawingQuads();

        for (int k = -byte2 * i; k <= byte2 * i; k += byte2)
        {
            for (int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2)
            {
                tessellator.addVertex(k + byte2, f, i1 + 0);
                tessellator.addVertex(k + 0, f, i1 + 0);
                tessellator.addVertex(k + 0, f, i1 + byte2);
                tessellator.addVertex(k + byte2, f, i1 + byte2);
            }
        }

        tessellator.draw();
        GL11.glEndList();
	}
	/*
	public void changeWorld(World world)
    {
		theWorld = world;
		renderStars();
		super.changeWorld(world);
	}
*/
	public void renderSky(float par1)
    {
		World worldObj = mc.theWorld;
		if(!(worldObj.worldProvider instanceof WorldProviderBase))
		{
			super.renderSky(par1);
			return;
		}
		WorldProviderBase worldProvider = (WorldProviderBase)worldObj.worldProvider;
        if (worldProvider.renderEndSky())
        {
            GL11.glDisable(GL11.GL_FOG);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderHelper.disableStandardItemLighting();
            GL11.glDepthMask(false);
            renderEngine.bindTexture(renderEngine.getTexture("/misc/tunnel.png"));
            Tessellator tessellator = Tessellator.instance;

            for (int i = 0; i < 6; i++)
            {
                GL11.glPushMatrix();

                if (i == 1)
                {
                    GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 2)
                {
                    GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 3)
                {
                    GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 4)
                {
                    GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
                }

                if (i == 5)
                {
                    GL11.glRotatef(-90F, 0.0F, 0.0F, 1.0F);
                }

                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(0x181818);
                tessellator.addVertexWithUV(-100D, -100D, -100D, 0.0D, 0.0D);
                tessellator.addVertexWithUV(-100D, -100D, 100D, 0.0D, 16D);
                tessellator.addVertexWithUV(100D, -100D, 100D, 16D, 16D);
                tessellator.addVertexWithUV(100D, -100D, -100D, 16D, 0.0D);
                tessellator.draw();
                GL11.glPopMatrix();
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            return;
        }

        if (!worldProvider.func_48217_e())
        {
            return;
        }

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Vec3D vec3d = worldProvider.getSkyColor(worldObj, mc.renderViewEntity, par1);
        float f = (float)vec3d.xCoord;
        float f1 = (float)vec3d.yCoord;
        float f2 = (float)vec3d.zCoord;

        if (mc.gameSettings.anaglyph)
        {
            float f3 = (f * 30F + f1 * 59F + f2 * 11F) / 100F;
            float f4 = (f * 30F + f1 * 70F) / 100F;
            float f5 = (f * 30F + f2 * 70F) / 100F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }

        GL11.glColor3f(f, f1, f2);
        Tessellator tessellator1 = Tessellator.instance;
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor3f(f, f1, f2);
        GL11.glCallList(glSkyList);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.disableStandardItemLighting();
        float af[] = worldProvider.calcSunriseSunsetColors(worldObj.getCelestialAngle(par1), par1);

        if (af != null)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glPushMatrix();
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(MathHelper.sin(worldObj.getCelestialAngleRadians(par1)) >= 0.0F ? 0.0F : 180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
            float f6 = af[0];
            float f8 = af[1];
            float f11 = af[2];

            if (mc.gameSettings.anaglyph)
            {
                float f14 = (f6 * 30F + f8 * 59F + f11 * 11F) / 100F;
                float f17 = (f6 * 30F + f8 * 70F) / 100F;
                float f20 = (f6 * 30F + f11 * 70F) / 100F;
                f6 = f14;
                f8 = f17;
                f11 = f20;
            }

            tessellator1.startDrawing(6);
            tessellator1.setColorRGBA_F(f6, f8, f11, af[3]);
            tessellator1.addVertex(0.0D, 100D, 0.0D);
            int j = 16;
            tessellator1.setColorRGBA_F(af[0], af[1], af[2], 0.0F);

            for (int k = 0; k <= j; k++)
            {
                float f21 = ((float)k * (float)Math.PI * 2.0F) / (float)j;
                float f22 = MathHelper.sin(f21);
                float f23 = MathHelper.cos(f21);
                tessellator1.addVertex(f22 * 120F, f23 * 120F, -f23 * 40F * af[3]);
            }

            tessellator1.draw();
            GL11.glPopMatrix();
            GL11.glShadeModel(GL11.GL_FLAT);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glPushMatrix();
        double d = 1.0F - worldObj.getRainStrength(par1);
		if(!worldProvider.darkenSkyDuringRain())
		{
			d = 1.0D;
		}
        float f7 = 0.0F;
        float f9 = 0.0F;
        float f12 = 0.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, (float) d);
        GL11.glTranslatef(f7, f9, f12);
        GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(worldObj.getCelestialAngle(par1) * 360F, 1.0F, 0.0F, 0.0F);
        float f15 = 30F * worldProvider.setSunSize();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture(worldProvider.getSunTexture()));
        tessellator1.startDrawingQuads();
        tessellator1.addVertexWithUV(-f15, 100D, -f15, 0.0D, 0.0D);
        tessellator1.addVertexWithUV(f15, 100D, -f15, 1.0D, 0.0D);
        tessellator1.addVertexWithUV(f15, 100D, f15, 1.0D, 1.0D);
        tessellator1.addVertexWithUV(-f15, 100D, f15, 0.0D, 1.0D);
        tessellator1.draw();
        f15 = 20F * worldProvider.setMoonSize();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture(worldProvider.getMoonTexture()));
        int i18 = worldObj.getMoonPhase(par1);
        int l = i18 % 4;
        int i1 = (i18 / 4) % 2;
        float f24 = (float)(l + 0) / 4F;
        float f25 = (float)(i1 + 0) / 2.0F;
        float f26 = (float)(l + 1) / 4F;
        float f27 = (float)(i1 + 1) / 2.0F;
        tessellator1.startDrawingQuads();
        tessellator1.addVertexWithUV(-f15, -100D, f15, f26, f27);
        tessellator1.addVertexWithUV(f15, -100D, f15, f24, f27);
        tessellator1.addVertexWithUV(f15, -100D, -f15, f24, f25);
        tessellator1.addVertexWithUV(-f15, -100D, -f15, f26, f25);
        tessellator1.draw();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        float f18 = (float)(worldProvider.getStarBrightness(worldObj, par1) * d);

        if (f18 > 0.0F)
        {
            GL11.glColor4f(f18, f18, f18, f18);
            GL11.glCallList(starGLCallList);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.0F, 0.0F, 0.0F);
        d = mc.thePlayer.getPosition(par1).yCoord - worldObj.getSeaLevel();

        if (worldProvider.renderVoidFog() && d < 0.0D)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12F, 0.0F);
            GL11.glCallList(glSkyList2);
            GL11.glPopMatrix();
            float f10 = 1.0F;
            float f13 = -(float)(d + 65D);
            float f16 = -f10;
            float f19 = f13;
            tessellator1.startDrawingQuads();
            tessellator1.setColorRGBA_I(0, 255);
            tessellator1.addVertex(-f10, f19, f10);
            tessellator1.addVertex(f10, f19, f10);
            tessellator1.addVertex(f10, f16, f10);
            tessellator1.addVertex(-f10, f16, f10);
            tessellator1.addVertex(-f10, f16, -f10);
            tessellator1.addVertex(f10, f16, -f10);
            tessellator1.addVertex(f10, f19, -f10);
            tessellator1.addVertex(-f10, f19, -f10);
            tessellator1.addVertex(f10, f16, -f10);
            tessellator1.addVertex(f10, f16, f10);
            tessellator1.addVertex(f10, f19, f10);
            tessellator1.addVertex(f10, f19, -f10);
            tessellator1.addVertex(-f10, f19, -f10);
            tessellator1.addVertex(-f10, f19, f10);
            tessellator1.addVertex(-f10, f16, f10);
            tessellator1.addVertex(-f10, f16, -f10);
            tessellator1.addVertex(-f10, f16, -f10);
            tessellator1.addVertex(-f10, f16, f10);
            tessellator1.addVertex(f10, f16, f10);
            tessellator1.addVertex(f10, f16, -f10);
            tessellator1.draw();
        }

        if (worldProvider.isSkyColored())
        {
            GL11.glColor3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
        }
        else
        {
            GL11.glColor3f(f, f1, f2);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -(float)(d - 16D), 0.0F);
        GL11.glCallList(glSkyList2);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
		worldProvider.renderCelestialObjects(par1);
    }
	
	public void renderClouds(float par1)
    {
		World worldObj = mc.theWorld;
		if(!(worldObj.worldProvider instanceof WorldProviderBase) || (((WorldProviderBase)worldObj.worldProvider).renderClouds()))
		{
			super.renderClouds(par1);
		}
	}
	
	public void renderStars()
    {
		Random random = new Random(10842L);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();

		for (int i = 0; i < 1500; i++)
		{
			double d = random.nextFloat() * 2.0F - 1.0F;
			double d1 = random.nextFloat() * 2.0F - 1.0F;
			double d2 = random.nextFloat() * 2.0F - 1.0F;
			double d3 = 0.25F + random.nextFloat() * 0.25F;
			double d4 = d * d + d1 * d1 + d2 * d2;

			if (d4 >= 1.0D || d4 <= 0.01D)
			{
				continue;
			}

			d4 = 1.0D / Math.sqrt(d4);
			d *= d4;
			d1 *= d4;
			d2 *= d4;
			double d5 = d * 100D;
			double d6 = d1 * 100D;
			double d7 = d2 * 100D;
			double d8 = Math.atan2(d, d2);
			double d9 = Math.sin(d8);
			double d10 = Math.cos(d8);
			double d11 = Math.atan2(Math.sqrt(d * d + d2 * d2), d1);
			double d12 = Math.sin(d11);
			double d13 = Math.cos(d11);
			double d14 = random.nextDouble() * Math.PI * 2D;
			double d15 = Math.sin(d14);
			double d16 = Math.cos(d14);

			for (int j = 0; j < 4; j++)
			{
				double d17 = 0.0D;
				double d18 = (double)((j & 2) - 1) * d3;
				double d19 = (double)((j + 1 & 2) - 1) * d3;
				double d20 = d17;
				double d21 = d18 * d16 - d19 * d15;
				double d22 = d19 * d16 + d18 * d15;
				double d23 = d22;
				double d24 = d21 * d12 + d20 * d13;
				double d25 = d20 * d12 - d21 * d13;
				double d26 = d25 * d9 - d23 * d10;
				double d27 = d24;
				double d28 = d23 * d9 + d25 * d10;
				tessellator.addVertex(d5 + d26, d6 + d27, d7 + d28);
			}
		}
		tessellator.draw();
    }
	
	public RenderEngine renderEngine;
	public Minecraft mc;
	public World theWorld;
    public int starGLCallList;
    public int glSkyList;
    public int glSkyList2;
}
