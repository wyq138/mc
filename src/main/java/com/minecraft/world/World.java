package com.minecraft.world;

import com.minecraft.engine.Renderer;
import com.minecraft.block.Block;
import com.minecraft.block.BlockType;
import com.minecraft.chunk.Chunk;
import com.minecraft.chunk.ChunkManager;
import com.minecraft.terrain.TerrainGenerator;
import com.minecraft.redstone.RedstoneSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.joml.Matrix4f;

public class World {

    private ChunkManager chunkManager;
    private Map<Long, Chunk> chunks;
    private RedstoneSystem redstoneSystem;

    private static final int RENDER_DISTANCE = 16;
    private static final int CHUNK_SIZE = 16;
    private static final int CHUNK_HEIGHT = 256;
    private static final int WORLD_HEIGHT = 64;

    private long seed;
    private String worldName;

    public World(String worldName) {
        this.worldName = worldName;
        this.seed = System.currentTimeMillis();
        chunkManager = new ChunkManager();
        chunks = new ConcurrentHashMap<>();
        redstoneSystem = new RedstoneSystem(this);
    }

    public void init() {
        generateInitialChunks();
    }

    private void generateInitialChunks() {
        for (int x = -RENDER_DISTANCE; x <= RENDER_DISTANCE; x++) {
            for (int z = -RENDER_DISTANCE; z <= RENDER_DISTANCE; z++) {
                loadChunk(x, z);
            }
        }
    }

    public void update() {
        updateChunks();
        redstoneSystem.update();
    }

    private void updateChunks() {
        int playerChunkX = (int)Math.floor(0.0 / CHUNK_SIZE);
        int playerChunkZ = (int)Math.floor(0.0 / CHUNK_SIZE);

        for (int x = playerChunkX - RENDER_DISTANCE; x <= playerChunkX + RENDER_DISTANCE; x++) {
            for (int z = playerChunkZ - RENDER_DISTANCE; z <= playerChunkZ + RENDER_DISTANCE; z++) {
                loadChunk(x, z);
            }
        }

        unloadDistantChunks(playerChunkX, playerChunkZ);
    }

    private void unloadDistantChunks(int playerChunkX, int playerChunkZ) {
        java.util.List<Long> chunksToUnload = new java.util.ArrayList<>();

        for (Map.Entry<Long, Chunk> entry : chunks.entrySet()) {
            long key = entry.getKey();
            int chunkX = (int)(key >> 32);
            int chunkZ = (int)(key & 0xFFFFFFFFL);

            if (Math.abs(chunkX - playerChunkX) > RENDER_DISTANCE + 2 || 
                Math.abs(chunkZ - playerChunkZ) > RENDER_DISTANCE + 2) {
                chunksToUnload.add(key);
            }
        }

        for (Long key : chunksToUnload) {
            Chunk chunk = chunks.remove(key);
            if (chunk != null) {
                chunkManager.unloadChunk(chunk);
            }
        }
    }

    private void loadChunk(int chunkX, int chunkZ) {
        long chunkKey = getChunkKey(chunkX, chunkZ);
        if (!chunks.containsKey(chunkKey)) {
            Chunk chunk = chunkManager.loadOrCreateChunk(chunkX, chunkZ, seed);
            if (chunk.isEmpty()) {
                TerrainGenerator.generateTerrain(chunk, seed);
            }
            chunk.rebuildMesh();
            chunks.put(chunkKey, chunk);
        }
    }

    private long getChunkKey(int chunkX, int chunkZ) {
        return ((long)chunkX << 32) | (chunkZ & 0xFFFFFFFFL);
    }

    public void render(Renderer renderer) {
        Matrix4f modelMatrix = new Matrix4f();
        
        for (Chunk chunk : chunks.values()) {
            chunk.render(renderer, modelMatrix);
        }
    }

    public Block getBlock(int x, int y, int z) {
        int chunkX = x / CHUNK_SIZE;
        int chunkZ = z / CHUNK_SIZE;
        long chunkKey = getChunkKey(chunkX, chunkZ);
        
        Chunk chunk = chunks.get(chunkKey);
        if (chunk != null) {
            int localX = x % CHUNK_SIZE;
            int localZ = z % CHUNK_SIZE;
            if (localX < 0) localX += CHUNK_SIZE;
            if (localZ < 0) localZ += CHUNK_SIZE;
            return chunk.getBlock(localX, y, localZ);
        }
        
        return new Block(BlockType.AIR);
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        int chunkX = x / CHUNK_SIZE;
        int chunkZ = z / CHUNK_SIZE;
        long chunkKey = getChunkKey(chunkX, chunkZ);
        
        Chunk chunk = chunks.get(chunkKey);
        if (chunk != null) {
            int localX = x % CHUNK_SIZE;
            int localZ = z % CHUNK_SIZE;
            if (localX < 0) localX += CHUNK_SIZE;
            if (localZ < 0) localZ += CHUNK_SIZE;
            chunk.setBlock(localX, y, localZ, type);
            chunk.rebuildMesh();
            
            updateNeighborChunks(chunkX, chunkZ);
        }
    }

    private void updateNeighborChunks(int chunkX, int chunkZ) {
        int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] neighbor : neighbors) {
            int nx = chunkX + neighbor[0];
            int nz = chunkZ + neighbor[1];
            long key = getChunkKey(nx, nz);
            Chunk chunk = chunks.get(key);
            if (chunk != null) {
                chunk.rebuildMesh();
            }
        }
    }

    public Map<Long, Chunk> getChunks() {
        return chunks;
    }

    public long getSeed() {
        return seed;
    }

    public void cleanup() {
        for (Chunk chunk : chunks.values()) {
            chunk.cleanup();
        }
        chunks.clear();
    }
}
