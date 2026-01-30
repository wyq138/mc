package com.minecraft.entity;

import com.minecraft.world.World;
import com.minecraft.engine.Renderer;
import com.minecraft.entity.animal.*;
import com.minecraft.entity.monster.*;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private World world;
    private List<Entity> entities;

    public EntityManager(World world) {
        this.world = world;
        this.entities = new ArrayList<>();
    }

    public void init() {
        spawnInitialEntities();
    }

    private void spawnInitialEntities() {
        for (int i = 0; i < 20; i++) {
            float x = (float)(Math.random() * 100 - 50);
            float z = (float)(Math.random() * 100 - 50);
            float y = getSpawnHeight(x, z);
            
            int entityType = (int)(Math.random() * 4);
            Entity entity;
            
            switch (entityType) {
                case 0:
                    entity = new Pig(world, x, y, z);
                    break;
                case 1:
                    entity = new Cow(world, x, y, z);
                    break;
                case 2:
                    entity = new Sheep(world, x, y, z);
                    break;
                case 3:
                    entity = new Zombie(world, x, y, z);
                    break;
                default:
                    entity = new Pig(world, x, y, z);
            }
            
            entities.add(entity);
        }
    }

    private float getSpawnHeight(float x, float z) {
        for (int y = 100; y >= 0; y--) {
            if (world.getBlock((int)x, y, (int)z).getType().isSolid()) {
                return y + 1;
            }
        }
        return 64.0f;
    }

    public void update() {
        List<Entity> toRemove = new ArrayList<>();
        
        for (Entity entity : entities) {
            entity.update();
            entity.updatePhysics();
            
            if (entity.isDead()) {
                toRemove.add(entity);
            }
        }
        
        entities.removeAll(toRemove);
    }

    public void render(Renderer renderer) {
        for (Entity entity : entities) {
            entity.render(renderer);
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void cleanup() {
        entities.clear();
    }
}
