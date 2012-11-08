package edu.unca.ajrobine.Test2TerrainBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import edu.unca.ajrobine.Test2TerrainBuilder.Test2TerrainBuilderCactusPopulator;
import edu.unca.ajrobine.Test2TerrainBuilder.Test2TerrainBuilderGrassPopulator;

public class Test2TerrainBuilderGenerator extends ChunkGenerator {
	public List<BlockPopulator> getDefaultPopulators(World world) {
		ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
		
		populators.add(new Test2TerrainBuilderGrassPopulator());
		populators.add(new Test2TerrainBuilderCactusPopulator());
		
		return populators;
	}
	
	private int coordsToInt(int x, int y, int z) {
		return (x * 16 + z) * 128 + y;
	}
	
	public byte[] generate(World world, Random random, int chunkX, int chunkZ) {
		byte[] blocks = new byte[32768];
		int x, y, z;
		
		Random rand = new Random(world.getSeed());
		
		SimplexOctaveGenerator octave = new SimplexOctaveGenerator(rand, 8);
		
		octave.setScale(1 / 64.0);
		
		for (x = 0; x < 16; ++x) {
			for (z = 0; z < 16; ++z) {
		       blocks[this.coordsToInt(x, 0, z)] = (byte) Material.BEDROCK.getId();
		       
		       double noise = octave.noise(x + chunkX * 16, z + chunkZ * 16, 0.5, 0.5) * 4;
	           
		       for (y = 1; y < 32 + noise; ++y) {
		    	   blocks[this.coordsToInt(x, y, z)] = (byte) Material.DIRT.getId();
		       }
		       
		       blocks[this.coordsToInt(x, y, z)] = (byte) Material.GRASS.getId();
		
			}
		}	
		
		return blocks;
	}

}
