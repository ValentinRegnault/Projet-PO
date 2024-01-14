package ressources;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import librairies.StdDraw;

/**
 * Classe qui gere l'affichage; vous pouvez la modifier si vous souhaiter
 * changer l'affichage.
 */
public class Affichage {
    /**
     * Affiche une image.
     * 
     * @param xMin        Abscisse du bord gauche de l'image
     * @param xMax        Abscisse du bord droit de l'image
     * @param yMin        Ordonnée du bord bas de l'image
     * @param yMax        Ordonnée du bord haut de l'image
     * @param cheminImage Chemin du fichier contenant l'image (JPEG, PNG, ou GIF)
     */
    public static void image(double xMin, double xMax, double yMin, double yMax, String cheminImage) {
        double xCentre = (xMin + xMax) / 2;
        double yCentre = (yMin + yMax) / 2;
        double xTaille = xMax - xMin;
        double yTaille = yMax - yMin;
        StdDraw.picture(xCentre, yCentre, cheminImage, xTaille, yTaille);
    }

    /**
     * Affiche un texte centré.
     * 
     * @param x       Abscisse du centre du texte
     * @param y       Ordonnée du centre du texte
     * @param texte   Texte à afficher
     * @param font    Police d'écriture à utiliser
     * @param couleur Couleur du texte
     */
    public static void texteCentre(double x, double y, String texte, Font font, Color couleur) {
        StdDraw.setPenColor(couleur);
        StdDraw.setFont(font);
        StdDraw.text(x, y, texte);
    }

    /**
     * Affiche un texte centré avec la police et la couleur par défaut.
     * 
     * @param x     Abscisse du centre du texte
     * @param y     Ordonnée du centre du texte
     * @param texte Texte à afficher
     */
    public static void texteCentre(double x, double y, String texte) {
        texteCentre(x, y, texte, Config.POLICE_PAR_DEFAUT, Config.COULEUR_PAR_DEFAULT);
    }

    /**
     * Affiche un texte centré avec la police et la couleur par défaut.
     * 
     * @param x     Abscisse du centre du texte
     * @param y     Ordonnée du centre du texte
     * @param texte Texte à afficher
     */
    public static void texteCentre(double x, double y, String texte, Color couleur) {
        texteCentre(x, y, texte, Config.POLICE_PAR_DEFAUT, couleur);
    }

    /**
     * Affiche un texte aligné à gauche.
     * 
     * @param x       Abscisse du bord gauche du texte
     * @param y       Ordonnée du centre du texte
     * @param texte   Texte à afficher
     * @param font    Police d'écriture à utiliser
     * @param couleur Couleur du texte
     */
    public static void texteGauche(double x, double y, String texte, Font font, Color couleur) {
        StdDraw.setPenColor(couleur);
        StdDraw.setFont(font);
        StdDraw.textLeft(x, y, texte);
    }

    /**
     * Affiche un texte aligné à gauche avec la police et la couleur par défaut.
     * 
     * @param x     Abscisse du bord gauche du texte
     * @param y     Ordonnée du centre du texte
     * @param texte Texte à afficher
     */
    public static void texteGauche(double x, double y, String texte) {
        texteGauche(x, y, texte, Config.POLICE_PAR_DEFAUT, Config.COULEUR_PAR_DEFAULT);
    }

    /**
     * Affiche un texte aligné à droite.
     * 
     * @param x       Abscisse du bord droit du texte
     * @param y       Ordonnée du centre du texte
     * @param texte   Texte à afficher
     * @param font    Police d'écriture à utiliser
     * @param couleur Couleur du texte
     */
    public static void texteDroite(double x, double y, String texte, Font font, Color couleur) {
        StdDraw.setPenColor(couleur);
        StdDraw.setFont(font);
        StdDraw.textRight(x, y, texte);
    }

    /**
     * Affiche un texte aligné à droite avec la police et la couleur par défaut.
     * 
     * @param x     Abscisse du bord droit du texte
     * @param y     Ordonnée du centre du texte
     * @param texte Texte à afficher
     */
    public static void texteDroite(double x, double y, String texte) {
        texteDroite(x, y, texte, Config.POLICE_PAR_DEFAUT, Config.COULEUR_PAR_DEFAULT);
    }

    /**
     * Affiche un rectangle rempli.
     * 
     * @param xMin    Abscisse du bord gauche du rectangle
     * @param xMax    Abscisse du bord droit du rectangle
     * @param yMin    Ordonnée du bord bas du rectangle
     * @param yMax    Ordonnée du bord haut du rectangle
     * @param couleur Couleur du rectangle
     */
    public static void rectanglePlein(double xMin, double xMax, double yMin, double yMax, Color couleur) {
        StdDraw.setPenColor(couleur);
        StdDraw.filledRectangle((xMin + xMax) / 2.0, (yMin + yMax) / 2.0, (xMax - xMin) / 2.0, (yMax - yMin) / 2.0);
    }

    /**
     * Affiche un rectangle non-rempli.
     * 
     * @param xMin    Abscisse du bord gauche du rectangle
     * @param xMax    Abscisse du bord droit du rectangle
     * @param yMin    Ordonnée du bord bas du rectangle
     * @param yMax    Ordonnée du bord haut du rectangle
     * @param couleur Couleur du rectangle
     */
    public static void rectangleContour(double xMin, double xMax, double yMin, double yMax, Color couleur) {
        StdDraw.setPenColor(couleur);
        StdDraw.rectangle((xMin + xMax) / 2.0, (yMin + yMax) / 2.0, (xMax - xMin) / 2.0, (yMax - yMin) / 2.0);
    }

    /**
     * Affiche un menu graphique pour permettre un choix entre plusieurs option
     * 
     * @param messageExplicatif Message d'explication, ne doit pas etre trop long au
     *                          rique de sortir la fenetre
     * @param options           Liste d'options possibles pour l'utilisateur; si les
     *                          <code>String</code> des options sont trop longue,
     *                          l'affichage risque de depasser de la fenetre
     * @param curseurDefaut     La position du curseur lorsque la fenetre s'ouvre
     * @return L'indice entre <code>0</code> et <code>options.length-1</code> de
     *         l'option choisie par l'utilisateur, <code>-1</code> si echap
     */
    public static int popup(String messageExplicatif, String[] options, int curseurDefaut) {
        int nbOptions = options.length;
        double shareForOption = 0.128 * Config.Y_MAX;
        double height = shareForOption * (nbOptions + 2); // vertical height of the popup window
        int nombreMaxDOptionsAffichees = nbOptions;
        // S'il y a trop d'options a afficher comparer a la taille de la fenetre, il
        // faut faire un popup avec defilement
        if (height > Config.Y_MAX) {
            height = Config.Y_MAX;
            nombreMaxDOptionsAffichees = (int) Math.floor(height / shareForOption - 2.0);
        }
        double left = Config.X_MAX / 2.0 - 0.128 * Config.X_MAX; // abscisse de la gauche de la fenetre de popup
        double right = Config.X_MAX / 2.0 + 0.128 * Config.X_MAX; // abscisse de la droite de la fenetre de popup
        double top = Config.Y_MAX / 2.0 + height / 2.0; // ordonne du haut de la fenetre de popup
        double bottom = Config.Y_MAX / 2.0 - height / 2.0; // ordonnee du bas de la fenetre de popup
        double explainationY = bottom + (nombreMaxDOptionsAffichees + 1.0) * shareForOption; // ordonne du texte
                                                                                             // explicatif
        double XOption = 0.8 * left + 0.2 * right;
        double[] topPosOptions = new double[nombreMaxDOptionsAffichees];
        for (int i = 0; i < nombreMaxDOptionsAffichees; i++) {
            topPosOptions[i] = explainationY - shareForOption * (1.5 + i);
        }
        int popupCursor = curseurDefaut; // position du curseur, entre 0 et nbOptions - 1
        if (popupCursor >= nombreMaxDOptionsAffichees) {
            popupCursor = 0; // si le départ est en-dehors de la zone, départ à 0
        }
        int offsetOptions = 0; // le rang de l'element le plus en haut actuellement
        double centerX, centerY, widthX, widthY, pixelsCursor;
        while (true) {
            rectanglePlein(left, right, bottom, top, Config.POPUP_COULEUR_ARRIERE_PLAN);
            rectangleContour(left, right, bottom, top, Config.POPUP_COULEUR_CADRE);
            StdDraw.setFont(Config.POLICE_POPUP);
            StdDraw.setPenColor(Config.POPUP_COULEUR_TEXTE); // couleur du texte de popup
            StdDraw.textLeft(0.95 * left + 0.05 * right, explainationY, messageExplicatif);
            for (int i = 0; i < nombreMaxDOptionsAffichees; i++)
                StdDraw.textLeft(XOption, topPosOptions[i], options[i + offsetOptions]);
            StdDraw.show();
            centerX = XOption - 0.1 * (right - left);
            centerY = topPosOptions[popupCursor];
            pixelsCursor = Math.min(0.1 * (right - left) / (0.016 * Config.X_MAX),
                    shareForOption / (0.128 * Config.Y_MAX)); // pour s'assurer que l'image n'est pas deformee
            widthX = pixelsCursor * (0.016 * Config.X_MAX);
            widthY = pixelsCursor * (0.016 * Config.Y_MAX);
            StdDraw.picture(centerX, centerY, "assets" + File.separator + "pictures" + File.separator +  "cursor_popup.png", widthX, widthY);
            StdDraw.show();
            AssociationTouches.init();
            String touche = AssociationTouches.trouveProchaineEntree();
            switch (touche) {
                case "Haut":
                    if (popupCursor == 0 && offsetOptions > 0) {
                        offsetOptions--;
                    } else if (popupCursor > 0) {
                        rectanglePlein(centerX - widthX / 2.0, centerX + widthX / 2.0, centerY - widthY / 2.0,
                                centerY + widthY / 2.0, Config.POPUP_COULEUR_ARRIERE_PLAN);
                        popupCursor--;
                    }
                    break;
                case "Bas":
                    // TODO : quand le curseur est a l'avant derniere option affichee et qu'on
                    // appuie sur bas, ne pas deplacer le curseur mais augmenter le offset de 1
                    if (popupCursor == nombreMaxDOptionsAffichees - 2
                            && offsetOptions < (nbOptions - nombreMaxDOptionsAffichees)) {
                        offsetOptions++;
                    } else if (popupCursor < nombreMaxDOptionsAffichees - 1) {
                        rectanglePlein(centerX - widthX / 2.0, centerX + widthX / 2.0, centerY - widthY / 2.0,
                                centerY + widthY / 2.0, Config.POPUP_COULEUR_ARRIERE_PLAN);
                        popupCursor++;
                    }
                    break;
                case "Echap":
                    StdDraw.setFont(Config.POLICE_PAR_DEFAUT);
                    return -1;
                case "Entree":
                    StdDraw.setFont(Config.POLICE_PAR_DEFAUT);
                    return popupCursor + offsetOptions;
                default:
            }
        }
    }

    public static void selecteur(String touche, double x, double y, double largeur, double hauteur) {
        rectangleContour(x, x + largeur, y, y + largeur, new Color(255, 255, 255));
        rectanglePlein(x, x + largeur, y, y + largeur, new Color(255, 255, 255, 50));
        texteCentre(x + largeur/2, y + hauteur/2, touche);
    }
}