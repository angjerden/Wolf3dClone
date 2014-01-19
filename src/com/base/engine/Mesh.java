package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {
    private int vbo; //a pointer to a buffer
    private int size; //how many bytes of data we have

    public Mesh() {
        vbo = glGenBuffers(); //generate buffer object names
        size = 0;
    }

    public void addVertices(Vertex[] vertices) {
        //size = vertices.length * Vertex.SIZE;
        size = vertices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
                                //GL_STATIC_DRAW - used to tell that we're
                                //sending static drawing data
    }

    public void draw() {
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        //multiplying by 4 because one float is 4 bytes
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glDrawArrays(GL_TRIANGLES, 0, size);

        glDisableVertexAttribArray(0);
    }
}
