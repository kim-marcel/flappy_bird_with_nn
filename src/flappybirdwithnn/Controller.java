package flappybirdwithnn;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Controller {

    private static final int SPACE = 250;

    private ArrayList<Pipe> pipes;
    private Bird bird;

    private Pipe currentPipe;

    private Point panelSize;
    private int score;

    private Color color;

    public Controller(Point panelSize, Color color) {
        this.panelSize = panelSize;

        pipes = new ArrayList<>();
        bird = new Bird(panelSize, color);

        score = 0;

        this.color = color;
        initializePipes();
    }

    private void initializePipes() {
        int positionX = panelSize.x;
        int numberOfPipes = panelSize.x / SPACE + 1;

        for (int i = 0; i < numberOfPipes; i++) {
            pipes.add(new Pipe(positionX, panelSize, color));
            positionX += SPACE;
        }

        currentPipe = pipes.get(0);
    }

    public void drawAll(Graphics g) {
        drawPipes(g);
        drawBird(g);

        findCurrentPipe();
        collisionDetection();

        drawScore(g);
    }

    private void findCurrentPipe() {
        int birdPositionX = bird.getPositionX();
        int birdRadius = bird.getRadius();

        for (Pipe pipe : pipes) {
            if (pipe.getPositionX() + pipe.getWidth() >= birdPositionX - birdRadius) {
                currentPipe = pipe;
                return;
            }
        }
    }

    private void drawPipes(Graphics g) {
        Pipe toRemove = null;

        for (Pipe pipe : pipes) {
            if (!pipe.isOffTheScreen()) {
                pipe.draw(g, panelSize);
                pipe.move();
            } else {
                toRemove = pipe;
            }
        }

        if (toRemove != null) {
            pipes.remove(toRemove);
            addNewPipe();
        }
    }

    private void drawBird(Graphics g) {
        bird.draw(g, panelSize);
    }

    private void drawScore(Graphics g) {
        Color outline = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        g.setFont(new Font("Calibri", Font.BOLD, 30));
        g.setColor(outline);
        g.drawString("Score: " + score, 25 - 1, 35 + 1);
        g.drawString("Score: " + score, 25 - 1, 35 - 1);
        g.drawString("Score: " + score, 25 + 1, 35 + 1);
        g.drawString("Score: " + score, 25 + 1, 35 - 1);
        g.setColor(color);
        g.drawString("Score: " + score, 25, 35);
    }

    private void collisionDetection() {
        if (currentPipe.passedByBird(bird)) {
            score++;
        }

        if (currentPipe.detectCollisionWithBird(bird)) {
            score = 0;
        }
    }

    private void addNewPipe() {
        int positionX = pipes.get(pipes.size() - 1).getPositionX() + SPACE;

        pipes.add(new Pipe(positionX, panelSize, color));
    }

    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.fly();
        }
    }
}
