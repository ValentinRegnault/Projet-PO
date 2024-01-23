/** package principal */
package main;

import librairies.StdDraw;
import ressources.Config;

/**
 * Affichable est la classe abstraite mère de tous ce qui peut être affiché dans le jeu.
 */
public abstract class Affichable {
	/** 
	 * Position x par rapport au bord bas de la fenêtre.
	 */
	private double x = 0;

	/**
	 * Position y par rapport au bord gauche de la fenêtre.
	 */
	private double y = 0;

	protected Affichable(double x, double y) {
		setX(x);
		setY(y);
	}

	public static void initialiserAffichage() {
		StdDraw.enableDoubleBuffering(); // rend l'affichage plus fluide: tout draw est mis en buffer et ne s'affiche
		// qu'au prochain StdDraw.show();
	}

	/**
	 * Affiche ce Renderable via StdDraw.
	 */
	public abstract void afficher();

	public double getX() {
		return x;
	}

	public void setX(double x) {
		if (x > Config.X_MAX) {
			throw new IllegalArgumentException("x ne peut pas être supérieur à " + Config.X_MAX);
		}
		if (x < 0) {
			throw new IllegalArgumentException("x ne peut pas être inférieur à 0");
		}

		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		if (y > Config.Y_MAX) {
			throw new IllegalArgumentException("y ne peut pas être supérieur à " + Config.Y_MAX);
		}
		if (y < 0) {
			throw new IllegalArgumentException("y ne peut pas être inférieur à 0");
		}

		this.y = y;
	}

}
