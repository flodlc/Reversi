/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

/**
 *
 * @author Florian
 */
public class StateListener implements ActionListener {

    private Plateau plateau;

    public StateListener(Plateau plateau) {
        super();
        this.plateau = plateau;
    }

    public void actionPerformed(ActionEvent e) {
        if (((JRadioButton) e.getSource()).getText().equals("facile")) {
            plateau.setProfondeur(1);
        }
        if (((JRadioButton) e.getSource()).getText().equals("moyen")) {
            plateau.setProfondeur(2);
        }
        if (((JRadioButton) e.getSource()).getText().equals("difficile")) {
            plateau.setProfondeur(3);
        }
    }
}
