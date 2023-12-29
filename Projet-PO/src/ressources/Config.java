package ressources;

import librairies.StdDraw;

import java.awt.Color;
import java.awt.Font;

/**
 * Classe de configuration, qui contient des constantes utiles notamment a
 * l'affichage, et qui initialise les variables d'affichage en fonction de la
 * dimension de la carte
 */
public class Config {

  /** Taille de la fenêtre */
  final public static double SCALE = 2.5;
  final public static int X_MAX = (int) (SCALE * 717); // 717 est la taille de l'image de background
  final public static int Y_MAX = (int) (SCALE * 375); // 375 est la taille de l'image de background

  /** Police par defaut */
  final public static Font POLICE_PAR_DEFAUT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

  /** Couleur du texte par defaut */
  final public static Color COULEUR_PAR_DEFAULT = new Color(200, 200, 200);

  /** Police du texte de popup */
  final public static Font POLICE_POPUP = new Font("Arial", Font.BOLD, 16);

  /** Couleur du texte dans la fenetre de popup */
  final public static Color POPUP_COULEUR_TEXTE = new Color(0, 0, 0);

  /** Couleur d'arriere-plan de la fenetre de popup */
  final protected static Color POPUP_COULEUR_ARRIERE_PLAN = new Color(255, 255, 230);

  /** Couleur du cadre de la fenetre de popup */
  final protected static Color POPUP_COULEUR_CADRE = new Color(120, 40, 0);

  /**
   * Initialise toutes les constantes necessaires a l'affichage; a appeler une
   * fois avant d'afficher quoi que ce soit
   */
  public static void init() {
    StdDraw.setCanvasSize(X_MAX, Y_MAX);
    StdDraw.setXscale(0, X_MAX);
    StdDraw.setYscale(0, Y_MAX);
  }

}
