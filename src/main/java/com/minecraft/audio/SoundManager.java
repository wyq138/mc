package com.minecraft.audio;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private AudioManager audioManager;
    private Map<String, Integer> soundBuffers;
    private Map<String, Integer> soundSources;

    public SoundManager(AudioManager audioManager) {
        this.audioManager = audioManager;
        this.soundBuffers = new HashMap<>();
        this.soundSources = new HashMap<>();
    }

    public void init() {
        loadSounds();
    }

    private void loadSounds() {
        String[] soundFiles = {
            "step_stone",
            "step_grass",
            "step_dirt",
            "step_wood",
            "break_block",
            "place_block",
            "hurt",
            "death",
            "ambient_day",
            "ambient_night",
            "ambient_cave"
        };

        for (String sound : soundFiles) {
            int buffer = audioManager.createBuffer();
            audioManager.loadSound(buffer, "assets/sounds/" + sound + ".wav");
            soundBuffers.put(sound, buffer);

            int source = audioManager.createSource();
            soundSources.put(sound, source);
        }
    }

    public void playSound(String soundName) {
        if (soundBuffers.containsKey(soundName) && soundSources.containsKey(soundName)) {
            int buffer = soundBuffers.get(soundName);
            int source = soundSources.get(soundName);
            audioManager.playSound(source, buffer);
        }
    }

    public void playSoundAt(String soundName, float x, float y, float z) {
        if (soundBuffers.containsKey(soundName) && soundSources.containsKey(soundName)) {
            int buffer = soundBuffers.get(soundName);
            int source = soundSources.get(soundName);
            audioManager.setPosition(source, x, y, z);
            audioManager.playSound(source, buffer);
        }
    }

    public void setVolume(String soundName, float volume) {
        if (soundSources.containsKey(soundName)) {
            int source = soundSources.get(soundName);
            audioManager.setVolume(source, volume);
        }
    }

    public void setPitch(String soundName, float pitch) {
        if (soundSources.containsKey(soundName)) {
            int source = soundSources.get(soundName);
            audioManager.setPitch(source, pitch);
        }
    }

    public void setListenerPosition(float x, float y, float z) {
        audioManager.setListenerPosition(x, y, z);
    }

    public void setListenerOrientation(float atX, float atY, float atZ, float upX, float upY, float upZ) {
        audioManager.setListenerOrientation(atX, atY, atZ, upX, upY, upZ);
    }

    public void cleanup() {
        for (int source : soundSources.values()) {
            audioManager.deleteSource(source);
        }
        for (int buffer : soundBuffers.values()) {
            audioManager.deleteBuffer(buffer);
        }
        soundSources.clear();
        soundBuffers.clear();
    }
}
