package com.minecraft.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f rotation;
    private Matrix4f viewMatrix;

    public Camera() {
        this.position = new Vector3f(0.0f, 20.0f, 0.0f);
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        this.viewMatrix = new Matrix4f();
    }

    public Matrix4f getViewMatrix() {
        viewMatrix.identity();
        viewMatrix.rotateX((float)Math.toRadians(rotation.x));
        viewMatrix.rotateY((float)Math.toRadians(rotation.y));
        viewMatrix.rotateZ((float)Math.toRadians(rotation.z));
        viewMatrix.translate(-position.x, -position.y, -position.z);
        return viewMatrix;
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
    }

    public void setRotation(float pitch, float yaw, float roll) {
        rotation.set(pitch, yaw, roll);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void moveForward(float amount) {
        float rad = (float)Math.toRadians(rotation.y);
        position.x -= Math.sin(rad) * amount;
        position.z -= Math.cos(rad) * amount;
    }

    public void moveBackward(float amount) {
        float rad = (float)Math.toRadians(rotation.y);
        position.x += Math.sin(rad) * amount;
        position.z += Math.cos(rad) * amount;
    }

    public void moveLeft(float amount) {
        float rad = (float)Math.toRadians(rotation.y - 90);
        position.x -= Math.sin(rad) * amount;
        position.z -= Math.cos(rad) * amount;
    }

    public void moveRight(float amount) {
        float rad = (float)Math.toRadians(rotation.y - 90);
        position.x += Math.sin(rad) * amount;
        position.z += Math.cos(rad) * amount;
    }

    public void moveUp(float amount) {
        position.y += amount;
    }

    public void moveDown(float amount) {
        position.y -= amount;
    }
}
