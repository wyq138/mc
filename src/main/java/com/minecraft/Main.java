package com.minecraft;

import org.lwjgl.glfw.GLFW;

import com.minecraft.engine.Window;
import com.minecraft.engine.Renderer;
import com.minecraft.world.World;
import com.minecraft.world.WorldManager;
import com.minecraft.player.Player;
import com.minecraft.player.PlayerController;
import com.minecraft.input.Input;
import com.minecraft.input.MouseInput;
import com.minecraft.ui.UIManager;
import com.minecraft.audio.AudioManager;
import com.minecraft.entity.EntityManager;
import com.minecraft.crafting.CraftingSystem;

public class Main implements Runnable {

    private Thread gameThread;
    private Window window;
    private Renderer renderer;
    private World world;
    private WorldManager worldManager;
    private Player player;
    private PlayerController playerController;
    private Input input;
    private MouseInput mouseInput;
    private UIManager uiManager;
    private AudioManager audioManager;
    private EntityManager entityManager;
    private CraftingSystem craftingSystem;

    private boolean running = false;
    private boolean paused = false;

    public static final int TARGET_FPS = 60;
    public static final int TARGET_UPS = 60;

    public void start() {
        running = true;
        gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    @Override
    public void run() {
        init();
        gameLoop();
        cleanup();
    }

    private void init() {
        window = new Window("Minecraft 1.20.1 Clone", 1280, 720, true);
        window.init();
        window.setVSync(true);

        renderer = new Renderer();
        renderer.init();

        input = new Input(window);
        input.init();

        mouseInput = new MouseInput(window);
        mouseInput.init();

        audioManager = new AudioManager();
        audioManager.init();

        worldManager = new WorldManager();
        worldManager.loadWorld("world");

        world = worldManager.getCurrentWorld();
        world.init();

        entityManager = new EntityManager(world);
        entityManager.init();

        player = new Player(world);
        player.init();

        playerController = new PlayerController(player, input, mouseInput, world);
        playerController.init();

        craftingSystem = new CraftingSystem(player.getInventory());
        craftingSystem.init();

        uiManager = new UIManager(window, player, craftingSystem);
        uiManager.init();

        System.out.println("Minecraft 1.20.1 Clone initialized!");
    }

    private void gameLoop() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / TARGET_UPS;
        double delta = 0.0;
        int frames = 0;
        int updates = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1.0) {
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer >= 1000) {
                window.setTitle("Minecraft 1.20.1 Clone | FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                timer += 1000;
            }

            if (window.shouldClose()) {
                running = false;
            }
        }
    }

    private void update() {
        window.update();
        input.update();
        mouseInput.update();

        if (!paused) {
            playerController.update();
            player.update();
            world.update();
            entityManager.update();
            craftingSystem.update();
        }

        uiManager.update();
    }

    private void render() {
        renderer.clear();
        
        renderer.beginScene(player.getCamera());
        world.render(renderer);
        entityManager.render(renderer);
        renderer.endScene();

        uiManager.render();

        window.swapBuffers();
    }

    private void cleanup() {
        worldManager.saveWorld();
        
        uiManager.cleanup();
        craftingSystem.cleanup();
        entityManager.cleanup();
        playerController.cleanup();
        player.cleanup();
        world.cleanup();
        renderer.cleanup();
        window.cleanup();
        audioManager.cleanup();
        
        GLFW.glfwTerminate();
        System.out.println("Game cleaned up!");
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
