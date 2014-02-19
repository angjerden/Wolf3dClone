package com.base.engine;

/**
 * Vertices are the "dots", which the graphics card will connect
 */
public class Vertex {
    public static final int SIZE = 8;   //How many integers or floating points
                    //are totally in this class
                    //It's used when allocating the vertex attrib pointer's stride
                    //Stride - Specifies the byte offset
                    //between consecutive generic vertex attributes.
    public static final int VERTEX_SIZE = 3;
    public static final int TEXTURE_SIZE = 2;
    public static final int NORMAL_SIZE = 3;

    private Vector3f pos;
    private Vector2f texCoord;
    private Vector3f normal;

    public Vertex(Vector3f pos) {
        this(pos, new Vector2f(0, 0));
    }

    public Vertex(Vector3f pos, Vector2f texCoord) {
        this(pos, texCoord, new Vector3f(0, 0, 0));
    }

    public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal) {
        this.pos = pos;
        this.texCoord = texCoord;
        this.normal = normal;
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

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }
}
