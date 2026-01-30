package com.minecraft.item;

import com.minecraft.block.BlockType;

public class ItemStack {

    private BlockType type;
    private int count;
    private int damage;

    public ItemStack(BlockType type, int count) {
        this.type = type;
        this.count = count;
        this.damage = 0;
    }

    public ItemStack(BlockType type, int count, int damage) {
        this.type = type;
        this.count = count;
        this.damage = damage;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = Math.max(0, Math.min(64, count));
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isEmpty() {
        return type == BlockType.AIR || count <= 0;
    }

    public ItemStack copy() {
        return new ItemStack(type, count, damage);
    }
}
