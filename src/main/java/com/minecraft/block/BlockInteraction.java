package com.minecraft.block;

import com.minecraft.world.World;
import com.minecraft.player.Player;
import com.minecraft.inventory.Inventory;
import com.minecraft.item.ItemStack;

import org.joml.Vector3f;

public class BlockInteraction {

    private World world;
    private Vector3f rayOrigin;
    private Vector3f rayDirection;
    private float reachDistance = 5.0f;

    private int targetX, targetY, targetZ;
    private int faceX, faceY, faceZ;
    private boolean hasTarget = false;

    public BlockInteraction(World world) {
        this.world = world;
        this.rayOrigin = new Vector3f();
        this.rayDirection = new Vector3f();
    }

    public void update(Vector3f playerPos, float yaw, float pitch) {
        rayOrigin.set(playerPos.x, playerPos.y + 1.62f, playerPos.z);
        
        float radYaw = (float)Math.toRadians(yaw);
        float radPitch = (float)Math.toRadians(pitch);
        
        rayDirection.x = -(float)Math.sin(radYaw) * (float)Math.cos(radPitch);
        rayDirection.y = (float)Math.sin(radPitch);
        rayDirection.z = -(float)Math.cos(radYaw) * (float)Math.cos(radPitch);
        
        rayDirection.normalize();
        
        raycast();
    }

    private void raycast() {
        hasTarget = false;
        
        Vector3f currentPos = new Vector3f(rayOrigin);
        Vector3f step = new Vector3f(rayDirection).mul(0.1f);
        
        for (float distance = 0; distance < reachDistance; distance += 0.1f) {
            int x = (int)Math.floor(currentPos.x);
            int y = (int)Math.floor(currentPos.y);
            int z = (int)Math.floor(currentPos.z);
            
            if (world.getBlock(x, y, z).getType().isSolid()) {
                targetX = x;
                targetY = y;
                targetZ = z;
                
                Vector3f prevPos = new Vector3f(currentPos).sub(step);
                faceX = (int)Math.floor(prevPos.x);
                faceY = (int)Math.floor(prevPos.y);
                faceZ = (int)Math.floor(prevPos.z);
                
                hasTarget = true;
                return;
            }
            
            currentPos.add.x(step.x);
            currentPos.add.y(step.y);
            currentPos.add.z(step.z);
        }
    }

    public void breakBlock() {
        if (hasTarget) {
            world.setBlock(targetX, targetY, targetZ, BlockType.AIR);
        }
    }

    public void placeBlock() {
        if (hasTarget) {
            world.setBlock(faceX, faceY, faceZ, BlockType.STONE);
        }
    }

    public void pickBlock() {
        if (hasTarget) {
            BlockType blockType = world.getBlock(targetX, targetY, targetZ).getType();
        }
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public int getTargetZ() {
        return targetZ;
    }
}
