package com.base.engine;

import javafx.geometry.VerticalDirection;
import org.lwjgl.input.Keyboard;

public class Game {

    private Mesh mesh;
    private Shader shader;
    private Transform transform;

    public Game() {
        mesh = ResourceLoader.loadMesh("box.obj");
        shader = new Shader();

        transform = new Transform();

        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();

        shader.addUniform("transform");
    }

    public void input() {
        if(Input.getKeyDown(Keyboard.KEY_UP)) {
            System.out.println("We've just pressed up!");
        }

        if(Input.getKeyUp(Keyboard.KEY_UP)) {
            System.out.println("We've just released up!");
        }

        if(Input.getMouseDown(1)) {
            System.out.println("We've just right clicked at " + Input.getMousePosition());
        }
        if(Input.getMouseUp(1)) {
            System.out.println("We've just released right mouse button!");
        }
    }

    float temp = 0.0f;

    public void update() {
        temp += Time.getDelta();

        float sinTemp = (float) Math.sin(temp);

        transform.setTranslation(sinTemp, 0, 0);
        transform.setRotation(0, sinTemp * 180, 0);
        transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
    }

    public void render() {
        shader.bind();
        shader.setUniform("transform", transform.getTransformation());
        mesh.draw();
    }
}
