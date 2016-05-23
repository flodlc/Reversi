/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;



/**
 *
 * @author Florian
 */
public class IAA {

    private Plateau plateau;

    public IAA(Plateau plateau) {
        this.plateau = plateau;

    }

    //recherche en profondeur le meilleurs coups avec une méthode récursive
    public int[] IA(Plateau plateau1, int compteur, int pionsRestants) {

        Couleur couleurTour;
        Couleur couleurAdv;
        if (plateau1.isTourNoir()) {
            couleurTour = Couleur.NOIR;
            couleurAdv = Couleur.BLANC;
        } else {
            couleurTour = Couleur.BLANC;
            couleurAdv = Couleur.NOIR;
        }

        int[] tob = new int[3];
        tob[0] = -1;
        tob[1] = -1;
        tob[2] = -1000;

        for (int i = 0; i < plateau.getTAILLE(); i++) {
            for (int j = 0; j < plateau.getTAILLE(); j++) {

                if (plateau1.verifierPosition(plateau1.getCase(i, j))) {

                    if (compteur > 0) {
                        //copie le plateau
                        Plateau plateau2 = plateau1.copie(plateau1);

                        //joue le tour sur la case (i,j)
                        plateau2.getCase(i, j).add(plateau2.creerPion(couleurTour));
                        plateau2.getCase(i, j).validate();
                        plateau2.mangerPion(plateau2.getCase(i, j));
                        plateau2.setTourNoir(!plateau2.isTourNoir());
                        if (plateau2.isTourNoir()) {
                            couleurTour = Couleur.NOIR;
                            couleurAdv = Couleur.BLANC;
                        } else {
                            couleurTour = Couleur.BLANC;
                            couleurAdv = Couleur.NOIR;
                        }

                        //joue le tour de l'adversaire en appelant la méthode IA
                        int[] tib = IA(plateau2, compteur - 1, pionsRestants);
                        if (tib[0] != -1) {
                            plateau2.getCase(tib[0], tib[1]).add(plateau2.creerPion(couleurTour));
                            plateau2.getCase(tib[0], tib[1]).validate();
                            plateau2.mangerPion(plateau2.getCase(tib[0], tib[1]));
                        }
                        plateau2.setTourNoir(!plateau2.isTourNoir());
                        if (plateau2.isTourNoir()) {
                            couleurTour = Couleur.NOIR;
                            couleurAdv = Couleur.BLANC;
                        } else {
                            couleurTour = Couleur.BLANC;
                            couleurAdv = Couleur.NOIR;
                        }

                        //si le plateau à la profondeur suivante est plus avantageux on le selectionne
                        if ((IA(plateau2, compteur - 1, pionsRestants))[2] > tob[2]) {
                            tob[2] = (IA(plateau2, compteur - 1, pionsRestants))[2];
                            tob[0] = i;
                            tob[1] = j;

                        }
                    }

                    //Fin de la méthode récursive
                    if (compteur <= 0) {

                        if (plateau1.compterMesPoints() > tob[2] && pionsRestants > 3) {
                            tob[0] = i;
                            tob[1] = j;
                            tob[2] = plateau1.compterMesPoints();

                        } else if (plateau1.compterMesPions() > tob[2] && pionsRestants <= 3) {
                            tob[0] = i;
                            tob[1] = j;
                            tob[2] = plateau1.compterMesPions();

                        }
                    }
                }
            }
        }
        return tob;
    }

}
