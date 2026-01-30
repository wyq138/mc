package com.minecraft.crafting;

import com.minecraft.inventory.Inventory;
import com.minecraft.item.ItemStack;
import com.minecraft.block.BlockType;

import java.util.ArrayList;
import java.util.List;

public class CraftingSystem {

    private Inventory inventory;
    private ItemStack[] craftingGrid;
    private ItemStack craftingResult;
    private List<CraftingRecipe> recipes;

    private static final int CRAFTING_GRID_SIZE = 9;

    public CraftingSystem(Inventory inventory) {
        this.inventory = inventory;
        this.craftingGrid = new ItemStack[CRAFTING_GRID_SIZE];
        this.craftingResult = new ItemStack(BlockType.AIR, 0);
        this.recipes = new ArrayList<>();
        initializeRecipes();
    }

    public void init() {
        for (int i = 0; i < CRAFTING_GRID_SIZE; i++) {
            craftingGrid[i] = new ItemStack(BlockType.AIR, 0);
        }
    }

    private void initializeRecipes() {
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.WOOD}, BlockType.PLANKS, 4));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.PLANKS, BlockType.PLANKS}, BlockType.CRAFTING_TABLE, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.COBBLESTONE, BlockType.COBBLESTONE}, BlockType.STONE_SLAB, 6));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.STONE}, BlockType.COBBLESTONE, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.STICK, BlockType.STICK, BlockType.STICK}, BlockType.WOODEN_PICKAXE, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.STICK, BlockType.STICK, BlockType.STICK}, BlockType.WOODEN_SWORD, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.IRON_INGOT, BlockType.STICK, BlockType.STICK}, BlockType.IRON_PICKAXE, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.DIAMOND, BlockType.STICK, BlockType.STICK}, BlockType.DIAMOND_PICKAXE, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.SAND, BlockType.SAND}, BlockType.SANDSTONE, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.GOLD_INGOT, BlockType.STICK, BlockType.STICK}, BlockType.GOLD_PICKAXE, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.COAL}, BlockType.TORCH, 4));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.PLANKS}, BlockType.STICK, 4));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.PLANKS, BlockType.PLANKS}, BlockType.CHEST, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.COBBLESTONE, BlockType.COBBLESTONE, BlockType.COBBLESTONE, BlockType.COBBLESTONE}, BlockType.FURNACE, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.DIAMOND}, BlockType.DIAMOND_BLOCK, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.IRON_INGOT}, BlockType.IRON_BLOCK, 1));
        recipes.add(new CraftingRecipe(new BlockType[]{BlockType.GOLD_INGOT}, BlockType.GOLD_BLOCK, 1));
    }

    public void update() {
        checkCrafting();
    }

    private void checkCrafting() {
        for (CraftingRecipe recipe : recipes) {
            if (recipe.matches(craftingGrid)) {
                craftingResult = recipe.getResult().copy();
                return;
            }
        }
        craftingResult = new ItemStack(BlockType.AIR, 0);
    }

    public void setCraftingSlot(int slot, ItemStack item) {
        if (slot >= 0 && slot < CRAFTING_GRID_SIZE) {
            craftingGrid[slot] = item;
            checkCrafting();
        }
    }

    public ItemStack getCraftingResult() {
        return craftingResult;
    }

    public void takeCraftingResult() {
        if (craftingResult != null && !craftingResult.isEmpty()) {
            inventory.addItem(craftingResult.copy());
            
            for (int i = 0; i < CRAFTING_GRID_SIZE; i++) {
                if (craftingGrid[i] != null && !craftingGrid[i].isEmpty()) {
                    craftingGrid[i].setCount(craftingGrid[i].getCount() - 1);
                    if (craftingGrid[i].getCount() <= 0) {
                        craftingGrid[i] = new ItemStack(BlockType.AIR, 0);
                    }
                }
            }
            
            checkCrafting();
        }
    }

    public ItemStack[] getCraftingGrid() {
        return craftingGrid;
    }

    public void cleanup() {
    }
}

class CraftingRecipe {

    private BlockType[] ingredients;
    private ItemStack result;

    public CraftingRecipe(BlockType[] ingredients, BlockType result, int count) {
        this.ingredients = ingredients;
        this.result = new ItemStack(result, count);
    }

    public boolean matches(ItemStack[] grid) {
        List<BlockType> gridTypes = new java.util.ArrayList<>();
        for (ItemStack item : grid) {
            if (item != null && !item.isEmpty()) {
                gridTypes.add(item.getType());
            }
        }

        List<BlockType> ingredientTypes = new java.util.ArrayList<>();
        for (BlockType type : ingredients) {
            ingredientTypes.add(type);
        }

        return gridTypes.containsAll(ingredientTypes) && ingredientTypes.containsAll(gridTypes);
    }

    public ItemStack getResult() {
        return result;
    }
}
