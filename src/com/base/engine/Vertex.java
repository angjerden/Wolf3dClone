package com.base.engine;

/**
 * Vertices are the "dots", which the graphics card will connect
 */
public class Vertex {
    public static final int SIZE = 3;   //How many integers
                                        //are totally in this class
    private Vector3f pos;

    public Vertex(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
}
