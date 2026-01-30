package com.minecraft.chunk;

public class ChunkManager {

    public ChunkManager() {
    }

    public Chunk createChunk(int chunkX, int chunkZ) {
        return new Chunk(chunkX, chunkZ);
    }
}
