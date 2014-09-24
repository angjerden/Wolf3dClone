package com.base.engine;

import java.util.ArrayList;

public class Level {
    private Mesh mesh;
    private Bitmap level;
    private Shader shader;
    private Material material;
    private Transform transform;

    //Temp variable
    private Door door;

    private static final float SPOT_WIDTH = 1;
    private static final float SPOT_LENGTH = 1;
    private static final float SPOT_HEIGHT = 1;

    private static final int NUM_TEX_EXP = 4; //Number of rows of textures
    private static final int NUM_TEXTURES = (int) Math.pow(2, NUM_TEX_EXP);


    public Level(String levelName, String textureName) {
        level = new Bitmap(levelName).flipY();
        material = new Material(new Texture(textureName));
        transform = new Transform();

        shader = BasicShader.getInstance();

        generateLevel();

        //Temporary door
        Transform tempTransform = new Transform();
        tempTransform.setTranslation(new Vector3f(10.5f, 0, 9f));
        door = new Door(tempTransform, material);
    }

    public void input() {

    }

    public void update() {
        door.update();
    }

    public void render() {
        shader.bind();
        shader.updateUniforms(transform.getTransformation(),
                transform.getProjectedTransformation(),
                material);
        mesh.draw();
        door.render();
    }

    public Vector3f checkCollision(Vector3f oldPos, Vector3f newPos, float objectWidth, float objectLength) {
        Vector2f collisionVector = new Vector2f(1, 1); //we only check for collision on the X and Z axes
        Vector3f movementVector = newPos.sub(oldPos);

        if (movementVector.length() > 0){ //if we are moving
            Vector2f blockSize = new Vector2f(SPOT_WIDTH, SPOT_LENGTH);
            Vector2f objectSize = new Vector2f(objectWidth, objectLength);

            Vector2f oldPos2 = new Vector2f(oldPos.getX(), oldPos.getZ());
            Vector2f newPos2 = new Vector2f(newPos.getX(), newPos.getZ());

            //Checking for block/wall collision
            for (int i = 0; i < level.getWidth(); i++) {
                for (int j = 0; j < level.getHeight(); j++) {
                    if ((level.getPixel(i, j) & 0xFFFFFF) == 0) {
                        collisionVector = collisionVector.mul(rectCollide(oldPos2, newPos2, objectSize, blockSize.mul(new Vector2f(i, j)), blockSize));
                    }
                }
            }

            //Checking for door collision
            Vector2f doorSize = new Vector2f(Door.LENGTH, Door.WIDTH);
            Vector3f doorPos3f = door.getTransform().getTranslation();
            Vector2f doorPos2f = new Vector2f(doorPos3f.getX(), doorPos3f.getZ());
            collisionVector = collisionVector.mul(rectCollide(oldPos2, newPos2, objectSize, doorPos2f, doorSize));
        }

        return new Vector3f(collisionVector.getX(), 0, collisionVector.getY()); //returning Y on the Z-axis since
                                                                            //collisionVector is a Vector2f
    }

    /**
     * Checks on which axis the player and the obstacle intersects, collision detection
     *
     * @param oldPos Player's old position
     * @param newPos Player's new position
     * @param size1 Player's size
     * @param pos2 Object's position
     * @param size2 Object's size (The blocks)
     * @return
     */
    private Vector2f rectCollide(Vector2f oldPos, Vector2f newPos, Vector2f size1, Vector2f pos2, Vector2f size2) {
        Vector2f result = new Vector2f(0, 0); //assuming we hit something

        if (newPos.getX() + size1.getX() < pos2.getX() || //if player's right edge is less than block's left edge
                newPos.getX() - size1.getX() > pos2.getX() + size2.getX() * size2.getX() ||
                oldPos.getY() + size1.getY() < pos2.getY() ||
                oldPos.getY() - size1.getY() > pos2.getY() + size2.getY() * size2.getY()) {
            result.setX(1);
        }
        if (oldPos.getX() + size1.getX() < pos2.getX() ||
                oldPos.getX() - size1.getX() > pos2.getX() + size2.getX() * size2.getX() ||
                newPos.getY() + size1.getY() < pos2.getY() ||
                newPos.getY() - size1.getY() > pos2.getY() + size2.getY() * size2.getY()) {
            result.setY(1);
        }
        return result;
    }

    private void addFace(ArrayList<Integer> indices, int startLocation, boolean direction) {
        if (direction) {
            indices.add(startLocation + 2);
            indices.add(startLocation + 1);
            indices.add(startLocation + 0);
            indices.add(startLocation + 3);
            indices.add(startLocation + 2);
            indices.add(startLocation + 0);
        } else {
            indices.add(startLocation + 0);
            indices.add(startLocation + 1);
            indices.add(startLocation + 2);
            indices.add(startLocation + 0);
            indices.add(startLocation + 2);
            indices.add(startLocation + 3);
        }
    }

    private float[] calcTexCoords(int value) {
        int texX = value / NUM_TEXTURES;
        int texY = texX % NUM_TEX_EXP;
        texX /= NUM_TEX_EXP;

        float[] result = new float[4];

        result[0] = 1f - (float) texX / (float) NUM_TEX_EXP;
        result[1] = result[0] - 1f / (float) NUM_TEX_EXP;
        result[3] = 1f - (float) texY / (float) NUM_TEX_EXP;
        result[2] = result[3] - 1f / (float) NUM_TEX_EXP;
        return result;
    }

    private void addVertices(ArrayList<Vertex> vertices,
                             int xPos,
                             int zPos,
                             float offset,
                             boolean x,
                             boolean y,
                             boolean z,
                             float[] texCoords) {
        float XHigher = texCoords[0];
        float XLower = texCoords[1];
        float YHigher = texCoords[2];
        float YLower = texCoords[3];
        if (x && z) { //generate floor or ceiling depending on offset
            vertices.add(new Vertex(
                    new Vector3f(xPos * SPOT_WIDTH, offset * SPOT_HEIGHT, zPos * SPOT_LENGTH),
                    new Vector2f(XLower, YLower)));
            vertices.add(new Vertex(
                    new Vector3f((xPos + 1) * SPOT_WIDTH, offset * SPOT_HEIGHT, zPos * SPOT_LENGTH),
                    new Vector2f(XHigher, YLower)));
            vertices.add(new Vertex(
                    new Vector3f((xPos + 1) * SPOT_WIDTH, offset * SPOT_HEIGHT, (zPos + 1) * SPOT_LENGTH),
                    new Vector2f(XHigher, YHigher)));
            vertices.add(new Vertex(
                    new Vector3f(xPos * SPOT_WIDTH, offset * SPOT_HEIGHT, (zPos + 1) * SPOT_LENGTH),
                    new Vector2f(XLower, YHigher)));
        } else if (x && y) {
            vertices.add(new Vertex(
                    new Vector3f(xPos * SPOT_WIDTH, zPos * SPOT_HEIGHT, offset * SPOT_LENGTH),
                    new Vector2f(XLower, YLower)));
            vertices.add(new Vertex(
                    new Vector3f((xPos + 1) * SPOT_WIDTH, zPos * SPOT_HEIGHT, offset * SPOT_LENGTH),
                    new Vector2f(XHigher, YLower)));
            vertices.add(new Vertex(
                    new Vector3f((xPos + 1) * SPOT_WIDTH, (zPos + 1) * SPOT_HEIGHT, offset * SPOT_LENGTH),
                    new Vector2f(XHigher, YHigher)));
            vertices.add(new Vertex(
                    new Vector3f(xPos * SPOT_WIDTH, (zPos + 1) * SPOT_HEIGHT, offset * SPOT_LENGTH),
                    new Vector2f(XLower, YHigher)));
        } else if (y && z) {
            vertices.add(new Vertex(
                    new Vector3f(offset * SPOT_WIDTH, xPos * SPOT_HEIGHT, zPos * SPOT_LENGTH),
                    new Vector2f(XLower, YLower)));
            vertices.add(new Vertex(
                    new Vector3f(offset * SPOT_WIDTH, xPos * SPOT_HEIGHT, (zPos + 1) * SPOT_LENGTH),
                    new Vector2f(XHigher, YLower)));
            vertices.add(new Vertex(
                    new Vector3f(offset * SPOT_WIDTH, (xPos + 1) * SPOT_HEIGHT, (zPos + 1) * SPOT_LENGTH),
                    new Vector2f(XHigher, YHigher)));
            vertices.add(new Vertex(
                    new Vector3f(offset * SPOT_WIDTH, (xPos + 1) * SPOT_HEIGHT, zPos * SPOT_LENGTH),
                    new Vector2f(XLower, YHigher)));
        } else {
            System.out.println("You've done screwed up now! Invalid plane used in level generator");
            new Exception().printStackTrace();
            System.exit(1);
        }
    }

    private void generateLevel() {
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Integer> indices = new ArrayList<Integer>();

        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {

                //if the pixel is black, it's a wall, don't generate floor
                if ((level.getPixel(i, j) & 0xFFFFFF) == 0) {
                    continue;
                }

                float[] texCoords = calcTexCoords(((level.getPixel(i, j) & 0x00FF00) >> 8));

                //Generate floor
                addFace(indices, vertices.size(), true);
                addVertices(vertices, i, j, 0, true, false, true, texCoords);

                //Generate ceiling
                addFace(indices, vertices.size(), false);
                addVertices(vertices, i, j, SPOT_HEIGHT, true, false, true, texCoords);

                //Generate walls
                texCoords = calcTexCoords(((level.getPixel(i, j) & 0xFF0000) >> 16));

                if ((level.getPixel(i, j - 1) & 0xFFFFFF) == 0) {
                    addFace(indices, vertices.size(), false);
                    addVertices(vertices, i, 0, j, true, true, false, texCoords);
                }
                if ((level.getPixel(i, j + 1) & 0xFFFFFF) == 0) {
                    addFace(indices, vertices.size(), true);
                    addVertices(vertices, i, 0, (j + 1), true, true, false, texCoords);
                }
                if ((level.getPixel(i - 1, j) & 0xFFFFFF) == 0) {
                    addFace(indices, vertices.size(), true);
                    addVertices(vertices, 0, j, i, false, true, true, texCoords);
                }
                if ((level.getPixel(i + 1, j) & 0xFFFFFF) == 0) {
                    addFace(indices, vertices.size(), false);
                    addVertices(vertices, 0, j, (i + 1), false, true, true, texCoords);
                }
            }
        }

        Vertex[] vertArray = new Vertex[vertices.size()];
        Integer[] indicesArray = new Integer[indices.size()];

        vertices.toArray(vertArray);
        indices.toArray(indicesArray);

        mesh = new Mesh(vertArray, Util.toIntArray(indicesArray));
    }

    public Shader getShader() {
        return shader;
    }
}
