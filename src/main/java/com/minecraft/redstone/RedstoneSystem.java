package com.minecraft.redstone;

import com.minecraft.world.World;
import com.minecraft.block.BlockType;

import java.util.HashMap;
import java.util.Map;

public class RedstoneSystem {

    private World world;
    private Map<Long, RedstoneWire> wires;
    private Map<Long, RedstoneComponent> components;

    private static final int REDSTONE_MAX_POWER = 15;
    private static final int UPDATE_DELAY = 2;
    private int updateTimer = 0;

    public RedstoneSystem(World world) {
        this.world = world;
        this.wires = new HashMap<>();
        this.components = new HashMap<>();
    }

    public void update() {
        updateTimer++;
        if (updateTimer >= UPDATE_DELAY) {
            updateTimer = 0;
            updateRedstone();
        }
    }

    private void updateRedstone() {
        Map<Long, Integer> powerUpdates = new HashMap<>();
        
        for (RedstoneComponent component : components.values()) {
            int power = calculatePower(component.getX(), component.getY(), component.getZ());
            powerUpdates.put(getBlockKey(component.getX(), component.getY(), component.getZ()), power);
        }
        
        for (Map.Entry<Long, Integer> entry : powerUpdates.entrySet()) {
            long key = entry.getKey();
            int power = entry.getValue();
            
            int x = (int)(key >> 32);
            int y = (int)((key >> 16) & 0xFFFFL);
            int z = (int)(key & 0xFFFFL);
            
            RedstoneComponent component = components.get(key);
            if (component != null) {
                component.setPower(power);
            }
        }
    }

    private int calculatePower(int x, int y, int z) {
        int maxPower = 0;
        
        int[][][] directions = {
            {{1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}
        };
        
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            int nz = z + dir[2];
            
            BlockType block = world.getBlock(nx, ny, nz).getType();
            int power = getBlockPower(block, nx, ny, nz);
            
            if (power > maxPower) {
                maxPower = power;
            }
        }
        
        return maxPower;
    }

    private int getBlockPower(BlockType block, int x, int y, int z) {
        switch (block) {
            case REDSTONE_WIRE:
                RedstoneWire wire = wires.get(getBlockKey(x, y, z));
                return wire != null ? wire.getPower() : 0;
            case REDSTONE_TORCH_ON:
                return REDSTONE_MAX_POWER;
            case REDSTONE_REPEATER_ON:
                return REDSTONE_MAX_POWER;
            case REDSTONE_BLOCK:
                return REDSTONE_MAX_POWER;
            case LEVER:
                return REDSTONE_MAX_POWER;
            default:
                return 0;
        }
    }

    public void placeRedstoneWire(int x, int y, int z) {
        long key = getBlockKey(x, y, z);
        RedstoneWire wire = new RedstoneWire(x, y, z);
        wires.put(key, wire);
    }

    public void removeRedstoneWire(int x, int y, int z) {
        long key = getBlockKey(x, y, z);
        wires.remove(key);
    }

    public void addComponent(int x, int y, int z, RedstoneComponent component) {
        long key = getBlockKey(x, y, z);
        components.put(key, component);
    }

    public void removeComponent(int x, int y, int z) {
        long key = getBlockKey(x, y, z);
        components.remove(key);
    }

    private long getBlockKey(int x, int y, int z) {
        return ((long)x << 32) | ((long)(y & 0xFFFF) << 16) | (z & 0xFFFFL);
    }
}

class RedstoneWire {

    private int x, y, z;
    private int power;

    public RedstoneWire(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.power = 0;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
}

class RedstoneComponent {

    private int x, y, z;
    private int power;
    private boolean isActive;

    public RedstoneComponent(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.power = 0;
        this.isActive = false;
    }

    public void setPower(int power) {
        this.power = power;
        this.isActive = power > 0;
    }

    public int getPower() {
        return power;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
}
