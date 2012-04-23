package net.minecraft.src.sgc.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.sgc.GUIDHD;
import net.minecraft.src.sgc.SGCBlocks;
import net.minecraft.src.sgc.SGCDimensionModel;
import net.minecraft.src.sgc.SGCDimensions;
import net.minecraft.src.sgc.WorldProviderSGCBase;

public class BlockDHD extends Block {

	public BlockDHD(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		System.out.println("Should now show GUI");
		ModLoader.getMinecraftInstance().displayGuiScreen(new GUIDHD());
		return false;
    }
}
