package net.minecraft.src.sgc.blocks;
import java.util.Vector;

import javax.jws.Oneway;

import net.minecraft.src.*;
import net.minecraft.src.sgc.SGCBlocks;
import net.minecraft.src.sgc.SGCDimensionModel;
import net.minecraft.src.sgc.SGCDimensions;
import net.minecraft.src.sgc.WorldProviderSGCBase;

public class BlockStargate extends BlockObsidian {

	public BlockStargate(int par1, int par2) {
		super(par1, par2);
	}
	
	/**
	 * Attempts to place a new, complete stargate as close to the designated location as possible.
	 * @param i
	 * @param j
	 * @param k
	 * @param generateDHD Whether or not to generate a DHD accompanying the Stargate
	 */
	public Vector<Integer> generateStargateAround(World world, int i, int j, int k, boolean generateDHD) {
		int x, y, z;
		//Find empty location
		//TODO: Implement empty location finding
		for (j = 128; j > 0; j--) {
			if (world.getBlockMaterial(i, j, k).isSolid() && world.getBlockMaterial(i, j, k).isOpaque()) {
				Vector<Integer> v = new Vector<Integer>();
				v.add(i); v.add(j);	v.add(k);
				generateStargateAt(world, i, j, k);
				
				if (generateDHD) {
					k += 15;
					int t = j + 6;
					for (j = j - 6; j < t; j++) {
						if (!world.getBlockMaterial(i, j, k).isSolid())
							placeDHDAt(world, i, j, k);
						j = t;
					}
				}
				
				return v;
			}
		}

		//Place the gate
		x = i; y = j; z = k;
		this.generateStargateAt(world, i, j, k);
		
		Vector<Integer> v = new Vector<Integer>();
		v.add(i); v.add(j);	v.add(k);
		return v;
	}
	
	public void placeDHDAt(World world, int i, int j, int k) {
		world.setBlock(i, j, k, SGCBlocks.dhdBlockId);
	}
	
	private static int[][] gateMap = {{
		0, 0, 0, 1, 1, 1, 0, 0, 0
	},{
		0, 1, 1, 0, 0, 0, 1, 1, 0
	},{
		0, 1, 0, 0, 0, 0, 0, 1, 0
	},{
		1, 0, 0, 0, 0, 0, 0, 0, 1
	},{
		1, 0, 0, 0, 0, 0, 0, 0, 1
	},{
		1, 0, 0, 0, 0, 0, 0, 0, 1
	},{
		0, 1, 0, 0, 0, 0, 0, 1, 0
	},{
		0, 1, 1, 0, 0, 0, 1, 1, 0
	},{
		0, 0, 0, 1, 1, 1, 0, 0, 0
	}};
	
	/**
	 * Places a complete stargate, with the center bottom block being at the given position.
	 * @param i
	 * @param j
	 * @param k
	 */
	public void generateStargateAt(World world, int i, int j, int k) {
		
		i -= 4;
		
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (gateMap[x][y] == 1)
					world.setBlock(i + x, j + y, k, SGCBlocks.stargateBlockID);
				else
					world.setBlock(i + x, j + y, k, 0);
			}
		}
		
		/*//Bottom
		world.setBlock(i, j, k, SGCBlocks.stargateBlockID);
		world.setBlock(i - 1, j, k, SGCBlocks.stargateBlockID);
		world.setBlock(i + 1, j, k, SGCBlocks.stargateBlockID);
		
		//Top
		world.setBlock(i, j + 8, k, SGCBlocks.stargateBlockID);
		world.setBlock(i - 1, j + 8, k, SGCBlocks.stargateBlockID);
		world.setBlock(i + 1, j + 8, k, SGCBlocks.stargateBlockID);
		
		//Lower left corner
		world.setBlock(i - 2, j + 1, k, SGCBlocks.stargateBlockID);
		world.setBlock(i - 3, j + 1, k, SGCBlocks.stargateBlockID);
		world.setBlock(i - 3, j + 2, k, SGCBlocks.stargateBlockID);
		
		//Lower right corner
		world.setBlock(i + 2, j + 1, k, SGCBlocks.stargateBlockID);
		world.setBlock(i + 3, j + 1, k, SGCBlocks.stargateBlockID);
		world.setBlock(i + 3, j + 2, k, SGCBlocks.stargateBlockID);
		
		//Upper left corner
		world.setBlock(i - 2, j + 7, k, SGCBlocks.stargateBlockID);
		world.setBlock(i - 3, j + 7, k, SGCBlocks.stargateBlockID);
		world.setBlock(i - 3, j + 6, k, SGCBlocks.stargateBlockID);
		
		//Upper right corner
		world.setBlock(i + 2, j + 7, k, SGCBlocks.stargateBlockID);
		world.setBlock(i + 3, j + 7, k, SGCBlocks.stargateBlockID);
		world.setBlock(i + 3, j + 6, k, SGCBlocks.stargateBlockID);
		
		//Left
		world.setBlock(i - 4, j + 3, k, SGCBlocks.stargateBlockID);
		world.setBlock(i - 4, j + 4, k, SGCBlocks.stargateBlockID);
		world.setBlock(i - 4, j + 5, k, SGCBlocks.stargateBlockID);
		
		//Right
		world.setBlock(i + 4, j + 3, k, SGCBlocks.stargateBlockID);
		world.setBlock(i + 4, j + 4, k, SGCBlocks.stargateBlockID);
		world.setBlock(i + 4, j + 5, k, SGCBlocks.stargateBlockID);*/
	}
}
