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

        glBindBuffer(GL_ARRAY_BUFFER, vbo); //bind a named buffer object
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
                                //creates and initializes a buffer object's data store
                                //GL_STATIC_DRAW - used to tell that we're
                                //sending static drawing data
    }

    public void draw() {
        glEnableVertexAttribArray(0);
            //Enable or disable a generic vertex attribute array

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        //multiplying by 4 because one float is 4 bytes
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glDrawArrays(GL_TRIANGLES, 0, size); //render primitives from array data

        glDisableVertexAttribArray(0);
    }
}
