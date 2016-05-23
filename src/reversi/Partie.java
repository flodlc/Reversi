/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Florian
 */
public class Partie {
    private Plateau plateau;
    private JFrame f;
    public Partie() {
        f = new JFrame();
        f.setSize(600, 700);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        plateau = new Plateau();
        plateau.init();
        f.add(plateau);
        JPanel pan = new JPanel();
        JButton recommencerButton = new JButton("Recommencer");
        recommencerButton.addActionListener(new RecommencerListener(plateau));
        pan.setSize(600, 100);
        pan.add(recommencerButton);
        f.add(pan, BorderLayout.SOUTH);

        f.setVisible(
                true);

        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous jouer contre l'ordinateur ?", "Adversaire", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            plateau.setiA(true);
            ButtonGroup bg = new ButtonGroup();

            JRadioButton jr1 = new JRadioButton("facile");
            JRadioButton jr2 = new JRadioButton("moyen");
            JRadioButton jr3 = new JRadioButton("difficile");
            jr1.setSelected(true);
            bg.add(jr1);
            bg.add(jr2);
            bg.add(jr3);
            jr1.addActionListener(new StateListener(plateau));
            jr2.addActionListener(new StateListener(plateau));
            jr3.addActionListener(new StateListener(plateau));
            pan.add(jr1);
            pan.add(jr2);
            pan.add(jr3);
            pan.validate();
            pan.repaint();

        } else {
            plateau.setiA(false);
        }
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public JFrame getF() {
        return f;
    }
    
    
}
