package com.base.engine;

import org.lwjgl.util.vector.*;

/**
 * Should handle all our transformation processes:
 * - Translation
 * - Rotation
 * - Scale
 *
 * Wraps a Vector3f + all the transformative properties
 * A way to represent this is through matrices.
 */
public class Transform {
    //Variables for projection
    private static float zNear; //how close an object has to be to us,
                                //before it clips
    private static float zFar;  //how far it can be from us before it clips
    private static float width;
    private static float height;
    private static float fov; //field of view, an angle away from our camera


    //Variables for transformation
    private Vector3f translation; //movement aligned with axes
    private Vector3f rotation; //movement around axes
    private Vector3f scale;

    public Transform() {
        translation = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        scale = new Vector3f(1, 1, 1);
    }

    public Matrix4f getTransformation() {
        Matrix4f translationMatrix = new Matrix4f().initTranslation(
                translation.getX(), translation.getY(), translation.getZ()
        );
        Matrix4f rotationMatrix = new Matrix4f().initRotation(
                rotation.getX(), rotation.getY(), rotation.getZ()
        );
        Matrix4f scaleMatrix = new Matrix4f().initScale(
                scale.getX(), scale.getY(), scale.getZ()
        );

        return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
    }

    public Matrix4f getProjectedTransformation() {
        Matrix4f transformationMatrix = getTransformation();
        Matrix4f projectionMatrix = new Matrix4f().initProjection(
                fov,
                width,
                height,
                zNear,
                zFar);
        return projectionMatrix.mul(transformationMatrix);
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public static void setProjection(float fov,
                                     float width,
                                     float height,
                                     float zNear,
                                     float zFar) {
        Transform.fov = fov;
        Transform.width = width;
        Transform.height = height;
        Transform.zNear = zNear;
        Transform.zFar = zFar;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation = new Vector3f(x, y, z);
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);
    }
}
