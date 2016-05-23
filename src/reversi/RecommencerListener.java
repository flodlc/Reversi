/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Florian
 */
public class RecommencerListener implements ActionListener{
    private Plateau plateau;
    
    public RecommencerListener(Plateau plateau){
        this.plateau=plateau;
    }
    
    public void actionPerformed(ActionEvent e){
        plateau.reinitialise();
    }
    
}
