package net.minecraft.src.sgc.blocks;

/**
 * AMAZING tutorial on how to make a special block renderer:
 * http://www.zidmc.com/index.php?topic=2033.0
 */

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_SGC;
import net.minecraft.src.sgc.GUIDHD;
import net.minecraft.src.sgc.SGCBlocks;
import net.minecraft.src.sgc.SGCDimensionModel;
import net.minecraft.src.sgc.SGCDimensions;
import net.minecraft.src.sgc.TileEntityDHD;
import net.minecraft.src.sgc.TileEntityRendererDHD;
import net.minecraft.src.sgc.WorldProviderSGCBase;

public class BlockDHD extends BlockContainer {

	public static int modelID;

	public BlockDHD(int par1, Material par2Material) {
		super(par1, par2Material);
		
		ModLoader.registerTileEntity(net.minecraft.src.sgc.TileEntityDHD.class, "TileEntityDHD", new TileEntityRendererDHD());
		modelID = ModLoader.getUniqueBlockModelID(mod_SGC.instance, true);
		this.setBlockBounds(0f, 0f, 0f, 1f, 1.1f, 1f);
	}

	@Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		//Should show GUI and return true if stargate was found;
		//Else, should return false.
		//Or do we want to always show the GUI?
		System.out.println("Should now show GUI");
		if (world.getBlockTileEntity(x, y, z) != null) {
			System.out.println("Entity: " + world.getBlockTileEntity(x, y, z).toString());
		} else {
			System.out.println("Tile entity was null!");
		}
		ModLoader.getMinecraftInstance().displayGuiScreen(new GUIDHD());
		return true;
    }

    public TileEntity getBlockEntity()
    {
        return new TileEntityDHD();
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }

    public int getRenderType()
    {
        return modelID;
    }
}
