package com.base.engine;

import javafx.geometry.VerticalDirection;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Game {

    private Level level;

    public Game() {

        Transform.setCamera(new Camera());
        Transform.setProjection(70, Window.getWidth(), Window.getHeight(), 0.01f, 1000f);
        level = new Level("level1.png", "WolfCollection.png");
    }

    public void input() {
        Transform.getCamera().input();
    }

    public void update() {

    }

    public void render() {
        level.render();
    }
}
