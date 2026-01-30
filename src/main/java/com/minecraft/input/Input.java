package com.minecraft.input;

import org.lwjgl.glfw.GLFW;

public class Input {

    private long window;
    private boolean[] keys;

    public Input(long window) {
        this.window = window;
        this.keys = new boolean[GLFW.GLFW_KEY_LAST];
    }

    public void update() {
        for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) {
            keys[i] = GLFW.glfwGetKey(window, i) == GLFW.GLFW_PRESS;
        }
    }

    public boolean isKeyPressed(int key) {
        return keys[key];
    }

    public boolean isKeyReleased(int key) {
        return !keys[key];
    }
}
