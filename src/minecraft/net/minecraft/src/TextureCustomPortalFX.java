package net.minecraft.src;

import java.util.Random;
import org.lwjgl.opengl.GL11;

public class TextureCustomPortalFX extends TextureFX
{
    private int portalTickCounter;
    private byte portalTextureData[][];
	public String textureOverride;
	
	public TextureCustomPortalFX(int blockTextureIndex, float red, float green, float blue, float alpha)
	{
		this(blockTextureIndex, red, green, blue, alpha, "/terrain.png");
	}

    public TextureCustomPortalFX(int blockTextureIndex, float red, float green, float blue, float alpha, String string)
    {
        super(blockTextureIndex);
		textureOverride = string;
        portalTickCounter = 0;
        portalTextureData = new byte[32][1024];
        Random random = new Random(100L);

        for (int i = 0; i < 32; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                for (int k = 0; k < 16; k++)
                {
                    float f = 0.0F;

                    for (int l = 0; l < 2; l++)
                    {
                        float f1 = (float)(l * 16) * 0.5F;
                        float f2 = (float)(l * 16) * 0.5F;
                        float f3 = (((float)j - f1) / 16F) * 2.0F;
                        float f4 = (((float)k - f2) / 16F) * 2.0F;

                        if (f3 < -1F)
                        {
                            f3 += 2.0F;
                        }

                        if (f3 >= 1.0F)
                        {
                            f3 -= 2.0F;
                        }

                        if (f4 < -1F)
                        {
                            f4 += 2.0F;
                        }

                        if (f4 >= 1.0F)
                        {
                            f4 -= 2.0F;
                        }

                        float f5 = f3 * f3 + f4 * f4;
                        float f6 = (float)Math.atan2(f4, f3) + ((((float)i / 32F) * (float)Math.PI * 2.0F - f5 * 10F) + (float)(l * 2)) * (float)(l * 2 - 1);
                        f6 = (MathHelper.sin(f6) + 1.0F) / 2.0F;
                        f6 /= f5 + 1.0F;
                        f += f6 * 0.5F;
                    }

                    f += random.nextFloat() * 0.1F;
                    int i1 = (int)(f * blue);
                    int j1 = (int)(f * red);
                    int k1 = (int)(f * green);
                    int l1 = (int)(f * alpha);
                    int i2 = k * 16 + j;
                    portalTextureData[i][i2 * 4 + 0] = (byte)j1;
                    portalTextureData[i][i2 * 4 + 1] = (byte)k1;
                    portalTextureData[i][i2 * 4 + 2] = (byte)i1;
                    portalTextureData[i][i2 * 4 + 3] = (byte)l1;
                }
            }
        }
    }

    public void onTick()
    {
        portalTickCounter++;
        byte abyte0[] = portalTextureData[portalTickCounter & 0x1f];

        for (int i = 0; i < 256; i++)
        {
            int j = abyte0[i * 4 + 0] & 0xff;
            int k = abyte0[i * 4 + 1] & 0xff;
            int l = abyte0[i * 4 + 2] & 0xff;
            int i1 = abyte0[i * 4 + 3] & 0xff;

            if (anaglyphEnabled)
            {
                int j1 = (j * 30 + k * 59 + l * 11) / 100;
                int k1 = (j * 30 + k * 70) / 100;
                int l1 = (j * 30 + l * 70) / 100;
                j = j1;
                k = k1;
                l = l1;
            }

            imageData[i * 4 + 0] = (byte)j;
            imageData[i * 4 + 1] = (byte)k;
            imageData[i * 4 + 2] = (byte)l;
            imageData[i * 4 + 3] = (byte)i1;
        }
    }
	
	public void bindImage(RenderEngine renderengine)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderengine.getTexture(textureOverride));
	}
}
