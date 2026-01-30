package com.minecraft.input;

import org.lwjgl.glfw.GLFW;

public class MouseInput {

    private long window;
    private double lastX, lastY;
    private double deltaX, deltaY;
    private double scrollX, scrollY;
    private boolean[] buttons;
    private boolean scrolled;

    public MouseInput(long window) {
        this.window = window;
        this.lastX = 0;
        this.lastY = 0;
        this.deltaX = 0;
        this.deltaY = 0;
        this.scrollX = 0;
        this.scrollY = 0;
        this.buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
        this.scrolled = false;
    }

    public void update() {
        double[] x = new double[1];
        double[] y = new double[1];
        GLFW.glfwGetCursorPos(window, x, y);

        deltaX = x[0] - lastX;
        deltaY = y[0] - lastY;

        lastX = x[0];
        lastY = y[0];

        for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) {
            buttons[i] = GLFW.glfwGetMouseButton(window, i) == GLFW.GLFW_PRESS;
        }

        scrolled = false;
        scrollX = 0;
        scrollY = 0;
    }

    public float[] getDelta() {
        return new float[]{(float)deltaX, (float)deltaY};
    }

    public boolean isButtonPressed(int button) {
        return buttons[button];
    }

    public boolean isButtonReleased(int button) {
        return !buttons[button];
    }

    public void setScroll(double x, double y) {
        this.scrollX = x;
        this.scrollY = y;
        this.scrolled = true;
    }

    public boolean isScrolled() {
        return scrolled;
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public void setCursorMode(int mode) {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, mode);
    }
}
