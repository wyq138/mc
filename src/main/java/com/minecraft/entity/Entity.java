package com.minecraft.entity;

import com.minecraft.world.World;
import com.minecraft.engine.Renderer;
import com.minecraft.physics.AABB;

import org.joml.Vector3f;
import org.joml.Matrix4f;

public abstract class Entity {

    protected World world;
    protected Vector3f position;
    protected Vector3f velocity;
    protected Vector3f rotation;
    protected AABB boundingBox;

    protected float width = 0.6f;
    protected float height = 1.8f;
    protected boolean onGround = false;
    protected boolean isDead = false;

    protected int health = 20;
    protected int maxHealth = 20;

    public Entity(World world, float x, float y, float z) {
        this.world = world;
        this.position = new Vector3f(x, y, z);
        this.velocity = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        updateBoundingBox();
    }

    public abstract void update();

    public abstract void render(Renderer renderer);

    public void updatePhysics() {
        updateBoundingBox();
        onGround = checkGroundCollision();
        applyGravity();
        checkCollisions();
    }

    private void updateBoundingBox() {
        boundingBox = new AABB(
            position.x - width / 2,
            position.y,
            position.z - width / 2,
            position.x + width / 2,
            position.y + height,
            position.z + width / 2
        );
    }

    private boolean checkGroundCollision() {
        float checkY = position.y - 0.1f;
        for (int x = (int)(position.x - width / 2); x <= (int)(position.x + width / 2); x++) {
            for (int z = (int)(position.z - width / 2); z <= (int)(position.z + width / 2); z++) {
                if (world.getBlock(x, (int)checkY, z).getType().isSolid()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void applyGravity() {
        if (!onGround) {
            velocity.y -= 0.08f;
        } else {
            velocity.y = 0;
        }
    }

    private void checkCollisions() {
        Vector3f newPos = new Vector3f(position.x + velocity.x, position.y + velocity.y, position.z + velocity.z);
        
        if (checkCollision(newPos.x, position.y, position.z)) {
            velocity.x = 0;
        }
        
        if (checkCollision(position.x, newPos.y, position.z)) {
            velocity.y = 0;
        }
        
        if (checkCollision(position.x, position.y, newPos.z)) {
            velocity.z = 0;
        }

        position.add(velocity);
    }

    private boolean checkCollision(float x, float y, float z) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = 0; dy <= (int)height; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    int bx = (int)(x + dx * width / 2);
                    int by = (int)(y + dy);
                    int bz = (int)(z + dz * width / 2);
                    
                    if (world.getBlock(bx, by, bz).getType().isSolid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            isDead = true;
            onDeath();
        }
    }

    protected void onDeath() {
    }

    public Vector3f getPosition() {
        return position;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getHealth() {
        return health;
    }
}
