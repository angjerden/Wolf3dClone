package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

public class RenderUtil {

    /**
     * erase everything on the screen
     */
    public static void clearScreen() {
        //TODO: Stencil buffer

        //clear all the colors from the screen
        //and clear the depth buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Enabling or disabling the textures
     * @param enabled
     */
    public static void setTextures(boolean enabled) {
        if(enabled) {
            glEnable(GL_TEXTURE_2D);
        } else {
            glDisable(GL_TEXTURE_2D);
        }
    }

    public static void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static void setClearColor(Vector3f color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    /**
     * set everything to default values
     */
    public static void initGraphics() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); //complete blackness

        glFrontFace(GL_CW); //every face drawn in clockwise order
                        // is going to be the front face
        glCullFace(GL_BACK); //don't draw the back-face
        glEnable(GL_CULL_FACE); //enables back-face culling,
                                // renders back sides of things
        glEnable(GL_DEPTH_TEST); //determining draw order

        glEnable(GL_DEPTH_CLAMP); //Prevents the camera from clipping through the mesh

        glEnable(GL_TEXTURE_2D); //enabling and using textures
        //glEnable(GL_FRAMEBUFFER_SRGB); //free gamma correction
                                        //exponential correction
    }

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }
}
