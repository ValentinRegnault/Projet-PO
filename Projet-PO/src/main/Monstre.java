package main;

import java.io.File;
import patterns.Pattern;
import ressources.Affichage;
import ressources.Config;

/**
 * Représente un monstre du jeu. Cette classe se sérialise et se désérialise. Typiquement, on créera
 * un monstre au format JSON et on le désérialisera en une instance de cette classe.
 */
public class Monstre extends Entite {
    /**
     * Les monstres ont tous un pattern qui définit leur comportement (les actions qu'il peut faire
     * et la manière dont il les choisit)
     */
    private Pattern pattern;

    public Monstre() {
        super("", 0, 0,
                "assets" + File.separator + "pictures" + File.separator + "monstres"
                        + File.separator + "Acid-slime-S.png",
                0.0, 0.0, 356.0, 259.0);
        this.pattern = null;
    }

    public Monstre(String nom, int pvMax, Pattern pattern, String imageFilePath, double x, double y,
            double width, double height) {
        super(nom, pvMax, 0, imageFilePath, x, y, width, height);
        this.pattern = pattern;
    }

    public void actionSuivante() {
        this.pattern.actionSuivante();
    }

    public Action actionActuelle() {
        return this.pattern.actionActuelle();
    }

    @Override
    public void afficher() {
        super.afficher();
        Affichage.texteCentre(getX() + getWidth() / 2, getY() + getHeight(), "Intention : " + this.pattern.intention().getNom());
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "Monstre [" + super.toString() + "]";
    }
}
