package com.minecraft.entity.animal;

import com.minecraft.entity.Entity;
import com.minecraft.world.World;
import com.minecraft.engine.Renderer;

import org.joml.Matrix4f;

public class Sheep extends Entity {

    private float moveTimer = 0;
    private float moveDirection = 0;
    private boolean isSheared = false;

    public Sheep(World world, float x, float y, float z) {
        super(world, x, y, z);
        this.width = 0.9f;
        this.height = 1.3f;
        this.health = 8;
        this.maxHealth = 8;
    }

    @Override
    public void update() {
        moveTimer += 0.016f;
        
        if (moveTimer > 5.0f) {
            moveTimer = 0;
            moveDirection = (float)(Math.random() * Math.PI * 2);
        }

        float speed = 0.018f;
        velocity.x = (float)Math.sin(moveDirection) * speed;
        velocity.z = (float)Math.cos(moveDirection) * speed;
    }

    @Override
    public void render(Renderer renderer) {
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.translate(position.x, position.y, position.z);
        modelMatrix.rotateY(moveDirection);
    }
}
