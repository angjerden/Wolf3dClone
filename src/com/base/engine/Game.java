package com.base.engine;

import javafx.geometry.VerticalDirection;
import org.lwjgl.input.Keyboard;

public class Game {

    Bitmap level;
    Shader shader;
    Material material;
    Mesh mesh;
    Transform transform;

    public Game() {
        level = new Bitmap("level1.png");

        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                System.out.print(level.getPixel(i, j));
            }
            System.out.println();
        }

        shader = BasicShader.getInstance();
        material = new Material(new Texture("test.png"));

        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3f(0, 0, 0), new Vector2f(0, 0)),
                new Vertex(new Vector3f(0, 1, 0), new Vector2f(0, 1)),
                new Vertex(new Vector3f(1, 1, 0), new Vector2f(1, 1)),
                new Vertex(new Vector3f(1, 0, 0), new Vector2f(1, 0))
        };

        int[] indices = new int[] {0, 1, 2,
                                    0, 2, 3};

        mesh = new Mesh(vertices, indices);
        transform = new Transform();
        Transform.setCamera(new Camera());
        Transform.setProjection(70, Window.getWidth(), Window.getHeight(), 0.01f, 1000f);
    }

    public void input() {

    }

    public void update() {
        Transform.getCamera().input();
    }

    public void render() {
        shader.bind();
        shader.updateUniforms(transform.getTransformation(),
                transform.getProjectedTransformation(),
                material);
        mesh.draw();
    }
}
