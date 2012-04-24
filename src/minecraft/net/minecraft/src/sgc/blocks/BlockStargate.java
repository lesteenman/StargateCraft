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
	 */
	public Vector<Integer> generateStargateAround(World world, int i, int j, int k) {
		int x, y, z;
		//Find empty location
		//TODO: Implement empty location finding
		x = i; y = j; z = k;
		
		//Place the gate
		this.generateStargateAt(world, i, j, k);
		
		Vector<Integer> v = new Vector<Integer>();
		v.add(i); v.add(j);	v.add(k);
		return v;
	}
	
	/**
	 * Places a complete stargate, with the center bottom block being at the given position.
	 * @param i
	 * @param j
	 * @param k
	 */
	public void generateStargateAt(World world, int i, int j, int k) {
		//Bottom
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
		world.setBlock(i + 4, j + 5, k, SGCBlocks.stargateBlockID);
	}
}
