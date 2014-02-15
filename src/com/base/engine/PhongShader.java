package com.base.engine;


public class PhongShader extends Shader{
    private static final PhongShader instance = new PhongShader();
    private static Vector3f ambientLight;


    public static PhongShader getInstance() {
        return instance;
    }
    
    private PhongShader() {
        super();

        addVertexShader(ResourceLoader.loadShader("phongVertex.vs"));
        addFragmentShader(ResourceLoader.loadShader("phongFragment.fs"));
        compileShader();

        addUniform("transform");
        addUniform("baseColor");
        addUniform("ambientLight");
    }

    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix,
                               Material material) {
        if (material.getTexture() != null) {
            material.getTexture().bind();
        } else {
            RenderUtil.unbindTextures();
        }
        setUniform("transform", projectedMatrix);
        setUniform("baseColor", material.getColor());
        setUniform("ambientLight", ambientLight);
    }

    public static Vector3f getAmbientLight() {
        return ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }
}
