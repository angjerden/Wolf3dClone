package com.base.engine;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;

import static org.lwjgl.opengl.GL11.*;

/**
 * Class to hold a pointer (an int - id)
 * to a texture object on the graphics card
 */
public class Texture {

    private int id;

    public Texture(String fileName) {
        this(loadTexture(fileName));
    }

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

    private static int loadTexture(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try {
            int id = TextureLoader.getTexture(
                    ext,
                    new FileInputStream(
                            new File("./res/textures/" + fileName)))
            .getTextureID();

            return id;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return 0;
    }
}
