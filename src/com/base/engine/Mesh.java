package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {
    private int vbo; //a pointer to a buffer (vertex buffer object)
    private int ibo; //index buffer object
    private int size; //how many bytes of data we have

    public Mesh() {
        vbo = glGenBuffers(); //generate buffer object names
        ibo = glGenBuffers();
        size = 0;
    }

    public void addVertices(Vertex[] vertices, int[] indices) {
        size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vbo); //bind a named buffer object
        glBufferData(GL_ARRAY_BUFFER,
                Util.createFlippedBuffer(vertices),
                GL_STATIC_DRAW);
                                //creates and initializes a buffer object's data store
                                //GL_STATIC_DRAW - used to tell that we're
                                //sending static drawing data
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,
                       Util.createFlippedBuffer(indices),
                       GL_STATIC_DRAW);

    }

    public void draw() {
        glEnableVertexAttribArray(0);
            //Enable or disable a generic vertex attribute array

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        //multiplying by 4 because one float is 4 bytes
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
    }
}
