package com.minecraft.shader;

import org.lwjgl.opengl.GL20;
import org.joml.Matrix4f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Shader {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    public Shader() {
        this.programID = 0;
        this.vertexShaderID = 0;
        this.fragmentShaderID = 0;
    }

    public void init() {
        String vertexSource = readShaderSource("src/main/resources/shaders/vertex.glsl");
        String fragmentSource = readShaderSource("src/main/resources/shaders/fragment.glsl");

        vertexShaderID = compileShader(vertexSource, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = compileShader(fragmentSource, GL20.GL_FRAGMENT_SHADER);

        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        GL20.glLinkProgram(programID);

        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0) {
            System.err.println("Shader linking failed: " + GL20.glGetProgramInfoLog(programID));
        }
    }

    private String readShaderSource(String filePath) {
        StringBuilder source = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                source.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Failed to read shader file: " + e.getMessage());
        }
        return source.toString();
    }

    private int compileShader(String source, int type) {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, source);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0) {
            System.err.println("Shader compilation failed: " + GL20.glGetShaderInfoLog(shaderID));
        }

        return shaderID;
    }

    public void bind() {
        GL20.glUseProgram(programID);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void setUniformMat4(String name, Matrix4f matrix) {
        int location = GL20.glGetUniformLocation(programID, name);
        float[] data = new float[16];
        matrix.get(data);
        GL20.glUniformMatrix4fv(location, false, data);
    }

    public void cleanup() {
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }
}
