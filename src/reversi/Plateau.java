package reversi;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Plateau extends JPanel {

    private static final int TAILLE = 8;
    private boolean tourNoir;
    private boolean iA;
    private int nbPionsTotal;
    private boolean bloque;
    private int profondeur;
    private static int[] poids = {130, -13, 15, 10, 10, 15, -13, 130, -13, -13, 9, 12, 12, 9, -13, -13, 15, 9, 11, 15, 15, 11, 9, 15, 10, 12, 15, 18, 18, 15, 12, 10, 10, 12, 15, 18, 18, 15, 12, 10, 15, 9, 11, 15, 15, 11, 9, 15, -13, -13, 9, 12, 12, 9, -13, -13, 130, -13, 15, 10, 10, 15, -13, 130};

    public void setiA(boolean iA) {
        this.iA = iA;
    }

    public Plateau() {

        bloque = false;
        nbPionsTotal = 4;
        tourNoir = true;
        setLayout(new GridLayout(TAILLE, TAILLE));
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                ajouterCase();
            }
        }

    }

    private void ajouterCase() {
        Case case1 = new Case();
        case1.addMouseListener(new ListenerCase(case1, this));
        add(case1);
    }

    public Pion creerPion(Couleur couleur) {
        Pion pion = new Pion(couleur);

        return pion;
    }

    public void poserPion(Case case1) {

        if (case1.getComponentCount() == 0) {
            if (!iA) {
                if (tourNoir) {
                    if (verifierPosition(case1)) {
                        case1.add(creerPion(Couleur.NOIR));
                        case1.validate();
                        case1.repaint();
                        mangerPion(case1);
                        tourNoir = !tourNoir;

                    }
                } else {
                    if (verifierPosition(case1)) {
                        case1.add(creerPion(Couleur.BLANC));
                        case1.validate();
                        case1.repaint();
                        mangerPion(case1);
                        tourNoir = !tourNoir;

                    }
                }

            } else {
                if (tourNoir) {
                    bloque = true;
                    boolean ok = false;
                    for (int i = 0; i < TAILLE; i++) {
                        for (int j = 0; j < TAILLE; j++) {
                            if (verifierPosition(getCase(i, j))) {
                                bloque = false;
                            }
                        }
                    }
                    if (verifierPosition(case1)) {
                        case1.add(creerPion(Couleur.NOIR));
                        case1.validate();
                        case1.repaint();
                        mangerPion(case1);
                        nbPionsTotal++;
                        ok = true;

                    }

                    if (bloque || ok) {
                        tourNoir = !tourNoir;

                        //IA
                        while ((64 - nbPionsTotal) < profondeur * 2) {
                            profondeur--;
                        }
                        IAA iiA = new IAA(this);
                        int[] tab = iiA.IA(this, profondeur, 64 - nbPionsTotal);
                        //int[] tab = iiA.getTab();
                        if (tab[0] != -1) {
                            getCase(tab[0], tab[1]).add(creerPion(Couleur.BLANC));
                            getCase(tab[0], tab[1]).validate();
                            getCase(tab[0], tab[1]).repaint();
                            mangerPion(getCase(tab[0], tab[1]));
                            bloque = false;
                        }
                        tourNoir = !tourNoir;
                    }
                }

            }
        }
        nbPionsTotal = testFin();
    }

//Verifie si la case selectionee est jouable
    public boolean verifierPosition(Case c) {
        int j = getLigne(c);
        int i = getColonne(c);

        Couleur couleurTour;
        Couleur couleurAdv;
        if (tourNoir) {
            couleurTour = Couleur.NOIR;
            couleurAdv = Couleur.BLANC;
        } else {
            couleurTour = Couleur.BLANC;
            couleurAdv = Couleur.NOIR;
        }

        ArrayList list = verifier(i, j, couleurTour, couleurAdv);
        if (list.size() > 0 && c.getComponentCount() == 0) {
            return true;
        }
        return false;
    }
    
    //Verifie si la partie est terminée et determine le vainqueur avant de l'afficher dans une boite de dialogue
    public int testFin() {
        boolean fin = true;
        int nbPionsTotal = 0;
        int nbBlanc = 0;
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (getCase(i, j).getComponentCount() > 0) {
                    nbPionsTotal++;
                    fin = false;
                }
                if (getCase(i, j).getComponentCount() > 0 && ((Pion) getCase(i, j).getComponent(0)).getCouleur().equals(Couleur.BLANC)) {
                    nbBlanc++;
                }
            }
        }
        if (TAILLE * TAILLE == nbPionsTotal || bloque) {

            String gagnant;
            if (nbBlanc > nbPionsTotal / 2) {
                gagnant = "Victoir des blancs ";
            } else if (nbBlanc < nbPionsTotal / 2) {
                gagnant = "Victoir des noirs ";
            } else {
                gagnant = "Egalité !";
            }

            int nbNoir = nbPionsTotal - nbBlanc;

            //boite de dialogue
            JOptionPane jop1;
            jop1 = new JOptionPane();
            jop1.showMessageDialog(null, gagnant + "\nScore noir: " + nbNoir + "     Score blanc: " + nbBlanc, "Fin de partie!", JOptionPane.INFORMATION_MESSAGE);
        }
        return nbPionsTotal;
    }

    //Permet de changer la couleur des pions mangés
    public void mangerPion(Case c) {
        int abs = getColonne(c);
        int ord = getLigne(c);
        Couleur couleurTour;
        Couleur couleurAdv;
        if (tourNoir) {
            couleurTour = Couleur.NOIR;
            couleurAdv = Couleur.BLANC;
        } else {
            couleurTour = Couleur.BLANC;
            couleurAdv = Couleur.NOIR;
        }
        ArrayList list = verifier(abs, ord, couleurTour, couleurAdv);
        for (int i = 0; i < list.size(); i++) {
            ((Pion) ((Case) list.get(i)).getComponent(0)).changeCouleur();
            ((Case) list.get(i)).validate();
            ((Case) list.get(i)).repaint();
        }

    }

    public void init() {
        getCase(28).add(creerPion(Couleur.NOIR));
        getCase(27).add(creerPion(Couleur.BLANC));
        getCase(35).add(creerPion(Couleur.NOIR));
        getCase(36).add(creerPion(Couleur.BLANC));
    }

    public Case getCase(int i, int j) {
        return (Case) getComponent(j + i * TAILLE);
    }

    public Case getCase(int i) {
        return (Case) getComponent(i);
    }

    public int getLigne(Case case1) {
        int res = 0;
        for (int i = 0; i < TAILLE * TAILLE; i++) {
            if (getCase(i).equals(case1)) {
                res = i / TAILLE;
            }
        }
        return res;
    }

    public int getColonne(Case case1) {
        int res = 0;
        for (int i = 0; i < TAILLE * TAILLE; i++) {
            if (getCase(i).equals(case1)) {
                res = i % TAILLE;
            }
        }
        return res;
    }

    public int compterMesPions() {
        Couleur couleurTour;
        Couleur couleurAdv;
        int nb = 0;
        if (tourNoir) {
            couleurTour = Couleur.NOIR;
            couleurAdv = Couleur.BLANC;
        } else {
            couleurTour = Couleur.BLANC;
            couleurAdv = Couleur.NOIR;
        }
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (getCase(i, j).getComponentCount() == 1 && ((Pion) getCase(i, j).getComponent(0)).getCouleur().equals(couleurTour)) {
                    nb++;
                }
            }
        }
        return nb;
    }

    
    //retourne le score du plateau prit en argument
    public int compterMesPoints() {
        Couleur couleurTour;
        Couleur couleurAdv;
        if (tourNoir) {
            couleurTour = Couleur.NOIR;
            couleurAdv = Couleur.BLANC;
        } else {
            couleurTour = Couleur.BLANC;
            couleurAdv = Couleur.NOIR;
        }
        int score = 0;

        for (int i = 0; i < TAILLE * TAILLE; i++) {
            if (getCase(i).getComponentCount() >= 1 && ((Pion) getCase(i).getComponent(0)).getCouleur().equals(couleurTour)) {
                score += poids[i];
            }
        }

        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {

                //si le pion parcouru est à nous:
                if (getCase(i, j).getComponentCount() >= 1 && ((Pion) getCase(i, j).getComponent(0)).getCouleur().equals(couleurTour)) {

                    if (getCase(0).getComponentCount() == 1) {

                        //si l'angle est occupé par un des joueurs on augmente les coefs des cases voisines à l'angle car ils étaient très bas pour protéger l'angle
                        if ((i == 0) && (j == 1)) {
                            score += 25;
                        }
                        if ((i == 1) && (j == 1)) {
                            score += 25;
                        }
                        if ((i == 1) && (j == 0)) {
                            score += 25;
                        }
                    } else if (getCase(7).getComponentCount() == 1) {

                        if ((i == 0) && (j == 6)) {
                            score += 25;
                        }
                        if ((i == 1) && (j == 6)) {
                            score += 25;
                        }
                        if ((i == 1) && (j == 7)) {
                            score += 25;
                        }
                    } else if (getCase(56).getComponentCount() == 1) {

                        if ((i == 6) && (j == 0)) {
                            score += 25;
                        }
                        if ((i == 6) && (j == 1)) {
                            score += 25;
                        }
                        if ((i == 7) && (j == 1)) {
                            score += 25;
                        }
                    } else if (getCase(63).getComponentCount() == 1) {

                        if ((i == 6) && (j == 6)) {
                            score += 25;
                        }
                        if ((i == 6) && (j == 7)) {
                            score += 25;
                        }
                        if ((i == 7) && (j == 6)) {
                            score += 25;
                        }
                    }

                    if ((i == 0 || j == 0) && getCase(0).getComponentCount() == 1 && ((Pion) getCase(0).getComponent(0)).getCouleur().equals(couleurTour)) {
                        score += 4;
                    }

                    if ((i == 0 || j == TAILLE - 1) && getCase(7).getComponentCount() == 1 && ((Pion) getCase(7).getComponent(0)).getCouleur().equals(couleurTour)) {
                        score += 4;
                    }

                    if ((i == TAILLE - 1 || j == 0) && getCase(56).getComponentCount() == 1 && ((Pion) getCase(56).getComponent(0)).getCouleur().equals(couleurTour)) {
                        score += 4;
                    }

                    if ((i == TAILLE - 1 || j == TAILLE - 1) && getCase(63).getComponentCount() == 1 && ((Pion) getCase(63).getComponent(0)).getCouleur().equals(couleurTour)) {
                        score += 4;
                    }

                }
                //si l'adversaire prend un angle on baisse le score
                if (getCase(i, j).getComponentCount() >= 1 && ((Pion) getCase(i, j).getComponent(0)).getCouleur().equals(couleurAdv)) {
                    if ((i == 0 && j == 0) || (i == TAILLE - 1 && j == TAILLE - 1) || (i == TAILLE - 1 && j == 0) || (i == 0 && j == TAILLE - 1)) {
                        score -= 150;
                    }

                }
            }
        }
        return score;
    }

    //teinte les pions qui seront mangés si clique sur la case
    public void griser(Case case1) {
        int ord = getLigne(case1);
        int abs = getColonne(case1);

        Couleur couleurTour;
        Couleur couleurAdv;
        if (case1.getComponentCount() == 0) {
            if (tourNoir) {
                couleurTour = Couleur.NOIR;
                couleurAdv = Couleur.BLANC;
            } else {
                couleurTour = Couleur.BLANC;
                couleurAdv = Couleur.NOIR;
            }

            ArrayList list = verifier(abs, ord, couleurTour, couleurAdv);
            for (int i = 0; i < list.size(); i++) {
                ((Pion) ((Case) list.get(i)).getComponent(0)).grise();
            }
        }
    }

    //remet la couleur des pions normale
    public void degrise() {
        for (int i = 0; i < TAILLE * TAILLE - 1; i++) {
            if (getCase(i).getComponentCount() > 0) {
                ((Pion) getCase(i).getComponent(0)).degrise();
            }
        }
    }

    public ArrayList verifier(int i, int j, Couleur couleurTour, Couleur couleurAdv) {
        ArrayList list = new ArrayList();

        //appelle les listes de pions verifiés dans les differentes directions
        list.addAll(verifierHorizontal(i, j, couleurTour, couleurAdv));
        list.addAll(verifierVertical(i, j, couleurTour, couleurAdv));
        list.addAll(verifierDiagonal1(i, j, couleurTour, couleurAdv));
        list.addAll(verifierDiagonal2(i, j, couleurTour, couleurAdv));
        return list;
    }

    //Verification des pions à griser 
    public ArrayList verifierHorizontal(int abs, int ord, Couleur couleurTour, Couleur couleurAdv) {
        ArrayList list = new ArrayList();
        ArrayList def = new ArrayList();

        int y = abs - 1;

        while (y >= 0 && getCase(ord, y).getComponentCount() == 1 && ((Pion) getCase(ord, y).getComponent(0)).getCouleur().equals(couleurAdv)) {
            list.add(getCase(ord, y));
            y--;
        }
        if (y >= 0 && getCase(ord, y).getComponentCount() == 1) {
            def = list;
        }

        list = new ArrayList();

        y = abs + 1;

        while (y <= TAILLE - 1 && getCase(ord, y).getComponentCount() == 1 && ((Pion) getCase(ord, y).getComponent(0)).getCouleur().equals(couleurAdv)) {
            list.add(getCase(ord, y));
            y++;
        }
        if (y <= TAILLE - 1 && getCase(ord, y).getComponentCount() == 1) {
            def.addAll(list);
        }
        return def;
    }

    public ArrayList verifierVertical(int abs, int ord, Couleur couleurTour, Couleur couleurAdv) {
        ArrayList list = new ArrayList();
        ArrayList def = new ArrayList();

        int y = ord - 1;

        while (y >= 0 && getCase(y, abs).getComponentCount() == 1 && ((Pion) getCase(y, abs).getComponent(0)).getCouleur().equals(couleurAdv)) {
            list.add(getCase(y, abs));
            y--;
        }
        if (y >= 0 && getCase(y, abs).getComponentCount() == 1) {
            def = list;
        }

        list = new ArrayList();

        y = ord + 1;

        while (y <= TAILLE - 1 && getCase(y, abs).getComponentCount() == 1 && ((Pion) getCase(y, abs).getComponent(0)).getCouleur().equals(couleurAdv)) {
            list.add(getCase(y, abs));
            y++;
        }
        if (y <= TAILLE - 1 && getCase(y, abs).getComponentCount() == 1) {
            def.addAll(list);
        }

        return def;
    }

    public ArrayList verifierDiagonal1(int abs, int ord, Couleur couleurTour, Couleur couleurAdv) {
        ArrayList list = new ArrayList();
        ArrayList def = new ArrayList();

        int y = ord - 1;
        int x = abs - 1;

        while (y >= 0 && x >= 0 && getCase(y, x).getComponentCount() == 1 && ((Pion) getCase(y, x).getComponent(0)).getCouleur().equals(couleurAdv)) {
            list.add(getCase(y, x));
            y--;
            x--;
        }
        if (y >= 0 && x >= 0 && getCase(y, x).getComponentCount() == 1) {
            def = list;
        }

        list = new ArrayList();

        y = ord + 1;
        x = abs + 1;

        while (y <= TAILLE - 1 && x <= TAILLE - 1 && getCase(y, x).getComponentCount() == 1 && ((Pion) getCase(y, x).getComponent(0)).getCouleur().equals(couleurAdv)) {
            list.add(getCase(y, x));
            y++;
            x++;
        }
        if (y <= TAILLE - 1 && x <= TAILLE - 1 && getCase(y, x).getComponentCount() == 1) {
            def.addAll(list);
        }

        return def;
    }

    public ArrayList verifierDiagonal2(int abs, int ord, Couleur couleurTour, Couleur couleurAdv) {
        ArrayList list = new ArrayList();
        ArrayList def = new ArrayList();

        int y = ord - 1;
        int x = abs + 1;

        while (y >= 0 && x <= TAILLE - 1 && getCase(y, x).getComponentCount() == 1 && ((Pion) getCase(y, x).getComponent(0)).getCouleur().equals(couleurAdv)) {
            list.add(getCase(y, x));
            y--;
            x++;
        }
        if (y >= 0 && x <= TAILLE - 1 && getCase(y, x).getComponentCount() == 1) {
            def = list;
        }

        list = new ArrayList();

        y = ord + 1;
        x = abs - 1;

        while (y <= TAILLE - 1 && x >= 0 && getCase(y, x).getComponentCount() == 1 && ((Pion) getCase(y, x).getComponent(0)).getCouleur().equals(couleurAdv)) {
            list.add(getCase(y, x));
            y++;
            x--;
        }
        if (y <= TAILLE - 1 && x >= 0 && getCase(y, x).getComponentCount() == 1) {
            def.addAll(list);
        }

        return def;
    }

    public void setTourNoir(boolean tourNoir) {
        this.tourNoir = tourNoir;
    }

    public static int getTAILLE() {
        return TAILLE;
    }

    public int getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(int profondeur) {
        this.profondeur = profondeur;
    }

    public boolean isTourNoir() {
        return tourNoir;
    }

    public boolean isiA() {
        return iA;
    }

    //retourne une instance copie du plateau pour l'IA
    public Plateau copie(Plateau plateau1) {
        Plateau copie = new Plateau();
        if (plateau1.isTourNoir()) {
            copie.setTourNoir(true);
        } else {
            copie.setTourNoir(false);
        }

        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (plateau1.getCase(i, j).getComponentCount() > 0) {
                    if (((Pion) plateau1.getCase(i, j).getComponent(0)).getCouleur().equals(Couleur.NOIR)) {
                        copie.getCase(i, j).add(creerPion(Couleur.NOIR));
                        /*copie.getCase(i, j).validate();
                         copie.getCase(i, j).repaint();*/
                    }
                    if (((Pion) plateau1.getCase(i, j).getComponent(0)).getCouleur().equals(Couleur.BLANC)) {
                        copie.getCase(i, j).add(creerPion(Couleur.BLANC));
                        /*copie.getCase(i, j).validate();
                         copie.getCase(i, j).repaint();*/
                    }
                }
            }
        }
        return copie;
    }

    
    //permet de recommencer une partie
    public void reinitialise() {
        iA = iA;
        bloque = false;
        nbPionsTotal = 4;
        tourNoir = true;
       removeAll();
        for (int i = 0; i < TAILLE * TAILLE; i++) {

            ajouterCase();
            int a = 2;
        }
        init();
        validate();
        repaint();
    }
}
