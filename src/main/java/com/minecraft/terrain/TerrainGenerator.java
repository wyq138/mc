package com.minecraft.terrain;

import com.minecraft.block.BlockType;
import com.minecraft.chunk.Chunk;

import java.util.Random;

public class TerrainGenerator {

    private static final int SEA_LEVEL = 62;
    private static final int MAX_HEIGHT = 100;
    private static final int MIN_HEIGHT = 40;

    public static void generateTerrain(Chunk chunk) {
        Random random = new Random(chunk.getChunkX() * 31 + chunk.getChunkZ() * 17);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunk.getChunkX() * 16 + x;
                int worldZ = chunk.getChunkZ() * 16 + z;
                
                int height = generateHeight(worldX, worldZ, random);
                
                for (int y = 0; y < height; y++) {
                    BlockType blockType = getBlockTypeForHeight(y, height);
                    chunk.setBlock(x, y, z, blockType);
                }

                if (height < SEA_LEVEL) {
                    for (int y = height; y < SEA_LEVEL; y++) {
                        chunk.setBlock(x, y, z, BlockType.WATER);
                    }
                }

                generateTrees(chunk, x, height, z, random);
            }
        }
        
        chunk.rebuildMesh();
    }

    private static int generateHeight(int x, int z, Random random) {
        double height = MIN_HEIGHT;
        
        height += Math.sin(x * 0.05) * 10;
        height += Math.cos(z * 0.05) * 10;
        height += Math.sin((x + z) * 0.03) * 5;
        
        height += (random.nextDouble() - 0.5) * 4;
        
        return (int)Math.max(MIN_HEIGHT, Math.min(MAX_HEIGHT, height));
    }

    private static BlockType getBlockTypeForHeight(int y, int surfaceHeight) {
        if (y == surfaceHeight) {
            if (surfaceHeight < SEA_LEVEL) {
                return BlockType.SAND;
            }
            return BlockType.GRASS;
        } else if (y > surfaceHeight - 4) {
            return BlockType.DIRT;
        } else {
            return BlockType.STONE;
        }
    }

    private static void generateTrees(Chunk chunk, int x, int surfaceHeight, int z, Random random) {
        if (surfaceHeight >= SEA_LEVEL && random.nextDouble() < 0.01) {
            int treeHeight = 4 + random.nextInt(3);
            
            for (int y = 1; y <= treeHeight; y++) {
                if (x + 1 < 16) {
                    chunk.setBlock(x + 1, surfaceHeight + y, z, BlockType.WOOD);
                }
            }
            
            for (int lx = -2; lx <= 2; lx++) {
                for (int lz = -2; lz <= 2; lz++) {
                    for (int ly = treeHeight - 2; ly <= treeHeight + 1; ly++) {
                        int worldX = x + 1 + lx;
                        int worldZ = z + lz;
                        if (worldX >= 0 && worldX < 16 && worldZ >= 0 && worldZ < 16) {
                            if (Math.abs(lx) + Math.abs(lz) <= 2) {
                                chunk.setBlock(worldX, surfaceHeight + ly, worldZ, BlockType.LEAVES);
                            }
                        }
                    }
                }
            }
        }
    }
}
