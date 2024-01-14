package main;

/**
 * Classe abstraite représentant une salle du jeu. Une salle contient le héro,
 * qui est déplacé de salle en salle par Partie.
 */
public abstract class Salle {
    protected Heros hero = Partie.getHeros();

    public abstract boolean jouerSalle();
}
