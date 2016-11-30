package io.mincongh.entity;

/**
 * @author Mincong Huang
 */
public class Rectangle {

    private int id;
    private int surface;

    public Rectangle(int id, int surface) {
        this.id = id;
        this.surface = surface;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return "Rectangle [id=" + id + ", surface=" + surface + "]";
    }
}
