package main;
public abstract class Salle {

    protected Hero hero = Partie.getPartie().getHero();

    public abstract boolean jouerSalle();
}

