package com.base.engine;

/**
 * Vertices are the "dots", which the graphics card will connect
 */
public class Vertex {
    public static final int SIZE = 5;   //How many integers or floating points
                    //are totally in this class
                    //It's used when allocating the vertex attrib pointer's stride
                    //Stride - Specifies the byte offset
                    //between consecutive generic vertex attributes.

    private Vector3f pos;
    private Vector2f texCoord;

    public Vertex(Vector3f pos) {
        this(pos, new Vector2f(0, 0));
    }

    public Vertex(Vector3f pos, Vector2f texCoord) {
        this.pos = pos;
        this.texCoord = texCoord;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector2f getTexCoord() {
        return texCoord;
    }

    public void setTexCoord(Vector2f texCoord) {
        this.texCoord = texCoord;
    }
}
