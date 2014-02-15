package com.base.engine;


public class BasicShader extends Shader {
    public BasicShader() {
        super();

        addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        compileShader();

        addUniform("transform");
        addUniform("color");
    }

    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix,
                               Material material) {
        if (material.getTexture() != null) {
            material.getTexture().bind();
        } else {
            RenderUtil.unbindTextures();
        }
        setUniform("transform", projectedMatrix);
        setUniform("color", material.getColor());
    }
}
