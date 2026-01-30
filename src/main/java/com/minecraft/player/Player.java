package com.minecraft.player;

import com.minecraft.world.World;
import com.minecraft.engine.Camera;
import com.minecraft.inventory.Inventory;
import com.minecraft.physics.AABB;
import com.minecraft.physics.Physics;

import org.joml.Vector3f;

public class Player {

    private World world;
    private Camera camera;
    private Inventory inventory;
    private Physics physics;

    private Vector3f position;
    private Vector3f velocity;
    private Vector3f rotation;

    private float width = 0.6f;
    private float height = 1.8f;
    private float eyeHeight = 1.62f;

    private boolean onGround = false;
    private boolean isSneaking = false;
    private boolean isSprinting = false;

    private int selectedSlot = 0;

    public Player(World world) {
        this.world = world;
        this.position = new Vector3f(0.0f, 64.0f, 0.0f);
        this.velocity = new Vector3f(0.0f, 0.0f, 0.0f);
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        this.camera = new Camera();
        this.inventory = new Inventory();
        this.physics = new Physics(world);
    }

    public void init() {
        camera.setPosition(position.x, position.y + eyeHeight, position.z);
    }

    public void update() {
        updatePosition();
        updateCamera();
        updatePhysics();
    }

    private void updatePosition() {
        position.add(velocity.x * 0.016f, velocity.y * 0.016f, velocity.z * 0.016f);
    }

    private void updateCamera() {
        camera.setPosition(position.x, position.y + eyeHeight, position.z);
        camera.setRotation(rotation.x, rotation.y, rotation.z);
    }

    private void updatePhysics() {
        AABB playerBox = new AABB(
            position.x - width / 2,
            position.y,
            position.z - width / 2,
            position.x + width / 2,
            position.y + height,
            position.z + width / 2
        );

        onGround = physics.checkGroundCollision(playerBox);
        physics.applyGravity(velocity, onGround);
        physics.checkCollisions(playerBox, velocity);
    }

    public void moveForward(float amount) {
        float speed = isSprinting ? 0.1f : 0.06f;
        if (isSneaking) speed *= 0.3f;
        
        float rad = (float)Math.toRadians(rotation.y);
        velocity.x -= Math.sin(rad) * amount * speed;
        velocity.z -= Math.cos(rad) * amount * speed;
    }

    public void moveBackward(float amount) {
        float speed = isSprinting ? 0.1f : 0.06f;
        if (isSneaking) speed *= 0.3f;
        
        float rad = (float)Math.toRadians(rotation.y);
        velocity.x += Math.sin(rad) * amount * speed;
        velocity.z += Math.cos(rad) * amount * speed;
    }

    public void moveLeft(float amount) {
        float speed = isSprinting ? 0.1f : 0.06f;
        if (isSneaking) speed *= 0.3f;
        
        float rad = (float)Math.toRadians(rotation.y - 90);
        velocity.x -= Math.sin(rad) * amount * speed;
        velocity.z -= Math.cos(rad) * amount * speed;
    }

    public void moveRight(float amount) {
        float speed = isSprinting ? 0.1f : 0.06f;
        if (isSneaking) speed *= 0.3f;
        
        float rad = (float)Math.toRadians(rotation.y - 90);
        velocity.x += Math.sin(rad) * amount * speed;
        velocity.z += Math.cos(rad) * amount * speed;
    }

    public void jump() {
        if (onGround) {
            velocity.y = 0.42f;
            onGround = false;
        }
    }

    public void rotate(float pitch, float yaw) {
        rotation.x += pitch;
        rotation.y += yaw;

        rotation.x = Math.max(-90, Math.min(90, rotation.x));
    }

    public void setSneaking(boolean sneaking) {
        this.isSneaking = sneaking;
        eyeHeight = sneaking ? 1.54f : 1.62f;
        height = sneaking ? 1.5f : 1.8f;
    }

    public void setSprinting(boolean sprinting) {
        this.isSprinting = sprinting;
    }

    public void setSelectedSlot(int slot) {
        if (slot >= 0 && slot < 9) {
            this.selectedSlot = slot;
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void cleanup() {
    }
}
