package flappybirdwithnn;

import java.awt.*;

/**
 * Created by KimFeichtinger on 19.05.18.
 */
public class Frame extends javax.swing.JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 550;

    private GUI gui;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Frame().setVisible(true));
    }

    public Frame(){
        gui = new GUI(new Point(WIDTH, HEIGHT));

        this.setSize(WIDTH, HEIGHT + 22);
        this.setLocationRelativeTo(null); // displays JFrame in the center of the screen

        gui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                guiKeyPressed(evt);
            }
        });

        this.add(gui);

        this.setFocusable(false);
        this.gui.setFocusable(true);
        this.gui.requestFocus();
    }

    private void guiKeyPressed(java.awt.event.KeyEvent evt) {
        gui.keyPressed(evt);
    }

}
