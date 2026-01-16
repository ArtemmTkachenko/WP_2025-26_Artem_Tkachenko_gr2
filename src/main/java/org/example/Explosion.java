package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Explosion {

    private double radius = 6;
    private boolean alive = true;

    public void update() {
        radius += 4;
        if (radius > 70) alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void render(GraphicsContext g, double cx, double cy) {
        g.setStroke(Color.ORANGE);
        g.setLineWidth(5);
        g.strokeOval(cx - radius, cy - radius, radius * 2, radius * 2);

        g.setStroke(Color.RED);
        g.setLineWidth(3);
        g.strokeOval(cx - radius / 2, cy - radius / 2, radius, radius);
    }
}
