package com.base.engine;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Java stores buffers differently than OpenGL
 * This utility recreates a buffer in the format which OpenGL expects
 */
public class Util {

    /**
     * Wrapper method for creating a
     * @param size
     * @return
     */
    public static FloatBuffer createFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

        for (int i = 0; i < vertices.length; i++) {
            buffer.put(vertices[i].getPos().getX());
            buffer.put(vertices[i].getPos().getY());
            buffer.put(vertices[i].getPos().getZ());
        }

        buffer.flip(); //puts the buffer in the proper order

        return buffer;
    }
}
