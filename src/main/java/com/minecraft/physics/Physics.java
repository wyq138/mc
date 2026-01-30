package com.minecraft.physics;

import com.minecraft.world.World;
import com.minecraft.block.BlockType;

public class Physics {

    private World world;
    private static final float GRAVITY = 0.08f;
    private static final float TERMINAL_VELOCITY = 0.003f;

    public Physics(World world) {
        this.world = world;
    }

    public void applyGravity(org.joml.Vector3f velocity, boolean onGround) {
        if (!onGround) {
            velocity.y -= GRAVITY * 0.016f;
        } else {
            velocity.y = 0;
        }

        if (Math.abs(velocity.y) < TERMINAL_VELOCITY) {
            velocity.y = 0;
        }
    }

    public boolean checkGroundCollision(AABB box) {
        float checkY = box.minY - 0.1f;
        for (int x = (int)box.minX; x <= (int)box.maxX; x++) {
            for (int z = (int)box.minZ; z <= (int)box.maxZ; z++) {
                if (world.getBlock(x, (int)checkY, z).getType().isSolid()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkCollisions(AABB box, org.joml.Vector3f velocity) {
        float newX = box.minX + velocity.x;
        float newY = box.minY + velocity.y;
        float newZ = box.minZ + velocity.z;

        if (checkCollision(newX, box.minY, box.minZ, box.maxX - box.minX, box.maxY - box.minY, box.maxZ - box.minZ)) {
            velocity.x = 0;
        }

        if (checkCollision(box.minX, newY, box.minZ, box.maxX - box.minX, box.maxY - box.minY, box.maxZ - box.minZ)) {
            velocity.y = 0;
        }

        if (checkCollision(box.minX, box.minY, newZ, box.maxX - box.minX, box.maxY - box.minY, box.maxZ - box.minZ)) {
            velocity.z = 0;
        }
    }

    private boolean checkCollision(float x, float y, float z, float width, float height, float depth) {
        for (int dx = 0; dx <= (int)width; dx++) {
            for (int dy = 0; dy <= (int)height; dy++) {
                for (int dz = 0; dz <= (int)depth; dz++) {
                    int bx = (int)(x + dx);
                    int by = (int)(y + dy);
                    int bz = (int)(z + dz);
                    
                    if (world.getBlock(bx, by, bz).getType().isSolid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
