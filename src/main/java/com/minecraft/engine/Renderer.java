package com.minecraft.engine;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import com.minecraft.camera.Camera;
import com.minecraft.shader.Shader;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private Shader shader;
    private Camera currentCamera;

    private static final float FOV = 70.0f;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000.0f;

    public Renderer() {
        shader = new Shader("vertex.glsl", "fragment.glsl");
    }

    public void init() {
        shader.init();
        shader.bind();
        shader.setUniform("uTexture", 0);
        shader.unbind();
    }

    public void clear() {
        glClearColor(0.529f, 0.808f, 0.922f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void beginScene(Camera camera) {
        this.currentCamera = camera;
        shader.bind();
        
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.perspective(FOV, (float)1280 / 720, NEAR_PLANE, FAR_PLANE);
        shader.setUniform("uProjection", projectionMatrix);
    }

    public void endScene() {
        shader.unbind();
    }

    public void renderMesh(int vao, int vertexCount, Matrix4f modelMatrix) {
        shader.bind();
        
        Matrix4f viewMatrix = currentCamera.getViewMatrix();
        shader.setUniform("uView", viewMatrix);
        shader.setUniform("uModel", modelMatrix);

        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
        
        shader.unbind();
    }

    public void cleanup() {
        shader.cleanup();
    }
}
