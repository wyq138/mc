package com.minecraft.chunk;

import com.minecraft.block.Block;
import com.minecraft.block.BlockType;
import com.minecraft.engine.Renderer;
import com.minecraft.terrain.TerrainGenerator;

import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Chunk {

    private int chunkX;
    private int chunkZ;
    private Block[][][] blocks;

    private int vao;
    private int vbo;
    private int ebo;
    private int vertexCount;

    private static final int CHUNK_SIZE = 16;
    private static final int CHUNK_HEIGHT = 256;

    public Chunk(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.blocks = new Block[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
        
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    blocks[x][y][z] = new Block(BlockType.AIR);
                }
            }
        }
    }

    public void generate() {
        TerrainGenerator.generateTerrain(this);
    }

    public void rebuildMesh() {
        if (vao != 0) {
            cleanup();
        }

        float[] vertices = generateVertices();
        int[] indices = generateIndices(vertices.length / 8);

        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ebo = glGenBuffers();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * Float.BYTES, 0);

        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);

        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 8 * Float.BYTES, 5 * Float.BYTES);

        glBindVertexArray(0);

        vertexCount = indices.length;
    }

    private float[] generateVertices() {
        java.util.List<Float> vertexList = new java.util.ArrayList<>();

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    Block block = blocks[x][y][z];
                    if (block.getType() != BlockType.AIR) {
                        addBlockVertices(vertexList, x, y, z, block.getType());
                    }
                }
            }
        }

        float[] vertices = new float[vertexList.size()];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = vertexList.get(i);
        }

        return vertices;
    }

    private void addBlockVertices(java.util.List<Float> vertexList, int x, int y, int z, BlockType type) {
        float worldX = chunkX * CHUNK_SIZE + x;
        float worldZ = chunkZ * CHUNK_SIZE + z;

        if (y < CHUNK_HEIGHT - 1 && blocks[x][y + 1][z].getType() == BlockType.AIR) {
            addFaceVertices(vertexList, worldX, y, worldZ, type, 0);
        }
        if (y > 0 && blocks[x][y - 1][z].getType() == BlockType.AIR) {
            addFaceVertices(vertexList, worldX, y, worldZ, type, 1);
        }
        if (x < CHUNK_SIZE - 1 && blocks[x + 1][y][z].getType() == BlockType.AIR) {
            addFaceVertices(vertexList, worldX, y, worldZ, type, 2);
        }
        if (x > 0 && blocks[x - 1][y][z].getType() == BlockType.AIR) {
            addFaceVertices(vertexList, worldX, y, worldZ, type, 3);
        }
        if (z < CHUNK_SIZE - 1 && blocks[x][y][z + 1].getType() == BlockType.AIR) {
            addFaceVertices(vertexList, worldX, y, worldZ, type, 4);
        }
        if (z > 0 && blocks[x][y][z - 1].getType() == BlockType.AIR) {
            addFaceVertices(vertexList, worldX, y, worldZ, type, 5);
        }
    }

    private void addFaceVertices(java.util.List<Float> vertexList, float x, float y, float z, BlockType type, int face) {
        float texX = type.getTextureX();
        float texY = type.getTextureY();
        float texSize = 1.0f / 16.0f;

        float[][] faceVertices = getFaceVertices(face, x, y, z);
        float[][] faceUVs = getFaceUVs(face, texX, texY, texSize);
        float[][] faceNormals = getFaceNormals(face);

        for (int i = 0; i < 4; i++) {
            vertexList.add(faceVertices[i][0]);
            vertexList.add(faceVertices[i][1]);
            vertexList.add(faceVertices[i][2]);
            vertexList.add(faceUVs[i][0]);
            vertexList.add(faceUVs[i][1]);
            vertexList.add(faceNormals[i][0]);
            vertexList.add(faceNormals[i][1]);
            vertexList.add(faceNormals[i][2]);
        }
    }

    private float[][] getFaceVertices(int face, float x, float y, float z) {
        switch (face) {
            case 0:
                return new float[][] {
                    {x, y + 1, z}, {x + 1, y + 1, z}, {x + 1, y + 1, z + 1}, {x, y + 1, z + 1}
                };
            case 1:
                return new float[][] {
                    {x, y, z + 1}, {x + 1, y, z + 1}, {x + 1, y, z}, {x, y, z}
                };
            case 2:
                return new float[][] {
                    {x + 1, y, z}, {x + 1, y, z + 1}, {x + 1, y + 1, z + 1}, {x + 1, y + 1, z}
                };
            case 3:
                return new float[][] {
                    {x, y, z + 1}, {x, y, z}, {x, y + 1, z}, {x, y + 1, z + 1}
                };
            case 4:
                return new float[][] {
                    {x, y, z + 1}, {x + 1, y, z + 1}, {x + 1, y + 1, z + 1}, {x, y + 1, z + 1}
                };
            case 5:
                return new float[][] {
                    {x + 1, y, z}, {x, y, z}, {x, y + 1, z}, {x + 1, y + 1, z}
                };
            default:
                return new float[4][3];
        }
    }

    private float[][] getFaceUVs(int face, float texX, float texY, float texSize) {
        return new float[][] {
            {texX, texY + texSize},
            {texX + texSize, texY + texSize},
            {texX + texSize, texY},
            {texX, texY}
        };
    }

    private float[][] getFaceNormals(int face) {
        switch (face) {
            case 0:
                return new float[][] {{0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}};
            case 1:
                return new float[][] {{0, -1, 0}, {0, -1, 0}, {0, -1, 0}, {0, -1, 0}};
            case 2: 
                return new float[][] {{1, 0, 0}, {1, 0, 0}, {1, 0, 0}, {1, 0, 0}};
            case 3:
                return new float[][] {{-1, 0, 0}, {-1, 0, 0}, {-1, 0, 0}, {-1, 0, 0}};
            case 4:
                return new float[][] {{0, 0, 1}, {0, 0, 1}, {0, 0, 1}, {0, 0, 1}};
            case 5:
                return new float[][] {{0, 0, -1}, {0, 0, -1}, {0, 0, -1}, {0, 0, -1}};
            default:
                return new float[4][3];
        }
    }

    private int[] generateIndices(int vertexCount) {
        int[] indices = new int[vertexCount * 6 / 4];
        for (int i = 0; i < vertexCount / 4; i++) {
            int offset = i * 6;
            int vertexOffset = i * 4;
            indices[offset] = vertexOffset;
            indices[offset + 1] = vertexOffset + 1;
            indices[offset + 2] = vertexOffset + 2;
            indices[offset + 3] = vertexOffset;
            indices[offset + 4] = vertexOffset + 2;
            indices[offset + 5] = vertexOffset + 3;
        }
        return indices;
    }

    public void render(Renderer renderer, Matrix4f modelMatrix) {
        if (vertexCount > 0) {
            renderer.renderMesh(vao, vertexCount, modelMatrix);
        }
    }

    public Block getBlock(int x, int y, int z) {
        if (x >= 0 && x < CHUNK_SIZE && y >= 0 && y < CHUNK_HEIGHT && z >= 0 && z < CHUNK_SIZE) {
            return blocks[x][y][z];
        }
        return new Block(BlockType.AIR);
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        if (x >= 0 && x < CHUNK_SIZE && y >= 0 && y < CHUNK_HEIGHT && z >= 0 && z < CHUNK_SIZE) {
            blocks[x][y][z] = new Block(type);
        }
    }

    public void cleanup() {
        if (vao != 0) {
            glDeleteVertexArrays(vao);
            glDeleteBuffers(vbo);
            glDeleteBuffers(ebo);
        }
    }
}
