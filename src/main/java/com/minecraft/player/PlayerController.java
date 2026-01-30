package com.minecraft.player;

import com.minecraft.world.World;
import com.minecraft.input.Input;
import com.minecraft.input.MouseInput;
import com.minecraft.block.BlockInteraction;

import org.lwjgl.glfw.GLFW;

public class PlayerController {

    private Player player;
    private Input input;
    private MouseInput mouseInput;
    private World world;
    private BlockInteraction blockInteraction;

    private float mouseSensitivity = 0.1f;
    private boolean mouseLocked = true;

    public PlayerController(Player player, Input input, MouseInput mouseInput, World world) {
        this.player = player;
        this.input = input;
        this.mouseInput = mouseInput;
        this.world = world;
        this.blockInteraction = new BlockInteraction(world);
    }

    public void init() {
        lockMouse();
    }

    public void update() {
        handleKeyboardInput();
        handleMouseInput();
        updateBlockInteraction();
    }

    private void handleKeyboardInput() {
        if (input.isKeyPressed(GLFW.GLFW_KEY_W)) {
            player.moveForward(1.0f);
        }
        if (input.isKeyPressed(GLFW.GLFW_KEY_S)) {
            player.moveBackward(1.0f);
        }
        if (input.isKeyPressed(GLFW.GLFW_KEY_A)) {
            player.moveLeft(1.0f);
        }
        if (input.isKeyPressed(GLFW.GLFW_KEY_D)) {
            player.moveRight(1.0f);
        }
        if (input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            player.jump();
        }
        if (input.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            player.setSneaking(true);
        } else {
            player.setSneaking(false);
        }
        if (input.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            player.setSprinting(true);
        } else {
            player.setSprinting(false);
        }

        for (int i = 0; i < 9; i++) {
            if (input.isKeyPressed(GLFW.GLFW_KEY_1 + i)) {
                player.setSelectedSlot(i);
            }
        }
    }

    private void handleMouseInput() {
        float[] mouseDelta = mouseInput.getDelta();
        player.rotate(-mouseDelta[0] * mouseSensitivity, -mouseDelta[1] * mouseSensitivity);

        if (mouseInput.isButtonPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            blockInteraction.breakBlock();
        }

        if (mouseInput.isButtonPressed(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            blockInteraction.placeBlock();
        }

        if (mouseInput.isButtonPressed(GLFW.GLFW_MOUSE_BUTTON_MIDDLE)) {
            blockInteraction.pickBlock();
        }

        if (mouseInput.isScrolled()) {
            float scroll = mouseInput.getScrollY();
            int currentSlot = player.getSelectedSlot();
            int newSlot = (int)((currentSlot + scroll) % 9);
            if (newSlot < 0) newSlot += 9;
            player.setSelectedSlot(newSlot);
        }
    }

    private void updateBlockInteraction() {
        blockInteraction.update(player.getPosition(), player.getRotation().y, player.getRotation().x);
    }

    private void lockMouse() {
        if (mouseLocked) {
            mouseInput.setCursorMode(GLFW.GLFW_CURSOR_DISABLED);
        }
    }

    public void cleanup() {
    }
}
