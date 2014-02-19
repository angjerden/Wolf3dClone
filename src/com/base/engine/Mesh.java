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
        addVertices(vertices, indices, false);
    }

    public void addVertices(Vertex[] vertices,
                            int[] indices,
                            boolean calcNormals) {
        if (calcNormals) {
            calcNormals(vertices, indices);
        }

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
        //multiplying by 4 because one float is 4 bytes
        final int floatByteSize = 4;

        glEnableVertexAttribArray(0);
            //Enable or disable a generic vertex attribute array
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL_FLOAT, false, Vertex.SIZE * floatByteSize, 0);
        long textureOffset = Vertex.VERTEX_SIZE * floatByteSize; //number of vertices * size of a float in bytes
                //because we want to start at the texture data
        glVertexAttribPointer(1, Vertex.TEXTURE_SIZE, GL_FLOAT, false, Vertex.SIZE * floatByteSize, textureOffset);
        long normalOffset = Vertex.TEXTURE_SIZE * floatByteSize + textureOffset;
        glVertexAttribPointer(2, Vertex.NORMAL_SIZE, GL_FLOAT, false, Vertex.SIZE * floatByteSize, normalOffset);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    /**
     * Take in all vertices, treat them as triangles
     * and calculate the normals (up direction) of each of them.
     * @param vertices
     */
    private void calcNormals(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) { //adding by 3 because we look at every
            int i0 = indices[i];                    //individual face
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            //v1 and v2 represent the / and \ of a triangle, respectively
            Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
            Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());

            Vector3f normal = v1.cross(v2).normalized();

            //set all vertices to normal
            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setNormal(vertices[i].getNormal().normalized());
        }
    }
}
