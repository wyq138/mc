package com.minecraft.inventory;

import com.minecraft.block.BlockType;
import com.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private ItemStack[] mainInventory;
    private ItemStack[] hotbar;
    private ItemStack[] armor;

    private static final int MAIN_INVENTORY_SIZE = 27;
    private static final int HOTBAR_SIZE = 9;
    private static final int ARMOR_SIZE = 4;

    public Inventory() {
        mainInventory = new ItemStack[MAIN_INVENTORY_SIZE];
        hotbar = new ItemStack[HOTBAR_SIZE];
        armor = new ItemStack[ARMOR_SIZE];

        for (int i = 0; i < MAIN_INVENTORY_SIZE; i++) {
            mainInventory[i] = new ItemStack(BlockType.AIR, 0);
        }
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            hotbar[i] = new ItemStack(BlockType.AIR, 0);
        }
        for (int i = 0; i < ARMOR_SIZE; i++) {
            armor[i] = new ItemStack(BlockType.AIR, 0);
        }
    }

    public ItemStack getHotbarSlot(int slot) {
        if (slot >= 0 && slot < HOTBAR_SIZE) {
            return hotbar[slot];
        }
        return null;
    }

    public void setHotbarSlot(int slot, ItemStack item) {
        if (slot >= 0 && slot < HOTBAR_SIZE) {
            hotbar[slot] = item;
        }
    }

    public ItemStack getMainInventorySlot(int slot) {
        if (slot >= 0 && slot < MAIN_INVENTORY_SIZE) {
            return mainInventory[slot];
        }
        return null;
    }

    public void setMainInventorySlot(int slot, ItemStack item) {
        if (slot >= 0 && slot < MAIN_INVENTORY_SIZE) {
            mainInventory[slot] = item;
        }
    }

    public ItemStack getArmorSlot(int slot) {
        if (slot >= 0 && slot < ARMOR_SIZE) {
            return armor[slot];
        }
        return null;
    }

    public void setArmorSlot(int slot, ItemStack item) {
        if (slot >= 0 && slot < ARMOR_SIZE) {
            armor[slot] = item;
        }
    }

    public boolean addItem(ItemStack item) {
        for (int i = 0; i < MAIN_INVENTORY_SIZE; i++) {
            if (mainInventory[i].getType() == item.getType()) {
                int space = 64 - mainInventory[i].getCount();
                int toAdd = Math.min(space, item.getCount());
                mainInventory[i].setCount(mainInventory[i].getCount() + toAdd);
                item.setCount(item.getCount() - toAdd);
                
                if (item.getCount() <= 0) {
                    return true;
                }
            }
        }

        for (int i = 0; i < MAIN_INVENTORY_SIZE; i++) {
            if (mainInventory[i].getType() == BlockType.AIR) {
                mainInventory[i] = item;
                return true;
            }
        }

        return false;
    }

    public ItemStack removeItem(int count) {
        for (int i = 0; i < MAIN_INVENTORY_SIZE; i++) {
            if (mainInventory[i].getType() != BlockType.AIR && mainInventory[i].getCount() > 0) {
                ItemStack removed = new ItemStack(mainInventory[i].getType(), Math.min(count, mainInventory[i].getCount()));
                mainInventory[i].setCount(mainInventory[i].getCount() - removed.getCount());
                
                if (mainInventory[i].getCount() <= 0) {
                    mainInventory[i] = new ItemStack(BlockType.AIR, 0);
                }
                
                return removed;
            }
        }
        return null;
    }

    public List<ItemStack> getAllItems() {
        List<ItemStack> items = new ArrayList<>();
        
        for (ItemStack item : hotbar) {
            if (item.getType() != BlockType.AIR && item.getCount() > 0) {
                items.add(item);
            }
        }
        
        for (ItemStack item : mainInventory) {
            if (item.getType() != BlockType.AIR && item.getCount() > 0) {
                items.add(item);
            }
        }
        
        return items;
    }

    public void clear() {
        for (int i = 0; i < MAIN_INVENTORY_SIZE; i++) {
            mainInventory[i] = new ItemStack(BlockType.AIR, 0);
        }
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            hotbar[i] = new ItemStack(BlockType.AIR, 0);
        }
        for (int i = 0; i < ARMOR_SIZE; i++) {
            armor[i] = new ItemStack(BlockType.AIR, 0);
        }
    }
}
