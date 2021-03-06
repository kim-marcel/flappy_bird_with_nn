package flappybirdwithnn;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Controller {

    private static final int SPACE = 250;
    private static final int BIRDS = 100;
    private static final int BIRD_RADIUS = 15;
    private static final int BIRD_POSITION_X = 200;

    private ArrayList<Pipe> pipes;
    private ArrayList<Bird> birds;

    private Pipe currentPipe;
    private Point panelSize;
    private Color color;
    private int epoch;

    public Controller(Point panelSize, Color color) {
        this.panelSize = panelSize;

        epoch = 0;

        pipes = new ArrayList<>();
        birds = new ArrayList<>();

        this.color = color;
        initializeBirds();
        initializePipes();
    }

    private void initializeBirds(){
        for (int i = 0; i < BIRDS; i++) {
            birds.add(new Bird(panelSize, color));
        }
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
        for (Bird bird : birds) {
            bird.fly(currentPipe);
        }

        drawPipes(g);
        drawBird(g);

        findCurrentPipe();
        collisionDetection();

        drawScore(g);
        drawEpoch(g);
    }

    private void findCurrentPipe() {
        for (Pipe pipe : pipes) {
            if (pipe.getPositionX() + pipe.getWidth() >= BIRD_POSITION_X - BIRD_RADIUS) {
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
        for (Bird bird : birds) {
            bird.draw(g, panelSize);
        }
    }

    private void drawScore(Graphics g) {
        int highestScore = getHighestScore();

        Color outline = color == Color.BLACK ? Color.WHITE : Color.BLACK;

        String score = "Highest Score: " + highestScore;

        g.setFont(new Font("Calibri", Font.BOLD, 30));
        g.setColor(outline);
        g.drawString(score, 25 - 1, 35 + 1);
        g.drawString(score, 25 - 1, 35 - 1);
        g.drawString(score, 25 + 1, 35 + 1);
        g.drawString(score, 25 + 1, 35 - 1);
        g.setColor(color);
        g.drawString(score, 25, 35);
    }
    
    private void drawEpoch(Graphics g){
        Color outline = color == Color.BLACK ? Color.WHITE : Color.BLACK;

        String text = "Epoch: " + epoch;

        g.setFont(new Font("Calibri", Font.BOLD, 30));
        g.setColor(outline);
        g.drawString(text, 25 - 1, 65 + 1);
        g.drawString(text, 25 - 1, 65 - 1);
        g.drawString(text, 25 + 1, 65 + 1);
        g.drawString(text, 25 + 1, 65 - 1);
        g.setColor(color);
        g.drawString(text, 25, 65);
    }

    private int getHighestScore() {
        int highestScore = 0;

        for (Bird bird : birds) {
            int birdScore = bird.getScore();

            if (birdScore > highestScore){
                highestScore = birdScore;
            }
        }

        return highestScore;
    }

    private void collisionDetection() {
        Iterator<Bird> i = birds.iterator();
        while (i.hasNext()) {
            Bird bird = i.next();
            if (currentPipe.passedByBird(bird)) {
                bird.increaseScore();
            }

            if (currentPipe.detectCollisionWithBird(bird)) {
                bird.setScoreToZero();
                i.remove();
            }
        }

        if (birds.isEmpty()){
            newEpoch();
        }
    }

    private void newEpoch() {
        pipes.clear();

        initializePipes();
        initializeBirds();

        epoch++;
    }

    private void addNewPipe() {
        int positionX = pipes.get(pipes.size() - 1).getPositionX() + SPACE;

        pipes.add(new Pipe(positionX, panelSize, color));
    }
}
