package com.base.engine;

import static org.lwjgl.opengl.GL11.*;

/**
 * Class to hold a pointer (an int - id)
 * to a texture object on the graphics card
 */
public class Texture {

    private int id;

    public Texture(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id); //GL_TEXTURE_2D is the target
                //We're going to use 2d textures
    }
}
