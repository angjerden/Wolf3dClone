package com.base.engine;

public class Vector2f {

    private float x;
    private float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the length of the vector using the Pythagorean theorem
     *
     * @return length of the vector as a float
     */
    public float length() {
        return (float)Math.sqrt(x * x + y * y);
    }

    /**
     * Multiplies one vector with another:
     * a scalar function of two vectors, equal to
     * the product of their magnitudes and the cosine
     * of the angle between them.
     *
     * @param r The input vector to be "dot produced" with
     * @return the effect on  as a float
     */
    public float dot(Vector2f r) {
        return x * r.getX() + y * r.getY();
    }

    public Vector2f normalized() {
        float length = length();

        return new Vector2f(x / length, y / length);
    }

    public Vector2f rotate(float angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float)(x * cos - y * sin), (float)(x * sin + y * cos));
    }

    public Vector2f add(Vector2f r) {
        return new Vector2f(x + r.getX(), y + r.getY());
    }

    public Vector2f add(float r) {
        return new Vector2f(x + r, y + r);
    }

    public Vector2f sub(Vector2f r) {
        return new Vector2f(x - r.getX(), y - r.getY());
    }

    public Vector2f sub(float r) {
        return new Vector2f(x - r, y - r);
    }

    public Vector2f mul(Vector2f r) {
        return new Vector2f(x * r.getX(), y * r.getY());
    }

    public Vector2f mul(float r) {
        return new Vector2f(x * r, y * r);
    }

    public Vector2f div(Vector2f r) {
        return new Vector2f(x / r.getX(), y / r.getY());
    }

    public Vector2f div(float r) {
        return new Vector2f(x / r, y / r);
    }

    public Vector2f abs() {
        return new Vector2f(Math.abs(x), Math.abs(y));
    }

    public String toString() {
        return "(" + x + " " + y + ")";
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
