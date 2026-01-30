package com.minecraft.entity.monster;

import com.minecraft.entity.Entity;
import com.minecraft.world.World;
import com.minecraft.engine.Renderer;
import com.minecraft.player.Player;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Zombie extends Entity {

    private float moveTimer = 0;
    private float moveDirection = 0;
    private float attackCooldown = 0;

    public Zombie(World world, float x, float y, float z) {
        super(world, x, y, z);
        this.width = 0.6f;
        this.height = 1.8f;
        this.health = 20;
        this.maxHealth = 20;
    }

    @Override
    public void update() {
        moveTimer += 0.016f;
        attackCooldown -= 0.016f;

        Player player = findNearestPlayer();
        if (player != null) {
            moveTowardsPlayer(player);
            
            float distance = position.distance(player.getPosition());
            if (distance < 1.5f && attackCooldown <= 0) {
                attackPlayer(player);
                attackCooldown = 1.0f;
            }
        } else {
            wander();
        }
    }

    private Player findNearestPlayer() {
        return null;
    }

    private void moveTowardsPlayer(Player player) {
        Vector3f direction = new Vector3f(player.getPosition()).sub(position);
        moveDirection = (float)Math.atan2(direction.x, direction.z);
        
        float speed = 0.03f;
        velocity.x = (float)Math)Math.sin(moveDirection) * speed;
        velocity.z = (float)Math.cos(moveDirection) * speed;
    }

    private void attackPlayer(Player player) {
        player.takeDamage(3);
    }

    private void wander() {
        if (moveTimer > 3.0f) {
            moveTimer = 0;
            moveDirection = (float)(Math.random() * Math.PI * 2);
        }

        float speed = 0.01f;
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
