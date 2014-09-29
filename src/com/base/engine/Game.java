package com.base.engine;

import javafx.geometry.VerticalDirection;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Game {

    private static Level level;

    public Game() {
        Player player = new Player(new Vector3f(8.5f, 0.4375f, 8.5f));
        level = new Level("level1-doors.png", "WolfCollection.png", player);
        Transform.setProjection(70, Window.getWidth(), Window.getHeight(), 0.01f, 1000f);
        Transform.setCamera(player.getCamera());
    }

    public void input() {
        level.input();
    }

    public void update() {
        level.update();
    }

    public void render() {
        level.render();
    }

    public static Level getLevel() {
        return level;
    }
}
