package com.minecraft.world;

import com.minecraft.nbt.NBTManager;
import com.minecraft.nbt.NBTTagCompound;

import java.io.File;

public class WorldManager {

    private World currentWorld;
    private NBTManager nbtManager;
    private String worldPath;

    public WorldManager() {
        nbtManager = new NBTManager();
        worldPath = "saves/";
    }

    public void loadWorld(String worldName) {
        File worldDir = new File(worldPath + worldName);
        if (!worldDir.exists()) {
            worldDir.mkdirs();
        }

        File levelDat = new File(worldDir, "level.dat");
        
        if (levelDat.exists()) {
            NBTTagCompound levelData = nbtManager.readNBT(levelDat);
            long seed = levelData.getLong("RandomSeed");
            currentWorld = new World(worldName);
            currentWorld.init();
        } else {
            currentWorld = new World(worldName);
            currentWorld.init();
            saveWorld();
        }
    }

    public void saveWorld() {
        if (currentWorld != null) {
            File worldDir = new File(worldPath + currentWorld.getWorldName());
            if (!worldDir.exists()) {
                worldDir.mkdirs();
            }

            File levelDat = new File(worldDir, "level.dat");
            
            NBTTagCompound levelData = new NBTTagCompound();
            levelData.setString("LevelName", currentWorld.getWorldName());
            levelData.setLong("RandomSeed", currentWorld.getSeed());
            levelData.setInt("DataVersion", 2681);
            levelData.setInt("GameType", 0);
            levelData.setBoolean("hardcore", false);
            levelData.setBoolean("allowCommands", true);
            levelData.setBoolean("initialized", true);

            nbtManager.writeNBT(levelDat, levelData);
            
            saveChunks(worldDir);
            
            System.out.println("World saved: " + currentWorld.getWorldName());
        }
    }

    private void saveChunks(File worldDir) {
        File regionDir = new File(worldDir, "region");
        if (!regionDir.exists()) {
            regionDir.mkdirs();
        }
    }

    public World getCurrentWorld() {
        return currentWorld;
    }
}
