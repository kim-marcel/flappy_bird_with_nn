package flappybirdwithnn;

import java.awt.*;

public class Bird {

    private static final int POSITION_X = 200;
    private static final int RADIUS = 15;
    private static final int LIFT = -6;
    private static final double GRAVITY = 0.2;

    private double positionY;
    private double velocity;

    Color color;

    public Bird(Point panelSize, Color color){
        this.positionY = panelSize.y / 2;
        this.velocity = 0;
        this.color = color;
    }

    public void draw(Graphics g, Point panelSize){
        Color background = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        g.setColor(background);
        g.fillOval(POSITION_X - RADIUS - 1, (int) (positionY - RADIUS) + 1, RADIUS * 2, RADIUS * 2);
        g.fillOval(POSITION_X - RADIUS - 1, (int) (positionY - RADIUS) - 1, RADIUS * 2, RADIUS * 2);
        g.fillOval(POSITION_X - RADIUS + 1, (int) (positionY - RADIUS) + 1, RADIUS * 2, RADIUS * 2);
        g.fillOval(POSITION_X - RADIUS + 1, (int) (positionY - RADIUS) - 1, RADIUS * 2, RADIUS * 2);
        g.setColor(color);
        g.fillOval(POSITION_X - RADIUS, (int) (positionY - RADIUS), RADIUS * 2, RADIUS * 2);

        update(panelSize);
    }

    private void update(Point panelSize){
        velocity += GRAVITY;
        positionY += velocity;

        stayInBoundaries(panelSize);
    }

    public void fly(){
        velocity = LIFT;
    }

    private void stayInBoundaries(Point panelSize) {
        if (positionY >= panelSize.y - RADIUS) {
            positionY = panelSize.y - RADIUS;
            velocity = 0;
        }

        if (positionY <= RADIUS) {
            positionY = RADIUS;
            velocity = 0;
        }
    }

    public int getPositionX() {
        return POSITION_X;
    }

    public int getRadius() {
        return RADIUS;
    }

    public double getPositionY() {
        return positionY;
    }
}
