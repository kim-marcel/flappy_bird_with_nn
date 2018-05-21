package flappybirdwithnn;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;


/**
 * Created by KimFeichtinger on 19.05.18.
 */
public class GUI extends javax.swing.JPanel {

    private static final int DELAY = 10;

    private Point panelSize;

    private Controller c;

    private Timer timer;

    private Color backgroundColor;
    private Color color;

    public GUI(Point panelSize) {
        Random random = new Random();

        this.panelSize = panelSize;

        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();

        double a = 1 - ( 0.299 * red * 255 + 0.587 * green * 255 + 0.114 * blue * 255)/255;

        color = a < 0.5 ? Color.BLACK : Color.WHITE;

        c = new Controller(this.panelSize, color);

        backgroundColor = new Color(red, green, blue);

        ActionListener taskPerformer = evt -> repaint();

        timer = new Timer(DELAY, taskPerformer);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g){
        paintBackground(g);
        paintForeground(g);
    }

    private void paintForeground(Graphics g) {
        c.drawAll(g);
    }

    private void paintBackground(Graphics g){
        g.setColor(backgroundColor);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
    }

    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_P){
            timer.stop();
        }
    }
}
