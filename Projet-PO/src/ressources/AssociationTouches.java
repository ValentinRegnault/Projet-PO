package ressources;

import java.awt.event.KeyEvent;
import java.util.Hashtable;

import librairies.StdDraw;

/** Gere les valeurs numeriques des touches. */
public class AssociationTouches {
    // Contient l'association entre le code d'une touche et la chaine de caractère
    // correpspondant à cette touche
    public static Hashtable<Integer, String> clavier = new Hashtable<Integer, String>();;

    /**
     * Remplit les associations entre code et touches pour certaines touches, dites
     * pertinantes
     * Il y a les touches "entrée", "echap", les touches directionnelles et la
     * lettre A
     * Toutes les autres touches ne seront pas détectée par le jeu.
     */
    public static void init() {
        clavier.put(KeyEvent.VK_ENTER, "Entree");
        clavier.put(KeyEvent.VK_ESCAPE, "Echap");
        clavier.put(KeyEvent.VK_LEFT, "Gauche");
        clavier.put(KeyEvent.VK_RIGHT, "Droite");
        clavier.put(KeyEvent.VK_UP, "Haut");
        clavier.put(KeyEvent.VK_DOWN, "Bas");
        clavier.put(KeyEvent.VK_A, "A"); // Vous pouvez ajouter toute les lettres voulues de cette façon
        // Attention les touches absente d'un clavier QWERTY ("2/é", "9/ç", par
        // exemple), sont incompatibles avec StdDraw.
        clavier.put(KeyEvent.VK_Z, "Z");
        clavier.put(KeyEvent.VK_E, "E");
        clavier.put(KeyEvent.VK_R, "R");
        clavier.put(KeyEvent.VK_T, "T");
        clavier.put(KeyEvent.VK_Y, "Y");
        clavier.put(KeyEvent.VK_U, "U");
        clavier.put(KeyEvent.VK_I, "I");
        clavier.put(KeyEvent.VK_O, "O");
        clavier.put(KeyEvent.VK_P, "P");
        clavier.put(KeyEvent.VK_Q, "Q");
        clavier.put(KeyEvent.VK_S, "S");
        clavier.put(KeyEvent.VK_D, "D");
        clavier.put(KeyEvent.VK_F, "F");
        clavier.put(KeyEvent.VK_G, "G");
        clavier.put(KeyEvent.VK_H, "H");
        clavier.put(KeyEvent.VK_J, "J");
        clavier.put(KeyEvent.VK_K, "K");
        clavier.put(KeyEvent.VK_L, "L");
        clavier.put(KeyEvent.VK_M, "M");
        clavier.put(KeyEvent.VK_ESCAPE, "ESCAPE");
    }

    /**
     * Renvoie la prochaine entree pertinente de l'utilisateur: cette entree n'est
     * renvoyee que quand la touche est relachee, et la touche doit correspondre a
     * un element de TOUCHES_PERTINENTES_SPECIALES ou de
     * TOUCHES_PERTINENTES_CARACTERES
     * 
     * @return Chaine de caractère associée à la prochaine entree pertinente de
     *         l'utilisateur
     */
    public static String trouveProchaineEntree() {
        while (true) {
            for (Integer k : clavier.keySet()) {
                if (StdDraw.isKeyPressed(k)) {
                    while (StdDraw.isKeyPressed(k)) {
                    }
                    return clavier.get(k);
                }

            }
        }
    }
}
