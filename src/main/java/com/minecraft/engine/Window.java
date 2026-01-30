package com.minecraft.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {

    private long windowHandle;
    private int width;
    private int height;
    private String title;
    private boolean vsync;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.vsync = false;
    }

    public void init() {
        if (!GLFW.glfwInit()) {
            System.err.println("Failed to initialize GLFW");
            return;
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        windowHandle = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (windowHandle == 0) {
            System.err.println("Failed to create window");
            GLFW.glfwTerminate();
            return;
        }

        GLFW.glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        GLFW.glfwSetWindowSizeCallback(windowHandle, (window, w, h) -> {
            this.width = w;
            this.height = h;
            GL11.glViewport(0, 0, w, h);
        });

        setVsync(vsync);
        GLFW.glfwShowWindow(windowHandle);
    }

    public void update() {
        GLFW.glfwSwapBuffers(windowHandle);
        GLFW.glfwPollEvents();
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
        GLFW.glfwSwapInterval(vsync ? 1 : 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public void cleanup() {
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
    }
}
