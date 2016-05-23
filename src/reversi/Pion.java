package reversi;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.JPanel;

public class Pion extends JPanel {

    private Couleur couleur;

    public Pion(Couleur couleur) {
        this.couleur = couleur;
        setOpaque(false);
        switch (couleur) {
            case BLANC:
                setForeground(new Color(220, 220, 220));
                setBackground(new Color(220, 220, 220));
                break;
            case NOIR:
                setForeground(new Color(20, 20, 20));
                setBackground(new Color(20, 20, 20));
                break;

        }
    }

    //change la couleur du pion
    public void changeCouleur() {
        if (couleur.equals(Couleur.BLANC)) {
            setForeground(new Color(20, 20, 20));
            setBackground(new Color(20, 20, 20));
            couleur = Couleur.NOIR;
        } else {
            setForeground(new Color(220, 220, 220));
            setBackground(new Color(220, 220, 220));
            couleur = Couleur.BLANC;
        }
    }

    //grise le pion
    public void grise() {
        setBackground(new Color(70,70,70));
        setForeground(new Color(70,70,70));
    }

    public void degrise() {
        switch (couleur) {
            case BLANC:
                setForeground(new Color(220, 220, 220));
                setBackground(new Color(220, 220, 220));
                break;
            case NOIR:
                setForeground(new Color(20, 20, 20));
                setBackground(new Color(20, 20, 20));
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Paint paint;
        Graphics2D g2d;
        if (g instanceof Graphics2D) {
            g2d = (Graphics2D) g;
        } else {
            System.out.println("Error");
            return;
        }
        paint = new GradientPaint(0, 0, getBackground(), getWidth(), getHeight(), getForeground());
        g2d.setPaint(paint);
        g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);

    }

    public Couleur getCouleur() {
        return couleur;
    }

}
