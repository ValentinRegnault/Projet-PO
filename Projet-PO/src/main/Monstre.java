package main;

import patterns.Pattern;

/**
 * Représente un monstre du jeu. Cette classe se sérialise et se désérialise.
 * Typiquement, on créera un monstre au format JSON et on le désérialisera en
 * une instance de cette classe.
 */
public class Monstre extends Entite {
    /**
     * Les monstres ont tous un pattern qui définit leur comportement (les actions
     * qu'il peut faire et la manière dont il les choisit)
     */
    private Pattern pattern;

    public Monstre() {
        super("", 0, 0);
        this.pattern = null;
    }

    public Monstre(String nom, int pvMax, Pattern pattern) {
        super(nom, pvMax, 0);
        this.pattern = pattern;
    }

    /**
     * Joue l'action qui a été programmée dans le pattern du monstre.
     */
    public void jouerAction() {
        this.pattern.jouerAction(this);
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
