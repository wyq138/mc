package com.minecraft.audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.AL10;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class AudioManager {

    private long device;
    private long context;
    private boolean initialized;

    public AudioManager() {
        this.initialized = false;
    }

    public void init() {
        device = ALC.alcOpenDevice(null);
        if (device == 0) {
            System.err.println("Failed to open OpenAL device");
            return;
        }

        context = ALC.alcCreateContext(device, null);
        if (context == 0) {
            System.err.println("Failed to create OpenAL context");
            ALC.alcCloseDevice(device);
            return;
        }

        ALC.alcMakeContextCurrent(context);
        AL.createCapabilities(ALC.alcGetCapabilities(device));

        initialized = true;
    }

    public void cleanup() {
        if (initialized) {
            ALC.alcDestroyContext(context);
            ALC.alcCloseDevice(device);
            initialized = false;
        }
    }

    public int createSource() {
        IntBuffer source = IntBuffer.allocate(1);
        AL10.alGenSources(source);
        return source.get(0);
    }

    public void deleteSource(int source) {
        IntBuffer buffer = IntBuffer.allocate(1);
        buffer.put(source);
        AL10.alDeleteSources(buffer);
    }

    public int createBuffer() {
        IntBuffer buffer = IntBuffer.allocate(1);
        AL10.alGenBuffers(buffer);
        return buffer.get(0);
    }

    public void deleteBuffer(int buffer) {
        IntBuffer buf = IntBuffer.allocate(1);
        buf.put(buffer);
        AL10.alDeleteBuffers(buf);
    }

    public void loadSound(int buffer, String filePath) {
    }

    public void playSound(int source, int buffer) {
        AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(source);
    }

    public void stopSound(int source) {
        AL10.alSourceStop(source);
    }

    public void setVolume(int source, float volume) {
        AL10.alSourcef(source, AL10.AL_GAIN, volume);
    }

    public void setPitch(int source, float pitch) {
        AL10.alSourcef(source, AL10.AL_PITCH, pitch);
    }

    public void setPosition(int source, float x, float y, float z) {
        AL10.alSource3f(source, AL10.AL_POSITION, x, y, z);
    }

    public void setListenerPosition(float x, float y, float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
    }

    public void setListenerOrientation(float atX, float atY, float atZ, float upX, float upY, float upZ) {
        float[] orientation = {atX, atY, atZ, upX, upY, upZ};
        AL10.alListenerfv(AL10.AL_ORIENTATION, orientation);
    }
}
